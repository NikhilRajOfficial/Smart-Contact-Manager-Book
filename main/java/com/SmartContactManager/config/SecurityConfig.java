package com.SmartContactManager.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.SmartContactManager.services.implementation.SecurityCustomeUserDetailService;

@Configuration
public class SecurityConfig {
        
       @Autowired
       private AuthFailureHandler authFailureHandler;

      @Autowired
      private OAuthenticationSuccessHandler handler;

     @Autowired
    private SecurityCustomeUserDetailService userDetailService;

    // // Creating user and login  using java code 
    // @Bean
    // public UserDetailsService userDetailsService()
    // {

    //      UserDetails userDetails = User
    //      .withDefaultPasswordEncoder()
    //      .username("admin123")
    //      .password("nike@321")
    //      .roles("ADMIN" , "USER")
    //      .build();

    //   var inMemoryUserDetailsManager =new InMemoryUserDetailsManager(userDetails);
    //   return inMemoryUserDetailsManager;
    // }


       // Configuration of Authentication provider for spring security 

       @Bean
       public DaoAuthenticationProvider authenticationProvider()
       {
           DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
           /// user details object
            daoAuthenticationProvider.setUserDetailsService(userDetailService);

            // password encoder object
            daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
            
           return daoAuthenticationProvider;
       }


           @Bean
           public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception
           {
                 // configuration
                 
                  httpSecurity.authorizeHttpRequests(authorize -> {
                //          authorize.requestMatchers("/home" , "/login" , "/sign").permitAll();
                            authorize.requestMatchers("/user/**").authenticated();
                            authorize.anyRequest().permitAll();
                 });


                 //Form default login 
                  httpSecurity.formLogin(formLogin -> {
                        // for customize login 
                        formLogin.loginPage("/login");
                        formLogin.loginProcessingUrl("/authenticate");
                        formLogin.successForwardUrl("/user/profile");
                        //formLogin.failureForwardUrl("/login?errrer=true");
                        formLogin.usernameParameter("email");
                        formLogin.passwordParameter("password");
                       // formLogin.failureHandler(new AuthenticationFailureHandler() {

                        //       @Override
                        //       public void onAuthenticationFailure(HttpServletRequest request,
                        //                   HttpServletResponse response, AuthenticationException exception)
                        //                   throws IOException, ServletException {
                        //             // TODO Auto-generated method stub
                        //             throw new UnsupportedOperationException("Unimplemented method 'onAuthenticationFailure'");
                        //       }
                              
                        // });

                        // formLogin.successHandler(new AuthenticationSuccessHandler() {

                        //       @Override
                        //       public void onAuthenticationSuccess(HttpServletRequest request,
                        //                   HttpServletResponse response, Authentication authentication)
                        //                   throws IOException, ServletException {
                        //             // TODO Auto-generated method stub
                        //             throw new UnsupportedOperationException("Unimplemented method 'onAuthenticationSuccess'");
                        //       }
                              
                        // });

                        formLogin.failureHandler(authFailureHandler);

                          
                  });

                      httpSecurity.csrf(AbstractHttpConfigurer::disable);
                      httpSecurity.logout(logoutForm -> {
                             logoutForm.logoutUrl("/logout");
                             logoutForm.logoutSuccessUrl("/login?logout=true");
                      });


                      // OAuth Configuration 

                      httpSecurity.oauth2Login(oauth->{
                          oauth.loginPage("/login");
                          oauth.successHandler(handler);
                      });

                 return httpSecurity.build();
           }


         @Bean
         public PasswordEncoder passwordEncoder()
         {
               return new BCryptPasswordEncoder();
         }
}
