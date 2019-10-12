package rbn.com.multi.auth.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

import rbn.com.multi.auth.service.model.InternalClient;

@Service
@Primary
public class ClientUserService implements ClientDetailsService {

	private Logger logger = LoggerFactory.getLogger(ClientUserService.class);

	private List<InternalClient> clients;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostConstruct
	public void init() {
		clients = new ArrayList<>();
		HashSet<String> scope = new HashSet<String>(Arrays.asList("read", "write"));
		HashSet<String> scopeReadToken = new HashSet<String>(Arrays.asList("read", "write", "read-token"));
		HashSet<String> grantTypesResourceApp = new HashSet<String>(
				Arrays.asList("client_credentials", "authorization_code"));
		HashSet<String> grantTypesClientApp = new HashSet<String>(
				Arrays.asList("client_credentials", "authorization_code", "client-jwt-custom"));
		String password = passwordEncoder.encode("123");
//		String password = "123";

		List<String> authoritiesClient = Arrays.asList("client");
		clients.add(new InternalClient("client-app-1", password, scope, authoritiesClient, grantTypesClientApp));

		List<String> authoritiesResource = Arrays.asList("resource");
		clients.add(new InternalClient("resource-app-1", password, scopeReadToken, authoritiesResource,
				grantTypesResourceApp));
	}

	@Override
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
		return clients.stream()//
				.peek(x -> logger.info("Checking client id: {}", x.getClientId()))//
				.filter(x -> x.getClientId().equals(clientId))//
				.findFirst()//
				.get();
	}

}
