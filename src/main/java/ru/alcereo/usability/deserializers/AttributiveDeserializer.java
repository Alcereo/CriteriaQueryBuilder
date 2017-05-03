package ru.alcereo.usability.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.TextNode;
import org.hibernate.mapping.Map;
import ru.alcereo.usability.UsabilityConfiguration;
import ru.alcereo.usability.predicates.Attributive;

import java.io.IOException;

/**
 * Created by alcereo on 03.05.17.
 */
public class AttributiveDeserializer extends StdDeserializer<Attributive> {

    public AttributiveDeserializer() {
        super(Attributive.class);
    }

    @Override
    public Attributive deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {

        TreeNode treeNode = p.getCodec().readTree(p);

        if(!treeNode.isObject())
            throw ctxt.mappingException("\"attributive\" must be an object");

        TreeNode tableNode = treeNode.get("table");
        TreeNode propertyNode = treeNode.get("property");

        if (tableNode==null)
            throw ctxt.mappingException("Attributive must have \"table\" property");

        if (propertyNode==null)
            throw ctxt.mappingException("Attributive must have \"property\" property");

        String tableString =    tableNode   .traverse(p.getCodec()).readValueAs(String.class);
        String propertyString = propertyNode.traverse(p.getCodec()).readValueAs(String.class);

        Attributive attributiveOnView = UsabilityConfiguration.getAttributiveOnView(tableString, propertyString);

        return attributiveOnView;
    }
}
