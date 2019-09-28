package rbn.com.multi.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(
		  prePostEnabled = true)
public class ApplicationConfig extends WebSecurityConfigurerAdapter {

	private static final String ROLE_CUSTOMER = "CUSTOMER";

	private static final String ROLE_ADMIN = "ADMIN";

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
//			.antMatcher("/api/form")
//			.addFilterBefore(filter, beforeFilter)
//			.authenticationProvider(authenticationProvider)
			.authorizeRequests()
				.antMatchers("/api/form")
				.authenticated()
				.and()
				.formLogin()
					.usernameParameter("user")
					.passwordParameter("pass")
				.loginProcessingUrl("/api/form")
				.defaultSuccessUrl("/login-success", true);
		http
			.authorizeRequests()
				.antMatchers("/api/unauthorized")
				.permitAll();
			
		http
			.authorizeRequests()
				.antMatchers("/api/basic")
					.authenticated()
				.antMatchers("/api/admin")
					.hasRole(ROLE_ADMIN)
				.antMatchers("/api/customer")
					.hasRole(ROLE_CUSTOMER)
				.and()
				.httpBasic();
		
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(new MyAuthenticationProvider());
		inMemoryAuthentication(auth);
	}

	private void inMemoryAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		String password = bCryptPasswordEncoder.encode("123");
		auth.inMemoryAuthentication()
			.passwordEncoder(bCryptPasswordEncoder)
			.withUser("admin")
				.password(password)
				.roles(ROLE_ADMIN)
			.and()
			.withUser("customer1")
				.password(password)
				.roles(ROLE_CUSTOMER)
			.and()
				.withUser("customer2")
				.password(password)
				.accountExpired(true)
				.roles(ROLE_CUSTOMER);
	}

}