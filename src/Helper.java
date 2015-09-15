import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



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

}
