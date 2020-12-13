package concurrent;

import java.util.Random;

public class ImageFactory {
	final static int N = 30;
	public static void main(String[] args) {
		boolean[][] image = randomImage(N);
		ImageEdit   ed    = new ImageEdit(4);
		printImage(image);
		try {
			System.out.println("\n\n\n"); printImage(ed.smoothImage(image));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		System.out.println("\n\n"); 

		

	}
	
	public static void printImage(boolean[][] image){
		
		for(boolean[] array : image){
			for(boolean element  : array){
				System.out.print(element? '*' : ' ');
			} 
			System.out.println();
		}
		
	}
	
	public static boolean[][] randomImage(int N){
		boolean[][] image = new boolean[N][N];
		Random rand       = new Random();
		for(boolean[] array : image)
			for(int i = 0; i < N; ++i)
				array[i] = rand.nextBoolean();
			
		return image;
		
	}
	
	

}
