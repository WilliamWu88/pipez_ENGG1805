package pipez;

import pipez.core.Block;
import pipez.core.Pipe;
import pipez.core.SimpleBlock;

/**
 * Reverses the order of fields in blocks.
 * e.g. reverse column order in CSV files.
 * 
 * @author whwong
 *
 */
public class ReversePipe implements Pipe{

	private ReversePipe() {}
	
	public static ReversePipe create() {
		return new ReversePipe();
	}
	
	@Override
	public String getName() {
		return "Reverse Order";
	}

	@Override
	public Block transform(Block block) {
		SimpleBlock newBlock = new SimpleBlock();
		String[] fields = block.fields();
		
		for(int i=fields.length-1; i>=0; i--) {
			newBlock.add(fields[i], block.value(fields[i]));
		}
		return newBlock;
	}

}
