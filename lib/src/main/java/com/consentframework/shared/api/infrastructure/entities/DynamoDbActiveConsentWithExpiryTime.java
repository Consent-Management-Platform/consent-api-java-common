package com.consentframework.shared.api.infrastructure.entities;

import com.consentframework.shared.api.infrastructure.annotations.DynamoDbImmutableStyle;
import org.immutables.value.Value.Immutable;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbImmutable;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondaryPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondarySortKey;

/**
 * DynamoDB data class representing an ActiveConsentsByExpiryHour GSI item.
 */
@Immutable
@DynamoDbImmutableStyle
@DynamoDbImmutable(builder = DynamoDbActiveConsentWithExpiryTime.Builder.class)
public interface DynamoDbActiveConsentWithExpiryTime {
    public static final String TABLE_NAME = "ServiceUserConsent";
    public static final String TABLE_PARTITION_KEY = "id";
    public static final String ACTIVE_CONSENTS_BY_EXPIRY_HOUR_GSI_NAME = "ActiveConsentsByExpiryHour";

    static Builder builder() {
        return new Builder();
    }

    /**
     * Builder class, intentionally empty.
     */
    class Builder extends ImmutableDynamoDbActiveConsentWithExpiryTime.Builder {}

    @DynamoDbPartitionKey
    String id();

    @DynamoDbSecondaryPartitionKey(indexNames = { ACTIVE_CONSENTS_BY_EXPIRY_HOUR_GSI_NAME })
    String expiryHour();

    @DynamoDbSecondarySortKey(indexNames = { ACTIVE_CONSENTS_BY_EXPIRY_HOUR_GSI_NAME })
    String expiryTimeId();

    Integer consentVersion();
}
