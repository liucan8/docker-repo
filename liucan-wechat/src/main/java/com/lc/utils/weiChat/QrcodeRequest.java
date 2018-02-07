package com.lc.utils.weiChat;

/**
 * Created by dell on 2017/2/22.
 */
public class QrcodeRequest {

    private String action_name;
    private QrcodeActionInfo action_info;


    public QrcodeActionInfo getAction_info() {
        return action_info;
    }

    public void setAction_info(QrcodeActionInfo action_info) {
        this.action_info = action_info;
    }

    public String getAction_name() {
        return action_name;
    }

    public void setAction_name(String action_name) {
        this.action_name = action_name;
    }



}
