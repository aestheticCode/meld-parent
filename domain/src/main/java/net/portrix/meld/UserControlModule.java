package net.portrix.meld;

import net.portrix.generic.ddd.Module;
import net.portrix.generic.rest.SecurityAction;
import net.portrix.generic.rest.jsr339.*;
import net.portrix.meld.usercontrol.Permission;
import net.portrix.meld.usercontrol.PermissionManager;
import org.apache.commons.lang3.StringUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.List;
import java.util.Set;

/**
 * @author by Patrick Bittner on 10.06.15.
 */
@ApplicationScoped
public class UserControlModule {

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
                    return  permission;
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

            process(resources);

        };

    }

    private void process(Set<Resource<?>> resources) {
        for (Resource<?> resource : resources) {
            for (Operation operation : resource.getOperations()) {
                if (operation.isSecured()) {
                    for (PathName pathName : operation.getDenormalizedUrls()) {

                        Permission permission = new Permission();

                        permission.setName(pathName.getName());
                        permission.setPath(pathName.getPath());
                        permission.setMethod(operation.getHttpMethod());

                        permissionManager.save(permission);
                    }
                }

            }
            for (Locator locator : resource.getLocators()) {
                process(locator.getTypes());
            }
        }
    }

}
