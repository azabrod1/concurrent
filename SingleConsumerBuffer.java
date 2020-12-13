package concurrent;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SingleConsumerBuffer<T> {
	private final Object[][] buffers;
	private Lock lock = new ReentrantLock(); 
	private Condition full = lock.newCondition();
	private Condition notFull = lock.newCondition();
	private final int capacity;
	private int size;
	private int active = 0;
	private boolean off = false;
	public SingleConsumerBuffer(int size){
		this.capacity = size;
		this.buffers = (Object[][]) Array.newInstance(Integer.class, 2, size);
	} 
	
	public Object[]  get() {
		lock.lock();
		while((size != capacity) || off){
			try{full.await();}catch(Exception e){e.printStackTrace();}
		}
		T[] toReturn = (T[]) buffers[active];
		active = active ^ 1; size = 0; 
		notFull.signalAll();
		lock.unlock();
		return (T[]) toReturn;
	}
	
	public void put(T item){
		lock.lock();
		while(size == capacity){
			try{notFull.await();}catch(Exception e){e.printStackTrace();}
		}
		
		buffers[active][size] = item;
		if(++size == capacity)
			full.signal();
		lock.unlock();
	}
	
	public void turnOff(){
		lock.lock();
		off = true;
		full.signal();
		lock.unlock();
		
	}
	

}
