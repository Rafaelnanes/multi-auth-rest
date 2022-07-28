package rbn.com.multi.auth.service;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.util.JsonParser;
import org.springframework.security.oauth2.common.util.JsonParserFactory;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

public class InternalJwkAccessTokenConverter extends JwtAccessTokenConverter {

	private Map<String, String> customHeaders = new HashMap<>();
	private JsonParser objectMapper = JsonParserFactory.create();
	final RsaSigner signer;

	public InternalJwkAccessTokenConverter(Map<String, String> customHeaders, KeyPair keyPair) {
		super();
		super.setKeyPair(keyPair);
		this.signer = new RsaSigner((RSAPrivateKey) keyPair.getPrivate());
		this.customHeaders = customHeaders;
	}

	@Override
	protected String encode(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		String content;
		try {
			Map<String, ?> convertAccessToken = getAccessTokenConverter().convertAccessToken(accessToken, authentication);
			content = this.objectMapper
					.formatMap(convertAccessToken);
		} catch (Exception ex) {
			throw new IllegalStateException("Cannot convert access token to JSON", ex);
		}
		String token = JwtHelper.encode(content, this.signer, this.customHeaders).getEncoded();
		return token;
	}

}