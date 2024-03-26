package serverPkg;

import packetPkg.*;
import java.util.ArrayList;

public class RequestHandler {
    private static final Database db = new Database();
    private Packet processedPacket;

    public Packet receivePacket(Packet packet) {
        switch (packet.getRequestType()) {
            case SEND_MESSAGE_GROUP:
                processedPacket = handleGroupMessagePacket(packet);
                break;
            case SEND_MESSAGE_CHAT:
                processedPacket = handleChatMessagePacket(packet);
                break;
            case RECEIVE_MESSAGE_GROUP:
                processedPacket = handleReceivedGroupMessagePacket(packet);
                break;
            case RECEIVE_MESSAGE_CHAT:
                processedPacket = handleReceivedChatMessagePacket(packet);
                break;
            case CREATE_GROUP:
                processedPacket = handleCreateGroupPacket(packet);
                break;
            case CREATE_CHAT:
                processedPacket = handleCreateChatPacket(packet);
                break;
            case JOIN_GROUP:
                processedPacket = handleJoinGroupPacket(packet);
                break;
            case LEAVE_GROUP:
                processedPacket = handleLeaveGroupPacket(packet);
                break;
            case LOGIN:
                processedPacket = handleLoginPacket(packet);
                break;
            case LOGOUT:
                processedPacket = handleLogoutPacket(packet);
                break;
            case RECEIVE_INVITE:
                //processedPacket = handleReceivedInviteListPacket(packet).getStatusType();
                break;
            case KICK_USER:
                //processedPacket = handleKickUserPacket(packet).getStatusType();
                break;
            case REPORT_USER:
                //processedPacket = handleReportUserPacket(packet).getStatusType();
                break;
            case BLOCK_USER:
                //processedPacket = handleBlockUserPacket(packet).getStatusType();
                break;
            case SEND_INVITE:
                //processedPacket = handleBlockListRequestPacket(packet).getStatusType();
                break;
            case REQUEST_INVITE_LIST:
                //processedPacket = handleInviteListRequestPacket(packet).getStatusType();
                break;
            case REQUEST_GROUP_LIST:
                //processedPacket = handleGroupListRequestPacket(packet).getStatusType();
                break;
            case REQUEST_CHAT_LIST:
                //processedPacket = handleChatListRequestPacket(packet).getStatusType();
                break;
            default:
                packet.setStatusType(StatusType.FAIL);
                processedPacket = packet;
                break;
        }
        return processedPacket;
    }

    /* ============== HANDLING METHODS ================== */
    private Packet handleLoginPacket(Packet packet) {
        // If authentication was successful:
        // User will have User Status ONLINE
        User userAuthenticated = authenticateUser(packet.getUsername(), packet.getPassword());
        if (userAuthenticated != null) {
            // Set Packet Status to SUCCESSFUL request
            packet.setUser(userAuthenticated);
            packet.getUser().setUserStatus(UserStatus.ONLINE);
            packet.setUserStatus(UserStatus.ONLINE);
            packet.setStatusType(StatusType.SUCCESS);
        }
        // Packet Status FAIL when login authentication failed.
        else {
            packet.setStatusType(StatusType.FAIL);
        }
        return packet;
    }

    private Packet handleLogoutPacket(Packet packet) {
        // Logic to handle logout packet
        if (logout(packet.getUser())) {
            packet.setStatusType(StatusType.SUCCESS);
        } else {
            packet.setStatusType(StatusType.FAIL);
        }
        return packet;
    }

    private Packet handleGroupMessagePacket(Packet packet) {
        // Logic to handle group message packet
        writeToGroup(packet.getGroup(), packet.getMessageObject());
        packet.setStatusType(StatusType.SUCCESS);
        return packet;
    }

    private Packet handleChatMessagePacket(Packet packet) {
        // Logic to handle chat message packet
        writeToChat(packet.getChat(), packet.getMessageObject());
        packet.setStatusType(StatusType.SUCCESS);
        return packet;
    }

    private Packet handleReceivedGroupMessagePacket(Packet packet) {
        // Logic to handle received group message packet
        return readGroup(packet.getGroup());
    }

    private Packet handleReceivedChatMessagePacket(Packet packet) {
        // Logic to handle received chat message packet
        return readChat(packet.getChat());
    }

    private Packet handleCreateGroupPacket(Packet packet) {
        // Logic to handle create group packet
        db.addGroupToGroupList(packet.getGroup());
        packet.setStatusType(StatusType.SUCCESS);
        return packet;
    }

    private Packet handleCreateChatPacket(Packet packet) {
        // Logic to handle create chat packet
        db.addChatToChatList(packet.getChat());
        packet.setStatusType(StatusType.SUCCESS);
        return packet;
    }

    private Packet handleJoinGroupPacket(Packet packet) {
        // Logic to handle join group packet
        db.addUserToGroup(packet.getUser().getAcctNum(), packet.getGroup().getGroupID());
        packet.setStatusType(StatusType.SUCCESS);
        return packet;
    }

    private Packet handleLeaveGroupPacket(Packet packet) {
        // Logic to handle leave group packet
        db.removeUserFromGroup(packet.getUser().getAcctNum(), packet.getGroup().getGroupID());
        packet.setStatusType(StatusType.SUCCESS);
        return packet;
    }


   /*============== SUPPORTING METHODS ===================*/
   private User authenticateUser(String username, String password) {
       // Checking the HashMap for Users
       for (User existingUser : db.getGeneralUsers().values()) {
           // If a User shares username and password, check if they are online
           if (existingUser.getUsername().equals(username) &&
                   existingUser.getPassword().equals(password)) {
               // If User is not online, add them to connected user list
               if(!db.getConnectedUsers().containsValue(existingUser)) {
                   db.addConnectedUserToList(existingUser);
                   return existingUser;
               }
           }
       }
       return null; // User not found or password incorrect
   }

    private boolean logout(User user) {
        for (User existingUser : db.getConnectedUsers().values()) {
            if (existingUser.getUsername().equals(user.getUsername()) &&
                    existingUser.getPassword().equals(user.getPassword())) {
                db.removeConnectedUserFromList(existingUser);
                return true;
            }
        }
        return false; // User information lost
    }

    private void writeToGroup(Group group, Message message) {
        group.getMessageList().add(message);

        // Construct Packet for successful database update
    }

    private Packet readGroup(Group group) {
        ArrayList<Message> list = group.getMessageList();

        // Construct Packet to send back list to print
        return new Packet();
    }
    private void writeToChat(Chat chat, Message message) {
        if (db.getChats().containsKey(chat.getChatID())) {
            chat.msgList.add(message);
        }
//        else { //first message
//                //Receiver.Chat newChat = new Receiver.Chat(ArrayList<User> userList); // needs work
//                //addChat(newChat);
//        }
   }

    private Packet readChat(Chat chat) {
        return new Packet(PacketType.REQUEST, RequestType.RECEIVE_MESSAGE_CHAT, chat);
    }

    private void resetPacket(Packet packet) {
        // Resetting fields to default values
        packet.setPacketType(null);
        packet.setRequestType(null);
        packet.setStatusType(null);
        packet.setMessage(null);
        packet.setMsgList(null);
        packet.setLocation(null);
        packet.setPort(null);
        packet.setGroup(null);
        packet.setChat(null);
        packet.setGroupList(null);
        packet.setChatList(null);
        packet.setUser(null);
        packet.setUserList(null);
        packet.setString(null);
    }
}


