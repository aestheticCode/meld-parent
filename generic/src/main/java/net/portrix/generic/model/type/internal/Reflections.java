package net.portrix.generic.model.type.internal;

import net.portrix.generic.model.type.signature.FieldSignature;
import net.portrix.generic.model.type.signature.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * @author Patrick Bittner on 13.04.2014.
 */
public final class Reflections {

    private static final Logger log = LoggerFactory.getLogger(Reflections.class);

    public static List<TypeHierarchy<Class<?>, Constructor<?>>> constructors(final Class<?> declaringClass) {
        final List<TypeHierarchy<Class<?>, Constructor<?>>> result = new ArrayList<>();
        for (final Constructor<?> constructor : declaringClass.getDeclaredConstructors()) {
            if (declaringClass.isEnum()) {
                if (Arrays.equals(constructor.getParameterTypes(), new Class<?>[]{String.class, int.class})) {
                    // Filter out Default Enum Constructor
                    continue;
                }
            }
            final TypeHierarchy<Class<?>, Constructor<?>> hierarchy = new TypeHierarchy<>();
            hierarchy.add(declaringClass, constructor);
            result.add(hierarchy);
        }
        return result;
    }

    public static Map<MethodSignature, TypeHierarchy<Class<?>, Method>> methods(final List<Class<?>> hierarchy) {
        final Map<MethodSignature, TypeHierarchy<Class<?>, Method>> result = new LinkedHashMap<>();
        for (final Class<?> declaringClass : hierarchy) {
            for (final Method classMethod : declaringClass.getDeclaredMethods()) {
                if (!classMethod.isBridge()) {
                    final MethodSignature methodSignature = new MethodSignature(classMethod.getName(), classMethod.getGenericParameterTypes());
                    TypeHierarchy<Class<?>, Method> methodMap = result.get(methodSignature);
                    if (methodMap == null) {
                        methodMap = new TypeHierarchy<>();
                        result.put(methodSignature, methodMap);
                    }
                    methodMap.add(declaringClass, classMethod);
                }
            }
            for (final Class<?> declaringInterface : declaringClass.getInterfaces()) {
                for (final Method interfaceMethod : declaringInterface.getDeclaredMethods()) {
                    if (!interfaceMethod.isBridge()) {
                        final MethodSignature methodSignature = new MethodSignature(interfaceMethod.getName(), interfaceMethod.getGenericParameterTypes());
                        TypeHierarchy<Class<?>, Method> methodMap = result.get(methodSignature);
                        if (methodMap == null) {
                            methodMap = new TypeHierarchy<>();
                            result.put(methodSignature, methodMap);
                        }
                        methodMap.add(declaringInterface, interfaceMethod);
                    }
                }
            }
        }
        return result;
    }

    public static Map<FieldSignature, TypeHierarchy<Class<?>, Field>> fields(final List<Class<?>> hierarchy) {
        final Map<FieldSignature, TypeHierarchy<Class<?>, Field>> result = new LinkedHashMap<>();
        for (final Class<?> declaringClass : hierarchy) {
            for (final Field declaredField : declaringClass.getDeclaredFields()) {
                final FieldSignature fieldSignature = new FieldSignature(declaredField.getName(), declaredField.getGenericType());
                final TypeHierarchy<Class<?>, Field> typeHierarchy = result.get(fieldSignature);
                if (typeHierarchy == null) {
                    final TypeHierarchy<Class<?>, Field> newTypeHierarchy = new TypeHierarchy<>();
                    result.put(fieldSignature, newTypeHierarchy);
                    newTypeHierarchy.add(declaringClass, declaredField);
                } else {
                    if (Modifier.isPrivate(declaredField.getModifiers())) {
                        log.warn(String.format("Hidden Field %s at %s is private and will be dropped", declaredField.getName(), declaringClass.getName()));
                    } else {
                        typeHierarchy.add(declaringClass, declaredField);
                    }
                }
            }
        }

        return result;
    }

    public static List<Class<?>> hierarchy(final Class<?> classType) {
        if (classType.isInterface()) {
            return Collections.singletonList(classType);
        }
        final List<Class<?>> result = new ArrayList<>();
        Class<?> cursor = classType;
        while (cursor.getSuperclass() != null) {
            result.add(cursor);
            cursor = cursor.getSuperclass();
        }
        return result;
    }


}
