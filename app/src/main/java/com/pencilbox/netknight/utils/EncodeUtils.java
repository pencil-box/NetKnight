package com.pencilbox.netknight.utils;

/**
 * Created by pencil-box on 16/6/23.
 */
public class EncodeUtils {

    //十进制数转化为16进制数
//    public static char int2hex(int value){
//
//        if(value<10){
//            return (char) ('0'+value);
//        }else{
//
//            return (char)('a'+value-10);
//
//        }
//
//    }

    /**
     * 字节转换为bit
     * @param b
     * @return
     */
    public static String byte2bits(byte b){

        StringBuffer sb = new StringBuffer();
        int offset = 1<<7;
        int i= 7;
        while(i>=0) {
            sb.append((offset & b)>>i);
            offset>>=1;
            i--;
        }
        return  sb.toString();
    }


    /**
     * 十进制转换为16进制字符串
     * @param value
     * @return
     */
    public static String int2hex(int value){

        return Integer.toHexString(value);

    }

    /**
     * 十六进制转换为Integer
     * @param hexValue
     * @return
     */
    public static int hex2int(String hexValue){

        return Integer.valueOf(hexValue,16);
    }

}
