package com.consentframework.shared.api.infrastructure.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
* Represents the data stored in DynamoDB ConsentHistory consent images.
*
* Differences from externalized Consent data objects:
* - Stored consents have an "id" field that represents the partition key of "ServiceId|UserId|ConsentId",
    while externalized consents do not have this field.
* - Stored consents use the "consentStatus" attribute name while externalized consents use "status".
*/
@JsonPropertyOrder({
    "id",
    "consentId",
    "consentVersion",
    "userId",
    "serviceId",
    "consentStatus",
    "consentType",
    "consentData",
    "expiryTime",
    "activeId"
})
public class StoredConsentImage {
    public static final String JSON_PROPERTY_ID = "id";
    private String id;
    public static final String JSON_PROPERTY_CONSENT_ID = "consentId";
    private String consentId;
    public static final String JSON_PROPERTY_CONSENT_VERSION = "consentVersion";
    private Integer consentVersion;
    public static final String JSON_PROPERTY_USER_ID = "userId";
    private String userId;
    public static final String JSON_PROPERTY_SERVICE_ID = "serviceId";
    private String serviceId;
    public static final String JSON_PROPERTY_CONSENT_STATUS = "consentStatus";
    private String consentStatus;
    public static final String JSON_PROPERTY_CONSENT_TYPE = "consentType";
    private String consentType;
    public static final String JSON_PROPERTY_CONSENT_DATA = "consentData";
    private Map<String, String> consentData = new HashMap<>();
    public static final String JSON_PROPERTY_EXPIRY_TIME = "expiryTime";
    private OffsetDateTime expiryTime;
    public static final String JSON_PROPERTY_ACTIVE_ID = "activeId";
    private String activeId;

    /**
     * Default constructor required by Jackson, does not initialize any fields.
     */
    public StoredConsentImage() {
    }

    /**
     * Sets the ID and returns the updated StoredConsentImage.
     */
    public StoredConsentImage id(final String id) {
        this.id = id;
        return this;
    }

    /**
     * Returns the ID.
     */
    @Nonnull
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(Include.ALWAYS)
    public String getId() {
        return this.id;
    }

    /**
     * Sets the consent ID.
     */
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(Include.ALWAYS)
    public void setId(final String id) {
        this.id = id;
    }

    /**
     * Sets the consent ID and returns the updated StoredConsentImage.
     */
    public StoredConsentImage consentId(final String consentId) {
        this.consentId = consentId;
        return this;
    }

    /**
     * Returns the consent ID.
     */
    @Nonnull
    @JsonProperty(JSON_PROPERTY_CONSENT_ID)
    @JsonInclude(Include.ALWAYS)
    public String getConsentId() {
        return this.consentId;
    }

    /**
     * Sets the consent ID.
     */
    @JsonProperty(JSON_PROPERTY_CONSENT_ID)
    @JsonInclude(Include.ALWAYS)
    public void setConsentId(final String consentId) {
        this.consentId = consentId;
    }

    /**
     * Sets the consent version and returns the updated StoredConsentImage.
     */
    public StoredConsentImage consentVersion(final Integer consentVersion) {
        this.consentVersion = consentVersion;
        return this;
    }

    /**
     * Returns the consent version.
     */
    @Nonnull
    @JsonProperty(JSON_PROPERTY_CONSENT_VERSION)
    @JsonInclude(Include.ALWAYS)
    public Integer getConsentVersion() {
        return this.consentVersion;
    }

    /**
     * Sets the consent version.
     */
    @JsonProperty(JSON_PROPERTY_CONSENT_VERSION)
    @JsonInclude(Include.ALWAYS)
    public void setConsentVersion(final Integer consentVersion) {
        this.consentVersion = consentVersion;
    }

    /**
     * Sets the userId and returns the updated StoredConsentImage.
     */
    public StoredConsentImage userId(final String userId) {
        this.userId = userId;
        return this;
    }

    /**
     * Returns the userId.
     */
    @Nonnull
    @JsonProperty(JSON_PROPERTY_USER_ID)
    @JsonInclude(Include.ALWAYS)
    public String getUserId() {
        return this.userId;
    }

    /**
     * Sets the userId.
     */
    @JsonProperty(JSON_PROPERTY_USER_ID)
    @JsonInclude(Include.ALWAYS)
    public void setUserId(final String userId) {
        this.userId = userId;
    }

    /**
     * Sets the serviceId and returns the updated StoredConsentImage.
     */
    public StoredConsentImage serviceId(final String serviceId) {
        this.serviceId = serviceId;
        return this;
    }

    /**
     * Returns the serviceId.
     */
    @Nonnull
    @JsonProperty(JSON_PROPERTY_SERVICE_ID)
    @JsonInclude(Include.ALWAYS)
    public String getServiceId() {
        return this.serviceId;
    }

    /**
     * Sets the serviceId.
     */
    @JsonProperty(JSON_PROPERTY_SERVICE_ID)
    @JsonInclude(Include.ALWAYS)
    public void setServiceId(final String serviceId) {
        this.serviceId = serviceId;
    }

    /**
     * Sets the consent status and returns the updated StoredConsentImage.
     */
    public StoredConsentImage consentStatus(final String consentStatus) {
        this.consentStatus = consentStatus;
        return this;
    }

    /**
     * Returns the consent status.
     */
    @Nonnull
    @JsonProperty(JSON_PROPERTY_CONSENT_STATUS)
    @JsonInclude(Include.ALWAYS)
    public String getConsentStatus() {
        return this.consentStatus;
    }

