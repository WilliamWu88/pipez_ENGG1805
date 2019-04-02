package pipez.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import pipez.core.Block;
import pipez.core.PipezExceptionHandler;
import pipez.core.SimpleBlock;

public class CSVReader implements Reader {

	private File in;
	private Scanner sc;
	
	private CSVReader(File in) {
		this.in = in;
	}
	
	public static CSVReader from(String in) {
		return new CSVReader(new File(in));
	}
	
	@Override
	public boolean hasNext() {
		if(sc == null) open();  //If firstly the scanner is null, open the scanner
		return sc.hasNextLine();  //Scanner reads the file and detects whether it reaches the end.
	}

	@Override
	public Block next() {
		if(sc == null) open();  //If firstly the scanner is null, open the scanner
		if(! sc.hasNextLine()) return Block.EMPTY_BLOCK;  //If reaches the end of the file, return Block
		
		String line = sc.nextLine();  //Read a line
		return parse(line);  //parse the line
	}

	/**
	 * A simple parser that tokenizes on the simple comma.
	 * 
	 * @param line
	 * @return
	 */
	private Block parse(String line) {
		
		String[] fields = line.split(","); // be careful of empty lines here - split() will return the original 
										   // line in fields[0] if nothing matched the split pattern
		
		int i=1;
		SimpleBlock block = new SimpleBlock();
		if(line.length() > 0)
			for(String f: fields) {  //Add a line to corresponding fields.
				block.add("V"+i++, f);  //We have each field with Index Vi
			}
		return block;
	}

	@Override
	/**
	 * Open the scanner and throws FileNotFound Exception
	 */
	public void open() {
		try {
			sc = new Scanner(in);
		} catch (FileNotFoundException e) {
			PipezExceptionHandler.handle(e);
		}
	}

	@Override
	/**
	 * Close the scanner
	 */
	public void close() {
		sc.close();
	}
}
