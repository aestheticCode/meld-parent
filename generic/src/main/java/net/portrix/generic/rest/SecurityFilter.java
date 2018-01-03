package net.portrix.generic.rest;

import org.picketlink.Identity;
import org.picketlink.credential.DefaultLoginCredentials;
import org.picketlink.idm.credential.TokenCredential;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Patrick Bittner on 08.06.2015.
 */
@Provider
@Secured
public class SecurityFilter implements ContainerRequestFilter {

    private final Event<SecurityAction> securityActionEvent;

    private final Identity identity;

    private final DefaultLoginCredentials credentials;

    @Inject
    public SecurityFilter(Event<SecurityAction> securityActionEvent,
                          Identity identity,
                          DefaultLoginCredentials credentials) {
        this.securityActionEvent = securityActionEvent;
        this.identity = identity;
        this.credentials = credentials;
    }

    public SecurityFilter() {
        this(null, null, null);
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        if (identity.isLoggedIn()) {

            final UriInfo uriInfo = requestContext.getUriInfo();

            final Map<String, Object> params = new HashMap<>();

            for (Map.Entry<String, List<String>> entry : uriInfo.getPathParameters().entrySet()) {
                for (String value : entry.getValue()) {
                    params.put(entry.getKey(), value);
                }
            }

            for (Map.Entry<String, List<String>> entry : uriInfo.getQueryParameters().entrySet()) {
                for (String value : entry.getValue()) {
                    params.put(entry.getKey(), value);
                }
            }

            final SecurityAction securityAction = new SecurityAction(uriInfo.getPath(), requestContext.getMethod(), params);

            securityActionEvent.fire(securityAction);

            if (! securityAction.isValid()) {
                requestContext.abortWith(Response.status(Response.Status.FORBIDDEN).build());
            }

        } else {
            Cookie rememberMe = requestContext.getCookies().get("rememberMe");
            if (rememberMe != null) {
                String[] split = rememberMe.getValue().split("\\.");
                credentials.setCredential(new TokenCredential(new LoginToken(split[0], split[1])));
                Identity.AuthenticationResult authenticationResult = identity.login();

                if (authenticationResult.equals(Identity.AuthenticationResult.SUCCESS)) {
                    filter(requestContext);
                } else {
                    requestContext.abortWith(Response.status(Response.Status.FORBIDDEN).build());
                }
            } else {
                requestContext.abortWith(Response.status(Response.Status.FORBIDDEN).build());
            }
        }

    }
}
