package com.SmartContactManager.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.SmartContactManager.entities.User;
import com.SmartContactManager.helpers.Helper;
import com.SmartContactManager.services.UserService;

@ControllerAdvice
public class RootController {
    @Autowired
     private UserService userService;
        
        Logger logger= LoggerFactory.getLogger(this.getClass());

    @ModelAttribute
        public void addLoggedUserInfo(Model model, Authentication authentication)
        {

            if (authentication==null) {
                return ;
            }

             System.out.println("Adding logged in User information");
            String username= Helper.getEmailOfLogedUser(authentication);
            logger.info("UserLogged in: {}" , username);
           User user =  userService.getUserByEmail(username);
                System.out.println(user);
                System.out.println(user.getName());
                System.out.println(user.getEmail());
                
                logger.info("User found: {}", user);
                model.addAttribute("loggedUser", user);


           
        }
      
    // @ModelAttribute
    // public void addLoggedUserInfo(Model model, Authentication authentication) {
    //     if (authentication == null) {
    //         logger.info("No user is authenticated.");
    //         return;
    //     }
    
    //     String username = Helper.getEmailOfLogedUser(authentication);
    //     logger.info("User logged in: {}", username);
    //     User user = userService.getUserByEmail(username);
    
    //     if (user != null) {
    //         logger.info("User found: {}", user);
    //         model.addAttribute("loggedUser", user);
    //     } else {
    //         logger.warn("No user found for email: {}", username);
    //     }
    // }


}
