package rbn.com.multi.auth.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import rbn.com.multi.auth.ApplicationConstants;
import rbn.com.multi.auth.model.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	private List<User> users;

	@PostConstruct
	public void init() {
		users = new ArrayList<>();
		String password = passwordEncoder.encode("123");
		createUser(users, "admin", password, ApplicationConstants.ROLE_ADMIN);
		createUser(users, "customer1", password, ApplicationConstants.ROLE_CUSTOMER);
		createUser(users, "customer2", password, ApplicationConstants.ROLE_CUSTOMER);
	}

	@Override
	public User loadUser(User user) {
		return users.stream()//
				.filter(x -> x.getUsername().equals(user.getUsername()))//
				.filter(x -> StringUtils.hasText(user.getPassword())
						&& passwordEncoder.matches(user.getPassword(), x.getPassword()))//
				.findFirst()//
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
	}

	private void createUser(List<User> users, String username, String password, String authority) {
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setAuthorities(Arrays.asList(() -> authority));
		users.add(user);
	}

}
