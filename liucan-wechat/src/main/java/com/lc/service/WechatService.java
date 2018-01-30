package com.lc.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lc.utils.HttpRequestUtil;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Arrays;

/**
 * Created by Administrator on 2018/1/14 0014.
 */
@Service
public class WechatService {

    public String checkWechat(HttpServletRequest request){
        String token = "12";

        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");

        String params[] = {token,nonce,timestamp};
        Arrays.sort(params);

        String encodeParam = "";
        for(int i=0;i<3;i++) {
            encodeParam += params[i];
        }
        encodeParam = DigestUtils.md5DigestAsHex(encodeParam.getBytes());

        if(encodeParam.equals(signature)) {
            return echostr;
        } else {
            return "1";
        }
    }

    public String getAccessToken(){
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

        try {
            String result = HttpRequestUtil.doGet(url);
            JSONObject json = JSON.parseObject(result);
            return json.getString("access_token");
        } catch (Exception e) {
            e.printStackTrace();
        }
            return "";
    }

    public String createMenu(String paramJsonStr){
        String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

        JSONObject paramJson = new JSONObject();
        JSONArray menusArray = new JSONArray();
        JSONObject menuJson = new JSONObject();

        menuJson.put("type","location_select");
        menuJson.put("name","位置信息");
        try {
            String result = HttpRequestUtil.doPost(url,menuJson.toJSONString());
            JSONObject json = JSON.parseObject(result);
            return json.getString("access_token");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
