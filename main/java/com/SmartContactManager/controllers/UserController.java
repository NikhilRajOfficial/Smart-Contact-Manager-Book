package com.SmartContactManager.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.SmartContactManager.entities.User;
import com.SmartContactManager.helpers.Helper;
import com.SmartContactManager.services.ContactService;
import com.SmartContactManager.services.UserService;


@Controller
@RequestMapping("/user")
public class UserController {
    
        private Logger logger=LoggerFactory.getLogger(UserController.class);

        
        @Autowired
        private UserService userService;

        
      // user dashboad controller
      @RequestMapping(value = "/dashboard")
      public String userDash(Model model) {
        
          return "user/dashboard" ;
      }

      // User profile Controllers 
      @RequestMapping(value = "/profile")
      public String userProfile(Model model , Authentication authentication ) {
         String username= Helper.getEmailOfLogedUser(authentication);
          //String name = principal.getName();
          logger.info("UserLogged in: {}" , username);

         User user =  userService.getUserByEmail(username);
         
         
            System.out.println(user.getName());
            System.out.println(user.getEmail());
            model.addAttribute("loggedUser", user);


          return "user/profile" ;
      }
      
      

}
