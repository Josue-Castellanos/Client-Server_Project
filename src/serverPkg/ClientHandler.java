package serverPkg;

import java.io.*;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.io.OutputStream;
import packetPkg.Packet;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {

	public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
	private Socket clientSocket;
	private BufferedWriter bufferedWriter;
	private BufferedReader bufferedReader;
	private ObjectInputStream inputStream;
	private Packet packet;
	private PacketHandler packetHandler = new PacketHandler();
    private String clientUsername;
   
    
	// Constructor
	public ClientHandler(Socket socket) {
		
		try {
			this.clientSocket = socket;
			this.inputStream = new ObjectInputStream(clientSocket.getInputStream());
			this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
			this.bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			// Reads Login Packet
			this.packet = (Packet) inputStream.readObject();


			while (packetHandler.receivePacket(packet) == "INVALID") {

			}


			this.clientUsername = packet.getUser().username;


			clientHandlers.add(this);
			// send this code to packet receiver to add to list once chat is created
			// packet.getGroup().userList.add(clientUsername);
			broadcastMessage("SERVER: " + clientUsername + " has entered the chat!");	
		}
		catch (IOException | ClassNotFoundException e) {
			closeChat(clientSocket, inputStream, bufferedWriter, bufferedReader);
		}
	}

	@Override
	public void run() {
		String messageFromPacket;
		
		while (clientSocket.isConnected()) {
			try {
				packet = ObjectInputStream.readObject();
				messageFromPacket = packet.getMessage().message;
				messageFromPacket = inputStream.readLine();
				broadcastMessage(messageFromPacket);
			}
			catch (IOException e) {
				closeChat(clientSocket, inputStream, bufferedWriter, bufferedReader);
				break;
			}
		}
	}

	private void broadcastMessage(String messageToSend) {
		for(ClientHandler clientHandler: clientHandlers) {
			try {
				// If the clients name in the clientHandlers list does not match the
				// username broadcast the message to that client
				if(!clientHandler.clientUsername.equals(clientUsername)) {
					clientHandler.bufferedWriter.write(messageToSend);
					clientHandler.bufferedWriter.newLine();
					clientHandler.bufferedWriter.flush();
				}
			}
			catch(IOException e) {
				closeChat(clientSocket, inputStream, bufferedWriter, bufferedReader);
			}
		}
	}
	
	public void removeClientHandler() {
		clientHandlers.remove(this);
		broadcastMessage("SERVER: " + clientUsername + " has left the chat!");
	}
	
	public void closeChat(Socket socket, ObjectInputStream inputStream, BufferedWriter bufferedWriter, BufferedReader bufferedReader) {
		try {
			removeClientHandler();

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