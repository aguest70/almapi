/**
 * 
 */
package de.almapi.domain.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * General entities collection in ALM.
 * <p>
 * An ALM entity is a complete functional unit of information. Entities are members of collections
 * called {@link Entities}.
 * </p>
 * 
 * @author Sebastian Kirchner
 * 
 */
@XmlRootElement(name = "Entities")
public class Entities implements List<Entity> {

	private Integer totalResults = null;

	private List<Entity> delegate = null;

	/**
	 * @return the totalResults
	 */
	@XmlAttribute(name = "TotalResults")
	public Integer getTotalResults() {
		if(this.delegate != null){
			return this.delegate.size();
		}
		return totalResults;
	}

	/**
	 * @param totalResults
	 *            the totalResults to set
	 */
	public void setTotalResults(Integer totalResults) {
		this.totalResults = totalResults;
	}

	/**
	 * @return the entityList
	 */
	@XmlElement(name = "Entity")
	public List<Entity> getList() {
		if(this.delegate == null){
			this.delegate = new LinkedList<>();
		}
		return this.delegate;
	}

	/**
	 * @param entityList
	 *            the entityList to set
	 */
	public void setList(List<Entity> entityList) {
		this.delegate = entityList;
	}

	/**
	 * @return
	 * @see java.util.List#size()
	 */
	public int size() {
		return delegate.size();
	}

	/**
	 * @return
	 * @see java.util.List#isEmpty()
	 */
	public boolean isEmpty() {
		return delegate.isEmpty();
	}

	/**
	 * @param o
	 * @return
	 * @see java.util.List#contains(java.lang.Object)
	 */
	public boolean contains(Object o) {
		return delegate.contains(o);
	}

	/**
	 * @return
	 * @see java.util.List#iterator()
	 */
	public Iterator<Entity> iterator() {
		return delegate.iterator();
	}

	/**
	 * @return
	 * @see java.util.List#toArray()
	 */
	public Object[] toArray() {
		return delegate.toArray();
	}

	/**
	 * @param a
	 * @return
	 * @see java.util.List#toArray(T[])
	 */
	public <T> T[] toArray(T[] a) {
		return delegate.toArray(a);
	}

	/**
	 * @param e
	 * @return
	 * @see java.util.List#add(java.lang.Object)
	 */
	public boolean add(Entity e) {
		return delegate.add(e);
	}

	/**
	 * @param o
	 * @return
	 * @see java.util.List#remove(java.lang.Object)
	 */
	public boolean remove(Object o) {
		return delegate.remove(o);
	}

	/**
	 * @param c
	 * @return
	 * @see java.util.List#containsAll(java.util.Collection)
	 */
	public boolean containsAll(Collection<?> c) {
		return delegate.containsAll(c);
	}

	/**
	 * @param c
	 * @return
	 * @see java.util.List#addAll(java.util.Collection)
	 */
	public boolean addAll(Collection<? extends Entity> c) {
		return delegate.addAll(c);
	}

	/**
	 * @param index
	 * @param c
	 * @return
	 * @see java.util.List#addAll(int, java.util.Collection)
	 */
	public boolean addAll(int index, Collection<? extends Entity> c) {
		return delegate.addAll(index, c);
	}

	/**
	 * @param c
	 * @return
	 * @see java.util.List#removeAll(java.util.Collection)
	 */
	public boolean removeAll(Collection<?> c) {
		return delegate.removeAll(c);
	}

	/**
	 * @param c
	 * @return
	 * @see java.util.List#retainAll(java.util.Collection)
	 */
	public boolean retainAll(Collection<?> c) {
		return delegate.retainAll(c);
	}

	/**
	 * 
	 * @see java.util.List#clear()
	 */
	public void clear() {
		delegate.clear();
	}

	/**
	 * @param o
	 * @return
	 * @see java.util.List#equals(java.lang.Object)
	 */
	public boolean equals(Object o) {
		return delegate.equals(o);
	}

	/**
	 * @return
	 * @see java.util.List#hashCode()
	 */
	public int hashCode() {
		return delegate.hashCode();
	}

	/**
	 * @param index
	 * @return
	 * @see java.util.List#get(int)
	 */
	public Entity get(int index) {
		return delegate.get(index);
	}

	/**
	 * @param index
	 * @param element
	 * @return
	 * @see java.util.List#set(int, java.lang.Object)
	 */
	public Entity set(int index, Entity element) {
		return delegate.set(index, element);
	}

	/**
	 * @param index
	 * @param element
	 * @see java.util.List#add(int, java.lang.Object)
	 */
	public void add(int index, Entity element) {
		delegate.add(index, element);
	}

	/**
	 * @param index
	 * @return
	 * @see java.util.List#remove(int)
	 */
	public Entity remove(int index) {
		return delegate.remove(index);
	}

	/**
	 * @param o
	 * @return
	 * @see java.util.List#indexOf(java.lang.Object)
	 */
	public int indexOf(Object o) {
		return delegate.indexOf(o);
	}

	/**
	 * @param o
	 * @return
	 * @see java.util.List#lastIndexOf(java.lang.Object)
	 */
	public int lastIndexOf(Object o) {
		return delegate.lastIndexOf(o);
	}

	/**
	 * @return
	 * @see java.util.List#listIterator()
	 */
	public ListIterator<Entity> listIterator() {
		return delegate.listIterator();
	}

	/**
	 * @param index
	 * @return
	 * @see java.util.List#listIterator(int)
	 */
	public ListIterator<Entity> listIterator(int index) {
		return delegate.listIterator(index);
	}

	/**
	 * @param fromIndex
	 * @param toIndex
	 * @return
	 * @see java.util.List#subList(int, int)
	 */
	public List<Entity> subList(int fromIndex, int toIndex) {
		return delegate.subList(fromIndex, toIndex);
	}

}
