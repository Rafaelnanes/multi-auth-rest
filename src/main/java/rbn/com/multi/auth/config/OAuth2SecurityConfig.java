package rbn.com.multi.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
public class OAuth2SecurityConfig {

	public static final String RESOURCE_ID = "myAuthorizationServer";

	@EnableResourceServer
	protected static class OAuth2ResourceServer extends ResourceServerConfigurerAdapter {

		@Override
		public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
			resources.resourceId(RESOURCE_ID);
		}

		@Override
		public void configure(HttpSecurity http) throws Exception {
			http//
					.antMatcher("/api/v2/**")//
					.authorizeRequests()//
					.anyRequest()//
					.authenticated();
		}

	}

	@EnableAuthorizationServer
	protected static class OAuth2AuthorizationServer extends AuthorizationServerConfigurerAdapter {

		@Autowired
		private PasswordEncoder passwordEncoder;

		@Override
		public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
			String encode = passwordEncoder.encode("123456");
			clients.inMemory()//
					.withClient("cliente-app-id")//
					.secret(encode)//
					.redirectUris("http://localhost:8089")//
					.authorizedGrantTypes("authorization_code", "password", "implicit")//
					.scopes("read")//
					.autoApprove(true)//
					.and()//
					.withClient("cliente-app-id-admin")//
					.secret(encode)//
					.redirectUris("http://localhost:8089")//
					.authorizedGrantTypes("authorization_code", "password", "implicit")//
					.scopes("read-admin")//
					.autoApprove(true)//
					.resourceIds(RESOURCE_ID);
		}

	}

}
