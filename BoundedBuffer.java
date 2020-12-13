package concurrent;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class BoundedBuffer<T> {
	private AtomicReferenceArray<T> elements;
	private AtomicInteger head, tail;
	protected final int capacity;
	public BoundedBuffer(int capacity) {
		this.capacity = capacity;
		this.elements = new  AtomicReferenceArray<>(capacity);
		this.head     = new  AtomicInteger(0);
		this.tail     = new  AtomicInteger(0);

	}
	
	public void put(T item){
		int currHead, currTail;
		
		do{ currHead = head.get(); currTail = tail.get();
		if(currHead == currTail - 1){
			try { Thread.sleep(1); } catch (InterruptedException e) {	e.printStackTrace(); }
			
		} else{
			
			
			
			
			
			
		}
		
			
			
		}while(true);
		
	
}
	
	
	
	
	
	
	
}