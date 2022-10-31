package com.sourcetrace.eses.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtil {
    public static final String NAME_PATTERN = "^[\\p{L} .'-]+$";
    public static final String NUMBER_PATTERN = "\\d+";
    public static final String ALPHANUMERIC_PATTERN = "^[a-zA-Z0-9\\-\\s]+$";
    public static final String LATITUDE_PATTERN = "(-?[0-8]?[0-9](\\.\\d*)?)|-?90(\\.[0]*)?";
    public static final String LONGITUDE_PATTERN = "(-?([1]?[0-7][1-9]|[1-9]?[0-9])?(\\.\\d*)?)|-?180(\\.[0]*)?";
    public static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    public static final String ALPHANUMERIC_PATTERN_WITHOUT_SPACE = "^[a-zA-Z0-9]+$";
    public static final String SPECIAL_CHARACTER = "[^@#!$%^*_]*$";
    public static final String ALPHABET_PATTERN = "^[a-zA-Z]+$";
   public static final String MAC_ADDRESS_REGX="^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$";
    public static boolean isPatternMaches(String property, String regexp) {

        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(property);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }
}
