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
	private BufferedReader bufferedReader;
	private ObjectInputStream inputStream;
    private final RequestHandler requestHandler = new RequestHandler();
    private String clientUsername;
	private Packet packet;


	// Constructor
	public ClientHandler(Socket socket) {
		
		try {
			this.clientSocket = socket;
			this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
			this.bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			this.inputStream = new ObjectInputStream(clientSocket.getInputStream());
            this.packet = (Packet) inputStream.readObject();
			this.clientUsername = packet.getUser().getUsername();
			clientHandlers.add(this);
			broadcastMessage("SERVER: " + clientUsername + " has entered the chat!", packet);
		}
		catch (IOException | ClassNotFoundException e) {
			closeChat(clientSocket, inputStream, bufferedWriter, bufferedReader);
		}
	}

	@Override
	public void run() {
		String messageFromClient;
		Packet messagePacket;
		Message message;
		
		while (clientSocket.isConnected()) {
			try {
				messageFromClient = bufferedReader.readLine();
				message = new Message(packet.getUser().getAcctNum(), packet.getChat().getChatID(), messageFromClient);
				messagePacket = new Packet(PacketType.REQUEST, RequestType.SEND_MESSAGE_CHAT,
						packet.getUser(), packet.getChat(), message);
				broadcastMessage(messageFromClient, messagePacket);
			}
			catch (IOException e) {
				closeChat(clientSocket, inputStream, bufferedWriter, bufferedReader);
				break;
			}
		}
	}

	// Send message to chat
	private void broadcastMessage(String messageToSend, Packet sendPacket) {
		Packet packetProcessed = requestHandler.receivePacket(new Packet(PacketType.REQUEST, RequestType.SEND_MESSAGE_CHAT,
				sendPacket.getUser(), sendPacket.getChat(), sendPacket.getMessageObject()));

		if(Objects.equals(packetProcessed.getStatusType().toString(), "SUCCESS")) {
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
					closeChat(clientSocket, inputStream, bufferedWriter, bufferedReader);
				}
			}
		}

	}
	
	public void removeClientHandler() {
		Packet logoutPacket = requestHandler.receivePacket(new Packet(PacketType.REQUEST, RequestType.LOGOUT, StatusType.PROGRESS, UserStatus.ONLINE,
				packet.getChat(), packet.getUser(), packet.getLocation(), packet.getPort()));
		if(Objects.equals(logoutPacket.getUser().getUserStatus().toString(), "OFFLINE")) {
			clientHandlers.remove(this);
			broadcastMessage("SERVER: " + clientUsername + " has left the chat!", logoutPacket);
		}
	}
	
	public void closeChat(Socket socket, ObjectInputStream inputStream, BufferedWriter bufferedWriter, BufferedReader bufferedReader) {
		removeClientHandler();
		try {
			if(inputStream != null) {
				inputStream.close();
			}
			if(bufferedWriter != null) {
				bufferedWriter.close();
			}
			if(bufferedReader != null) {
				bufferedReader.close();
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