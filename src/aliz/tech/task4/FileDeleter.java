package aliz.tech.task4;

import java.nio.file.Files;
import java.nio.file.Path;

import java.util.List;

/**
 * @author Gergely Mathe
 */
public class FileDeleter implements Runnable {

	public FileDeleter(Path file, List<Path> foldersToDelete) {
		_file = file;
		_foldersToDelete = foldersToDelete;
	}

	@Override
	public void run() {
		try {
			System.out.println("Started deleting " + _file.toString());

			Files.delete(_file);

			System.out.println("Finished deleting " + _file.toString());

			for (Path folder : _foldersToDelete) {
				Files.delete(folder);

				System.out.println("Deleted empty folder " + folder.toString());
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Path _file;
	private List<Path> _foldersToDelete;

}
