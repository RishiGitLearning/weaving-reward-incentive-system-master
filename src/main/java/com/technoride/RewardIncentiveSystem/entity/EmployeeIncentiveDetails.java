package com.technoride.RewardIncentiveSystem.entity;

import lombok.*;

import javax.persistence.*;
@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "WI_EMP_INC_DETAIL_PAY")
public class EmployeeIncentiveDetails {

        private static final long serialVersionUID = 1L;

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "id")
        private Long id;

        @Column(name = "EMP_NO")
        private String empId;

        @Column(name = "EMP_NAME")
        private String empName;

        @Column(name = "MONTHLY_BASIC_RATE")
        private double monthlyBasicRate;

        @Column(name = "ATTENDANCE")
        private double attendance;

        @Column(name = "BASIC_RATE_PER_DAY")
        private double basicRatePerDay;

        @Column(name = "ACTUAL_BASIC_AMOUNT")
        private double actualBasicAmount;

        @Column(name = "INCENTIVE_AMOUNT")
        private double incentive;

        @Column(name = "TOTAL_INCENTIVE_AMOUNT")
        private double totalIncentiveAmount;

        @Column(name = "ROUND")
        private int round;

        @Column(name = "INC_MONTH")
        private int incMonth;

        @Column(name = "INC_YEAR")
        private int incYear;

        @Column(name = "TOTAL_DEPT_INC")
        private double totalDeptIncentive;

        @Column(name = "TOTAL_DEPT_BASIC_AMT")
        private double totalDeptBasicAmt;

        @Column(name = "TOTAL_DEPT_INC_PER_PERSON")
        private double totalDeptIncentivePerPerson;

        @Column(name = "PAY_CAT")
        private String payCat;

        @Transient
        private int srNo;

        public static long getSerialVersionUID() {
                return serialVersionUID;
        }

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
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

        public double getMonthlyBasicRate() {
                return monthlyBasicRate;
        }

        public void setMonthlyBasicRate(double monthlyBasicRate) {
                this.monthlyBasicRate = monthlyBasicRate;
        }

        public double getAttendance() {
                return attendance;
        }

        public void setAttendance(double attendance) {
                this.attendance = attendance;
        }

        public double getBasicRatePerDay() {
                return basicRatePerDay;
        }

        public void setBasicRatePerDay(double basicRatePerDay) {
                this.basicRatePerDay = basicRatePerDay;
        }

        public double getActualBasicAmount() {
                return actualBasicAmount;
        }

        public void setActualBasicAmount(double actualBasicAmount) {
                this.actualBasicAmount = actualBasicAmount;
        }

        public double getIncentive() {
                return incentive;
        }

        public void setIncentive(double incentive) {
                this.incentive = incentive;
        }

        public double getTotalIncentiveAmount() {
                return totalIncentiveAmount;
        }

        public void setTotalIncentiveAmount(double totalIncentiveAmount) {
                this.totalIncentiveAmount = totalIncentiveAmount;
        }

        public int getRound() {
                return round;
        }

        public void setRound(int round) {
                this.round = round;
        }

        public int getIncMonth() {
                return incMonth;
        }

        public void setIncMonth(int incMonth) {
                this.incMonth = incMonth;
        }

        public int getIncYear() {
                return incYear;
        }

        public void setIncYear(int incYear) {
                this.incYear = incYear;
        }

        public double getTotalDeptIncentive() {
                return totalDeptIncentive;
        }

        public void setTotalDeptIncentive(double totalDeptIncentive) {
                this.totalDeptIncentive = totalDeptIncentive;
        }

        public double getTotalDeptBasicAmt() {
                return totalDeptBasicAmt;
        }

        public void setTotalDeptBasicAmt(double totalDeptBasicAmt) {
                this.totalDeptBasicAmt = totalDeptBasicAmt;
        }

        public double getTotalDeptIncentivePerPerson() {
                return totalDeptIncentivePerPerson;
        }

        public void setTotalDeptIncentivePerPerson(double totalDeptIncentivePerPerson) {
                this.totalDeptIncentivePerPerson = totalDeptIncentivePerPerson;
        }

        public String getPayCat() {
                return payCat;
        }

        public void setPayCat(String payCat) {
                this.payCat = payCat;
        }

        public int getSrNo() {
                return srNo;
        }

        public void setSrNo(int srNo) {
                this.srNo = srNo;
        }
}
