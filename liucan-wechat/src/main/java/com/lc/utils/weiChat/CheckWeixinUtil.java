package com.lc.utils.weiChat;

import java.util.Arrays;

/**
 * check微信参数是否正确
 * Created by feihaitao on 2017/2/20.
 */
public class CheckWeixinUtil {


    public static boolean check(String signature,String timestamp,String nonce,String token){
        String[] arr=new String[]{token,timestamp,nonce};
        Arrays.sort(arr);
        StringBuffer buffer=new StringBuffer();
        for(int i=0;i<arr.length;i++){
            buffer.append(arr[i]);
        }
        String aes= DecriptUtil.SHA1(buffer.toString());
        if(aes.equals(signature)){
            return true;
        }
        return false;
    }

}
