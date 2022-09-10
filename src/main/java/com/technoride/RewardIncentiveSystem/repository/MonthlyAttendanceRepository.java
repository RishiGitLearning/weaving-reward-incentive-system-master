package com.technoride.RewardIncentiveSystem.repository;

import com.technoride.RewardIncentiveSystem.Id.MonthlyAttendanceId;
import com.technoride.RewardIncentiveSystem.entity.MonthlyAttendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonthlyAttendanceRepository extends JpaRepository<MonthlyAttendance, MonthlyAttendanceId> {

    @Query("select m from MonthlyAttendance m where m.masEmpname !=:masEmpName and m.masEmpid =:masEmpid and m.masMM =:masMM and m.masYYYY =:masYYYY group by m.payCatg")
    List<MonthlyAttendance> findMonthAttendance(@Param("masEmpName") String masEmpName,@Param("masEmpid") String masEmpid, @Param("masMM") int masMM, @Param("masYYYY") int masYYYY);

    @Query("select distinct(m.masEmpid) from MonthlyAttendance m where m.incentiveFlag=true and m.masMM =:masMM and m.masYYYY =:masYYYY and m.designationGrade>=1 and m.masEmpid !=:masEmpid order by m.masEmpid")
    List<String> findEmpListForInc(@Param("masMM") int masMM, @Param("masYYYY") int masYYYY, @Param("masEmpid") String masEmpid);

    @Query(value = "select distinct(m.masEmpid) from MonthlyAttendance m where m.masEmpid!=:masEmpid")
    List<String> findEmpList(@Param("masEmpid") String masEmpid);

    @Query(value = "select distinct(m.masEmpname) from MonthlyAttendance m where masEmpid=:masEmpid and m.masEmpname !=:masEmpname")
    List<String> findEmpName(@Param("masEmpid") String masEmpid, @Param("masEmpname") String masEmpname);

    @Query(value = "select distinct(m) from MonthlyAttendance m where m.incentiveFlag=true and m.designationGrade>=1 and m.masEmpid !=:masEmpid and m.masEmpname !=:masEmpname group by m.masEmpid")
    List<MonthlyAttendance> findEmpMonthly(@Param("masEmpid") String masEmpid,@Param("masEmpname") String masEmpname);

    @Query("select distinct(m.masEmpid) from MonthlyAttendance m where m.incentiveFlag=true and m.masMM =:masMM and m.masYYYY =:masYYYY and m.designationGrade>=1 and m.masEmpid !=:masEmpid and m.payCatg =:payCatg order by m.masEmpid")
    List<String> findEmpListForIncByPayCat(@Param("masMM") int masMM, @Param("masYYYY") int masYYYY, @Param("masEmpid") String masEmpid, @Param("payCatg") String payCatg);

    @Query("select m from MonthlyAttendance m where m.incentiveFlag=true and m.masMM =:masMM and m.masYYYY =:masYYYY and m.designationGrade>=1 and m.masEmpid =:masEmpid and m.payCatg =:payCatg")
    MonthlyAttendance findEmpDataByPayCatg(@Param("masMM") int masMM, @Param("masYYYY") int masYYYY, @Param("masEmpid") String masEmpid, @Param("payCatg") String payCatg);

    //  @Query(value = "select distinct(m) from MonthlyAttendance m where m.incentiveFlag=true and m.designationGrade>=1 and m.masEmpid !=:masEmpid and m.masEmpname !=:masEmpname group by m.masEmpid")
  //  List<MonthlyAttendance> findEmpMonthly(@Param("masEmpid") String masEmpid,@Param("masEmpname") String masEmpname);
}
