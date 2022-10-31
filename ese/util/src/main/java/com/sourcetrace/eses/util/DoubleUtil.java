/*
 * DoubleUtil.java
 * Copyright (c) 2014-2015, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */
package com.sourcetrace.eses.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * The Class DoubleUtil.
 * @author $Author: aravind $
 * @version $Rev: 74 $, $Date: 2009-10-08 08:19:33 +0530 (Thu, 08 Oct 2009) $
 */
public class DoubleUtil {

    public static String FORMAT_1 = "0.00";
    private static DecimalFormat df = new DecimalFormat("0.00");
    private static DecimalFormat dfDec = new DecimalFormat("00");
    private static DecimalFormat weightDf = new DecimalFormat("0.000");
    private static DecimalFormat threeDecimalFormat = new DecimalFormat("0.000");
    private static DecimalFormat sixDecimalFormat = new DecimalFormat("0.000000");

    /**
     * Gets the string.
     * @param obj the obj
     * @return the string
     */
    public static String getString(double obj) {

        return df.format(obj);
    }

    /**
     * Gets the string decimal.
     * @param obj the obj
     * @return the string decimal
     */
    public static String getStringDecimal(double obj) {

        return dfDec.format(new Double(df.format(obj)) * 100);
    }

    /**
     * Gets the weight string.
     * @param number the number
     * @return the weight string
     */
    public static String getWeightString(double number) {

        return weightDf.format(number);
    }

    /**
     * Gets the string.
     * @param obj the obj
     * @return the string
     */
    public static String getString(Object obj) {

        return (!ObjectUtil.isEmpty(obj) && obj instanceof Double) ? df.format((Double) obj) : df
                .format(new Double(0));
    }

    /**
     * Double value.
     * @param value the value
     * @return the double
     */
    public static double doubleValue(String value) {

        return StringUtil.isEmpty(value) ? 0 : Double.parseDouble(value);
    }

    /**
     * Double value.
     * @param value the value
     * @return the double
     */
    public static double doubleValue(Object value) {

        return Double.parseDouble(StringUtil.getString(value, "0.0"));
    }

    /**
     * Round.
     * @param d the d
     * @param decimalPlace the decimal place
     * @return the double
     */
    public static double round(double d, int decimalPlace) {

        BigDecimal bd = new BigDecimal(Double.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.doubleValue();
    }

    /**
     * Gets the three decimal point string.
     * @param obj the obj
     * @return the three decimal point string
     */
    public static String getThreeDecimalPointString(double obj) {

        return threeDecimalFormat.format(obj);
    }

    /**
     * Gets the six decimal point string.
     * @param obj
     * @return the six decimal point string
     */
    public static String getSixDecimalPointString(double obj) {

        return sixDecimalFormat.format(obj);
    }

    /**
     * Gets the string.
     * @param value the value
     * @param format the format
     * @return the string
     */
    public static String getString(double value, String format) {

        String string = "";
        if (!StringUtil.isEmpty(format)) {
            try {
                DecimalFormat decimalFormat = new DecimalFormat(format);
                string = decimalFormat.format(value);
            } catch (Exception e) {
                string = getString(value);
            }
        } else {
            string = getString(value);
        }
        return string;
    }
}
