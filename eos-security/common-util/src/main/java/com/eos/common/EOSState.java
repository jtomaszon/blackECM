/**
 * 
 */
package com.eos.common;

/**
 * Enumerator indicating an entity state.
 * 
 * @author santos.fabiano
 * 
 */
public enum EOSState {
	/**
	 * Entity not verified.
	 */
	INACTIVE,
	/**
	 * Entity active, already verified.
	 */
	ACTIVE,
	/**
	 * Entity removed, logical deletion
	 */
	REMOVED,
	/**
	 * Entity disabled, is active but is not accessible or cannot perform any
	 * action.
	 */
	DISABLED;
}
