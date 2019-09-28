package rbn.com.multi.auth.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
public class ApiController {

	protected final Log logger = LogFactory.getLog(ApiController.class);

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/role/admin")
	public String admin() {
		return "You are the Admin";
	}

	@PreAuthorize("hasRole('CUSTOMER')")
	@GetMapping("/role/customer")
	public String customer() {
		return "You are the customer";
	}

	@GetMapping("/unauthorized")
	public String unauthorized() {
		return "Success unauthorized access";
	}

	@GetMapping("/basic")
	public String basic() {
		return "Success Basic Authentication access";
	}

}
