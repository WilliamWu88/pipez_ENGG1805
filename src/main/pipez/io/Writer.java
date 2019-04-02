package pipez.io;

import pipez.core.Block;

public interface Writer {

	public void open();
	public void write(Block block);
	public void close();
}
