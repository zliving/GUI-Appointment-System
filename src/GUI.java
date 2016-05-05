import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.BorderLayout;
import java.awt.Dimension;
//import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.List;

public class GUI implements ActionListener{

	//globals for the manipulation of GUI
	private JFrame mainFrame;
	private JMenuBar mainMenu;
	private JMenu optionsFile;
	private LoginSystem newLogin;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JPanel textPane;
	private JPanel buttonPane;
	private boolean LoginStatus = false;
	private ManageAppointments newApt;
	private List<String> aptInfo;
	private JList<String> aptList;
	private JScrollPane pane;
	private JPanel aptInfoPane;
	private JLabel aptInfoLabel;
	private JPanel modifyButtonPane;
	private String getChangeInfo;
	private JFrame editFrame;
	private JComboBox<String> doctorList;
	private JComboBox<String> timesList;
	private String[] doctors;
	private String[] parsedInfo;
	private String[] aptTimes;
	private JTextField patientName;
	private JPanel editMenuPanel;
	private JPanel editMenuButtonPanel;
	private JTextField dateField;
	
	public GUI()
	{
		//Create gui as it is created
		createGUI();
	}
	public void createGUI()
	{
		//builds the main frame in which the program will be printed to
		mainFrame = new JFrame();
		mainFrame.setLayout(new GridLayout(2,0));
        mainFrame.setTitle("HealthCare Reservation System");
        //mainFrame.setSize(500, 500);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
        createMenu();
        createLoginBox();
        mainFrame.pack();
        centerFrame();
	}
	public void centerFrame()
	{
		//move the frame to the center of the screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        mainFrame.setLocation(dim.width/2-mainFrame.getSize().width/2, dim.height/2-mainFrame.getSize().height/2);
	}
	public void resetGUI()
	{
		//after someone signs out remove all the gui components and rebuild the frame
		mainFrame.remove(aptInfoPane);
		mainFrame.remove(modifyButtonPane);
		mainFrame.remove(pane);
		mainFrame.remove(aptInfoLabel);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
        createMenu();
        createLoginBox();
        mainFrame.pack();
        createMenu();
		mainFrame.setLayout(new GridLayout(2,0));
        mainFrame.setTitle("HealthCare Reservation System");
        mainFrame.pack();
        centerFrame();
        mainFrame.repaint();
	}
	public void createMenu()
	{
		//creates the main login menu
		mainMenu = new JMenuBar();
		optionsFile = new JMenu("Options");
		
		JMenuItem signIn = new JMenuItem("Sign In");
		signIn.setActionCommand("SignIn");
		signIn.addActionListener(this);
		JMenuItem signOut = new JMenuItem("Sign Out");
		signOut.setActionCommand("SignOut");
		signOut.addActionListener(this);
		JMenuItem Exit = new JMenuItem("Exit");
		Exit.setActionCommand("Exit");
		Exit.addActionListener(this);
		mainFrame.add(mainMenu);
		if(!LoginStatus)
		{optionsFile.add(signIn);
		
		}
		else{
		optionsFile.add(signOut);
		}
		optionsFile.add(Exit);
		mainMenu.add(optionsFile);
		mainFrame.setJMenuBar(mainMenu);
	}
	
