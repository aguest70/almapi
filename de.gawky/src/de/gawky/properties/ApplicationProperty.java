/**
 * 
 */
package de.gawky.properties;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * TODO
 * 
 * @author Sebastian Kirchner (Volkswagen AG)
 * 
 */
public class ApplicationProperty {

	private static final Logger LOG = Logger.getLogger(ApplicationProperty.class.getName());

	private String key;

	private String defaultValue;

	/**
	 * @param key
	 *            the name of the property
	 */
	public ApplicationProperty(final String key) {
		this.key = key;
	}

	/**
	 * @param key
	 *            the name of the property
	 * @param defaultValue
	 *            this fallback value will be used if no value has been set via
	 *            {@link ApplicationProperty#loadProperties(File)}
	 */
	public ApplicationProperty(final String key, final String defaultValue) {
		this.key = key;
		this.defaultValue = defaultValue;
	}

	/**
	 * @return the string value of the application property
	 */
	public String getValue() {
		final String property = System.getProperty(this.key);
		return property == null ? defaultValue : property;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ApplicationProperty [key=" + key + ", value=" + this.getValue() + "]";
	}

	/**
	 * @return the application property parsed to an integer value. If
	 *         parsing the value fails, <code>null</code> will be returned.
	 */
	public Integer getIntValue() {
		try{
			return Integer.parseInt(this.getValue());
		}catch(NumberFormatException e){
			LOG.warning("The string \"" + this.getValue() + "\" could not be parsed as an integer value!");
		}
		return null;
	}

	/**
	 * @return the application property parsed to a boolean value. If
	 *         parsing the value fails, <code>null</code> will be returned.
	 */
	public Boolean getBooleanValue() {

		try{
			return Boolean.parseBoolean(this.getValue());
		}catch(NumberFormatException e){
			LOG.warning("The string \"" + this.getValue() + "\" could not be parsed as an boolean value!");
		}
		return null;
	}

	/**
	 * @return the value of the application property in form of a Java class. The property value is
	 *         considered to be a full-qualified class name. The appropriate class is being loaded
	 *         via {@link Class#forName(String)}. If the class could not be found <code>null</code>
	 *         will be returned.
	 */
	public Class<?> getClassValue() {
		try{
			return Class.forName(this.getValue());
		}catch(ClassNotFoundException e){
			LOG.warning("The class \"" + this.getValue() + "\" could not be found!"); //$NON-NLS-1$
		}
		return null;
	}

	/**
	 * Loading the application property from the properties file.
	 * <p>
	 * Existing system properties will be overwritten.
	 * </p>
	 * 
	 * @param propertiesFile
	 *            the properties file
	 */
	public static void loadProperties(final File propertiesFile) {
		LOG.info("initializing application properties"); //$NON-NLS-1$
		final Properties p = new Properties();
		try{
			p.load(new FileReader(propertiesFile));
		}catch(IOException e){
			LOG.warning("Application properties not found: " + propertiesFile.getAbsolutePath()); //$NON-NLS-1$
			return;
		}
		for(String name : p.stringPropertyNames()){
			String value = p.getProperty(name).trim();
			System.setProperty(name, value);
		}
	}

}
