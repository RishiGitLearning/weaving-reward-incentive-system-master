package com.technoride.RewardIncentiveSystem.entity;

import com.technoride.RewardIncentiveSystem.Id.RewardIncentiveLockId;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "INCENTIVE_FLAG")
@IdClass(RewardIncentiveLockId.class)
public class IncentiveLock {
    @Id
    @Column(name = "month")
    private int month;

    @Id
    @Column(name = "year")
    private int year;

    @Column(name = "incentive_flag")
    private boolean incentiveFlag;

    @Column(name = "incentive_lock")
    private boolean incentiveLock;
}
