package ru.alcereo.usability;

import org.reflections.Reflections;
import ru.alcereo.usability.annotations.UMetaClass;
import ru.alcereo.usability.annotations.UPredicateView;
import ru.alcereo.usability.predicates.UPredicate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by alcereo on 02.05.17.
 */
public class UsabilityConfiguration {

    private static Map<String, Class> uMetaClasses = new HashMap<>();
    private static Map<String, Class<? extends UPredicate>> predicatesDeserializationMap = new HashMap<>();

    static {
        Reflections reflections = new Reflections("ru.alcereo");

        reflections
                .getTypesAnnotatedWith(UMetaClass.class)
                .forEach(aClass ->
                                uMetaClasses.put(
                                        aClass.getAnnotation(UMetaClass.class).value(),
                                        aClass
                                )
                );

        reflections
                .getTypesAnnotatedWith(UPredicateView.class)
                .forEach(aClass ->
                        predicatesDeserializationMap.put(
                                aClass.getAnnotation(UPredicateView.class).value(),
                                (Class<? extends UPredicate>)aClass
                        )
                );
    }

    public static Map<String, Class> getuMetaClasses(){
        return uMetaClasses;
    }

    public static Map<String, Class<? extends UPredicate>> getPredicatesDeserializationMap() {
        return predicatesDeserializationMap;
    }
}
