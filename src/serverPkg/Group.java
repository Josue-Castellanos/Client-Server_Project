package serverPkg;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Group implements Serializable {

    protected ArrayList<String> userList;	//Holds the list of userIDs
    protected ArrayList<Message> msgList;	//Holds the list of message objects
    protected Date created;		//Date the Receiver was created
    protected String createdByUser; //Name of user who created the Receiver
    protected int groupCount;
    protected int groupMessages;
    private final String groupID;		//Uniquely Generated groupID
    private final String groupName;		//Name for the Group. Doesn't have to be unique at the moment. Can implement uniqueness later if needed.
    protected boolean isPrivate;		//Indicates whether the group is designated private or public. If private, should not be accessible by users not on the userList.

    //Group Constructor
    public Group(String user, boolean privacy, String newGroupName) {
        this.createdByUser = user;
        this.isPrivate = privacy;
        this.groupName = newGroupName;
        this.userList = new ArrayList<>();
        this.msgList = new ArrayList<>();
        this.created = new Date();
        this.groupID = idGenerator();
        this.groupMessages = 0;
        this.groupCount = 0;
    }

    public String getGroupID() {
        return groupID;
    }

    public String getGroupName() {
        return groupName;
    }


    public ArrayList<Message> getMessageList() {
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

    public boolean getPrivacy() {
        return isPrivate;
    }
    public int getGroupCount() {
        return groupCount;
    }


    //Generates IDs for groups.
    private String idGenerator() {
        // Generate random id, for example 283952-V8M32
        Random rnd = new Random();
        return (100000 + rnd.nextInt(900000)) + "-" + "G";
    }


}
