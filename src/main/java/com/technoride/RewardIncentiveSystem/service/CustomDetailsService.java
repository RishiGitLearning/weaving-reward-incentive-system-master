package com.technoride.RewardIncentiveSystem.service;

import com.technoride.RewardIncentiveSystem.configuration.CustomUserService;
import com.technoride.RewardIncentiveSystem.model.User;
import com.technoride.RewardIncentiveSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if(user == null){
            throw new UsernameNotFoundException("User not Found");
        }
        return new CustomUserService(user);
    }
}
