package serverPkg;

import java.util.ArrayList;
import java.util.Date;

public class Group {
    //Fields for the Group Class - Child of Receiver
    //private String leadMod;			//Current lead moderator of a group - Default is the creator of the group
    // private ArrayList<String> moderators;	//Additional moderators added by the lead moderator
    protected ArrayList<String> userList;	//Holds the list of users
    protected ArrayList<Message> msgList;	//Holds the list of message objects
    protected Date created;		//Date the Receiver was created
    protected String createdByUser; //Name of user who created the Receiver
    private int groupCount = 0;
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
        groupID = idHelper(groupCount, "Grp-");
        groupCount++;
    }

    public String getGroupID() {
        return groupID;
    }

    //		public String getLeadModerator() {
    //			return leadMod;
    //		}

    //		public void changeLeadModerator(String userID) {
    //			leadMod = userID;
    //		}

    //		public ArrayList<String> getModerators() {
    //			return moderators;
    //		}
    //Adds a moderator to the list
    //		public void addModerator(String userID) {
    //			int index = findHelper(userID, moderators);
    //			if (index == -1) {
    //				moderators.add(userID);
    //				Collections.sort(moderators);
    //			}
    //			else {
    //				//System.out.println("\nUser " + userID + " is already a member. \n");
    //			}
    //		}

    //Removes a moderator from the list
    //		public void removeModerator(String userID) {
    //			int index = findHelper(userID, moderators);
    //			if (index != -1) {
    //				moderators.add(userID);
    //			}
    //			else {
    //				//System.out.println("\nUser " + userID + " is already a member. \n");
    //			}
    //		}

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

    //Generates IDs for groups and chats.
    private String idHelper(int counter, String receiver ) {
        String temp = new String();
        temp = ("00000000" + Integer.toString(counter)).substring(Integer.toString(counter).length()); //Converts counter to a string, and adds leading zeroes.
        return receiver + temp;
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
