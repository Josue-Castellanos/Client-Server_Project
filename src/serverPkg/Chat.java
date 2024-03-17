package serverPkg;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Chat {
    //Fields for the Chat Class
    protected ArrayList<String> userList;	//Holds the list of users names or users id
    protected ArrayList<Message> msgList;	//Holds the list of message objects
    protected Date created;		//Date the Receiver was created
    protected String createdByUser; //Name of user who created the Receiver
    private final String chatID;
    private static final int chatCount = 1;
    private String chatName;        // ChatName is the username of the recipient

    //Constructor
    public Chat(String user, ArrayList<String> userList, ArrayList<Message> msgList) {
        this.createdByUser = user;
        this.userList = userList;
        this.msgList = msgList;
        this.created = new Date();
        this.chatID = idGenerator();
    }


    public String getChatID() {
        return chatID;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String newChatName) {
        chatName = newChatName;
    }

    public ArrayList<Message> getMsgList() {
        return msgList;
    }

    public Message getMessage(int index) {
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

    //Generates IDs for chats.
    private String idGenerator() {
        // Generate random id, for example 283952-V8M32
        Random rnd = new Random();
        return (100000 + rnd.nextInt(900000)) + "-" + "C";
    }

    //Searches for in the list
    private int findHelper(String userID, ArrayList<String> userList) {
        for(int i = 0; i < userList.size(); i++) {
            if (userID == userList.get(i)){
                //If found, return index
                return i;
            }
        }
        //If not found, return -1;
        return -1;
    }

}
