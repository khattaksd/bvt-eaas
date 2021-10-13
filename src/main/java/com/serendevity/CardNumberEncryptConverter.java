package com.serendevity;

import static java.util.Base64.getEncoder;

import java.util.Base64;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class CardNumberEncryptConverter implements AttributeConverter<String, String> {

    @Override
    public String convertToDatabaseColumn(String attribute) {
        return getEncoder().encodeToString(attribute.getBytes());
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return new String(Base64.getDecoder().decode(dbData));
    }

}
