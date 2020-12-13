package concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class ImageEdit {
	private boolean  [][] prev;
	private boolean  [][] curr;
	private volatile boolean stable; 
	private Thread[]      workers;
	private CyclicBarrier barrier;
	private final int     T;
	private       int     N;
	private       int rounds;


	public ImageEdit(int numThreads){
		this.T       = numThreads;
		this.barrier = new CyclicBarrier(T);
	}

	public boolean[][] smoothImage(boolean[][] image) throws InterruptedException{
		this.N       = image.length;
		this.prev    = image;
		this.curr    = new boolean[N][N];
		this.rounds  = 0;
		this.workers = new Thread[T];
		
		int rows_per = N/T;

		
		for(int t = 0; t < T - 1; ++t)
			workers[t] = new Thread(new Smoothing(t*rows_per, (t+1)*rows_per - 1, t));
		
		workers[T-1]   = new Thread(new Smoothing((T-1)*rows_per, N-1, T-1));
		for(Thread worker : workers)
			worker.start();
		for(Thread worker : workers)
			worker.join();
		System.out.println("Rounds taken:" + rounds);
		return curr;
		
	}


	private class Smoothing implements Runnable{
		private final int startRow, endRow, me;
		private final static int MAX_ROUNDS = 10000;

		public Smoothing(int startRow, int endRow, int me){
			this.startRow = startRow; this.endRow = endRow; this.me = me;
		}
		@Override
		public void run() {
			do{
			for(int row = startRow; row <= endRow; ++row)
				for(int col = 0; col < N; ++col)
					curr[row][col] = assignMark(row,col);
			
			
					

			try {
				barrier.await();
			} catch (InterruptedException | BrokenBarrierException e) {	e.printStackTrace(); }

			if(me == 0){ //One thread in change of checking stability
				stable = isStable();
				boolean[][] temp = prev;
				prev = curr; curr = temp; ++rounds;
				
			}
			
			try {
				barrier.await(); } catch (InterruptedException | BrokenBarrierException e) {e.printStackTrace(); }
			
			}while(rounds < MAX_ROUNDS && !stable);
		}

		private boolean isStable(){
			for(int r = 0; r < N; ++r)
				for(int c = 0; c < N; ++c)
					if(prev[r][c] != curr[r][c])
						return false;

			return true;
		}

		private boolean assignMark(int row, int col){
			int majority = 0;
			for(int r = Math.max(row - 1, 0); r < Math.min(N, row + 2); ++r){
				for(int c = Math.max(col - 1, 0); c < Math.min(N, col + 2); ++c){
					if(prev[r][c]) ++majority; else --majority;
				}
			}
			return majority > 0;
		}

	}
}
