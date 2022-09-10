package com.technoride.RewardIncentiveSystem.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "WI_EMP_INC_PAY")
public class EmployeeIncentive {
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

    @Transient
    private int srNo;
}
