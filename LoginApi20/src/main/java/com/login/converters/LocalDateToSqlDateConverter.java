package com.login.converters;

import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;


@Converter
public class LocalDateToSqlDateConverter implements AttributeConverter<LocalDate, Date> {

    @Override
    public Date convertToDatabaseColumn(LocalDate localDate) {
        return ( localDate == null ? null : Date.valueOf(localDate) );
    }

    @Override
    public LocalDate convertToEntityAttribute(Date date) {
        return ( date == null ? null : date.toLocalDate() );
    }
}
