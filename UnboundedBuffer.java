package concurrent;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

public class UnboundedBuffer<T> {

	private Queue<T> buf = new ArrayDeque<T>();
	
	public synchronized void put(T item){
		buf.add(item); 
		if(buf.size() == 1)
			notifyAll(); //
	}
	
	public synchronized T get() throws InterruptedException{
		while(buf.isEmpty())
			wait();
		return buf.remove();
		
	}
}
