package com.pencilbox.netknight.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by AbelSu on 16/7/12.
 * 检验IP地址合法性的工具类
 */
public class IPCheckUtils {

    /**
     * 判断是否为合法的IP地址
     * @param addr
     * @return
     */
    public boolean isIP(String addr)
    {
        if(addr.length() < 7 || addr.length() > 15 || "".equals(addr))
        {
            return false;
        }
        /**
         * 判断IP格式和范围
         */
        String rexp = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";

        Pattern pat = Pattern.compile(rexp);

        Matcher mat = pat.matcher(addr);

        boolean ipAddress = mat.find();

        return ipAddress;
    }
}
