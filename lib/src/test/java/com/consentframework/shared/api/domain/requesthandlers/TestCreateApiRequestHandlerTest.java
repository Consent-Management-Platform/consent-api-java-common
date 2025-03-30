package com.consentframework.shared.api.domain.requesthandlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.consentframework.shared.api.domain.constants.HttpMethod;
import com.consentframework.shared.api.domain.constants.HttpStatusCode;
import com.consentframework.shared.api.domain.entities.ApiRequest;
import com.consentframework.shared.api.testcommon.constants.TestConstants;
import com.consentframework.shared.api.testcommon.infrastructure.TestContentParser;
import com.consentframework.shared.api.testcommon.infrastructure.TestCreateRequestHandler;
import com.consentframework.shared.api.testcommon.infrastructure.TestCreateResponseContent;
import org.junit.jupiter.api.Test;

import java.util.Map;

class TestCreateApiRequestHandlerTest extends ApiRequestHandlerTest {
    private static final String TEST_USER_ID = "someUser";
    private static final String TEST_STATUS = "someStatus";
    private static final Map<String, String> VALID_PATH_PARAMS = Map.of(TestConstants.USER_ID_PARAM_NAME, TEST_USER_ID);
    private static final String VALID_REQUEST_BODY = "{\"status\": \"" + TEST_STATUS + "\"}";

    private ApiRequestHandler handler = new TestCreateRequestHandler();

    @Test
    protected void testHandleNullRequest() {
        final Map<String, Object> response = handler.handleRequest(null);
        assertMissingPathParametersResponse(response);
    }

    @Test
    protected void testHandleRequestMissingPathParameters() {
        final ApiRequest request = buildApiRequest(Map.of(), null, null);
        final Map<String, Object> response = handler.handleRequest(request);
        assertMissingPathParametersResponse(response);
    }

    @Test
    void testHandleRequestMissingBody() {
        final ApiRequest request = buildApiRequest(VALID_PATH_PARAMS, null, null);
        final Map<String, Object> response = handler.handleRequest(request);
        assertExceptionResponse(HttpStatusCode.BAD_REQUEST, "Missing create request body", response);
    }

    @Test
    void testHandleRequestWithInvalidBody() {
        final ApiRequest request = buildApiRequest(VALID_PATH_PARAMS, null, "Invalid body");
        final Map<String, Object> response = handler.handleRequest(request);
        assertNotNull(response);
        assertStatusCodeEquals(HttpStatusCode.BAD_REQUEST, response);
    }

    @Test
    void testHandleValidRequest() throws Exception {
        final ApiRequest request = buildApiRequest(VALID_PATH_PARAMS, null, VALID_REQUEST_BODY);

        final Map<String, Object> response = handler.handleRequest(request);
        assertSuccessResponse(response);

        final Object responseBody = getResponseBody(response);
        assertTrue(responseBody instanceof String);

        final TestCreateResponseContent parsedResponseBody = TestContentParser.parseResponseBody((String) responseBody);
        assertNotNull(parsedResponseBody);
        assertEquals(TEST_STATUS, parsedResponseBody.getStatus());
    }

    @Test
    void testHandleRequestWhenConflictError() {
        final Map<String, Object> queryStringParameters = Map.of(TestCreateRequestHandler.MOCK_ERROR_CODE_QUERY_PARAM, 409);
        final ApiRequest request = buildApiRequest(VALID_PATH_PARAMS, queryStringParameters, VALID_REQUEST_BODY);

        final Map<String, Object> response = handler.handleRequest(request);
        assertExceptionResponse(HttpStatusCode.CONFLICT, "Conflict", response);
    }

    @Test
    void testHandleRequestWhenNotFoundError() {
        final Map<String, Object> queryStringParameters = Map.of(TestCreateRequestHandler.MOCK_ERROR_CODE_QUERY_PARAM, 404);
        final ApiRequest request = buildApiRequest(VALID_PATH_PARAMS, queryStringParameters, VALID_REQUEST_BODY);

        final Map<String, Object> response = handler.handleRequest(request);
        assertExceptionResponse(HttpStatusCode.NOT_FOUND, "Resource not found", response);
    }

    private ApiRequest buildApiRequest(final Map<String, String> pathParameters, final Map<String, Object> queryStringParameters,
            final String requestBody) {
        return new ApiRequest(HttpMethod.POST.name(), "/v1/users/{userId}/consents", "/v1/users/someUser/consents",
            pathParameters, queryStringParameters, null, false, requestBody);
    }

    private void assertMissingPathParametersResponse(final Map<String, Object> response) {
        assertExceptionResponse(
            HttpStatusCode.BAD_REQUEST,
            "Missing required path parameters, expected " + TestConstants.USER_ID_PARAM_NAME,
            response
        );
    }
}
