package pipez;

import pipez.core.Block;
import pipez.core.Pipe;
import pipez.core.SimpleBlock;

/**
 * A demo pipe that does nothing but write out its input blocks.
 * 
 * @author whwong
 *
 */
public class IdentityPipe implements Pipe{

	public static IdentityPipe create() {
		return new IdentityPipe();
	}
	
	private IdentityPipe() {}
	
	@Override
	public String getName() {
		return "Identity";
	}

	@Override
	public Block transform(Block block) {
		if(block == Block.EMPTY_BLOCK) return block;
		
		SimpleBlock nb = new SimpleBlock(); //copy the block 
		for(String f: block.fields()) {
			nb.add(f, f.valueOf(f)); 
		}
		
		return nb;
	}
	
}
