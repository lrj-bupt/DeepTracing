package org.tracing.core.boot;

import org.tracing.config.Config;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class ServiceConfig implements BootService {
    @Override
    public void prepare() {

    }

    @Override
    public void boot() {
        Properties properties = new Properties();
        try {
            InputStreamReader reader = new InputStreamReader(new FileInputStream("E:/MySkyWalking/src/main/resources/config.properties"), StandardCharsets.UTF_8);
            properties.load(reader);
            initConfig(properties, Config.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void shutdown() {

    }

    private void initConfig(Properties properties, Class<?> configType) throws IllegalAccessException {
        Field[] fields = configType.getFields();
        for (Field field : fields) {
            if (Modifier.isPublic(field.getModifiers()) && Modifier.isStatic(field.getModifiers())) {
                String name = field.getName().toLowerCase();
                System.out.println(name);
                Class<?> type = field.getType();
                if (Map.class.isAssignableFrom(type)) {
                    ParameterizedType genericType = (ParameterizedType) field.getGenericType();
                    Type[] arguments = genericType.getActualTypeArguments();
                    Type keyType = arguments[0];
                    Type valueType = arguments[1];
                    Map map = readMapType(type, properties, keyType, valueType);
                    field.set(null, map);
                } else if (properties.containsKey(name)) {
                    String property = properties.getProperty(name);
                    if (Collection.class.isAssignableFrom(type)) {
                        ParameterizedType genericType = (ParameterizedType) field.getGenericType();
                        Type typeArgument = genericType.getActualTypeArguments()[0];
                        Collection collection = convertToCollection(typeArgument, type, property);
                        field.set(null, collection);
                    } else {
                        Object field_value = convertToTypicalType(type, property);
                        field.set(null, field_value);
                    }
                }
            }
        }
    }

    private static Collection convertToCollection(Type argumentType, Type basicType, String value) {
        Collection<Object> collection;
        if (basicType.equals(Set.class)) {
            collection = new HashSet<>();
        } else if (basicType.equals(TreeSet.class)) {
            collection = new TreeSet<>();
        } else if (basicType.equals(List.class) || basicType.equals(LinkedList.class)) {
            collection = new LinkedList<>();
        } else if (basicType.equals(ArrayList.class)) {
            collection = new ArrayList<>();
        } else {
            throw new UnsupportedOperationException("Unsupported collection type: " + basicType);
        }
        Arrays.stream(value.split(",")).map(v -> convertToTypicalType(argumentType, v)).forEach(collection::add);
        return collection;
    }

    private static Object convertToTypicalType(Type type, String value) {
        Object result = null;
        if (String.class.equals(type)) {
            result = value;
        } else if (int.class.equals(type) || Integer.class.equals(type)) {
            result = Integer.valueOf(value);
        } else if (long.class.equals(type) || Long.class.equals(type)) {
            result = Long.valueOf(value);
        } else if (double.class.equals(type) || Double.class.equals(type)) {
            result = Double.valueOf(value);
        } else if (float.class.equals(type) || Float.class.equals(type)) {
            result = Float.valueOf(value);
        }else if (boolean.class.equals(type)){
            result = Boolean.valueOf(value);
        }else if (type instanceof Class){
            Class<?> clazz = (Class<?>) type;
            if(clazz.isEnum()) {
                result = Enum.valueOf((Class<Enum>)type, value);
            }
        }
        return result;
    }

    private static Map readMapType(Class<?> type, Properties properties, Type keyType, Type valueType) {
        Map<Object, Object> map = initEmptyMap(type);
        properties.forEach((key, value) -> {
            String propertyStringKey = key.toString();

            Object keyObj;
            Object valueObj;
            keyObj = convertToTypicalType(keyType, propertyStringKey);
            valueObj = convertToTypicalType(valueType, value.toString());
            map.put(keyObj, valueObj);
        });

        return map;
    }

    private static Map<Object, Object> initEmptyMap(Class<?> type) {
        if(type.equals(Map.class) || type.equals(HashMap.class)) {
            return new HashMap<>();
        }else if(type.equals(TreeMap.class)){
            return new TreeMap<>();
        }else {
            throw new UnsupportedOperationException("Unsupported map type: " + type);
        }
    }
}
