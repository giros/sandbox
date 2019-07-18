package aliz.tech.task3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * @author Gergely Mathe
 */
public class IndexFileCreator {

	public static void create(File inputFile) throws IOException {
		Map<String, List<Integer>> indexMap = new TreeMap<>();
		BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile));

		String currentLine = bufferedReader.readLine();
		int currentLineNumber = 1;

		while (currentLine != null) {
			Scanner scanner = new Scanner(currentLine);

			while (scanner.hasNext()) {
				String currentWord = scanner.next();

				if (indexMap.containsKey(currentWord)) {
					List<Integer> lineNumbers = indexMap.get(currentWord);

					if (!lineNumbers.contains(currentLineNumber)) {
						lineNumbers.add(currentLineNumber);

						indexMap.put(currentWord, lineNumbers);
					}
				}
				else {
					List<Integer> lineNumbers = new ArrayList<>();

					lineNumbers.add(currentLineNumber);

					indexMap.put(currentWord, lineNumbers);
				}
			}

			currentLine = bufferedReader.readLine();
			currentLineNumber++;
		}

		if (indexMap.isEmpty()) {
			return;
		}

		String indexFileName = _getIndexFileName(inputFile.getName());

		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(indexFileName));

		for (Map.Entry<String, List<Integer>> entry : indexMap.entrySet()) {
			bufferedWriter.write(entry.getKey() + " " + _getLineNumbersString(entry.getValue()));

			bufferedWriter.newLine();
		}

		bufferedWriter.close();
	}

	private static String _getIndexFileName(String inputFileName) {
		int endPos = inputFileName.lastIndexOf('.');

		return inputFileName.substring(0, endPos) + INDEX_FILE_NAME_SUFFIX;
	}

	private static String _getLineNumbersString(List<Integer> lineNumbers) {
		if (lineNumbers.isEmpty()) {
			return null;
		}

		StringBuilder sb = new StringBuilder();

		sb.append(lineNumbers.get(0));

		for (int i = 1; i < lineNumbers.size(); i++) {
			sb.append(",");
			sb.append(lineNumbers.get(i));
		}

		return sb.toString();
	}

	private static final String INDEX_FILE_NAME_SUFFIX = "-index.txt";

}
