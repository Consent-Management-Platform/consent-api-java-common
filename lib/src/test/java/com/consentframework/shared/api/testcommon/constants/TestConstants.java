package com.consentframework.shared.api.testcommon.constants;

import com.consentframework.consentmanagement.api.models.Consent;
import com.consentframework.consentmanagement.api.models.ConsentStatus;
import com.consentframework.shared.api.domain.entities.StoredConsent;

import java.util.Map;

/**
 * Utility class defining common test constants.
 */
public final class TestConstants {
    public static final String TEST_CONSENT_ID = "TestConsentId";
    public static final String TEST_SERVICE_ID = "TestServiceId";
    public static final String TEST_USER_ID = "TestUserId";
    public static final String TEST_PARTITION_KEY = String.format("%s|%s|%s", TEST_SERVICE_ID, TEST_USER_ID, TEST_CONSENT_ID);

    public static final ConsentStatus TEST_CONSENT_STATUS = ConsentStatus.ACTIVE;
    public static final String TEST_CONSENT_TYPE = "TestConsentType";
    public static final Map<String, String> TEST_CONSENT_DATA = Map.of(
        "testKey1", "testValue1",
        "testKey2", "testValue2"
    );

    public static final String LIMIT_PARAM_NAME = "limit";
    public static final String PAGE_TOKEN_PARAM_NAME = "pageToken";
    public static final String USER_ID_PARAM_NAME = "userId";

    public static final Integer TEST_PAGE_LIMIT = 2;
    public static final String TEST_PAGE_TOKEN = "1";
    public static final Map<String, Object> TEST_PAGINATION_QUERY_PARAMETERS = Map.of(
        LIMIT_PARAM_NAME, TEST_PAGE_LIMIT,
        PAGE_TOKEN_PARAM_NAME, TEST_PAGE_TOKEN
    );

    public static final Consent TEST_CONSENT = new Consent()
        .serviceId(TEST_SERVICE_ID)
        .userId(TEST_USER_ID)
        .consentId(TEST_CONSENT_ID)
        .consentVersion(1)
        .status(TEST_CONSENT_STATUS)
        .consentType(TEST_CONSENT_TYPE)
        .consentData(TEST_CONSENT_DATA);

    public static final StoredConsent TEST_STORED_CONSENT = new StoredConsent()
        .id(TEST_PARTITION_KEY)
        .serviceId(TEST_SERVICE_ID)
        .userId(TEST_USER_ID)
        .consentId(TEST_CONSENT_ID)
        .consentVersion(1)
        .consentStatus(TEST_CONSENT_STATUS)
        .consentType(TEST_CONSENT_TYPE)
        .consentData(TEST_CONSENT_DATA);
}
