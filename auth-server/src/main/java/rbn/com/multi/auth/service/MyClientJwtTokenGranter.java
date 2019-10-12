package rbn.com.multi.auth.service;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import io.jsonwebtoken.Jwts;

public class MyClientJwtTokenGranter extends AbstractTokenGranter {

	private String customJwtClientSecret;

	public MyClientJwtTokenGranter(AuthorizationServerTokenServices tokenServices,
			ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, String grantType,
			String customJwtClientSecret) {
		super(tokenServices, clientDetailsService, requestFactory, grantType);
		this.customJwtClientSecret = customJwtClientSecret;
	}

	@Override
	protected OAuth2AccessToken getAccessToken(ClientDetails client, TokenRequest tokenRequest) {
		assert tokenRequest.getRequestParameters().containsKey("token");

		String token = tokenRequest.getRequestParameters().get("token");

		Jwts.parser().setSigningKey(customJwtClientSecret).parse(token).getBody();
		return super.getAccessToken(client, tokenRequest);
	}

}
