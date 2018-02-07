package com.lc.utils.weiChat;

import java.util.List;

/**
 * Created by feihaitao on 2017/11/29.
 */
public class WechatPush {
    private String token;
    private TemplateRequest templateRequest;
    private List<TemplateDataRequest> dataRequests;
    private String remark;

    public TemplateRequest getTemplateRequest() {
        return templateRequest;
    }

    public void setTemplateRequest(TemplateRequest templateRequest) {
        this.templateRequest = templateRequest;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<TemplateDataRequest> getDataRequests() {
        return dataRequests;
    }

    public void setDataRequests(List<TemplateDataRequest> dataRequests) {
        this.dataRequests = dataRequests;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
