/**
 * 
 */
package com.eos.security.impl.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Role user relation entity.
 * 
 * @author santos.fabiano
 * 
 */
@Entity(name = "EOSRoleUser")
@Table(name = "tbroleuser", uniqueConstraints = { @UniqueConstraint(columnNames = {
		"roleid", "userid" }) })
@NamedQueries({ @NamedQuery(name = EOSRoleUserEntity.QUERY_FIND, query = "SELECT t FROM EOSRoleUser t "
		+ "WHERE t.userId = :userId AND t.roleId = :roleId AND t.tenantId = :tenantId") })
public class EOSRoleUserEntity extends AbstractTenantEntity {

	private static final long serialVersionUID = 6787741943234171944L;

	public static final String QUERY_FIND = "EOSRoleUser.FindByUserAndRole";

	public static final String PARAM_USER = "userId";
	public static final String PARAM_ROLE = "roleId";

	@Column(name = "userid", insertable = true, nullable = false, updatable = false)
	private Long userId;

	@ManyToOne(optional = false)
	@JoinColumn(name = "userid", referencedColumnName = "id", insertable = false, nullable = false, updatable = false)
	private EOSUserTenantEntity user;

	@Column(name = "roleid", insertable = true, nullable = false, updatable = false)
	private Long roleId;

	@ManyToOne(optional = false)
	@JoinColumn(name = "roleid", referencedColumnName = "id", insertable = false, nullable = false, updatable = false)
	private EOSRoleEntity role;

	/**
	 * Default constructor.
	 */
	public EOSRoleUserEntity() {
		super();
	}

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public EOSRoleUserEntity setUserId(Long userId) {
		this.userId = userId;

		if (userId == null) {
			user = null;
		} else {
			user = new EOSUserTenantEntity(userId);
		}

		return this;
	}

	/**
	 * @return the user
	 */
	public EOSUserTenantEntity getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public EOSRoleUserEntity setUser(EOSUserTenantEntity user) {
		this.user = user;

		if (user == null) {
			userId = null;
		} else {
			userId = user.getId();
		}

		return this;
	}

	/**
	 * @return the roleId
	 */
	public Long getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId
	 *            the roleId to set
	 */
	public EOSRoleUserEntity setRoleId(Long roleId) {
		this.roleId = roleId;

		if (roleId == null) {
			role = null;
		} else {
			role = new EOSRoleEntity(roleId);
		}

		return this;
	}

	/**
	 * @return the role
	 */
	public EOSRoleEntity getRole() {
		return role;
	}

	/**
	 * @param role
	 *            the role to set
	 */
	public EOSRoleUserEntity setRole(EOSRoleEntity role) {
		this.role = role;

		if (role == null) {
			roleId = null;
		} else {
			roleId = role.getId();
		}

		return this;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result + ((roleId == null) ? 0 : roleId.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		EOSRoleUserEntity other = (EOSRoleUserEntity) obj;
		if (role == null) {
			if (other.role != null)
				return false;
		} else if (!role.equals(other.role))
			return false;
		if (roleId == null) {
			if (other.roleId != null)
				return false;
		} else if (!roleId.equals(other.roleId))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "EOSRoleUserEntity [userId=" + userId + ", roleId=" + roleId
				+ ", getId()=" + getId() + ", getTenantId()=" + getTenantId()
				+ "]";
	}

}
