package com.lc.utils.weiChat;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.*;

public class CreateSign {


	public static String signRequest(Map<String,Object> request){
		//生成待签名的参数
		Map<String,Object> param = filterRequest(request);
		//生成待签名的字符串
		String preSignStr = createLinkString(param);
		
		preSignStr += "&key="+WeiChatConfig.GZH_KEY;
		System.out.println("The Pre Sign String is: " + preSignStr);
		//签名
		String sign = DigestUtils.md5Hex(preSignStr).toUpperCase();
		System.out.println("The MD5 sign is " + sign);
		return sign;
	}
	
	public static String js_sdk_sign(Map<String,Object> request){
		//生成待签名的参数
		Map<String,Object> param = filterRequest(request);
		//生成待签名的字符串
		String preSignStr = createLinkString(param);
		
		//preSignStr += "&key="+WxPayConfig.GZH_KEY;
		System.out.println("The Pre Sign String is: " + preSignStr);
		//签名
		String sign = DigestUtils.md5Hex(preSignStr).toUpperCase();
		System.out.println("The SHA1 sign is " + sign);
		return sign;
	}
	
	
	public static void main(String[] args) {
		
	}
	private static Map<String,Object> filterRequest(Map<String,Object> sArray){
		Map<String, Object> result = new HashMap<String, Object>();

        if (sArray == null || sArray.size() <= 0) {
            return result;
        }

        for (String key : sArray.keySet()) {
            Object value = sArray.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign")
                || key.equalsIgnoreCase("sign_type")) {
                continue;
            }
            result.put(key, value);
        }

        return result;
	}
	
    public static String createLinkString(Map<String, Object> params) {

        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        String prestr = "";

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            Object value = params.get(key);

            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }

        return prestr;
    }
	
	
	


}
