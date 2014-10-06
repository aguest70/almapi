/**
 * 
 */
package de.gawky.treemodels;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.tree.TreeModel;

/**
 * A {@link TreeModel} for a list of {@link TreeModel}
 * 
 * @author Sebastian Kirchner (Volkswagen AG)
 * 
 */
public class TreeModelTreeModel extends ListTreeModel {

	private Collection<TreeModel> treeModels = new ArrayList<>(0);

	private Object root = "Root";

	private Map<Object, TreeModel> nodeModelMap = new HashMap<>();

	/**
	 * @param root
	 * @param treeModels
	 */
	public TreeModelTreeModel(Object root, Collection<TreeModel> treeModels) {
		super();
		this.root = root;
		this.treeModels = treeModels;
	}

	/**
	 * @param root
	 */
	public TreeModelTreeModel(final Object root, final TreeModel... treeModels) {
		this(treeModels);
		this.root = root;
	}

	/**
	 * @param treeModels
	 */
	public TreeModelTreeModel(final TreeModel... treeModels) {
		super();
		this.treeModels = new ArrayList<TreeModel>(treeModels.length);
		for (int i = 0; i < treeModels.length; i++) {
			this.treeModels.add(treeModels[i]);
		}
	}

	/**
	 * @param treeModels
	 */
	public TreeModelTreeModel(Collection<TreeModel> treeModels) {
		super();
		this.treeModels = treeModels;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.tree.TreeModel#getRoot()
	 */
	@Override
	public Object getRoot() {
		return this.root;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.gawkytreemodels.ListTreeModel#getChildren(java.lang.Object)
	 */
	@Override
	protected List<?> getChildren(Object parent) {
		if (parent == this.getRoot()) {
			final List<Object> roots = new ArrayList<>(treeModels.size());
			for (final TreeModel treeModel : treeModels) {
				// register Child
				final Object root = treeModel.getRoot();
				this.nodeModelMap.put(root, treeModel);
				roots.add(root);
			}
			return roots;
		}
		return getChildren(parent, nodeModelMap.get(parent));
	}

	/**
	 * @param node
	 * @param treeModel
	 * @return
	 */
	private List<?> getChildren(final Object node, final TreeModel treeModel) {
		final int childCount = treeModel.getChildCount(node);
		final List<Object> children = new ArrayList<>(childCount);
		for (int i = 0; i < childCount; i++) {
			final Object child = treeModel.getChild(node, i);
			// register Child
			this.nodeModelMap.put(child, treeModel);
			children.add(child);
		}
		return children;
	}
}
