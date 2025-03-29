package com.consentframework.shared.api.domain.parsers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.consentframework.shared.api.domain.constants.HttpMethod;
import com.consentframework.shared.api.domain.entities.ApiRequest;
import com.consentframework.shared.api.domain.exceptions.BadRequestException;
import com.consentframework.shared.api.testcommon.constants.TestConstants;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Map;

class ApiQueryStringParameterParserTest {
    private static final Map<String, Object> INCOMPLETE_QUERY_PARAMETERS = Map.of(
        "TestStringParamKey", "TestStringParamValue",
        "TestIntegerParamKey", 100,
        "TestBooleanParamKey", true
    );

    @Nested
    class ParseStringParameter {
        @Test
        void testWhenRequestNull() throws BadRequestException {
            validateReturnsNull(null, TestConstants.PAGE_TOKEN_PARAM_NAME);
        }

        @Test
        void testWhenNoQueryParameters() throws BadRequestException {
            final ApiRequest request = buildRequest(null);
            validateReturnsNull(request, TestConstants.PAGE_TOKEN_PARAM_NAME);
        }

        @Test
        void testWhenMissingQueryParameter() throws BadRequestException {
            final ApiRequest request = buildRequest(INCOMPLETE_QUERY_PARAMETERS);
            validateReturnsNull(request, TestConstants.PAGE_TOKEN_PARAM_NAME);
        }

        @Test
        void testWhenWrongType() {
            final ApiRequest request = buildRequest(TestConstants.TEST_PAGINATION_QUERY_PARAMETERS);

            final BadRequestException thrownException = assertThrows(BadRequestException.class, () ->
                ApiQueryStringParameterParser.parseStringQueryStringParameter(request, TestConstants.LIMIT_PARAM_NAME));

            final String expectedErrorMessage = String.format(ApiQueryStringParameterParser.PARSE_FAILURE_MESSAGE,
                TestConstants.LIMIT_PARAM_NAME);
            assertEquals(expectedErrorMessage, thrownException.getMessage());
        }

        @Test
        void testWhenQueryParameterPresent() throws BadRequestException {
            final ApiRequest request = buildRequest(TestConstants.TEST_PAGINATION_QUERY_PARAMETERS);
            final String parameterValue = ApiQueryStringParameterParser.parseStringQueryStringParameter(
                request, TestConstants.PAGE_TOKEN_PARAM_NAME);
            assertEquals(TestConstants.TEST_PAGE_TOKEN, parameterValue);
        }

        private void validateReturnsNull(final ApiRequest request, final String parameterName)
                throws BadRequestException {
            final String parameterValue = ApiQueryStringParameterParser.parseStringQueryStringParameter(request, parameterName);
            assertNull(parameterValue);
        }
    }

    @Nested
    class ParseIntParameter {
        @Test
        void testWhenRequestNull() throws BadRequestException {
            validateReturnsNull(null, TestConstants.LIMIT_PARAM_NAME);
        }

        @Test
        void testWhenNoQueryParameters() throws BadRequestException {
            final ApiRequest request = buildRequest(null);
            validateReturnsNull(request, TestConstants.LIMIT_PARAM_NAME);
        }

        @Test
        void testWhenMissingQueryParameter() throws BadRequestException {
            final ApiRequest request = buildRequest(INCOMPLETE_QUERY_PARAMETERS);
            validateReturnsNull(request, TestConstants.LIMIT_PARAM_NAME);
        }

        @Test
        void testWhenWrongType() {
            final Map<String, Object> queryParameters = Map.of(TestConstants.PAGE_TOKEN_PARAM_NAME, Map.of());
            final ApiRequest request = buildRequest(queryParameters);

            final BadRequestException thrownException = assertThrows(BadRequestException.class, () ->
                ApiQueryStringParameterParser.parseIntQueryStringParameter(request, TestConstants.PAGE_TOKEN_PARAM_NAME));

            final String expectedErrorMessage = String.format(ApiQueryStringParameterParser.PARSE_FAILURE_MESSAGE,
                TestConstants.PAGE_TOKEN_PARAM_NAME);
            assertEquals(expectedErrorMessage, thrownException.getMessage());
        }

        @Test
        void testWhenIntegerPassedAsString() throws BadRequestException {
            final String limitStringValue = "5";
            final Map<String, Object> queryParameters = Map.of(TestConstants.LIMIT_PARAM_NAME, limitStringValue);
            final ApiRequest request = buildRequest(queryParameters);

            final Integer parsedParameterValue = ApiQueryStringParameterParser.parseIntQueryStringParameter(request,
                TestConstants.LIMIT_PARAM_NAME);
            assertEquals(5, parsedParameterValue);
        }

        @Test
        void testWhenStringNotParseableAsInteger() {
            final Map<String, Object> queryParameters = Map.of(TestConstants.PAGE_TOKEN_PARAM_NAME, "InvalidPageToken");
            final ApiRequest request = buildRequest(queryParameters);

            final BadRequestException thrownException = assertThrows(BadRequestException.class, () ->
                ApiQueryStringParameterParser.parseIntQueryStringParameter(request, TestConstants.PAGE_TOKEN_PARAM_NAME));

            final String expectedErrorMessage = String.format(ApiQueryStringParameterParser.PARSE_FAILURE_MESSAGE,
                TestConstants.PAGE_TOKEN_PARAM_NAME);
            assertEquals(expectedErrorMessage, thrownException.getMessage());
        }

        @Test
        void testWhenQueryParameterPresent() throws BadRequestException {
            final ApiRequest request = buildRequest(TestConstants.TEST_PAGINATION_QUERY_PARAMETERS);
            final Integer parameterValue = ApiQueryStringParameterParser.parseIntQueryStringParameter(
                request, TestConstants.LIMIT_PARAM_NAME);
            assertEquals(TestConstants.TEST_PAGE_LIMIT, parameterValue);
        }

        private void validateReturnsNull(final ApiRequest request, final String parameterName)
                throws BadRequestException {
            final Integer parameterValue = ApiQueryStringParameterParser.parseIntQueryStringParameter(request, parameterName);
            assertNull(parameterValue);
        }
    }

    private ApiRequest buildRequest(final Map<String, Object> queryParameters) {
        return new ApiRequest(HttpMethod.GET.name(), "/", "/", null, queryParameters, null, false, null);
    }
}
