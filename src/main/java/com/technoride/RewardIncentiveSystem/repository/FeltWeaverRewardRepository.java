package com.technoride.RewardIncentiveSystem.repository;

import com.technoride.RewardIncentiveSystem.entity.FeltWeaverReward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface FeltWeaverRewardRepository extends JpaRepository<FeltWeaverReward,Integer> {
    @Query(value = "Select f from FeltWeaverReward f where f.empNo=:empId and f.rewardMonth=:pickMonth and f.rewardYear=:pickYear")
    FeltWeaverReward findByEmpIdAndMonthYear(String empId,int pickMonth,int pickYear);

    @Transactional
    @Modifying
    @Query(value = "update FeltWeaverReward f set f.rewardAmount=:totalReward where f.empNo=:empId and f.rewardMonth=:pickMonth and f.rewardYear=:pickYear")
    int updateWeaverReward(double totalReward,String empId, int pickMonth, int pickYear);

    @Transactional
    @Modifying
    @Query(value = "delete from FeltWeaverReward f where f.rewardMonth=:month and f.rewardYear=:year")
    int deleteByMonthYear(int month, int year);

    @Query(value = "Select f from FeltWeaverReward f where f.rewardMonth=:pickMonth and f.rewardYear=:pickYear order by f.id")
    List<FeltWeaverReward> findByMonthYear(int pickMonth, int pickYear);

    @Query(value = "Select f from FeltWeaverReward f where f.rewardMonth=:pickMonth and f.rewardYear=:pickYear and f.rewardAmount>0 order by f.empNo")
    List<FeltWeaverReward> findNonZeroRewards(int pickMonth, int pickYear);
}
