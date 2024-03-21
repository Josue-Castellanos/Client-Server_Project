package serverPkg;

import java.io.*;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.io.OutputStream;
import packetPkg.Packet;
import packetPkg.PacketType;
import packetPkg.RequestType;
import packetPkg.StatusType;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;

public class ClientHandler implements Runnable {

	public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
	private Socket clientSocket;
    private BufferedWriter bufferedWriter;
	private ObjectInputStream inputStream;
    private final RequestHandler requestHandler = new RequestHandler();
    private String clientUsername;
	private Packet loginPacket;


	// Constructor
	public ClientHandler(Socket socket) {
		try {
			this.clientSocket = socket;
			this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
			this.inputStream = new ObjectInputStream(clientSocket.getInputStream());
			// Read login packet from the client
            this.loginPacket = readPacket();

			// Authenticate user by UserStatus (ONLINE) or Packet StatusType (SUCCESS)

			if (requestHandler.receivePacket(loginPacket).getUser().getUserStatus().equals(UserStatus.ONLINE)) {
				System.out.println("[SERVER] User " + loginPacket.getUser().getAcctNum() + " authenticated!");
			}
			this.clientUsername = loginPacket.getUser().getUsername();

			clientHandlers.add(this);
			broadcastMessage("SERVER: " + clientUsername + " has entered the chat!", loginPacket);
		}
		catch (IOException | ClassNotFoundException e) {
			closeChat(clientSocket, inputStream, bufferedWriter);
		}
	}

	// Method to read a Packet object
	private Packet readPacket() throws IOException, ClassNotFoundException {
		return (Packet) inputStream.readObject();
	}

	// Send message to chat
	private void broadcastMessage(String messageToSend, Packet sendPacket) {
		// Process packet
		//Packet requestProcessed = requestHandler.receivePacket(sendPacket);

		//if(requestProcessed.getStatusType() == StatusType.SUCCESS) {
			for(ClientHandler clientHandler: clientHandlers) {
				try {
					// If the clients name in the clientHandlers list does not match the
					// username broadcast the message to that client
					if (!clientHandler.clientUsername.equals(clientUsername)) {
						clientHandler.bufferedWriter.write(messageToSend);
						clientHandler.bufferedWriter.newLine();
						clientHandler.bufferedWriter.flush();
					}
				} catch (IOException e) {
					closeChat(clientSocket, inputStream, bufferedWriter);
				}
			}
		//}

	}

	@Override
	public void run() {
		String messageFromClient;
		Packet clientPacket;
		// New Packet contains User information
		Packet messagePacket = loginPacket;
		Message newMessage;
		
		while (clientSocket.isConnected()) {
			try {
				// Read Client Packet
				clientPacket = readPacket();
				// Extract String message from Packet
				messageFromClient = clientPacket.getMessageObject().getString();
				// Construct new Message
				newMessage = new Message(loginPacket.getUser().getAcctNum(), loginPacket.getChat().getChatID(), messageFromClient);
				// Set parameters to fulfill send message REQUEST
				messagePacket.setRequestType(RequestType.SEND_MESSAGE_CHAT);
				messagePacket.setMessage(newMessage);

				broadcastMessage(messageFromClient, messagePacket);
			}
			catch (IOException | ClassNotFoundException e) {
				closeChat(clientSocket, inputStream, bufferedWriter);
				break;
			}
		}
	}
	
	public void removeClientHandler() {
		Packet logoutPacket = loginPacket;
		logoutPacket.setRequestType(RequestType.LOGOUT);

		clientHandlers.remove(this);
		broadcastMessage("SERVER: " + clientUsername + " has left the chat!", logoutPacket);
	}
	
	public void closeChat(Socket socket, ObjectInputStream inputStream, BufferedWriter bufferedWriter) {
		removeClientHandler();
		try {
			if(inputStream != null) {
				inputStream.close();
			}
			if(bufferedWriter != null) {
				bufferedWriter.close();
			}
			if(socket != null) {
				socket.close();

			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
}