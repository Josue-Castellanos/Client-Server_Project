package clientPkg;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;


import packetPkg.*;
import serverPkg.Message;

public class Client {

	private Socket clientSocket;
	private BufferedReader bufferedReader;
	private ObjectOutputStream outputStream;
	private Packet loginPacket;
	private String username;


	public Client(Socket socket, Packet packet) {
		try {
			this.clientSocket = socket;
			this.outputStream = new ObjectOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
			this.bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			this.username = packet.getUsername();
			this.loginPacket = packet;

		} catch (IOException e) {
			closeChat(clientSocket, bufferedReader, outputStream);
		}
	}

	// Method to send a Packet object to the server
	private void sendPacket(Packet packet) throws IOException {
		outputStream.writeObject(packet);
		outputStream.flush();
	}

	public void sendMessage() {
		try {
			// Send login packet
			sendPacket(loginPacket);

			Scanner scanner = new Scanner(System.in);
			while (clientSocket.isConnected() ) {
				String messageToSend = username + ": " + scanner.nextLine();
				// Construct Message
				Message message = new Message(loginPacket.getUser().getAcctNum(), loginPacket.getChat().getChatID(), messageToSend);
				// Construct Message Packet
				Packet messagePacket = new Packet(PacketType.REQUEST, RequestType.SEND_MESSAGE_CHAT, StatusType.PROGRESS, loginPacket.getUser(), loginPacket.getChat(), message);

				sendPacket(messagePacket);
			}
		}
		catch (IOException e) {
			closeChat(clientSocket, bufferedReader, outputStream);
		}
	}

	public void listenForMessage() {
		// Replaced new Runnable() with lambda ->
		new Thread(() -> {
            String messageFromGroupChat;
            while (clientSocket.isConnected()) {
                try {
                    messageFromGroupChat = bufferedReader.readLine();
                    System.out.println(messageFromGroupChat);
                } catch (IOException e) {
                    closeChat(clientSocket, bufferedReader, outputStream);
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

