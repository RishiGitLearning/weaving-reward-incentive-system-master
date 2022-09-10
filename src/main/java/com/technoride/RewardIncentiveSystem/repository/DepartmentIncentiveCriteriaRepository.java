package com.technoride.RewardIncentiveSystem.repository;

import com.technoride.RewardIncentiveSystem.entity.DepartmentIncentiveCriteria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface DepartmentIncentiveCriteriaRepository extends JpaRepository<DepartmentIncentiveCriteria, Integer> {

  //  @Transactional
  //  @Modifying
  //  @Query(value = "insert into DepartmentIncentiveCriteria (id, monthlyAchievedProduction, factorToMultiply) values (:id, :monthlyAchievedProduction, :factorToMultiply)")
  //  void saveIncCriteria(@Param("id") int id, @Param("monthlyAchievedProduction") double monthlyAchievedProduction, @Param("factorToMultiply") double factorToMultiply);

    @Query(value = "select distinct(w.id) from DepartmentIncentiveCriteria w")
    List<Integer> findAllId();

    @Query(value = "select w from DepartmentIncentiveCriteria w where w.id=:id")
    DepartmentIncentiveCriteria findIncById(@Param("id") int id);

    @Transactional
    @Modifying
    @Query(value = "update DepartmentIncentiveCriteria p set p.monthlyAchievedProduction=:monthlyAchievedProduction, p.factorToMultiply=:factorToMultiply where p.id=:id")
    int updateIncentiveCriteria(@Param("monthlyAchievedProduction") Double monthlyAchievedProduction, @Param("factorToMultiply")Double factorToMultiply, @Param("id")int id);

    @Modifying
    @Transactional
    @Query(value = "delete from DepartmentIncentiveCriteria e where e.id=:id")
    int deleteIncentiveCriteria(int id);

}
