package com.serendevity;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.vault.authentication.TokenAuthentication;
import org.springframework.vault.client.VaultEndpoint;
import org.springframework.vault.core.VaultOperations;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.core.VaultTransitOperations;

@Converter
public class CardNumberEncryptConverter implements AttributeConverter<String, String> {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Override
    public String convertToDatabaseColumn(String plaintext) {
        return this.getTransitOps().encrypt("cardnum", plaintext);
    }

    @Override
    public String convertToEntityAttribute(String ciphertext) {
        return this.getTransitOps().decrypt("cardnum", ciphertext);
    }

    private VaultTransitOperations getTransitOps() {
        VaultEndpoint vaultEndpoint = new VaultEndpoint();
        vaultEndpoint.setScheme("http");
        VaultOperations operations = new VaultTemplate(vaultEndpoint, new TokenAuthentication("s.zfRe3rLhgbKKCoFGfH2ViiMx"));
        return operations.opsForTransit("transit");
    }
}
