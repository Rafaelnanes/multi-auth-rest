package rbn.com.multi.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import rbn.com.multi.auth.service.InternalUserDetailsService;

@Configuration
@Order(1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.requestMatchers()//
				.antMatchers("/login", "/oauth/authorize")//
				.and()//
				.authorizeRequests()//
				.anyRequest()//
				.authenticated()//
				.and()//
				.userDetailsService(new InternalUserDetailsService(passwordEncoder)).formLogin()//
				.permitAll();
	}

}