package com.consentframework.shared.api.domain.parsers;

import com.consentframework.shared.api.domain.entities.ApiRequest;
import com.consentframework.shared.api.domain.exceptions.BadRequestException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * API query string parameter parser.
 */
public final class ApiQueryStringParameterParser {
    public static final String PARSE_FAILURE_MESSAGE = "Unable to parse %s query parameter from request";

    private static final String WRONG_TYPE_LOG_MESSAGE = "Expected %s query parameter to be a %s but was a %s: %s";

    private static final Logger logger = LogManager.getLogger(ApiQueryStringParameterParser.class);

    private ApiQueryStringParameterParser() {}

    /**
     * Parse string query string parameter value from request.
     *
     * @param request API request
     * @return query string parameter value, or null if not found
     * @throws BadRequestException exception thrown when query string parameter value is not a string
     */
    public static String parseStringQueryStringParameter(final ApiRequest request, final String parameterName)
            throws BadRequestException {
        final Object pathParameterValue = parseQueryStringParameterObject(request, parameterName);
        if (pathParameterValue == null || pathParameterValue instanceof String) {
            return (String) pathParameterValue;
        }
        logger.warn(String.format(WRONG_TYPE_LOG_MESSAGE, parameterName, String.class, pathParameterValue.getClass(),
            pathParameterValue.toString()));
        throw buildBadRequestException(parameterName);
    }

    /**
     * Parse integer query string parameter value from request.
     *
     * @param request API request
     * @return query string parameter value, or null if not found
     * @throws BadRequestException exception thrown when query string parameter value is not an integer
     */
    public static Integer parseIntQueryStringParameter(final ApiRequest request, final String parameterName)
            throws BadRequestException {
        final Object pathParameterValue = parseQueryStringParameterObject(request, parameterName);
        if (pathParameterValue == null || pathParameterValue instanceof Integer) {
            return (Integer) pathParameterValue;
        }
        if (pathParameterValue instanceof String) {
            try {
                return Integer.parseInt((String) pathParameterValue);
            } catch (final NumberFormatException e) {
                logger.warn(String.format(PARSE_FAILURE_MESSAGE, parameterName)
                    + String.format(", value '%s' is not parseable as Integer", pathParameterValue.toString()));
                throw buildBadRequestException(parameterName);
            }
        }
        logger.warn(String.format(WRONG_TYPE_LOG_MESSAGE, parameterName, Integer.class, pathParameterValue.getClass(),
            pathParameterValue.toString()));
        throw buildBadRequestException(parameterName);
    }

    /**
     * Parse query string parameter value from request.
     *
     * @param request API request
     * @return query string parameter value, or null if not found
     */
    private static Object parseQueryStringParameterObject(final ApiRequest request, final String parameterName) {
        if (!hasQueryStringParameter(request, parameterName)) {
            return null;
        }
        return request.queryStringParameters().get(parameterName);
    }

    private static boolean hasQueryStringParameter(final ApiRequest request, final String parameterName) {
        return request != null && request.queryStringParameters() != null
            && request.queryStringParameters().containsKey(parameterName);
    }

    private static BadRequestException buildBadRequestException(final String parameterName) {
        return new BadRequestException(String.format(PARSE_FAILURE_MESSAGE, parameterName));
    }
}
