package rbn.com.multi.auth.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.ObjectUtils;

import rbn.com.multi.auth.model.User;
import rbn.com.multi.auth.service.UserService;

//@Component
public class InternalAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private UserService userService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		User user = new User();
		if (!ObjectUtils.isEmpty(authentication.getPrincipal())) {
			user.setUsername(authentication.getPrincipal().toString());
		}

		if (!ObjectUtils.isEmpty(authentication.getCredentials())) {
			user.setPassword(authentication.getCredentials().toString());
		}
		User loadUser = userService.loadUser(user);
		return authentication;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
	}

}
