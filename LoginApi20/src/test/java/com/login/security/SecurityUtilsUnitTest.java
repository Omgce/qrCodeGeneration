package com.login.security;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.login.api.security.SecurityUtils;

/**
* Test class for the SecurityUtils utility class.
*/
public class SecurityUtilsUnitTest {

    @Test
    public void getCurrentUserLogin()
    {
    	
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken("mromraj123@gmail.com", "123456"));
        SecurityContextHolder.setContext(securityContext);
        String login = SecurityUtils.getCurrentUserLogin();
        assertThat(login).isEqualTo("admin");
        
    }

    @Test
    public void isAuthenticated()
    {
    	
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken("mromraj123@gmail.com", "123456"));
        SecurityContextHolder.setContext(securityContext);
        boolean isAuthenticated = SecurityUtils.isAuthenticated();
        assertThat(isAuthenticated).isTrue();
        
    }

    
}
