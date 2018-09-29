package com.johnson.commonlibs.common_utils.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhangwt on 16/3/4.<br/>
 *
 * @author zhangwt
 * @version 1.0
 * @date 16/3/4
 */
public class StrUtils {


    public static String join(final ArrayList<String> array, String separator) {
        StringBuffer result = new StringBuffer();
        if (array != null && array.size() > 0) {
            for (String str : array) {
                result.append(str);
                result.append(separator);
            }
            result.delete(result.length() - 1, result.length());
        }
        return result.toString();
    }


    /**
     * 是否满足password 6-14位
     *
     * @param password
     * @return
     */
    public static boolean isMatchPass(String password) {
        int len = password.trim().length();
        if (len > 14 || len < 6) {
            return false;
        }
        return true;
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean equals(String str1, String str2) {
        return str1 == null ? str2 == null : str1.equals(str2);
    }

    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
//            Pattern p = Pattern.compile("\\s*|\t|\r|\n|");
//            Matcher m = p.matcher(str);
//            dest = m.replaceAll("");
            dest = str.replace("</p>", "");
            dest = dest.replace("<p>", "");
            dest = dest.replace("<br/>", "");
        }
        return dest;
    }


    public static float getStrLength(String str, int charSize, float density) {
        float length = 0;
        char[] chars = str.toCharArray();
        for (char c : chars) {
            if (!isDbcCase(c)) {
                length = length + charSize * density;
            } else {
                length = length + charSize * density / 2;
            }
        }
        return length;
    }

    /**
     * 半角、全角字符判断
     *
     * @param c 字符
     * @return true：半角； false：全角
     */
    public static boolean isDbcCase(char c) {
        // 基本拉丁字母（即键盘上可见的，空格、数字、字母、符号）
        if (c >= 32 && c <= 127) {
            return true;
        }
        // 日文半角片假名和符号
        else if (c >= 65377 && c <= 65439) {
            return true;
        }
        return false;
    }

    public static String getIndexStr(int index) {
        if (index > 9) {
            return String.valueOf(index);
        } else {
            return "0" + index;
        }
    }

    public static String substring(String str, int start) {
        if (str == null) {
            return null;
        }

        // handle negatives, which means last n characters
        if (start < 0) {
            start = str.length() + start; // remember start is negative
        }

        if (start < 0) {
            start = 0;
        }
        if (start > str.length()) {
            return "";
        }

        return str.substring(start);
    }
}
