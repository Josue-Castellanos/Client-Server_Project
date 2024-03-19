package serverPkg;
import java.util.ArrayList;
import java.util.*;

public class Database {

	private static Map<String, User> users = new HashMap<>();  // Map of all Users
	private static Map<String, User> connectedUsers = new HashMap<>();  // Map of (ONLINE) Users only
	private static Map<String, User> disconnectedUsers = new HashMap<>();  // Map of (OFFLINE) Users only
	private static Map<String, Chat> chats = new HashMap<>();  // Holds the map of 1-on-1 chats
	private static Map<String, Group> groups = new HashMap<>();  // Map of all Type Groups
	private static Map<String, Group> publicGroups = new HashMap<>();  // Map of PUBLIC Type Groups only
	private static Map<String, Group> privateGroups = new HashMap<>();  // Map of PRIVATE Type Groups only
	private static int activeGroups = 0; 	// Tracker of groups.
	private static int activeChats = 0; 	// Tracker of chats.


	public Database() {
		User a = new User("adam", "a", "111111", UserType.GENERAL, UserStatus.OFFLINE);
		User b = new User("josh", "b", "222222", UserType.GENERAL, UserStatus.OFFLINE);
		User c = new User("cash", "c", "333333", UserType.GENERAL, UserStatus.OFFLINE);
		users.put(a.getAcctNum(), a);
		users.put(b.getAcctNum(), b);
		users.put(c.getAcctNum(), c);

	}

	/* ================= GETTERS =====================*/
	public Map<String, Group> getPublicGroups() {
		return publicGroups;
	}

	public Map<String, Group> getPrivateGroups() {
		return privateGroups;
	}

	public Map<String, User> getGeneralUsers() {
		return users;
	}

	public Map<String, User> getConnectedUsers() {
		return connectedUsers;
	}

	public Map<String, User> getDisconnectedUsers() {
		return disconnectedUsers;
	}

	public Map<String, Chat> getChats() {
		return chats;
	}

	public Map<String, Group> getGroups() {
		return groups;
	}

	/* =================== MUTATORS ======================*/

	public void removeConnectedUserFromList(User user) {
		if (getConnectedUsers().containsKey(user.getAcctNum())) {
			getConnectedUsers().remove(user.getAcctNum());
			getDisconnectedUsers().put(user.getAcctNum(), user);

		} else {
			System.out.println("\nUser " + user.getAcctNum() + " is not connected.\n");
		}
	}

	public void addConnectedUserToList(User user) {
		if (!getConnectedUsers().containsKey(user.getAcctNum())) {
			getConnectedUsers().put(user.getAcctNum(), user);
		} else {
			System.out.println("\nUser " + user.getAcctNum() + " is already connected.\n");
		}
	}

	public void addUserToUserList(User user) {
		if (!getGeneralUsers().containsKey(user.getAcctNum())) {
			getGeneralUsers().put(user.getAcctNum(), user);
		} else {
			System.out.println("\nUser " + user.getAcctNum() + " already exists. \n");
		}
	}

	public void removeUserFromUserList(String userID) {
		if (getGeneralUsers().containsKey(userID)) {
			getGeneralUsers().remove(userID);
		} else {
			System.out.println("\nUser " + userID + " is not found. \n");
		}
	}

	public void removeUserFromGroup(String userID, String groupID) {
		// check if group exists
		if(getGroups().containsKey(groupID)) {
			Group group = getGroups().get(groupID);
			ArrayList<String> list = group.getUserList();
			int groupCount = group.getGroupCount();

			// Remove userId if listed
			if (list.contains(userID)) {
				list.remove(userID);
				groupCount--;
				// Remove userID from private/public groups
				if (group.getPrivacy() ) {
					getPrivateGroups().get(groupID).getUserList().remove(userID);

				} else {
					getPublicGroups().get(groupID).getUserList().remove(userID);
				}
				// Remove group if it becomes empty after removing the user.
				if (groupCount == 0) {
					removeGroupFromGroupList(group);
				}
			}
			else {
				System.out.println("\nUser " + userID + " is not in group.\n");
			}
		}
		else {
			System.out.println("\nGroup " + groupID + " not found.\n");
		}
	}

	public void addUserToGroup(String userID, String groupID) {
		if(getGroups().containsKey(groupID)) {
			Group group = getGroups().get(groupID);
			ArrayList<String> list = group.getUserList();
			int groupCount = group.getGroupCount();

			if (!list.contains(userID)) {
				list.add(userID);
				groupCount++;
				// Add userID to private/public groups
				if (group.getPrivacy() ) {
					getPrivateGroups().get(groupID).getUserList().add(userID);

				} else {
					getPublicGroups().get(groupID).getUserList().add(userID);
				}
			}
			else {
				System.out.println("\nUser " + userID + " already in group.\n");
			}
		}
		else {
			System.out.println("\nGroup " + groupID + " not found.\n");
		}
	}

	public void removeUserFromChat(String userID, String chatID) {
		// check if chat exists
		if(getChats().containsKey(chatID)) {
			Chat chat = getChats().get(chatID);
			ArrayList<String> list = chat.getUserList();
			int chatCount = chat.getChatCount();

			// Remove userId if listed
			if (list.contains(userID)) {
				list.remove(userID);
				chatCount--;
				// Remove group if it becomes empty after removing the user.
				if (chatCount == 1) {
					removeChatFromChatList(chat);
				}
			}
			else {
				System.out.println("\nUser " + userID + " is not in chat.\n");
			}
		}
		else {
			System.out.println("\nChat " + chatID + " not found.\n");
		}
	}

	public void addUserToChat(String userID, String chatID) {
		if(getChats().containsKey(chatID)) {
			Chat chat = getChats().get(chatID);
			ArrayList<String> list = chat.getUserList();
			int chatCount = chat.getChatCount();

			if (!list.contains(userID)) {
				list.add(userID);
				chatCount++;
			}
			else {
				System.out.println("\nUser " + userID + " already in chat.\n");
			}
		}
		else {
			System.out.println("\nChat " + chatID + " not found.\n");
		}
	}

	// Group methods
	public void addGroupToGroupList(Group group) {
		getGroups().put(group.getGroupID(), group);
		if (group.getPrivacy()) {
			getPrivateGroups().put(group.getGroupID(), group);
		} else {
			getPublicGroups().put(group.getGroupID(), group);
		}
	}

	public void removeGroupFromGroupList(Group group) {
		getGroups().remove(group.getGroupID());
		if (group.getPrivacy()) {
			getPrivateGroups().remove(group.getGroupID(), group);
		} else {
			getPublicGroups().remove(group.getGroupID(), group);
		}
	}

	public void addChatToChatList(Chat chat) {
		if (!getChats().containsKey(chat.getChatID())) {
			getChats().put(chat.getChatID(), chat);
		} else {
			System.out.println("\nChat " + chat.getChatID() + " already exists. \n");
		}
	}

	public void removeChatFromChatList(Chat chat) {
		if (getChats().containsKey(chat.getChatID())) {
			getChats().remove(chat.getChatID(), chat);
		} else {
			System.out.println("\nChat " + chat.getChatID() + " does not exists. \n");
		}
	}
}
