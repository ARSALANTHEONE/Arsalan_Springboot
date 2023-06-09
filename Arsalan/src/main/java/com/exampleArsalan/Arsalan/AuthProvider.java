package com.exampleArsalan.Arsalan;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component 
public class AuthProvider implements AuthenticationProvider { 
	 Logger logger = LoggerFactory.getLogger(AuthProvider.class);
	 
	 
   @Autowired
	DatabaseConnection db;
   @Autowired 
   private SecurityUserDetailsService userDetailsService; 
   @Autowired 
   private PasswordEncoder passwordEncoder; 


   @Override 
   public Authentication authenticate(Authentication authentication)  throws AuthenticationException { 
	
	   String username = authentication.getName();
       String password = authentication.getCredentials().toString();   
       Login user = db.getUser(username);
       if (user == null) {
    	   logger.error("Authentication failed");
           throw new BadCredentialsException("Details not found");
       }
       if (passwordEncoder.matches(password, user.getPassword())) {
           return new UsernamePasswordAuthenticationToken(username, password, null);
       } else {
           throw new BadCredentialsException("Password mismatch");
       }
   
   } 
  
   @Override 
   public boolean supports(Class<?> authentication) { 
      return true; 
   }
}

