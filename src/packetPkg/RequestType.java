package packetPkg;

public enum RequestType {
	
//	USER_DATA,				//Send User's Data
//	GROUP_LIST,				//Send a List of Groups
//	CHAT_LIST,				//Send a List of Chats
//	INVITE_LIST,			//Send a List of Group Invitations
//	RECEIVE_INVITE,
//	SEND_INVITE,
	SEND_MESSAGE_GROUP,    //Send Message Request
	RECEIVE_MESSAGE_GROUP, //Receive Message Request
	SEND_MESSAGE_CHAT,    //Send Message Request
	RECEIVE_MESSAGE_CHAT, //Receive Message Request
	CREATE_GROUP,	 //Create Group Request
	CREATE_CHAT,	 //Create Chat Request
	JOIN_GROUP,		 //Join Group Request
	LEAVE_GROUP,	 //Leave Group Request
//	KICK_USER,		 //Kick User Request
//	REPORT_USER,	 //Report User Request
//	BLOCK_USER,		 //Block User Request
	NULL
}
