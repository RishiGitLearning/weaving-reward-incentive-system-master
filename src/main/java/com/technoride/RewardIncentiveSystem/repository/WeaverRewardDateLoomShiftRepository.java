package com.technoride.RewardIncentiveSystem.repository;

import com.technoride.RewardIncentiveSystem.entity.WeaverRewardDateLoomShiftWise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public interface WeaverRewardDateLoomShiftRepository extends JpaRepository<WeaverRewardDateLoomShiftWise, Long> {

    @Query(value = "select distinct(empNo) from WeaverRewardDateLoomShiftWise w where w.rewardDate>=:startdate and w.rewardDate<=:enddate")
    List<String> findEmpList(LocalDate startdate, LocalDate enddate);

    @Query(value = "select w from WeaverRewardDateLoomShiftWise w where w.rewardDate>=:startdate and w.rewardDate<=:enddate and w.empNo=:empNo order by w.rewardDate")
    List<WeaverRewardDateLoomShiftWise> findWithCriteria(LocalDate startdate, LocalDate enddate, String empNo);

    @Query(value = "select w from WeaverRewardDateLoomShiftWise w where w.empNo=:empNo and w.rewardDate>=:startdate and w.rewardDate<=:enddate order by w.rewardDate")
    List<WeaverRewardDateLoomShiftWise> findByEmpNo(String empNo, LocalDate startdate, LocalDate enddate);

    @Query(value = "select w from WeaverRewardDateLoomShiftWise w where w.loom_no=:loom and w.rewardDate>=:startdate and w.rewardDate<=:enddate order by w.rewardDate")
    ArrayList<WeaverRewardDateLoomShiftWise> findByLoom(int loom, LocalDate startdate, LocalDate enddate);

    @Query(value = "select w from WeaverRewardDateLoomShiftWise w where w.empNo=:empNo and  month(w.rewardDate)=:pickMonth and year(w.rewardDate)=:pickYear")
    ArrayList<WeaverRewardDateLoomShiftWise> findByEmpNoAndMonthYear(String empNo, int pickMonth, int pickYear);

    @Query(value = "select w from WeaverRewardDateLoomShiftWise w where w.empNo=:empNo and w.rewardDate=:date and w.loom_no=:loom and w.shift=:shiftId")
    WeaverRewardDateLoomShiftWise findWeaver(String empNo, Date date, int loom, String shiftId);

    @Transactional
    @Modifying
    @Query(value = "update WeaverRewardDateLoomShiftWise w set w.rewardAmount=:rewardAmount, w.rewardAmountSlab=:rewardAmountSlab where w.empNo=:empNo and w.rewardDate=:date and w.loom_no=:loom and w.shift=:shiftId")
    int updateWeaverReward(double rewardAmount, String rewardAmountSlab,String empNo,Date date, int loom, String shiftId);

    @Transactional
    @Modifying
    @Query(value = "delete from WeaverRewardDateLoomShiftWise w where month(w.rewardDate)=:pickMonth and year(w.rewardDate)=:pickYear")
    int deleteByMonthYear(int pickMonth, int pickYear);

}
