package com.lc.utils.other;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lc.dbconfig.redis.template.O2ORedisTemplate;
import com.lc.utils.weiChat.WorkWeixinUtil;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by dicongyan on 2016/11/30.
 *
 */
public class MobileAddressUtil {
    @Autowired
    @Qualifier(value = "o2oRedisTemplate")
    private static O2ORedisTemplate cache;

    private static final String apiUrl = "http://www.ip138.com:8080/search.asp?action=mobile&mobile=%s";
    private static final String apiUrl_juhe =  "http://apis.juhe.cn/mobile/get?key=c4314a0296e1d8fd96eb3cd8ca7f5775&dtype=json&phone=";

    /**
     * @return 返回结果
     */
    private String getMobileAdress(String httpPhoneArg) {
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        String httpUrl = apiUrl + "?phone=" + httpPhoneArg;

        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("GET");
            // 填入apikey到HTTP header
            connection.setRequestProperty("apikey",  "");
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 主接口
     *
     * @return 返回结果
     */
    public static String getMobileCity(String httpPhoneArg) throws IOException {
        String result = "";
        String url = apiUrl;
        url = String.format(url, httpPhoneArg);
        try {
            Document doc = Jsoup.connect(url).get();
            //没有发生异常，判断返回值
            if (null == doc) {
                //返回值为null时，调用备用接口
                try {
                    result = getMobileCityJuhe(httpPhoneArg);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                Elements els = doc.getElementsByClass("tdc2");
                String houseAddress = els.get(1).text();

                if (houseAddress.trim().equals("北京 ") || houseAddress.trim().equals("天津 ")
                        || houseAddress.trim().equals("上海 ") || houseAddress.trim().equals("重庆 ")) {
                    result = houseAddress.replaceAll(" ", "");
                } else {
                    result = houseAddress.replaceAll(" ", "省");
                }
            }
        } catch (Exception e) {
            //发生异常的情况，调用备用接口
            try {
                result = getMobileCityJuhe(httpPhoneArg);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 备用接口（备用接口发生异常时，需要报警处理）
     *
     * @return 返回结果
     */
    private static String getMobileCityJuhe(String httpPhoneArg) throws Exception {
        /*SmsMessage smsMessage = new SmsMessage();
        String sendMassage = "";
        sendMassage = "【借乐花】发生时间 " + DateUtil.format(DateUtil.getCurrentDate(), DateUtil.PATTERN_CLASSICAL) + "\n";

        String result = "";
        String getUrl = apiUrl_juhe + httpPhoneArg;
        try {
            String httpResult = HttpRequestUtil.doGet(getUrl);
            JSONObject jsonObject = JSON.parseObject(httpResult);
            if (Integer.parseInt(jsonObject.getString("error_code")) == 0) {
                JSONObject areaObject = jsonObject.getJSONObject("result");

                String province = areaObject.getString("province");
                String city = areaObject.getString("city");

                if (province.trim().equals("北京") || province.trim().equals("天津") || province.trim().equals("上海") || province.trim().equals("重庆")) {
                    result = province + city;
                } else {
                    result = province + "省" + city + "市";
                }
            } else {
                //接口返回错误码，需要报警处理
                sendMassage += "手机归属地查询主接口发生异常，备用接口返回错误码，请处理！";
                smsMessage.setContent(sendMassage);
                WorkWeixinUtil.send(getWechatToken(), sendMassage);
            }
        } catch (Exception e) {
            e.printStackTrace();
            //接口发生异常，需要报警处理
            sendMassage += "手机归属地查询主接口和备用接口均发生异常，请处理！";
            smsMessage.setContent(sendMassage);
            WorkWeixinUtil.send(getWechatToken(), sendMassage);
        }
        return result;*/
        return "";
    }

    private static String getWechatToken() {
        String access_token = cache.get("work_wechat_access_token");
        if (StringUtils.isEmpty(access_token)) {
            access_token = WorkWeixinUtil.getToken();
            cache.put("work_wechat_access_token", access_token, 1800,false);
        }
        return access_token;
    }

    public static void main(String[] args) {
        try {

            System.out.println("====================== the mobile city is : " + getMobileCity("13804730882"));
        } catch (Exception e) {
            ;
        }

    }
}
