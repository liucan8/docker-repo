package com.lc.utils.weiChat;

/**
 * Created by feihaitao on 2017/11/29.
 */
public class TemplateInfo {
    private  long customerId;
    private String creditMoney;
    private String name;
    private String repayMoney;//还款金额
    private String repayDate;//还款时间
    private String overDueMoney;//逾期金额
    private String applyDate;//申请日期
    private int type;// 1:决策成功推送，2 额度失效推送 3 到期还款提醒 4 还款逾期提醒 5 提现最新状态通知

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public String getCreditMoney() {
        return creditMoney;
    }

    public void setCreditMoney(String creditMoney) {
        this.creditMoney = creditMoney;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRepayMoney() {
        return repayMoney;
    }

    public void setRepayMoney(String repayMoney) {
        this.repayMoney = repayMoney;
    }

    public String getRepayDate() {
        return repayDate;
    }

    public void setRepayDate(String repayDate) {
        this.repayDate = repayDate;
    }

    public String getOverDueMoney() {
        return overDueMoney;
    }

    public void setOverDueMoney(String overDueMoney) {
        this.overDueMoney = overDueMoney;
    }

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
