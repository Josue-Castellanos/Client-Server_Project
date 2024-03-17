package serverPkg;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Group {
    //Fields for the Group Class
    protected ArrayList<String> userList;	//Holds the list of users
    protected ArrayList<Message> msgList;	//Holds the list of message objects
    protected Date created;		//Date the Receiver was created
    protected String createdByUser; //Name of user who created the Receiver
    private static int groupCount = 0;
    private String groupID;			//Uniquely Generated groupID
    private String groupName;		//Name for the Group. Doesn't have to be unique at the moment. Can implement uniqueness later if needed.
    public boolean isPrivate;		//Indicates whether the group is designated private or public. If private, should not be accessible by users not on the userList.

    //Group Constructor
    public Group(String user, boolean privacy, String newGroupName) {
        //leadMod = user;
        createdByUser = user;
        //addUser to the list. Need to implement.
        isPrivate = privacy;
        groupName = newGroupName;
        created = new Date();
        groupID = idGenerator();
        groupCount++;
    }

    public String getGroupID() {
        return groupID;
    }

    public String getGroupname() {
        return groupName;
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

    public void deleterGroup() {
        //Need to implement
    }
    public void getGroup() {
        return;
    }

    //Generates IDs for groups.
    private String idGenerator() {
        // Generate random id, for example 283952-V8M32
        Random rnd = new Random();
        return (100000 + rnd.nextInt(900000)) + "-" + "G";
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
