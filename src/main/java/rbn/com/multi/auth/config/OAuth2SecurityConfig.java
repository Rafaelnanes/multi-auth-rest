package rbn.com.multi.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@Order(Ordered.LOWEST_PRECEDENCE)
public class OAuth2SecurityConfig extends WebSecurityConfigurerAdapter {

	public static final String RESOURCE_ID = "myAuthorizationServer";

//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.requestMatchers()//
//				.antMatchers("/login", "/oauth/authorize")//
//				.and()//
//				.authorizeRequests()//
//				.anyRequest()//
//				.authenticated()//
//				.and()//
//				.oauth2Login();
//	}

	@EnableResourceServer
	protected static class OAuth2ResourceServer extends ResourceServerConfigurerAdapter {

		@Autowired
		private AuthenticationProvider authenticationProvider;

		@Override
		public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
			resources.resourceId(RESOURCE_ID);
//			.authenticationManager(authenticationManager)
//			resources.authenticationManager(authenticationManager)
		}

		@Override
		public void configure(HttpSecurity http) throws Exception {
			http.authenticationProvider(authenticationProvider).antMatcher("/api/v2/**").authorizeRequests()
					.anyRequest().authenticated();
		}

	}

	@EnableAuthorizationServer
	protected static class OAuth2AuthorizationServer extends AuthorizationServerConfigurerAdapter {

		@Override
		public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
			clients.inMemory()//
					.withClient("cliente-app-id")//
					.secret("123456")//
					.redirectUris("http://localhost:8080/any-redirect-uri")//
					.authorizedGrantTypes("authorization_code")//
					.scopes("scope.read")//
					.autoApprove(true)//
					.resourceIds(RESOURCE_ID);
		}

	}

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
