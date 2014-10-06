/**
 * 
 */
package de.almapi;

import java.awt.GridLayout;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.api.client.config.DefaultClientConfig;

/**
 * @author Sebastian Kirchner
 * 
 */
public class RestRepository {

	private static final Logger LOG = Logger.getLogger(RestRepository.class.getName());

	/*
	 * eg.: http://alm11.wob.vw.vwg/qcbin/rest/domains/VW_DE_PROD/projects/MEDUSA_RD/
	 */
	private static final String ALM_REST_SERVICE_URL = ALMProperties.ALM_REST_BASE_URL.getValue() + "/domains/"
			+ ALMProperties.ALM_DOMAIN.getValue() + "/projects/" + ALMProperties.ALM_PROJECT.getValue() + "/";

	private CookieStore loginCookies = new CookieStore();

	/*
	 * (non-Javadoc)
	 * 
	 * @see vwg.alma.repository.IRequirementRepository#initialize()
	 */
	@Override
	public void initialize() {
		// getting password of the current user
		final PasswordPanel passwordPanel = new PasswordPanel();
		if(JOptionPane.showConfirmDialog(null, passwordPanel, "Enter your username and password:", //$NON-NLS-1$
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.CANCEL_OPTION){
			System.exit(0);
		}
		final char[] pass = passwordPanel.getPassword();
		final String user = passwordPanel.getUser();
		try{
			loginCookies = ALMLogin.login(user, pass);
		}catch(IOException e){
			LOG.log(Level.WARNING, "Login failed! No connection to the HP ALM login service.", e);
		}catch(de.almapi.ALMLogin.LoginException e){
			LOG.log(Level.WARNING, "Login failed! Please check the provided credentials.", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see vwg.alma.repository.IRequirementRepository#getAllRequirements()
	 */
	@Override
	public Set<IRequirement> getRequirements() {

		final Set<IRequirement> result = new TreeSet<>();
		final int pageSize = 20;
		int startIndex = 1;
		int totalResults = 0;
		long time = System.currentTimeMillis();

		do{
			final String url = ALM_REST_SERVICE_URL + "requirements?page-size=" + pageSize + "&start-index="
					+ startIndex;
			final ClientResponse response = request(url);
			if(response == null){
				// TODO log warn
				return result;
			}
			final Entities entities = response.getEntity(Entities.class);
			totalResults = entities.getTotalResults();

			// TODO
			System.out.println("->>>>>>>>>>>>>>>>" + url);

			for(int index = 0; index < totalResults; index++){
				final Entity entity = entities.get(index);
				// TODO
				final RestRequirement r = new RestRequirement(entity);
				System.out.println(r.getId());
				result.add(r);
			}

			try{
				Thread.sleep(1000);
			}catch(InterruptedException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			startIndex = startIndex + pageSize;
		}while(totalResults == pageSize);

		System.out.println("TIME: " + ((System.currentTimeMillis() - time) / 1000));

		return result;
	}

	/**
	 * @param id
	 *            the id of the requested requirement
	 * @return the {@link IRequirement} with the given id or <code>null</code>, if the request
	 *         fails.
	 */
	public IRequirement getRequirement(final int id) {
		final ClientResponse response = request(ALM_REST_SERVICE_URL + "requirements/" + id);
		if(response == null){
			return null;
		}
		return new RestRequirement(response.getEntity(Entity.class));
	}

	/**
	 * @param url
	 *            the rest service URL to request
	 * @return the resulting {@link ClientResponse}. If the request fails (HTTP response code NOT
	 *         200) <code>null</code> will be returned.
	 */
	private ClientResponse request(final String url) {
		// build the request
		final DefaultClientConfig cc = new DefaultClientConfig();
		final Client client = Client.create(cc);
		final WebResource resource = client.resource(url);
		final Builder builder = resource.getRequestBuilder();
		builder.accept(MediaType.APPLICATION_XML);
		// Set the cookies
		if(loginCookies == null){
			initialize();
		}
		for(Cookie cookie : loginCookies.getCookies()){
			builder.cookie(cookie);
		}
		final ClientResponse response = builder.get(ClientResponse.class);
		if(response.getStatus() != 200){
			LOG.warning("Request failed! HTTP response code: " + response.getStatus());
		}
		return response;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see vwg.alma.repository.IRepository#getReleases()
	 */
	@Override
	public Set<IRelease> getReleases() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * The content of the dialog for the user credentials.
	 * 
	 * @author Sebastian Kirchner (Volkswagen AG)
	 */
	private static class PasswordPanel extends JPanel {

		private static final long serialVersionUID = 1L;
		private JPasswordField passwordFld = new JPasswordField();
		private JTextField userFld = new JTextField("ekirchs");

		public PasswordPanel() {
			super();
			this.setLayout(new GridLayout(2, 2));
			this.add(new JLabel("UserId:")); //$NON-NLS-1$
			this.add(this.userFld);
			this.add(new JLabel("Password:")); //$NON-NLS-1$
			this.add(this.passwordFld);
		}

		@Override
		public void setVisible(boolean aFlag) {
			super.setVisible(aFlag);
			this.passwordFld.requestFocus();
		}

		public char[] getPassword() {
			return this.passwordFld.getPassword();
		}

		public String getUser() {
			return this.userFld.getText();
		}
	}
}
