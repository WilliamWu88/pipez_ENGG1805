package pipez;

import static org.hamcrest.CoreMatchers.is; //IGNORE - 	eclipse mistakenly flags this as deprecated
import static org.junit.Assert.*;

import org.junit.Test;

import pipez.core.Block;
import pipez.core.SimpleBlock;

public class ReversePipeTest {
	
	@Test
	public void test_empty_block() {
		ReversePipe id = ReversePipe.create();
		Block b = id.transform(Block.EMPTY_BLOCK);
		//assertThat(b, is(Block.EMPTY_BLOCK));
	}
	
	@Test
	public void test_single_field() throws Exception {
		ReversePipe id = ReversePipe.create();
		SimpleBlock sb = new SimpleBlock();
		sb.add("abc", "123");
		sb.add("def", "456");
		//sb.add("ijk", "test");
		
		Block tb = id.transform(sb);
		assertThat(tb.fields().length, is(2));
		assertThat(tb.fields()[0], is("def"));
		assertThat(tb.value("def"), is("456"));
	}
}
