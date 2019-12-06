import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;

public class Login extends JFrame implements ActionListener {

	private static final long serialVersionUID = -8965773902056088264L;

	private JPanel pnlLogin;

	private JButton btnLogin, btnRegister, btnExit;

	private JLabel lblServer, lblUserName, lblPassword, lblLogo;

	private JTextField txtUserName, txtServer;

	private JPasswordField pwdPassword;

	private String strServerIp;

	private Dimension scrnsize;

	private Toolkit toolkit = Toolkit.getDefaultToolkit();

	// constructor of log-in window
	public Login() {
		super("Happy--Chat");
		pnlLogin = new JPanel();
		this.getContentPane().add(pnlLogin);

		lblServer = new JLabel("Server(S):");
		lblUserName = new JLabel("Username(U):");
		lblPassword = new JLabel("Password(P):");
		txtServer = new JTextField(20);
		txtServer.setText("127.0.0.1");
		txtUserName = new JTextField(20);
		pwdPassword = new JPasswordField(20);
		btnLogin = new JButton("Login(L)");
		btnLogin.setToolTipText("Log-on to server");
		btnLogin.setMnemonic('L');
		btnRegister = new JButton("Register(R)");
		btnRegister.setToolTipText("Register new user");
		btnRegister.setMnemonic('R');
		btnExit = new JButton("Exit(X)");
		btnExit.setToolTipText("Exit system");
		btnExit.setMnemonic('X');
		pnlLogin.setLayout(null);
		pnlLogin.setBackground(new Color(52, 130, 203));

		lblServer.setBounds(50, 100, 100, 30);
		txtServer.setBounds(150, 100, 120, 25);
		lblUserName.setBounds(50, 130, 100, 30);
		txtUserName.setBounds(150, 130, 120, 25);
		lblPassword.setBounds(50, 160, 100, 30);
		pwdPassword.setBounds(150, 160, 120, 25);
		btnLogin.setBounds(50, 200, 90, 25);
		btnRegister.setBounds(130, 200, 100, 25);
		btnExit.setBounds(220, 200, 90, 25);

		Font fontstr = new Font("Arial", Font.PLAIN, 12);
		lblServer.setFont(fontstr);
		txtServer.setFont(fontstr);
		lblUserName.setFont(fontstr);
		txtUserName.setFont(fontstr);
		lblPassword.setFont(fontstr);
		pwdPassword.setFont(fontstr);
		btnLogin.setFont(fontstr);
		btnRegister.setFont(fontstr);
		btnExit.setFont(fontstr);

		lblUserName.setForeground(Color.BLACK);
		lblPassword.setForeground(Color.BLACK);
		btnLogin.setBackground(Color.ORANGE);
		btnRegister.setBackground(Color.ORANGE);
		btnExit.setBackground(Color.ORANGE);

		pnlLogin.add(lblServer);
		pnlLogin.add(txtServer);
		pnlLogin.add(lblUserName);
		pnlLogin.add(txtUserName);
		pnlLogin.add(lblPassword);
		pnlLogin.add(pwdPassword);
		pnlLogin.add(btnLogin);
		pnlLogin.add(btnRegister);
		pnlLogin.add(btnExit);

		// setbackground image
		Icon logo1 = new ImageIcon("images\\loginlogo.jpg");
		lblLogo = new JLabel(logo1);
		lblLogo.setBounds(0, 0, 340, 66);
		pnlLogin.add(lblLogo);
		// set log-in window
		setResizable(false);
		setSize(340, 260);
		setVisible(true);
		scrnsize = toolkit.getScreenSize();
		setLocation(scrnsize.width / 2 - this.getWidth() / 2, scrnsize.height
				/ 2 - this.getHeight() / 2);
		Image img = toolkit.getImage("images\\appico.jpg");
		setIconImage(img);

		// monitor three buttons
		btnLogin.addActionListener(this);
		btnRegister.addActionListener(this);
		btnExit.addActionListener(this);

	} // constructor end

	@SuppressWarnings({ "deprecation", "static-access" })
	public void actionPerformed(ActionEvent ae) {
		Object source = ae.getSource();
		if (source.equals(btnLogin)) {
			// detect if the username or password is null
			if (txtUserName.getText().equals("")
					|| pwdPassword.getText().equals("")) {
				JOptionPane op1 = new JOptionPane();
				op1.showMessageDialog(null, "username or password is not null");
			} else {
				strServerIp = txtServer.getText();
				login();
			}
		}
		if (source.equals(btnRegister)) {
			strServerIp = txtServer.getText();
			this.dispose();
			new Register(strServerIp);
		}
		if (source == btnExit) {
			System.exit(0);
		}
	} // actionPerformed() end


	@SuppressWarnings("deprecation")
	public void login() {
		// receive details of client
		Customer data = new Customer();
		data.custName = txtUserName.getText();
		data.custPassword = pwdPassword.getText();
		try {
			// connect to server 
			Socket toServer;
			toServer = new Socket(strServerIp, 1001);
			ObjectOutputStream streamToServer = new ObjectOutputStream(toServer
					.getOutputStream());
			// write client details to server socket
			streamToServer.writeObject((Customer) data);
			// read client's login status from server socket
			BufferedReader fromServer = new BufferedReader(
					new InputStreamReader(toServer.getInputStream()));
			String status = fromServer.readLine();
			if (status.equals("log_successful")) {
				new ChatRoom((String) data.custName, strServerIp);
				this.dispose();
				// close data flow object
				streamToServer.close();
				fromServer.close();
				toServer.close();
			} else {
				JOptionPane.showMessageDialog(null, status);
				// close data flow object
				streamToServer.close();
				fromServer.close();
				toServer.close();
			}
		} catch (ConnectException e1) {
			JOptionPane.showMessageDialog(null, "failed to make connection");
		} catch (InvalidClassException e2) {
			JOptionPane.showMessageDialog(null, "class failed");
		} catch (NotSerializableException e3) {
			JOptionPane.showMessageDialog(null, "object failed to order");
		} catch (IOException e4) {
			JOptionPane.showMessageDialog(null, "failed to write to server");
		}
	} // login() end

	/*
	 * start login window
	 */
	public static void main(String args[]) 
	{
		new Login();
	}

} // Class Login end
