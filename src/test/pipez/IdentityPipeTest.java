package pipez;

import static org.hamcrest.CoreMatchers.is; //IGNORE - 	eclipse mistakenly flags this as deprecated
import static org.junit.Assert.*;

import org.junit.Test;

import pipez.core.Block;
import pipez.core.SimpleBlock;

public class IdentityPipeTest {

	@Test
	public void test_empty_block() {
		IdentityPipe id = IdentityPipe.create();
		Block b = id.transform(Block.EMPTY_BLOCK);
		assertThat(b, is(Block.EMPTY_BLOCK));
	}
	
	@Test
	public void test_single_field() throws Exception {
		IdentityPipe id = IdentityPipe.create();
		SimpleBlock sb = new SimpleBlock();
		sb.add("abc", "123");
		
		Block tb = id.transform(sb);
		assertThat(tb.fields().length, is(1));
		assertThat(tb.fields()[0], is("abc"));
		//assertThat(tb.value("abc"), is("123"));
	}
}
