
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.table.DefaultTableModel;


public class ThreadService {
	private final static int NUM_CSV_PARAMETERS = 3;
	private static String url;
	private static int tries;
	private static int waitTime;
	private static int threads;
	private static String username;
	private static String password;
	private static String usernameParam;
	private static String passwordParam;
	
	ThreadService(String url, int tries, 
			int waitTime, int threads, String username, String password, 
			String usernameParam, String passwordParam){
	ThreadService.url = url;
	ThreadService.tries = tries;
	ThreadService.threads = threads;
	ThreadService.username = username;
	ThreadService.password = password;
	ThreadService.usernameParam = usernameParam;
	ThreadService.passwordParam = passwordParam;
	
	ThreadService.main();
	
	}
	 DefaultTableModel model;
	 public static int[] throughputArray;
	 public static Object[][] csvArray;
	 public static ArrayList<String> csvList;
	 public static void main(String ... args) {
		//NEW MULTITHREAD START
		long runTime = 0;
		long endTime = 0;
		long startTime = 0;
		final int MYTHREADS = threads;
		final int CsvLength = MYTHREADS*tries*MainWindow.work.length;
		final int threadHits = tries*MainWindow.work.length;
		throughputArray = new int[MYTHREADS];
		csvArray = new Object[MYTHREADS][threadHits];//array of array of arrays.
		csvList = new ArrayList<String>();
		ExecutorService executor = Executors.newFixedThreadPool(MYTHREADS);
		runTime = 0;
    	startTime = System.currentTimeMillis();
		for (int i = 0; i < MYTHREADS; i++) {
			Runnable worker = new MyThread(url, tries, waitTime, username, password, 
					usernameParam, passwordParam, i);			
			executor.execute(worker);
			
		}
		executor.shutdown();
		// Wait until all threads are finish
		while (!executor.isTerminated()) {
			
		}
		endTime = System.currentTimeMillis();
    	runTime = endTime - startTime;
        
		double sum = 0;
		for (double i : throughputArray)
		    sum += i;
		System.out.println("Number of hits: " + sum);
		System.out.println("runTime: " + runTime);
		double throughput = sum/runTime;
		if(sum>0){
			Helper.printGrid(threadHits, MYTHREADS, MainWindow.filename);	
		}
	}
}

