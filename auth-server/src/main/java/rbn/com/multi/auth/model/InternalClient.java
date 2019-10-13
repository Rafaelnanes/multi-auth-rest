package rbn.com.multi.auth.model;

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

	private List<String> authorities;

	private Set<String> authorizedGrantTypes;

	private Set<String> resourceIds;

	private Set<String> redirectUri;

	public InternalClient(String clientId, String clientSecret, Set<String> scope, List<String> authorities,
			Set<String> authorizedGrantTypes, Set<String> resourceIds, Set<String> redirectUri) {
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.scope = scope;
		this.authorities = authorities;
		this.authorizedGrantTypes = authorizedGrantTypes;
		this.resourceIds = resourceIds;
		this.redirectUri = redirectUri;
	}

	@Override
	public String getClientId() {
		return clientId;
	}

	@Override
	public Set<String> getResourceIds() {
		return resourceIds;
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
		return true;
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
		return redirectUri;
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return authorities.stream().map(x -> new SimpleGrantedAuthority(x)).collect(Collectors.toList());
	}

	@Override
	public Integer getAccessTokenValiditySeconds() {
		return 120;
	}

	@Override
	public Integer getRefreshTokenValiditySeconds() {
		return 120;
	}

	@Override
	public boolean isAutoApprove(String scope) {
		return true;
	}

	@Override
	public Map<String, Object> getAdditionalInformation() {
		return null;
	}

}
