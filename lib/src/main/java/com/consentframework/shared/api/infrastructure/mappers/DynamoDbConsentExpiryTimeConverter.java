package com.consentframework.shared.api.infrastructure.mappers;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * Utility functions for converting expiryTime attribute values.
 */
public final class DynamoDbConsentExpiryTimeConverter {
    private static final DateTimeFormatter EXPIRY_HOUR_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:00'Z'");
    private static final DateTimeFormatter EXPIRY_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private DynamoDbConsentExpiryTimeConverter() {}

    /**
     * Converts an OffsetDateTime expiryTime to an expiryHour string in the format "yyyy-MM-dd'T'HH:00'Z'".
     *
     * @param expiryTime The expiryTime to convert.
     * @return The expiryHour string.
     */
    public static String toExpiryHour(final OffsetDateTime expiryTime) {
        return expiryTime.withOffsetSameInstant(ZoneOffset.UTC)
            .truncatedTo(ChronoUnit.HOURS)
            .format(EXPIRY_HOUR_FORMATTER);
    }

    /**
     * Converts an expiryTime and id to an expiryTimeId string in the format "2011-12-03T10:15:12Z|ServiceId|UserId|ConsentId".
     *
     * @param expiryTime The consent expiryTime as an OffsetDateTime.
     * @param id The consent partition key.
     * @return The expiryTimeId string.
     */
    public static String toExpiryTimeId(final OffsetDateTime expiryTime, final String id) {
        final String expiryTimePrefix = expiryTime.withOffsetSameInstant(ZoneOffset.UTC)
            .format(EXPIRY_TIME_FORMATTER);
        return String.format("%s|%s", expiryTimePrefix, id);
    }
}
