package de.almapi.domain.impl;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * An Adapter to convert the map property {@link Entity#fields} into a List of
 * {@link Field}-Objects and vice versa to un-/marshal the following XML:
 * 
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8" standalone="yes"?&gt;
 * &lt;Entities TotalResults="2"&gt;
 *     &lt;Entity type="test1"&gt;
 *         &lt;Fields&gt;
 *             &lt;Field Name="user-13"&gt;
 *                 &lt;Value&gt;2013-12-20&lt;/Value&gt;
 *             &lt;/Field&gt;
 *             &lt;Field Name="user-14"&gt;
 *                 &lt;Value&gt;1.0.0&lt;/Value&gt;
 *             &lt;/Field&gt;
 *         &lt;/Fields&gt;
 *     &lt;/Entity&gt;
 * &lt;/Entities&gt;
 * </pre>
 * 
 * @author Sebastian Kirchner
 * 
 */
public class FieldsAdapter extends XmlAdapter<FieldsAdapter.Fields, Map<String, String>> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.xml.bind.annotation.adapters.XmlAdapter#unmarshal(java.lang.Object)
	 */
	@Override
	public Map<String, String> unmarshal(FieldsAdapter.Fields fields) throws Exception {
		final TreeMap<String, String> map = new TreeMap<>();
		for (final Field field : fields.fieldSet) {
			map.put(field.name, field.value);
		}
		return map;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.xml.bind.annotation.adapters.XmlAdapter#marshal(java.lang.Object)
	 */
	@Override
	public FieldsAdapter.Fields marshal(Map<String, String> map) throws Exception {
		final Fields fields = new Fields();
		for (String key : map.keySet()) {
			fields.fieldSet.add(new Field(key, map.get(key)));
		}
		return fields;
	}

	/**
	 * @author Sebastian Kirchner (Volkswagen AG)
	 * 
	 */
	public static class Fields {
		@XmlElement(name = "Field")
		public Set<Field> fieldSet = new TreeSet<>();
	}

	/**
	 * @author Sebastian Kirchner (Volkswagen AG)
	 * 
	 */
	public static class Field implements Comparable<Field> {

		@XmlAttribute(name = "Name")
		public String name;

		@XmlElement(name = "Value")
		public String value;

		/**
		 * Default constructor
		 */
		public Field() {
			super();
		}

		/**
		 * @param name
		 * @param value
		 */
		public Field(String name, String value) {
			super();
			this.name = name;
			this.value = value;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Comparable#compareTo(java.lang.Object)
		 */
		@Override
		public int compareTo(Field o) {
			if (this.name == null) {
				return o.name == null ? 0 : -1;
			}
			return this.name.compareTo(o.name);
		}

		/**
		 * @return the name
		 */
	}
}
