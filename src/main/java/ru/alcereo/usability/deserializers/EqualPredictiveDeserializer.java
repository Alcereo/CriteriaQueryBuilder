package ru.alcereo.usability.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
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



        return null;
    }
}
