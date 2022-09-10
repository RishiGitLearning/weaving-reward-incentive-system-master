package com.technoride.RewardIncentiveSystem.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "WI_EMP_INC_DAILY_DETAILS")
public class EmployeeIncentiveDailyDetails {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "EMP_NO")
    private String empId;

    @Column(name = "EMP_NAME")
    private String empName;

    @Column(name = "INC_DATE")
    private LocalDate incDate;

    @Column(name = "INC_DESIGNATION")
    private String incDesignation;

    @Column(name = "INC_CATEGORY")
    private String incCategory;

    @Column(name = "INC_SELECTIONGRADE")
    private String incSelectionGrade;

    @Column(name = "INC_DAILY_RATE")
    private double incDailyRate;

    @Column(name = "INC_MONTH")
    private int incMonth;

    @Column(name = "INC_YEAR")
    private int incYear;

    @Column(name = "presentrokdi")
    private double presentrokadi;

    @Column(name = "present")
    private double present;

    @Column(name = "rokdi")
    private double rokdi;

    @Column(name = "PAY_CATG")
    private String payCatg;

    @Transient
    private int srNo;

    @Transient
    private String dateInString;
}
