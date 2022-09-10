package com.technoride.RewardIncentiveSystem.model;

import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Transient;
@Component
public class EmpIncAttendanceModel {
    String srNo;
    String payCat;
    String dept;
    String empCode;
    String empName;
    String categoryGrade;
    String designationGrade;
    String eligibleIncGrade;
    String eligibleRate;
    String present;
    String rokdi;
    String total;
    int pickMonth;
    int pickYear;
    String presentSum;
    String rokdiSum;
    String totalSum;

    public EmpIncAttendanceModel() {
    }

    public EmpIncAttendanceModel(String empCode, String presentSum, String totalSum) {
        this.empCode = empCode;
        this.presentSum = presentSum;
        this.totalSum = totalSum;
    }

    public EmpIncAttendanceModel(String payCat, String dept, String empCode, String empName, String categoryGrade, String designationGrade, String eligibleIncGrade, String eligibleRate, String presentSum, String rokdiSum, String totalSum) {
        this.payCat = payCat;
        this.dept = dept;
        this.empCode = empCode;
        this.empName = empName;
        this.categoryGrade = categoryGrade;
        this.designationGrade = designationGrade;
        this.eligibleIncGrade = eligibleIncGrade;
        this.eligibleRate = eligibleRate;
        this.presentSum = presentSum;
        this.rokdiSum = rokdiSum;
        this.totalSum = totalSum;
    }

    public String getSrNo() {
        return srNo;
    }

    public void setSrNo(String srNo) {
        this.srNo = srNo;
    }

    public String getPayCat() {
        return payCat;
    }

    public void setPayCat(String payCat) {
        this.payCat = payCat;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getCategoryGrade() {
        return categoryGrade;
    }

    public void setCategoryGrade(String categoryGrade) {
        this.categoryGrade = categoryGrade;
    }

    public String getDesignationGrade() {
        return designationGrade;
    }

    public void setDesignationGrade(String designationGrade) {
        this.designationGrade = designationGrade;
    }

    public String getEligibleIncGrade() {
        return eligibleIncGrade;
    }

    public void setEligibleIncGrade(String eligibleIncGrade) {
        this.eligibleIncGrade = eligibleIncGrade;
    }

    public String getEligibleRate() {
        return eligibleRate;
    }

    public void setEligibleRate(String eligibleRate) {
        this.eligibleRate = eligibleRate;
    }

    public String getPresent() {
        return present;
    }

    public void setPresent(String present) {
        this.present = present;
    }

    public String getRokdi() {
        return rokdi;
    }

    public void setRokdi(String rokdi) {
        this.rokdi = rokdi;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public int getPickMonth() {
        return pickMonth;
    }

    public void setPickMonth(int pickMonth) {
        this.pickMonth = pickMonth;
    }

    public int getPickYear() {
        return pickYear;
    }

    public void setPickYear(int pickYear) {
        this.pickYear = pickYear;
    }

    public String getPresentSum() {
        return presentSum;
    }

    public void setPresentSum(String presentSum) {
        this.presentSum = presentSum;
    }

    public String getRokdiSum() {
        return rokdiSum;
    }

    public void setRokdiSum(String rokdiSum) {
        this.rokdiSum = rokdiSum;
    }

    public String getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(String totalSum) {
        this.totalSum = totalSum;
    }
}
