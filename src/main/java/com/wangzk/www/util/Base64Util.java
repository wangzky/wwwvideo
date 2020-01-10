package com.wangzk.www.util;

import java.util.Base64;

/**
 * PackageName: com.wangzk.www.util
 * ClassName: Base64Util
 * Description:
 * date: 2020/1/10 17:07
 *
 * @author bufou-wangzk
 */
public class Base64Util {

    public static String decoderBase64 (String str){
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] bytes = decoder.decode(str);
        return new String(bytes);
    }


    private static final String DATA = "";

    public static void main( String[] args ) {
        try {
            // BASE64加密
            Base64.Encoder encoder = Base64.getEncoder();
            byte[] data = encoder.encode(DATA.getBytes());
            System.out.println("BASE64加密：" + new String(data));

            // BASE64解密
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] bytes = decoder.decode(data);
            System.out.println("BASE64解密：" + new String(bytes));

        } catch (Exception e) {
            System.out.println("BASE64加解密异常");
            e.printStackTrace();
        }
    }
}
