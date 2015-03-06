package test;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import main.CsvFileWriter;

import org.junit.Test;

public class CsvFileWriterUnitTest {
	
	@Test
	public void test() throws IOException {
		CsvFileWriter<String> csvFW = new CsvFileWriter<>();
		List<String> line = new ArrayList<>();
		line.add("a");
		line.add("b");
		line.add("c");
		csvFW.writeLine(line);

		String content = csvFW.getContent();
		assertTrue(content.matches("a,b,c"));

		csvFW.writeLine(line);
		csvFW.writeLine(line);
		System.out.println(csvFW.getContent());
		System.out.println("----------------_");
		csvFW.rewriteValue(0, 0, "X");
		csvFW.rewriteValue(1, 2, "Y");
		csvFW.rewriteValue(2, 1, "Z");
		System.out.println(csvFW.getContent());
		assertTrue(csvFW.getContent(0, 0).matches("X"));
		assertTrue(csvFW.getContent(1, 0).matches("b"));
		assertTrue(csvFW.getContent(2, 0).matches("c"));
		assertTrue(csvFW.getContent(0, 1).matches("a"));
		assertTrue(csvFW.getContent(1, 1).matches("b"));
		assertTrue(csvFW.getContent(2, 1).matches("Z"));
		assertTrue(csvFW.getContent(0, 2).matches("a"));
		assertTrue(csvFW.getContent(1, 2).matches("Y"));
		assertTrue(csvFW.getContent(2, 2).matches("c"));
	}
}
