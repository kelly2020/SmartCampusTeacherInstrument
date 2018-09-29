package com.johnson.commonlibs.common_utils.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhangwt on 16/3/4.<br/>
 * 正则表达式
 *
 * @author zhangwt
 * @version 1.0
 * @date 16/3/4
 */
public class RegxUtils {
    private static final int PWD_MAX_LEN = 16;
    private static final int PWD_MIN_LEN = 6;
    private static final int USER_NAME_MAX_LEN = 40;
    private static final int USER_NAME_MIN_LEN = 4;
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
//    private static final String PHONE_PATTERN = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
private static final String PHONE_PATTERN="^((16[0-9])|(13[0-9])|(14[5-8])|(15([0-3]|[5-9]))|(17([0-9]))|(18[0-9])|(19[8-9]))\\d{8}$";;
    private static final String PWD_PATTERN = "[`~!#$%^&*()+=|{}':;',\\[\\].<>/?~！#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
    private static final String NUM_LETTER_PATTERN = "^[A-Za-z0-9]+$";

    /**
     * 是否为手机号码
     *
     * @param tel
     * @return
     */
    public static boolean validatePhone(String tel) {
        Pattern p = Pattern.compile(PHONE_PATTERN);
        Matcher matcher = p.matcher(tel);
        return matcher.matches();
    }

    public static boolean validateEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * 数字和字母
     * @param email
     * @return
     */
    public static boolean validateNumLetter(String email) {
        Pattern pattern = Pattern.compile(NUM_LETTER_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * @prama: str 除了@ 以外的特殊字符
     */

    public static boolean compileExChar(String str) {
        Pattern pattern = Pattern.compile(PWD_PATTERN);
        Matcher matcher = pattern.matcher(str);
        return matcher.find();
    }

    public static boolean validatePwd(String pwd) {
        int cou = pwd.length();
        if (cou < PWD_MIN_LEN || cou >= PWD_MAX_LEN) {
            return true;
        }
        return false;
    }

    public static boolean validateName(String name) {
        int cou = name.length();
        if (cou < USER_NAME_MIN_LEN || cou >= USER_NAME_MAX_LEN) {
            return true;
        }
        return false;
    }

    public static boolean validateActivateCode(String name) {
        int cou = name.length();
        if (cou > PWD_MIN_LEN) {
            return true;
        }
        return false;
    }


}
