package rbn.com.multi.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

@Configuration
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private PasswordEncoder passwordEncoder;

	private AuthenticationManager authenticationManager;

	public AuthorizationServerConfig(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		this.authenticationManager = authenticationConfiguration.getAuthenticationManager();
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//		clients.jdbc(dataSource)//
		clients.inMemory()//
				.withClient("client-app-1")//
				.secret(passwordEncoder.encode("123"))//
				.redirectUris("http://localhost:8080")//
				.authorizedGrantTypes("password", "client_credentials", "authorization_code")//
				.scopes("read")//
				.autoApprove(true)//
				.and()//
				.withClient("resource-app-1")//
				.secret(passwordEncoder.encode("123"))//
				.resourceIds("my-resource-id1");
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.tokenKeyAccess("permitAll()")//
				.checkTokenAccess("isAuthenticated()")//
				.allowFormAuthenticationForClients();
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
		endpoints.authenticationManager(authenticationManager);
	}

}