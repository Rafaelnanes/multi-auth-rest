package rbn.com.multi.auth.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rbn.com.multi.auth.model.entity.User;
import rbn.com.multi.auth.repository.UserRepository;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@GetMapping
	public Iterable<User> getAll() throws Exception {
		return userRepository.findAll();
	}

}