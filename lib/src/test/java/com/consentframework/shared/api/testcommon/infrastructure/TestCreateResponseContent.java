package com.consentframework.shared.api.testcommon.infrastructure;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.annotation.Nullable;

import java.util.Objects;

/**
 * Example API response content for use in tests.
 */
@JsonPropertyOrder({"status"})
public class TestCreateResponseContent {
    public static final String JSON_PROPERTY_STATUS = "status";
    private String status = null;

    /**
     * Empty constructor required for Jackson.
     */
    public TestCreateResponseContent() {
    }

    /**
     * Set status and return the current instance.
     */
    public TestCreateResponseContent status(final String status) {
        this.status = status;
        return this;
    }

    /**
     * Get status.
     */
    @Nullable
    @JsonProperty("status")
    @JsonInclude(Include.USE_DEFAULTS)
    public String getStatus() {
        return this.status;
    }

    /**
     * Set status.
     */
    @JsonProperty("status")
    @JsonInclude(Include.USE_DEFAULTS)
    public void setStatus(final String status) {
        this.status = status;
    }

    /**
     * Check if equal to a given object.
     */
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            final TestCreateResponseContent listServiceUserConsentResponseContent = (TestCreateResponseContent) o;
            return Objects.equals(this.status, listServiceUserConsentResponseContent.status);
        }
        return false;
    }

    /**
     * Compute hash code.
     */
    public int hashCode() {
        return Objects.hash(new Object[]{this.status});
    }

    /**
     * Convert to string.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class TestCreateResponseContent {\n");
        sb.append("    status: ").append(this.status).append("\n");
        sb.append("}");
        return sb.toString();
    }
}
