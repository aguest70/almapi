package de.gawky.treemodels;

/**
 * TODO: Typkommentar für ArrayTreeModel einfügen!
 * 
 * @author Sebastian Kirchner
 * @since 04.10.2005 12:29:02
 * @version %version: %
 */
public abstract class ArrayTreeModel extends NotificationTreeModel {

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.tree.TreeModel#getChildCount(java.lang.Object)
	 */
	@Override
	public int getChildCount(final Object parent) {
		final Object[] children = this.getChildren(parent);
		if (children == null) {
			return 0;
		}
		return children.length;
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
		final Object[] children = this.getChildren(parent);
		if (children == null || index < 0 || index >= children.length) {
			return null;
		}
		return children[index];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.tree.TreeModel#getIndexOfChild(java.lang.Object,
	 * java.lang.Object)
	 */
	@Override
	public int getIndexOfChild(final Object parent, final Object child) {
		final Object[] children = this.getChildren(parent);
		if (children == null) {
			return -1;
		}
		for (int i = 0; i < children.length; i++) {
			if (children[i].equals(child)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * @param parent
	 *            the parent node
	 * @return the child array of the given node
	 */
	protected abstract Object[] getChildren(Object parent);

}
