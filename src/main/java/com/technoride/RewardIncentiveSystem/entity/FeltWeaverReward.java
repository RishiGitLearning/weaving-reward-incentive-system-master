package com.technoride.RewardIncentiveSystem.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "WI_FELT_WVR_RWD_PAY")
public class FeltWeaverReward {
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

    @Column(name = "month")
    private int rewardMonth;

    @Column(name = "year")
    private int rewardYear;

    @Column(name = "REWARD_AMOUNT")
    private double rewardAmount;

    @Column(name = "total_shift")
    private double totalShift;

    @Transient
    private int srNo;
}
