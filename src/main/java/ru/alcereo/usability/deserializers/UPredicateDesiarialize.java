package ru.alcereo.usability.deserializers;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import ru.alcereo.usability.CriteriaBuildData;
import ru.alcereo.usability.predicates.AndUPredicate;
import ru.alcereo.usability.predicates.OrUPredicate;
import ru.alcereo.usability.predicates.UPredicate;

import javax.persistence.criteria.Predicate;
import java.io.IOException;
import java.util.*;

/**
 * Created by alcereo on 02.05.17.
 */
public class UPredicateDesiarialize extends StdDeserializer<UPredicate>{

    public UPredicateDesiarialize() {
        super(UPredicate.class);
    }

    public UPredicateDesiarialize(Class<?> vc) {
        super(vc);
    }

    public UPredicateDesiarialize(JavaType valueType) {
        super(valueType);
    }

    public UPredicateDesiarialize(StdDeserializer<?> src) {
        super(src);
    }

    private static final String AND = "and";
    private static final String OR = "or";

    @Override
    public UPredicate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {

        TreeNode treeNode = p.getCodec().readTree(p);

        if (!treeNode.isObject() || treeNode.size()>1){
            throw ctxt.mappingException("Predicate must start with single compound predicate key");
        }

        if (treeNode.size()==0)
            return null;

        UPredicate result = null;

        String predicateField = treeNode.fieldNames().next();

        Map<String, Class<? extends UPredicate>> deserializators = new HashMap<>();
        deserializators.put(AND, AndUPredicate.class);
        deserializators.put(OR, OrUPredicate.class);


        Optional<Map.Entry<String, Class<? extends UPredicate>>> deserializator = deserializators.entrySet()
                .stream().filter(predicateField::equals).findFirst();

        if (!deserializator.isPresent())
            throw ctxt.weirdKeyException(String.class,predicateField,"Predicate: \""+predicateField+"\" not implemented");

        result = treeNode
                .get(predicateField)
                .traverse(p.getCodec())
                .readValueAs(deserializator.get().getValue());

        return result;
    }
}
