package serverPkg;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Chat implements Serializable {
    //private static final int MAX_USERS = 2;
    //protected String[] users = new String[MAX_USERS];
    protected ArrayList<String> userList;	//Holds the list of users names or users id
    protected ArrayList<Message> msgList;	//Holds the list of message objects
    protected Date created;		//Date the Receiver was created
    protected String createdByUser; //Name of user who created the Receiver
    private final String chatID;
    private int messageCount;   // Tracker of messages
    private String chatName;        // ChatName is the username of the recipient
    protected int chatCount;

    //Constructor
    public Chat(String user) {
        this.createdByUser = null;
        this.userList = new ArrayList<>();
        this.msgList = new ArrayList<>();
        this.created = new Date();
        this.chatID = idGenerator();
        this.messageCount = 0;
        this.chatCount = 1;

        userList.add(user);
    }

    public String getChatID() {
        return chatID;
    }

    public String getChatName() {
        return chatName;
    }
    public ArrayList<Message> getMsgList() {
        return msgList;
    }

    public Message getMessageObject(int index) {
        return msgList.get(index);
    }

    public ArrayList<String> getUserList() {
        return userList;
    }

    public String getUserID(int index) {
        return userList.get(index);
    }

    public String getCreator() {
        return createdByUser;
    }

    public String getDate() {
        return created.toString();
    }
    public void setChatName(ArrayList<String> userList) {
        chatName = userList.get(1);
    }
    public int getChatCount() {
        return this.chatCount;
    }

    //Generates IDs for chats.
    private String idGenerator() {
        // Generate random id, for example 283952-V8M32
        Random rnd = new Random();
        return (100000 + rnd.nextInt(900000)) + "-" + "C";
    }

}
