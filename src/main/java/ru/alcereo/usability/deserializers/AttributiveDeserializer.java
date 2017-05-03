package ru.alcereo.usability.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.TextNode;
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

        TreeNode viewNode = treeNode.get("view");

        String viewString = ((TextNode) viewNode).textValue();

        String[] split = viewString.split("\\.");

        if (split.length!=2)
            throw ctxt.weirdStringException(viewString, String.class, "\"View\" mast have mask: <table>.<property>");

        Attributive attributiveOnView = UsabilityConfiguration.getAttributiveOnView(split[0], split[1]);

        return attributiveOnView;
    }
}
