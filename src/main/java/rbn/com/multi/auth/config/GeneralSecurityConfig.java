package rbn.com.multi.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class GeneralSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http //
				.csrf().disable()//
				.authorizeRequests()//
				.antMatchers("/api/v1/form")//
				.authenticated()//
				.and()//
				.formLogin()//
				.usernameParameter("user")//
				.passwordParameter("pass")//
				.loginProcessingUrl("/api/form")//
				.defaultSuccessUrl("/login-success", false);
		http//
				.authorizeRequests()//
				.antMatchers("/api/unauthorized")//
				.permitAll();

		http.authorizeRequests()//
				.antMatchers("/api/v1/role/basic", "/api/v1/role/admin", "/api/v1/customer")//
				.authenticated()//
				.and()//
				.httpBasic();

	}

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}