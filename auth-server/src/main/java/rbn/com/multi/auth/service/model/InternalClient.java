package rbn.com.multi.auth.service.model;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

public class InternalClient implements ClientDetails {

	private static final long serialVersionUID = 4383028774760199396L;

	private String clientId;

	private String clientSecret;

	private Set<String> scope;

	private List<String> grantedAuthorities;

	private Set<String> authorizedGrantTypes;

	public InternalClient(String clientId, String clientSecret, Set<String> scope, List<String> grantedAuthorities,
			Set<String> authorizedGrantTypes) {
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.scope = scope;
		this.grantedAuthorities = grantedAuthorities;
		this.authorizedGrantTypes = authorizedGrantTypes;
	}

	@Override
	public String getClientId() {
		return clientId;
	}

	@Override
	public Set<String> getResourceIds() {
		return null;
	}

	@Override
	public boolean isSecretRequired() {
		return true;
	}

	@Override
	public String getClientSecret() {
		return clientSecret;
	}

	@Override
	public boolean isScoped() {
		return false;
	}

	@Override
	public Set<String> getScope() {
		return scope;
	}

	@Override
	public Set<String> getAuthorizedGrantTypes() {
		return authorizedGrantTypes;
	}

	@Override
	public Set<String> getRegisteredRedirectUri() {
		return null;
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return grantedAuthorities.stream().map(x -> new SimpleGrantedAuthority(x)).collect(Collectors.toList());
	}

	@Override
	public Integer getAccessTokenValiditySeconds() {
		return null;
	}

	@Override
	public Integer getRefreshTokenValiditySeconds() {
		return null;
	}

	@Override
	public boolean isAutoApprove(String scope) {
		return false;
	}

	@Override
	public Map<String, Object> getAdditionalInformation() {
		return null;
	}

}
