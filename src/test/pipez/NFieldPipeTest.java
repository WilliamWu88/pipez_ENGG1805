package pipez;

import static org.hamcrest.CoreMatchers.is; //IGNORE - 	eclipse mistakenly flags this as deprecated
import static org.junit.Assert.*;

import org.junit.Test;

import pipez.core.Block;
import pipez.core.SimpleBlock;
import pipez.io.CSVReader;


public class NFieldPipeTest {

	@Test
	public void test_empty_block(){
		NFieldPipe id = NFieldPipe.create();
		Block b = id.transform(Block.EMPTY_BLOCK);
	}
	
	@Test
	public void test_single_field() throws Exception{
		NFieldPipe id = NFieldPipe.create(1,3);
		SimpleBlock sb = new SimpleBlock();
		sb.add("01", "123");
		sb.add("02", "456");
		sb.add("03", "789");
		
		Block tb = id.transform(sb);
		assertThat(tb.fields().length, is(2));
		assertThat(tb.fields()[0], is("01"));
		assertThat(tb.value("03"), is("789"));
	}
}
