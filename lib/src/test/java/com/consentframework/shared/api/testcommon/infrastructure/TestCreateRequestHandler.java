package com.consentframework.shared.api.testcommon.infrastructure;

import com.consentframework.shared.api.domain.entities.ApiRequest;
import com.consentframework.shared.api.domain.exceptions.BadRequestException;
import com.consentframework.shared.api.domain.exceptions.ConflictingResourceException;
import com.consentframework.shared.api.domain.exceptions.ResourceNotFoundException;
import com.consentframework.shared.api.domain.parsers.ApiPathParameterParser;
import com.consentframework.shared.api.domain.parsers.ApiQueryStringParameterParser;
import com.consentframework.shared.api.domain.requesthandlers.ApiRequestHandler;
import com.consentframework.shared.api.testcommon.constants.TestConstants;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

/**
 * Test implementation of ApiRequestHandler.
 */
public class TestCreateRequestHandler extends ApiRequestHandler {
    private static final Logger logger = LogManager.getLogger(TestCreateRequestHandler.class);
    private static final List<String> REQUIRED_PATH_PARAM_NAMES = List.of(TestConstants.USER_ID_PARAM_NAME);

    public static final String MOCK_ERROR_CODE_QUERY_PARAM = "mockErrorCode";

    /**
     * Construct API handler.
     */
    public TestCreateRequestHandler() {
        super(REQUIRED_PATH_PARAM_NAMES);
    }

    /**
     * Handle API requests.
     *
     * @param request API request
     * @return API response
     */
    @Override
    public Map<String, Object> handleRequest(final ApiRequest request) {
        final String userId;
        try {
            userId = ApiPathParameterParser.parsePathParameter(request, TestConstants.USER_ID_PARAM_NAME);
        } catch (final BadRequestException badRequestException) {
            return logAndBuildMissingPathParamResponse(badRequestException);
        }
        logger.info("Handling Create Consent request for userId: {}", userId);

        if (request.body() == null) {
            return logAndBuildErrorResponse(new BadRequestException("Missing create request body"));
        }

        try {
            final Integer errorCode = ApiQueryStringParameterParser.parseIntQueryStringParameter(request, MOCK_ERROR_CODE_QUERY_PARAM);
            if (errorCode != null) {
                if (errorCode == 404) {
                    throw new ResourceNotFoundException("Resource not found");
                }
                if (errorCode == 409) {
                    throw new ConflictingResourceException("Conflict");
                }
            }
        } catch (final BadRequestException | ConflictingResourceException | ResourceNotFoundException exception) {
            return logAndBuildErrorResponse(exception);
        }

        final TestCreateRequestContent requestContent;
        try {
            requestContent = TestContentParser.parseRequestBody(request.body());
        } catch (final JsonProcessingException exception) {
            return logAndBuildJsonProcessingErrorResponse(exception);
        }

        final TestCreateResponseContent responseContent = new TestCreateResponseContent()
            .status(requestContent.getStatus());

        final String responseBodyString;
        try {
            responseBodyString = toJsonString(TestContentParser.OBJECT_MAPPER, responseContent);
        } catch (final JsonProcessingException exception) {
            return logAndBuildJsonProcessingErrorResponse(exception);
        }

        return buildApiSuccessResponse(responseBodyString);
    }
}
