package com.consentframework.shared.api.testcommon.infrastructure;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;

/**
 * Test API request/response parser.
 */
public final class TestContentParser {
    /**
     * Object mapper for parsing JSON requests and responses.
     */
    public static final ObjectMapper OBJECT_MAPPER = JsonMapper.builder()
        .serializationInclusion(Include.NON_NULL)
        .disable(MapperFeature.ALLOW_COERCION_OF_SCALARS)
        .enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        .enable(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE)
        .enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING)
        .enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING)
        .build();

    /**
     * Parse a string request body into a TestCreateRequestContent object.
     *
     * @param requestBody The request body to parse.
     * @return Parsed TestCreateRequestContent object.
     * @throws JsonProcessingException if unable to parse the request.
     */
    public static TestCreateRequestContent parseRequestBody(final String requestBody) throws JsonProcessingException {
        return OBJECT_MAPPER.readValue(requestBody, TestCreateRequestContent.class);
    }

    /**
     * Parse a string response body into a TestCreateResponseContent object.
     *
     * @param responseBody The response body to parse.
     * @return Parsed TestCreateResponseContent object.
     * @throws JsonProcessingException if unable to parse the response.
     */
    public static TestCreateResponseContent parseResponseBody(final String responseBody) throws JsonProcessingException {
        return OBJECT_MAPPER.readValue(responseBody, TestCreateResponseContent.class);
    }
}
