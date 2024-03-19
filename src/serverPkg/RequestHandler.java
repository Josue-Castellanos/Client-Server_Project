package serverPkg;

import packetPkg.*;

import java.util.ArrayList;

public class RequestHandler {
    private Database db;
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
                //processedPacket = handleReceivedInviteListPacket(packet);
                break;
            case KICK_USER:
                //processedPacket = handleKickUserPacket(packet);
                break;
            case REPORT_USER:
                //processedPacket = handleReportUserPacket(packet);
                break;
            case BLOCK_USER:
                //processedPacket = handleBlockUserPacket(packet);
                break;
            case SEND_INVITE:
                //processedPacket = handleBlockListRequestPacket(packet);
                break;
            case REQUEST_INVITE_LIST:
                //processedPacket = handleInviteListRequestPacket(packet);
                break;
            case REQUEST_GROUP_LIST:
                //processedPacket = handleGroupListRequestPacket(packet);
                break;
            case REQUEST_CHAT_LIST:
                //processedPacket = handleChatListRequestPacket(packet);
                break;
            default:
                processedPacket.setStatusType(StatusType.FAIL);
                break;
        }
        return processedPacket;
    }

    /* ==============HANDLING METHODS ================== */
    private Packet handleLoginPacket(Packet packet) {
        // If authentication was successful:
        // User will have User Status ONLINE

        if (authenticateUser(packet.getUser())) {
            // Set Packet Status to SUCCESSFUL request
            Packet processedPacket = new Packet();
            processedPacket.setStatusType(StatusType.SUCCESS);
        }
        // Packet Status FAIL when login authentication failed.
        else {
            packet.setStatusType(StatusType.FAIL);
        }
        return packet;
    }

    private Packet handleLogoutPacket(Packet packet) {
        // Logic to handle logout packet
        boolean success = logout(packet.getUser());
        if (success) {
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
   public boolean authenticateUser(User user) {
       for (int i = 0; i < db.getGeneralUsers().size(); i++) {
           if (db.getGeneralUsers().get(i).getUsername().equals(user.getUsername()) &&
                   db.getGeneralUsers().get(i).getPassword().equals(user.getPassword())) {
               db.addConnectedUserToList(user);
               user.setUserStatus(UserStatus.ONLINE);
               return true;
           }
       }
       user.setUserStatus(UserStatus.PROGRESS);
       return false;
   }
    public boolean logout(User user) {
        for (int i = 0; i < db.getConnectedUsers().size(); i++) {
            if (db.getConnectedUsers().get(i).getAcctNum().equals(user.getAcctNum())) {
                db.getConnectedUsers().remove(i);
            }
        }
        return true;
    }

    public void writeToGroup(Group group, Message message) {
        group.getMessageList().add(message);

        // Construct Packet for successful database update
    }

    public Packet readGroup(Group group) {
        ArrayList<Message> list = group.getMessageList();

        // Construct Packet to send back list to print
        return new Packet();
    }
    public void writeToChat(Chat chat, Message message) {
        if (db.getChats().containsKey(chat.getChatID())) {
            chat.msgList.add(message);
        }
//        else { //first message
//                //Receiver.Chat newChat = new Receiver.Chat(ArrayList<User> userList); // needs work
//                //addChat(newChat);
//        }
   }

    public Packet readChat(Chat chat) {
        return new Packet(PacketType.REQUEST, RequestType.RECEIVE_MESSAGE_CHAT, chat);
    }

    public void resetPacket(Packet packet) {
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


