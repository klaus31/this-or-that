package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static java.nio.file.StandardCopyOption.*;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

class RateCsvController implements DoRateActionController {

	private final static String CSV_FILE_NAME = "this-or-that.csv";
	private final static int COL_IMAGE = 0;
	private final static int COL_RATE_LIKE = 1;
	private final static int COL_RATE_DISLIKE = 2;

	@Override
	public void wasChoosen(FileImage fileImage) {
		CsvFileWriter<String> csvFW = getCsvFileWriter(fileImage);
		Integer line = csvFW.getLineWhere(COL_IMAGE, fileImage.getFile()
				.getName());
		if (line == null) {
			csvFW.writeLine(fileImage.getFile().getName(), "1", "0");
		} else {
			int newRate = Integer.parseInt(csvFW
					.getContent(COL_RATE_LIKE, line)) + 1;
			csvFW.rewriteValue(COL_RATE_LIKE, line, newRate + "");
		}
	}

	private CsvFileWriter<String> getCsvFileWriter(FileImage fileImage) {
		String directory = fileImage.getFile().getParent();
		File file = new File(directory + File.separator + CSV_FILE_NAME);
		return new CsvFileWriter<>(file);
	}

	@Override
	public void wasNotChoosen(FileImage fileImage) {
		CsvFileWriter<String> csvFW = getCsvFileWriter(fileImage);
		Integer line = csvFW.getLineWhere(COL_IMAGE, fileImage.getFile()
				.getName());
		if (line == null) {
			csvFW.writeLine(fileImage.getFile().getName(), "0", "1");
		} else {
			int newRate = Integer.parseInt(csvFW
					.getContent(COL_RATE_DISLIKE, line)) + 1;
			csvFW.rewriteValue(COL_RATE_DISLIKE, line, newRate + "");
		}
	}

}
