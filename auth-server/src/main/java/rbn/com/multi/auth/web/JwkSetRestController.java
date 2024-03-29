package rbn.com.multi.auth.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusds.jose.jwk.JWKSet;

@RestController
@RequestMapping("/key-set")
public class JwkSetRestController {

	@Autowired
	private JWKSet jwkSet;

	@GetMapping("/.well-known/auth-server-sign-jwt.json")
	public Map<String, Object> keys() {
		return this.jwkSet.toJSONObject();
	}

}