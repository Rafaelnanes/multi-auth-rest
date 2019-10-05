package rbn.com.multi.auth.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum UserAuthorizationType {

	ROLE_ADMIN("ROLE_ADMIN"), ROLE_VISITOR("ROLE_VISITOR"), ROLE_CUSTOMER("ROLE_CUSTOMER");

	private final String value;

	private UserAuthorizationType(final String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}

	public static List<String> getStringValues() {
		return Arrays.stream(values()).map(x -> x.value).collect(Collectors.toList());
	}

}