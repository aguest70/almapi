package de.almapi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;

import javax.ws.rs.core.Cookie;

/**
 * @author Sebastian Kirchner
 */
public class CookieStore {

	private Map<String, String> delegate = new HashMap<>();

	/**
	 * @return
	 * @see java.util.Map#size()
	 */
	public int size() {
		return delegate.size();
	}

	/**
	 * @return
	 * @see java.util.Map#isEmpty()
	 */
	public boolean isEmpty() {
		return delegate.isEmpty();
	}

	/**
	 * @param key
	 * @return
	 * @see java.util.Map#containsKey(java.lang.Object)
	 */
	public boolean containsKey(Object key) {
		return delegate.containsKey(key);
	}

	/**
	 * @param value
	 * @return
	 * @see java.util.Map#containsValue(java.lang.Object)
	 */
	public boolean containsValue(Object value) {
		return delegate.containsValue(value);
	}

	/**
	 * @param key
	 * @return
	 * @see java.util.Map#get(java.lang.Object)
	 */
	public String get(Object key) {
		return delegate.get(key);
	}

	/**
	 * @param key
	 * @param value
	 * @return
	 * @see java.util.Map#put(java.lang.Object, java.lang.Object)
	 */
	public String put(String key, String value) {
		return delegate.put(key, value);
	}

	/**
	 * @param cookieList
	 *            a list of cookies. Every element of this list is formatted
	 *            like: KEY=VALUE. If not, the element will not be added to the
	 *            store.
	 */
	public void put(List<String> cookieList) {
		if (cookieList == null) {
			return;
		}
		for (String string : cookieList) {
			// Cutoff the path "key=value; Path=/;"
			final String cookie = string.indexOf("; ") > 0 ? string.substring(0,
					string.indexOf("; ")) : string;
			final StringTokenizer t = new StringTokenizer(cookie, "=");
			if (t.countTokens() < 2) {
				continue;
			}
			this.delegate.put(t.nextToken(), t.nextToken());
		}
	}

	/**
	 * @param key
	 * @return
	 * @see java.util.Map#remove(java.lang.Object)
	 */
	public String remove(Object key) {
		return delegate.remove(key);
	}

	/**
	 * @param m
	 * @see java.util.Map#putAll(java.util.Map)
	 */
	public void putAll(Map<? extends String, ? extends String> m) {
		delegate.putAll(m);
	}

	/**
	 * 
	 * @see java.util.Map#clear()
	 */
	public void clear() {
		delegate.clear();
	}

	/**
	 * @return
	 * @see java.util.Map#keySet()
	 */
	public Set<String> keySet() {
		return delegate.keySet();
	}

	/**
	 * @return
	 * @see java.util.Map#values()
	 */
	public Collection<String> values() {
		return delegate.values();
	}

	/**
	 * @return a list of all {@link Cookie}s in this store
	 */
	public List<Cookie> getCookies() {
		final List<Cookie> cookies = new ArrayList<>(this.delegate.size());
		for (final String key : this.delegate.keySet()) {
			cookies.add(new Cookie(key, this.delegate.get(key)));
		}
		return cookies;
	}

	/**
	 * @return
	 * @see java.util.Map#entrySet()
	 */
	public Set<Entry<String, String>> entrySet() {
		return delegate.entrySet();
	}

	/**
	 * @param o
	 * @return
	 * @see java.util.Map#equals(java.lang.Object)
	 */
	public boolean equals(Object o) {
		return delegate.equals(o);
	}

	/**
	 * @return
	 * @see java.util.Map#hashCode()
	 */
	public int hashCode() {
		return delegate.hashCode();
	}

	@Override
	public String toString() {
		String result = new String();
		for (Iterator<String> i = delegate.keySet().iterator(); i.hasNext();) {
			final String key = i.next();
			result = result.concat(key).concat("=").concat(delegate.get(key));
			if (i.hasNext()) {
				result = result.concat("; ");
			}
		}
		return result;
	};

}
