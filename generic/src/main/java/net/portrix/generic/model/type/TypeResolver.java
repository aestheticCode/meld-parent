package net.portrix.generic.model.type;

import com.google.common.cache.Cache;
import com.google.common.reflect.TypeToken;
import net.portrix.generic.model.TCCLCache;
import net.portrix.generic.model.type.internal.Reflections;
import net.portrix.generic.model.type.resolved.ResolvedConstructor;
import net.portrix.generic.model.type.resolved.ResolvedField;
import net.portrix.generic.model.type.resolved.ResolvedMethod;
import net.portrix.generic.model.type.resolved.ResolvedType;
import net.portrix.generic.model.type.resolved.generic.GenericResolvedType;
import net.portrix.generic.model.type.resolved.raw.RawResolvedConstructor;
import net.portrix.generic.model.type.resolved.raw.RawResolvedField;
import net.portrix.generic.model.type.resolved.raw.RawResolvedMethod;
import net.portrix.generic.model.type.resolved.raw.RawResolvedType;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * @author Patrick Bittner on 13.04.2014.
 */
public final class TypeResolver {

    private static final Cache<Class<?>, ResolvedType<?>> cache = new TCCLCache<>();

    public static <X> ResolvedType<X> resolve(final Class<X> classType) {
        try {
            @SuppressWarnings("unchecked")
            final ResolvedType<X> resolvedType = (ResolvedType<X>) cache.get(classType, () -> resolveInternal(classType));
            return resolvedType;
        } catch (final ExecutionException e) {
            throw new IllegalStateException(e);
        }
    }

    public static <X> ResolvedType<X> resolve(final TypeToken<X> typeToken) {
        @SuppressWarnings("unchecked")
        final Class<X> rawType = (Class<X>) typeToken.getRawType();

        final ResolvedType<X> resolvedType = resolve(rawType);

        return new GenericResolvedType<>(typeToken, resolvedType);
    }

    private static <X> ResolvedType<X> resolveInternal(final Class<X> classType) {

        final List<ResolvedConstructor<X>> resolvedConstructors = new ArrayList<>();

        final List<ResolvedField<X>> resolvedFields = new ArrayList<>();

        final List<ResolvedMethod<X>> resolvedMethods = new ArrayList<>();

        final List<Class<?>> hierarchy = Reflections.hierarchy(classType);

        final ResolvedType<X> resolvedType = RawResolvedType.create(classType, hierarchy, resolvedConstructors, resolvedFields, resolvedMethods);

        resolvedConstructors.addAll(
                Reflections.constructors(classType)
                        .stream()
                        .map(typeHierarchy -> RawResolvedConstructor.create(resolvedType, typeHierarchy))
                        .collect(Collectors.toList())
        );

        resolvedFields.addAll(
                Reflections.fields(hierarchy)
                        .entrySet()
                        .stream()
                        .map(entry -> RawResolvedField.create(entry.getKey().getName(), resolvedType, entry.getValue()))
                        .collect(Collectors.toList())
        );

        resolvedMethods.addAll(
                Reflections.methods(hierarchy)
                        .entrySet()
                        .stream()
                        .map(entry -> RawResolvedMethod.create(entry.getKey().getName(), resolvedType, entry.getValue()))
                        .collect(Collectors.toList())
        );

        return resolvedType;
    }

}
