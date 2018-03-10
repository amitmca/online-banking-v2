package com.userfront.config;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.userfront.service.UserServiceImpl.UserDetailsServiceImpl;

@Configuration // to let String knows this is a config file
@EnableWebSecurity // to enable Spring security
public class SecurityConfig extends WebSecurityConfigurerAdapter{ // extend to make some security config

	@Autowired
	private Environment env;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	private static final String SALT ="salt"; // used to encrypt the password
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder(12, new SecureRandom(SALT.getBytes()));
	}
	
	private static final String[] PUBLIC_MATCHERS ={ //list of path that will be accessed publicly, no security needed
		"/webjars/**",
		"/css/**",
		"/js/**",
		"/images/**",
		"/",
		"/about/**",
		"/contact/**",
		"/error/**/*",
		"/console/**",
		"/signup"
	};
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{ //override default configure method in WebSecurityConfigurerAdapter class
		
		http.
			authorizeRequests().
			//antMatchers("/**").
			antMatchers(PUBLIC_MATCHERS).
			permitAll().
			anyRequest().
			authenticated();
		
		http
			.csrf().disable().cors().disable()
			.formLogin().failureUrl("/index?error").defaultSuccessUrl("/userFront").loginPage("/index").permitAll()
			.and()
			.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/index?logout").deleteCookies("remember-me").permitAll()
			.and()
			.rememberMe();
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		//auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");
		auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder());
	}
}
