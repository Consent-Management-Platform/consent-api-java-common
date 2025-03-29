package com.consentframework.shared.api.domain.parsers;

import com.consentframework.shared.api.domain.entities.ApiRequest;
import com.consentframework.shared.api.domain.exceptions.BadRequestException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * API path parameter parser.
 */
public final class ApiPathParameterParser {
    public static final String PARSE_FAILURE_MESSAGE = "Unable to parse %s path parameter from request";

    private static final Logger logger = LogManager.getLogger(ApiPathParameterParser.class);

    private ApiPathParameterParser() {}

    /**
     * Parse path parameter value from request.
     *
     * @param request API request
     * @return path parameter value
     * @throws BadRequestException exception thrown when path parameter not found
     */
    public static String parsePathParameter(final ApiRequest request, final String parameterName) throws BadRequestException {
        if (request == null || request.pathParameters() == null || !request.pathParameters().containsKey(parameterName)) {
            logger.warn(String.format("Missing required %s path parameter from request", parameterName));
            throw new BadRequestException(String.format(PARSE_FAILURE_MESSAGE, parameterName));
        }
        return request.pathParameters().get(parameterName);
    }
}
