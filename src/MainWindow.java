import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.swing.JList;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JCheckBox;


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
	private String loginParam;
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
		frmWebAppPerformance.setBounds(100, 100, 607, 441);
		frmWebAppPerformance.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JCheckBox chckbxLoginPage = new JCheckBox("Login Form?");
		JCheckBox chckbxPostLoginUri = new JCheckBox("POST Login URI?");
		DefaultTableModel model = new DefaultTableModel();
		JPanel panel = new JPanel();
		frmWebAppPerformance.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JTextField urlField = new JTextField();
		urlField.setText("http://maxrozen.com/wapt/wp-login.php");
		urlField.setBounds(119, 6, 465, 28);
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
				try {
					ThreadService.main(null,url, tries, wait, threads, username, password, usernameParam, passwordParam, loginParam);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnRun.setBounds(484, 384, 117, 29);
		panel.add(btnRun);
		
		
		
		JLabel lblUrl = new JLabel("Base URI");
		lblUrl.setBounds(16, 12, 71, 16);
		panel.add(lblUrl);
		
		JLabel lblWaittime = new JLabel("Wait Time (ms)");
		lblWaittime.setBounds(16, 55, 97, 16);
		panel.add(lblWaittime);
		
		waitTime = new JTextField();
		waitTime.setText("100");
		waitTime.setColumns(10);
		waitTime.setBounds(119, 49, 86, 28);
		panel.add(waitTime);
		
		numTries = new JTextField();
		numTries.setText("1");
		numTries.setColumns(10);
		numTries.setBounds(300, 49, 86, 28);
		panel.add(numTries);
		
		JLabel lblNumtries = new JLabel("numTries");
		lblNumtries.setBounds(217, 55, 71, 16);
		panel.add(lblNumtries);
		
		numThreads = new JTextField();
		numThreads.setText("1");
		numThreads.setColumns(10);
		numThreads.setBounds(513, 49, 71, 28);
		panel.add(numThreads);
		
		JLabel lblNumthreads = new JLabel("numThreads");
		lblNumthreads.setBounds(415, 55, 86, 16);
		panel.add(lblNumthreads);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(30, 100, 400, 80);
		panel.add(scrollPane);
		
		
		JList<String> list = new JList<String>();
		scrollPane.setViewportView(list);

		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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
		btnListParameters.setBounds(442, 97, 140, 29);
		panel.add(btnListParameters);
		
		txtUsername = new JTextField();
		txtUsername.setText("admin");
		txtUsername.setBounds(90, 186, 134, 28);
		panel.add(txtUsername);
		txtUsername.setColumns(10);
		
		txtPassword = new JTextField();
		txtPassword.setText("webapplicationperformancetesting");
		txtPassword.setBounds(300, 186, 134, 28);
		panel.add(txtPassword);
		txtPassword.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(16, 192, 81, 16);
		panel.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(236, 192, 71, 16);
		panel.add(lblPassword);
		//DefaultListModel<String> pageModel = new DefaultListModel<String>();
		
		model.addColumn("Page URI"); 
		model.addColumn("Is Login Form?");
		model.addColumn("Is Login POST URI?");
		
		
		
		pageName = new JTextField();
		pageName.setBounds(100, 230, 202, 28);
		panel.add(pageName);
		pageName.setColumns(10);
		
		JButton btnAddPage = new JButton("Add");
		
		btnAddPage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (pageName.getText() != null){
					model.addRow(new Object[]{pageName.getText(), chckbxLoginPage.isSelected(), chckbxPostLoginUri.isSelected()});
				}
			}
		});
		btnAddPage.setBounds(444, 231, 71, 29);
		panel.add(btnAddPage);
		
		JLabel lblPageToTest = new JLabel("Page to test");
		lblPageToTest.setBounds(16, 234, 86, 24);
		panel.add(lblPageToTest);
		
		JButton btnRemove = new JButton("Remove");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.removeRow(pageTable.getSelectedRow());
				
			}
		});
		btnRemove.setBounds(442, 276, 117, 29);
		panel.add(btnRemove);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_1.setBounds(30, 280, 400, 80);
		panel.add(scrollPane_1);
		
		// Append a row 
		model.addRow(new Object[]{"wp-login.php", true, false});
		model.addRow(new Object[]{"wp-login.php", false, true});
		pageTable = new JTable();
		scrollPane_1.setViewportView(pageTable);
		pageTable.setModel(model);
		
		
		chckbxLoginPage.setBounds(300, 226, 128, 23);
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
		btnSetUserparameter.setBounds(442, 124, 140, 29);
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
		btnSetPassparameter.setBounds(442, 151, 142, 29);
		panel.add(btnSetPassparameter);
		
		
		chckbxPostLoginUri.setBounds(300, 245, 149, 23);
		panel.add(chckbxPostLoginUri);
		
		JButton btnSetLoginParameter = new JButton("Set Login Parameter");
		btnSetLoginParameter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//loginParam
				if (!list.isSelectionEmpty()){
					if(list.getSelectedIndices().length == 1){
						//set userparam
						loginParam = list.getSelectedValue();
					}else{
				        JOptionPane.showMessageDialog(null, "Please only select one value", "Error!", JOptionPane.ERROR_MESSAGE);

					}
				}
			}
		});
		btnSetLoginParameter.setBounds(442, 179, 142, 29);
		panel.add(btnSetLoginParameter);
		
	
	}
	
	public JTable getJTable(){
		return pageTable;
	}
}

