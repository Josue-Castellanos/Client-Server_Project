package serverPkg;

import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.io.OutputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import packetPkg.Packet;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {


	public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
	private Socket clientSocket;
	private BufferedReader inputStream;
	private BufferedWriter outputStream;
	private ObjectOutputStream outputStream;
	private ObjectInputStream inputStream;
	private PacketReceiver packetReceived;
	private Packet packetSent;
    private String clientUsername;
   
    
	// Constructor
	public ClientHandler(Socket socket) {
		
		try {
			this.clientSocket = socket;
			//this.outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
			//this.inputStream = new ObjectInputStream(clientSocket.getInputStream());
		
		/*============== REMOVE AFTERWARDS ===================*/
			this.outputStream = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
			this.inputStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	    /*=================== ENDING ===================*/

			this.clientUsername = inputStream.readLine();
			// Packet packet = (Packet) inputStream.readObject();
			// clientUsername = packet.getGenUser().username;
			
			clientHandlers.add(this);
			// packet.getGroup().userList.add(clientUsername);
			broadcastMessage("SERVER: " + clientUsername + " has entered the chat!");	
		}
		catch (IOException e) {
			closeChat(clientSocket, inputStream, outputStream);
		}
	}

	@Override
	public void run() {
		String messageFromPacket;
		
		while (clientSocket.isConnected()) {
			try {
				// packet = ObjectInputStream.readObject();
				//messageFromPacket = packet.getMessage().message;
				messageFromPacket = inputStream.readLine();
				broadcastMessage(messageFromPacket);
			}
			catch (IOException e) {
				closeChat(clientSocket, inputStream, outputStream);
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
					clientHandler.outputStream.write(messageToSend);
					clientHandler.outputStream.newLine();
					clientHandler.outputStream.flush();
				}
			}
			catch(IOException e) {
				closeChat(clientSocket, inputStream, outputStream);
			}
		}
	}
	
	public void removeClientHandler() {
		clientHandlers.remove(this);
		broadcastMessage("SERVER: " + clientUsername + " has left the chat!");
	}
	
	public void closeChat(Socket socket, BufferedReader inputStream, BufferedWriter outputStream) {
		try {
			removeClientHandler();

			if(inputStream != null) {
				inputStream.close();
			}
			if(outputStream != null) {
				outputStream.close();
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