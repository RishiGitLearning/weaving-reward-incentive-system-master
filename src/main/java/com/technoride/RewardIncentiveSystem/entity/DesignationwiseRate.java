package com.technoride.RewardIncentiveSystem.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "WI_DESIGNATIONWISE_INCENTIVE_RATE")
public class DesignationwiseRate {
    @Id
    @Column(name = "DESIGNATION")
    private String designation;

    @Column(name = "RATE")
    private double rate;

    @Column(name = "GRADE")
    private double grade;
}
