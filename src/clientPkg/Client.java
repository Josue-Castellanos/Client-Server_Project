package clientPkg;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;


import packetPkg.*;
//import serverPkg.*;

public class Client {

	// driver code
	private Socket clientSocket;
	private BufferedReader bufferedReader;
	private ObjectOutputStream outputStream;
	private Packet packet;
	private String username;

	public Client(Socket socket, String username, Packet packet) {
		try {
			this.clientSocket = socket;
			this.outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
			this.bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			this.username = username;
			this.packet = packet;

		} catch (IOException e) {
			closeChat(clientSocket, bufferedReader, outputStream);
		}
	}

	public void sendPacket() {
		try {
			// Send Login Packet from Main
			outputStream.writeObject(packet);
			outputStream.flush();

			Scanner scanner = new Scanner(System.in);
			while (clientSocket.isConnected()) {
				String messageToSend = scanner.nextLine();

				// Construct Packet


				outputStream.writeObject(username + ": " + messageToSend);
				// outputStream.newLine();
				outputStream.flush();
			}
		}
		catch (IOException e) {
			closeChat(clientSocket, bufferedReader, outputStream);
		}
	}


	public void listenForMessage() {
		// Replaced new Runnable() with lambda ->
		new Thread(new Runnable() {
			@Override
			public void run() {
				String messageFromGroupChat;
				while (clientSocket.isConnected()) {
					try {
						messageFromGroupChat = bufferedReader.readLine();
						System.out.println(messageFromGroupChat);
					} catch (IOException e) {
						closeChat(clientSocket, bufferedReader, outputStream);
					}
				}
			}
		}).start();
	}

