package rbn.com.multi.auth.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import rbn.com.multi.auth.model.InternalUser;

public class InternalUserDetailsService implements UserDetailsService {

	private Logger logger = LoggerFactory.getLogger(InternalUserDetailsService.class);

	private PasswordEncoder passwordEncoder;

	private List<InternalUser> users;

	public InternalUserDetailsService(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
		init();
	}

	public void init() {
		users = new ArrayList<>();
		String password = passwordEncoder.encode("123");
		users.add(new InternalUser("admin", password, Arrays.asList("admin")));
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		InternalUser internalUser = users.stream()//
				.peek(x -> logger.info("Checking User id: {}", x.getUsername()))//
				.filter(x -> x.getUsername().equals(username))//
				.findFirst()//
				.get();
		return internalUser;

	}

}
