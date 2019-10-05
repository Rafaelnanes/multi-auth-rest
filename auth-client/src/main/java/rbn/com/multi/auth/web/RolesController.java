package rbn.com.multi.auth.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/role")
public class RolesController {

	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String getAdmin() throws Exception {
		return "You have admin role";
	}

	@GetMapping("/customer")
	@PreAuthorize("hasRole('CUSTOMER')")
	public String getCustomer() throws Exception {
		return "You have customer role";
	}

	@GetMapping("/visitor")
	public String getVisitor() throws Exception {
		return "You have visitor role";
	}

}