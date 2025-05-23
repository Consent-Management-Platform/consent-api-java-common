package com.consentframework.shared.api.infrastructure.entities;

import com.consentframework.shared.api.infrastructure.annotations.DynamoDbImmutableStyle;
import com.consentframework.shared.api.infrastructure.mappers.DynamoDbConsentConverter;
import jakarta.annotation.Nullable;
import org.immutables.value.Value.Immutable;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbConvertedBy;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbImmutable;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondaryPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

/**
 * Represents a ConsentHistory DynamoDB table record.
 */
@Immutable
@DynamoDbImmutableStyle
@DynamoDbImmutable(builder = DynamoDbConsentHistory.Builder.class)
public interface DynamoDbConsentHistory {
    public static final String TABLE_NAME = "ConsentHistory";
    public static final String CONSENT_HISTORY_BY_SERVICE_USER_GSI_NAME = "ConsentHistoryByServiceUser";
    public static final String PARTITION_KEY = "id";
    public static final String SORT_KEY = "eventId";

    static Builder builder() {
        return new Builder();
    }

    /**
     * DynamoDbConsentHistory Builder class, intentionally empty.
     */
    class Builder extends ImmutableDynamoDbConsentHistory.Builder {}

    @DynamoDbPartitionKey
    String id();

    @DynamoDbSortKey
    String eventId();

    String eventType();

    String eventTime();

    @DynamoDbSecondaryPartitionKey(indexNames = { CONSENT_HISTORY_BY_SERVICE_USER_GSI_NAME })
    String serviceUserId();

    @Nullable
    @DynamoDbConvertedBy(DynamoDbConsentConverter.class)
    StoredConsentImage oldImage();

    @Nullable
    @DynamoDbConvertedBy(DynamoDbConsentConverter.class)
    StoredConsentImage newImage();
}
