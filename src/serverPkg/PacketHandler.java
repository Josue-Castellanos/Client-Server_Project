package serverPkg;

import packetPkg.*;

import java.util.Objects;

public class PacketHandler {
    private Database db;
    private String status;
    public String receivePacket(Packet packet) {
        if (Objects.requireNonNull(packet.getPacketType()) == PacketType.REQUEST) {
            switch (packet.getRequestType()) {
                case SEND_MESSAGE_GROUP:
                    status = handleGroupMessagePacket(packet).toString();
                    break;
                case SEND_MESSAGE_CHAT:
                    status = handleChatMessagePacket(packet).toString();
                    break;
                case RECEIVE_MESSAGE_GROUP:
                    status = handleReceivedGroupMessagePacket(packet).toString();
                    break;
                case RECEIVE_MESSAGE_CHAT:
                    status = handleReceivedChatMessagePacket(packet).toString();
                    break;
                case CREATE_GROUP:
                    status = handleCreateGroupPacket(packet).toString();
                    break;
                case CREATE_CHAT:
                    status = handleCreateChatPacket(packet).toString();
                    break;
                case JOIN_GROUP:
                    status = handleJoinGroupPacket(packet).toString();
                    break;
                case LEAVE_GROUP:
                    status = handleLeaveGroupPacket(packet).toString();
                    break;
                case LOGIN:
                    status = handleLoginPacket(packet).toString();
                    break;
                case LOGOUT:
                    status = handleLogoutPacket(packet).toString();
                    break;
//                    case RECEIVE_INVITE_LIST:
//                        handleReceivedInviteListPacket(packet);
//                        break;
//                    case KICK_USER:
//                        handleKickUserPacket(packet);
//                        break;
//                    case REPORT_USER:
//                        handleReportUserPacket(packet);
//                        break;
//                    case BLOCK_USER:
//                        handleBlockUserPacket(packet);
//                        break;
//                    case REQUEST_BLOCK_LIST:
//                        handleBlockListRequestPacket(packet);
//                        break;
//                    case REQUEST_INVITE_LIST:
//                        handleInviteListRequestPacket(packet);
//                        break;
//                    case REQUEST_GROUP_LIST:
//                        handleGroupListRequestPacket(packet);
//                        break;
//                    case REQUEST_CHAT_LIST:
//                        handleChatListRequestPacket(packet);
//                        break;
                default:
                    status = StatusType.FAIL.toString();
                    break;
            }
        }
        return status;
    }

    /* ==============HANDLING METHODS ================== */
    private StatusType handleLoginPacket(Packet packet) {

        // If authentication was successful:
        // User will have User Status ONLINE
        if (db.authenticateUser(packet.getUser()).equals(UserStatus.ONLINE)) {
            // Set Packet Status to SUCCESSFUL request
            packet.setStatusType(StatusType.SUCCESS);
        }
        // Packet Status FAIL when login authentication failed.
        else {
            packet.setStatusType(StatusType.FAIL);
        }
        return packet.getStatusType();
    }


    private void handleLogoutPacket(Packet packet) {
        // Logic to handle logout packet


    }

    private void handleGroupMessagePacket(Packet packet) {
        // Logic to handle group message packet
        comSystem.writeToGroup(packet.getGroup(), packet.getMessage());
        packet.setStatusType(StatusType.SUCCESS);
        out.writeObject(packet);
        out.flush();
    }

    private void handleChatMessagePacket(Packet packet) {
        // Logic to handle chat message packet
        String messageReceived = packet.getMessage().ge
        comSystem.writeToChat(packet.getChat(), packet.getMessage());
        packet.setStatusType(StatusType.SUCCESS);
        out.writeObject(packet);
        out.flush();
    }

    private void handleReceivedGroupMessagePacket(Packet packet) {
        // Logic to handle received group message packet
        packet = comSystem.readGroup(packet.getGroup());
        packet.setStatusType(StatusType.SUCCESS);
        out.writeObject(packet);
        out.flush();
    }

    private void handleReceivedChatMessagePacket(Packet packet) {
        // Logic to handle received chat message packet
        packet = comSystem.readChat(packet.getChat());
        packet.setStatusType(StatusType.SUCCESS);
        out.writeObject(packet);
        out.flush();
    }

    private void handleCreateGroupPacket(Packet packet) {
        // Logic to handle create group packet
        comSystem.addGroup(packet.getGroup());
        packet.setStatusType(StatusType.SUCCESS);
        out.writeObject(packet);
        out.flush();
    }

    private void handleCreateChatPacket(Packet packet) {
        // Logic to handle create chat packet
        comSystem.addChat(packet.getChat());
        packet.setStatusType(StatusType.SUCCESS);
        out.writeObject(packet);
        out.flush();
    }

    private void handleJoinGroupPacket(Packet packet) {
        // Logic to handle join group packet
        comSystem.addUserToGroup(packet.getUser());
        packet.setStatusType(StatusType.SUCCESS);
        out.writeObject(packet);
        out.flush();
    }

    private void handleLeaveGroupPacket(Packet packet) {
        // Logic to handle leave group packet
        comSystem.removeUserFromGroup(packet.getString());
        packet.setStatusType(StatusType.SUCCESS);
        out.writeObject(packet);
        out.flush();
    }
   /*============== SUPORTING METHODS ===================*/

    //    private void handleBlockListRequestPacket(Packet packet) {
//        // Logic to handle block list request packet
//    }

//    private void handleInviteListRequestPacket(Packet packet) {
//        // Logic to handle invite list request packet
//    }

    //    private void handleGroupListRequestPacket(Packet packet) {
//        // Logic to handle group list request packet
//    }
//
//    private void handleChatListRequestPacket(Packet packet) {
//        // Logic to handle chat list request packet
//    }
//    private void handleReceivedInviteListPacket(Packet packet) {
//        // Logic to handle received invite list packet
    //            		//comSystem.addUserToGroup(packet.getUser(), packet.getGroup());
//            		packet.setStatusType(StatusType.SUCCESS);
//            		out.writeObject(packet);
//            		out.flush();
//    }
//    private void handleKickUserPacket(Packet packet) {
//        // Logic to handle kick user packet
    //comSystem.removeUserFromGroup(packet.getString());
//            		packet.setStatusType(StatusType.SUCCESS);
//            		out.writeObject(packet);
//            		out.flush();
//    }
    // private void handleSendInvitePacket(Packet packet) {
    // Logic to handle send invite packet
    //            		packet = comSystem.sendInvite(packet.getUser(), packet.getGroup());
//            		packet.setStatusType(StatusType.SUCCESS);
//            		out.writeObject(packet);
//            		out.flush();

//    private void handleReportUserPacket(Packet packet) {
//        // Logic to handle report user packet
//    }
//
//    private void handleBlockUserPacket(Packet packet) {
//        // Logic to handle block user packet
//            		//packet = comSystem.addBlockList(packet.getUser(), packet.getBlocked());
//            		packet.setStatusType(StatusType.SUCCESS);
//            		out.writeObject(packet);
//            		out.flush();
//    }
//
//    private void handleUnknownPacket(Packet packet) {
//        // Logic to handle unknown packet types
//
//    }
}


