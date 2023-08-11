package com.onebyte.life4cut.common.vo;


import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter()
public class EmailConverter implements AttributeConverter<Email, String> {

    @Override
    public String convertToDatabaseColumn(Email attribute) {
        return attribute == null ? null : attribute.getValue();
    }

    @Override
    public Email convertToEntityAttribute(String dbData) {
        return dbData == null ? null : new Email(dbData);
    }
}
