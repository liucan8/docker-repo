package com.lc.utils.weiChat;

import com.alibaba.fastjson.JSONObject;

/**
 * 微信消息模板
 * Created by feihaitao on 2017/11/29.
 */
public class TemplateRequest {
    private String touser;
    private String template_id;
    private JSONObject data;

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }
}
