/**
 * 
 */
package de.almapi;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.codec.binary.Base64;

/**
 * @author Sebastian Kirchner
 * 
 */
public class ALMLogin {

	/**
	 * @param user
	 *            the login user name
	 * @param pass
	 *            the login password
	 * 
	 * @return a {@link CookieStore} containing the necessary session cookies to
	 *         proceed. In case the login went wrong a {@link LoginException} is
	 *         thrown.
	 * 
	 * @throws IOException
	 *             in case the connection to ALM could not be established for
	 *             some reason
	 * 
	 * @throws LoginException
	 *             if the login failed.
	 */
	public static CookieStore login(final String user, final char[] pass) throws IOException, LoginException {

		final CookieStore cookieStore = new CookieStore();

		// encode the credentials
		final String cred = "Basic " + new String(Base64.encodeBase64((user + ":" + new String(pass)).getBytes()));

		// authenticating
		final String authSiteURL = ALMProperties.ALM_AUTH_SITE_URL.getValue();
		final HttpURLConnection c2 = (HttpURLConnection) new URL(authSiteURL).openConnection();
		c2.setRequestProperty("Authorization", cred);
		c2.setRequestMethod("POST");
		c2.connect();
		c2.disconnect();
		if(c2.getResponseCode() != 200){
			throw new LoginException();
		}

		// collect the provided cookies
		cookieStore.put(c2.getHeaderFields().get("Set-Cookie"));
		return cookieStore;
	}

	/**
	 * An exception being thrown, in case the login to ALM could not be
	 * established.
	 * 
	 * @author Sebastian Kirchner
	 */
	public static class LoginException extends Exception {

		private static final long serialVersionUID = 1L;

		/**
		 * Default constructor.
		 */
		public LoginException() {
			super("Login failed. Please check you credetials."); //$NON-NLS-1$
		}

	}

}
