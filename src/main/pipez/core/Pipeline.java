package pipez.core;


import pipez.io.Reader;
import pipez.io.Writer;

public class Pipeline {

	private String name;
	private Reader reader;
	private Writer writer;
	private Piper head, tail; //the head and tail of this pipeline; 
	//the head is the source of initial data or connects to the pipeline's datasource
	// and the tail produces the final output of the pipeline
	
	/**
	 * Piper is an internal wrapper around Pipe for use by this engine.
	 * 
	 * @author whwong
	 *
	 */
	private class Piper{
		
		private class EOSException extends Exception{}; //exception thrown when no more data 
		
		Reader reader;
		Piper pred;
		Pipe pipe;
		
		Piper(Reader read){
			if(read == null) throw new IllegalArgumentException("Reader cannot be null");
//			if(read == null) PipezExceptionHandler.handle(new IllegalArgumentException("Reader cannot be null"));
			this.reader = read;
		}
		
		Piper(Piper pred, Pipe pipe){
			//if(pred == null) throw new IllegalArgumentException("Predecessor cannot be null");
//			if(pipe == null) PipezExceptionHandler.handle(new IllegalArgumentException("Pipe cannot be null"));
			if(pipe == null) throw new IllegalArgumentException("Pipe cannot be null");
			this.pred = pred;
			this.pipe = pipe;
		}
		
		public Block invoke() throws EOSException{
			
			if(reader != null) {
				if(!reader.hasNext()) throw new EOSException();
				return reader.next();
			}
			
			return pipe.transform(pred.invoke());
		}
	}
	
	public static Pipeline create(String name) {
		return new Pipeline(name);
	}

	private Pipeline(String name) {
		this.name = name;
	}
	
//	public Pipeline merge(Pipeline a, Pipeline b) {
//		
//		return this;
//	}
//	
//	public Pipeline[] tee() {
//		
//	}
	
	public Pipeline in(Reader reader) {
		this.reader = reader;
		Piper p = new Piper(reader);
		if(head != null) {
			head.pred = p;
		}
		head = p;
		if(tail == null) tail = p;
		return this;
	}
	
	public Pipeline append(Pipe pipe) {
		Piper p = new Piper(tail, pipe);
		if(head == null) head = p;
		tail = p;
		return this;
	}

	public void out(Writer writer) {
//		if(reader == null) throw new Exception(); //a pipelie can start without a reader e.g. merge of two pipelines
		if(reader != null) reader.open();
		if(head == null || tail == null) {
			PipezExceptionHandler.handle(
					new Exception("Pipeline " + name + " is empty and cannot be executed."));
		}
		
		if(writer == null) {
			PipezExceptionHandler.handle(
					new Exception("Pipeline " + name + " out() cannot be provided with a null Writer argument."));
			return;
		}
		
		this.writer = writer;
		// prepare writer
		writer.open();
		
		// this will run the whole pipeline
		try {
			while(true) {
				Block block = tail.invoke(); // this will run the entire pipeline for a single block
				writer.write(block);
			}
		}catch(Piper.EOSException eos) {
			// end of data 
		}finally {
			if(reader != null) reader.close();
			writer.close();
		}
		
	}
	
}
