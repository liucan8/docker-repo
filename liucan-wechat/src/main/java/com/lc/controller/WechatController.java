package com.lc.controller;

import com.alibaba.fastjson.JSONObject;
import com.lc.service.WechatService;
import com.lc.utils.InputStreamUtil;
import com.lc.utils.weiChat.WeiChatApiUtil;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.jdom2.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Administrator on 2018/1/14 0014.
 */
@RestController
@RequestMapping("/wechat")
public class WechatController {
    @Autowired
    private WechatService wechatService;

    @GetMapping("/checkWechat")
    public String checkWechat(HttpServletRequest request){
        return wechatService.checkWechat(request);
    }

    @PostMapping("/checkWechat")
    public String wechatEvent(HttpServletRequest request){
        //返回信息
        String message = "";
        try {
            Map<String, String> resultMap = InputStreamUtil.inputStream2Map(request.getInputStream());
            String msgType= resultMap.get("MsgType");//获取事件类型
            String openId = resultMap.get("FromUserName");
            String developerWechat = resultMap.get("ToUserName");//开发者微信号

            //取个人信息
            //JSONObject jsonObject = wechatService.getUserBaseInfo(wechatService.getAccessToken(),openId);

            if("event".equals(msgType)) {
                String event=resultMap.get("Event");
                String EventKey=resultMap.get("EventKey");
                //扫码关注
                if(!StringUtils.isEmpty(EventKey)) {

                } else {
                    //非扫码关注

                }

                if("subscribe".equals(event)) {
                    String responseContent = "welcome!";
                    message = WeiChatApiUtil.autoReply(developerWechat,openId,responseContent);
                } else if("SCAN".equals(event)) {

                } else if("unsubscribe".equals(event)) {
                    String responseContent = "bye!bye!";
                    message = WeiChatApiUtil.autoReply(developerWechat,openId,responseContent);
                }
            } else if("text".equals(msgType)){
                String Content=resultMap.get("Content");

                if(Content.equals("test")) {
                    String responseContent = "test response!";
                    message = WeiChatApiUtil.autoReply(developerWechat,openId,responseContent);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }

    @GetMapping("/createMenu")
    public String createMenu(HttpServletRequest request){
        wechatService.createMenu("");
        return "";
    }

    @GetMapping("/getToken")
    public String testWechat(){
        String token = wechatService.getAccessToken();
        return "";
    }

}
