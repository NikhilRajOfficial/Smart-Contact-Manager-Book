package com.SmartContactManager.config;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.SmartContactManager.entities.Providers;
import com.SmartContactManager.entities.User;
import com.SmartContactManager.helpers.AppConatants;
import com.SmartContactManager.repositories.UserRepo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.var;

@Component
public class OAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

     Logger logger= LoggerFactory.getLogger(OAuthenticationSuccessHandler.class);
     
       @Autowired
       private UserRepo userRepo;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
              
                  logger.info("OAuthenticationSuccessHandler");

                    // identify the provider 

                      var oauth2AuthenicationToken=(OAuth2AuthenticationToken) authentication;

                  String authorizedClientRegid=     oauth2AuthenicationToken.getAuthorizedClientRegistrationId();

                  logger.info(authorizedClientRegid);

                   var oauthUser= (DefaultOAuth2User)authentication.getPrincipal();
                   oauthUser.getAttributes().forEach((key,value)->{
                       logger.info(key + " : " + value);
                   });

                     User user=new User();
                     user.setUserId(UUID.randomUUID().toString());
                     user.setRoleList(List.of(AppConatants.ROLE_USER));
                     user.setEmailVerified(true);
                     user.setEnabled(true);

                  if(authorizedClientRegid.equalsIgnoreCase("google"))
                  {
                       // google 
                       //google attributes

                       user.setEmail(oauthUser.getAttribute("email"));
                       user.setProfilePic(oauthUser.getAttribute("picture").toString());
                       user.setName(oauthUser.getAttribute("name").toString());
                       user.setProviderUserId(oauthUser.getName());
                       user.setProvider(Providers.GOOGLE);
                       user.setAbout("This account is login by google");
                  }
                  else if(authorizedClientRegid.equalsIgnoreCase("github"))
                  {
                         
                          //github
                          //github attributes
                          String email= oauthUser.getAttribute("email") !=null ? oauthUser.getAttribute("email").toString() : oauthUser.getAttribute("login").toString() +"@gmail.com";

                          String picture = oauthUser.getAttribute("avatar_url").toString();

                          String name= oauthUser.getAttribute("login").toString();

                          String providerUser= oauthUser.getName();

                          user.setEmail(email);
                          user.setProfilePic(picture);
                          user.setName(name);
                          user.setProviderUserId(providerUser);
                          user.setProvider(Providers.GITHUB);
                          user.setAbout("This account is login by github");
                  }
                  else
                  {
                       logger.info("OAuthAuthenicationSuccessHandler: Unknown provider");
                  }

                    /* DefaultOAuth2User user=(DefaultOAuth2User) authentication.getPrincipal();
                   
                   logger.info(user.getName());

                   user.getAttributes().forEach((key,value) -> {
                          logger.info("{} => {}" , key , value);
                   });

                    logger.info(user.getAuthorities().toString());
                   
                   

                   String email = user.getAttribute("email").toString();
                   String name= user.getAttribute("name").toString();
                   String picture =  user.getAttribute("picture").toString();

                   // saving user in databases 
                     
                   User user1 =new User();
                   user1.setEmail(email);
                   user1.setName(name);
                   user1.setProfilePic(picture);
                   user1.setPassword("password");
                   user1.setUserId(UUID.randomUUID().toString());
                   user1.setProvider(Providers.GOOGLE);
                   user1.setEnabled(true);
                   user1.setEmailVerified(true);
                   user1.setProviderUserId(user.getName());
                   user1.setRoleList(List.of(AppConatants.ROLE_USER));
                   user1.setAbout("This account is created using google... ");

                     User user2= userRepo.findByEmail(email).orElse(null);

                      if(user2==null)
                      {
                           userRepo.save(user1);
                           logger.info("User saved:" +email);
                      }*/ 

                      User user2= userRepo.findByEmail(user.getEmail()).orElse(null);
                      if(user2==null)
                      {
                           userRepo.save(user);
                      }
                     
                  new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");

            }

        
}
