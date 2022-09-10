package com.technoride.RewardIncentiveSystem.repository;

import com.technoride.RewardIncentiveSystem.Id.PtaxMonthId;
import com.technoride.RewardIncentiveSystem.entity.PtaxMonth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface PtaxMonthRepository extends JpaRepository<PtaxMonth, PtaxMonthId> {

    @Transactional
    @Modifying
    @Query(value = "update PtaxMonth p set p.ptWvrRewardGross=:ptWvrRewardGross where p.ptEmpCode=:ptEmpCode and p.ptMonth=:ptMonth and ptYear=:ptYear")
    int updateRewardPtax(@Param("ptWvrRewardGross") Double ptWvrRewardGross, @Param("ptEmpCode")String ptEmpCode, @Param("ptMonth")int ptMonth, @Param("ptYear")int ptYear);

    @Transactional
    @Modifying
    @Query(value = "update PtaxMonth p set p.ptWvgIncGross=:ptWvgIncGross, p.ptWvgIncPtax=:ptWvgIncPtax where p.ptEmpCode=:ptEmpCode and p.ptMonth=:ptMonth and ptYear=:ptYear")
    int updateIncentivePtax(@Param("ptWvgIncGross") Double ptWvgIncGross, @Param("ptWvgIncPtax")Double ptWvgIncPtax, @Param("ptEmpCode")String ptEmpCode, @Param("ptMonth")int ptMonth, @Param("ptYear")int ptYear);

    @Query(value = "Select p from PtaxMonth p where p.ptMonth=:ptMonth and p.ptYear=:ptYear")
    List<PtaxMonth> getPtax(@Param("ptMonth") int ptMonth, @Param("ptYear") int ptYear);

    @Query(value = "Select p from PtaxMonth p where p.ptMonth=:ptMonth and p.ptYear=:ptYear")
    List<String> findEmpByMonthYear(@Param("ptMonth") int ptMonth, @Param("ptYear") int ptYear);

    @Query(value = "Select p from PtaxMonth p where p.ptEmpCode=:ptEmpCode and p.ptMonth=:ptMonth and p.ptYear=:ptYear")
    List<PtaxMonth> findEmpGross(@Param("ptEmpCode") String ptEmpCode, @Param("ptMonth") int ptMonth, @Param("ptYear") int ptYear);

    @Transactional
    @Modifying
    @Query(value = "update PtaxMonth p set p.ptTotalGross=:ptTotalGross, p.ptTotalPtax=:ptTotalPtax where p.ptEmpCode=:ptEmpCode and p.ptMonth=:ptMonth and ptYear=:ptYear")
    int updateGross(@Param("ptTotalGross") Double ptTotalGross, @Param("ptTotalPtax")Double ptTotalPtax, @Param("ptEmpCode")String ptEmpCode, @Param("ptMonth")int ptMonth, @Param("ptYear")int ptYear);

}
