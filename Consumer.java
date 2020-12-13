package concurrent;

import java.util.Arrays;

public class Consumer implements Runnable{

	private final  SingleConsumerBuffer<Integer> buffer;
	private final  int N; 
	private final  Integer[][] toFill;
	public Consumer(SingleConsumerBuffer<Integer> buffer, int N, Integer[][] toFill){
		this.buffer = buffer;
		this.N      = N;
		this.toFill = toFill;
	}
	@Override
	public void run() {
		for(int i = 0; i < N/2; ++i){
			Integer[] toProcess =  (Integer[]) buffer.get();
			toFill[i] = Arrays.copyOf(toProcess, toProcess.length);
			//System.out.println(toFill[0]);
			
		} try {
			Thread.sleep(5);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i = N/2; i < N; ++i){
			Integer[] toProcess = (Integer[]) buffer.get();
			toFill[i] = Arrays.copyOf(toProcess, toProcess.length);
		}
		
	}

}
