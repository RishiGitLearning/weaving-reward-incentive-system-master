package com.technoride.RewardIncentiveSystem.model;

public class WeaverReward {
    String empId;
    String empName;
    String month;
    String reward;


    public WeaverReward() {
    }

    public WeaverReward(String empId, String empName, String month, String reward) {
        this.empId = empId;
        this.empName = empName;
        this.month = month;
        this.reward = reward;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }
}
