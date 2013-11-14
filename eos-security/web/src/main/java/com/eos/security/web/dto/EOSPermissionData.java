/**
 * 
 */
package com.eos.security.web.dto;

import java.io.Serializable;

/**
 * @author santos.fabiano
 * 
 */
public class EOSPermissionData implements Serializable {

	private static final long serialVersionUID = -4455823072886225790L;

	private String permission;
	private String login;
	private Boolean hasPermission;

	/**
	 * Default constructor.
	 */
	public EOSPermissionData() {
		super();
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
	public EOSPermissionData setPermission(String permission) {
		this.permission = permission;
		return this;
	}

	/**
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * @param login
	 *            the login to set
	 */
	public EOSPermissionData setLogin(String login) {
		this.login = login;
		return this;
	}

	/**
	 * @return the hasPermission
	 */
	public Boolean hasPermission() {
		return hasPermission;
	}

	/**
	 * @param hasPermission
	 *            the hasPermission to set
	 */
	public EOSPermissionData setHasPermission(Boolean hasPermission) {
		this.hasPermission = hasPermission;
		return this;
	}

}
