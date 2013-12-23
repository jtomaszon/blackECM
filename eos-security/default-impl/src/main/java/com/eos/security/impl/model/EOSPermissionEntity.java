/**
 * 
 */
package com.eos.security.impl.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

import com.eos.commons.jpa.EntityFieldSizes;

/**
 * Role Permission entity.
 * 
 * @author santos.fabiano
 * 
 */
@Entity(name = "EOSPermission")
@Table(name = "tbpermission", uniqueConstraints = { @UniqueConstraint(columnNames = {
		"roleid", "permission", "tenantid" }) })
public class EOSPermissionEntity extends AbstractTenantEntity {

	private static final long serialVersionUID = -4228463366776986941L;

	@Column(name = "roleid", insertable = true, nullable = false, updatable = false)
	private Long roleId;

	@ManyToOne(optional = false)
	@JoinColumn(name = "roleid", referencedColumnName = "id", insertable = false, nullable = false, updatable = false)
	private EOSRoleEntity role;

	@Size(min = EntityFieldSizes.MINIMUM, max = EntityFieldSizes.DATA_SMALL)
	@Column(name = "permission", nullable = false, updatable = false)
	private String permission;

	/**
	 * Default constructor.
	 */
	public EOSPermissionEntity() {
		super();
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
	public EOSPermissionEntity setRoleId(Long roleId) {
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
	public EOSPermissionEntity setRole(EOSRoleEntity role) {
		this.role = role;

		if (role == null) {
			roleId = null;
		} else {
			roleId = role.getId();
		}

		return this;
	}

	/**
	 * @return the permission
	 */
	public String getPermission() {
		return permission;
	}

	/**
	 * @param permission
	 *            the permission to set
	 */
	public EOSPermissionEntity setPermission(String permission) {
		this.permission = permission;
		return this;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((permission == null) ? 0 : permission.hashCode());
		result = prime * result + ((roleId == null) ? 0 : roleId.hashCode());
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
		EOSPermissionEntity other = (EOSPermissionEntity) obj;
		if (permission == null) {
			if (other.permission != null)
				return false;
		} else if (!permission.equals(other.permission))
			return false;
		if (roleId == null) {
			if (other.roleId != null)
				return false;
		} else if (!roleId.equals(other.roleId))
			return false;
		return true;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "EOSPermissionEntity [roleId=" + roleId + ", permission="
				+ permission + ", getId()=" + getId() + ", getTenantId()="
				+ getTenantId() + "]";
	}

}
