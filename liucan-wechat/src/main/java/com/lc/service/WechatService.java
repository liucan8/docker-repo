package com.lc.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lc.dbconfig.redis.template.O2ORedisTemplate;
import com.lc.utils.DecriptUtil;
import com.lc.utils.HttpRequestUtil;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;

/**
 * Created by Administrator on 2018/1/14 0014.
 */
@Service
public class WechatService {

    @Autowired
    @Qualifier(value = "o2oRedisTemplate")
    private O2ORedisTemplate cache;

    public String checkWechat(HttpServletRequest request){
        String token = "liucan12";

        try {
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
            //encodeParam = DigestUtils.md5DigestAsHex(encodeParam.getBytes());
            encodeParam = DecriptUtil.SHA1(encodeParam);

            if(encodeParam.equals(signature)) {
                return echostr;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "1";
    }

    public String getAccessToken(){
        //String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx30553bde1dde4fed&secret=a081ce36619603bc27692f989ecd5be3";
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxa9f7a14c85f03a17&secret=d3960466d4f96f78d04bf832665ffc91";
        String accessToken = "";
        try {
            accessToken = cache.get("wechat_accessToken");
            if(!StringUtils.isEmpty(accessToken)) {
                return accessToken;
            }

            String result = HttpRequestUtil.doGet(url);
            JSONObject json = JSON.parseObject(result);
            accessToken = json.getString("access_token");
            cache.put("wechat_accessToken",accessToken,3600,false);
        } catch (Exception e) {
            e.printStackTrace();
        }
            return accessToken;
    }

    /**
     * 获取用户基本信息【未认证订阅号无法获取】
     * @param accessToken
     * @param openId
     * @return JSONObject
     */
    public JSONObject getUserBaseInfo(String accessToken,String openId){
        String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="+accessToken+"&openid="+openId+"&lang=zh_CN";
        JSONObject resultJson = null;
        try {
            String result = HttpRequestUtil.doGet(url);
            resultJson = JSON.parseObject(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultJson;
    }

    /**
     * 新增按钮【未认证订阅号无法创建按钮】
     * @param paramJsonStr
     * @return String
     */
    public String createMenu(String paramJsonStr){
        String accessToken = getAccessToken();
        String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+accessToken;

        JSONObject paramJson = new JSONObject();
        JSONArray menusArray = new JSONArray();
        JSONObject menuJson = new JSONObject();

        menuJson.put("type","location_select");
        menuJson.put("name","位置信息");

        menusArray.add(menuJson);

        paramJson.put("button",menusArray);
        try {
            String result = HttpRequestUtil.doPost(url,paramJson.toJSONString());
            JSONObject json = JSON.parseObject(result);
            return json.getString("access_token");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
