package com.wangzk.www.util;

/**
 * PackageName: com.wangzk.www.util
 * ClassName: RandomUtil
 * Description:
 * date: 2020/1/7 15:18
 *
 * @author bufou-wangzk
 */
public class RandomUtil {
    private static int min = 10000;
    private static int max = 99999;
    private static int seq = 10000;

    public static String getRandom(Boolean numberFlag , int length){
        String retStr = "";
        String strTable = numberFlag ? "1234567890" : "1234567890abcdefghijkmnpqrstuvwxyz";
        int len = strTable.length();
        boolean bDone = true;
        do {
            retStr = "";
            int count = 0;
            for (int i = 0; i < length; i++) {
                double dblR = Math.random() * len;
                int intR = (int) Math.floor(dblR);
                char c = strTable.charAt(intR);
                if (('0' <= c) && (c <= '9')) {
                    count++;
                }
                retStr += strTable.charAt(intR);
            }
            if (count >= 2) {
                bDone = false;
            }
        } while (bDone);

        return retStr;
    }

    public static synchronized long getId(){
        if (seq == max){
            seq=min;
        }
        seq++;
        return System.currentTimeMillis()+seq;
    }


    public static void main(String[] args) {
        System.out.println(getRandom(true , 5));
    }
}
