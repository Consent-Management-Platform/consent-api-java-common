package com.consentframework.shared.api.infrastructure.mappers;

import com.consentframework.consentmanagement.api.JSON;
import com.consentframework.shared.api.infrastructure.entities.StoredConsentImage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.amazon.awssdk.enhanced.dynamodb.AttributeConverter;
import software.amazon.awssdk.enhanced.dynamodb.AttributeValueType;
import software.amazon.awssdk.enhanced.dynamodb.EnhancedType;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

/**
 * Convert between StoredConsentImage objects and DynamoDB JSON string attribute values.
 */
public class DynamoDbConsentConverter implements AttributeConverter<StoredConsentImage> {
    private static final Logger logger = LogManager.getLogger(DynamoDbConsentConverter.class);
    private final ObjectMapper objectMapper;

    public DynamoDbConsentConverter() {
        this.objectMapper = new JSON().getMapper();
    }

    /**
     * Convert from a StoredConsentImage object to an AttributeValue that can be stored in a DynamoDB record.
     */
    @Override
    public AttributeValue transformFrom(final StoredConsentImage consent) {
        if (consent == null) {
            return null;
        }

        final String consentJsonString;
        try {
            consentJsonString = this.objectMapper.writeValueAsString(consent);
        } catch (final JsonProcessingException e) {
            logger.error("Error converting StoredConsentImage to JSON string: {}", e.getMessage(), e);
            throw new RuntimeException("Error converting StoredConsentImage to JSON string", e);
        }
        return AttributeValue.fromS(consentJsonString);
    }

    /**
     * Convert from a DynamoDB JSON string attribute value to a StoredConsentImage object.
     */
    @Override
    public StoredConsentImage transformTo(final AttributeValue input) {
        if (input == null || input.s() == null) {
            return null;
        }
        final String jsonString = input.s();
        try {
            return this.objectMapper.readValue(jsonString, StoredConsentImage.class);
        } catch (final JsonProcessingException e) {
            logger.error("Error converting JSON string to StoredConsentImage: {}", e.getMessage(), e);
            throw new RuntimeException("Error converting JSON string to StoredConsentImage", e);
        }
    }

    /**
     * Return the EnhancedType for StoredConsentImage objects.
     */
    @Override
    public EnhancedType<StoredConsentImage> type() {
        return EnhancedType.of(StoredConsentImage.class);
    }

    /**
     * Return the DynamoDB attribute value type.
     */
    @Override
    public AttributeValueType attributeValueType() {
        return AttributeValueType.S;
    }
}
