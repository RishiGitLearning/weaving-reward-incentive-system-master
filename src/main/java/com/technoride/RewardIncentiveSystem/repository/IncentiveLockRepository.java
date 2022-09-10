package com.technoride.RewardIncentiveSystem.repository;

import com.technoride.RewardIncentiveSystem.entity.IncentiveLock;
import com.technoride.RewardIncentiveSystem.entity.RewardLock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface IncentiveLockRepository extends JpaRepository<IncentiveLock, Integer> {
    @Query(value = "Select i from IncentiveLock i where i.month=:month and i.year=:year")
    IncentiveLock findByMonthAndYear(int month, int year);

    @Transactional
    @Modifying
    @Query("update IncentiveLock i set i.incentiveFlag=:incentiveFlag, i.incentiveLock=:incentiveLock where i.month=:month and i.year=:year")
    int updateIncentiveFlag(boolean incentiveFlag,boolean incentiveLock,int month,int year);
}
