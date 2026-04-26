package org.example.webaimanagement.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    /**
     * 将 JS 时间字符串 转成 yyyy-MM-dd HH:mm:ss
     * @param jsDateStr 例如：Wed Mar 25 2026 00:00:00 GMT+0800 (中国标准时间)
     * @return 2026-03-25 00:00:00
     */
    public static String convertJsDate(String jsDateStr) {
        try {
            SimpleDateFormat sdfInput = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'Z", java.util.Locale.ENGLISH);
            Date date = sdfInput.parse(jsDateStr);

            SimpleDateFormat sdfOutput = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdfOutput.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}