package packetPkg;

import serverPkg.*;

import java.io.Serializable;
import java.util.ArrayList;

public class Packet implements Serializable{
	//private static final long serialVersionUID = 1L;	//Warning was given by IDE. This line was added to satisfy the warning condition.
	protected PacketType type;
	protected RequestType request;
	protected StatusType status;
	protected Message message;
	protected ArrayList<Message> msgList;
	protected String location;
	protected int port;
	protected PacketReceiver group;
	protected PacketReceiver chat;
	protected ArrayList<PacketReceiver> receiverList;
	protected ArrayList<PacketReceiver>groupList;
	protected ArrayList<PacketReceiver>chatList;
	protected User user;
	protected ArrayList<User> userList;
	protected String string;

	//Default Constructor
	public Packet(){
		this(null, null, null, null, null,
				null, null, null, null,
				null, null);
	}
	// Custom Constructor
	public Packet(PacketType newType, RequestType newRequest, StatusType newStatus, Message newMessage,
				  ArrayList<Message> newMsgList, /*Group newGroup, Chat newChat, */
				  ArrayList<PacketReceiver> newReceiverList,
				  User newUser, ArrayList<User> newUserList, String newString, String newLocation, Integer newPort) {
		this.setPacketType(newType);
		this.setRequestType(newRequest);
		this.setStatusType(newStatus);
		this.setMessage(newMessage);
		this.setMsgList(newMsgList);
//		this.setGroup(newGroup);
//		this.setChat(newChat);
		this.setReceiverList(newReceiverList);
		this.setUser(newUser);
		this.setString(newString);
		this.setUserList(newUserList);
		this.setLocation(newLocation);
		this.setPort(newPort);

	}

	//Login and Logout Constructor
	public Packet(PacketType packet, User newUser){
		this.setPacketType(packet);
		//this.setRequestType(request);
		this.setUser(newUser);
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
	public Packet(PacketType packet, RequestType request, User fromUser, Chat trgtChat, Message newMessage) {
		this.setPacketType(packet);
		this.setRequestType(request);
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
	public Message getMessage() {
		return message;
	}

	// Get Message List containing Message Objects
	public ArrayList<Message> getMsgList(){
		return msgList;
	}

	// Get Inet address (location)
	public PacketReceiver getLocation(){
		return location;
	}

	// Get
	public Group getGroup(){
		return this.Group;
	}

	public Chat getChat(){
		return this.Chat;
	}

	public ArrayList<PacketReceiver> getReceiverList(){
		return receiverList;
	}

	public ArrayList<Group> getGroupList(){
		return groupList;
	}

	public ArrayList<Chat> getChatList(){
		return chatList;
	}

	public User getUser(){
		return user;
	}

	public String getLocation() {
		return this.location;
	}

	public Integer getPort() {
		return this.port;
	}

	public ArrayList<User> getUserList(){
		return userList;
	}

	public String getLocation(){
		return location;
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
	public void setMessage(Message message) {
		this.message = message;
	}

	public void setMsgList(ArrayList<Message> newList){
		this.msgList = newList;
	}

	public void setReceiver(PacketReceiver newLocation){
		this.location = newLocation;
	}

	public void setGroup(Group newGroup){
		this.group = newGroup;
	}

	public void setChat(Chat newChat){
		this.chat = newChat;
	}

	public void setReceiverList(ArrayList<PacketReceiver> newList){
		this.receiverList =newList;
	}

	public void setGroupList(ArrayList<PacketReceiver.Group> newList){
		this.groupList = newList;
	}

	public void setChatList(ArrayList<PacketReceiver.Chat> newList){
		this.chatList = newList;
	}

	public void setUser(User newUser){
		this.user = newUser;
	}

//	public void setGenUser(User.GeneralUser newUser){
//		this.genUser = newUser;
//	}
//	public void setITUser(User.ITUser newUser){
//		this.ITUser = newUser;
//	}

	public void setUserList(ArrayList<User> newUser){
		this.userList = newUser;
	}

	public void setLocation(String newLocation){
		this.string = newLocation;
	}
	public void setPort(Integer newInteger){
		this.port = newInteger;
	}
	public void setString(String newString){
		this.string = newString;
	}


}
