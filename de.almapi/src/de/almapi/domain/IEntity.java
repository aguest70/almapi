package de.almapi.domain;

import java.util.Map;
import java.util.Set;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import de.almapi.domain.impl.FieldsAdapter;

public interface IEntity {

	/**
	 * @param fieldName
	 * @return
	 * @see java.util.Map#containsKey(java.lang.Object)
	 */
	public abstract boolean containsField(String fieldName);

	/**
	 * @return
	 * @see java.util.Map#keySet()
	 */
	public abstract Set<String> getFieldNames();

	/**
	 * @return the fields
	 */
	@XmlElement(name = "Fields")
	@XmlJavaTypeAdapter(value = FieldsAdapter.class)
	public abstract Map<String, String> getFields();

	/**
	 * @param fieldName
	 * @return
	 * @see java.util.Map#get(java.lang.Object)
	 */
	public abstract String getFieldValue(Object fieldName);

	/**
	 * @return the type
	 */
	@XmlAttribute(name = "Type")
	public abstract String getType();

	/**
	 * @param fieldName
	 * @return
	 * @see java.util.Map#remove(java.lang.Object)
	 */
	public abstract String removeField(Object fieldName);

	/**
	 * @param fieldName
	 * @param fieldValue
	 * @return
	 * @see java.util.Map#put(java.lang.Object, java.lang.Object)
	 */
	public abstract String setField(String fieldName, String fieldValue);

	/**
	 * @param fields
	 *            the fields to set
	 */
	public abstract void setFields(Map<String, String> fields);

	/**
	 * @param type
	 *            the type to set
	 */
	public abstract void setType(String type);

}