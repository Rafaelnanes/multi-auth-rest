package rbn.com.multi.auth.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client")
public class ClientController {

	@PreAuthorize(value = "#oauth2.hasScope('read')")
	@GetMapping("/read-only")
	public String readOnly() {
		return "This endpoint is just for read-only scope";
	}

	@PreAuthorize(value = "#oauth2.hasScope('write')")
	@GetMapping("/write-only")
	public String writeOnly() {
		return "This endpoint is just for write-only scope";
	}

}