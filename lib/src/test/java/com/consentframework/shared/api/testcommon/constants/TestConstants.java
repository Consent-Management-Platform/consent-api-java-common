package com.consentframework.shared.api.testcommon.constants;

import java.util.Map;

/**
 * Utility class defining common test constants.
 */
public final class TestConstants {
    public static final String LIMIT_PARAM_NAME = "limit";
    public static final String PAGE_TOKEN_PARAM_NAME = "pageToken";
    public static final String USER_ID_PARAM_NAME = "userId";

    public static final Integer TEST_PAGE_LIMIT = 2;
    public static final String TEST_PAGE_TOKEN = "1";
    public static final Map<String, Object> TEST_PAGINATION_QUERY_PARAMETERS = Map.of(
        LIMIT_PARAM_NAME, TEST_PAGE_LIMIT,
        PAGE_TOKEN_PARAM_NAME, TEST_PAGE_TOKEN
    );
}
