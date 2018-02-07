package com.lc.utils.weiChat;

/**
 * 微信配置
 * Created by fei hai tao on 2017/2/22.
 */
public class WeiChatConfig {


    public  static  String pay_id_app="wxdcb86af17b41faab";
    public  static String GZH_KEY="1aq13sujc3z475115Aaf9aUHqazafx75";//私钥

    public  static  String GZH_MCH_ID="1480125002";//商户号


    /**
     * 微信 appID
     * wx87e3fc0191dcd3d7 测试
     *
     * wxf54d8bb270b14b5e 正式
     *
     */
    public static String appId="wxf54d8bb270b14b5e";//  wxf54d8bb270b14b5e

    /**
     * 微信 appSecret
     * e99c241b36eb055b19e338bb540c595d 测试
     * 81d5e766af4b3d520f23dcce2d8602a8(正式)
     */

    public static String appSecret="81d5e766af4b3d520f23dcce2d8602a8";//  81d5e766af4b3d520f23dcce2d8602a8

    /**
     * 微信认证token
     */

    public static String TOKEN="jielehua";//shCjD019RWe2cG0rYne4hODePA6JbuqA   jielehua

    /**
     * 微信 菜单调转controller 域名
     *
     */

    public static  String controllerUrl="https://jielehua.vcash.cn/api/jlh/";//http://dfzlfei.ngrok.cc/api/jlh/  jlhpredeploy.vcash.cn   jielehua.vcash.cn

    /**
     * 微信 重定向 域名
     */
    public  static String htmlUrl="http://jielehua.vcash.cn/webview";

    /**
     * 决策出额度 提示
     * 4x7AmCwCP6r0kp9IgtpuuZmK6gdC8isfHg8RWhIV2j0 测试
     * 11hpo44t9RgyL6aMswd_GO67BFV2g7d2IYZmkRNDApg 正式
     */
    public static String credit_template_id="11hpo44t9RgyL6aMswd_GO67BFV2g7d2IYZmkRNDApg";//受信 消息模板id 11hpo44t9RgyL6aMswd_GO67BFV2g7d2IYZmkRNDApg(正式)
    /**
     * 额度失效 提示
     * N-t8RQBReQaPgP-UKEzZpk4C5sbg_XtfWTHh_yrHPjU 测试
     * x8bW6vrg73Vs5RlQYCdjX3x0DPEPBZVWJXGLhcLsbMw 正式
     */
    public static String credit_overdue_template_id="x8bW6vrg73Vs5RlQYCdjX3x0DPEPBZVWJXGLhcLsbMw";//额度失效推送 消息模板id x8bW6vrg73Vs5RlQYCdjX3x0DPEPBZVWJXGLhcLsbMw（正式）
    /**
     * 到期还款提醒
     * -nbWhyqpmWfl8ndovaOIFpMaIdPwuVXfqHAB7qLRSms 测试
     * MVF2UQpBll0DqY_uyF3dC854fraSJt6XAc-fOZPaUeE
     */
    public static String bill_come_due_template_id="MVF2UQpBll0DqY_uyF3dC854fraSJt6XAc-fOZPaUeE";//到期还款提醒 消息模板id MVF2UQpBll0DqY_uyF3dC854fraSJt6XAc-fOZPaUeE（正式）
    /**
     * 还款逾期提醒
     * oigzpsKOQq2Qqk5Iq36H6eK2NTlnnnVg4pF7azN9IIw 测试
     * sekruPkIhYdhlhLnJxI6z8osGlbgwj1bAAy3TrzCx3M 正式
     */
    public static String bill_due_template_id="sekruPkIhYdhlhLnJxI6z8osGlbgwj1bAAy3TrzCx3M";//还款逾期提醒 消息模板id  sekruPkIhYdhlhLnJxI6z8osGlbgwj1bAAy3TrzCx3M（正式）
    /**
     * 提现最新状态通知
     * BmZRVGV-Axq0UKor2tAkcU-o-yGITH11phLy5yNSLGk 测试
     * xxPI43ApgmL93_iGT9f93QNGMf_44udP3g6dUaYeEJQ 正式
     */
    public static String draw_template_id="xxPI43ApgmL93_iGT9f93QNGMf_44udP3g6dUaYeEJQ";//提现最新状态通知 消息模板id  xxPI43ApgmL93_iGT9f93QNGMf_44udP3g6dUaYeEJQ（正式）

}
