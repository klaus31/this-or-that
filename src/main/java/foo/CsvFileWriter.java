package foo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

public class CsvFileWriter<T> {
	
	private File file = null;

	public CsvFileWriter() {
		try {
			file = File.createTempFile("this-or-that", "tmp.csv");
		} catch (IOException e) {
			System.out.println("ach du scheisse");
			System.exit(0);
		}
	}
	public CsvFileWriter(File file) {
			this.file = file;
	}

	private CSVParser parser() {
		try {
			return new CSVParser(new FileReader(file), CSVFormat.DEFAULT);
		} catch (FileNotFoundException e) {
			System.out.println("och menno");
			System.exit(0);
		} catch (IOException e) {
			System.out.println("arsch");
			System.exit(0);
		}
		return null;
	}

	private CSVPrinter printer(boolean append) {
		try {
			FileWriter fileWriter;
			if (append) {

				fileWriter = new FileWriter(file, append);
			} else {
				fileWriter = new FileWriter(file);

			}
			return new CSVPrinter(fileWriter, CSVFormat.DEFAULT);
		} catch (FileNotFoundException e) {
			System.out.println("och menno auch");
			System.exit(0);
		} catch (IOException e) {
			System.out.println("arsch auch");
			System.exit(0);
		}
		return null;
	}

	public void writeLine(List<T> line) {
		try {
			CSVPrinter printer = printer(true);
			printer.printRecord(line);
			printer.flush();
		} catch (IOException e) {
			System.out.println("oh gott oh gott");
			System.exit(0);
		}
	}

	public Path getPath() {
		return file.toPath();
	}

	public String getContent() {
		Charset encoding = Charset.defaultCharset();
		byte[] encoded;
		try {
			encoded = Files.readAllBytes(getPath());
			return new String(encoded, encoding).trim();
		} catch (IOException e) {
			System.out.println("aaah, blöd");
			System.exit(0);
		}
		return null;
	}

	public void rewriteValue(int column, int line, String value) {
		try {
			List<String[]> lines = new ArrayList<>();
			int currentLine = 0;
			CSVParser parser = parser();
			List<CSVRecord> records = parser.getRecords();
			for (CSVRecord record : records) {
				int currentColumn = 0;
				List<String> values = new ArrayList<>();
				while (currentColumn < record.size()) {
					if (currentLine == line && currentColumn == column) {
						values.add(value);
					} else {
						values.add(record.get(currentColumn));
					}
					currentColumn++;
				}
				System.out.println(values);
				lines.add(values.toArray(new String[values.size()]));
				currentLine++;
			}
			CSVPrinter printer = printer(false);
			printer.printRecords(lines);
			printer.flush();
		} catch (IOException e) {
			System.out.println("oh haua ha");
			System.exit(0);
		}
	}

	public String getContent(int column, int line) {
		try {
			return parser().getRecords().get(line).get(column);
		} catch (IOException e) {
			System.out.println("Zzzzisch");
			System.exit(0);
		}
		return null;
	}

	public void writeLine(T... values) {
		writeLine(new ArrayList<>(Arrays.asList(values)));
	}

	public Integer getLineWhere(int column, T value) {
		Integer result = 0;
		try {
			while (!getContent(column, result).equals(value)) {
				result++;
			}
		} catch (Exception e) {
			result = null;
		}
		return result;
	}

}
