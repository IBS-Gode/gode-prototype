package org.ibs.cdx.gode.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.ibs.cdx.gode.exception.ClassicRuntimeException;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.Map;

@Configuration
public class ObjectTranslator {

    private ObjectMapper objectMapper;

    public ObjectTranslator() {
        this.objectMapper = new ObjectMapper().registerModule(new ParameterNamesModule())
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule())
                .enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);;
    }

    public String mapToString(Object object) throws IOException {
        return objectMapper.writeValueAsString(object);
    }


    public <T> T mapToObject(String source, Class<T> clazz){
        try {
            return objectMapper.readValue(source, clazz);
        } catch (Exception e) {
            throw new ClassicRuntimeException("Data parsing failure", e);
        }
    }

    public <T> T mapToObject(Object source, Class<T> clazz) {
        return objectMapper.convertValue(source, clazz);
    }

    public Map<String, Object> mapObject(Object source) {
        return objectMapper.convertValue(source, new TypeReference<Map<String, Object>>() {
        });
    }

    public <T> T readObject(Map<String, Object> source, Class<T> targetType) {
        try {
            return objectMapper.convertValue(objectMapper.writeValueAsString(source), targetType);
        } catch (JsonProcessingException e) {
            throw new ClassicRuntimeException("Data parsing failure", e);
        }
    }
}