package concurrent;

public class Runner {
	public static void main(String[] args){
		int N = 10;
		int PRODUCERS = 5;
		int INPUT_SIZE = 10000;
		Integer[][] output = new Integer[N][PRODUCERS*INPUT_SIZE];
		SingleConsumerBuffer<Integer> buffer = new SingleConsumerBuffer<Integer>(PRODUCERS * INPUT_SIZE);
		Thread consumer = new Thread(new Consumer(buffer, N, output));
		Thread producers[] = new Thread[PRODUCERS];
		consumer.start();
		for(int t = 0; t < PRODUCERS; ++t)
			producers[t] = new Thread(new Producer(buffer, INPUT_SIZE, N));
		for(int t = 0; t < PRODUCERS; ++t)
			producers[t].start();
		for(int t = 0; t < PRODUCERS; ++t){
			try {
				producers[t].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} buffer.turnOff();
		try{consumer.join();} catch(Exception e){;}
		int [] a = new int[INPUT_SIZE];
		for(int i = 0; i < N; ++i){
			for(Integer toPrint : output[i]){
				a[toPrint]++;
			}
			
		}
		for(int i : a){
			if(i != 50)
				System.out.println(a[0]);
		
		}
		
		
		
		
	}

}
