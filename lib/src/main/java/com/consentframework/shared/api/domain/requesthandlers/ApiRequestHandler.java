package com.consentframework.shared.api.domain.requesthandlers;

import com.consentframework.shared.api.domain.constants.ApiResponseParameterName;
import com.consentframework.shared.api.domain.constants.HttpStatusCode;
import com.consentframework.shared.api.domain.entities.ApiRequest;
import com.consentframework.shared.api.domain.exceptions.BadRequestException;
import com.consentframework.shared.api.domain.exceptions.ConflictingResourceException;
import com.consentframework.shared.api.domain.exceptions.ResourceNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Abstract class for an API request handler.
 */
public abstract class ApiRequestHandler {
    public static final String ERROR_RESPONSE_BODY = "{\"message\":\"%s\"}";
    public static final String MISSING_PATH_PARAMETERS_MESSAGE = "Missing required path parameters, expected %s";
    public static final String REQUEST_PARSE_FAILURE_MESSAGE = "Unable to parse request";

    private static final Logger logger = LogManager.getLogger(ApiRequestHandler.class);

    public final List<String> requiredPathParameters;

    /**
     * Construct ApiRequestHandler with properties shared across subclasses.
     *
     * @param requiredPathParameters required path parameters
     */
    public ApiRequestHandler(final List<String> requiredPathParameters) {
        this.requiredPathParameters = requiredPathParameters;
    }

    /**
     * Handle API request.
     *
     * @param request API request object
     * @return API response
     */
    protected abstract Map<String, Object> handleRequest(final ApiRequest request);

    /**
     * Convert response content to a JSON string as required for API Gateway to interpret the response body.
     *
     * @param responseContent response content object
     * @return response content as a JSON string
     * @throws JsonProcessingException exception thrown if unable to convert object into a JSON string
     */
    protected String toJsonString(final ObjectMapper mapper, final Object responseContent) throws JsonProcessingException {
        return mapper.writeValueAsString(responseContent);
    }

    /**
     * Log missing path parameter exception and return API error response.
     *
     * @param exception original bad request exception
     * @return 400 Bad Request API error response including list of required parameters
     */
    protected Map<String, Object> logAndBuildMissingPathParamResponse(final BadRequestException exception) {
        final String errorMessage = String.format(MISSING_PATH_PARAMETERS_MESSAGE,
            String.join(", ", requiredPathParameters));

        logger.warn(errorMessage, exception);
        return buildApiErrorResponse(new BadRequestException(errorMessage, exception));
    }

    /**
     * Log JSON processing exception and return API error response.
     *
     * @param jsonProcessingException exception thrown while parsing request body
     * @return 400 Bad Request API error response
     */
    protected Map<String, Object> logAndBuildJsonProcessingErrorResponse(final JsonProcessingException jsonProcessingException) {
        logger.warn("Received unexpected JsonProcessingException parsing request body", jsonProcessingException);
        return buildApiErrorResponse(new BadRequestException(REQUEST_PARSE_FAILURE_MESSAGE));
    }

    /**
     * Log exception message and return API error response.
     *
     * @param exception original exception thrown
     * @return API error response
     */
    protected Map<String, Object> logAndBuildErrorResponse(final Exception exception) {
        logger.warn(exception.getMessage());
        return buildApiErrorResponse(exception);
    }

    /**
     * Build API success response.
     *
     * @param responseBody API response body
     * @return 200 Success API response
     */
    protected Map<String, Object> buildApiSuccessResponse(final String responseBody) {
        return buildApiResponse(HttpStatusCode.SUCCESS, responseBody);
    }

    /**
     * Build API error response with appropriate status code and message body.
     *
     * @param exception original thrown exception
     * @return API error response
     */
    private Map<String, Object> buildApiErrorResponse(final Exception exception) {
        final HttpStatusCode statusCode = determineStatusCode(exception);
        return buildApiResponse(statusCode, String.format(ERROR_RESPONSE_BODY, exception.getMessage()));
    }

    /**
     * Build API response with given status code and response body.
     *
     * @param statusCode HTTP status code to return
     * @param body response body
     * @return map of response parameter names and values
     */
    private Map<String, Object> buildApiResponse(final HttpStatusCode statusCode, final String body) {
        final Map<String, Object> apiResponse = new HashMap<String, Object>();
        apiResponse.put(ApiResponseParameterName.STATUS_CODE.getValue(), statusCode.getValue());
        if (body != null) {
            apiResponse.put(ApiResponseParameterName.BODY.getValue(), body);
        }
        return apiResponse;
    }

    private HttpStatusCode determineStatusCode(final Exception exception) {
        if (exception instanceof BadRequestException) {
            return HttpStatusCode.BAD_REQUEST;
        }
        if (exception instanceof ConflictingResourceException) {
            return HttpStatusCode.CONFLICT;
        }
        if (exception instanceof ResourceNotFoundException) {
            return HttpStatusCode.NOT_FOUND;
        }
        return HttpStatusCode.INTERNAL_SERVER_ERROR;
    }
}
