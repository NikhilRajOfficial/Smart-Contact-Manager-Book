package com.SmartContactManager.helpers;


import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.var;

public class Helper {

    public static String getEmailOfLogedUser(Authentication authentication)
    {

         // AuthenticationPrincipal principal=(AuthenticationPrincipal)authentication.getPrincipal();
        // if user login through email and password 
            if(authentication instanceof OAuth2AuthenticationToken)
            {

               var aOAuth2AuthenticationToken=(OAuth2AuthenticationToken)authentication;
                  var clientId = aOAuth2AuthenticationToken.getAuthorizedClientRegistrationId();

                  var oauth2=(OAuth2User)authentication.getPrincipal();
                  String username="";
                  
                  // sign in with github
                   if(clientId.equalsIgnoreCase("google"))
                   {
                        System.out.println("Getting email from google ");
                      username= oauth2.getAttribute("email").toString();
                   }
                     
                    // sign in with github
                   else if (clientId.equalsIgnoreCase("github")) 
                   {
                    System.out.println("Getting email from google ");

                    username= oauth2.getAttribute("email") !=null ? oauth2.getAttribute("email").toString() : oauth2.getAttribute("login").toString() +"@gmail.com";


                   }
                   

                        return username;
                  }  
                  
                  else {
                       System.out.println("Getting data from local database ");
                       return authentication.getName();
                  }


    }

     public static String getLinkForEmailVerification(String emailToken)
     {
      
          String link= "http://localhost:8080/auth/verify-email?token=" + emailToken;
          return link;
           
           
     }
                    
     

}
