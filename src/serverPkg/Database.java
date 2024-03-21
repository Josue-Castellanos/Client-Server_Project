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
		User a = new User("adam", "a", "1", UserType.GENERAL, UserStatus.OFFLINE);
		User b = new User("josh", "b", "2", UserType.GENERAL, UserStatus.OFFLINE);
		User c = new User("cash", "c", "3", UserType.GENERAL, UserStatus.OFFLINE);
		users.put(a.getAcctNum(), a);
		users.put(b.getAcctNum(), b);
		users.put(c.getAcctNum(), c);

		disconnectedUsers.put(a.getAcctNum(), a);
		disconnectedUsers.put(b.getAcctNum(), b);
		disconnectedUsers.put(c.getAcctNum(), c);

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
	public int getActiveGroups() {
		return activeGroups;
	}

	public static int getActiveChats() {
		return activeChats;
	}

	/* =================== MUTATORS ======================*/

	public void removeConnectedUserFromList(User user) {
		if (getConnectedUsers().containsKey(user.getAcctNum())) {
			getConnectedUsers().remove(user.getAcctNum());
			getDisconnectedUsers().put(user.getAcctNum(), user);
			System.out.println("[Database] User " + user.getAcctNum() + " logout accepted!");
		} else {
			System.out.println("[Database] User " + user.getAcctNum() + " not connected!");
		}
	}

	public void addConnectedUserToList(User user) {
		if (!getConnectedUsers().containsKey(user.getAcctNum())) {
			getConnectedUsers().put(user.getAcctNum(), user);
		} else {
			System.out.println("[Database] User " + user.getAcctNum() + " already connected!");
		}
	}

	public void addUserToUserList(User user) {
		if (!getGeneralUsers().containsKey(user.getAcctNum())) {
			getGeneralUsers().put(user.getAcctNum(), user);
			System.out.println("[Database] User " + user.getAcctNum() + " created!");
		} else {
			System.out.println("[Database] User " + user.getAcctNum() + " already exists!");
		}
	}

	public void removeUserFromUserList(String userID) {
		if (getGeneralUsers().containsKey(userID)) {
			getGeneralUsers().remove(userID);
			System.out.println("[Database] User " + userID + " deleted");
		} else {
			System.out.println("[Database] User " + userID + " not found");
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
				System.out.println("[Database] User " + userID + " removed from group " + groupID);
				// Remove userID from private
				if (group.getPrivacy() ) {
					getPrivateGroups().get(groupID).getUserList().remove(userID);
					System.out.println("[Database] User " + userID + " removed from private group " + groupID);

				}
				// Remove userID from public
				else {
					getPublicGroups().get(groupID).getUserList().remove(userID);
					System.out.println("[Database] User " + userID + " removed from public group " + groupID);
				}
				// Remove group if it becomes empty after removing the user.
				if (groupCount == 0) {
					removeGroupFromGroupList(group);
					System.out.println("[Database] Group " + group.getGroupID() + " empty and removed");
				}
			}
			else {
				System.out.println("[Database] User " + userID + " not in group " + groupID);
			}
		}
		else {
			System.out.println("[Database] Group " + groupID + " not found");
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
				System.out.println("[Database] User " + userID + " added to group " + groupID);
				// Add userID to private/public groups
				if (group.getPrivacy() ) {
					getPrivateGroups().get(groupID).getUserList().add(userID);
					System.out.println("[Database] User " + userID + " added to private group " + groupID);
				} else {
					getPublicGroups().get(groupID).getUserList().add(userID);
					System.out.println("[Database] User " + userID + " added to public group " + groupID);
				}
			}
			else {
				System.out.println("[Database] User " + userID + " already in group " + groupID);
			}
		}
		else {
			System.out.println("[Database] Group " + groupID + " not found");
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
				System.out.println("[Database] User " + userID + " removed from chat " + chatID);
				// Remove group if it becomes empty after removing the user.
				if (chatCount == 1) {
					removeChatFromChatList(chat);
					System.out.println("[Database] Chat " + chatID + " empty and removed");
				}
			}
			else {
				System.out.println("[Database] User " + userID + " not in chat " + chatID);
			}
		}
		else {
			System.out.println("[Database] Chat " + chatID + " not found");
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
				System.out.println("[Database] User " + userID + " added to chat " + chatID);
			}
			else {
				System.out.println("[Database] User " + userID + " already in chat " + chatID);
			}
		}
		else {
			System.out.println("[Database] Chat " + chatID + " not found");
		}
	}

	// Group methods
	public void addGroupToGroupList(Group group) {
		getGroups().put(group.getGroupID(), group);
		System.out.println("[Database] Group " + group.getGroupID() + " created!");
		if (group.getPrivacy()) {
			getPrivateGroups().put(group.getGroupID(), group);
			System.out.println("[Database] Private Group " + group.getGroupID() + " added");
		} else {
			getPublicGroups().put(group.getGroupID(), group);
			System.out.println("[Database] Public Group " + group.getGroupID() + " added");
		}
	}

	public void removeGroupFromGroupList(Group group) {
		getGroups().remove(group.getGroupID());
		System.out.println("[Database] Group " + group.getGroupID() + " deleted!");
		if (group.getPrivacy()) {
			getPrivateGroups().remove(group.getGroupID(), group);
			System.out.println("[Database] Private Group " + group.getGroupID() + " removed");
		} else {
			getPublicGroups().remove(group.getGroupID(), group);
			System.out.println("[Database] Public Group " + group.getGroupID() + " removed");
		}
	}

	public void addChatToChatList(Chat chat) {
		if (!getChats().containsKey(chat.getChatID())) {
			getChats().put(chat.getChatID(), chat);
			System.out.println("[Database] Chat " + chat.getChatID() + " created!");
		} else {
			System.out.println("[Database] Chat " + chat.getChatID() + " already exists");
		}
	}

	public void removeChatFromChatList(Chat chat) {
		if (getChats().containsKey(chat.getChatID())) {
			getChats().remove(chat.getChatID(), chat);
			System.out.println("[Database] Chat " + chat.getChatID() + " deleted!");
		} else {
			System.out.println("[Database] Chat " + chat.getChatID() + " not found");
		}
	}
}