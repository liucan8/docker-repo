package com.lc.utils.weiChat;

/**
 * Created by dell on 2017/2/23.
 */
public class JSSign {

    private String appId;// 必填，公众号的唯一标识

    private Long timestamp; // 必填，生成签名的时间戳

    private String nonceStr; // 必填，生成签名的随机串

    private String  signature;// 必填，签名，见附录1

    private String jsapi_ticket;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getJsapi_ticket() {
        return jsapi_ticket;
    }

    public void setJsapi_ticket(String jsapi_ticket) {
        this.jsapi_ticket = jsapi_ticket;
    }


}
