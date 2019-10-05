package rbn.com.multi.auth.model.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "USR_USER")
@AttributeOverrides({ @AttributeOverride(name = AbstractEntity.PK, column = @Column(name = User.PK)) })
public class User extends AbstractEntity<Long> {

	private static final long serialVersionUID = 4120347212196628824L;

	public static final String PK = "USR_ID";

	@Column(name = "USR_USERNAME")
	private String username;

	@Column(name = "USR_PASSWORD")
	private String password;

	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	private Set<UserLevel> userLevels = new HashSet<UserLevel>();

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<UserLevel> getUserLevels() {
		return userLevels;
	}

	public void setUserLevels(Set<UserLevel> userLevels) {
		this.userLevels = userLevels;
	}

}