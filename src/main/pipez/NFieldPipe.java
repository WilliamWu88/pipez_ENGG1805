package pipez;

import java.util.Arrays;

import pipez.core.Block;
import pipez.core.Pipe;
import pipez.core.SimpleBlock;

/**
 * Selects the n-th field of each block; n=1 corresponds to the first column.
 * e.g. select the n-th column of a CSV file.
 * 
 * Also allows multiple fields to be selected.
 * 
 * @author whwong
 *
 */
public class NFieldPipe implements Pipe {

	public static NFieldPipe create(int n) {
		return new NFieldPipe(n);
	}

	public static NFieldPipe create(int... ns) {
		return new NFieldPipe(ns);
	}
	
	int[] ns;
	private NFieldPipe(int... ns) {
		this.ns = ns;
		for(int i=0;i<ns.length;i++) ns[i]--;
		Arrays.sort(this.ns);
	}
	
	
	@Override
	public String getName() {
		return "n-th field pipe";
	}

	@Override
	public Block transform(Block block) {
		SimpleBlock newBlock = new SimpleBlock();
	
		int stop = 0;
		String[] fields = block.fields();
		for(int i=0; i< fields.length && i <= ns[ns.length-1]; i++) {
			if(i == ns[stop]) {
				newBlock.add(fields[i], block.value(fields[i]));
				stop++;
			}
		}
		
		return newBlock;
	}

}
