package de.gawky.treemodels;

import java.util.List;

/**
 * <p>
 * Abstract, simple implementation of the TreeModel interface.
 * </p>
 * <p>
 * All implemented methods compute their return value by calling <code>getChildren()</code> directly
 * or indirectly. <b>Note:</b> the method <code>getChildren()</code> must never return a
 * <code>null</code> value.
 * </p>
 * 
 * @author Sebastian Kirchner
 * @since 30.03.2005 09:23:39
 */
public abstract class ListTreeModel extends NotificationTreeModel {

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.tree.TreeModel#getChildCount(java.lang.Object)
	 */
	@Override
	public int getChildCount(final Object parent) {
		final List<?> children = this.getChildren(parent);
		if(children == null){
			return 0;
		}
		return children.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.tree.TreeModel#isLeaf(java.lang.Object)
	 */
	@Override
	public boolean isLeaf(final Object node) {
		return this.getChildCount(node) == 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.tree.TreeModel#getChild(java.lang.Object, int)
	 */
	@Override
	public Object getChild(final Object parent, final int index) {
		final List<?> children = this.getChildren(parent);
		if(children == null || index < 0 || index >= children.size()){
			return null;
		}
		return children.get(index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.tree.TreeModel#getIndexOfChild(java.lang.Object,
	 * java.lang.Object)
	 */
	@Override
	public int getIndexOfChild(final Object parent, final Object child) {
		final List<?> children = this.getChildren(parent);
		if(children == null){
			return -1;
		}
		return children.indexOf(child);
	}

	/**
	 * @param parent
	 *            the parent node
	 * @return a list of children of the given node
	 * 
	 * @see ListTreeModel#getChild(Object, int)
	 * @see ListTreeModel#getChildCount(Object)
	 * @see ListTreeModel#getIndexOfChild(Object, Object)
	 */
	protected abstract List<?> getChildren(Object parent);
}