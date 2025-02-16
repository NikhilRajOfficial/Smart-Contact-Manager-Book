package com.SmartContactManager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.SmartContactManager.entities.User;
import com.SmartContactManager.forms.UserForm;
import com.SmartContactManager.helpers.Message;
import com.SmartContactManager.helpers.MessageType;
import com.SmartContactManager.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;



@Controller
public class PageController {

       @Autowired
       private UserService userService;


       @GetMapping("/")
       public String index()
       {
              return "redirect:/home";
       }

    @RequestMapping("/home")
    public String home()
    {
           System.out.println("Home page ");
           return "home";
    }

    @RequestMapping("/about")
    public String about()
    {
           System.out.println("Home page ");
           return "about";
    }

    @RequestMapping("/services")
    public String service()
    {
           System.out.println("Home page ");
           return "services";
    }

    // contact details 

    @GetMapping("/contact")
    public String contact()
    {
           
           return new String("contact");
    }

    @GetMapping("/login")
    public String login()
    {
           
           return new String("login");
    }

    @GetMapping("/sign")
    public String signIn(Model model)
    {
           UserForm userForm=new UserForm();

       //     userForm.setName("Nikhil");
       //     userForm.setEmail("nikhilraj2277@gmail.com");
       //     userForm.setPassword("nike@321");
       //     userForm.setPhoneNumber("7418529637");
       //     userForm.setAbout("Hey my name is nikhil raj ");
           model.addAttribute("userForm", userForm);
           return "sign";
    }


    // processing sign form  
    @RequestMapping(value = "/do-register", method=RequestMethod.POST)  
    public String signForm(@Valid @ModelAttribute UserForm userForm ,BindingResult rBindingResult,  HttpSession session)
    {
         System.out.println("SignIn Sucessfully");
         System.out.println(userForm);



           // Validation form data 
           if(rBindingResult.hasErrors())
           {
              return "sign";
           }
           




         // saving userservices data 
       //   User user=User.builder()
       //   .name(userForm.getName())
       //   .email(userForm.getEmail())
       //   .password(userForm.getPassword())
       //   .about(userForm.getAbout())
       //   .phoneNumber(userForm.getPhoneNumber())
       //   .profilePic("https://preview.redd.it/default-profile-picture-template-based-off-of-mf-doom-albums-v0-jdkp3xxvv4ua1.png?width=1000&format=png&auto=webp&s=877f3ba1d9f86651d50dceca01f223466d03f783")
       //   .build();

 
         User user =new User();
         user.setName(userForm.getName());
         user.setEmail(userForm.getEmail());
         user.setPassword(userForm.getPassword());
         user.setAbout(userForm.getAbout());
         user.setPhoneNumber(userForm.getPhoneNumber());
         user.setEnabled(false);
         user.setProfilePic("https://preview.redd.it/default-profile-picture-template-based-off-of-mf-doom-albums-v0-jdkp3xxvv4ua1.png?width=1000&format=png&auto=webp&s=877f3ba1d9f86651d50dceca01f223466d03f783");

         User savedUser= userService.saveUser(user);
           System.out.println("usersaved");


           // adding message 
          Message message= Message.builder().content("Registration Sucessfull").type(MessageType.green).build();
          session.setAttribute("message" , message);



          //   Redirecting page 
         return "redirect:/sign";
    }

    
    
}
