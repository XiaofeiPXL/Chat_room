import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;

import javax.swing.*;

// server window class
public class ServerFrame extends JFrame implements ActionListener 
{
	public  JList list;

	private static final long serialVersionUID = -8936397327038098620L;

    // server information panel
	JPanel pnlServer, pnlServerInfo;

	JLabel lblStatus, lblNumber, lblMax, lblServerName, lblProtocol, lblIP,
			lblPort, lblLog;

	public JTextField txtStatus, txtNumber, txtMax, txtServerName, txtProtocol, txtIP,
			txtPort;

	JButton btnStop, btnSaveLog;

	public TextArea taLog;

	JTabbedPane tpServer;

	public TextArea taMessage;

    // user information panel
	JPanel pnlUser;

	public JLabel lblMessage, lblUser, lblNotice, lblUserCount;

	JList lstUser;

	JScrollPane spUser;

	JTextField txtNotice;

	JButton btnSend, btnKick;
	
	public String kick = "";
	
	public String serverMessage ="";

	public ServerFrame() 
	{
		// server window
		super("[Happy--Chat]");
		setSize(550, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		Dimension scr = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension fra = this.getSize();
		if (fra.width > scr.width) {
			fra.width = scr.width;
		}
		if (fra.height > scr.height) {
			fra.height = scr.height;
		}
		this.setLocation((scr.width - fra.width) / 2,
				(scr.height - fra.height) / 2);

		// server inforamation panel
		pnlServer = new JPanel();
		pnlServer.setLayout(null);
		pnlServer.setBackground(new Color(52, 130, 203));

		pnlServerInfo = new JPanel(new GridLayout(14, 1));
		pnlServerInfo.setBackground(new Color(52, 130, 203));
		pnlServerInfo.setFont(new Font("Arial", 0, 12));
		pnlServerInfo.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(""), BorderFactory
						.createEmptyBorder(1, 1, 1, 1)));

		lblStatus = new JLabel("Current Status:");
		lblStatus.setForeground(Color.YELLOW);
		lblStatus.setFont(new Font("Arial", 0, 12));
		txtStatus = new JTextField(10);
		txtStatus.setBackground(Color.decode("#d6f4f2"));
		txtStatus.setFont(new Font("Arial", 0, 12));
		txtStatus.setEditable(false);

		lblNumber = new JLabel("Current Online:");
		lblNumber.setForeground(Color.YELLOW);
		lblNumber.setFont(new Font("Arial", 0, 12));
		txtNumber = new JTextField("0", 10);
		txtNumber.setBackground(Color.decode("#d6f4f2"));
		txtNumber.setFont(new Font("Arial", 0, 12));
		txtNumber.setEditable(false);

		lblMax = new JLabel("Max:");
		lblMax.setForeground(Color.YELLOW);
		lblMax.setFont(new Font("Arial", 0, 12));
		txtMax = new JTextField("50", 10);
		txtMax.setBackground(Color.decode("#d6f4f2"));
		txtMax.setFont(new Font("Arial", 0, 12));
		txtMax.setEditable(false);

		lblServerName = new JLabel("Server Name:");
		lblServerName.setForeground(Color.YELLOW);
		lblServerName.setFont(new Font("Arial", 0, 12));
		txtServerName = new JTextField(10);
		txtServerName.setBackground(Color.decode("#d6f4f2"));
		txtServerName.setFont(new Font("Arial", 0, 12));
		txtServerName.setEditable(false);

		lblProtocol = new JLabel("Protocol:");
		lblProtocol.setForeground(Color.YELLOW);
		lblProtocol.setFont(new Font("Arial", 0, 12));
		txtProtocol = new JTextField("HTTP", 10);
		txtProtocol.setBackground(Color.decode("#d6f4f2"));
		txtProtocol.setFont(new Font("Arial", 0, 12));
		txtProtocol.setEditable(false);

		lblIP = new JLabel("Server IP:");
		lblIP.setForeground(Color.YELLOW);
		lblIP.setFont(new Font("Arial", 0, 12));
		txtIP = new JTextField(10);
		txtIP.setBackground(Color.decode("#d6f4f2"));
		txtIP.setFont(new Font("Arial", 0, 12));
		txtIP.setEditable(false);

		lblPort = new JLabel("Server Port:");
		lblPort.setForeground(Color.YELLOW);
		lblPort.setFont(new Font("Arial", 0, 12));
		txtPort = new JTextField("8000", 10);
		txtPort.setBackground(Color.decode("#d6f4f2"));
		txtPort.setFont(new Font("Arial", 0, 12));
		txtPort.setEditable(false);

