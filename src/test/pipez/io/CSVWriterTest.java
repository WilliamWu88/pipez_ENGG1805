package pipez.io;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.eq;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import pipez.core.Block;
import pipez.core.SimpleBlock;

public class CSVWriterTest {
	
	@Test
	public void test_directory() {
		Writer csv = CSVWriter.to("src");

		PrintStream err = mock(PrintStream.class);
		PrintStream orierr = System.err;
		System.setErr(err);
		
		csv.open(); //you should see an error message
		verify(err).println(eq("src is a directory and cannot be used for output."));
		System.setErr(orierr);
	}
	
	@Rule 
	public TemporaryFolder tmp = new TemporaryFolder();
	
	@Test
	public void test_empty_file() throws IOException {
		File zerolc0 = tmp.newFile();
		Writer w = CSVWriter.to(zerolc0.getAbsolutePath());
		w.close();
		
		Reader r = CSVReader.from(zerolc0.getAbsolutePath());
		assertThat(r.next(), is(Block.EMPTY_BLOCK));
		assertThat(r.hasNext(), is(false));
		r.close();
	}
	
	@Test
	public void test_empty_lines() throws IOException {
		File fivelc0 = tmp.newFile();
		Writer w = CSVWriter.to(fivelc0.getAbsolutePath());

		for(int i=0; i<5; i++) {
			Block b = Block.EMPTY_BLOCK;
			w.write(b);
		}
		w.close();
		
		Reader csv = CSVReader.from(fivelc0.getAbsolutePath());
		int numLines = 0;
		while(csv.hasNext()) {
			Block b = csv.next();
			assertThat(b.fields().length, is(0));
			numLines++;
		}
		assertThat(numLines, is(5));
		csv.close();
	}
	
	@Test
	public void test_one_line_one_col() throws IOException {
		
		File onelc1 = tmp.newFile();
		Writer w = CSVWriter.to(onelc1.getAbsolutePath());
		SimpleBlock sb = new SimpleBlock();
		sb.add("V1", "l1c1");
		w.write(sb);
		w.close();
		
		Reader csv = CSVReader.from(onelc1.getAbsolutePath());
		int numLines = 0;
		assertThat(csv.hasNext(), is(true));
		
		Block b = csv.next();
		assertThat(b.fields().length, is(1));
		assertThat(b.value(b.fields()[0]), is("V1"));
		numLines++;

		b = csv.next();
		assertThat(b.fields().length, is(1));
		assertThat(b.value(b.fields()[0]), is("l1c1"));
		numLines++;
		
		assertThat(numLines, is(2));
		csv.close();
	}

	@Test
	public void test_one_line_four_col() throws IOException {
		
		File fourlc1 = tmp.newFile();
		Writer w = CSVWriter.to(fourlc1.getAbsolutePath());
		SimpleBlock sb = new SimpleBlock();
		sb.add("V1", "l1c1");
		sb.add("V2", "l1c2");
		sb.add("V3", "l1c3");
		sb.add("V4", "l1c4");
		w.write(sb);
		w.close();
		
		
		Reader csv = CSVReader.from(fourlc1.getAbsolutePath());
		int numLines = 0;
		assertThat(csv.hasNext(), is(true));
		Block b = csv.next();
		assertThat(b.fields().length, is(4));
		for(int i=0; i<4; i++) {
			assertThat(b.value(b.fields()[i]), is("V"+(i+1)));
		}
		numLines++;

		b = csv.next();
		assertThat(b.fields().length, is(4));
		for(int i=0; i<4; i++) {
			assertThat(b.value(b.fields()[i]), is("l1c"+(i+1)));
		}
		numLines++;
		
		assertThat(numLines, is(2));
		csv.close();
	}
	
	@Test
	public void test_ten_line_ten_col() throws IOException {
		
		File fourlc1 = tmp.newFile();
		Writer w = CSVWriter.to(fourlc1.getAbsolutePath());
		for(int i=1; i<11; i++) {
			SimpleBlock sb = new SimpleBlock();
			for(int j=1; j<11; j++) {
				sb.add("V"+j, "l"+i+"c"+j);
			}
			w.write(sb);
		}
		w.close();

		
		Reader csv = CSVReader.from(fourlc1.getAbsolutePath());
		int numLines = 0;
		assertThat(csv.hasNext(), is(true));
		
		Block b = csv.next();
		assertThat(b.fields().length, is(10));
		for(int i=0; i<10; i++) {
			assertThat(b.value(b.fields()[i]), is("V"+(i+1)));
		}
		numLines++;
		
		while(csv.hasNext()) {
			b = csv.next();
			assertThat(b.fields().length, is(10));
			for(int i=0; i<10; i++) {
				assertThat(b.value(b.fields()[i]), is("l"+ (numLines) + "c" +(i+1)));
			}
			numLines++;
		}
		assertThat(numLines, is(11));
		csv.close();
	}
	
	@Test
	public void test_incomplete_lines() throws Exception {
		/**
		 * ,l1c2,l1c3,l1c4
		 * l2c1,,l2c3,l2c4
		 * l3c1,l3c2,,l3c4
		 * l4c1,l4c2,l4c3,
		 * ,l5c2,l5c3,
		 * l6c1,,l6c3,
		 * l7c1,l7c2,,
		 * ,,l8c3,l8c4
		 */
		
		fail();
		
	}
}