package ru.alcereo.usability.deserializers;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import ru.alcereo.usability.CriteriaBuildData;
import ru.alcereo.usability.UsabilityConfiguration;
import ru.alcereo.usability.predicates.AndUPredicate;
import ru.alcereo.usability.predicates.EqualPredictive;
import ru.alcereo.usability.predicates.OrUPredicate;
import ru.alcereo.usability.predicates.UPredicate;

import javax.persistence.criteria.Predicate;
import java.io.IOException;
import java.util.*;

/**
 * Created by alcereo on 02.05.17.
 */
public class UPredicateDeserializer extends StdDeserializer<UPredicate>{

    public UPredicateDeserializer() {
        super(UPredicate.class);
    }

    public UPredicateDeserializer(Class<?> vc) {
        super(vc);
    }

    public UPredicateDeserializer(JavaType valueType) {
        super(valueType);
    }

    public UPredicateDeserializer(StdDeserializer<?> src) {
        super(src);
    }

    @Override
    public UPredicate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {

        TreeNode treeNode = p.getCodec().readTree(p);

        if (!treeNode.isObject() || treeNode.size()>1){
            throw ctxt.mappingException("Predicate must start with first single predicate key");
        }

        if (treeNode.size()==0)
            return null;

        String predicateString = treeNode.fieldNames().next();

        Map<String, Class<? extends UPredicate>> deserializators =
                UsabilityConfiguration.getPredicatesDeserializationMap();

        Optional<Map.Entry<String, Class<? extends UPredicate>>> deserializator =
                deserializators
                        .entrySet().stream()
                        .filter(stringClassEntry -> stringClassEntry.getKey().equals(predicateString))
                        .findFirst();

        if (!deserializator.isPresent())
            throw ctxt.weirdKeyException(String.class,predicateString,"Predicate: \""+predicateString+"\" not implemented");

        return treeNode
                .get(predicateString)
                .traverse(p.getCodec())
                .readValueAs(deserializator.get().getValue());

    }
}
