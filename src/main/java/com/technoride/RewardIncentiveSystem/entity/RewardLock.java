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
@Table(name = "REWARD_FLAG")
@IdClass(RewardIncentiveLockId.class)
public class RewardLock {
    @Id
    @Column(name = "month")
    private int month;

    @Id
    @Column(name = "year")
    private int year;

    @Column(name = "reward_flag")
    private boolean rewardFlag;

    @Column(name = "reward_lock")
    private boolean rewardLock;
}
