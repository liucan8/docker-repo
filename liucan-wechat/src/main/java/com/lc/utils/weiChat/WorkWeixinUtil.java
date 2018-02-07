package com.lc.utils.weiChat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lc.utils.other.HttpRequestUtil;
import com.lc.utils.other.JsonUtil;

/**
 * 企业号发消息
 * Created by feihaitao on 2017/6/22.
 */
public class WorkWeixinUtil {
    private static String corpid="ww95552cafb2cfc914";
    private static String secrect="mEjoAH7IxML4sbsinSTuM5azloRkeBbpcruRHZNrEbs";
    private static  int agentid= 1000003;


    private static String corpid_test="ww95552cafb2cfc914";
    private static String secrect_test="vKVySqXZcR5upug35xy9jgEhkRCAAf4_TkzqiibBVec";
    private static  int agentid_test= 1000004;

    public static String getToken(){
        try{
            String url="https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid="+corpid+"&corpsecret="+secrect;
            String request= HttpRequestUtil.doGet(url);
            JSONObject jsonObject= JSON.parseObject(request);
            String access_token=(String) jsonObject.get("access_token");
            System.out.println(access_token);
            return  access_token;
        }catch (Exception e){
            e.printStackTrace();
        }
        return  "";
    }

    /**
     * 企业号发送消息
     * @param token
     * @return
     */
    public static String send(String token,String content){
        String url="https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token="+token;
        try{
            SendMessage sendMessage=new SendMessage();
            sendMessage.setMsgtype("text");
            sendMessage.setAgentid(agentid);
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("content",content);
            sendMessage.setText(jsonObject);
            String param= JsonUtil.beanToJson(sendMessage);
            String result= HttpRequestUtil.doPost(url,param);
            System.out.println(result);
        }catch (Exception e){
            e.printStackTrace();
        }
        return  "";
    }

    public static void main(String[] args) {
        WorkWeixinUtil.send(WorkWeixinUtil.getToken(),"我在测试") ;
    }
}
