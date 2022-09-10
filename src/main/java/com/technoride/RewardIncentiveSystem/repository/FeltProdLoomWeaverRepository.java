package com.technoride.RewardIncentiveSystem.repository;

import com.technoride.RewardIncentiveSystem.entity.FeltProdLoomWeaver;
import com.technoride.RewardIncentiveSystem.Id.FeltProdLoomWeaverId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface FeltProdLoomWeaverRepository extends JpaRepository<FeltProdLoomWeaver, FeltProdLoomWeaverId> {

  /*  @Query(value = "Select distinct empNo from FeltProdLoomWeaver")
    List<String> getEmpNo();

    @Query(value = "select f from FeltProdLoomWeaver f where f.docDate >=:date1 and f.docDate <=:date2 and f.loomEng =:loomEng")
    List<FeltProdLoomWeaver> findEmp(@Param("date1") Date date1, @Param("date2") Date date2, @Param("loomEng") String loomEng);

    @Query(value = "select f from FeltProdLoomWeaver f where f.docDate >=:date1 and f.docDate <=:date2")
    List<FeltProdLoomWeaver> findEmpDate(@Param("date1") Date date1, @Param("date2") Date date2);

    @Query(value = "select f from FeltProdLoomWeaver f where f.empNo=:empNo and f.docDate=:docDate")
    List<FeltProdLoomWeaver> findEmpShift(@Param("empNo") String empNo, @Param("docDate") Date docDate);*/

    @Query(value = "select f from FeltProdLoomWeaver f where f.empNo=:empNo and f.docDate=:docDate and f.loomEng!=:loomEng and f.weftDetails!=:weftDetails and f.pick!=:pick")
    List<FeltProdLoomWeaver> findCategoryDateWise(@Param("empNo") String empNo, @Param("docDate") Date docDate, @Param("loomEng") String loomEng, @Param("weftDetails") String weftDetails, @Param("pick") String pick);

    @Query(value = "select f from FeltProdLoomWeaver f where f.empNo=:empNo")
    List<FeltProdLoomWeaver> findCategoryById(@Param("empNo") String empNo);

    @Query(value = "select f from FeltProdLoomWeaver f where f.empNo != null and month(f.docDate)=:pickMonth and year(f.docDate)=:pickYear")
    List<FeltProdLoomWeaver> findEmpByMonth(@Param("pickMonth") int pickMonth, @Param("pickYear") int pickYear);

    @Query(value = "select distinct(f.empNo) from FeltProdLoomWeaver f where month(f.docDate)=:pickMonth and year(f.docDate)=:pickYear and f.empNo!=:empNo order by f.empNo")
    List<String> findEmpNoByMonth(@Param("pickMonth") int pickMonth, @Param("pickYear") int pickYear, @Param("empNo") String empNo);

    @Query(value = "select f from FeltProdLoomWeaver f where month(f.docDate)=:pickMonth and year(f.docDate)=:pickYear and f.category=:empCategory")
    List<FeltProdLoomWeaver> findEmpByMonthAndCategory(@Param("pickMonth") int pickMonth, @Param("pickYear") int pickYear, @Param("empCategory") String empCategory);

    @Query(value = "select distinct(f.shiftId) from FeltProdLoomWeaver f")
    List<String> findShift();

    @Query(value = "select f from FeltProdLoomWeaver f where f.empNo=:empNo")
    List<FeltProdLoomWeaver> findByEmpId(@Param("empNo") String empNo);

    @Query(value = "select f from FeltProdLoomWeaver f where f.empNo=:empNo and month(f.docDate)=:pickMonth and year(f.docDate)=:pickYear and f.loomEng!=:loomEng and f.weftDetails!=:weftDetails and f.pick!=:pick and f.empName!=:empName and f.designation!=:designation and f.shiftId!=:shiftId")
    List<FeltProdLoomWeaver> findEmpByIdMonthYear(@Param("empNo") String empNo, @Param("pickMonth") int pickMonth, @Param("pickYear") int pickYear, @Param("loomEng") String loomEng, @Param("weftDetails") String weftDetails, @Param("pick") String pick, @Param("empName") String empName, @Param("designation") String designation, @Param("shiftId") String shiftId);

    @Query(value = "select f from FeltProdLoomWeaver f where f.empNo=:empNo and month(f.docDate)=:pickMonth and year(f.docDate)=:pickYear and f.empName!=:empName and f.designation!=:designation and f.shiftId!=:shiftId")
    List<FeltProdLoomWeaver> findEmpByIdMonthYearList(@Param("empNo") String empNo, @Param("pickMonth") int pickMonth, @Param("pickYear") int pickYear,@Param("empName") String empName, @Param("designation") String designation, @Param("shiftId") String shiftId);

    @Query(value = "select categoryGrade,sum(present),sum(rokdi) from FeltProdLoomWeaver f where f.empNo=:empNo and month(f.docDate)=:pickMonth and year(f.docDate)=:pickYear and f.loomEng!=:loomEng and f.weftDetails!=:weftDetails and f.pick!=:pick and f.empName!=:empName and f.designation!=:designation and f.shiftId!=:shiftId group by categoryGrade")
    List<List<Double>> findCategoryPresentRokdi(@Param("empNo") String empNo, @Param("pickMonth") int pickMonth, @Param("pickYear") int pickYear, @Param("loomEng") String loomEng, @Param("weftDetails") String weftDetails, @Param("pick") String pick, @Param("empName") String empName, @Param("designation") String designation, @Param("shiftId") String shiftId);

    @Query(value = "select categoryGrade,sum(present),sum(rokdi) from FeltProdLoomWeaver f where f.empNo=:empNo and month(f.docDate)=:pickMonth and year(f.docDate)=:pickYear and f.empName!=:empName and f.designation!=:designation and f.shiftId!=:shiftId group by categoryGrade")
    List<List<Double>> findCategoryPresentRokdiList(@Param("empNo") String empNo, @Param("pickMonth") int pickMonth, @Param("pickYear") int pickYear, @Param("empName") String empName, @Param("designation") String designation, @Param("shiftId") String shiftId);
    @Query(value = "select distinct(f.empNo) from FeltProdLoomWeaver f where f.empNo!=:empNo")
    List<String> findEmpId(@Param("empNo") String empNo);

    @Query(value = "select distinct(f) from FeltProdLoomWeaver f group by f.empNo")
    List<FeltProdLoomWeaver> findEmpList();
}