		btnStop = new JButton("Close(C)");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				closeServer();
			}
		});
		btnStop.setBackground(Color.ORANGE);
		btnStop.setFont(new Font("Arial", 0, 12));

		lblLog = new JLabel("[Server Log]");
		lblLog.setForeground(Color.YELLOW);
		lblLog.setFont(new Font("Arial", 0, 12));

		taLog = new TextArea(20, 50);
		taLog.setFont(new Font("Arial", 0, 12));
		btnSaveLog = new JButton("Save(S)");
		btnSaveLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				saveLog();
			}
		});
		btnSaveLog.setBackground(Color.ORANGE);
		btnSaveLog.setFont(new Font("Arial", 0, 12));

		pnlServerInfo.add(lblStatus);
		pnlServerInfo.add(txtStatus);
		pnlServerInfo.add(lblNumber);
		pnlServerInfo.add(txtNumber);
		pnlServerInfo.add(lblMax);
		pnlServerInfo.add(txtMax);
		pnlServerInfo.add(lblServerName);
		pnlServerInfo.add(txtServerName);
		pnlServerInfo.add(lblProtocol);
		pnlServerInfo.add(txtProtocol);
		pnlServerInfo.add(lblIP);
		pnlServerInfo.add(txtIP);
		pnlServerInfo.add(lblPort);
		pnlServerInfo.add(txtPort);

		pnlServerInfo.setBounds(5, 5, 100, 400);
		lblLog.setBounds(110, 5, 100, 30);
		taLog.setBounds(110, 35, 400, 370);
		btnStop.setBounds(200, 410, 120, 30);
		btnSaveLog.setBounds(320, 410, 120, 30);
		pnlServer.add(pnlServerInfo);
		pnlServer.add(lblLog);
		pnlServer.add(taLog);
		pnlServer.add(btnStop);
		pnlServer.add(btnSaveLog);
		// online user panel
		pnlUser = new JPanel();
		pnlUser.setLayout(null);
		pnlUser.setBackground(new Color(52, 130, 203));
		pnlUser.setFont(new Font("Arial", 0, 12));
		lblMessage = new JLabel("[User Message]");
		lblMessage.setFont(new Font("Arial", 0, 12));
		lblMessage.setForeground(Color.YELLOW);
		taMessage = new TextArea(20, 20);
		taMessage.setFont(new Font("Arial", 0, 12));
		lblNotice = new JLabel("Note:");
		lblNotice.setFont(new Font("Arial", 0, 12));
		txtNotice = new JTextField(20);
		txtNotice.setFont(new Font("Arial", 0, 12));
		btnSend = new JButton("Send(S)");
		btnSend.setBackground(Color.ORANGE);
		btnSend.setFont(new Font("Arial", 0, 12));
		btnSend.setEnabled(true);
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				serverMessage();
			}
		});

		lblUserCount = new JLabel("Total_online 0");
		lblUserCount.setFont(new Font("Arial", 0, 12));

		btnKick = new JButton("Kick(K)");
		btnKick.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tiRen();
			}
		});
		btnKick.setBackground(Color.ORANGE);
		btnKick.setFont(new Font("Arial", 0, 12));
		lblUser = new JLabel("[Online List]");
		lblUser.setFont(new Font("Arial", 0, 12));
		lblUser.setForeground(Color.YELLOW);

		lstUser = new JList();
		lstUser.setFont(new Font("Arial", 0, 12));
		lstUser.setVisibleRowCount(17);
		lstUser.setFixedCellWidth(180);
		lstUser.setFixedCellHeight(18);

		spUser = new JScrollPane();
		spUser.setBackground(Color.decode("#d6f4f2"));
		spUser.setFont(new Font("Arial", 0, 12));
		spUser
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		spUser.getViewport().setView(lstUser);

		lblMessage.setBounds(5, 5, 100, 25);
		taMessage.setBounds(5, 35, 300, 360);
		lblUser.setBounds(310, 5, 100, 25);
		spUser.setBounds(310, 35, 220, 360);
		lblNotice.setBounds(5, 410, 40, 25);
		txtNotice.setBounds(50, 410, 160, 25);
		btnSend.setBounds(210, 410, 80, 25);
		lblUserCount.setBounds(320, 410, 100, 25);
		btnKick.setBounds(440, 410, 80, 25);

		pnlUser.add(lblMessage);
		pnlUser.add(taMessage);
		pnlUser.add(lblUser);
		pnlUser.add(spUser);

		list = new JList();
		list.setListData(new String[] { "" });
		spUser.setViewportView(list);
		pnlUser.add(lblNotice);
		pnlUser.add(txtNotice);
		pnlUser.add(btnSend);
		pnlUser.add(lblUserCount);
		pnlUser.add(btnKick);

		// main tag panel
		tpServer = new JTabbedPane(JTabbedPane.TOP);
		tpServer.setBackground(Color.decode("#d6f4f2"));
		tpServer.setFont(new Font("Arial", 0, 12));
		tpServer.add("Server_M", pnlServer);
		tpServer.add("User_M", pnlUser);
		this.getContentPane().add(tpServer);
		setVisible(true);
	}

	protected void serverMessage() 
	{
		this.serverMessage = txtNotice.getText();
		txtNotice.setText("");
	}

	protected void closeServer() 
	{
		this.dispose();
	}

	protected void saveLog() 
	{
		try {
			FileOutputStream fileoutput = new FileOutputStream("log.txt",
					true);
			String temp = taMessage.getText();
			fileoutput.write(temp.getBytes());
			fileoutput.close();
			JOptionPane.showMessageDialog(null, "log save in log.txt");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	protected void tiRen() 
	{
		kick = list.getSelectedValue().toString();
		log("Kick:"+kick);
	}

	private void log(String string) 
	{
		String newta = taMessage.getText();
		newta += ("\n"+string);
		taMessage.setText(newta);
	}

	public void actionPerformed(ActionEvent evt) {

	}

	public static void main(String args[]) {
		new ServerFrame();
	}
}