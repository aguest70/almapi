/**
 * 
 */
package de.almapi.connection;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import de.almapi.domain.IEntity;
import de.almapi.domain.impl.Entities;
import de.almapi.domain.impl.Entity;

/**
 * @author Sebastian Kirchner
 * 
 */
public class ALMConnection {

	/**
	 * Constant for the name of the requirement service
	 * 
	 * @see ALMConnection#getEntity(String, int)
	 * @see ALMConnection#getEntities(String, int)
	 */
	public static final String REQUIREMENTS_SERVICE_NAME = "requirements";
	/**
	 * Constant for the name of the defect service
	 * 
	 * @see ALMConnection#getEntity(String, int)
	 * @see ALMConnection#getEntities(String, int)
	 */
	public static final String DEFECTS_SERVICE_NAME = "defects";
	/**
	 * Constant for the name of the release service
	 * 
	 * @see ALMConnection#getEntity(String, int)
	 * @see ALMConnection#getEntities(String, int)
	 */
	public static final String RELEASES_SERVICE_NAME = "releases";

	private static final Logger LOG = Logger.getLogger(ALMConnection.class.getName());
	/*
	 * eg.: http://alm11.wob.vw.vwg/qcbin/rest/domains/VW_DE_PROD/projects/MEDUSA_RD/
	 */
	private static final String ALM_REST_SERVICE_URL = ALMProperties.ALM_REST_BASE_URL.getValue() + "/domains/"
			+ ALMProperties.ALM_DOMAIN.getValue() + "/projects/" + ALMProperties.ALM_PROJECT.getValue() + "/";
	/*
	 * The cookie store
	 */
	private CookieStore loginCookies = new CookieStore();

	private boolean connected = false;

	public boolean isConnected() {
		return this.connected;
	}

	/**
	 * TODO
	 * 
	 * <p>
	 * Using a default page size of 20.
	 * </p>
	 * 
	 * @param serviceName
	 * @return
	 * 
	 * @see ALMConnection#getEntities(String, int);
	 */
	public List<IEntity> getEntities(final String serviceName) {
		return this.getEntities(serviceName, 20);
	}

	/**
	 * TODO
	 * 
	 * @param serviceName
	 * @param pageSize
	 * @return
	 */
	public List<IEntity> getEntities(final String serviceName, final int pageSize) {

		int startIndex = 1;
		int resultCount = -1;
		final LinkedList<IEntity> result = new LinkedList<IEntity>();

		do{
			final String url = ALM_REST_SERVICE_URL + serviceName + "?page-size=" + pageSize + "&start-index="
					+ startIndex;
			final ClientResponse response = request(url);
			if(response == null){
				// TODO log warn
				return result;
			}
			final Entities entities = response.getEntity(Entities.class);
			resultCount = entities.getTotalResults();
			result.addAll(entities);
			startIndex = startIndex + pageSize;

		}while(resultCount == pageSize);
		return result;
	}

	/**
	 * @param serviceName
	 *            name of the rest service that should provide the {@link Entity}. E.g.:
	 *            "requirements" or "defects"
	 * @param id
	 *            the id of the requested entity
	 * @return the {@link Entity} with the given id or <code>null</code>, if the request
	 *         fails.
	 * 
	 * @see ALMConnection#REQUIREMENTS_SERVICE_NAME
	 * @see ALMConnection#DEFECTS_SERVICE_NAME
	 */
	public IEntity getEntity(final String serviceName, final int id) {
		final String url = ALM_REST_SERVICE_URL + serviceName + "/" + id;
		final ClientResponse response = request(url);
		if(response == null){
			return null;
		}
		return response.getEntity(Entity.class);
	}

	/**
	 * Login to ALM with user name and password via ALM login service.
	 * 
	 * @param username
	 *            the user name to log on
	 * @param password
	 *            the password of the user
	 * 
	 * @see ALMProperties#ALM_AUTH_SITE_URL
	 */
	public void login(final String username, final char[] password) {
		try{
			loginCookies = ALMLogin.login(username, password);
			this.connected = true;
		}catch(IOException e){
			LOG.log(Level.WARNING, "Login failed! No connection to the HP ALM login service.", e);
		}catch(de.almapi.connection.ALMLogin.LoginException e){
			LOG.log(Level.WARNING, "Login failed! Please check the provided credentials.", e);
		}
	}

	/**
	 * TODO Documentation
	 * 
	 * @param url
	 *            the rest service URL to request
	 * @return the resulting {@link ClientResponse}. If the request fails (HTTP response code NOT
	 *         200) <code>null</code> will be returned.
	 */
	private ClientResponse request(final String url) {
		// Set the cookies
		if(loginCookies == null){
			// TODO throw a real exception here
			throw new RuntimeException("Please login first");
		}
		// build the request
		final DefaultClientConfig cc = new DefaultClientConfig();
		final Client client = Client.create(cc);
		final WebResource resource = client.resource(url);
		final Builder builder = resource.getRequestBuilder();
		builder.accept(MediaType.APPLICATION_XML);
		for(Cookie cookie : loginCookies.getCookies()){
			builder.cookie(cookie);
		}
		final ClientResponse response = builder.get(ClientResponse.class);
		if(response.getStatus() != 200){
			LOG.warning("Request failed! HTTP response code: " + response.getStatus());
		}
		return response;
	}

}
