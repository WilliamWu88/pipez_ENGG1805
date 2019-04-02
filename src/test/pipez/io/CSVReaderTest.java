package pipez.io;

import static org.hamcrest.CoreMatchers.is; //eclipse mistakenly marks this as deprecated
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.eq;

import java.io.PrintStream;

import org.junit.Test;

import pipez.core.Block;

public class CSVReaderTest {

	@Test
	public void test_nonexisting_file() {
		Reader csv = CSVReader.from("test_data/this_does_not_exist");
		
		PrintStream err = mock(PrintStream.class);
		PrintStream orierr = System.err;
		System.setErr(err);
		
		csv.open(); //you should see an error message
		verify(err).println(eq("test_data/this_does_not_exist (No such file or directory)"));
		System.setErr(orierr);
		
	}
	
	@Test
	public void test_empty_file() {
		Reader csv = CSVReader.from("test_data/0_line_0_col.csv");
		assertThat(csv.next(), is(Block.EMPTY_BLOCK));
		assertThat(csv.hasNext(), is(false));
		csv.close();
	}
	
	@Test
	public void test_empty_lines() {
		Reader csv = CSVReader.from("test_data/5_line_0_col.csv");
		int numLines = 0;
		while(csv.hasNext()) {
			Block b = csv.next();
			assertThat(b.fields().length, is(0)); //Check the value of number of fields in each line meets the condition equal to zero.
			numLines++;
		}
		assertThat(numLines, is(5)); //Check the block has 5 lines.
		csv.close();
	}
	
	@Test
	public void test_one_line_one_col() {
		
		Reader csv = CSVReader.from("test_data/1_line_1_col.csv");
		int numLines = 0;
		assertThat(csv.hasNext(), is(true));
		while(csv.hasNext()) {
			Block b = csv.next();
			assertThat(b.fields().length, is(1));
			assertThat(b.value(b.fields()[0]), is("l1c1"));
			numLines++;
		}
		assertThat(numLines, is(1));
		csv.close();
	}

	@Test
	public void test_one_line_four_col() {
		
		Reader csv = CSVReader.from("test_data/1_line_4_col.csv");
		int numLines = 0;
		assertThat(csv.hasNext(), is(true));
		while(csv.hasNext()) {
			Block b = csv.next();
			assertThat(b.fields().length, is(4));
			for(int i=0; i<4; i++) {
				assertThat(b.value(b.fields()[i]), is("l1c"+(i+1)));
			}
			numLines++;
		}
		assertThat(numLines, is(1));
		csv.close();
	}
	
	@Test
	public void test_ten_line_ten_col() {
		
		Reader csv = CSVReader.from("test_data/10_line_10_col.csv");
		int numLines = 0;
		assertThat(csv.hasNext(), is(true));
		while(csv.hasNext()) {
			Block b = csv.next();
			assertThat(b.fields().length, is(10));
			for(int i=0; i<10; i++) {
				assertThat(b.value(b.fields()[i]), is("l"+ (numLines+1) + "c" +(i+1)));
			}
			numLines++;
		}
		assertThat(numLines, is(10));
		csv.close();
	}
}