    /**
     * Sets the consent status.
     */
    @JsonProperty(JSON_PROPERTY_CONSENT_STATUS)
    @JsonInclude(Include.ALWAYS)
    public void setConsentStatus(final String consentStatus) {
        this.consentStatus = consentStatus;
    }

    /**
     * Sets the consent type and returns the updated StoredConsentImage.
     */
    public StoredConsentImage consentType(final String consentType) {
        this.consentType = consentType;
        return this;
    }

    /**
     * Returns the consent type.
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CONSENT_TYPE)
    @JsonInclude(Include.USE_DEFAULTS)
    public String getConsentType() {
        return this.consentType;
    }

    /**
     * Sets the consent type.
     */
    @JsonProperty(JSON_PROPERTY_CONSENT_TYPE)
    @JsonInclude(Include.USE_DEFAULTS)
    public void setConsentType(final String consentType) {
        this.consentType = consentType;
    }

    /**
     * Sets the consent data map and returns the updated StoredConsentImage.
     */
    public StoredConsentImage consentData(final Map<String, String> consentData) {
        setConsentData(consentData);
        return this;
    }

    /**
     * Adds a consent data item to the consent data map and returns the updated StoredConsentImage.
     */
    public StoredConsentImage putConsentDataItem(final String key, final String consentDataItem) {
        if (this.consentData == null) {
            this.consentData = new HashMap<>();
        }

        this.consentData.put(key, consentDataItem);
        return this;
    }

    /**
     * Returns the consent data map.
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CONSENT_DATA)
    @JsonInclude(Include.USE_DEFAULTS)
    public Map<String, String> getConsentData() {
        return this.consentData;
    }

    /**
     * Sets the consent data map.
     */
    @JsonProperty(JSON_PROPERTY_CONSENT_DATA)
    @JsonInclude(Include.USE_DEFAULTS)
    public void setConsentData(final Map<String, String> consentData) {
        this.consentData = consentData == null ? null : new HashMap<>(consentData);
    }

    /**
     * Sets the expiry time of the consent and returns the updated StoredConsentImage.
     */
    public StoredConsentImage expiryTime(final OffsetDateTime expiryTime) {
        this.expiryTime = expiryTime;
        return this;
    }

    /**
     * Returns the expiry time of the consent.
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_EXPIRY_TIME)
    @JsonInclude(Include.USE_DEFAULTS)
    public OffsetDateTime getExpiryTime() {
        return this.expiryTime;
    }

    /**
     * Sets the expiry time of the consent.
     */
    @JsonProperty(JSON_PROPERTY_EXPIRY_TIME)
    @JsonInclude(Include.USE_DEFAULTS)
    public void setExpiryTime(final OffsetDateTime expiryTime) {
        this.expiryTime = expiryTime;
    }

    /**
     * Sets activeId and returns the updated StoredConsentImage.
     */
    public StoredConsentImage activeId(final String activeId) {
        this.activeId = activeId;
        return this;
    }

    /**
     * Returns the activeId value.
     *
     * This is an optional field that is set to the same value as id
     * when the consent is active and has a non-null expiry time,
     * and is a sparse partition key for the ActiveConsentsWithExpiryTime GSI.
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ACTIVE_ID)
    @JsonInclude(Include.USE_DEFAULTS)
    public String getActiveId() {
        return this.activeId;
    }

    /**
     * Sets the active ID.
     */
    @JsonProperty(JSON_PROPERTY_ACTIVE_ID)
    @JsonInclude(Include.USE_DEFAULTS)
    public void setActiveId(final String activeId) {
        this.activeId = activeId;
    }

    /**
     * Indicates whether another object is equal to this object.
     */
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            final StoredConsentImage consent = (StoredConsentImage) o;
            return Objects.equals(this.id, consent.id)
                && Objects.equals(this.consentId, consent.consentId)
                && Objects.equals(this.consentVersion, consent.consentVersion)
                && Objects.equals(this.userId, consent.userId)
                && Objects.equals(this.serviceId, consent.serviceId)
                && Objects.equals(this.consentStatus, consent.consentStatus)
                && Objects.equals(this.consentType, consent.consentType)
                && Objects.equals(this.consentData, consent.consentData)
                && Objects.equals(this.expiryTime, consent.expiryTime)
                && Objects.equals(this.activeId, consent.activeId);
        }
        return false;
    }

    /**
     * Returns a hash code value for the object.
     */
    public int hashCode() {
        return Objects.hash(new Object[]{
            this.id,
            this.consentId,
            this.consentVersion,
            this.userId,
            this.serviceId,
            this.consentStatus,
            this.consentType,
            this.consentData,
            this.expiryTime,
            this.activeId
        });
    }

    /**
     * Pretty print the StoredConsentImage object for logging.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class StoredConsentImage {\n");
        sb.append("    serviceId: ").append(this.toIndentedString(this.serviceId)).append("\n");
        sb.append("    userId: ").append(this.toIndentedString(this.userId)).append("\n");
        sb.append("    consentId: ").append(this.toIndentedString(this.consentId)).append("\n");
        sb.append("    consentVersion: ").append(this.toIndentedString(this.consentVersion)).append("\n");
        sb.append("    consentStatus: ").append(this.toIndentedString(this.consentStatus)).append("\n");
        sb.append("    consentType: ").append(this.toIndentedString(this.consentType)).append("\n");
        sb.append("    consentData: ").append(this.toIndentedString(this.consentData)).append("\n");
        sb.append("    expiryTime: ").append(this.toIndentedString(this.expiryTime)).append("\n");
        sb.append("    activeId: ").append(this.toIndentedString(this.activeId)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    private String toIndentedString(final Object o) {
        return o == null ? "null" : o.toString().replace("\n", "\n    ");
    }
}
