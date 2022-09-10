package com.technoride.RewardIncentiveSystem.repository;

import com.technoride.RewardIncentiveSystem.entity.EmployeeIncentive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface EmployeeIncentiveRepository extends JpaRepository<EmployeeIncentive, Long> {

    @Query(value = "Select e from EmployeeIncentive e where e.incMonth=:pickMonth and e.incYear=:pickYear order by e.id")
    List<EmployeeIncentive> findByMonthAndYear(@Param("pickMonth") int pickMonth, @Param("pickYear") int pickYear);

    @Query(value = "Select e from EmployeeIncentive e where e.empId=:empNo and e.incMonth=:pickMonth and e.incYear=:pickYear")
    List<EmployeeIncentive> findByEmpNoAndMonthYear(@Param("empNo") String empNo, @Param("pickMonth") int pickMonth, @Param("pickYear") int pickYear);

    @Modifying
    @Transactional
    @Query(value = "delete from EmployeeIncentive e where e.incMonth=:month and e.incYear=:year")
    int deleteEmpIncentive(int month, int year);
}
