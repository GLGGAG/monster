package com.orange.entity;

import com.orange.entity.base.BaseEntity;

/**
 * @author: GLGGAG
 * @Date: 2017/12/05/下午 12:03
 * @Description:日报
 */
public class Daily extends BaseEntity{

    /**
     * 用户编号
     */
    private Integer userNo;

    /**
     * 当天日报内容
     */
    private String todayContent;

    /**
     * 未完成原因
     */
    private String incompleteCause;

    /**
     * 次日工作计划
     */
    private String morrowPlan;

    /**
     * 风险点和对策
     */
    private String riskPoint;

    /**
     * 备注
     */
    private String remark;

    public Integer getUserNo() {
        return userNo;
    }

    public void setUserNo(Integer userNo) {
        this.userNo = userNo;
    }

    public String getTodayContent() {
        return todayContent;
    }

    public void setTodayContent(String todayContent) {
        this.todayContent = todayContent;
    }

    public String getIncompleteCause() {
        return incompleteCause;
    }

    public void setIncompleteCause(String incompleteCause) {
        this.incompleteCause = incompleteCause;
    }

    public String getMorrowPlan() {
        return morrowPlan;
    }

    public void setMorrowPlan(String morrowPlan) {
        this.morrowPlan = morrowPlan;
    }

    public String getRiskPoint() {
        return riskPoint;
    }

    public void setRiskPoint(String riskPoint) {
        this.riskPoint = riskPoint;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
