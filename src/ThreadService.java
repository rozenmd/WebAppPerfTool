
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.table.DefaultTableModel;

public class ThreadService {
	 DefaultTableModel model;
	public static void main(String[] args, String url, int tries, 
			int waitTime, int threads, String username, String password, 
			String usernameParam, String passwordParam, String loginParam) throws Exception{
		//NEW MULTITHREAD START
		
		final int MYTHREADS = threads;
		ExecutorService executor = Executors.newFixedThreadPool(MYTHREADS);
		//System.out.println("URL,Ping");
		
		for (int i = 0; i < MYTHREADS; i++) {
			Runnable worker = new MyThread(url, tries, waitTime, username, password, usernameParam, passwordParam, loginParam);			
			executor.execute(worker);
			
		}
		executor.shutdown();
		// Wait until all threads are finish
		while (!executor.isTerminated()) {
			
		}
		System.out.println("\nFinished all threads");
		//NEW MULTITHREAD END		
	
	}
}

