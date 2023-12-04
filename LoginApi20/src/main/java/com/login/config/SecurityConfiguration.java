package com.login.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;
import org.springframework.security.web.SecurityFilterChain;

import com.login.api.jwt.JWTConfigurer;
import com.login.api.jwt.TokenProvider;
import com.login.api.security.CustomUserDetailsService;

@Configuration
public class SecurityConfiguration {

	@Autowired
    private Http401UnauthorizedEntryPoint authenticationEntryPoint;
	
//	@Autowired
//	private IpValidationFilter ipValidationFilter;
	
	@Autowired
	private TokenProvider tokenProvider;

	@Bean
	public UserDetailsService userDetailsService() {
		return new CustomUserDetailsService();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception {

	http.exceptionHandling()
        .authenticationEntryPoint(authenticationEntryPoint)
        .and()
        .csrf().disable()
        .headers().frameOptions().disable()
        .and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
    //  .addFilterBefore(ipValidationFilter, BasicAuthenticationFilter.class)
        .authorizeRequests()
        .antMatchers("/loginListener/**").permitAll()
        .antMatchers("/h2-console/**", "/chat", "/loginListener", "/topic").permitAll()
        .antMatchers("/api/**").authenticated()
        .antMatchers("/auth/**").permitAll()
        .antMatchers("/").permitAll()
        .and()
        .apply(securityConfigurerAdapter());

		return http.build();

	}

	private JWTConfigurer securityConfigurerAdapter() {
		return new JWTConfigurer(tokenProvider);
	}

    @Bean
    public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
        return new SecurityEvaluationContextExtension();
    }
	 
}
