import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;

public class Register extends JFrame  implements ActionListener
{
	private JComboBox comboBox;

	private static final long serialVersionUID = 9019746127517522180L;
	JPanel  pnlRegister;
	JLabel  lblUserName,lblGender,lblAge;
	JLabel  lblPassword,lblConfirmPass,lblEmail,logoPosition;
	JTextField  txtUserName,txtAge,txtEmail;
	JPasswordField  pwdUserPassword,pwdConfirmPass;
	JRadioButton  rbtnMale,rbtnFemale;
	ButtonGroup  btngGender;
    JButton  btnOk,btnCancel,btnClear;
	String  strServerIp;
	final JLabel headLabel = new JLabel();
	// use to locate window
	Dimension scrnsize;
    Toolkit toolkit=Toolkit.getDefaultToolkit();
    // contructor
	public Register(String ip)
	{
		super("Happy--Chat");
		strServerIp=ip;
		pnlRegister=new JPanel();
		this.getContentPane().add(pnlRegister);
	
		lblUserName=new JLabel("Username:");
		lblGender=new JLabel("Sex:");
		lblAge=new JLabel("Age:");
		lblPassword=new JLabel("Password:");
		lblConfirmPass=new JLabel("Confirm:");
		lblEmail=new JLabel("Email:");
		txtUserName=new JTextField(30);
		txtEmail=new JTextField(30);
		txtAge=new JTextField(10);
		pwdUserPassword=new JPasswordField(30);
		pwdConfirmPass=new JPasswordField(30);
		rbtnMale=new JRadioButton("Male",true);
		rbtnFemale=new JRadioButton("Female");
	    btngGender=new ButtonGroup();
	    btnOk=new JButton("OK(O)");
	    btnOk.setMnemonic('O');
	    btnOk.setToolTipText("Save register information");
		btnCancel=new JButton("Back(B)");
		btnCancel.setMnemonic('B');
		btnCancel.setToolTipText("Back to login window");
		btnClear=new JButton("Clear(L)");
		btnClear.setMnemonic('L');
		btnClear.setToolTipText("Clear register information");
		

		pnlRegister.setLayout(null);    
		pnlRegister.setBackground(new Color(52,130,203));

		lblUserName.setBounds(30,80,100,30);
		txtUserName.setBounds(110,85,120,20);
		lblPassword.setBounds(30,141,100,30);
		pwdUserPassword.setBounds(110,146,120,20);
		lblConfirmPass.setBounds(30,166,100,30);
		pwdConfirmPass.setBounds(110,171,120,20);
		lblGender.setBounds(30,191,100,30);
		rbtnMale.setBounds(110,196,60,20);
		rbtnFemale.setBounds(160,196,70,20);
		lblAge.setBounds(30,216,100,30);
		txtAge.setBounds(110,221,120,20);
		lblEmail.setBounds(30,241,100,30);
		txtEmail.setBounds(110,246,120,20);

	    btnOk.setBounds(246,166,80,25);	
	    btnCancel.setBounds(246,201,80,25);
	    btnClear.setBounds(246,241,80,25);
	
		Font fontstr=new Font("Arial",Font.PLAIN,12);	
		lblUserName.setFont(fontstr);
	    lblGender.setFont(fontstr);
		lblPassword.setFont(fontstr);
		lblConfirmPass.setFont(fontstr);
		lblAge.setFont(fontstr);
		lblEmail.setFont(fontstr);
        rbtnMale.setFont(fontstr);
		rbtnFemale.setFont(fontstr);
		txtUserName.setFont(fontstr);
		txtEmail.setFont(fontstr);	
		btnOk.setFont(fontstr);
		btnCancel.setFont(fontstr);
		btnClear.setFont(fontstr);
						
		lblUserName.setForeground(Color.BLACK);
		lblGender.setForeground(Color.BLACK);
		lblPassword.setForeground(Color.BLACK);
		lblAge.setForeground(Color.BLACK);
		lblConfirmPass .setForeground(Color.BLACK);
		lblEmail.setForeground(Color.BLACK);
		rbtnMale.setForeground(Color.BLACK);
		rbtnFemale.setForeground(Color.BLACK);
		rbtnMale.setBackground(Color.white);
		rbtnFemale.setBackground(Color.white);
		btnOk.setBackground(Color.ORANGE);	
	    btnCancel.setBackground(Color.ORANGE);
	    btnClear.setBackground(Color.ORANGE);
		rbtnMale.setOpaque(false);   
		rbtnFemale.setOpaque(false);
		
		pnlRegister.add(lblUserName);
		pnlRegister.add(lblGender);
		pnlRegister.add(lblPassword);
		pnlRegister.add(lblConfirmPass);
		pnlRegister.add(lblEmail);
		pnlRegister.add(lblAge);
		pnlRegister.add(txtAge);
		pnlRegister.add(txtUserName);
		pnlRegister.add(txtEmail);
		pnlRegister.add(pwdUserPassword);
		pnlRegister.add(pwdConfirmPass);
		pnlRegister.add(btnOk);
		pnlRegister.add(btnCancel);
		pnlRegister.add(btnClear);
		pnlRegister.add(rbtnMale);
		pnlRegister.add(rbtnFemale);
		btngGender.add(rbtnMale);
	    btngGender.add(rbtnFemale);
	    
	    // set background image
	    Icon logo = new ImageIcon("images\\registerlogo.jpg");
	 	logoPosition = new JLabel(logo);
		logoPosition.setBounds(0, 0, 360,78);
		pnlRegister.add(logoPosition);
	    
	    this.setSize(360,313);
		this.setVisible(true);
		this.setResizable(false);
		// set the window in the middle of screen
    	scrnsize=toolkit.getScreenSize();
    	this.setLocation(scrnsize.width/2-this.getWidth()/2,
    	                 scrnsize.height/2-this.getHeight()/2);
		Image img=toolkit.getImage("images\\appico.jpg");
        this.setIconImage(img);
        // three button monitor
		btnOk.addActionListener(this);
		btnCancel.addActionListener(this);
		btnClear.addActionListener(this);

		final JLabel label = new JLabel();
		label.setText("Image:");
		label.setBounds(30, 120, 60, 15);
		pnlRegister.add(label);

		comboBox = new JComboBox();
		comboBox.setAutoscrolls(true);
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3"}));
		comboBox.setBounds(110, 116, 47, 23);
		comboBox.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent arg0) {
				Icon logo = new ImageIcon("face\\"+comboBox.getSelectedItem().toString()+".PNG");
				headLabel.setIcon(logo);
			}
		});
		pnlRegister.add(comboBox);
		headLabel.setHorizontalAlignment(SwingConstants.CENTER);
		headLabel.setIcon(new ImageIcon("face//1.PNG"));
		headLabel.setBounds(247, 88, 74, 72);
		pnlRegister.add(headLabel);
	}  // constructor end
	
	public void actionPerformed(ActionEvent ae)
	{
		Object source=new Object();
	    source=ae.getSource();
	    if (source.equals(btnOk))   // ok button
	    {
	        register();
	    }
	    if (source.equals(btnCancel))  // return button
	    {
	    	new Login();
	    	this.dispose();
	    }
	    if (source.equals(btnClear))   // clear button
	    {
	        txtUserName.setText("");
	        pwdUserPassword.setText("");
	        pwdConfirmPass.setText("");
	        txtAge.setText("");
	        txtEmail.setText("");	
	    }
	}  // actionPerformed() end
	
	@SuppressWarnings({ "deprecation", "static-access" })
	public void register()
	{
		// receive client details
        Register_Customer data=new Register_Customer();
	    data.custName     = txtUserName.getText();
		data.custPassword = pwdUserPassword.getText();
		data.age          = txtAge.getText();
		data.sex          = rbtnMale.isSelected()?"Male":"Female";
		data.email        = txtEmail.getText();
		data.head		  = comboBox.getSelectedItem().toString();
		// detect if the username is null
		if(data.custName.length()==0)
		{
		    JOptionPane.showMessageDialog(null,"Username not null");	
            return;	
		}
		// detect if the password is null
		if(data.custPassword.length()==0)
		{
		    JOptionPane.showMessageDialog(null,"Password not null");	
            return;	
		}
		
		// confirm password is match for two inputs
		if(!data.custPassword.equals(pwdConfirmPass.getText()))
		{
		    JOptionPane.showMessageDialog(null,"please input password again");	
            return;
		}
		
		// detect if the age is null
		if(data.age.length()==0)
		{
		    JOptionPane.showMessageDialog(null,"Age is not null");	
            return;	
		}
		
		// detect if the age is not legal
		int age=Integer.parseInt(txtAge.getText());
		if (age<=0||age>100){
			JOptionPane.showMessageDialog(null,"Age not legal");
			return;
		}
		
		// detect if the email format is wrong
		int Found_flag=0;    
		for (int i=0;i<data.email.length();i++)
		{
		    if(data.email.charAt(i)=='@')
		    {
		        Found_flag++;	
		    }	
		}
		if(Found_flag!=1)
		{
		    JOptionPane.showMessageDialog(null,"Email format is not correct,input again");	
            return;	
		}
		
		try
		{
			// connect to server
		    Socket toServer;
  		    toServer = new Socket(strServerIp,1001);
		    ObjectOutputStream streamToServer=new ObjectOutputStream (toServer.getOutputStream());					
		    // write client details to server socket
		    streamToServer.writeObject((Register_Customer)data);
            // read login status from server socket
		    BufferedReader fromServer=new BufferedReader(new InputStreamReader(toServer.getInputStream()));
            String status=fromServer.readLine();
            // show successful message
            JOptionPane op=new JOptionPane();
            op.showMessageDialog(null,status);
            if(status.equals(data.custName+"register_successful"))
            {
                txtUserName.setText("");
                pwdUserPassword.setText("");
                pwdConfirmPass.setText("");
                txtAge.setText("");
                txtEmail.setText("");
            }
            // close flow object
		    streamToServer.close();
            fromServer.close();
         }
		 catch(InvalidClassException e1)
		 {
		    JOptionPane.showMessageDialog(null,"class failed!");
		 }
		 catch(NotSerializableException e2)
		 {
			JOptionPane.showMessageDialog(null,"object failed to order!");
		 }
		 catch(IOException e3)
		 {
		 	JOptionPane.showMessageDialog(null,"failed to write to server!");
		 }
		
	}// register() end  
	
	public static void main(String args[])
	{
		new Register("127.0.0.1");
	}

}  
