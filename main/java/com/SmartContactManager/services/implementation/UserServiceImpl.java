package com.SmartContactManager.services.implementation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.SmartContactManager.entities.User;
import com.SmartContactManager.helpers.AppConatants;
import com.SmartContactManager.helpers.Helper;
import com.SmartContactManager.helpers.ResourceNotFoundException;
import com.SmartContactManager.repositories.UserRepo;
import com.SmartContactManager.services.EmailService;
import com.SmartContactManager.services.UserService;

@Service
public class UserServiceImpl implements UserService {

       @Autowired
       private UserRepo userRepo; 

       @Autowired
       private PasswordEncoder passwordEncoder;

       @Autowired
       private EmailService emailService;

       private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public User saveUser(User user) {
        String userId = UUID.randomUUID().toString();
        user.setUserId(userId);
        //user.setPassword(userId);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

         // set the user role 
          user.setRoleList(List.of(AppConatants.ROLE_USER));


        logger.info(user.getProvider().toString());
        String emailToken= UUID.randomUUID().toString();
        user.setEmailToken(emailToken);
        User savedUser= userRepo.save(user);
        String emailLink=Helper.getLinkForEmailVerification(emailToken);
        emailService.sendMail(savedUser.getEmail() , "verify Account: Smart Contact Book " , emailLink);
        return savedUser;

    }

    @Override
    public Optional<User> getUserById(String id) {

        return userRepo.findById(id);
    }

    @Override
    public Optional<User> updatUser(User user) {
       User user2=  userRepo.findById(user.getUserId()).orElseThrow(()-> new ResourceNotFoundException("User not found"));
       user2.setName(user.getName());
       user2.setEmail(user.getEmail());
       user2.setPassword(user.getPassword());
       user2.setAbout(user.getAbout());
       user2.setPhoneNumber(user.getPhoneNumber());
       user2.setProfilePic(user.getProfilePic());
       user2.setEnabled(user.isEnabled());
       user2.setEmailVerified(user.isEmailVerified());
       user2.setPhoneVerified(user.isPhoneVerified());
       user2.setProvider(user.getProvider());
       user2.setProviderUserId(user.getProviderUserId());

        User save=userRepo.save(user2);

        return Optional.ofNullable(save);

    }

    @Override
    public void deleteUser(String id) {
        User user2=  userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User not found"));

         userRepo.delete(user2);

    }

    @Override
    public boolean isUserExits(String userId) {
        User user2=  userRepo.findById(userId).orElse(null);

        return user2!=null ? true : false;

    }

    @Override
    public boolean isUserExitsByEmail(String email) {
        User user= userRepo.findByEmail(email).orElse(null);
        return user!=null ? true : false;
    }

    @Override
    public List<User> getAllUsers() {
       return userRepo.findAll();
    }

    @Override
    public User getUserByEmail(String email) {
         return userRepo.findByEmail(email).orElse(null);
    }

}
