package com.technoride.RewardIncentiveSystem.model;

public class EmployeeIncentiveModel {
    private String empId;
    private String empName;
    private String incemonth;
    private String inceYear;
    private String incentive;

    public EmployeeIncentiveModel() {
    }

    public EmployeeIncentiveModel(String empId, String empName, String incemonth, String inceYear, String incentive) {
        this.empId = empId;
        this.empName = empName;
        this.incemonth = incemonth;
        this.inceYear = inceYear;
        this.incentive = incentive;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getIncentive() {
        return incentive;
    }

    public void setIncentive(String incentive) {
        this.incentive = incentive;
    }

    public String getIncemonth() {
        return incemonth;
    }

    public void setIncemonth(String incemonth) {
        this.incemonth = incemonth;
    }

    public String getInceYear() {
        return inceYear;
    }

    public void setInceYear(String inceYear) {
        this.inceYear = inceYear;
    }
}
