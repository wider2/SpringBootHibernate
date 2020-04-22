package com.april.task.util;

import com.april.task.model.BitPayModel;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.util.Date;


public class BitPayDeserializer extends JsonDeserializer<BitPayModel> {

	  @Override
	  public BitPayModel deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

	    ObjectCodec oc = jp.getCodec();
	    JsonNode node = oc.readTree(jp);

	    String fromCurrency = node.get("fromCurrency").asText();
	    String code = node.get("code").asText();
	    String name = node.get("name").asText();
	    Double rate = node.get("rate").asDouble();
	    Long timestamp = node.get("datePub").asLong();
	    
	    return new BitPayModel(fromCurrency, code, name, rate, new Date(timestamp));

	  }
}
