package aliz.tech.task2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gergely Mathe
 */
public class WritableFolderTreeCreator {

	/**
	 * Builds and returns a FolderTreeItem structure with writable folders at leaves, that are reachable through readable (or writable) folders from the root.
	 * It considers a writable folder reachable if all of it's ancestor folders are readable.
	 *
	 * @param readableFolders The list of readable folders with full path.
	 * @param writableFolders The list of writable folders with full path (a subset of readable folders).
	 * @return The root FolderTreeItem of a folder structure with writable folders at leaves, that are reachable through readable (or writable) folders from the root.
	 */
	public static FolderTreeItem create(List<String> readableFolders, List<String> writableFolders) {
		if (!readableFolders.contains("/")) {
			return null;
		}

		List<String> validWritableFolders = new ArrayList<>();

		for (String writableFolder : writableFolders) {
			String ancestorPath = writableFolder.substring(0, writableFolder.lastIndexOf("/"));

			if (ancestorPath.isEmpty()) {
				ancestorPath = "/";
			}

			if (_isFullPathReadable(ancestorPath, readableFolders)) {
				validWritableFolders.add(writableFolder);
			}
		}

		if (validWritableFolders.isEmpty()) {
			return null;
		}

		return _buildFolderTree(validWritableFolders);
	}

	/**
	 * Adds a path of child folders to the given FolderTreeItem with the names in the given String array.
	 * If a child with a specific name already exists, it uses that.
	 *
	 * @param folder The folder for which the child folder path needs to be added.
	 * @param newFolders The name of the new child folders.
	 */
	private static void _addFolderPath(FolderTreeItem folder, String[] newFolders) {
		if (newFolders.length == 0) {
			return;
		}

		FolderTreeItem currentFolder = folder;

		for (String newFolder : newFolders) {
			if (newFolder.isEmpty()) {
				continue;
			}

			currentFolder = currentFolder.addChildWithName(newFolder);
		}
	}

	/**
	 * Builds a FolderTreeItem structure from the given full folder paths.
	 *
	 * @param folderPaths Full paths of folders from which the structure needs to be built.
	 * @return The root FolderTreeItem of the built structure.
	 */
	private static FolderTreeItem _buildFolderTree(List<String> folderPaths) {
		FolderTreeItem rootFolder = new FolderTreeItem("/");

		for (String folderPath : folderPaths) {
			_addFolderPath(rootFolder, folderPath.split("/"));
		}

		return rootFolder;
	}

	/**
	 * Checks if all folders in the given full path are readable.
	 *
	 * @param path The full path to check.
	 * @param readableFolders The full paths of readable folders.
	 * @return <code>true</code> if all folders in the given full path are readable, <code>false</code> otherwise.
	 */
	private static boolean _isFullPathReadable(String path, List<String> readableFolders) {
		int endPos = path.length();

		while (endPos > 0) {
			if (!readableFolders.contains(path.substring(0, endPos))) {
				return false;
			}

			endPos = path.lastIndexOf("/", endPos - 1);
		}

		return true;
	}

}
