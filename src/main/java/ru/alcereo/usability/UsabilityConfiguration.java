package ru.alcereo.usability;

import org.reflections.Reflections;
import ru.alcereo.usability.annotations.UMetaClass;
import ru.alcereo.usability.annotations.UMetaMethod;
import ru.alcereo.usability.annotations.UPredicateView;
import ru.alcereo.usability.predicates.Attributive;
import ru.alcereo.usability.predicates.UPredicate;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by alcereo on 02.05.17.
 */
public class UsabilityConfiguration {

    private static Map<String, Map<String, Method>> uMetaClasses = new HashMap<>();
    private static Map<String, Class<? extends UPredicate>> predicatesDeserializationMap = new HashMap<>();

    static {
        Reflections reflections = new Reflections("ru.alcereo");

        reflections
                .getTypesAnnotatedWith(UMetaClass.class)
                .forEach(aClass -> {

                            String className = aClass.getAnnotation(UMetaClass.class).value();

                            Map<String, Method> metaMethodsMap = new HashMap<>();

                            Arrays.asList(aClass.getMethods())
                                    .forEach(method -> {
                                        UMetaMethod annotation = method.getAnnotation(UMetaMethod.class);
                                        if (annotation != null)
                                            metaMethodsMap.put(method.getName(), method);
                                    });

                            uMetaClasses.put(className, metaMethodsMap);
                        }
                );

        reflections
                .getTypesAnnotatedWith(UPredicateView.class)
                .forEach(aClass ->
                        predicatesDeserializationMap.put(
                                aClass.getAnnotation(UPredicateView.class).value(),
                                (Class<? extends UPredicate>) aClass
                        )
                );
    }

    public static Map<String, Map<String, Method>> getuMetaClasses() {
        return uMetaClasses;
    }

    public static Map<String, Class<? extends UPredicate>> getPredicatesDeserializationMap() {
        return predicatesDeserializationMap;
    }

    public static Attributive getAttributiveOnView(String table, String method){

        try {
            return (Attributive) uMetaClasses.get(table).get(method).invoke(null);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }

    }
}
