package com.technoride.RewardIncentiveSystem.repository;

import com.technoride.RewardIncentiveSystem.entity.EmployeeIncentive;
import com.technoride.RewardIncentiveSystem.entity.EmployeeIncentiveDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface EmployeeIncentiveDetailsRepository extends JpaRepository<EmployeeIncentiveDetails, Long> {

    @Query(value = "Select e from EmployeeIncentiveDetails e where e.empId=:empNo and e.incMonth=:pickMonth and e.incYear=:pickYear")
    List<EmployeeIncentiveDetails> findByEmpNoMonthYear(@Param("empNo") String empNo, @Param("pickMonth") int pickMonth, @Param("pickYear") int pickYear);

    @Query(value = "Select e from EmployeeIncentiveDetails e where e.incMonth=:pickMonth and e.incYear=:pickYear and e.payCat=:payCat order by e.id")
    List<EmployeeIncentiveDetails> findByMonthAndYear(@Param("pickMonth") int pickMonth, @Param("pickYear") int pickYear, @Param("payCat") String payCat);

    @Modifying
    @Transactional
    @Query(value = "delete from EmployeeIncentiveDetails e where e.incMonth=:month and e.incYear=:year")
    int deleteByMonthYear(int month, int year);

    @Query(value = "Select sum(actualBasicAmount) from EmployeeIncentiveDetails e where e.incMonth=:pickMonth and e.incYear=:pickYear")
    double findActualBasicAmount(@Param("pickMonth") int pickMonth, @Param("pickYear") int pickYear);

    @Query(value = "Select e from EmployeeIncentiveDetails e where e.incMonth=:pickMonth and e.incYear=:pickYear and e.payCat=:payCat and e.empId=:empId order by e.id")
    List<EmployeeIncentiveDetails> findByMonthAndYearAndId(@Param("pickMonth") int pickMonth, @Param("pickYear") int pickYear, @Param("payCat") String payCat, @Param("empId") String empId);


}
