package rbn.com.multi.auth.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

import rbn.com.multi.auth.service.model.InternalClient;

@Service
@Order(0)
@Primary
public class ClientUserService implements ClientDetailsService {

	private Logger logger = LoggerFactory.getLogger(ClientUserService.class);

	private List<InternalClient> clients;

	@PostConstruct
	public void init() {
		clients = new ArrayList<>();
		HashSet<String> scope = new HashSet<String>(Arrays.asList("read", "write"));
		HashSet<String> grantTypes = new HashSet<String>(Arrays.asList("client_credentials", "authorization_code"));
		clients.add(new InternalClient("client-app-1", "123", scope, Arrays.asList("client"), grantTypes));
		clients.add(new InternalClient("resource-app-1", "123", scope, Arrays.asList("resource"), grantTypes));
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
