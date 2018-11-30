package com.pencilbox.netknight.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by AbelSu on 16/7/12.
 * 检验IP地址合法性的工具类
 */
public class IPCheckUtils {
    private static final String REXP = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";

    /**
     * 判断是否为合法的IP地址
     */
    public static boolean isIP(String addr) {
        if (addr.length() < 7 || addr.length() > 15) {
            return false;
        }
        Pattern pat = Pattern.compile(REXP);
        Matcher mat = pat.matcher(addr);
        return mat.find();
    }
}
