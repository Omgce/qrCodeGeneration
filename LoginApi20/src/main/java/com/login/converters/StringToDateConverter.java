package com.login.converters;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;


@Converter
public class StringToDateConverter implements AttributeConverter<String, Date> {

    @Override
    public Date convertToDatabaseColumn(String s)
    {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormat.parse(s);
        }
        catch (ParseException e) {
            return null;
        }
    }

    @Override
    public String convertToEntityAttribute(Date date) {
        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(currentDate);
    }
    
}
