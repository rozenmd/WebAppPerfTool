import java.net.URL;

import javax.swing.table.DefaultTableModel;

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
	
	DefaultTableModel model;
	MyThread(String url, int tries, int wait, String username, 
			String password, String usernameParam, String passwordParam, String loginParam) {
		
		this.url = url;
		this.tries = tries;
		this.wait = wait;
		this.username = username;
		this.password = password;
		this.usernameParam = usernameParam;
		this.passwordParam = passwordParam;
		this.loginParam = loginParam;
	}

	@Override
	public void run(){
		for(int i = 0; i<tries; i++){
			long ping = 0;
			long endTime = 0;
			long startTime = 0;
			boolean gotCookies = false;
			Connection.Response loginForm = null;
			//String result = "";
			try{				
				
				//connection goes here
				//ExampleWebTestCase test = new ExampleWebTestCase();
                //Document d = Jsoup.connect(url).get();
				
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
			        	//Document doc = Jsoup.connect(tempUrl).get();
			        	startTime = System.currentTimeMillis();
			        	loginForm = Jsoup.connect(tempUrl).method(Connection.Method.GET).execute();
	        			gotCookies = true;
			        	endTime = System.currentTimeMillis();
						ping = endTime - startTime;
			        }else if(tempPOSTLogin){
			        	
			        	if(gotCookies){
			        		//Should I care that we're double hitting this resource?
			        		
			        		Document doc = Jsoup.connect(tempUrl)
				        			.data(usernameParam, username)
				        			.data(passwordParam, password)
		        			        .data(loginParam, loginParam)
	        			            .cookies(loginForm.cookies())
				        			.userAgent("Mozilla")
				        			.post();
				        	
				        	System.out.println(doc);	
			        	}
			        }
			        else{
			        	startTime = System.currentTimeMillis();
			        	Document doc = Jsoup.connect(tempUrl).get();
			        	System.out.println(doc);
			        	endTime = System.currentTimeMillis();
			        	ping = endTime - startTime;
			        }
				}
				System.out.println("Thread finished");

			} catch (Exception e) {
				//result = "Exception/Offline,";	
			}
			System.out.println(url + "," + ping);
			try {
			    Thread.sleep(wait);                
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}	
		}	
	}
	
}
