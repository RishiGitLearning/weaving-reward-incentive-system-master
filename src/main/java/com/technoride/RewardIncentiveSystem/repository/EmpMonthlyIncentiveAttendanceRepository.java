package com.technoride.RewardIncentiveSystem.repository;

import com.technoride.RewardIncentiveSystem.entity.EmpMonthlyIncentiveAttendance;
import com.technoride.RewardIncentiveSystem.model.EmpIncAttendanceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface EmpMonthlyIncentiveAttendanceRepository extends JpaRepository<EmpMonthlyIncentiveAttendance,Long> {

    @Query("select distinct(empCode) from EmpMonthlyIncentiveAttendance e where e.pickMonth=:pickMonth and e.pickYear=:pickYear order by e.empCode")
    List<String> findEmpIdByMonthYear(@Param("pickMonth") int pickMonth, @Param("pickYear") int pickYear);

    @Query("select new com.technoride.RewardIncentiveSystem.model.EmpIncAttendanceModel(e.payCat,e.dept,e.empCode,e.empName,e.categoryGrade,e.designationGrade,e.eligibleIncGrade,e.eligibleRate,sum(e.present) as presentSum, sum(e.rokdi) as rokdiSum, sum(e.total) as totalSum) from EmpMonthlyIncentiveAttendance e where e.empCode=:empCode and e.pickMonth=:pickMonth and e.pickYear=:pickYear and e.payCat=:payCat group by eligibleIncGrade, designationGrade, categoryGrade, payCat")
    List<EmpIncAttendanceModel> findByMonthAndYear(@Param("empCode") String empNo, @Param("pickMonth") int pickMonth, @Param("pickYear") int pickYear, @Param("payCat") String payCat);

    @Modifying
    @Transactional
    @Query("delete from EmpMonthlyIncentiveAttendance e where e.pickMonth=:pickMonth and e.pickYear=:pickYear")
    int deleteByMonthYear(@Param("pickMonth") int pickMonth, @Param("pickYear") int pickYear);
}
