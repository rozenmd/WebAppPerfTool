import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.apache.commons.lang3.StringUtils;


public class Helper {
/*Takes input webpage, scans for form elements, returns a list of parameters on the form
 */
	  @SuppressWarnings("finally")
	public List<String> getFormParams(String html) throws UnsupportedEncodingException{
		  List<String> params = new ArrayList<String>();
		  try {
			  Document doc;
			  doc = Jsoup.connect(html).get();
			  Elements inputs = doc.getElementsByTag("input");			
			  for(Element input : inputs){
				  String key = input.attr("name");//this should be the name of the field
				  //Need to figure out how to grab the login POST URI...
					  params.add(key);
			  }
			  return params;
		  	}catch (IOException e) { e.printStackTrace();} 
		  finally { return params;}
	  }
	  	/* Snippet From:
		 * http://stackoverflow.com/questions/4765469/how-to-retrieve-jtable-data-as-an-array
		 */
		public static Object[][] getTableData (JTable table) {
		    DefaultTableModel dtm = (DefaultTableModel) table.getModel();
		    int nRow = dtm.getRowCount(), nCol = dtm.getColumnCount();
		    Object[][] tableData = new Object[nRow][nCol];
		    for (int i = 0 ; i < nRow ; i++)
		        for (int j = 0 ; j < nCol ; j++)
		            tableData[i][j] = dtm.getValueAt(i,j);
		    return tableData;
		}
		/* 
		 * Threadsafe (more or less) method to populate the CSV array using threadNo's
		 */
		public static void populateCsvArray(String url, long responseTime, int hitNo, int threadNo){
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
			Date date = new Date();
			String dateVal = dateFormat.format(date);
			Object[] tempArray = {dateVal, url, responseTime};
			String temp = StringUtils.join(tempArray, ',');
			ThreadService.csvArray[threadNo][hitNo] = temp;
			
		}
		/*
		 * adapted from http://www.mkyong.com/java/how-to-append-content-to-file-in-java/
		 * NOTE this code appends!!
		 * If you want a new file each time, change this!
		 */
		public static void printGrid(int x, int y, String filename)
		{
			try{
				File file;
				if(filename != null){
					 file =new File(filename);
				}else{
					 file =new File("wapt-output-file.csv");	
				}
				System.out.println(file.getAbsolutePath().toString());
	    		if(!file.exists()){
	    			file.createNewFile();
	    		}
	    		FileWriter fileWritter = new FileWriter(filename,true);
		        BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
		        
				for(int i = 0; i < y; i++){
					for(int j = 0; j < x; j++){
						if(ThreadService.csvArray[i][j] != null){
							String toFile = ThreadService.csvArray[i][j].toString() + "\n";
							bufferWritter.write(toFile);	
						}else{
							String toFile = "null,timeout,-1" + "\n";
							bufferWritter.write(toFile);
						}
					}
				}
				bufferWritter.close();
			}catch(IOException e){
	    		e.printStackTrace();
	    	}
    		
		}
}
