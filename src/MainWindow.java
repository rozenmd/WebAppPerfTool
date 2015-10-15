import java.awt.EventQueue;
import java.awt.FileDialog;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.JFileChooser;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.prefs.Preferences;

import javax.swing.JList;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JCheckBox;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.lang3.StringUtils;


public class MainWindow {

	private JFrame frmWebAppPerformance;
	private JTextField waitTime;
	private JTextField numTries;
	private JTextField numThreads;
	private JTextField txtUsername;
	public static Object[][] work; 
	private JTextField txtPassword;
	private JTextField pageName;
	public JTable pageTable;
	private String usernameParam;
	private String passwordParam;
	
	private Preferences prefs = Preferences.userNodeForPackage(this.getClass());
	private boolean RELOAD_OLD_PREFS = false;
	final String BASE_URL = "base_url";
	final String USERNAME = "username";
	final String PASSWORD = "password";
	final String USER_PARAM = "user_param";
	final String PASS_PARAM = "pass_param";
	final String LOGIN_PARAM = "login_param";
	final String WAIT_TIME = "wait_time";
	final String NUM_TRIES = "num_tries";
	final String NUM_THREADS = "num_threads";
	final String REMEMBER = "remember_old_settings";
	final String RESULTS_DIR = "results_dir";
	public static String filename = "";
	JFileChooser chooser;
	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) throws Exception{
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frmWebAppPerformance.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmWebAppPerformance = new JFrame();
		frmWebAppPerformance.setTitle("Web App Performance Test Tool");
		frmWebAppPerformance.setBounds(100, 100, 606, 395);
		frmWebAppPerformance.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JCheckBox chckbxLoginPage = new JCheckBox("Login Form?");
		JCheckBox chckbxPostLoginUri = new JCheckBox("POST Login URI?");
		DefaultTableModel model = new DefaultTableModel();
		JPanel panel = new JPanel();
		JList<String> list = new JList<String>();
		frmWebAppPerformance.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		
		JTextField urlField = new JTextField();
		urlField.setText("http://");
		urlField.setBounds(119, 34, 465, 28);
		urlField.setColumns(10);
		panel.add(urlField);
		
		JButton btnRun = new JButton("Run");
		btnRun.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String url = urlField.getText();
				int tries = Integer.parseInt(numTries.getText());
				int wait = Integer.parseInt(waitTime.getText());
				int threads = Integer.parseInt(numThreads.getText());
				String username = txtUsername.getText();
				String password = txtPassword.getText();
				work = Helper.getTableData(pageTable);
				ThreadService runThread = new ThreadService(url, tries, wait, threads, username, password, usernameParam, passwordParam);
				
			}
		});
		btnRun.setBounds(454, 333, 117, 29);
		panel.add(btnRun);
		
		
		
		JLabel lblUrl = new JLabel("Base URI");
		lblUrl.setBounds(16, 40, 71, 16);
		panel.add(lblUrl);
		
		JLabel lblWaittime = new JLabel("Wait time (ms)");
		lblWaittime.setBounds(16, 68, 97, 16);
		panel.add(lblWaittime);
		
		waitTime = new JTextField();
		waitTime.setText("100");
		waitTime.setColumns(10);
		waitTime.setBounds(119, 62, 86, 28);
		panel.add(waitTime);
		
		numTries = new JTextField();
		numTries.setText("1");
		numTries.setColumns(10);
		numTries.setBounds(301, 62, 86, 28);
		panel.add(numTries);
		
		JLabel lblNumtries = new JLabel("# of tries");
		lblNumtries.setBounds(217, 68, 71, 16);
		panel.add(lblNumtries);
		
		numThreads = new JTextField();
		numThreads.setText("1");
		numThreads.setColumns(10);
		numThreads.setBounds(511, 62, 71, 28);
		panel.add(numThreads);
		
		JLabel lblNumthreads = new JLabel("# of threads");
		lblNumthreads.setBounds(413, 68, 86, 16);
		panel.add(lblNumthreads);
		JButton btnListParameters = new JButton("List Parameters");
		btnListParameters.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String url = urlField.getText();
				Helper test = new Helper();
				try {
					DefaultListModel<String> model = new DefaultListModel<String>();
					List<String> paramList;
					paramList = test.getFormParams(url);
					for(int i = 0; i<paramList.size(); i++){
						model.addElement(paramList.get(i));
					}
					list.setModel(model);
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnListParameters.setBounds(444, 96, 163, 29);
		panel.add(btnListParameters);
		
		txtUsername = new JTextField();
		txtUsername.setText("");
		txtUsername.setBounds(88, 182, 134, 28);
		panel.add(txtUsername);
		txtUsername.setColumns(10);
		
		txtPassword = new JTextField();
		txtPassword.setText("");
		txtPassword.setBounds(296, 182, 134, 28);
		panel.add(txtPassword);
		txtPassword.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(16, 188, 81, 16);
		panel.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(231, 188, 71, 16);
		panel.add(lblPassword);
		//DefaultListModel<String> pageModel = new DefaultListModel<String>();
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_1.setBounds(30, 283, 400, 80);
		panel.add(scrollPane_1);
		
		// Append a row 
		//model.addRow(new Object[]{"wp-login.php", true, false});
		//model.addRow(new Object[]{"wp-login.php", false, true});
		pageTable = new JTable();
		scrollPane_1.setViewportView(pageTable);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(30, 96, 400, 76);
		panel.add(scrollPane);
	
		scrollPane.setViewportView(list);
		
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JCheckBox chckbxRememberSettings = new JCheckBox("Remember Settings?");
		chckbxRememberSettings.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				RELOAD_OLD_PREFS = chckbxRememberSettings.isSelected();
			}
		});
		chckbxRememberSettings.setBounds(306, 199, 163, 41);
		panel.add(chckbxRememberSettings);
		RELOAD_OLD_PREFS = prefs.getBoolean(REMEMBER, false);
		chckbxRememberSettings.setSelected(RELOAD_OLD_PREFS);
		if(RELOAD_OLD_PREFS){
			model = (DefaultTableModel) getModel();
			if(model.getColumnCount() == 0){
				model.addColumn("Page URI"); 
				model.addColumn("Is Login Form?");
				model.addColumn("Is Login POST URI?");	
			}else{
				Vector<String> objVector = new Vector<String>(3);
				objVector.add("Page URI");
				objVector.add("Is Login Form?");
				objVector.add("Is Login POST URI?");
				model.setColumnIdentifiers(objVector);
				
			}	
		}else{
			if(model.getColumnCount() == 0){
				model.addColumn("Page URI"); 
				model.addColumn("Is Login Form?");
				model.addColumn("Is Login POST URI?");
				model.setRowCount(0);
			}
		}
		
		pageTable.setModel(model);
		
		
		pageName = new JTextField();
		pageName.setBounds(112, 227, 202, 28);
		panel.add(pageName);
		pageName.setColumns(10);
		
		JButton btnAddPage = new JButton("Add");
		
		btnAddPage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (pageName.getText() != null){
					((DefaultTableModel) pageTable.getModel()).addRow(new Object[]{pageName.getText(), chckbxLoginPage.isSelected(), chckbxPostLoginUri.isSelected()});
				}
			}
		});
		btnAddPage.setBounds(478, 228, 93, 29);
		panel.add(btnAddPage);
		
		JLabel lblPageToTest = new JLabel("Page to test");
		lblPageToTest.setBounds(16, 229, 86, 24);
		panel.add(lblPageToTest);
		
		JButton btnRemove = new JButton("Remove");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				((DefaultTableModel) pageTable.getModel()).removeRow(pageTable.getSelectedRow());
				
			}
		});
		btnRemove.setBounds(478, 251, 93, 29);
		panel.add(btnRemove);
		
		
		
		
		chckbxLoginPage.setBounds(306, 229, 128, 23);
		panel.add(chckbxLoginPage);
		
		JButton btnSetUserparameter = new JButton("Set UserParameter");
		btnSetUserparameter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!list.isSelectionEmpty()){
					if(list.getSelectedIndices().length == 1){
						//set userparam
						usernameParam = list.getSelectedValue();
					}else{
						JOptionPane.showMessageDialog(null, "Please only select one value", "Error!", JOptionPane.ERROR_MESSAGE);	
					}
				}
			}
		});
		btnSetUserparameter.setBounds(444, 122, 163, 29);
		panel.add(btnSetUserparameter);
		
		JButton btnSetPassparameter = new JButton("Set PassParameter");
		btnSetPassparameter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!list.isSelectionEmpty()){
					if(list.getSelectedIndices().length == 1){
						//set userparam
						passwordParam = list.getSelectedValue();
					}else{
				        JOptionPane.showMessageDialog(null, "Please only select one value", "Error!", JOptionPane.ERROR_MESSAGE);

					}
				}
			}
		});
		btnSetPassparameter.setBounds(444, 148, 165, 29);
		panel.add(btnSetPassparameter);
		
		
		chckbxPostLoginUri.setBounds(306, 252, 149, 23);
		panel.add(chckbxPostLoginUri);
		
		JMenuBar menuBar = new JMenuBar();
		JMenu menu;
		menuBar.setBounds(0, 0, 607, 22);
		panel.add(menuBar);
		menu = new JMenu("File");
		menuBar.add(menu);

		
		JMenuItem exitItem, saveSettings, importSettings, exportSettings;

		saveSettings = new JMenuItem("Save Settings");
		saveSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//save all settings to prefs
				if(RELOAD_OLD_PREFS){
					if(urlField.getText() != null){prefs.put(BASE_URL,urlField.getText());}
					if(txtUsername.getText() != null){prefs.put(USERNAME,txtUsername.getText());}
					if(txtPassword.getText() != null){prefs.put(PASSWORD,txtPassword.getText());}
					if(usernameParam != null){prefs.put(USER_PARAM,usernameParam);}
					if(passwordParam != null){prefs.put(PASS_PARAM,passwordParam);}
					prefs.putBoolean(REMEMBER, RELOAD_OLD_PREFS);	
					if(waitTime.getText() != null){prefs.put(WAIT_TIME,waitTime.getText());}					
					if(numTries.getText() != null){prefs.put(NUM_TRIES,numTries.getText());}					
					if(numThreads.getText() != null){prefs.put(NUM_THREADS,numThreads.getText());}
					storeTableModel((DefaultTableModel) pageTable.getModel());
					if(chooser != null){prefs.put(RESULTS_DIR, chooser.getSelectedFile().getPath());}
				}
			}
		});
		menu.add(saveSettings);
		
		
		
		exitItem = new JMenuItem("Exit");
		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		menu.add(exitItem);
		
		JButton btnSetOutputFile = new JButton("Set Output File");
		btnSetOutputFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			   String choosertitle = "Choose a folder";
				   
				 chooser = new JFileChooser(); 
				    //chooser.setCurrentDirectory(new java.io.File("."));
				    chooser.setDialogTitle(choosertitle);
				    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				    //
				    // disable the "All files" option.
				    //
				    chooser.setAcceptAllFileFilterUsed(false);
				    //    
				    
				    if (chooser.showOpenDialog(btnAddPage) == JFileChooser.APPROVE_OPTION) { 
				    	filename = chooser.getCurrentDirectory().toString();
				    	System.out.println("getCurrentDirectory(): " 
				         +  filename);
				    	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
						Date date = new Date();
						String dateVal = dateFormat.format(date);
				    	filename = chooser.getSelectedFile().toString() + "/wapt-tool-output-" + dateVal + ".csv";
				      System.out.println("getSelectedFile() : " 
				         +  chooser.getSelectedFile());
				      }
				    else {
				      System.out.println("No Selection ");
				      }

			}
		});
		btnSetOutputFile.setBounds(444, 175, 165, 29);
		panel.add(btnSetOutputFile);
		
		
		if(RELOAD_OLD_PREFS){
			urlField.setText(prefs.get(BASE_URL,""));
			txtUsername.setText(prefs.get(USERNAME,""));
			txtPassword.setText(prefs.get(PASSWORD,""));
			usernameParam = prefs.get(USER_PARAM,"");
			passwordParam = prefs.get(PASS_PARAM,"");
			waitTime.setText(prefs.get(WAIT_TIME, "100"));
			numTries.setText(prefs.get(NUM_TRIES, "2"));
			numThreads.setText(prefs.get(NUM_THREADS, "2"));
			
			
			}
		
		
			Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
            	storeTableModel((DefaultTableModel) pageTable.getModel());
            	prefs.putBoolean(REMEMBER, chckbxRememberSettings.isSelected());
            }
        });
	}
	
	public JTable getJTable(){
		return pageTable;
	}
	
	
	
	
	
	private TableModel getModel() {
        DefaultTableModel model = new DefaultTableModel(3,3);
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream("pagenameTable"));
            Vector data = (Vector) ois.readObject();
            Vector<Object> cols = new Vector<>();
            for(int i=1;i<=data.size();i++){
                cols.add(i);
            }
            model.setDataVector(data, cols);
        } catch (Exception e) {
            e.printStackTrace();
        }  finally{
            if(ois != null)
                try {
                    ois.close();
                } catch (IOException e) {
                }
        }
        return model;
    }
	public void storeTableModel(DefaultTableModel model) {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream("pagenameTable"));
            oos.writeObject(model.getDataVector());
        } catch (Exception e) {
            e.printStackTrace();
        }  finally{
            if(oos != null)
                try {
                    oos.close();
                } catch (IOException e) {
                }
        }
    }
}

