package net.portrix.generic.model.type.resolved.raw;

import com.google.common.base.Objects;
import com.google.common.reflect.TypeToken;
import net.portrix.generic.model.type.internal.TypeHierarchy;
import net.portrix.generic.model.type.resolved.ResolvedMethod;
import net.portrix.generic.model.type.resolved.ResolvedParameter;
import net.portrix.generic.model.type.resolved.ResolvedType;

import java.lang.annotation.ElementType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Patrick Bittner on 13.04.2014.
 */
public class RawResolvedMethod<X> extends AbstractRawResolvedExecutable<X> implements ResolvedMethod<X> {

    private final String name;

    private final TypeHierarchy<Class<?>, Method> typeHierarchy;

    private final TypeToken<?> returnType;


    RawResolvedMethod(final String name,
                      final TypeToken<?> returnType,
                      final ResolvedType<X> enclosingType,
                      final TypeHierarchy<Class<?>, Method> typeHierarchy,
                      final List<ResolvedParameter<X>> parameters) {
        super(enclosingType, typeHierarchy, parameters);
        this.name = name;
        this.typeHierarchy = typeHierarchy;
        this.returnType = returnType;
    }

    public static <X> ResolvedMethod<X> create(final String name,
                                               final ResolvedType<X> enclosingType,
                                               final TypeHierarchy<Class<?>, Method> typeHierarchy) {

        final TypeToken<?> returnType = enclosingType
                .getType()
                .resolveType(typeHierarchy.head().getGenericReturnType());


        final List<ResolvedParameter<X>> parameters = new ArrayList<>();

        final ResolvedMethod<X> resolvedMethod = new RawResolvedMethod<X>(name, returnType, enclosingType, typeHierarchy, parameters);

        final int parameterCount = typeHierarchy.head().getParameterCount();
        for (int parameterIndex = 0; parameterIndex < parameterCount; parameterIndex++) {

            final TypeHierarchy<Method, Parameter> parameterList = new TypeHierarchy<>();
            for (final Method method : typeHierarchy.values()) {
                parameterList.add(method, method.getParameters()[parameterIndex]);
            }

            parameters.add(RawResolvedParameter.create(parameterIndex, resolvedMethod, parameterList));
        }

        return resolvedMethod;
    }

    @Override
    public ElementType getElementType() {
        return ElementType.METHOD;
    }

    @Override
    public Object invoke(final X instance, final Object... args) {
        try {
            return typeHierarchy.key(instance.getClass()).invoke(instance, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public TypeToken<?> getReturnType() {
        return returnType;
    }

    @Override
    public boolean equalSignature(Method method) {
        return typeHierarchy.key(method.getDeclaringClass()).equals(method);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(
                getReturnType(),
                getName(),
                getParameters()
                        .stream()
                        .map(ResolvedParameter::getType)
                        .collect(Collectors.toList()), getEnclosingType()
        );
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof ResolvedMethod) {
            final ResolvedMethod<?> other = (ResolvedMethod<?>) obj;

            return Objects.equal(getReturnType(), other.getReturnType())
                    && Objects.equal(getName(), other.getName())
                    && Objects.equal(getParameters(), other.getParameters())
                    && Objects.equal(getEnclosingType(), other.getEnclosingType());
        }

        return false;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("name", getName())
                .add("return", getReturnType())
                .add("parameters", getParameters())
                .toString();
    }
}
