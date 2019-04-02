package pipez.core;

public interface Block {
	/**
	 * Returns fields in this block. The ordering is preserved when 
	 * the block is derived from applicable data sources e.g. 
	 * column ordering is preserved for Block derived from a CSV file.
	 * 
	 * @return fields in this block.
	 */
	public String[] fields();
	
	/**
	 * Returns the value associated with a field
	 * 
	 * @param field
	 * @return value associated with field parameter
	 */
	public String value(String field);
	
	/**
	 * Return the embedded blocks of this block.
	 * @return
	 */
	public Block[] blocks();
	
	public static Block EMPTY_BLOCK = new Block() {

		@Override
		public String value(String field) {
			return null;
		}

		String[] fields = {};
		Block[] blocks = new Block[0];
		
		@Override
		public String[] fields() {
			return fields;
		}

		@Override
		public Block[] blocks() {
			return blocks;
		}
	};
}
