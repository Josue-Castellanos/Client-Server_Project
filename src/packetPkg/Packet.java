package packetPkg;

import serverPkg.*;

import java.io.Serializable;
import java.util.ArrayList;

public class Packet implements Serializable{
	protected PacketType type;
	protected UserStatus userstatus;
	protected RequestType request;
	protected StatusType status;
	protected Message message;
	protected ArrayList<Message> msgList;
	protected String location;
	protected int port;
	protected Group group;
	protected Chat chat;
	protected ArrayList<Group>groupList;
	protected ArrayList<Chat>chatList;
	protected User user;
	private String username;
	private String password;
	protected ArrayList<User> userList;
	protected String string;

	//Default Constructor
	public Packet(){
		this(null, null, null, null, null,
				null, null, null,
				null, null, null, null, null, null);
	}
	// Custom Constructor
	public Packet(PacketType newType, RequestType newRequest, StatusType newStatus, Message newMessage,
				  ArrayList<Message> newMsgList, Group newGroup, Chat newChat,
				  User newUser, String newUsername, String newPassword, ArrayList<User> newUserList, String newString, String newLocation, Integer newPort) {
		this.setPacketType(newType);
		this.setRequestType(newRequest);
		this.setStatusType(newStatus);
		this.setMessage(newMessage);
		this.setMsgList(newMsgList);
		this.setGroup(newGroup);
		this.setChat(newChat);
		this.setUser(newUser);
		this.setString(newString);
		this.setUserList(newUserList);
		this.setLocation(newLocation);
		this.setPort(newPort);
	}

	// New Login and Logout Constructor
	public Packet(PacketType packet, RequestType request, StatusType status, Chat newChat, User newUser, String newLocation, Integer newPort){
		this.setPacketType(packet);
		this.setRequestType(request);
		this.setStatusType(status);
		this.setChat(newChat);
		this.setUser(newUser);
		this.setLocation(newLocation);
		this.setPort(newPort);
	}

	//Login and Logout Constructor
	public Packet(PacketType packet, RequestType request, StatusType status, String username, String password, String newLocation, Integer newPort){
		this.setPacketType(packet);
		this.setRequestType(request);
		this.setStatusType(status);
		this.setUsername(username);
		this.setPassword(password);
		this.setLocation(newLocation);
		this.setPort(newPort);
	}

	//SEND_MESSAGE_GROUP Constructor
	public Packet(PacketType packet, RequestType request, User fromUser, Group trgtGroup, Message newMessage) {
		this.setPacketType(packet);
		this.setRequestType(request);
		this.setUser(fromUser);
		this.setGroup(trgtGroup);
		this.setMessage(newMessage);
	}

	//Request BLOCK_LIST, INVITE_LIST, GROUP_LIST, CHAT_LIST Constructor
	public Packet(PacketType packet, RequestType request, User newUser, String list){
		this.setPacketType(packet);
		this.setRequestType(request);
		this.setUser(newUser);
		this.setString(list);
	}

	//RECEIVE_MESSAGE_GROUP and CREATE_GROUP Constructor
	public Packet(PacketType packet, RequestType request, Group trgtGroup) {
		this.setPacketType(packet);
		this.setRequestType(request);
		this.setGroup(trgtGroup);
	}

	//SEND_MESSAGE_CHAT Constructor
	public Packet(PacketType packet, RequestType request, StatusType status, User fromUser, Chat trgtChat, Message newMessage) {
		this.setPacketType(packet);
		this.setRequestType(request);
		this.setStatusType(status);
		this.setUser(fromUser);
		this.setChat(trgtChat);
		this.setMessage(newMessage);
	}

	//RECEIVE_MESSAGE_CHAT and CREATE_CHAT Constructor
	public Packet(PacketType packet, RequestType request, Chat trgtChat) {
		this.setPacketType(packet);
		this.setRequestType(request);
		this.setChat(trgtChat);
	}

	//JOIN_GROUP and LEAVE_GROUP Constructor
	public Packet(PacketType packet, RequestType request,Group trgtGroup, User trgtUser) {
		this.setPacketType(packet);
		this.setRequestType(request);
		this.setGroup(trgtGroup);
		this.setUser(trgtUser);
	}

	//RECEIVE_INVITE_LIST Constructor
//	public Packet(PacketType packet, RequestType request, User trgtUser, ArrayList<Receiver> inviteList) {
//		this.setPacketType(packet);
//		this.setRequestType(request);
//		this.setUser(trgtUser);
//		this.setReceiverList(inviteList);
//	}
	//KICK_USER, REPORT_USER, BLOCK_USER   Constructor
//	public Packet(PacketType packet, RequestType request, User trgtUser, Receiver.Group trgtGroup) {
//		this.setPacketType(packet);
//		this.setRequestType(request);
//		this.setUser(trgtUser);
//		this.setGroup(trgtGroup);
//	}

	/************************* Getters ***************************/

	public PacketType getPacketType() {
		return type;
	}

	public RequestType getRequestType() {
		return request;
	}

	public StatusType getStatusType() {
		return status;
	}

	// Get Message object containing the string
	public Message getMessageObject() {
		return message;
	}

	// Get Message List containing Message Objects
	public ArrayList<Message> getMsgList(){
		return msgList;
	}

	public Group getGroup(){
		return this.group;
	}

	public Chat getChat(){
		return this.chat;
	}


	public ArrayList<Group> getGroupList(){
		return groupList;
	}

	public ArrayList<Chat> getChatList(){
		return chatList;
	}

	public ArrayList<User> getUserList(){
		return userList;
	}

	public User getUser(){
		return user;
	}
	public String getUsername() {
		return this.username;
	}

	public String getPassword() {
		return this.password;
	}


	// Get Inet address (location)
	public String getLocation() {
		return this.location;
	}

	public Integer getPort() {
		return this.port;
	}


	public String getString(){
		return string;
	}

	/************************** Setters ****************************/
	public void setPacketType(PacketType type) {
		this.type = type;
	}

	public void setRequestType(RequestType request) {
		this.request = request;
	}

	public void setStatusType(StatusType status) {
		this.status = status;
	}
	public void setUserStatus(UserStatus userstatus) {
		this.userstatus = userstatus;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public void setMsgList(ArrayList<Message> newList){
		this.msgList = newList;
	}

	public void setLocation(String newLocation){
		this.location = newLocation;
	}

	public void setGroup(Group newGroup){
		this.group = newGroup;
	}

	public void setChat(Chat newChat){
		this.chat = newChat;
	}


	public void setGroupList(ArrayList<Group> newList){
		this.groupList = newList;
	}

	public void setChatList(ArrayList<Chat> newList){
		this.chatList = newList;
	}

	public void setUser(User newUser){
		this.user = newUser;
	}
	public void setUsername(String newUsername){
		this.username = newUsername;
	}

	public void setPassword(String newPassword){
		this.password = newPassword;
	}


	public void setUserList(ArrayList<User> newUser){
		this.userList = newUser;
	}

	public void setString(String newString){
		this.string = newString;
	}

	public void setPort(Integer newInteger){
		this.port = newInteger;
	}

}
