package com.lc.utils.other;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by dicongyan on 2016/12/21.
 *
 */
public class IdcardAddrUtil {

    private static Logger logger = LoggerFactory.getLogger(HttpRequestUtil.class);

    private static final String apiUrl = "http://apis.juhe.cn/idcard/index?key=5456a9be238d5c2730d9f0e5e0dee3c2&cardno=";

    public static String getUserAddrByIdcard(String idcard) throws Exception {
        String httpResult = "";
        try {
            String getUrl = apiUrl + idcard;
            httpResult = HttpRequestUtil.doGet(getUrl);
            JSONObject jsonObject = JSON.parseObject(httpResult);

            if(Integer.parseInt(jsonObject.getString("error_code"))==0){
                JSONObject areaObject = jsonObject.getJSONObject("result");

                String address = areaObject.getString("area");
                if(address.contains("地区")) {
                    address = address.replaceAll("地区", "市");
                }
                return address;

            } else {
                logger.info("获取身份证信息异常 --------====cardNO:"+idcard+" "+jsonObject.getString("error_code") +jsonObject.getString("reason"));
                return  null;
            }
        } catch (Exception e) {
            logger.info("获取身份证信息返回结果 --------====cardNO:"+idcard+" "+httpResult);
            e.printStackTrace();
        }
        return "";
    }
}
