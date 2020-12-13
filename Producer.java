package concurrent;

import java.util.Arrays;

public class Producer implements Runnable{

	private final  SingleConsumerBuffer<Integer> buffer;
	private final  int inputSize; 
	private final  int N;
	public Producer(SingleConsumerBuffer<Integer> buffer, int inputSize, int N){
		this.buffer = buffer;
		this.inputSize    = inputSize;
		this.N  = N;
	}
	@Override
	public void run() {
		for(int i = 0; i < N/2; ++i){
			
			for(int digit = 0; digit < inputSize; ++digit)
				buffer.put(digit);
			
		} try {
			Thread.sleep(5);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i = N/2; i < N; ++i){
			for(int digit = 0; digit < inputSize; ++digit)
				buffer.put(digit);
		}
		
	}

}
