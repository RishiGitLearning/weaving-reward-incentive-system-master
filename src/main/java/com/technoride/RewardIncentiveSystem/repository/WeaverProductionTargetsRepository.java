package com.technoride.RewardIncentiveSystem.repository;

import com.technoride.RewardIncentiveSystem.entity.WeaverProductionTargets;
import com.technoride.RewardIncentiveSystem.Id.WeaverProductionTargetsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface WeaverProductionTargetsRepository extends JpaRepository<WeaverProductionTargets, WeaverProductionTargetsId> {

    @Query(value = "select w from WeaverProductionTargets w where w.loom=:loom and w.weftDetails like %:weftDetails%")
    WeaverProductionTargets getRewardDetails(@Param("loom") int loom, @Param("weftDetails") String weftDetails);

    @Query(value = "select distinct(w.loom) from WeaverProductionTargets w")
    List<String> findLoomId();

    @Query(value = "select w from WeaverProductionTargets w")
    List<WeaverProductionTargets> findWeaverProdDetails();

    @Query(value = "select w.loomPrimary from WeaverProductionTargets w")
    List<String> findLoomPrimaryList();

    @Query(value = "select w from WeaverProductionTargets w where w.loomPrimary=:loomPrimary")
    WeaverProductionTargets findRewById(String loomPrimary);

    @Transactional
    @Modifying
    @Query(value = "update WeaverProductionTargets e set e.loom=:loom, e.rpm=:rpm, e.effPer=:effPer, e.prodnPerHr=:prodnPerHr, e.weftDetails=:weftDetails, e.shiftAB=:shiftAB, e.shiftC=:shiftC, e.shiftABPicksForRewards=:shiftABPicksForRewards, e.shiftCPicksForRewards=:shiftCPicksForRewards, e.reward1=:reward1,e.shiftABPicksForRewards2=:shiftABPicksForRewards2,e.shiftCPicksForRewards2=:shiftCPicksForRewards2,e.reward2=:reward2,e.shiftABPicksForRewards3=:shiftABPicksForRewards3,e.shiftCPicksForRewards3=:shiftCPicksForRewards3,e.reward3=:reward3,e.shiftABPicksForRewards4=:shiftABPicksForRewards4,e.shiftCPicksForRewards4=:shiftCPicksForRewards4,e.reward4=:reward4,e.shiftABPicksForRewards5=:shiftABPicksForRewards5,e.shiftCPicksForRewards5=:shiftCPicksForRewards5,e.reward5=:reward5,e.shiftABPicksForRewards6=:shiftABPicksForRewards6,e.shiftCPicksForRewards6=:shiftCPicksForRewards6,e.reward6=:reward6,e.shiftABPicksForRewards7=:shiftABPicksForRewards7,e.shiftCPicksForRewards7=:shiftCPicksForRewards7,e.reward7=:reward7,e.shiftABPicksForRewards8=:shiftABPicksForRewards8,e.shiftCPicksForRewards8=:shiftCPicksForRewards8,e.reward8=:reward8,e.shiftABPicksForRewards9=:shiftABPicksForRewards9,e.shiftCPicksForRewards9=:shiftCPicksForRewards9,e.reward9=:reward9,e.shiftABPicksForRewards10=:shiftABPicksForRewards10,e.shiftCPicksForRewards10=:shiftCPicksForRewards10,e.reward10=:reward10 where e.loomPrimary=:loomPrimary")
    int updateRewardSlab(int loom, String rpm, String effPer,String prodnPerHr,String weftDetails, String shiftAB,String shiftC,String shiftABPicksForRewards,String shiftCPicksForRewards,String reward1,String shiftABPicksForRewards2,String shiftCPicksForRewards2,String reward2,String shiftABPicksForRewards3,String shiftCPicksForRewards3,String reward3,String shiftABPicksForRewards4,String shiftCPicksForRewards4,String reward4,String shiftABPicksForRewards5,String shiftCPicksForRewards5,String reward5,String shiftABPicksForRewards6,String shiftCPicksForRewards6,String reward6,String shiftABPicksForRewards7,String shiftCPicksForRewards7,String reward7,String shiftABPicksForRewards8,String shiftCPicksForRewards8,String reward8,String shiftABPicksForRewards9,String shiftCPicksForRewards9,String reward9,String shiftABPicksForRewards10,String shiftCPicksForRewards10,String reward10, String loomPrimary);

  //  @Transactional
  //  @Modifying
  //  @Query(value = "update WeaverProductionTargets p set p.loom=:loom, p.rpm=:rpm, e.effPer=:effPer,e.prodnPerHr=:prodnPerHr,e.weftDetails=:weftDetails,e.shiftAB=:shiftAB,e.shiftC=:shiftC,e.shiftABPicksForRewards=:shiftABPicksForRewards,e.shiftCPicksForRewards=:shiftCPicksForRewards,e.reward1=:reward1,e.shiftABPicksForRewards2=:shiftABPicksForRewards2,e.shiftCPicksForRewards2=:shiftCPicksForRewards2,e.reward2=:reward2,e.shiftABPicksForRewards3=:shiftABPicksForRewards3,e.shiftCPicksForRewards3=:shiftCPicksForRewards3,e.reward3=:reward3,e.shiftABPicksForRewards4=:shiftABPicksForRewards4,e.shiftCPicksForRewards4=:shiftCPicksForRewards4,e.reward4=:reward4,e.shiftABPicksForRewards5=:shiftABPicksForRewards5,e.shiftCPicksForRewards5=:shiftCPicksForRewards5,e.reward5=:reward5,e.shiftABPicksForRewards6=:shiftABPicksForRewards6,e.shiftCPicksForRewards6=:shiftCPicksForRewards6,e.reward6=:reward6,e.shiftABPicksForRewards7=:shiftABPicksForRewards7,e.shiftCPicksForRewards7=:shiftCPicksForRewards7,e.reward7=:reward7,e.shiftABPicksForRewards8=:shiftABPicksForRewards8,e.shiftCPicksForRewards8=:shiftCPicksForRewards8,e.reward8=:reward8,e.shiftABPicksForRewards9=:shiftABPicksForRewards9,e.shiftCPicksForRewards9=:shiftCPicksForRewards9,e.reward9=:reward9,e.shiftABPicksForRewards10=:shiftABPicksForRewards10,e.shiftCPicksForRewards10=:shiftCPicksForRewards10,e.reward10=:reward10 where p.loomPrimary=:loomPrimary")
  //  int updateRewardSlab(String loom, String rpm, String effPer,String prodnPerHr,String weftDetails,String shiftAB,String shiftC,String shiftABPicksForRewards,String shiftCPicksForRewards,String reward1,String shiftABPicksForRewards2,String shiftCPicksForRewards2,String reward2,String shiftABPicksForRewards3,String shiftCPicksForRewards3,String reward3,String shiftABPicksForRewards4,String shiftCPicksForRewards4,String reward4,String shiftABPicksForRewards5,String shiftCPicksForRewards5,String reward5,String shiftABPicksForRewards6,String shiftCPicksForRewards6,String reward6,String shiftABPicksForRewards7,String shiftCPicksForRewards7,String reward7,String shiftABPicksForRewards8,String shiftCPicksForRewards8,String reward8,String shiftABPicksForRewards9,String shiftCPicksForRewards9,String reward9,String shiftABPicksForRewards10,String shiftCPicksForRewards10,String reward10, String loomPrimary);

    @Modifying
    @Transactional
    @Query(value = "delete from WeaverProductionTargets e where e.loomPrimary=:loomPrimary")
    int deleteRewardSlab(String loomPrimary);

}