	public void closeChat(Socket socket, BufferedReader bufferedReader, ObjectOutputStream outputStream) {
		try {
			if (bufferedReader != null) {
				bufferedReader.close();
			}
			if (outputStream != null) {
				outputStream.close();
			}
			if (socket != null) {
				socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}


//			
//			System.out.println("Connected to " + "localhost" + ":" + 1234);
//			try {
//				// Output stream socket.
//		        OutputStream outputStream = null;
//	
//		        // Create object output stream from the output stream to send an object through it
//		        ObjectOutputStream objectOutputStream;
//		        
//		        // get the input stream from the connected socket
//		        InputStream inputStream = null;
//	
//		        // create a ObjectInputStream so we can read data from it.
//		        ObjectInputStream objectInputStream;
//		        
//		        // object of scanner class
//				Scanner sc = new Scanner(System.in);
//				String line = null;
//				
//				//Login process start
//				User currentUser = new User();	//Build new object of type User
//				Packet login = new Packet(PacketType.LOGIN, RequestType.NULL, currentUser);
//
//		public static void main(String[] args)
//		{
//			// establish a connection by providing host and port
//			// number
//			try (Socket socket = new Socket("localhost", 1234)) {
//				
//				System.out.println("Connected to " + "localhost" + ":" + 1234);
//				try {
//					// Output stream socket.
//			        OutputStream outputStream = null;
//		
//			        // Create object output stream from the output stream to send an object through it
//			        ObjectOutputStream objectOutputStream;
//			        
//			        // get the input stream from the connected socket
//			        InputStream inputStream = null;
//		
//			        // create a ObjectInputStream so we can read data from it.
//			        ObjectInputStream objectInputStream;
//			        
//			        // object of scanner class
//					Scanner sc = new Scanner(System.in);
//					String line = null;
//					
//					//Login process start
//					User currentUser = new User();	//Build new object of type User
//					Packet login = new Packet(PacketType.LOGIN, RequestType.NULL, currentUser);
//					
//			/*********************	Login Loop	********************************/
//					while(login.getStatusType() != StatusType.SUCCESS ) {
//						//GUI - Replace with something that can connect to the GUI
//				        System.out.println("\nPlease enter your username: ");			     
//						line = sc.nextLine();	// reading text from user						
//						
//						currentUser.setUsername(line);	//set username in object of type User
//						
//						//GUI - Replace with something that can connect to the GUI
//				        System.out.println("\nPlease enter your password: ");			     
//						line = sc.nextLine();	// reading text from user		
//						
//						currentUser.setPassword(line);						
//						login.setUser(currentUser);			        
//				        
//				        outputStream = socket.getOutputStream();
//				        objectOutputStream = new ObjectOutputStream(outputStream);
//				        objectOutputStream.writeObject(login);
//				        
//				        inputStream = socket.getInputStream();
//				        objectInputStream = new ObjectInputStream(inputStream);
//				        login = (Packet) objectInputStream.readObject();
//				        
//				        //GUI - Replace with message in GUI
//				        System.out.println("\nAttempting login...");
//				        
//				        if(login.getStatusType() == StatusType.SUCCESS) {
//				        	System.out.println("Login successful");	//GUI - Message indicating successful login	
//				        }
//				        else {
//				        	//GUI - Message indicating failure, and restart.
//				        }
//					}//Exit the Login Loop
//			                	
//		/***************	Enter the Functional Branch	*************************/
//					Packet logout = new Packet(PacketType.LOGOUT, RequestType.NULL, currentUser);
//					//1. Load User Data from Server
//					//Create the local client objects used for the session. Will get periodically updated by the server.
//					currentUser = new User(login.getUser());
//
//					ArrayList<serverPkg.Receiver.Group> clientGroups = new ArrayList<serverPkg.Receiver.Group>();
//					ArrayList<serverPkg.Receiver.Chat> clientChats = new ArrayList<serverPkg.Receiver.Chat>();
//					ArrayList<serverPkg.Receiver.Group> inviteGroups = new ArrayList<serverPkg.Receiver.Group>();
//					//ArrayList<serverPkg.Receiver.Group> publicGroups = new ArrayList<serverPkg.Receiver.Group>();
//					
//					//For Loop Request for Data from Server. Start by getting size of lists.
//					ArrayList<String> inviteList = currentUser.getInviteList();
//					int sizeInviteList = inviteList.size();
//					
//					ArrayList<String> groupList = currentUser.getGroupList();
//					int sizeGroupList = groupList.size();
//					
//					ArrayList<String> chatList = currentUser.getChatList();
//					int sizeChatList = chatList.size();
//					Packet packetData;
//					while(logout.getStatusType() != StatusType.SUCCESS) {					
//						
//						//Loop to get InviteList
//						for(int i = 0; i < sizeInviteList; i++) {
//							packetData = new Packet(PacketType.REQUEST, RequestType.INVITE_LIST, currentUser, inviteList.get(i));
//							outputStream = socket.getOutputStream();
//					        objectOutputStream = new ObjectOutputStream(outputStream);
//					        objectOutputStream.writeObject(packetData);	//Send packet to server, with inviteList[i] as the string argument.
//					        
//					        inputStream = socket.getInputStream();
//					        objectInputStream = new ObjectInputStream(inputStream);
//					        packetData = (Packet) objectInputStream.readObject();
//					        inviteGroups.add(packetData.getGroup());
//						}	
//						
//						//Loop to get Groups List
//						for(int i = 0; i < sizeGroupList; i++) {
//							packetData = new Packet(PacketType.REQUEST, RequestType.INVITE_LIST, currentUser, groupList.get(i));
//							outputStream = socket.getOutputStream();
//					        objectOutputStream = new ObjectOutputStream(outputStream);
//					        objectOutputStream.writeObject(packetData);	//Send packet to server, with inviteList[i] as the string argument.
//					        
//					        inputStream = socket.getInputStream();
//					        objectInputStream = new ObjectInputStream(inputStream);
//					        packetData = (Packet) objectInputStream.readObject();
//					        clientGroups.add(packetData.getGroup());
//						}	
//						
//						//Loop to get Chats List
//						for(int i = 0; i < sizeChatList; i++) {
//							packetData = new Packet(PacketType.REQUEST, RequestType.INVITE_LIST, currentUser, chatList.get(i));
//							outputStream = socket.getOutputStream();
//					        objectOutputStream = new ObjectOutputStream(outputStream);
//					        objectOutputStream.writeObject(packetData);	//Send packet to server, with inviteList[i] as the string argument.
//					        
//					        inputStream = socket.getInputStream();
//					        objectInputStream = new ObjectInputStream(inputStream);
//					        packetData = (Packet) objectInputStream.readObject();
//					        clientChats.add(packetData.getChat());
//						}	
//						
//						//We should now have all our data from the Server to begin.				
//						
//						//2. Navigate from Home Screen
//						Menu menu = Menu.HOME;
//						MenuChat chat = MenuChat.CHATS;
//						MenuGroup group = MenuGroup.GROUPS;
//						MenuInvitation invite = null;
//						
//						//Menu Loop
//						while(menu != Menu.LOGOUT) {
//							switch(menu){
//							case HOME:
//								while(menu == Menu.HOME) {
//									
//								}
//								break;
//							case CHATS:
//								switch(chat) {
//								case NEW_CHAT:
//									break;
//								case SELECT_CHAT:
//									switch(chat) {
//									case READ_CHAT:
//										break;
//									case WRITE_CHAT:
//										break;
//									case BLOCK:
//										break;
//									case REPORT:
//										break;
//									case CHATS:
//										break;
//									case HOME:
//										break;									
//									}
//									break;
//								case HOME:
//								}
//								break;
//							case GROUPS:
//								switch(group) {
//								case SELECT_GROUP:
//									switch(group) {
//									case JOIN_GROUP:
//									case LEAVE_GROUP:
//									case WRITE:
//									case GROUPS:
//									case HOME:
//										break;
//									}
//								case NEW_GROUP:
//								case PUBLIC_GROUPS:
//								case MY_GROUPS:
//								case HOME:
//									break;
//									
//								}
//								break;
//							case INVITE:
//								switch(invite) {
//								
//								}
//								break;
//							case LOGOUT:
//								break;
//							
//						}
//						
//						}
//						
//						//3. 
//						//Packet message = new Packet(PacketType.text, StatusType.undefined, "");
//						
//						//End. Logout
//						objectOutputStream = new ObjectOutputStream(outputStream);
//						objectOutputStream.writeObject(logout);
//						
//						objectInputStream = new ObjectInputStream(inputStream);
//						logout = (Packet) objectInputStream.readObject();
//						if(logout.getStatusType() == StatusType.SUCCESS) {
//							System.out.println("\n\nLogged out successfully.");	//GUI - 
//							break;
//						}
//					}
//
//					// closing the scanner object
//					sc.close();
//					
//					//close the socket.
//					socket.close();
//					
//					//Exit out of application
//					System.exit(0);				
//	        }			
//			catch (IOException | ClassNotFoundException e) {
//				e.printStackTrace();
//			}
//		}
//		catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
