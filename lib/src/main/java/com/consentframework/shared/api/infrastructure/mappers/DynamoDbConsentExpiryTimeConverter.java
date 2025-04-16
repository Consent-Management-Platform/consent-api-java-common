package com.consentframework.shared.api.infrastructure.mappers;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * Utility functions for converting expiryTime attribute values.
 */
public final class DynamoDbConsentExpiryTimeConverter {
    public static final DateTimeFormatter EXPIRY_HOUR_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:00'Z'");
    public static final DateTimeFormatter EXPIRY_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

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
        return String.format("%s|%s", toExpiryTimeString(expiryTime), id);
    }

    /**
     * Converts an expiryTime to a string in the format "yyyy-MM-dd'T'HH:mm:ss'Z'".
     *
     * Eg. "2010-12-03T11:50:12Z"
     *
     * @param expiryTime The consent expiryTime as an OffsetDateTime.
     * @return The expiryTime as an ISO 8601 string with UTC timezone and second precision.
     */
    public static String toExpiryTimeString(final OffsetDateTime expiryTime) {
        return expiryTime.withOffsetSameInstant(ZoneOffset.UTC)
            .format(EXPIRY_TIME_FORMATTER);
    }

    /**
     * Converts an expiryTime string to an OffsetDateTime with UTC timezone and second precision.
     *
     * @param expiryTime The expiryTime string.
     * @return The expiryTime as an OffsetDateTime.
     */
    public static OffsetDateTime toExpiryTimeOffsetDateTime(final String expiryTime) {
        return OffsetDateTime.parse(expiryTime)
            .withOffsetSameInstant(ZoneOffset.UTC)
            .truncatedTo(ChronoUnit.SECONDS);
    }

    /**
     * Converts an expiryTimeId string to an OffsetDateTime with UTC timezone and second precision.
     *
     * @param expiryTimeId The expiryTimeId string, with format "2011-12-03T10:15:12Z|ServiceId|UserId|ConsentId".
     * @return The expiryTime as an OffsetDateTime.
     */
    public static OffsetDateTime toOffsetDateTimeFromExpiryTimeId(final String expiryTimeId) {
        return OffsetDateTime.parse(expiryTimeId.split("\\|")[0])
            .withOffsetSameInstant(ZoneOffset.UTC)
            .truncatedTo(ChronoUnit.SECONDS);
    }
}
