package com.technoride.RewardIncentiveSystem.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "WI_FELT_WVR_RWD_DATE_LOOM_SHIFT_PAY")
public class WeaverRewardDateLoomShiftWise {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "EMP_NO")
    private String empNo;

    @Column(name = "EMP_NAME")
    private String empName;

    @Column(name = "DESIGNATION")
    private String designation;

    @Column(name = "CATEGORY")
    private String category;

    @Column(name = "REWARD_DATE")
    private LocalDate rewardDate;

    @Column(name = "SHIFT")
    private String shift;

    @Column(name = "LOOMNO")
    private int loom_no;

    @Column(name = "PICK_METER")
    private String pickMeter;

    @Column(name = "REWARD_AMOUNT")
    private Double rewardAmount;

    @Column(name = "REWARD_AMOUNT_SLAB")
    private String rewardAmountSlab;

    @Column(name = "WEFT_DETAIL")
    private String weftDetail;

    @Column(name = "SHIFT_ACHIVE")
    private String shiftAchieved;
    @Transient
    private String dateInString;

    @Transient
    private int srNo;

}
