package serverPkg;

import java.util.ArrayList;
import java.io.Serializable;

public class User implements Serializable{

	protected String displayName;
	//protected UserType userType;
	protected String acctNum;
	protected String username;
	protected String password;
	protected ArrayList<String> groupList;	//Unique groupID should be stored here.
	protected ArrayList<String> chatList;	//Unique chatID should be stored here.
	//protected ArrayList<String> blockList;	//Unique userID should be stored here.
	//protected ArrayList<String> inviteList;	//Unique groupID invited to Join
	protected boolean status;

	public User() {
		this(null,null,null);
	} //userType null);
	
    public User(String displayName, String username, String password) {  // UserType userType) {
        this.displayName = displayName;
        this.username = username;
        this.password = password;
        //this.userType = userType;
        this.status = true;
        this.groupList = new ArrayList<String>();
        this.chatList = new ArrayList<String>();
//        this.blockList = new ArrayList<String>();
//        this.inviteList = new ArrayList<String>();
    }
    
    //Copy constructor
//    public User(User newUser) {
//    	this.displayName = newUser.displayName;
//        this.username = newUser.getUsername();
//        this.password = newUser.getPassword();
//        this.userType = newUser.getUserType();
//        this.status = newUser.getStatus();
//        this.acctNum = newUser.getAcctNum();
//        this.groupList = newUser.groupList;
//        this.chatList = newUser.chatList;
//        this.blockList = newUser.getBlockList();
//        this.inviteList = newUser.getInviteList();
//    }

    public String getDisplayName() {
        return displayName;
    }

//    public UserType getUserType() {
//        return userType;
//    }

    public String getAcctNum() {
        return acctNum;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

//    public boolean getStatus() {
//        return status;
//    }

//    public ArrayList<String> getBlockList() {
//        return blockList;
//    }
    
    public ArrayList<String> getGroupList() {
        return groupList;
    }
    
    public ArrayList<String> getChatList() {
        return chatList;
    }
    
//    public ArrayList<String> getInviteList() {
//        return inviteList;
//    }
    
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
    
//    public void setUserType(UserType type) {
//        this.userType = type;
//       }

//    public void setStatus(boolean newStatus) {
//        this.status = newStatus;
//    }

//    public void setBlockList(ArrayList<String> blockList) {
//      this.blockList = new ArrayList<String>(blockList);
//    }
    
    public void setGroupList(ArrayList<String> newGroup) {
    	this.groupList= newGroup;
    }
    
    public void setChatList(ArrayList<String> newChat) {
    	this.chatList= newChat;
    }
    
    /**********************	Mutators	*********************/
//    public void addToBlockList(String acctNum) {
//    	blockList.add(acctNum);
//    }
    
    public void addToGroupList(String newGroup) {
    	groupList.add(newGroup);
    }
    
    public void addToChatList(String newChat) {
    	chatList.add(newChat);
    }
    
//    public void addToInviteList(String newInvite) {
//    	inviteList.add(newInvite);
//    }
    
//    public void removeFromBlockList(String acctNum) {
//    	blockList.remove(acctNum);
//    }
    
    public void removeFromGroupList(String trgtGroup) {
    	groupList.remove(trgtGroup);
    }
    
    public void removeFromChatList(String trgtChat) {
    	chatList.remove(trgtChat);
    }
    
//    public void removeFromInviteList(String trgtInvite) {
//    	inviteList.remove(trgtInvite);
//    }
    
//    public class GeneralUser extends User {
//
//    	  public GeneralUser(String displayName, String username, String password, UserType userType) {
//    		  super(displayName,username, password, userType);
//    		  this.status = true;
//    	    }
//		/* public void reportIT(String acctNum) {
//		        // implementation to report an issue to the IT department
//		    }*/
//
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
    
}




