import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Date;
import java.util.Vector;

public class Connection extends Thread 
{

	private Socket netClient;

	private Vector<Customer> userOnline;

	private Vector<Chat> userChat;

	private ObjectInputStream fromClient;

	private PrintStream toClient;

	private static Vector vList = new Vector();

	private Object obj;

	private ServerFrame sFrame;

	@SuppressWarnings("unchecked")
	public Connection(ServerFrame frame, Socket client, Vector u, Vector c) {
		netClient = client;
		userOnline = u;
		userChat = c;
		sFrame = frame;
		try {
			fromClient = new ObjectInputStream(netClient.getInputStream());
			toClient = new PrintStream(netClient.getOutputStream());
		} catch (IOException e) {
			try {
				netClient.close();
			} catch (IOException e1) {
				System.out.println("Failed to set data flow" + e1);
				return;
			}
		}
		this.start();
	}

	public void run() {
		try 
		{
			obj = (Object) fromClient.readObject();
			if (obj.getClass().getName().equals("Customer")) {
				serverLogin();
			}
			if (obj.getClass().getName().equals("Register_Customer")) {
				serverRegiste();
			}
			if (obj.getClass().getName().equals("Message")) {
				serverMessage();
			}
			if (obj.getClass().getName().equals("Chat")) {
				serverChat();
			}
			if (obj.getClass().getName().equals("Exit")) {
				serverExit();
			}
		} catch (IOException e) {
			System.out.println(e);
		} catch (ClassNotFoundException e1) {
			System.out.println("Failed to read object" + e1);
		} finally {
			try {
				netClient.close();
			} catch (IOException e) {
				System.out.println(e);
			}
		}
	}

	@SuppressWarnings("deprecation")
	public void serverLogin() {

		try {
			Customer clientMessage2 = (Customer) obj;
			FileInputStream file3 = new FileInputStream("user.txt");
			ObjectInputStream objInput1 = new ObjectInputStream(file3);
			vList = (Vector) objInput1.readObject();

			int find = 0; 
			for (int i = 0; i < vList.size(); i++) {
				Register_Customer reg = (Register_Customer) vList.elementAt(i);

				if (reg.custName.equals(clientMessage2.custName)) {
					find = 1;
					if (!reg.custPassword.equals(clientMessage2.custPassword)) {
						toClient.println("password not correct");
						break;
					} else {
						int login_flag = 0;
						for (int a = 0; a < userOnline.size(); a++) {
							String _custName = ((Customer) userOnline
									.elementAt(a)).custName;
							if (clientMessage2.custName.equals(_custName)) {
								login_flag = 1;
								break;
							}
						}

						if (userOnline.size() >= 50) {
							toClient.println("User number reach max,try it later");
							break;
						}

						if (login_flag == 0) {
							clientMessage2.custHead = reg.head;
							userOnline.addElement(clientMessage2);
							toClient.println("log_successful");
							Date t = new Date();
							log("User" +" "+ clientMessage2.custName +" "+"login successful,"
									+ " "+"time:" + t.toLocaleString() + "\n");
							freshServerUserList();
							break;
						} else {
							toClient.println("This user is log-on");
						}
					}
				} else {
					continue;
				}
			}
			if (find == 0) {
				toClient.println("Register first");
			}

			file3.close();
			objInput1.close();
			fromClient.close();
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	private void freshServerUserList() {
		String[] userList = new String[50];
		Customer cus = null;
		for (int j = 0; j < userOnline.size(); j++) {
			cus = (Customer) userOnline.get(j);
			userList[j] = cus.custName;
		}
		sFrame.list.setListData(userList);
		sFrame.txtNumber.setText("" + userOnline.size());
		sFrame.lblUserCount.setText("Current_online:" + userOnline.size());
	}

	@SuppressWarnings( { "unchecked", "deprecation" })
	public void serverRegiste() {
		try {
			int flag = 0; 
			Register_Customer clientMessage = (Register_Customer) obj;
			File fList = new File("user.txt");
			if (fList.length() != 0)
			{
				ObjectInputStream objInput = new ObjectInputStream(
						new FileInputStream(fList));
				vList = (Vector) objInput.readObject();
				for (int i = 0; i < vList.size(); i++) {
					Register_Customer reg = (Register_Customer) vList
							.elementAt(i);
					if (reg.custName.equals(clientMessage.custName)) {
						toClient.println("Choose another name due to repeat");
						flag = 1;
						break;
					} else if (reg.custName.equals("All")) {
						toClient.println("Forbid this name, choose another!");
						flag = 1;
						break;
					}
				}
			}
			if (flag == 0) {
				vList.addElement(clientMessage);
				FileOutputStream file = new FileOutputStream(fList);
				ObjectOutputStream objout = new ObjectOutputStream(file);
				objout.writeObject(vList);

				toClient.println(clientMessage.custName +" "+"register_successful");
				Date t = new Date();
				
				log("User" + " "+clientMessage.custName + " "+"register successful, " + " "+"register time:"
						+ " "+t.toLocaleString() + "\n");
				file.close();
				objout.close();
				fromClient.close();
			}
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	public void serverMessage() {
		try {
			Message mess = new Message();
			mess.userOnLine = userOnline;
			mess.chat = userChat;
			mess.ti = sFrame.kick;
			mess.serverMessage = "" + sFrame.serverMessage;
			ObjectOutputStream outputstream = new ObjectOutputStream(netClient
					.getOutputStream());
			outputstream.writeObject((Message) mess);
			netClient.close();
			outputstream.close();
		} catch (IOException e) {
		}

	}

	public void serverChat() {
		Chat cObj = new Chat();
		cObj = (Chat) obj;
		cObj.chatMessage = WordFilter.filter(cObj.chatMessage);
		chatLog(cObj);
		userChat.addElement((Chat) cObj);
		return;
	}


	@SuppressWarnings("deprecation")
	public void serverExit() {
		Exit exit = new Exit();
		exit = (Exit) obj;
		removeUser(exit);
		if (sFrame.kick.equals(exit.exitname)) {
			sFrame.kick = "";
		}
		Date t = new Date();
		log("User" +" " +exit.exitname + " "+"have exit, " +" " +"time:" + " "+t.toLocaleString());

		freshServerUserList();
	}

	private void removeUser(Exit exit) 
	{
		Vector<Customer> vec = new Vector<Customer>();
		Customer _cus = null;
		for (int j = 0; j < userOnline.size(); j++) {
			_cus = (Customer) userOnline.get(j);
			if (!exit.exitname.equals(_cus.custName)) {
				vec.add(_cus);
			}
		}
		userOnline.removeAllElements();
		for (int j = 0; j < vec.size(); j++) {
			_cus = (Customer) vec.get(j);
			userOnline.add(_cus);
		}
	}
	public void log(String log) 
	{
		String newlog = sFrame.taLog.getText() + "\n" + log;
		sFrame.taLog.setText(newlog);
	}

	@SuppressWarnings("deprecation")
	public void chatLog(Chat obj) {
		String newlog = sFrame.taMessage.getText();
		Date date = new Date();
		if (!obj.whisper) {
			newlog += "\n";
			newlog += ("[" + date.getHours() + ":" + date.getMinutes() + ":"
					+ date.getSeconds() + "]");
			newlog += obj.chatUser;
			newlog += "->";
			newlog += obj.chatToUser;
			newlog += ":";
			newlog += obj.chatMessage;
		}

		sFrame.taMessage.setText(newlog);
	}
}