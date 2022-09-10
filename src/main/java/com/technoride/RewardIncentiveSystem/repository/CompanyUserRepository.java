package com.technoride.RewardIncentiveSystem.repository;

import com.technoride.RewardIncentiveSystem.Id.CompanyUserId;
import com.technoride.RewardIncentiveSystem.entity.CompanyUser;
import com.technoride.RewardIncentiveSystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyUserRepository extends JpaRepository<CompanyUser, CompanyUserId> {
    @Query("SELECT u from CompanyUser u WHERE u.loginId= :loginId and u.password= :password")
    CompanyUser findByLoginIdAndPassword(String loginId, String password);

    @Query("select u from CompanyUser u where u.loginId= :loginId")
    CompanyUser findByLoginId(String loginId);
}
