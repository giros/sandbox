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

	/**
	 * Walks the folder structure starting at the given root folder, and cleans orphan .bak files and empty folders that remain after them.
	 *
	 * @param rootFolder The root folder of the folder structure to clean.
	 * @throws IOException if an IOException occurs.
	 */
	public static void clean(String rootFolder) throws IOException {
		File root = new File(rootFolder);

		Files.walk(
			root.toPath()
		).forEach(
			BakFileCleaner::_processPath
		);
	}

	/**
	 * Determines if a .bak file is orphan, meaning there is no other file in the same directory with the same filename and any extension.
	 *
	 * @param bakFilePath The path of the .bak file.
	 * @return <code>true</code> there is no other file in the same directory with the same filename and any extension, <code>false</code> otherwise.
	 * @throws IOException if an IOException occurs.
	 */
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

	/**
	 * Recursively collects the empty ancestor folders, starting at the given folder, ending at the first non-empty ancestor.
	 *
	 * @param folder The starting folder.
	 * @param foldersToDelete The list of folders collected so far.
	 * @return The list of empty ancestor folders.
	 * @throws IOException
	 */
	private static List<Path> _addEmptyParentFolders(Path folder, List<Path> foldersToDelete) throws IOException {
		Stream<Path> folderContent = Files.list(folder);

		if (folderContent.count() == 1) {
			foldersToDelete.add(folder);

			foldersToDelete = _addEmptyParentFolders(folder.getParent(), foldersToDelete);
		}

		return foldersToDelete;
	}

	/**
	 * Processes a path and if it's an orphan .bak file deletes it asynchronously with a new thread to avoid poor performace caused by huge files.
	 * Also collects the folders that can be deleted after the file got removed and passes them to the thread.
	 *
	 * @param path The path to process.
	 */
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
