package com.technoride.RewardIncentiveSystem.repository;

import com.technoride.RewardIncentiveSystem.entity.EmployeeIncentive;
import com.technoride.RewardIncentiveSystem.entity.EmployeeIncentiveDailyDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface EmployeeIncentiveDailyDetailsRepository extends JpaRepository<EmployeeIncentiveDailyDetails,Long> {
    @Modifying
    @Transactional
    @Query(value = "delete from EmployeeIncentiveDailyDetails e where e.incMonth=:month and e.incYear=:year")
    int deleteEmpInc(int month, int year);

    @Query(value = "Select e from EmployeeIncentiveDailyDetails e where e.empId=:empNo and e.incMonth=:pickMonth and e.incYear=:pickYear")
    List<EmployeeIncentiveDailyDetails> findByEmpNoAndMonthAndYear(@Param("empNo") String empNo, @Param("pickMonth") int pickMonth, @Param("pickYear") int pickYear);

    @Query(value = "Select incDailyRate, sum(presentrokadi) from EmployeeIncentiveDailyDetails e where e.empId=:empNo and e.incMonth=:pickMonth and e.incYear=:pickYear group by incDailyRate")
    List<List<String>> findByEmpNoAndInceRate(@Param("empNo") String empNo, @Param("pickMonth") int pickMonth, @Param("pickYear") int pickYear);
}
