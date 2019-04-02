package pipez;

import static org.hamcrest.CoreMatchers.is; //IGNORE - 	eclipse mistakenly flags this as deprecated
import static org.junit.Assert.*;

import org.junit.Test;

import pipez.core.Block;
import pipez.core.SimpleBlock;

public class TruIdentityPipeTest {
	
	@Test
	public void test_empty_block(){
		TruIdentityPipe id = TruIdentityPipe.create();
		Block b = id.transform(Block.EMPTY_BLOCK);
		
	}
	
	public void test_single_field(){
		TruIdentityPipe id = TruIdentityPipe.create();
		SimpleBlock sb = new SimpleBlock();
		sb.add("01", "1");
		sb.add("02", "2");
		
		Block tb = id.transform(sb);
		assertThat(tb.fields().length, is(1));
		assertThat(tb.fields()[0], is(1));
		assertThat(tb.value("02"), is(2));
	}
}