	public void createLoginBox()
	{
		//creates the buttons and text fields for logging into the program
		usernameField = new JTextField(10);
		passwordField = new JPasswordField(10);
		JLabel userLabel = new JLabel("Username: ");
        userLabel.setLabelFor(usernameField);
		JLabel passLabel = new JLabel("Password: ");
        passLabel.setLabelFor(passwordField);
        
        textPane = new JPanel((LayoutManager) new FlowLayout(FlowLayout.CENTER));
        textPane.add(userLabel);
        textPane.add(usernameField);
        textPane.add(passLabel);
        textPane.add(passwordField);
        
        JButton signInButton = new JButton("Sign In");
        JButton signOutButton = new JButton("Exit");
        signInButton.setActionCommand("SignInButton");
        signInButton.addActionListener(this);
        signOutButton.setActionCommand("Exit");
        signOutButton.addActionListener(this);
        
        buttonPane = new JPanel(new GridLayout(1,0));
        buttonPane.add(signInButton);
        buttonPane.add(signOutButton);
        
        mainFrame.add(textPane);
        mainFrame.add(buttonPane);

	}
	public void createAptList(String[] arrayInfo)
	{
		//gets a lits of all of the appointments from the database
		mainFrame.setLayout(new BorderLayout());
		aptInfoLabel = new JLabel("Doctor - Customer Name - Appointment Time - Appointment Date");
		aptInfoPane = new JPanel();
		aptInfoPane.setLayout((LayoutManager) new BorderLayout());
        aptInfoPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        aptInfoLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
		aptList = new JList<String>(arrayInfo);
		aptList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    getChangeInfo = (String) aptList.getSelectedValue();
                    System.out.println("The info from the selected value: " +getChangeInfo);
                }
            }
        });
		
		//Displays the appointments
		pane = new JScrollPane();
        pane.getViewport().add(aptList,BorderLayout.CENTER);
        pane.setPreferredSize(new Dimension(400, 400));
        pane.add(aptInfoLabel);
        aptInfoPane.add(pane);
        aptInfoLabel.setLabelFor(pane);
        aptInfoLabel.setLabelFor(aptInfoPane);
        mainFrame.add(aptInfoLabel, BorderLayout.NORTH);
        mainFrame.add(aptInfoPane);
        
        //builds the buttons for editing, creating, and deleting appointments
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");
        JButton newButton = new JButton("New");
        editButton.setActionCommand("EditButton");
        editButton.addActionListener(this);
        deleteButton.setActionCommand("DeleteButton");
        deleteButton.addActionListener(this);
        newButton.setActionCommand("NewButton");
        newButton.addActionListener(this);
        
        //adds buttons to the frame and revalidates the frame
        modifyButtonPane = new JPanel((LayoutManager) new FlowLayout(FlowLayout.CENTER));
        modifyButtonPane.setMaximumSize(new Dimension(100, 100));
        modifyButtonPane.add(editButton);
        modifyButtonPane.add(deleteButton);
        modifyButtonPane.add(newButton);
        aptInfoPane.repaint();
        pane.repaint();
        mainFrame.repaint();
        aptInfoPane.revalidate();
        pane.revalidate();
        mainFrame.revalidate();
        mainFrame.add(modifyButtonPane, BorderLayout.SOUTH);
		
	}
	public void AfterLoginMenu()
	{
		//shows all of the appointments after you have logged in 
		newApt = new ManageAppointments();
		newApt.viewAppointments();
		aptInfo = newApt.returnList();
		String[] arrayInfo = new String[aptInfo.size()];
		arrayInfo = aptInfo.toArray(arrayInfo);
		createMenu();
		createAptList(arrayInfo);
		mainFrame.remove(textPane);
		mainFrame.remove(buttonPane);
		//mainFrame.setSize(500, 500);
		mainFrame.pack();
		mainFrame.validate();
		mainFrame.repaint();
		centerFrame();
	}
	public void createEditMenu()
	{
		//creates the menu for editing appointments
		editFrame = new JFrame();
		editFrame.setLayout(new GridLayout(2,0));
        editFrame.setTitle("Edit Appointment");
        editFrame.setSize(500, 500);
        editFrame.setLocationRelativeTo(null);
        editFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        editFrame.setVisible(true);
        
        doctorList= new JComboBox<>(doctors);
        doctorList.setSelectedItem(parsedInfo[0]);
        doctorList.addActionListener(this);
        
        timesList = new JComboBox<>(aptTimes);
        timesList.setSelectedItem(parsedInfo[2]);
        
        JPanel doctorBox = new JPanel();
        doctorBox.add(doctorList);
        
        patientName = new JTextField(20);
        patientName.setText(parsedInfo[1]);
        JLabel doctorLabel = new JLabel("Doctor: ");
        doctorLabel.setLabelFor(doctorList);
        JLabel patientLabel = new JLabel("Patient Name: ");
        patientLabel.setLabelFor(patientName);
        JLabel dateLabel = new JLabel("Date: ");
        JLabel timeLabel = new JLabel("Time: ");
        timeLabel.setLabelFor(timesList);
        dateField = new JTextField(10);
        dateField.setText(parsedInfo[3]);
        dateLabel.setLabelFor(dateField);
        
        editMenuPanel = new JPanel((LayoutManager) new FlowLayout(FlowLayout.LEADING));
        
        editMenuPanel.add(doctorLabel);
        editMenuPanel.add(doctorList);
        editMenuPanel.add(patientLabel);
        editMenuPanel.add(patientName);
        editMenuPanel.add(timeLabel);
        editMenuPanel.add(timesList);
        editMenuPanel.add(dateLabel);
        editMenuPanel.add(dateField);
        
        editMenuButtonPanel = new JPanel((LayoutManager) new FlowLayout(FlowLayout.CENTER));
        JButton submitButton = new JButton("Submit");
        JButton cancelButton = new JButton("Cancel");
        
        submitButton.setActionCommand("Submit");
        submitButton.addActionListener(this);
        cancelButton.setActionCommand("Cancel");
        cancelButton.addActionListener(this);
        
        editMenuButtonPanel.add(submitButton);
        editMenuButtonPanel.add(cancelButton);
        
        editFrame.add(editMenuPanel);
        editFrame.add(editMenuButtonPanel);
	}
	public void createNewMenu()
	{
		//creates a menu for making new appointments
		editFrame = new JFrame();
		editFrame.setLayout(new GridLayout(2,0));
        editFrame.setTitle("Edit Appointment");
        editFrame.setSize(500, 500);
        editFrame.setLocationRelativeTo(null);
        editFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        editFrame.setVisible(true);
        
        doctorList= new JComboBox<>(doctors);
        doctorList.addActionListener(this);
        
        timesList = new JComboBox<>(aptTimes);
        
        JPanel doctorBox = new JPanel();
        doctorBox.add(doctorList);
        
        patientName = new JTextField(20);
        JLabel doctorLabel = new JLabel("Doctor: ");
        doctorLabel.setLabelFor(doctorList);
        JLabel patientLabel = new JLabel("Patient Name: ");
        patientLabel.setLabelFor(patientName);
        JLabel dateLabel = new JLabel("Date: ");
        JLabel timeLabel = new JLabel("Time: ");
        timeLabel.setLabelFor(timesList);
        dateField = new JTextField(10);
        dateLabel.setLabelFor(dateField);
        
        editMenuPanel = new JPanel((LayoutManager) new FlowLayout(FlowLayout.LEADING));
        
        editMenuPanel.add(doctorLabel);
        editMenuPanel.add(doctorList);
        editMenuPanel.add(patientLabel);
        editMenuPanel.add(patientName);
        editMenuPanel.add(timeLabel);
        editMenuPanel.add(timesList);
        editMenuPanel.add(dateLabel);
        editMenuPanel.add(dateField);
        
        editMenuButtonPanel = new JPanel((LayoutManager) new FlowLayout(FlowLayout.CENTER));
        JButton submitButton = new JButton("New");
        JButton cancelButton = new JButton("Cancel");
        
        submitButton.setActionCommand("New");
        submitButton.addActionListener(this);
        cancelButton.setActionCommand("Cancel");
        cancelButton.addActionListener(this);
        
        editMenuButtonPanel.add(submitButton);
        editMenuButtonPanel.add(cancelButton);
        
        editFrame.add(editMenuPanel);
        editFrame.add(editMenuButtonPanel);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		//checks if a button or item has been selected
		String action = e.getActionCommand();
		
		if(action.equals("SignIn"))
		{
			
		}
		else if(action.equals("SignInButton"))
		{
			String user = usernameField.getText();
			char[] pass = passwordField.getPassword();
			String passString = new String(pass);
			newLogin = new LoginSystem(user,passString);
			if(newLogin.CheckLogin())
			{
				JOptionPane.showMessageDialog(mainFrame,"Welcome "+user,"Login Was Correct", JOptionPane.PLAIN_MESSAGE);
				LoginStatus = true;
				AfterLoginMenu();
			}
			else
			{
				JOptionPane.showMessageDialog(mainFrame,"Invalid username and password Combination","Invalid Login", 0);
			}
			
		}
		else if(action.equals("SignOut"))
		{
			LoginStatus = false;
			JOptionPane.showMessageDialog(mainFrame,"You have been signed out.","Signed Out", JOptionPane.INFORMATION_MESSAGE );
			resetGUI();
		}
		else if(action.equals("Exit"))
		{
			System.exit(0);
		}
		else if(action.equals("EditButton"))
		{
			if(getChangeInfo != null)
			{
			newApt = new ManageAppointments();
			//newApt.changeAppointment(getChangeInfo);
			doctors = newApt.returnDoctors();
			aptTimes = newApt.returnTimes();
			System.out.println(getChangeInfo);
			parsedInfo = newApt.parseString(getChangeInfo);
			for(String s:parsedInfo)
			{
				System.out.println("Parsed Info: "+s);
			}
			createEditMenu();
			}
			else{
				JOptionPane.showMessageDialog(mainFrame,"You must select an appointment to edit","Editor Error", 0);
			}
		}
		else if(action.equals("Submit"))
		{
			newApt = new ManageAppointments();
			newApt.changeAppointment(doctorList.getSelectedItem().toString(), patientName.getText(), timesList.getSelectedItem().toString(), dateField.getText(), parsedInfo);
			editFrame.dispatchEvent(new WindowEvent(editFrame, WindowEvent.WINDOW_CLOSING));
			JOptionPane.showMessageDialog(mainFrame,"The Appointment has been changed.","Confirmation", 1);
			AfterLoginMenu();
	        aptInfoPane.repaint();
	        pane.repaint();
	        mainFrame.repaint();
	        aptInfoPane.revalidate();
	        pane.revalidate();
	        mainFrame.revalidate();
		}
		else if(action.equals("Cancel"))
		{
			editFrame.dispatchEvent(new WindowEvent(editFrame, WindowEvent.WINDOW_CLOSING));
		}
		else if(action.equals("NewButton"))
		{

			newApt = new ManageAppointments();
			doctors = newApt.returnDoctors();
			aptTimes = newApt.returnTimes();

			createNewMenu();
			
		}
		else if(action.equals("New"))
		{
			newApt = new ManageAppointments();
			newApt.newApt(doctorList.getSelectedItem().toString(), patientName.getText(), timesList.getSelectedItem().toString(), dateField.getText());
			editFrame.dispatchEvent(new WindowEvent(editFrame, WindowEvent.WINDOW_CLOSING));
			JOptionPane.showMessageDialog(mainFrame,"The Appointment has been created.","New Appointment", 1);
		}
		else if (action.equals("DeleteButton"))
		{
			if(getChangeInfo != null)
			{
			newApt = new ManageAppointments();
			parsedInfo = newApt.parseString(getChangeInfo);
			if (JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this appointment?", "WARNING",
			        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				newApt.deleteAppointment(parsedInfo);
			} else {
			}
			
			}
			else{
				JOptionPane.showMessageDialog(mainFrame,"You must select an appointment to delete","Editor Error", 0);
			}
		}
	}
	
}
