package com.ysavitski.example.springmvcandangularjs.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@JsonComponent
@Slf4j
public class LocalDateTimeJsonSerializer extends JsonSerializer<LocalDateTime> {
	@Override
	public void serialize(LocalDateTime value, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException {
		jsonGenerator.writeString(value.atZone(ZoneId.systemDefault()).toInstant().toString());
	}

	public static class LacalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
		@Override
		public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException, JsonProcessingException {
			final JsonNode tree = jsonParser.getCodec().readTree(jsonParser);
			final String dateTimeAsString = tree.textValue();
			log.debug("dateTimeString value @" + dateTimeAsString);
			return LocalDateTime.ofInstant(Instant.parse(dateTimeAsString), ZoneId.systemDefault());
		}
	}
}
