package rbn.com.multi.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import rbn.com.multi.auth.ApplicationConstants;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class GeneralSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http //
				.csrf().disable()//
//			.antMatcher("/api/form")
//			.addFilterBefore(filter, beforeFilter)
//			.authenticationProvider(authenticationProvider)
				.authorizeRequests()//
				.antMatchers("/api/form")//
				.authenticated()//
				.and()//
				.formLogin()//
				.usernameParameter("user")//
				.passwordParameter("pass")//
				.loginProcessingUrl("/api/form")//
				.defaultSuccessUrl("/login-success", true);
		http//
				.authorizeRequests()//
				.antMatchers("/api/unauthorized")//
				.permitAll();

		http.authorizeRequests()//
				.antMatchers("/api/basic")//
				.authenticated()//
				.antMatchers("/api/admin")//
				.hasRole(ApplicationConstants.ROLE_ADMIN)//
				.antMatchers("/api/customer")//
				.hasRole(ApplicationConstants.ROLE_CUSTOMER)//
				.and()//
				.httpBasic();

	}

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}