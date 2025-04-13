package com.consentframework.shared.api.infrastructure.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.consentframework.consentmanagement.api.models.ConsentStatus;
import com.consentframework.shared.api.testcommon.constants.TestConstants;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.Map;

class StoredConsentImageTest {
    @Test
    void equalsWhenNull() {
        final StoredConsentImage consent = TestConstants.TEST_STORED_CONSENT;
        assertFalse(consent.equals(null));
    }

    @Test
    void equalsWhenSameObject() {
        final StoredConsentImage consent = TestConstants.TEST_STORED_CONSENT;
        assertTrue(consent.equals(consent));
    }

    @Test
    void equalsWhenSameValues() {
        final StoredConsentImage consent = TestConstants.TEST_STORED_CONSENT;
        final StoredConsentImage consentWithSameValues = cloneStoredConsent(consent);
        assertTrue(consent.equals(consentWithSameValues));
    }

    @Test
    void equalsWhenDifferentServiceId() {
        final StoredConsentImage consent = TestConstants.TEST_STORED_CONSENT;

        final StoredConsentImage consentWithDifferentData = cloneStoredConsent(consent)
            .serviceId("differentServiceId");
        assertFalse(consent.equals(consentWithDifferentData));
    }

    @Test
    void equalsWhenDifferentUserId() {
        final StoredConsentImage consent = TestConstants.TEST_STORED_CONSENT;

        final StoredConsentImage consentWithDifferentData = cloneStoredConsent(consent)
            .userId("differentUserId");
        assertFalse(consent.equals(consentWithDifferentData));
    }

    @Test
    void equalsWhenDifferentConsentId() {
        final StoredConsentImage consent = TestConstants.TEST_STORED_CONSENT;

        final StoredConsentImage consentWithDifferentData = cloneStoredConsent(consent)
            .consentId("differentConsentId");
        assertFalse(consent.equals(consentWithDifferentData));
    }

    @Test
    void equalsWhenDifferentId() {
        final StoredConsentImage consent = TestConstants.TEST_STORED_CONSENT;

        final StoredConsentImage consentWithDifferentData = cloneStoredConsent(consent)
            .id("differentId");
        assertFalse(consent.equals(consentWithDifferentData));
    }

    @Test
    void equalsWhenDifferentConsentVersion() {
        final StoredConsentImage consent = TestConstants.TEST_STORED_CONSENT;

        final StoredConsentImage consentWithDifferentData = cloneStoredConsent(consent)
            .consentVersion(5);
        assertFalse(consent.equals(consentWithDifferentData));
    }

    @Test
    void equalsWhenDifferentConsentStatus() {
        final StoredConsentImage consent = TestConstants.TEST_STORED_CONSENT;

        final StoredConsentImage consentWithDifferentData = cloneStoredConsent(consent)
            .consentStatus(ConsentStatus.EXPIRED);
        assertFalse(consent.equals(consentWithDifferentData));
    }

    @Test
    void equalsWhenDifferentConsentType() {
        final StoredConsentImage consent = TestConstants.TEST_STORED_CONSENT;

        final StoredConsentImage consentWithDifferentData = cloneStoredConsent(consent)
            .consentType("differentConsentType");
        assertFalse(consent.equals(consentWithDifferentData));
    }

    @Test
    void equalsWhenDifferentExpiryTime() {
        final StoredConsentImage consent = TestConstants.TEST_STORED_CONSENT;

        final StoredConsentImage consentWithDifferentData = cloneStoredConsent(consent)
            .expiryTime(OffsetDateTime.now());
        assertFalse(consent.equals(consentWithDifferentData));
    }

    @Test
    void equalsWhenDifferentData() {
        final StoredConsentImage consent = TestConstants.TEST_STORED_CONSENT;

        final Map<String, String> differentConsentData = Map.of("key", "value");
        final StoredConsentImage consentWithDifferentData = cloneStoredConsent(consent)
            .consentData(differentConsentData);
        assertFalse(consent.equals(consentWithDifferentData));
    }

    @Test
    void equalsWhenDifferentType() {
        final StoredConsentImage consent = TestConstants.TEST_STORED_CONSENT;
        assertFalse(consent.equals(TestConstants.TEST_CONSENT));
    }

    @Test
    void hashCodeIsSameForEqualValues() {
        final StoredConsentImage consent = TestConstants.TEST_STORED_CONSENT;
        final StoredConsentImage clonedConsent = cloneStoredConsent(consent);
        assertEquals(consent.hashCode(), clonedConsent.hashCode());
    }

    @Test
    void hashCodeIsDifferentForDifferentValues() {
        final StoredConsentImage consent = TestConstants.TEST_STORED_CONSENT;
        final StoredConsentImage clonedConsent = cloneStoredConsent(consent)
            .consentData(Map.of("differentKey", "differentValue"));
        assertNotEquals(consent.hashCode(), clonedConsent.hashCode());
    }

    @Test
    void putConsentDataItemWhenNotExists() {
        final StoredConsentImage consent = new StoredConsentImage()
            .consentData(null);

        final String newKey = "newKey";
        final String newValue = "newValue";
        consent.putConsentDataItem(newKey, newValue);

        final Map<String, String> expectedConsentData = Map.of(newKey, newValue);
        assertEquals(expectedConsentData, consent.getConsentData());
    }

    @Test
    void putConsentDataItemWhenExists() {
        final StoredConsentImage consent = cloneStoredConsent(TestConstants.TEST_STORED_CONSENT);

        final String newKey = "newKey";
        final String newValue = "newValue";
        consent.putConsentDataItem(newKey, newValue);

        final String existingKey = "testKey1";
        final String updatedValue = "testValue1_v2";
        consent.putConsentDataItem(existingKey, updatedValue);

        final Map<String, String> expectedConsentData = Map.of(
            existingKey, updatedValue,
            "testKey2", "testValue2",
            newKey, newValue
        );
        assertEquals(expectedConsentData, consent.getConsentData());
    }

    @Test
    void toStringMatchesExpectedFormat() {
        final StoredConsentImage consent = TestConstants.TEST_STORED_CONSENT;
        final String expectedString = String.format("class StoredConsentImage {\n"
            + "    serviceId: %s\n"
            + "    userId: %s\n"
            + "    consentId: %s\n"
            + "    consentVersion: %d\n"
            + "    consentStatus: %s\n"
            + "    consentType: %s\n"
            + "    consentData: %s\n"
            + "    expiryTime: %s\n"
            + "}",
            consent.getServiceId(),
            consent.getUserId(),
            consent.getConsentId(),
            consent.getConsentVersion(),
            consent.getConsentStatus(),
            consent.getConsentType(),
            consent.getConsentData(),
            consent.getExpiryTime());
        final String consentString = consent.toString();
        assertEquals(expectedString, consentString);
    }

    private StoredConsentImage cloneStoredConsent(final StoredConsentImage originalConsent) {
        return new StoredConsentImage()
            .id(originalConsent.getId())
            .serviceId(originalConsent.getServiceId())
            .userId(originalConsent.getUserId())
            .consentId(originalConsent.getConsentId())
            .consentVersion(originalConsent.getConsentVersion())
            .consentStatus(originalConsent.getConsentStatus())
            .consentType(originalConsent.getConsentType())
            .consentData(originalConsent.getConsentData());
    }
}
