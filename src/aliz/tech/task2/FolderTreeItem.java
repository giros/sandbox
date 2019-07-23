package aliz.tech.task2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gergely Mathe
 */
public class FolderTreeItem {

	/**
	 * Constructor that creates a new FolderTreeItem with the given name and no children.
	 *
	 * @param name The folder name.
	 */
	public FolderTreeItem(String name) {
		_name = name;
		_children = new ArrayList<>();
	}

	/**
	 * Adds a child to this folder with the given name and returns it.
	 * If it already has a child with the given name, it returns that.
	 *
	 * @param name The child folder name.
	 * @return The added or existing child folder.
	 */
	public FolderTreeItem addChildWithName(String name) {
		FolderTreeItem child = _getChildWithName(name);

		if (child != null) {
			return child;
		}

		child = new FolderTreeItem(name);

		_children.add(child);

		return child;
	}

	/**
	 * Retrieves the child of this folder with the given name, or <code>null</code> if there isn't any.
	 *
	 * @param name The child folder name.
	 * @return The child of this folder with the given name, or <code>null</code> if there isn't any.
	 */
	private FolderTreeItem _getChildWithName(String name) {
		for (FolderTreeItem child : _children) {
			if (name.equals(child.getName())) {
				return child;
			}
		}

		return null;
	}

	/**
	 * Returns the children of this folder.
	 *
	 * @return The children of this folder.
	 */
	public List<FolderTreeItem> getChildren() {
		return _children;
	}

	/**
	 * Returns the name of this folder.
	 *
	 * @return The name of this folder.
	 */
	public String getName() {
		return _name;
	}

	private final String _name;
	private List<FolderTreeItem> _children;

}
