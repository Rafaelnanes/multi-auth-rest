package rbn.com.multi.auth.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;

import rbn.com.multi.auth.authentication.InternalAuthenticationManager;
import rbn.com.multi.auth.token.MyClientJwtTokenGranter;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private TokenStore tokenStore;

	@Autowired
	private TokenEnhancer tokenEnhancer;

	@Autowired
	private ClientDetailsService clientDetailsService;

	@Value("${jwt.client.secret}")
	private String customJwtClientSecret;

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		InternalAuthenticationManager authenticationManager = new InternalAuthenticationManager(clientDetailsService,
				getPasswordEncoder());
		endpoints.tokenStore(tokenStore)//
				.tokenEnhancer(tokenEnhancer)//
				.tokenGranter(tokenGranter(endpoints))//
				.authenticationManager(authenticationManager);
	}

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	private TokenGranter tokenGranter(final AuthorizationServerEndpointsConfigurer endpoints) {
		List<TokenGranter> granters = new ArrayList<TokenGranter>(Arrays.asList(endpoints.getTokenGranter()));
		granters.add(new MyClientJwtTokenGranter(endpoints.getTokenServices(), endpoints.getClientDetailsService(),
				endpoints.getOAuth2RequestFactory(), "client-jwt-custom", customJwtClientSecret));
		return new CompositeTokenGranter(granters);
	}

}