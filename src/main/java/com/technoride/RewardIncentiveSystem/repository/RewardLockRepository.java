package com.technoride.RewardIncentiveSystem.repository;

import com.technoride.RewardIncentiveSystem.entity.RewardLock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface RewardLockRepository extends JpaRepository<RewardLock, Long> {

    @Query(value = "Select r from RewardLock r where r.month=:month and r.year=:year")
    RewardLock findByMonthAndYear(int month, int year);

    @Transactional
    @Modifying
    @Query("update RewardLock r set r.rewardFlag=:rewardFlag, r.rewardLock=:rewardLock where r.month=:month and r.year=:year")
    int updateRewardFlag(boolean rewardFlag,boolean rewardLock,int month,int year);

}
