package aliz.tech.task2;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Gergely Mathe
 */
public class Task2 {

	public static void main(String[] args) throws IOException {
		File readableFoldersFile = new File(args[0]);
		File writableFoldersFile = new File(args[1]);

		List<String> readableFoldersList = Files.lines(
			readableFoldersFile.toPath()
		).collect(
			Collectors.toList()
		);

		List<String> writableFoldersList = Files.lines(
			writableFoldersFile.toPath()
		).collect(
			Collectors.toList()
		);

		FolderTreeItem rootFolder = WritableFolderTreeCreator.create(readableFoldersList, writableFoldersList);

		if (rootFolder == null) {
			System.out.println("Generated folder tree is empty.");

			return;
		}

		_printFolderTree(rootFolder, "");
	}

	private static void _printFolderTree(FolderTreeItem rootFolder, String prefix) {
		System.out.println(prefix + rootFolder.getName());

		for (FolderTreeItem subFolder : rootFolder.getChildren()) {
			_printFolderTree(subFolder, prefix + "-- ");
		}
	}

}
