package pipez.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import pipez.core.Block;
import pipez.core.PipezExceptionHandler;

public class CSVWriter implements Writer, AutoCloseable {

	File out;
	
	//Write the block to a csv File.
	private CSVWriter(File out) {
		this.out = out;
	}
	
	public static CSVWriter to(String out) {
		return new CSVWriter(new File(out));
	}
	
	Map<String,Integer> field2Idx;
	List<String> headerLine = new LinkedList<>();
	List<Line> lines = new LinkedList<>();
	
	class Line{
		LinkedHashMap<String,String> fields = new LinkedHashMap<>();
	}
	
	@Override
	public void write(Block block) {
		/**
		 * An inefficient way of storing all lines of the file 
		 * in memory until the end of the pipeline.
		 * 
		 */
		Line line = new Line();
		write(block, line);
		lines.add(line);
	}
	
	private void write(Block block, Line line) {
		
		Set<String> currFields = line.fields.keySet();
		for(String f: block.fields()) {
			int i=1;
			while(currFields.contains(f)) {
				f += "_" + i++; 
			}
			line.fields.put(f, block.value(f));
		}
		
		//if any fields not in headerline
		// then add them to the headerline
		Set<String> newFields = new LinkedHashSet<>(line.fields.keySet());
		newFields.removeAll(headerLine);
		
		if(!newFields.isEmpty()) headerLine.addAll(newFields);
		
		//recursively call for embedded blocks;
		// Note: lots of potential bugs here
		for(Block b: block.blocks()) 
			write(b, line);
	}

	BufferedWriter bwrit;
	
	@Override
	public void open() {
		try {
			if(out.isDirectory()) throw new Exception(out.getName() + " is a directory and cannot be used for output.");
			
			bwrit = new BufferedWriter(new FileWriter(out));
		} catch (Exception e) {
			PipezExceptionHandler.handle(e);
		}
	}

	@Override
	public void close() {
		
		if(bwrit == null) open();
		
		if(bwrit != null) {
			try {
				StringBuffer wline = new StringBuffer();
				for(String h: headerLine) {
					wline.append(h);
					wline.append(',');
				}
				if(wline.length() > 0) {
					wline.deleteCharAt(wline.length()-1); // remove trailing delimiter
					bwrit.write(wline.toString());
					bwrit.newLine();
					wline.delete(0, wline.length()); //clear the buffer
				}
				
				for(Line l: lines) {
				
					for(String h: headerLine) {
						String val = l.fields.get(h);
						if(val != null) wline.append(val); // write out value
						wline.append(',');
					}
				
					if(wline.length() > 0) {
						wline.deleteCharAt(wline.length()-1); // remove trailing delimiter
						bwrit.write(wline.toString());
						wline.delete(0, wline.length()); //clear the buffer
					}
					bwrit.newLine();
				}
				
				
				bwrit.flush();
				bwrit.close();
			} catch (IOException e) {
				PipezExceptionHandler.handle(new Exception("Exception when writing output file " + out.getName(),e));
			} finally {
				bwrit = null;
			}
		}
	}
	
	@Override
	public void finalize() {
		/**
		 * To be safe I have used finalize so that by default the 
		 * csv output file will be written when the program terminates.
		 * 
		 */
		close();
	}

}
