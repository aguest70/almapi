/**
 * 
 */
package de.almapi;

import de.gawky.properties.ApplicationProperty;

/**
 * @author Sebastian Kirchner
 * 
 */
public class ALMProperties {

	/**
	 * base URL of the HP ALM REST service
	 * 
	 * @see RestRepository
	 */
	public static final ApplicationProperty ALM_REST_BASE_URL = new ApplicationProperty(
			"alm_rest_base_url", "http://alm/qcbin/rest"); //$NON-NLS-1$

	/**
	 * URL of the HP ALM authentication service
	 * 
	 * @see ALMLogin#login(String; char[])
	 */
	public static final ApplicationProperty ALM_AUTH_SITE_URL = new ApplicationProperty(
			"alm_auth_site_url", "http://alm/qcbin/authentication-point/authenticate"); //$NON-NLS-1$

	/**
	 * ALM domain name
	 * 
	 * @see RestRepository
	 */
	public static final ApplicationProperty ALM_DOMAIN = new ApplicationProperty("alm_domain", "DOMAIN"); //$NON-NLS-1$

	/**
	 * ALM project name
	 * 
	 * @see RestRepository
	 */
	public static final ApplicationProperty ALM_PROJECT = new ApplicationProperty("alm_project", "PROJECT"); //$NON-NLS-1$

}
