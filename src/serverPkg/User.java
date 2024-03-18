package serverPkg;

import packetPkg.StatusType;

import java.util.ArrayList;
import java.io.Serializable;

public class User implements Serializable {

	protected String displayName;
	protected String acctNum;
	protected String username;
	protected String password;
    protected UserType userType;
    protected UserStatus userStatus;
	protected ArrayList<String> groupList;	//Unique groupID should be stored here.
	protected ArrayList<String> chatList;	//Unique chatID should be stored here.
    protected ArrayList<String> blockList;	//Unique userID should be stored here.

	public User() {
		this(null,null,null, null, null);
	}
	
    public User(String displayName, String username, String password, UserType userType, UserStatus userStatus) {
        this.displayName = displayName;
        this.username = username;
        this.password = password;
        this.userStatus = userStatus;
        this.userType = userType;
        this.groupList = new ArrayList<String>();
        this.chatList = new ArrayList<String>();
        this.blockList = new ArrayList<String>();
    }
//    //Copy constructor
//    public User(User newUser) {
//    	this.displayName = newUser.displayName;
//        this.username = newUser.getUsername();
//        this.password = newUser.getPassword();
//        this.userStatus = userStatus;
//
//        this.status = newUser.getStatus();
//        this.acctNum = newUser.getAcctNum();
//        this.groupList = newUser.groupList;
//        this.chatList = newUser.chatList;
//        this.blockList = newUser.blockList;
//        this.userType = newUser.getUserType();
//    }

    public String getDisplayName() {
        return displayName;
    }

    public String getAcctNum() {
        return acctNum;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public UserType getUserType() {
        return userType;
    }
    
    public ArrayList<String> getGroupList() {
        return groupList;
    }
    
    public ArrayList<String> getChatList() {
        return chatList;
    }

    public ArrayList<String> getBlockList() {
        return blockList;
    }
    
    /****************	Setters	***********************/
    public void setDisplayName(String newName) {
        this.displayName = newName;
    }

    public void setAcctNum(String newAcct) {
        this.acctNum = newAcct;
    }

    public void setUsername(String newUsername) {
        this.username = newUsername;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserStatus(UserStatus newStatus) {
        this.userStatus = newStatus;
    }
    
    public void setGroupList(ArrayList<String> newGroup) {
    	this.groupList= newGroup;
    }
    
    public void setChatList(ArrayList<String> newChat) {
    	this.chatList= newChat;
    }

    public void setBlockList(ArrayList<String> newBlock) {
        this.blockList= newBlock;
    }
    
    /**********************	Mutators	*********************/
    public void addToGroupList(String newGroup) {
    	groupList.add(newGroup);
    }
    
    public void addToChatList(String newChat) {
    	chatList.add(newChat);
    }
    
    public void removeFromGroupList(String trgtGroup) {
    	groupList.remove(trgtGroup);
    }
    
    public void removeFromChatList(String trgtChat) {
    	chatList.remove(trgtChat);
    }
    public void addToBlockList(String newUser) {
        blockList.add(newUser);
    }
}
//    public class GeneralUser extends User {
//
//    	  public GeneralUser(String displayName, String username, String password, UserType userType) {
//    		  super(displayName,username, password, userType);
//    		  this.status = true;
//    	    }
//		/* public void reportIT(String acctNum) {
//		        // implementation to report an issue to the IT department
//		    }*/
//    }
//    public class ITUser extends User {
//
//    	  public ITUser(String displayName, String username, String password, UserType userType) {
//    		  super(displayName,username, password, userType);
//    		  this.status = true;
//    	    }
//    	/*
//    	public void kick(User user) {
//            // implementation to remove user from chat or group
//        }
//
//        public void log(String username) {
//            // implementation to log activity related to a specific user
//        }
//
//        public void log(Group group) {
//            // implementation to log activity related to a specific group
//        }
//
//        public void log(Chat chat) {
//            // implementation to log activity related to a specific chat
//        }
//
//        public void deleteGroup(Group group) {
//            // implementation to delete a group
//        }*/
//
//    }




