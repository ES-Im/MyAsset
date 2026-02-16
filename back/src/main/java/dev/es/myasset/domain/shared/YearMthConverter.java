package dev.es.myasset.domain.shared;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.YearMonth;

@Converter
public class YearMthConverter implements AttributeConverter<YearMonth, String> {

    @Override
    public String convertToDatabaseColumn(YearMonth attribute) {
        return attribute != null? attribute.toString() : null;
    }

    @Override
    public YearMonth convertToEntityAttribute(String dbData) {
        return dbData != null? YearMonth.parse(dbData) : null;
    }
}
