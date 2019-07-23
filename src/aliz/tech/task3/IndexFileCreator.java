package aliz.tech.task3;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.nio.file.Files;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Gergely Mathe
 */
public class IndexFileCreator {

	/**
	 * Iterates through the lines of the input file and stores the words alongside their line number list in a sorted TreeMap.
	 * Then writes the contents of the map to the output file.
	 *
	 * @param inputFile The input file.
	 * @throws IOException if an IOException occurs.
	 */
	public static void create(File inputFile) throws IOException {
		Iterator<String[]> lineIterator = Files.lines(
			inputFile.toPath()
		).map(
			line -> line.split(" ")
		).iterator();

		Map<String, List<Integer>> indexMap = new TreeMap<>();
		int currentLineNumber = 1;

		while (lineIterator.hasNext()) {
			String[] currentLine = lineIterator.next();

			for (String currentWord : currentLine) {
				_storeWord(currentWord, indexMap, currentLineNumber);
			}

			currentLineNumber++;
		}

		if (indexMap.isEmpty()) {
			return;
		}

		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(_getIndexFileName(inputFile.getName())));

		for (Map.Entry<String, List<Integer>> entry : indexMap.entrySet()) {
			bufferedWriter.write(entry.getKey() + " " + _getLineNumbersString(entry.getValue()));

			bufferedWriter.newLine();
		}

		bufferedWriter.close();
	}

	/**
	 * Stores the given word and the line number in the given Map.
	 * If the word is already stored but with different line numbers, it adds the new line number to the value field.
	 *
	 * @param currentWord The word to store.
	 * @param indexMap The Map to store the word in.
	 * @param currentLineNumber The line number of the word.
	 */
	private static void _storeWord(String currentWord, Map<String, List<Integer>> indexMap, int currentLineNumber) {
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

	/**
	 * Constructs the name of the output file.
	 *
	 * @param inputFileName The name of the input file.
	 * @return The name of the output file.
	 */
	private static String _getIndexFileName(String inputFileName) {
		int endPos = inputFileName.lastIndexOf('.');

		return inputFileName.substring(0, endPos) + INDEX_FILE_NAME_SUFFIX;
	}

	/**
	 * Constructs a string of line numbers separated with commas in order to store them in the output file.
	 *
	 * @param lineNumbers The list of line numbers.
	 * @return The string of line numbers separated with commas.
	 */
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
