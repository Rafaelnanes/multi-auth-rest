package rbn.com.multi.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

import rbn.com.multi.auth.service.MyTokenService;

@Configuration
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	// Internally, Resource Server uses an OAuth2RestTemplate to invoke the
	// /userinfo endpoint. At times, it may be necessary to add filters or perform
	// other customization for this invocation. To customize the creation of this
	// bean, you can expose a UserInfoRestTemplateCustomizer, like so:

//	@Bean
//	public UserInfoRestTemplateCustomizer customHeader() {
//		return restTemplate -> restTemplate.getInterceptors().add(new MyCustomInterceptor());
//	}

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		super.configure(resources);
		resources.resourceId("my-resource-id1");
	}

	@Bean
	public RemoteTokenServices getTokenService() {
		MyTokenService myTokenService = new MyTokenService();
		myTokenService.setCheckTokenEndpointUrl("http://localhost:8090/oauth/check_token");
		myTokenService.setClientId("resource-app-1");
		myTokenService.setClientSecret("123");
		return myTokenService;
	}

}
