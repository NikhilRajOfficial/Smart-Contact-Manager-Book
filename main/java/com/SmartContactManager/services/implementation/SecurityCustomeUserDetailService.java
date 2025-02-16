package com.SmartContactManager.services.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.SmartContactManager.repositories.UserRepo;

@Service
public class SecurityCustomeUserDetailService implements UserDetailsService{

     @Autowired
     private UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
           // Loading User 
       return    userRepo.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("User not found " +username));
    }

}
