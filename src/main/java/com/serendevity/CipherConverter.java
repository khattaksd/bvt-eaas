package com.serendevity;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.core.VaultTransitOperations;

@Converter
public class CipherConverter implements AttributeConverter<String, String> {

    private static final String TRANSIT = "transit";

    @Value("${vault.keyName}")
    private String keyName;

    @Autowired
    private VaultTemplate vaultTemplate;

    @Override
    public String convertToDatabaseColumn(final String plaintext) {
        return this.getOps().encrypt(this.keyName, plaintext);
    }

    @Override
    public String convertToEntityAttribute(final String ciphertext) {
        return this.getOps().decrypt(this.keyName, ciphertext);
    }

    private VaultTransitOperations getOps() {
        return this.vaultTemplate.opsForTransit(TRANSIT);
    }
}
