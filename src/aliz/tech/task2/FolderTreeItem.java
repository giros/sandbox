package aliz.tech.task2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gergely Mathe
 */
public class FolderTreeItem {

	public FolderTreeItem(String name) {
		_name = name;
		_children = new ArrayList<>();
	}

	public FolderTreeItem addChildWithName(String name) {
		FolderTreeItem child = getChildWithName(name);

		if (child != null) {
			return child;
		}

		child = new FolderTreeItem(name);

		_children.add(child);

		return child;
	}

	public FolderTreeItem getChildWithName(String name) {
		for (FolderTreeItem child : _children) {
			if (name.equals(child.getName())) {
				return child;
			}
		}

		return null;
	}

	public List<FolderTreeItem> getChildren() {
		return _children;
	}

	public String getName() {
		return _name;
	}

	private final String _name;
	private List<FolderTreeItem> _children;

}
