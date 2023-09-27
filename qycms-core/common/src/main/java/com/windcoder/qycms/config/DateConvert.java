package com.windcoder.qycms.config;


import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;

@Component
public class DateConvert implements Converter<String, Date> {

    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM", "HH:mm:ss", "HH:mm",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};
    @Override
    public Date convert(String value) {
        Date date = null;
        try {
            if(NumberUtils.isCreatable(value)) {
                date = new Date(NumberUtils.createLong(value));
            } else if (StringUtils.isBlank(value)) {
                return null;
            } else {
                date = DateUtils.parseDate(value, parsePatterns);
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return date;
    }

}
