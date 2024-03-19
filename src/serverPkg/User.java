package serverPkg;

import packetPkg.StatusType;

import java.util.ArrayList;
import java.io.Serializable;
import java.util.Random;

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
        this.acctNum = idGenerator();
    }

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

    private String idGenerator() {
        // Generate random id, for example 283952-V8M32
        Random rnd = new Random();
        return (100000 + rnd.nextInt(900000)) + "-" + "U";
    }
}




