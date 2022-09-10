package com.technoride.RewardIncentiveSystem.entity;

import javax.persistence.*;

@Entity
@Table(name = "WI_EMP_MONTH_INC_ATTENDANCE")
public class EmpMonthlyIncentiveAttendance {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Transient
    String srNo;
    @Column(name = "pay_cat")
    String payCat;
    @Column(name = "dept")
    String dept;
    @Column(name = "emp_code")
    String empCode;
    @Column(name = "emp_name")
    String empName;
    @Column(name = "category_grade")
    String categoryGrade;
    @Column(name = "designation_grade")
    String designationGrade;
    @Column(name = "eligible_inc_grade")
    String eligibleIncGrade;
    @Column(name = "eligible_rate")
    String eligibleRate;
    @Column(name = "present")
    String present;
    @Column(name = "rokdi")
    String rokdi;
    @Column(name = "total")
    String total;
    @Column(name = "pick_month")
    int pickMonth;
    @Column(name = "pick_year")
    int pickYear;

    @Transient
    String presentSum;
    @Transient
    String totalSum;
    public EmpMonthlyIncentiveAttendance() {
    }

    public EmpMonthlyIncentiveAttendance(String payCat, String dept, String empCode, String empName, String categoryGrade, String designationGrade, String eligibleIncGrade, String eligibleRate, String present, String rokdi, String total, int pickMonth, int pickYear, String presentSum, String totalSum) {
        this.payCat = payCat;
        this.dept = dept;
        this.empCode = empCode;
        this.empName = empName;
        this.categoryGrade = categoryGrade;
        this.designationGrade = designationGrade;
        this.eligibleIncGrade = eligibleIncGrade;
        this.eligibleRate = eligibleRate;
        this.present = present;
        this.rokdi = rokdi;
        this.total = total;
        this.pickMonth = pickMonth;
        this.pickYear = pickYear;
        this.presentSum = presentSum;
        this.totalSum = totalSum;
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

    public String getSrNo() {
        return srNo;
    }

    public void setSrNo(String srNo) {
        this.srNo = srNo;
    }

    public String getPresentSum() {
        return presentSum;
    }

    public void setPresentSum(String presentSum) {
        this.presentSum = presentSum;
    }

    public String getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(String totalSum) {
        this.totalSum = totalSum;
    }
}
