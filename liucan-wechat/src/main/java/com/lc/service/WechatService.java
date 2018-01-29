package com.lc.service;

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

}
