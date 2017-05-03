package ru.alcereo.usability.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import ru.alcereo.usability.predicates.Attributive;
import ru.alcereo.usability.predicates.EqualPredictive;

import java.io.IOException;

/**
 * Created by alcereo on 02.05.17.
 */
public class EqualPredictiveDeserializer extends StdDeserializer<EqualPredictive>{

    public EqualPredictiveDeserializer() {
        super(EqualPredictive.class);
    }

    @Override
    public EqualPredictive deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {

        TreeNode treeNode = p.getCodec().readTree(p);

        if (!treeNode.isObject())
            throw ctxt.mappingException("\"Equal\" predictive must be an object");

        TreeNode objectNode = treeNode.get("object");
        TreeNode attributiveNode = treeNode.get("attribute");

        if (objectNode==null)
            throw ctxt.mappingException("Field \"object\" not found");

        if (attributiveNode==null)
            throw ctxt.mappingException("Field \"attributiveNode\" not found");

        //TODO: Создание объекта должно быть единообразным
        EqualPredictive result = new EqualPredictive();
        result.setSubject(
                objectNode.traverse(p.getCodec()).readValueAs(Object.class)
        );

        result.setAttributive(
                attributiveNode.traverse(p.getCodec()).readValueAs(Attributive.class)
        );

        return result;
    }
}
