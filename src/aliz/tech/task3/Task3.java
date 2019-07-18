package aliz.tech.task3;

import java.io.File;
import java.io.IOException;

/**
 * @author Gergely Mathe
 */
public class Task3 {

	public static void main(String[] args) throws IOException {
		if (args.length == 0) {
			return;
		}

		IndexFileCreator.create(new File(args[0]));
	}

}
