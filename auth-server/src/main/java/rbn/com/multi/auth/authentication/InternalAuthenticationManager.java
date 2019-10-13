package rbn.com.multi.auth.authentication;

import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.util.Assert;

public class InternalAuthenticationManager implements AuthenticationManager {

	private ClientDetailsService clientDetailsService;

	private PasswordEncoder passwordEncoder;

	public InternalAuthenticationManager(ClientDetailsService clientDetailsService, PasswordEncoder passwordEncoder) {
		this.clientDetailsService = clientDetailsService;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String principal = (String) authentication.getPrincipal();
		String credentials = (String) authentication.getCredentials();
		Map<String, String> details = (Map<String, String>) authentication.getDetails();
		String grantType = details.get("grant_type");

		Assert.isTrue("password".equals(grantType), "The only grant type allowed is password");

		ClientDetails clientDetails = clientDetailsService.loadClientByClientId(principal);

		if (!passwordEncoder.matches(credentials, clientDetails.getClientSecret())) {
			throw new BadCredentialsException("Password does not match");
		}

		return new UsernamePasswordAuthenticationToken(principal, clientDetails.getClientSecret(),
				clientDetails.getAuthorities());
	}

}
