package aliz.tech.task2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gergely Mathe
 */
public class WritableFolderTreeCreator {

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

	private static FolderTreeItem _buildFolderTree(List<String> folderPaths) {
		FolderTreeItem rootFolder = new FolderTreeItem("/");

		for (String folderPath : folderPaths) {
			_addFolderPath(rootFolder, folderPath.split("/"));
		}

		return rootFolder;
	}

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
