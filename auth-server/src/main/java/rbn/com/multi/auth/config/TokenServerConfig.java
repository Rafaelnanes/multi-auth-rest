package rbn.com.multi.auth.config;

import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.Collections;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;

import rbn.com.multi.auth.token.InternalJwkAccessTokenConverter;

@Configuration
public class TokenServerConfig {

	private static final String KEY_STORE_FILE_SIGN = "keys/auth-server-sign-jwt.jks";
	private static final String KEY_ALIAS_SIGN = "auth-server-sign-jwt";
	private static final String KEY_STORE_PASSWORD = "rafael-pass";
	private static final String JWK_KID = "sign-key-id";

	private KeyPair signKeyPair;

	@PostConstruct
	public void init() {
		signKeyPair = getSigningKeyPair();
	}

	@Bean
	public TokenStore tokenStore(JwtAccessTokenConverter jwtAccessTokenConverter) {
		return new JwtTokenStore(jwtAccessTokenConverter);
	}

	@Bean
	public InternalJwkAccessTokenConverter accessTokenConverter() {
		Map<String, String> customHeaders = Collections.singletonMap("kid", JWK_KID);
		return new InternalJwkAccessTokenConverter(customHeaders, signKeyPair);
	}

	@Bean
	public JWKSet jwkSet() {
		RSAKey.Builder builder = new RSAKey.Builder((RSAPublicKey) signKeyPair.getPublic()).keyUse(KeyUse.SIGNATURE)
				.algorithm(JWSAlgorithm.RS256).keyID(JWK_KID);
		return new JWKSet(builder.build());
	}

	private KeyPair getSigningKeyPair() {
		ClassPathResource ksFile = new ClassPathResource(KEY_STORE_FILE_SIGN);
		KeyStoreKeyFactory ksFactory = new KeyStoreKeyFactory(ksFile, KEY_STORE_PASSWORD.toCharArray());
		return ksFactory.getKeyPair(KEY_ALIAS_SIGN);
	}

}