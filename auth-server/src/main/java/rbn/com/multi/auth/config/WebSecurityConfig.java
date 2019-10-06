package rbn.com.multi.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		String password = passwordEncoder.encode("123");
		auth.inMemoryAuthentication()//
				.withUser("admin")//
				.password(password)//
				.roles("ADMIN")//
				.and()//
				.withUser("admin2")//
				.password("123")//
				.roles("ADMIN");
	}

	// In case you need to do more specialized configuration of the
	// AuthenticationManager, you can do so in the WebSecurityConfigurerAdapter and
	// then expose it, as the following example shows:

//	@Bean(BeansId.AUTHENTICATION_MANAGER)
//	@Override
//	public AuthenticationManager authenticationManagerBean() {
//		return super.authenticationManagerBean();
//	}
//
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) {
//		auth.authenticationProvider(customAuthenticationProvider());
//	}

}