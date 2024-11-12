package co.casterlabs.koi.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.TypeToken;
import co.casterlabs.rakurai.json.serialization.JsonParseException;
import lombok.NonNull;
import lombok.SneakyThrows;

public abstract class GenericBuilder<T> {
    private static sun.misc.Unsafe UNSAFE;
    static {
        try {
            Field f = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            UNSAFE = (sun.misc.Unsafe) f.get(null);
        } catch (Throwable t) {
            // We don't have an Unsafe instance.
            UNSAFE = null;
        }
    }

    private final Map<String, Object> values = new HashMap<>();
    private final Class<T> clazz;
    private final List<Field> fields;

    protected GenericBuilder(Class<T> clazz) {
        this.clazz = clazz;

        Map<String, Field> fieldFilter = new HashMap<>();
        for (Field field : clazz.getFields()) {
            if (fieldFilter.containsKey(field.getName())) continue;
            fieldFilter.put(field.getName(), field);

            BuilderDefault builderDefault = field.getAnnotation(BuilderDefault.class);
            if (builderDefault == null) continue;
            // We have some defaults to populate!
            String defaultValueJson = builderDefault.value();

            try {
                Class<?>[] defaultValueComponents = getCollectionComponentForField(field);
                TypeToken<?> defaultValueType = TypeToken.of(field.getType(), defaultValueComponents);

                Object defaultValue = Rson.DEFAULT.fromJson(defaultValueJson, defaultValueType);
                this.values.put(field.getName(), defaultValue);
            } catch (JsonParseException | ClassNotFoundException e) {
                throw new IllegalStateException("Unable to deserialize default value " + defaultValueJson + " for " + field.getName(), e);
            }
        }

        // By using this field filtering logic, we can accommodate overridden field
        // types :)
        this.fields = new LinkedList<>(fieldFilter.values());
    }

    @Override
    public String toString() {
        return String.format("%s%s", this.getClass().getSimpleName(), this.values);
    }

    /**
     * Grabs the specified stored value and casts it for you. Useful for adding
     * items to Collections.
     */
    @SuppressWarnings("unchecked")
    protected synchronized <V> V get(String field) {
        return (V) this.values.get(field);
    }

    /**
     * Grabs the specified stored value and casts it for you. Useful for adding
     * items to Collections.
     */
    @SuppressWarnings("unchecked")
    protected synchronized <V> V getOrDefault(String field, V defaultValue) {
        return (V) this.values.getOrDefault(field, defaultValue);
    }

    /**
     * Stores the specified value.
     */
    protected synchronized void put(String field, Object value) {
        this.values.put(field, value);
    }

    /**
     * Adds all the values from the specified builder. Great for complex inheritance
     * relationships.
     */
    protected synchronized void inheritFromBuilder(GenericBuilder<?> other) {
        this.values.putAll(other.values);
    }

    /**
     * Mostly used for toBuilder()s.
     */
    @SneakyThrows
    protected synchronized void inherit(T other) {
        for (Field field : other.getClass().getFields()) {
            String fieldName = field.getName();
            Object value = field.get(other);
            this.values.put(fieldName, value);
        }
    }

    /**
     * Mostly used for toBuilder()s. THIS DOES NO TYPE CHECKING!
     */
    @SneakyThrows
    protected synchronized void inheritUnsafe(Object other) {
        for (Field field : other.getClass().getFields()) {
            String fieldName = field.getName();
            Object value = field.get(other);
            this.values.put(fieldName, value);
        }
    }

    @SuppressWarnings("unchecked")
    public synchronized T build() {
        T object;
        try {
            object = (T) UNSAFE.allocateInstance(this.clazz);
        } catch (InstantiationException | IllegalArgumentException | SecurityException e) {
            throw new IllegalStateException("Unable to allocate instance of class " + this.clazz.getSimpleName(), e);
        }

        // Loop over all of the fields and build a list of missing values.
        {
            List<String> missingValues = new LinkedList<>();
            for (Field field : this.fields) {
                String fieldName = field.getName();

                try {
                    if (!this.values.containsKey(fieldName)) {
                        missingValues.add(fieldName);
                    }
                } catch (IllegalArgumentException e) {
                    throw new IllegalStateException("Unable to inspect field " + fieldName, e);
                }
            }

            // Throw that list as an error. We do it like this so the developer knows
            // everything that's missing and not just the first problem we found.
            if (!missingValues.isEmpty()) {
                throw new IllegalStateException("Missing value(s) for " + missingValues + " during build. Did you forget something?");
            }
        }

        // Start setting the fields. We also check for finalization issues that might
        // occur and throw relevant exceptions for that.
        for (Field field : this.fields) {
            String fieldName = field.getName();
            Object newValue = this.values.get(fieldName);

            try {
                field.setAccessible(true);

                if (Modifier.isFinal(field.getModifiers())) {
                    if (field.get(object) != null) {
                        throw new IllegalStateException("Non-null final fields will just get inlined. Set your final field to `null` for this to work. Did you mean to use @BuilderDefault instead?");
                    }
                }

                field.set(object, newValue);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                throw new IllegalStateException("Unable to modify field " + fieldName, e);
            }
        }

        return object;
    }

    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public static @interface BuilderDefault {
        /**
         * Json value ;)
         */
        String value();
    }

    static Class<?>[] getCollectionComponentForField(Field field) throws ClassNotFoundException {
        Class<?> type = field.getType();

        if (type.isArray()) {
            return new Class<?>[] {
                    type.getComponentType()
            };
        }

        if (Collection.class.isAssignableFrom(type)) {
            ParameterizedType genericType = (ParameterizedType) field.getGenericType();
            Type parameter = genericType.getActualTypeArguments()[0];

            return new Class<?>[] {
                    typeToClass(parameter, field.getDeclaringClass().getClassLoader())
            };
        }

        if (Map.class.isAssignableFrom(type)) {
            ParameterizedType pt = (ParameterizedType) field.getGenericType();
            Type[] typeArguments = pt.getActualTypeArguments();

            return new Class<?>[] {
                    typeToClass(typeArguments[0], field.getDeclaringClass().getClassLoader()),
                    typeToClass(typeArguments[1], field.getDeclaringClass().getClassLoader())
            };
        }

        return new Class<?>[0];
    }

    @SneakyThrows // We assume that no error will be thrown, ever.
    static Class<?> typeToClass(@NonNull Type type, @Nullable ClassLoader cl) {
        if (type instanceof Class) {
            return (Class<?>) type; // Sometimes Java actually gives us a Class<?>!
        }

        return Class.forName(type.getTypeName(), false, cl);
    }

}
