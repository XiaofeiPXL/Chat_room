import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import java.util.*;

public class ChatRoom extends Thread implements ActionListener {
	private JComboBox daXiaoComboBox;

	private JComboBox yangShiComboBox;

	private JComboBox zitiComboBox;

	private JComboBox emote;

	static JFrame frmChat;

	JPanel pnlChat;

	JButton btnCls, btnExit, btnSend, btnClear, btnSave, btnTimer;

	JLabel lblUserList, lblUserMessage, lblSendMessage, lblChatUser;

	JLabel lblUserTotal, lblCount, lblBack;

	JTextField txtMessage;

	java.awt.List lstUserList;

	TextArea taUserMessage;

	JComboBox cmbUser;

	JCheckBox chPrivateChat;

	String strServerIp, strLoginName;

	Thread thread;

	final JLabel headLabel = new JLabel();

	Dimension scrnsize;// used to locate the chat-window

	Toolkit toolkit = Toolkit.getDefaultToolkit();

	Message messobj = null;

	String serverMessage = "";

	final JSlider blueSlider = new JSlider();

	final JSlider redSlider = new JSlider();

	final JSlider greenSlider = new JSlider();

	final JLabel hanziLabel = new JLabel();

	// constructor
	public ChatRoom(String name, String ip) 
	{
		strServerIp = ip;
		strLoginName = name;
		frmChat = new JFrame("Happy-Chat" + "[User:" + name + "]");
		pnlChat = new JPanel();
		frmChat.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmChat.getContentPane().add(pnlChat);

		Font fntDisp1 = new Font("Arial", Font.PLAIN, 12);

		String list[] = { "All" };
		btnCls = new JButton("Clear(C)");
		btnExit = new JButton("Exit(X)");
		btnSend = new JButton("Send(N)");
		btnSave = new JButton("Save(S)");
		btnTimer = new JButton("Clock(T)");
		lblUserList = new JLabel("Online_list");
		lblUserMessage = new JLabel("Information");
		lblSendMessage = new JLabel("Message:");
		lblChatUser = new JLabel("You:");
		lblUserTotal = new JLabel("Current:");
		lblCount = new JLabel("0");
		lstUserList = new java.awt.List();
		txtMessage = new JTextField(170);
		cmbUser = new JComboBox(list);
		cmbUser.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				freshHead();
			}
		});
		chPrivateChat = new JCheckBox("private");
		taUserMessage = new TextArea("", 300, 200,TextArea.SCROLLBARS_VERTICAL_ONLY);// only permit scroll down
		taUserMessage.setForeground(new Color(0, 0, 0));
		taUserMessage.setEditable(false); // not permit to write

		/*
		 * This layout choose layout by user, setBounds use to set location of each 
		 * component, setFont use to set font style, format and size, setForeground 
		 * use to set font color, setBackground use to set background color, setOpaque
		 * use to set background to transparnet
		 */
		pnlChat.setLayout(null);
		pnlChat.setBackground(new Color(52, 130, 203));
		btnTimer.setBounds(400, 360, 90, 25);
		btnSave.setBounds(500, 330, 90, 25);
		btnCls.setBounds(400, 330, 90, 25);
		btnExit.setBounds(500, 360, 90, 25);
		btnSend.setBounds(500, 300, 90, 25);

		lblUserList.setBounds(5, 0, 120, 40);
		lblUserTotal.setBounds(130, 0, 60, 40);
		lblCount.setBounds(190, 0, 60, 40);
		lblUserMessage.setBounds(225, 0, 180, 40);
		lblChatUser.setBounds(10, 290, 40, 40);
		lblSendMessage.setBounds(210, 290, 60, 40);

		lstUserList.setBounds(5, 40, 210, 255);
		taUserMessage.setBounds(225, 40, 360, 255);
		txtMessage.setBounds(270, 300, 210, 25);
		cmbUser.setBounds(50, 300, 80, 25);
		chPrivateChat.setBounds(300, 336, 75, 20);
		btnTimer.setFont(fntDisp1);
		btnCls.setFont(fntDisp1);
		btnExit.setFont(fntDisp1);
		btnSend.setFont(fntDisp1);
		btnSave.setFont(fntDisp1);
		lblUserList.setFont(fntDisp1);
		lblUserMessage.setFont(fntDisp1);
		lblChatUser.setFont(fntDisp1);
		lblSendMessage.setFont(fntDisp1);
		lblUserTotal.setFont(fntDisp1);
		lblCount.setFont(fntDisp1);
		cmbUser.setFont(fntDisp1);
		chPrivateChat.setFont(fntDisp1);

		lblUserList.setForeground(Color.YELLOW);
		lblUserMessage.setForeground(Color.YELLOW);
		lblSendMessage.setForeground(Color.black);
		lblChatUser.setForeground(Color.black);
		lblSendMessage.setForeground(Color.black);
		lblUserTotal.setForeground(Color.YELLOW);
		lblCount.setForeground(Color.YELLOW);
		cmbUser.setForeground(Color.black);
		chPrivateChat.setForeground(Color.black);
		lstUserList.setBackground(Color.white);
		taUserMessage.setBackground(Color.white);
		btnTimer.setBackground(Color.ORANGE);
		btnCls.setBackground(Color.ORANGE);
		btnExit.setBackground(Color.ORANGE);
		btnSend.setBackground(Color.PINK);
		btnSave.setBackground(Color.ORANGE);
		pnlChat.add(btnTimer);
		pnlChat.add(btnCls);
		pnlChat.add(btnExit);
		pnlChat.add(btnSend);
		pnlChat.add(btnSave);
		pnlChat.add(lblUserList);
		pnlChat.add(lblUserMessage);
		pnlChat.add(lblSendMessage);
		pnlChat.add(lblChatUser);
		pnlChat.add(lblUserTotal);
		pnlChat.add(lblCount);
		pnlChat.add(lstUserList);
		pnlChat.add(taUserMessage);
		pnlChat.add(txtMessage);
		pnlChat.add(cmbUser);
		pnlChat.add(chPrivateChat);

		frmChat.addWindowListener(new Windowclose());
		btnTimer.addActionListener(this);
		btnCls.addActionListener(this);
		btnExit.addActionListener(this);
		btnSend.addActionListener(this);
		btnSave.addActionListener(this);
		lstUserList.addActionListener(this);
		txtMessage.addActionListener(this);

		headLabel.setHorizontalAlignment(SwingConstants.CENTER);
		headLabel.setIcon(new ImageIcon("face//1.PNG"));
		headLabel.setBounds(15, 335, 70, 60);
		pnlChat.add(headLabel);

		emote = new JComboBox();
		emote.setModel(new DefaultComboBoxModel(new String[] { "smile", "angry","laugh"}));
		emote.setBounds(141, 301, 60, 23);
		pnlChat.add(emote);

		zitiComboBox = new JComboBox();
		zitiComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				freshFont();
			}

		});
		zitiComboBox.setModel(new DefaultComboBoxModel(new String[] { "Arial",
				"BOLD" }));
		zitiComboBox.setBounds(76, 337, 64, 23);
		pnlChat.add(zitiComboBox);

		yangShiComboBox = new JComboBox();
		yangShiComboBox.setModel(new DefaultComboBoxModel(new String[] { "Normal",
				"BOLD", "ITALIC", "BOLD-ITALIC" }));
		yangShiComboBox.setBounds(153, 337, 52, 23);
		yangShiComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				freshFont();
			}

		});
		pnlChat.add(yangShiComboBox);

		daXiaoComboBox = new JComboBox();
		daXiaoComboBox.setModel(new DefaultComboBoxModel(new String[] { "12",
				"14", "16", "18", "20" }));
		daXiaoComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				freshFont();
			}

		});
		daXiaoComboBox.setBounds(220, 335, 40, 23);
		pnlChat.add(daXiaoComboBox);

		blueSlider.setMaximum(255);
		blueSlider.setValue(128);
		blueSlider.setMinimum(0);
		blueSlider.addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent arg0) {
				freshColor();
			}
		});
		blueSlider.setBounds(80, 365, 180, 10);
		pnlChat.add(blueSlider);

		redSlider.setMaximum(255);
		redSlider.setValue(128);
		redSlider.setMinimum(0);
		redSlider.addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent arg0) {
				freshColor();
			}
		});
		redSlider.setBounds(80, 380, 180, 10);
		pnlChat.add(redSlider);

		greenSlider.setMaximum(255);
		greenSlider.setValue(128);
		greenSlider.setMinimum(0);
		greenSlider.addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent arg0) {
				freshColor();
			}
		});
		greenSlider.setBounds(80, 395, 180, 10);
		pnlChat.add(greenSlider);

		hanziLabel.setForeground(new Color(128, 128, 128));
		hanziLabel.setHorizontalAlignment(SwingConstants.CENTER);
		hanziLabel.setFont(new Font("Arial", Font.BOLD, 18));
		hanziLabel.setBackground(Color.ORANGE);
		hanziLabel.setText("word");
		hanziLabel.setBounds(273, 360, 57, 50);
		pnlChat.add(hanziLabel);

		// start the thread which use to refresh the chatting information 
		Thread thread = new Thread(this);
		thread.start();


		frmChat.setSize(600, 461);
		frmChat.setVisible(true);
		frmChat.setResizable(false);
        
		// set the window in the middle of the screen
		scrnsize = toolkit.getScreenSize();
		frmChat.setLocation(scrnsize.width / 2 - frmChat.getWidth() / 2,
				scrnsize.height / 2 - frmChat.getHeight() / 2);
		Image img = toolkit.getImage("images\\appico.jpg");
		frmChat.setIconImage(img);

	} // constructor end

	protected void freshFont() {
		String ziti = zitiComboBox.getSelectedItem().toString();
		int yangShi = 0;
		String yangShiString = yangShiComboBox.getSelectedItem().toString();
		if (yangShiString.equals("Normal")) {
			yangShi = Font.PLAIN;
		} else if (yangShiString.equals("BOLD")) {
			yangShi = Font.BOLD;
		} else if (yangShiString.equals("ITALIC")) {
			yangShi = Font.ITALIC;
		} else if (yangShiString.equals("BOLD-ITALIC")) {
			yangShi = Font.BOLD | Font.ITALIC;
		}

		int daXiao = Integer.parseInt(daXiaoComboBox.getSelectedItem()
				.toString());
		taUserMessage.setFont(new Font(ziti, yangShi, daXiao));
		taUserMessage.setForeground(hanziLabel.getForeground());
	}

	protected void freshColor() {
		hanziLabel.setForeground(new Color(blueSlider.getValue(), redSlider
				.getValue(), greenSlider.getValue()));
		freshFont();
	}

	@SuppressWarnings("deprecation")
	public void run() {
		int intMessageCounter = 0;
		int intUserTotal = 0;
		boolean isFirstLogin = true; // detect if this user log-in just recently
		boolean isFound; // detect if find this user in the user file
		Vector user_exit = new Vector();

		try {
			for (;;) {
				Socket toServer;
				toServer = new Socket(strServerIp, 1001);
				// send message to server
				messobj = new Message();
				ObjectOutputStream streamtoserver = new ObjectOutputStream(
						toServer.getOutputStream());
				streamtoserver.writeObject((Message) messobj);
				// receive message from server
				ObjectInputStream streamfromserver = new ObjectInputStream(
						toServer.getInputStream());
				messobj = (Message) streamfromserver.readObject();
				// refresh chat information list
				if (isFirstLogin) // if this user log-in recently
				{
					intMessageCounter = messobj.chat.size(); 
					isFirstLogin = false;
				}
				if (strLoginName.equals(messobj.ti)) {
					exitChatRoom();
					JOptionPane.showMessageDialog(null, strLoginName
							+" "+"Kick off by manager!");

				}
				
				if (!serverMessage.equals(messobj.serverMessage)) {
					serverMessage = messobj.serverMessage;
					taUserMessage.append("[System-message]:" + serverMessage+"\n");
				}
				for (int i = intMessageCounter; i < messobj.chat.size(); i++) {
					Chat temp = (Chat) messobj.chat.elementAt(i);

					String emote = temp.emote;
					if (emote.equals("emote")) {
						emote = "";
					} else {
						emote += "";
					}
					String temp_message;
					if (temp.chatUser.equals(strLoginName)) {
						if (temp.chatToUser.equals(strLoginName)) {
							temp_message = "Not to talk to yourself" + "\n";
						} else {
							if (!temp.whisper) // if the kind of chat is not whisper
							{
								temp_message = "[You to]" +" "+temp.chatToUser +" "+emote + " "+"said:" + temp.chatMessage+ "\n";
							} else 
							{
								temp_message = "[You private to]"+" "+temp.chatToUser+" "+ emote+" "+"said:" + temp.chatMessage+ "\n";
							}
						}
					} else {
						if (temp.chatToUser.equals(strLoginName)) {
							if (!temp.whisper) // if the kind of chat is not whisper
							{
								temp_message = "["+temp.chatUser+"]"+" "+"to [you]"+" "+emote +" "+"said:" +" "+temp.chatMessage+ "\n";
							} else 
							{
								temp_message = "["+temp.chatUser+"]"+" "+"private to [you]"+" "+emote +" "+"said:" +" "+temp.chatMessage+ "\n";
							}
						} else {
							if (!temp.chatUser.equals(temp.chatToUser)) 
							{
								if (!temp.whisper) // if the kind of chat is not whisper
								{
									temp_message = "["+temp.chatUser +"]to["+ temp.chatToUser +"]"+" "+emote+" "+"said:"+" "+temp.chatMessage + "\n";
								} else {
									temp_message = "";
								}
							} else {
								temp_message = "";
							}
						}
					}
					taUserMessage.append(temp_message);
					intMessageCounter++;
				}

				// refresh online user list
				lstUserList.clear();
				for (int i = 0; i < messobj.userOnLine.size(); i++) {
					String User = ((Customer) messobj.userOnLine.elementAt(i)).custName;
					lstUserList.addItem(User);
				}
				Integer a = new Integer(messobj.userOnLine.size());
				lblCount.setText(a.toString());
				// show the message which indicates this user join the chat room
				if (messobj.userOnLine.size() > intUserTotal) {
					String tempstr = ((Customer) messobj.userOnLine
							.elementAt(messobj.userOnLine.size() - 1)).custName;
					if (!tempstr.equals(strLoginName)) {
						taUserMessage.append("[" + tempstr + "]come" + "\n");
					}
				}

				// show the message which indicates this user exit the chat room
				if (messobj.userOnLine.size() < intUserTotal) {
					for (int b = 0; b < user_exit.size(); b++) {
						isFound = false;
						for (int c = 0; c < messobj.userOnLine.size(); c++) {
							String tempstr = ((Customer) user_exit.elementAt(b)).custName;

							if (tempstr.equals(((Customer) messobj.userOnLine
									.elementAt(c)).custName)) {
								isFound = true;
								break;
							}
						}
						if (!isFound) // failed to find this user
						{
							String tempstr = ((Customer) user_exit.elementAt(b)).custName;

							if (!tempstr.equals(strLoginName)) {
								taUserMessage.append("[" + tempstr + "]exit"
										+ "\n");
							}
						}
					}
				}
				user_exit = messobj.userOnLine;
				intUserTotal = messobj.userOnLine.size();
				streamtoserver.close();
				streamfromserver.close();
				toServer.close();
				Thread.sleep(3000);
			}

		} catch (Exception e) {
			@SuppressWarnings("unused")
			JOptionPane jop = new JOptionPane();
			JOptionPane.showMessageDialog(null, "Failed to connect server!");
			e.printStackTrace();
			frmChat.dispose();
		}
	} // run() method complete

	private void exitChatRoom() 
	{
		exit();
	}

	// monitor each button and give relative response
	public void actionPerformed(ActionEvent ae) {
		Object source = (Object) ae.getSource();
		if (source.equals(btnTimer)) {
			new Clock();
		}
		if (source.equals(btnCls)) {
			clearMessage();
		}
		if (source.equals(btnExit)) {
			exit();
		}
		if (source.equals(btnSend)) {
			sendMessage();
		}
		if (source.equals(btnSave)) {
			saveMessage();
		}
		if (source.equals(lstUserList)) // double click user list frame
		{
			changeUser();
		}
	} // action performed() complete

	// monitor if the window is closed
	class Windowclose extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			exit();
		}
	}

	// clear button action performed
	public void clearMessage() {
		taUserMessage.setText("");
	}

	// exit button action performed
	public void exit() {
		Exit exit = new Exit();
		exit.exitname = strLoginName;
		// send exit message
		try {
			Socket toServer = new Socket(strServerIp, 1001);
			// send this message to server
			ObjectOutputStream outObj = new ObjectOutputStream(toServer
					.getOutputStream());
			outObj.writeObject(exit);
			outObj.close();
			toServer.close();

			frmChat.dispose();
		} catch (Exception e) {
		}
	} // exit() end

	// send button action performed
	public void sendMessage() {
		Chat chatobj = new Chat();
		chatobj.chatUser = strLoginName;
		chatobj.chatMessage = txtMessage.getText();
		chatobj.chatToUser = String.valueOf(cmbUser.getSelectedItem());
		chatobj.whisper = chPrivateChat.isSelected() ? true : false;
		chatobj.emote = emote.getSelectedItem().toString();
		// send this message to server
		try {
			Socket toServer = new Socket(strServerIp, 1001);
			ObjectOutputStream outObj = new ObjectOutputStream(toServer
					.getOutputStream());
			outObj.writeObject(chatobj);
			txtMessage.setText(""); // clear text box
			outObj.close();
			toServer.close();
		} catch (Exception e) {
		}
	}
	
	// save button action performed 
	public void saveMessage() {
		try {
			FileOutputStream fileoutput = new FileOutputStream(this.strLoginName+" "+"_message.txt", true);
			String temp = taUserMessage.getText();
			fileoutput.write(temp.getBytes());
			fileoutput.close();
			JOptionPane.showMessageDialog(null, "Chat message save in" + " "+this.strLoginName
					+" "+"_message.txt");
		} catch (Exception e) {
			System.out.println(e);
		}

	}
    
	public void changeUser() 
	{
		boolean key = true;
		String selected = lstUserList.getSelectedItem();
		for (int i = 0; i < cmbUser.getItemCount(); i++) {
			if (selected.equals(cmbUser.getItemAt(i))) {
				key = false;
				break;
			}
		}
		if (key == true) {
			cmbUser.insertItemAt(selected, 0);
		}
		String head = getUserHead(lstUserList.getSelectedItem());
		cmbUser.setSelectedItem(selected);
		headLabel.setIcon(new ImageIcon("face//" + head + ".PNG"));
	} 


	protected void freshHead() 
	{
		String head = getUserHead(cmbUser.getSelectedItem().toString());
		headLabel.setIcon(new ImageIcon("face//" + head + ".PNG"));
	}

	private String getUserHead(String selectedItem) 
	{
		String head = "oo";
		for (int i = 0; i < messobj.userOnLine.size(); i++) {
			String User = ((Customer) messobj.userOnLine.elementAt(i)).custName;
			head = ((Customer) messobj.userOnLine.elementAt(i)).custHead;
			if (User.equals(selectedItem)) {
				break;
			}
		}
		return head;
	}

	public static void main(String args[]) {
		new ChatRoom("Test user", "127.0.0.1");
	}

}