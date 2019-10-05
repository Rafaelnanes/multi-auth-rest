package rbn.com.multi.auth.model.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import rbn.com.multi.auth.model.UserAuthorizationType;

@Entity
@Table(name = "USL_USER_LEVEL")
@AttributeOverrides({ @AttributeOverride(name = AbstractEntity.PK, column = @Column(name = UserLevel.PK)) })
public class UserLevel extends AbstractEntity<Long> implements GrantedAuthority {

	private static final long serialVersionUID = -4600840153957593563L;

	public static final String PK = "USL_ID";

	@ManyToOne
	@JoinColumn(name = User.PK, nullable = false)
	private User user;

	@Enumerated(EnumType.STRING)
	@Column(name = "USL_LEVEL")
	private UserAuthorizationType level;

	public UserLevel() {
	}

	public UserLevel(UserAuthorizationType level, User usuario) {
		this.level = level;
		this.user = usuario;
	}

	@Override
	public String getAuthority() {
		return level.toString();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public UserAuthorizationType getLevel() {
		return level;
	}

	public void setLevel(UserAuthorizationType level) {
		this.level = level;
	}

}
