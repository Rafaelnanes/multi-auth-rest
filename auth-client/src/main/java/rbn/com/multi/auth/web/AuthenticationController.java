package rbn.com.multi.auth.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rbn.com.multi.auth.model.JwtRequest;
import rbn.com.multi.auth.model.JwtResponse;
import rbn.com.multi.auth.service.JwtTokenService;
import rbn.com.multi.auth.service.JwtUserDetailsService;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

	@Autowired
	private JwtTokenService jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@PostMapping(value = "/token")
	public JwtResponse getToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);

		return new JwtResponse(token);
	}
}