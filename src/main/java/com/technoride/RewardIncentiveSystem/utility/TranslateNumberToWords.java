package com.technoride.RewardIncentiveSystem.utility;

import com.ibm.icu.text.RuleBasedNumberFormat;
import com.ibm.icu.util.CurrencyAmount;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

@Component
public class TranslateNumberToWords {
    public static String translate(String ctryCd, String lang, String reqStr) {
        StringBuffer result = new StringBuffer();

        Locale locale = new Locale(lang, ctryCd);
        Currency crncy = Currency.getInstance(locale);

        RuleBasedNumberFormat rule = new RuleBasedNumberFormat(locale, RuleBasedNumberFormat.SPELLOUT);

        int i = 0;

        CurrencyAmount crncyAmt = new CurrencyAmount(new BigDecimal(reqStr), crncy);
        if (i++ == 0) {
            result.append(rule.format(crncyAmt));
        }

        return result.toString();
    }
}
