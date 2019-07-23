package aliz.tech.task4;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author Gergely Mathe
 */
public class BakFileCleaner {

	public static void clean(String rootFolder) throws IOException {
		File root = new File(rootFolder);

		Files.walk(
			root.toPath()
		).forEach(
			BakFileCleaner::_processPath
		);
	}

	private static boolean _isOrphanBakFile(Path bakFilePath) throws IOException {
		String bakFilePathString = bakFilePath.toString();

		String bakFilePathWithoutExtension = bakFilePathString.substring(0, bakFilePathString.length() - 4);

		long sameFileNamesCount = Files.list(
			bakFilePath.getParent()
		).map(
			Path::toString
		).filter(
			sibling -> sibling.startsWith(bakFilePathWithoutExtension)
		).count();

		if (sameFileNamesCount > 1) {
			return false;
		}

		return true;
	}

	private static List<Path> _addEmptyParentFolders(Path folder, List<Path> foldersToDelete) throws IOException {
		Stream<Path> folderContent = Files.list(folder);

		if (folderContent.count() == 1) {
			foldersToDelete.add(folder);

			foldersToDelete = _addEmptyParentFolders(folder.getParent(), foldersToDelete);
		}

		return foldersToDelete;
	}

	private static void _processPath(Path path) {
		try {
			String pathString = path.toString();

			if (pathString.endsWith(".bak") && _isOrphanBakFile(path)) {
				List<Path> foldersToDelete = new ArrayList<>();

				foldersToDelete = _addEmptyParentFolders(path.getParent(), foldersToDelete);

				Thread fileDeleterThread = new Thread(new FileDeleter(path, foldersToDelete));

				fileDeleterThread.start();
			}
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

}
