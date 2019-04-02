package pipez.core;

import java.util.LinkedHashMap;
import java.util.LinkedList;

public class SimpleBlock implements Block{

	LinkedHashMap<String,String> block; //needed to preserve ordering
	LinkedList<Block> blocks;
	
	public SimpleBlock() {
		block = new LinkedHashMap<>();
		blocks = new LinkedList<>();
	}
	
	public void addEmbeddedBlock(Block block){
		blocks.add(block);
	}
	
	public void add(String field, String value) {
		block.put(field, value);	
	}
	
	@Override
	public String value(String field) {
		return block.get(field);
	}

	@Override
	public String[] fields() {
		
		return block.keySet().toArray(new String[block.size()]);
	}

	@Override
	public Block[] blocks() {
		return blocks.toArray(new Block[blocks.size()]);
	}

}
