package com.technoride.RewardIncentiveSystem.repository;

import com.technoride.RewardIncentiveSystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    @Query("SELECT u from User u WHERE u.email= :email")
    User findByEmail(String email);

    @Query("SELECT u from User u WHERE u.email= :email and u.password= :password")
    User findByEmailAndPassword(String email, String password);
}
