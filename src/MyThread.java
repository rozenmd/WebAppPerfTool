import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.table.DefaultTableModel;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;


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
	
	private final int threadNo;
	DefaultTableModel model;
	//Creates a thread using all the params
	MyThread(String url, int tries, int wait, String username, 
			String password, String usernameParam, 
			String passwordParam, int threadNo) {
		
		this.url = url;
		this.tries = tries;
		this.wait = wait;
		this.username = username;
		this.password = password;
		this.usernameParam = usernameParam;
		this.passwordParam = passwordParam;
		this.threadNo = threadNo;
	}

	@Override
	//The actual thread code
	
	public void run(){
		int countHits=0;
		for(int i = 0; i<tries; i++){
			long responseTime = 0;
			long endTime = 0;
			long startTime = 0;
			boolean gotCookies = false;
			//For cookie storage
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
					//Check if we're scraping cookies from the page
			        if(tempIsLogin){
			        	//First connect to the login form
			        	responseTime = 0;
			        	startTime = System.currentTimeMillis();
			        	//Scrape cookies, save to loginForm var
			        	loginForm = Jsoup.connect(tempUrl).timeout(0).method(Connection.Method.GET).execute();
			        	endTime = System.currentTimeMillis();
			        	responseTime = endTime - startTime;
			        	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
						Date date = new Date();
						String dateVal = dateFormat.format(date);
						Object[] tempArray = {dateVal, tempUrl, responseTime};
						String temp = StringUtils.join(tempArray, ',');
						ThreadService.csvArray[threadNo][countHits] = temp;

			        	gotCookies = true;
			        	countHits++;
			        }else if(tempPOSTLogin){
			        	if(gotCookies){//We have the cookies, now we can login
			        		responseTime = 0;
				        	startTime = System.currentTimeMillis();
				        	//line below is kept to verify login succeeds 
				        	//Once doc is retrieved, print it to console and check webpage for "Welcome to <SOME APP>, $USER" 
				        	//Or something similar
			        		//Document doc = 
				        	Jsoup.connect(tempUrl)
				        			.data(usernameParam, username)
				        			.data(passwordParam, password)
		        			        //.data(loginParam, loginParam) //a "login param" is sometimes needed, depends on web app implementation
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
							Object[] tempArray = {dateVal, tempUrl, responseTime};
							String temp = StringUtils.join(tempArray, ',');
							ThreadService.csvArray[threadNo][countHits] = temp;
			        		countHits++;
			        	}
			        }
			        else{
			        	responseTime = 0;
			        	startTime = System.currentTimeMillis();
	        			Jsoup.connect(tempUrl).timeout(0).ignoreHttpErrors(true).get();
			        	endTime = System.currentTimeMillis();
		        		responseTime = endTime - startTime;
		        		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
						Date date = new Date();
						String dateVal = dateFormat.format(date);
						Object[] tempArray = {dateVal, tempUrl, responseTime};
						String temp = StringUtils.join(tempArray, ',');
						ThreadService.csvArray[threadNo][countHits] = temp;
		        		countHits++;
			        }
				}
				ThreadService.throughputArray[threadNo] = countHits;

			} catch (Exception e) {
				
				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
				Date date = new Date();
				String dateVal = dateFormat.format(date);
				Object[] tempArray = {dateVal, "timeout", "-1"}; //this was to stop exceptions in writing the CSV
				//there are sometimes timeouts that can't be debugged, so we had to set ping to -1
				String temp = StringUtils.join(tempArray, ',');
				ThreadService.csvArray[threadNo][countHits] = temp;
			}
			
			try {
				if(wait > 0){//In case the user wants to test with user "thinking time", wait time is provided
					Thread.sleep(wait);	
				}
			                    
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}	
		}
	}
}
