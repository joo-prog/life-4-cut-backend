package com.onebyte.life4cut.common.vo;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class UrlPathConverter implements AttributeConverter<UrlPath, String> {
    @Override
    public String convertToDatabaseColumn(UrlPath attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getValue();
    }

    @Override
    public UrlPath convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }

        return UrlPath.of(dbData);
    }
}
