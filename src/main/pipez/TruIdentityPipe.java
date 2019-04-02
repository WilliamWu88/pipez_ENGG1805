package pipez;

import pipez.core.Block;
import pipez.core.Pipe;

public class TruIdentityPipe implements Pipe {

	public static TruIdentityPipe create() {
		return new TruIdentityPipe();
	}
	
	private TruIdentityPipe() {}
	
	@Override
	public String getName() {
		return "TruIdentity";
	}

	@Override
	public Block transform(Block block) {
		return block;
	}
}
