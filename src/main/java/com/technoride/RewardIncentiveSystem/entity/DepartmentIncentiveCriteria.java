package com.technoride.RewardIncentiveSystem.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "WI_DEPARTMENT_INCENTIVE_CRITERIA")
public class DepartmentIncentiveCriteria {

    @Id
    @Column(name = "ID")
    private int id;
    @Column(name = "MONTHLY_ACHIEVED_PRODUCTION")
    private double monthlyAchievedProduction;
    @Column(name = "FACTOR_TO_MULTIPLY")
    private double factorToMultiply;

}
