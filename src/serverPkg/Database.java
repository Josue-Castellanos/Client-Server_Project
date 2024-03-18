package serverPkg;
import packetPkg.*;
import java.util.ArrayList;

public class Database {

	private ArrayList<User> users;	// List of all Users
	private ArrayList<User> connectedUsers;	// List of (ONLINE) Users only
	private ArrayList<User> disconnectedUsers;	// List of (OFFLINE) Users only
	protected ArrayList<Chat> chats;	//Holds the list of 1-on-1 chats
	protected ArrayList<Group> groups;	// List of all Type Groups
	private ArrayList<Group> publicGroups;	// List of PUBLIC Type Groups only
	private ArrayList<Group> privateGroups;	// List of PRIVATE Type Groups only
	//protected ArrayList<Message> msgList;	//Holds the list of messages of Chat/Group
	protected static int chatCount; // Tracker of chats.
	protected static int groupCount; 	// Tracker of groups.

	public Database() {
		this.users = new ArrayList<>();
		this.connectedUsers = new ArrayList<>();
		this.disconnectedUsers = new ArrayList<>();
		this.chats = new ArrayList<>();
		this.groups = new ArrayList<>();
		this.publicGroups = new ArrayList<>();
		this.privateGroups = new ArrayList<>();
		chatCount = 0;
		groupCount = 0;

		User a = new User("adam", "adam", "1234567", UserType.GENERAL, UserStatus.OFFLINE);
		User b = new User("josh", "josh", "1111111", UserType.GENERAL, UserStatus.OFFLINE);
		users.add(a);
		users.add(b);
	}



//	private void createChatSession(User user, User trgtUser) {
//		// Logic to create a session for the user
//		// This might involve generating a session token, storing it in a session management system, etc.
//	}

	public boolean logout(User user) {
		for (int i = 0; i < connectedUsers.size(); i++) {
		      if (connectedUsers.get(i).getAcctNum().equals(user.getAcctNum())) {
		    	  connectedUsers.remove(i);
		      }
		}
		return true;
	}

	//connected user methods
	public void addConnectedUser(User user) {
		connectedUsers.add(user);
	}

	public void removeConnectedUser(User user) {
		for (int i = 0; i < connectedUsers.size(); i++) {
		      if (connectedUsers.get(i).getAcctNum().equals(user.getAcctNum())) {
		    	  connectedUsers.remove(i);
		      }
		}
	}

	public ArrayList<User> getConnectedUsers() {
		return connectedUsers;
	}

	//general user methods
	public void addGeneralUser(User user) {
		users.add(user);
	}

	public void removeGeneralUser(User user) {
		//no delete account
	}

	public ArrayList<User> getGeneralUsers() {
		return generalUser;
	}

	//group methods
	public void addGroup(Group group) {
		if (group.isPrivate == true) {
			privateGroups.add(group);
		}
		else {
			publicGroups.add(group);
		}
	}

	public void removeGroup(Group group) {
		//checks for private or public then copies group to deleted groups while
		//removing from visible groups
		if (group.isPrivate == true) {
			for (int i = 0; i < privateGroups.size(); i++) {
			      if (privateGroups.get(i).getGroupID().equals(group.getGroupID())) {
			    	  privateGroups.remove(i);
			      }
			}
		}
		else {
			for (int i = 0; i < publicGroups.size(); i++) {
			      if (publicGroups.get(i).getGroupID().equals(group.getGroupID())) {
			    	  publicGroups.remove(i);
			      }
			}
		}
	}

	public void removeUserFromGroupList(String user) {
		//
	}

	public void addUserToGroupList(User user) {
		//
	}

	//Adds a user to the list
	public void addUser(String userID) {
		int index = findHelper(userID, userList);
		if (index == -1) {
			userList.add(userID);
			Collections.sort(userList);
		}
		else {
			System.out.println("\nUser " + userID + " is already a member. \n");
		}
	}

	//Removes a user from the list
	public void removeUser(String userID) {
		int index = findHelper(userID, userList);
		if (index != -1) {
			userList.remove(userID);
		}
		else {
			System.out.println("\nUser " + userID + " is already a member. \n");
		}
	}

	public ArrayList<Group> getPublicGroups() {
		return publicGroups;
	}

	public ArrayList<Group> getPrivateGroups() {
		return privateGroups;
	}


	public void writeToGroup(Group group, Message message) {
		group.addMessage(message);
	}

	public Packet readGroup(Group group) {
		Packet packet = new Packet(PacketType.REQUEST, RequestType.RECEIVE_MESSAGE_GROUP, group);
		return packet;
	}

	public void addGroupToList(String user, Group group) {
		group.addUser(user);
	}

	//chat methods
	public void addChatToList(Chat chat) {
		chats.add(chat);
	}

	public UserStatus authenticateUser(User user) {
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getUsername().equals(user.getUsername()) &&
					users.get(i).getPassword().equals(user.getPassword())) {
				addConnectedUser(user);
				user.setUserStatus(UserStatus.ONLINE);
				return user.getUserStatus();
			}
		}
		user.setUserStatus(UserStatus.PROGRESS);
		return user.getUserStatus();
	}

	public boolean verify(User user) {
		//so far only works with general users

	}


	/*
	 * I'm not sure what this method does. Receiver.Chat is a single object, that contains an ArrayList of Message objects. Nabil
	 */
	public void writeToChat(Chat chat, Message message) {
		for (int i = 0; i < chats.size(); i++) {
			if (chat.getChatID().equals(chat.getChatID())) {
				chat.addMessage(message);
			}
			else { //first message
				//Receiver.Chat newChat = new Receiver.Chat(ArrayList<User> userList); // needs work
				//addChat(newChat);
			}
		}
	}

	public Packet readChat(Chat chat) {
		Packet packet = new Packet(PacketType.REQUEST, RequestType.RECEIVE_MESSAGE_CHAT, chat);
		return packet;
	}

	//Add to block list
	public Packet addBlockList(User user, User blocked) {
		user.addToBlockList(blocked.getAcctNum());
		Packet packet = new Packet(PacketType.REQUEST, RequestType.BLOCK_USER, user);
		return packet;
	}

}
