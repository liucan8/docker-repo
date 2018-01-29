package com.lc.controller;

import com.lc.service.WechatService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
