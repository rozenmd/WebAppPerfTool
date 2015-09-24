import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.table.DefaultTableModel;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class MyThread implements Runnable {
	private final int colPagename = 0;
	private final int colIsLogin = 1;
	private final int colPOSTLogin = 2;
	private final String url;
	private final int tries;
	private final int wait;
	private final String username;
	private final String password;
	private final String usernameParam;
	private final String passwordParam;
	private final String loginParam;
	private final int threadNo;
	
	


	DefaultTableModel model;
	MyThread(String url, int tries, int wait, String username, 
			String password, String usernameParam, 
			String passwordParam, String loginParam, int threadNo) {
		
		this.url = url;
		this.tries = tries;
		this.wait = wait;
		this.username = username;
		this.password = password;
		this.usernameParam = usernameParam;
		this.passwordParam = passwordParam;
		this.loginParam = loginParam;
		this.threadNo = threadNo;
	}

	@Override
	public void run(){
		int countHits=0;
		//System.out.println("Thread: " +threadNo+ " started");
		for(int i = 0; i<tries; i++){
			long responseTime = 0;
			long endTime = 0;
			long startTime = 0;
			boolean gotCookies = false;
			Connection.Response loginForm = null;
			try{				
				for(int j = 0; j < MainWindow.work.length; j++){
					String tempPage;
					boolean tempIsLogin, tempPOSTLogin;
					String tempUrl = this.url;
					
					tempPage =  MainWindow.work[j][colPagename].toString();
					tempUrl += tempPage;
					tempIsLogin = (boolean) MainWindow.work[j][colIsLogin];
					tempPOSTLogin = (boolean) MainWindow.work[j][colPOSTLogin];
			        if(tempIsLogin){
			        	//First connect to the login form
			        	responseTime = 0;
			        	startTime = System.currentTimeMillis();
			        	loginForm = Jsoup.connect(tempUrl).timeout(0).method(Connection.Method.GET).execute();
			        	endTime = System.currentTimeMillis();
			        	responseTime = endTime - startTime;
//			        	Helper.populateCsvArray(tempUrl, responseTime, countHits, this.threadNo);
			        	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
						Date date = new Date();
						String dateVal = dateFormat.format(date);
						Object[] tempArray = {dateVal, url, responseTime};
						String temp = StringUtils.join(tempArray, ',');
						ThreadService.csvArray[threadNo][countHits] = temp;
			        	//System.out.println(dateFormat.format(date) + "," + tempUrl + "," + responseTime)
			        	gotCookies = true;
			        	countHits++;
			        }else if(tempPOSTLogin){
			        	if(gotCookies){
			        		responseTime = 0;
				        	startTime = System.currentTimeMillis();
			        		//Document doc = 
				        	Jsoup.connect(tempUrl)
				        			.data(usernameParam, username)
				        			.data(passwordParam, password)
		        			        .data(loginParam, loginParam)
	        			            .cookies(loginForm.cookies())
				        			.userAgent("Mozilla")
				        			.timeout(0)
				        			.post();
			        		endTime = System.currentTimeMillis();
			        		responseTime = endTime - startTime;
			        		//Helper.populateCsvArray(tempUrl, responseTime, countHits, this.threadNo);
			        		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
							Date date = new Date();
							String dateVal = dateFormat.format(date);
							Object[] tempArray = {dateVal, url, responseTime};
							String temp = StringUtils.join(tempArray, ',');
							ThreadService.csvArray[threadNo][countHits] = temp;
			        		//
			        		countHits++;
				        	//System.out.println(doc);
			        	}
			        }
			        else{
			        	responseTime = 0;
			        	startTime = System.currentTimeMillis();
			        	//Document doc = 
			        			Jsoup.connect(tempUrl).timeout(0).ignoreHttpErrors(true).get();
			        	//System.out.println(doc);
			        	endTime = System.currentTimeMillis();
		        		responseTime = endTime - startTime;
		        		//Helper.populateCsvArray(tempUrl, responseTime, countHits, this.threadNo);
		        		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
						Date date = new Date();
						String dateVal = dateFormat.format(date);
						Object[] tempArray = {dateVal, url, responseTime};
						String temp = StringUtils.join(tempArray, ',');
						ThreadService.csvArray[threadNo][countHits] = temp;
		        		//
		        		countHits++;
			        }
				}
				//System.out.println("countHits: " + countHits);
				ThreadService.throughputArray[threadNo] = countHits;
				//System.out.println("throughPutArray: " + ThreadService.throughputArray[threadNo]);
				//System.out.println("Thread finished");

			} catch (Exception e) {
				
			//	System.err.println("Got an exception! ");
				e.printStackTrace();
			//	System.exit(1);
			}
			
			try {
			    Thread.sleep(wait);                
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}	
		}
		//System.out.println("Thread: " +threadNo+ " ended");
	}
	
}
