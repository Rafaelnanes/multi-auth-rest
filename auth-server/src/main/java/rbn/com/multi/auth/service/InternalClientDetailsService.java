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

import rbn.com.multi.auth.model.InternalClient;

@Service
@Primary
public class InternalClientDetailsService implements ClientDetailsService {

	private Logger logger = LoggerFactory.getLogger(InternalClientDetailsService.class);

	private List<InternalClient> clients;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostConstruct
	public void init() {
		clients = new ArrayList<>();
		String password = passwordEncoder.encode("123");

		addClient(password);
		addResource(password);
	}

	@Override
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
		InternalClient internalClient = clients.stream()//
				.peek(x -> logger.info("Checking client id: {}", x.getClientId()))//
				.filter(x -> x.getClientId().equals(clientId))//
				.findFirst()//
				.get();
		return internalClient;
	}

	private void addResource(String password) {
		HashSet<String> redirectUri = new HashSet<String>(
				Arrays.asList("http://localhost:8089/authorization-code/success"));
		HashSet<String> resourcesIds = new HashSet<String>(Arrays.asList("authorization-server-id"));
		HashSet<String> grantTypesResourceApp = new HashSet<String>(
				Arrays.asList("client_credentials", "authorization_code", "password"));
		HashSet<String> scopeReadToken = new HashSet<String>(Arrays.asList("write"));
		List<String> authoritiesResource = Arrays.asList("resource");
		clients.add(new InternalClient("resource-app-1", password, scopeReadToken, authoritiesResource,
				grantTypesResourceApp, resourcesIds, redirectUri));
	}

	private void addClient(String password) {
		HashSet<String> resourcesIds = new HashSet<String>(
				Arrays.asList("authorization-server-id", "resource-server-id"));
		HashSet<String> grantTypesClientApp = new HashSet<String>(
				Arrays.asList("client_credentials", "authorization_code", "client-jwt-custom"));
		HashSet<String> scope = new HashSet<String>(Arrays.asList("read"));
		List<String> authoritiesClient = Arrays.asList("client");
		clients.add(new InternalClient("client-app-1", password, scope, authoritiesClient, grantTypesClientApp,
				resourcesIds, null));
	}

}
