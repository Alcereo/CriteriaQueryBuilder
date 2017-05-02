package ru.alcereo.usability.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import ru.alcereo.usability.predicates.AndUPredicate;
import ru.alcereo.usability.predicates.UPredicate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alcereo on 02.05.17.
 */
public class AndUPredicateDeserializer extends StdDeserializer<AndUPredicate> {

    public AndUPredicateDeserializer() {
        super(AndUPredicate.class);
    }

    @Override
    public AndUPredicate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {

        TreeNode treeNode = p.getCodec().readTree(p);

        if(!treeNode.isArray())
            throw ctxt.mappingException("\"and\" predicate must contain array");

        AndUPredicate result = new AndUPredicate();
        List<UPredicate> predicates = new ArrayList<>();
        result.setChildPredicates(predicates);

        for (int i = 0; i < treeNode.size(); i++) {
            predicates.add(treeNode.get(i).traverse(p.getCodec()).readValueAs(UPredicate.class));
        }

        return result;
    }
}
