package rbn.com.multi.auth.config;

import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.Collections;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;

import rbn.com.multi.auth.service.InternalJwkAccessTokenConverter;

@Configuration
@EnableAuthorizationServer
public class TokenServerConfig {

	private static final String KEY_STORE_FILE = "keys/auth-server-sign-jwt.jks";
	private static final String KEY_STORE_PASSWORD = "rafael-pass";
	private static final String KEY_ALIAS = "auth-server-sign-jwt";
	private static final String JWK_KID = "sign-key-id";

	@Bean
	public TokenStore tokenStore(JwtAccessTokenConverter jwtAccessTokenConverter) {
		return new JwtTokenStore(jwtAccessTokenConverter);
	}

	@Bean
	public InternalJwkAccessTokenConverter accessTokenConverter() {
		Map<String, String> customHeaders = Collections.singletonMap("kid", JWK_KID);
		return new InternalJwkAccessTokenConverter(customHeaders, keyPair());
	}

	@Bean
	public KeyPair keyPair() {
		ClassPathResource ksFile = new ClassPathResource(KEY_STORE_FILE);
		KeyStoreKeyFactory ksFactory = new KeyStoreKeyFactory(ksFile, KEY_STORE_PASSWORD.toCharArray());
		return ksFactory.getKeyPair(KEY_ALIAS);
	}

	@Bean
	public JWKSet jwkSet() {
		RSAKey.Builder builder = new RSAKey.Builder((RSAPublicKey) keyPair().getPublic()).keyUse(KeyUse.SIGNATURE)
				.algorithm(JWSAlgorithm.RS256).keyID(JWK_KID);
		return new JWKSet(builder.build());
	}

}