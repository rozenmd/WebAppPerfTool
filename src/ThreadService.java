
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.table.DefaultTableModel;


public class ThreadService {
	 DefaultTableModel model;
	 public static int[] throughputArray;
	 public static void main(String[] args, String url, int tries, 
			int waitTime, int threads, String username, String password, 
			String usernameParam, String passwordParam, String loginParam) throws Exception{
		//NEW MULTITHREAD START
		long runTime = 0;
		long endTime = 0;
		long startTime = 0;
		final int MYTHREADS = threads;
		throughputArray = new int[MYTHREADS];
		

		ExecutorService executor = Executors.newFixedThreadPool(MYTHREADS);
		runTime = 0;
    	startTime = System.currentTimeMillis();
		for (int i = 0; i < MYTHREADS; i++) {
			Runnable worker = new MyThread(url, tries, waitTime, username, password, 
					usernameParam, passwordParam, loginParam, i);			
			executor.execute(worker);
			
		}
		executor.shutdown();
		// Wait until all threads are finish
		while (!executor.isTerminated()) {
			
		}
		endTime = System.currentTimeMillis();
    	runTime = endTime - startTime;
        
		System.out.println("\nFinished all threads in " + runTime + "ms");
		double sum = 0;
		for (double i : throughputArray)
		    sum += i;
		System.out.println("Number of hits: " + sum);
		//runTime = runTime/1000;
		System.out.println("runTime: " + runTime);
		double throughput = sum/runTime;
		System.out.println("Throughput: " + throughput);
		//NEW MULTITHREAD END		
	
	}
}

