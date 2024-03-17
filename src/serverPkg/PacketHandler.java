package serverPkg;

import packetPkg.*;

public class PacketHandler {
    private Database databaseCommunication;
    public void receivePacket(Packet packet) {
        switch (packet.getPacketType()) {
            case LOGIN:
                handleLoginPacket(packet);
                break;
            case LOGOUT:
                handleLogoutPacket(packet);
                break;
            case REQUEST:
                switch (packet.getRequestType()) {
                    case SEND_MESSAGE_GROUP:
                        handleGroupMessagePacket(packet);
                        break;
                    case SEND_MESSAGE_CHAT:
                        handleChatMessagePacket(packet);
                        break;
                    case RECEIVE_MESSAGE_GROUP:
                        handleReceivedGroupMessagePacket(packet);
                        break;
                    case RECEIVE_MESSAGE_CHAT:
                        handleReceivedChatMessagePacket(packet);
                        break;
                    case CREATE_GROUP:
                        handleCreateGroupPacket(packet);
                        break;
                    case CREATE_CHAT:
                        handleCreateChatPacket(packet);
                        break;
                    case JOIN_GROUP:
                        handleJoinGroupPacket(packet);
                        break;
                    case LEAVE_GROUP:
                        handleLeaveGroupPacket(packet);
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
                        //handleUnknownRequest
                        break;
                }
            default:
                //handleUnknownPacket
                break;
        }
    }

    /* ==============HANDLING METHODS ================== */
    private boolean handleLoginPacket(Packet packet) {
        // Logic to handle login packet
        if (authenticateUser(packet.getUser())) {
            createSession();
        }
        else {
            return false;
        }
        return true;

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
   public boolean authenticateUser(User user) {
       User loadedUser = verify(user);
       if(loadedUser.getUsername().equals("")) {
           Packet packet = new Packet(PacketType.LOGIN, RequestType.NULL , loadedUser);
           packet.setStatusType(StatusType.FAIL);
           return packet;
       }
       Packet packet = new Packet(PacketType.LOGIN, RequestType.NULL , loadedUser);
       packet.setStatusType(StatusType.SUCCESS);
       return packet;
   }

    public User verify(User user) {
        //so far only works with general users
        for (int i = 0; i < userList.size(); i++) {
            if (generalUser.get(i).getUsername().equals(user.getUsername()) &&
                    generalUser.get(i).getPassword().equals(user.getPassword())) {
                addConnectedUser(user);
                return generalUser.get(i);
            }
        }
        User failedUser = new User();
        return failedUser;
    }

    private boolean authenticateUser(String username, String password) {
        // Simulated authentication logic (e.g., check against a database)
        User user = packet.getUser(); // Get the user from the packet
        if (user != null) {
            // Perform authentication logic (e.g., check username/password against a database)
            boolean isAuthenticated = backEnd.authenticateUser(user.getUsername(), user.getPassword());
            if (isAuthenticated) {
                // Successful login
                // Update user status, create session, etc.
                user.setStatus(true);   // true = ONLINE
                createSession(user);
                System.out.println("User " + user.getUsername() + " logged in successfully.");
            } else {
                // Failed login
                System.out.println("Login failed for user " + user.getUsername() + ". Invalid credentials.");
            }
        } else {
            System.out.println("Invalid login packet. User data is missing.");
        }
        return true;
    }
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


