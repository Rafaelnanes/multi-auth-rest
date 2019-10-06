package rbn.com.multi.auth.web;

import java.security.Principal;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class MyResourcesController {

	@GetMapping("/user/me")
	public Principal user(Principal principal) {
		return principal;
	}

	@GetMapping("/any")
	public String getForAnyUser() {
		return "You got this, but you are not authenticated";
	}

	@PreAuthorize(value = "#oauth2.hasScope('read')")
	@GetMapping("/read")
	public String getReadScope() {
		return "You got the read scope";
	}

}
