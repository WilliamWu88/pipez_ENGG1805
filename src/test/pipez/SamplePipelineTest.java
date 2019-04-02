package pipez;

import static org.hamcrest.CoreMatchers.is; //IGNORE - 	eclipse mistakenly flags this as deprecated
import static org.junit.Assert.*;

import org.junit.Test;

import pipez.core.Block;
import pipez.core.Pipeline;
import pipez.io.CSVReader;
import pipez.io.CSVWriter;

public class SamplePipelineTest {

	@Test
	public void check_file() {
		Pipeline.create("test").in(CSVReader.from("in/sample.csv"))
			.append(ReversePipe.create())
			.out(CSVWriter.to("out/test.csv"));
		
		CSVReader testReader = CSVReader.from("out/test.csv");
		Block testBlock = testReader.next();
		assertThat(testBlock.fields().length, is(5));
		testReader.from("in/sample.csv");
		Block originalBlock = testReader.next();
		
		assertThat(testBlock.fields()[0], is(originalBlock.fields()[originalBlock.fields().length-1]));
	}

}
