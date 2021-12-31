package com.mcshares.demo.utils;

import com.mcshares.demo.McSharesConstants;
import com.mcshares.demo.ValidationResultEnum;
import com.mcshares.demo.model.ErrorLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@Component
public class ValidationUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidationUtils.class);

    public boolean validateAge(String dob, ErrorLog errorLog) {
        LOGGER.info("START validateAge dob: {}", dob);

        if (StringUtils.isEmpty(dob)) {
            errorLog.setErrorDescription("Age is empty");
            return false;
        }
        boolean isAgeValid;
        LocalDate today = LocalDate.now();
        LocalDate birthday = LocalDate.parse(dob, DateTimeFormatter.ofPattern(McSharesConstants.DATE_FORMAT_DD_MM_YYYY));

        Period period = Period.between(birthday, today);

        isAgeValid = period.getYears() >= 18;

        if (!isAgeValid) {
            errorLog.setErrorDescription(ValidationResultEnum.INVALID_AGE.getMessage());
        }

        LOGGER.info("END validateAge dob: {}, isAgeValid: {}", dob, isAgeValid);
        return isAgeValid;
    }

    public boolean validateNumberOfShares(int numberOfShares, ErrorLog errorLog) {
        LOGGER.info("START validateNumberOfShares, numberOfShares: {}", numberOfShares);
        boolean isNoOfSharesValid = numberOfShares > 0;

        if (!isNoOfSharesValid) {
            errorLog.setErrorDescription(ValidationResultEnum.INVALID_NUMBER_OF_SHARES.getMessage());
        }

        LOGGER.info("END validateNumberOfShares, numberOfShares: {}, isNoOfSharesValid: {}", numberOfShares, isNoOfSharesValid);
        return isNoOfSharesValid;
    }

    public boolean validateSharePrice(Double sharePrice, ErrorLog errorLog) {
        LOGGER.info("START validateSharePrice, sharePrice: {}", sharePrice);
        boolean isSharePriceValid = false;

        int numOfDecimals = sharePrice.toString().split("\\.")[1].length();

        isSharePriceValid = sharePrice > 0 && numOfDecimals <= 2;

        if (!isSharePriceValid) {
            errorLog.setErrorDescription(ValidationResultEnum.INVALID_SHARE_PRICE.getMessage());
        }

        LOGGER.info("END validateSharePrice, sharePrice: {}, isSharePriceValid: {}", sharePrice, isSharePriceValid);
        return isSharePriceValid;
    }
}
