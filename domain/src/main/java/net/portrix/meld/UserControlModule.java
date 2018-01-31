package net.portrix.meld;

import net.portrix.generic.ddd.Module;
import net.portrix.generic.rest.SecurityAction;
import net.portrix.generic.rest.jsr339.*;
import net.portrix.meld.usercontrol.Permission;
import net.portrix.meld.usercontrol.PermissionManager;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.ws.rs.core.UriBuilder;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * @author by Patrick Bittner on 10.06.15.
 */
@ApplicationScoped
public class UserControlModule {

    private final static Logger log = LoggerFactory.getLogger(UserControlModule.class);

    private final PermissionManager permissionManager;

    private final ResourceContainer resourceContainer;

    @Inject
    public UserControlModule(PermissionManager permissionManager,
                             ResourceContainer resourceContainer) {
        this.permissionManager = permissionManager;
        this.resourceContainer = resourceContainer;
    }

    public UserControlModule() {
        this(null, null);
    }

    public void onSecurityAction(@Observes SecurityAction securityAction) {

        final List<Permission> permissions = permissionManager.findAll();

        Permission permission = find(permissions, securityAction);

        if (permission == null) {
            securityAction.setValid(false);
        } else {
            securityAction.setValid(permissionManager.hasPermissions(permission));
        }

    }

    private Permission find(List<Permission> permissions, SecurityAction securityAction) {
        for (Permission permission : permissions) {
            final UriBuilder uriBuilder = UriBuilder.fromUri(permission.getPath());

            final URI uri;
            try {
                uri = uriBuilder.buildFromMap(securityAction.getParams());
                String path = StringUtils.removeStart(securityAction.getPath(), "/");
                path = StringUtils.removeEnd(path, "/");
                if (uri.getPath().equals(path) && permission.getMethod().equals(securityAction.getMethod())) {
                    return permission;
                }
            } catch (IllegalArgumentException e) {
                // No Op
            }
        }

        return null;
    }

    @Produces
    public Module produce() {
        return () -> {
            final Set<Resource<?>> resources = resourceContainer.getResources();
            processFoundResources(resources);
            List<Permission> permissions = permissionManager.findAll();
            processToDelete(resources, permissions);
        };

    }

    private void processToDelete(Set<Resource<?>> resources, List<Permission> permissions) {
        for (Permission permission : permissions) {
            Permission toDelete = processToDelete(permission, resources);

            if (toDelete != null) {
                String statement = "delete from uc_role_uc_permission WHERE permissions_id = '%s';";
                log.error(String.format("Permission Relations to delete\n" + statement, permission.getId()) );

                String statement1 = "delete from uc_permission WHERE id = '%s';";
                log.error(String.format("Permission to delete\n" + statement1, permission.getId()) );
            }
        }

    }

    private Permission processToDelete(Permission permission, Set<Resource<?>> resources) {
        for (Resource<?> resource : resources) {

            for (Operation operation : resource.getOperations()) {
                if (operation.isSecured()) {
                    for (PathName pathName : operation.getDenormalizedUrls()) {

                        if (permission.getMethod().equals(operation.getHttpMethod())
                                && permission.getPath().equals(pathName.getPath())) {
                            return null;
                        }

                    }
                }

            }
            for (Locator locator : resource.getLocators()) {
                processToDelete(permission, locator.getTypes());
            }
        }
        return permission;
    }

    private void processFoundResources(Set<Resource<?>> resources) {
        for (Resource<?> resource : resources) {
            for (Operation operation : resource.getOperations()) {
                if (operation.isSecured()) {
                    for (PathName pathName : operation.getDenormalizedUrls()) {

                        Permission permission = new Permission();

                        permission.setName(pathName.getName());
                        permission.setPath(pathName.getPath());
                        permission.setMethod(operation.getHttpMethod());

                        Permission foundPermission = permissionManager.find(permission);

                        if (foundPermission == null) {
                            String id = UUID.randomUUID().toString();
                            String statement = "INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('%s', '%s', '%s', '%s', now(), now(), 0);";
                            log.error(String.format("Missing Permission\n" + statement, id, permission.getMethod(), permission.getName(), permission.getPath()) );
                        }
                    }
                }

            }
            for (Locator locator : resource.getLocators()) {
                processFoundResources(locator.getTypes());
            }
        }
    }

}
