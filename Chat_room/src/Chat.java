import java.io.Serializable;

public class Chat implements Serializable
{

	private static final long serialVersionUID = 4058485121419391969L;

	public String  chatUser; // username who send a message

	public String  chatMessage; // content of chat

	public String  chatToUser; // username who receive the message

	public String  emote; // emote of chat
	
	public boolean whisper; // kind of chat
}  