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
/*Takes username, password and concatenates into a parameter string
 * 
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
				  //String value = input.attr("value");//this should be empty, always,
				  //Need to figure out how to grab the login POST URI...
				
					  params.add(key);
			}
			return params;
		  } catch (IOException e) {
				e.printStackTrace();
			} finally {
				return params;
			}
	  }
	  	/* From:
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
		 * 
		 */
		public static void populateCsvArray(String url, long responseTime, int hitNo, int threadNo){
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
			Date date = new Date();
			String dateVal = dateFormat.format(date);
			Object[] tempArray = {dateVal, url, responseTime};
			String temp = StringUtils.join(tempArray, ',');
			ThreadService.csvArray[threadNo][hitNo] = temp;
			//System.out.println(temp);
			//ThreadService.csvList.add(temp);
        	//ThreadService.csvArray[j+i+threadNo] = tempArray;
        	//System.out.println("RowCount: " + ThreadService.csvList.size());
		}
		/*
		 * inspired by http://www.mkyong.com/java/how-to-append-content-to-file-in-java/
		 */
		public static void printGrid(int x, int y)
		{
			try{
				File file =new File("javaio-appendfile.csv");
	    		if(!file.exists()){
	    			file.createNewFile();
	    		}
	    		FileWriter fileWritter = new FileWriter(file.getName(),true);
		        BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
		        
				for(int i = 0; i < y; i++){
					for(int j = 0; j < x; j++){
						
						String toFile = ThreadService.csvArray[i][j].toString() + "\n";
						bufferWritter.write(toFile);
						//System.out.println(toFile);
					}
				}
				bufferWritter.close();
			}catch(IOException e){
	    		e.printStackTrace();
	    	}
    		
		}
}
