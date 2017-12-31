package net.portrix.generic.rest;

import net.portrix.generic.model.bean.BeanIntrospector;
import net.portrix.generic.model.bean.BeanModel;
import net.portrix.generic.model.bean.BeanProperty;
import net.portrix.generic.model.type.TypeResolver;
import net.portrix.generic.model.type.resolved.ResolvedAnnotatedElement;
import net.portrix.generic.model.type.resolved.ResolvedMethod;
import net.portrix.generic.model.type.resolved.ResolvedParameter;
import net.portrix.generic.model.type.resolved.ResolvedType;
import net.portrix.generic.rest.api.Link;
import net.portrix.generic.rest.jsr339.ResourceUtil;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import org.jboss.resteasy.specimpl.ResteasyUriBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.event.Event;
import javax.ws.rs.*;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author by Patrick Bittner on 11.06.15.
 */
public class URLBuilder<B> {

    private static final Logger log = LoggerFactory.getLogger(URLBuilder.class);

    private final Event<SecurityAction> securityActionEvent;

    private final UriBuilder uriBuilder;

    private URLBuilder<?> child;

    private B instance;

    private String httpMethod;

    private String rel;

    private Map<String, Object> params = new HashMap<>();

    public URLBuilder(final Class<B> aClass,
                      final Event<SecurityAction> securityActionEvent,
                      final UriBuilder uriBuilder) {
        this.securityActionEvent = securityActionEvent;
        this.uriBuilder = uriBuilder;

        ResolvedType<B> type = TypeResolver.resolve(aClass);

        final Path classPath = type.getAnnotation(Path.class);

        instance = (B) Enhancer.create(aClass, (MethodInterceptor) (o, thisMethod, args, methodProxy) -> {
            final ResolvedMethod<B> resolvedMethod = type.getMethods()
                    .stream()
                    .filter(method -> method.equalSignature(thisMethod))
                    .findFirst()
                    .get();

            Path methodPath = resolvedMethod.getAnnotation(Path.class);

            if (classPath != null && methodPath != null) {
                uriBuilder.path(aClass);
                uriBuilder.path(thisMethod);
            }

            if (classPath == null && methodPath != null) {
                uriBuilder.path(thisMethod);
            }

            if (classPath != null && methodPath == null) {
                uriBuilder.path(aClass);
            }

            httpMethod = ResourceUtil.httpMethod(resolvedMethod);

            for (int index = 0; index < args.length; index++) {
                Object arg = args[index];
                ResolvedParameter<B> parameter = resolvedMethod.getParameters().get(index);

                readParameter(arg, parameter);

                BeanParam beanParam = parameter.getAnnotation(BeanParam.class);
                if (beanParam != null && arg != null) {

                    BeanModel<?> beanModel = BeanIntrospector.create(parameter.getType());

                    for (BeanProperty<?, ?> beanProperty : beanModel.getProperties()) {

                        readParameter(((BeanProperty<Object, ?>) beanProperty).apply(arg), beanProperty);

                    }

                }

            }

            if (httpMethod == null) {
                child = new URLBuilder<>(resolvedMethod.getReturnType().getRawType(), securityActionEvent, uriBuilder);
                return child.instance();
            } else {
                return null;
            }

        });
    }

    private void readParameter(Object arg, ResolvedAnnotatedElement parameter) {
        PathParam pathParam = parameter.getAnnotation(PathParam.class);
        if (pathParam != null) {
            params.put(pathParam.value(), arg);
        }

        QueryParam queryParam = parameter.getAnnotation(QueryParam.class);
        if (queryParam != null && arg != null) {
            uriBuilder.queryParam(queryParam.value(), arg);
        }

        MatrixParam matrixParam = parameter.getAnnotation(MatrixParam.class);
        if (matrixParam != null && arg != null) {
            uriBuilder.matrixParam(matrixParam.value(), arg);
        }
    }

    private B instance() {
        return instance;
    }

    private String buildMethod() {
        if (httpMethod != null) {
            return httpMethod;
        }

        return child.buildMethod();
    }

    public URLBuilder<B> record(Consumer<B> consumer) {
        consumer.accept(instance);
        return this;
    }

    public URLBuilder<B> rel(String rel) {
        this.rel = rel;
        return this;
    }

    public void buildSecured(Consumer<Link> consumer) {

        URI url = uriBuilder.buildFromMap(params);

        SecurityAction securityAction = new SecurityAction(url.getPath(), buildMethod(), params);

        securityActionEvent.fire(securityAction);

        if (securityAction.isValid()) {
            consumer.accept(new Link("service" + url.toASCIIString(), buildMethod(), rel));
        }
    }

    public void build(Consumer<Link> consumer) {

        URI url = uriBuilder.buildFromMap(params);

        consumer.accept(new Link("service" + url.toASCIIString(), buildMethod(), rel));

    }

    public Link build() {

        String s = ((ResteasyUriBuilder) uriBuilder).getPath();

        return new Link(s, buildMethod(), rel);

    }

    public Link generate() {
        URI url = uriBuilder.buildFromMap(params);
        return new Link("service" + url.toASCIIString(), buildMethod(), rel);
    }


    public URI generateUri() {
        try {
            return new URI("service" + uriBuilder.buildFromMap(params).toASCIIString());
        } catch (URISyntaxException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }
}
