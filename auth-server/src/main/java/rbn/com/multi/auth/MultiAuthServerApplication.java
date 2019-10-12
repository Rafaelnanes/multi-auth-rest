package rbn.com.multi.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

@SpringBootApplication
@EnableAuthorizationServer
public class MultiAuthServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MultiAuthServerApplication.class, args);
	}

}
