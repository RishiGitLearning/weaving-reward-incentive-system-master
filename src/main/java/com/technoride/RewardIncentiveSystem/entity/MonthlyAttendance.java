package com.technoride.RewardIncentiveSystem.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "WI_MONTHLY_ATTENDANCE")
@IdClass(MonthlyAttendance.class)
public class MonthlyAttendance implements Serializable {
    @Id
    @Column(name = "DOC_NO")
    private String docNo;
    @Id
    @Column(name = "DOC_DATE")
    private Date docDate;
    @Id
    @Column(name = "PRODUCT_CATEGORY")
    private String productCategory;
    @Id
    @Column(name = "MAS_EMPID")
    private String masEmpid;

    @Column(name = "MAS_EMPNAME")
    private String masEmpname;

    @Column(name = "MAS_EMPDEPT")
    private String masEmpdept;
    @Id
    @Column(name = "MAS_MM")
    private Integer masMM;
    @Id
    @Column(name = "MAS_YYYY")
    private Integer masYYYY;

    @Column(name = "TOTAL_MONTH_DAYS")
    private Double totalMonthDays;

    @Column(name = "PAID_DAYS")
    private Double paidDays;

    @Column(name = "PRESENT_DAYS")
    private Double presentDays;

    @Column(name = "ROKDI_DAYS")
    private Double rokdiDays;

    @Column(name = "COFF_DAYS")
    private Double coffDays;

    @Column(name = "DESIGNATION_DESC")
    private String designationDesc;

    @Column(name = "DESIGNATION_GRADE")
    private double designationGrade;

    @Column(name = "INCENTIVE_FLAG")
    private boolean incentiveFlag=false;

    @Column(name = "PAY_CATG")
    private String payCatg;

}
