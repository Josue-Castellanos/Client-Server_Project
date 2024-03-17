//package serverPkg;
//
//import java.util.Date;
//import java.util.ArrayList;
//import java.util.*;
//
////Implemented by Nabil Alhamal
//public class PacketUnwrapper {
//	//Fields for the Receiver Class
//
//
//	/******************* GETTERS ***********************/
//
//    public ArrayList<Message> getMsgList() {
//		return msgList;
//	}
//
//	public Message getMessage(int index) {
//		return msgList.get(index);
//	}
//
//	public ArrayList<String> getUserList() {
//		return userList;
//	}
//
//	public String getUserID(int index) {
//		return userList.get(index);
//	}
//
//	public String getCreator() {
//		return createdByUser;
//	}
//
//	public String getDate() {
//		return created.toString();
//	}
//
//	/******************* MUTATORS ***********************/
//
//
//	public void addMessage(Message newMessage) {
//		//Needs implementation to add to the end of the list. May want to change this to a sorted list
//		msgList.add(newMessage);
//	}
//
//	//Adds a user to the list
//	public void addUser(String userID) {
//		int index = findHelper(userID, userList);
//		if (index == -1) {
//			userList.add(userID);
//			Collections.sort(userList);
//		}
//		else {
//			System.out.println("\nUser " + userID + " is already a member. \n");
//		}
//	}
//
//	//Removes a user from the list
//	public void removeUser(String userID) {
//		int index = findHelper(userID, userList);
//		if (index != -1) {
//			userList.remove(userID);
//		}
//		else {
//			System.out.println("\nUser " + userID + " is already a member. \n");
//		}
//	}
//}
