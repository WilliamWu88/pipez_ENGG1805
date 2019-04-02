package pipez.io;

import pipez.core.Block;

public interface Reader {

	public void open();
	public boolean hasNext();
	public Block next();
	public void close();
}
