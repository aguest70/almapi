// CCM: %full_filespec: %
// CCM: %date_created: % %derived_by: %
package de.gawky.treemodels;

import java.util.LinkedList;
import java.util.List;

import javax.swing.event.EventListenerList;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

/**
 * TreeModel mit der im Interface vorgesehenen Behandlung von TreeModelListener
 * und einigen Hilfsmethoden.
 * 
 * @author Sebastian Kirchner
 * @since 04.10.2005 12:26:41
 * @version %version: %
 */
public abstract class NotificationTreeModel implements TreeModel {

	private EventListenerList eventListenerList = null;

	/**
	 * Lazy-Creator for the listeners list.
	 * 
	 * @return EventListenerList of the model
	 */
	protected EventListenerList getEventListenerList() {
		if(this.eventListenerList == null){
			this.eventListenerList = new EventListenerList();
		}
		return this.eventListenerList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.tree.TreeModel#addTreeModelListener(javax.swing.event.TreeModelListener)
	 */
	@Override
	public void addTreeModelListener(final TreeModelListener l) {
		this.getEventListenerList().add(TreeModelListener.class, l);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.tree.TreeModel#removeTreeModelListener(javax.swing.event.TreeModelListener)
	 */
	@Override
	public void removeTreeModelListener(final TreeModelListener l) {
		this.getEventListenerList().remove(TreeModelListener.class, l);
	}

	/**
	 * Notifies all TreeModelListener a node (or a set of siblings) has changed
	 * in some way. The node(s) have not changed locations in the tree or
	 * altered their children arrays, but other attributes have changed and may
	 * affect presentation. Example: the name of a file has changed, but it is
	 * in the same location in the file system.
	 * <p>
	 * To indicate the root has changed, childIndices and children will be null.
	 * </p>
	 * 
	 * @param e
	 *            the event to be fired
	 */
	protected void fireTreeNodesChanged(final TreeModelEvent e) {
		final TreeModelListener[] listeners = this.getEventListenerList().getListeners(
				TreeModelListener.class);
		for(final TreeModelListener listener : listeners){
			listener.treeNodesChanged(e);
		}
	}

	/**
	 * Notifies all TreeModelListener that nodes have been inserted into the
	 * tree.
	 * 
	 * @param e
	 *            the event to be fired
	 */
	protected void fireTreeNodesInserted(final TreeModelEvent e) {
		final TreeModelListener[] listeners = this.getEventListenerList().getListeners(
				TreeModelListener.class);
		for(final TreeModelListener listener : listeners){
			listener.treeNodesInserted(e);
		}
	}

	/**
	 * Notifies all TreeModelListener that nodes have been removed from the
	 * tree. Note that if a subtree is removed from the tree, this method may
	 * only be invoked once for the root of the removed subtree, not once for
	 * each individual set of siblings removed
	 * 
	 * @param e
	 *            the event to be fired
	 */
	protected void fireTreeNodesRemoved(final TreeModelEvent e) {
		final TreeModelListener[] listeners = this.getEventListenerList().getListeners(
				TreeModelListener.class);
		for(final TreeModelListener listener : listeners){
			listener.treeNodesRemoved(e);
		}
	}

	/**
	 * Notifies all TreeModelListener that the tree has drastically changed
	 * structure from a given node down.
	 * 
	 * @param e
	 *            the event to be fired
	 */
	protected void fireTreeStructureChanged(final TreeModelEvent e) {
		final TreeModelListener[] listeners = this.getEventListenerList().getListeners(
				TreeModelListener.class);
		for(final TreeModelListener listener : listeners){
			listener.treeStructureChanged(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.tree.TreeModel#valueForPathChanged(javax.swing.tree.TreePath,
	 * java.lang.Object)
	 */
	@Override
	public void valueForPathChanged(final TreePath path, final Object newValue) {
		this.fireTreeNodesChanged(new TreeModelEvent(this, path));
	}

	/**
	 * TODO Methode dokumentieren!
	 * 
	 * @param node
	 * @return
	 */
	protected TreePath[] getPaths(final Object node) {
		final List<Object> path = new LinkedList<Object>();
		path.add(this.getRoot());
		final List<TreePath> result = this.getPaths(new LinkedList<TreePath>(), path, node);
		return result.toArray(new TreePath[result.size()]);
	}

	private List<TreePath> getPaths(final List<TreePath> paths, final List<Object> currentPath,
			final Object node) {
		final Object lastPathComponent = currentPath.get(currentPath.size() - 1);
		if(lastPathComponent.equals(node)){
			paths.add(new TreePath(currentPath.toArray(new Object[currentPath.size()])));
		}
		for(int i = 0; i < this.getChildCount(lastPathComponent); i++){
			final Object child = this.getChild(lastPathComponent, i);
			currentPath.add(child);
			this.getPaths(paths, currentPath, node);
			currentPath.remove(child);
		}
		return paths;
	}
}
