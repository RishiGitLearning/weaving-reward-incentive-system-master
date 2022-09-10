package com.technoride.RewardIncentiveSystem.repository;

import com.technoride.RewardIncentiveSystem.Id.MonthTotalWeavingProdId;
import com.technoride.RewardIncentiveSystem.entity.MonthTotalWeavingProduction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MonthTotalWeavingProdRepository extends JpaRepository<MonthTotalWeavingProduction, MonthTotalWeavingProdId> {

    @Query("select w from MonthTotalWeavingProduction w where pickMonth =:pickMonth and pickYear =:pickYear")
    MonthTotalWeavingProduction getTotalWeavingProd(@Param("pickMonth") int pickMonth, @Param("pickYear") int pickYear);
}
