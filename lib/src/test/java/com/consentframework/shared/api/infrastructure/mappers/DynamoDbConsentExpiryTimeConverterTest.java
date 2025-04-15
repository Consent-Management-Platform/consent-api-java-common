package com.consentframework.shared.api.infrastructure.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.consentframework.shared.api.testcommon.constants.TestConstants;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

class DynamoDbConsentExpiryTimeConverterTest {
    private static final OffsetDateTime TEST_EXPIRY_TIME_PST = OffsetDateTime.of(2011, 10, 31, 20, 51, 12, 20, ZoneOffset.of("-08:00"));
    private static final OffsetDateTime TEST_EXPIRY_TIME_UTC = OffsetDateTime.of(2011, 10, 31, 20, 51, 12, 20, ZoneOffset.UTC);

    @Test
    void toExpiryHour_withUtcTimeInput() {
        final String expiryHour = DynamoDbConsentExpiryTimeConverter.toExpiryHour(TEST_EXPIRY_TIME_UTC);
        assertEquals("2011-10-31T20:00Z", expiryHour);
    }

    @Test
    void toExpiryHour_withPacificTimeInput() {
        final String expiryHour = DynamoDbConsentExpiryTimeConverter.toExpiryHour(TEST_EXPIRY_TIME_PST);
        assertEquals("2011-11-01T04:00Z", expiryHour);
    }

    @Test
    void toExpiryTimeId_withUtcTimeInput() {
        final String expiryTimeId = DynamoDbConsentExpiryTimeConverter.toExpiryTimeId(
            TEST_EXPIRY_TIME_UTC, TestConstants.TEST_PARTITION_KEY);
        assertEquals("2011-10-31T20:51:12Z|" + TestConstants.TEST_PARTITION_KEY, expiryTimeId);
    }

    @Test
    void toExpiryTimeId_withPacificTimeInput() {
        final String expiryTimeId = DynamoDbConsentExpiryTimeConverter.toExpiryTimeId(
            TEST_EXPIRY_TIME_PST, TestConstants.TEST_PARTITION_KEY);
        assertEquals("2011-11-01T04:51:12Z|" + TestConstants.TEST_PARTITION_KEY, expiryTimeId);
    }
}
