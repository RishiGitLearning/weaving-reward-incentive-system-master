package com.technoride.RewardIncentiveSystem.repository;

import com.technoride.RewardIncentiveSystem.entity.DesignationwiseRate;
import com.technoride.RewardIncentiveSystem.entity.FeltProdLoomWeaver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DesignationwiseRateRepository extends JpaRepository<DesignationwiseRate,String> {

    @Query(value = "select d from DesignationwiseRate d where d.designation=:designation")
    DesignationwiseRate findByDesignation(@Param("designation") String designation);

    @Query(value = "select d from DesignationwiseRate d where d.designation like %:designation%")
    List<DesignationwiseRate> findByDesignationLike(@Param("designation") String designation);

    @Query(value = "select d from DesignationwiseRate d where d.grade=:grade")
    List<DesignationwiseRate> findRateByGrade(@Param("grade") double grade);
}
