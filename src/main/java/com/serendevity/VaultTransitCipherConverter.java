package com.serendevity;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.core.VaultTransitOperations;

@Converter
public class VaultTransitCipherConverter implements AttributeConverter<String, String> {

    private static final String TRANSIT = "transit";

    @Value("${vault.keyName}")
    private String keyName;

    @Autowired
    private VaultTemplate vaultTemplate;

    @Override
    public String convertToDatabaseColumn(String plaintext) {
        return this.getTransitOps().encrypt(this.keyName, plaintext);
    }

    @Override
    public String convertToEntityAttribute(String ciphertext) {
        return this.getTransitOps().decrypt(this.keyName, ciphertext);
    }

    private VaultTransitOperations getTransitOps() {
        return this.vaultTemplate.opsForTransit(TRANSIT);
    }
}
