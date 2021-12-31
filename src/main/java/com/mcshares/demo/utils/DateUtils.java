package com.mcshares.demo.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DateUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(DateUtils.class);

    public static Date fromString(String dateString, String format) throws ParseException {
        if (StringUtils.isEmpty(dateString) || StringUtils.isEmpty(format)) {
            LOGGER.warn("date or format not specified");
            return null;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date formattedDate = simpleDateFormat.parse(dateString);
        LOGGER.info("dateString: {} -> formatted to {}", dateString, formattedDate);
        return simpleDateFormat.parse(dateString);
    }
}
