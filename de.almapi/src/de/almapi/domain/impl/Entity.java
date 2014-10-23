/**
 * 
 */
package de.almapi.domain.impl;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import de.almapi.domain.IEntity;

/**
 * An ALM entity is a complete functional unit of information. Entities are
 * members of collections called {@link Entities}.
 * <p>
 * The fields ({@link #getFields()}) of an {@link Entity} conforms
 * to the Entity Schema: http://alm/qcbin/Help/doc_library/api_refs/
 * REST/Content/XSD/entity_xsd.html .
 * </p>
 * 
 * @author Sebastian Kirchner 
 * 
 * @see Entities
 * @see RestRepository
 */
@XmlRootElement(name="Entity")
public class Entity implements IEntity {

	private Map<String, String> fields = new TreeMap<>();

	private String type = null;

	/* (non-Javadoc)
	 * @see de.almapi.domain.impl.IEntity#containsField(java.lang.String)
	 */
	@Override
	public boolean containsField(String fieldName) {
		return fields.containsKey(fieldName);
	}

	/* (non-Javadoc)
	 * @see de.almapi.domain.impl.IEntity#getFieldNames()
	 */
	@Override
	public Set<String> getFieldNames() {
		return fields.keySet();
	}

	/* (non-Javadoc)
	 * @see de.almapi.domain.impl.IEntity#getFields()
	 */
	@Override
	@XmlElement(name = "Fields")
	@XmlJavaTypeAdapter(value = FieldsAdapter.class)
	public Map<String, String> getFields() {
		return fields;
	}

	/* (non-Javadoc)
	 * @see de.almapi.domain.impl.IEntity#getFieldValue(java.lang.Object)
	 */
	@Override
	public String getFieldValue(Object fieldName) {
		return fields.get(fieldName);
	}

	/* (non-Javadoc)
	 * @see de.almapi.domain.impl.IEntity#getType()
	 */
	@Override
	@XmlAttribute(name = "Type")
	public String getType() {
		return type;
	}

	/* (non-Javadoc)
	 * @see de.almapi.domain.impl.IEntity#removeField(java.lang.Object)
	 */
	@Override
	public String removeField(Object fieldName) {
		return fields.remove(fieldName);
	}

	/* (non-Javadoc)
	 * @see de.almapi.domain.impl.IEntity#setField(java.lang.String, java.lang.String)
	 */
	@Override
	public String setField(String fieldName, String fieldValue) {
		return fields.put(fieldName, fieldValue);
	}

	/* (non-Javadoc)
	 * @see de.almapi.domain.impl.IEntity#setFields(java.util.Map)
	 */
	@Override
	public void setFields(Map<String, String> fields) {
		this.fields = fields;
	}

	/* (non-Javadoc)
	 * @see de.almapi.domain.impl.IEntity#setType(java.lang.String)
	 */
	@Override
	public void setType(String type) {
		this.type = type;
	}

}
