package clientPkg;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;


import packetPkg.*;
import serverPkg.Message;

public class Client {

	private Socket clientSocket;
	private BufferedReader bufferedReader;
	private BufferedWriter bufferedWriter;
	private ObjectOutputStream outputStream;
	private Packet packet;
	private String username;

	public Client(Socket socket, Packet packet) {
		try {
			this.clientSocket = socket;
			this.outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
			this.bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			this.username = packet.getUser().getUsername();
			this.packet = packet;

		} catch (IOException e) {
			closeChat(clientSocket, bufferedReader, outputStream);
		}
	}

	public void sendMessage() {
		try {
			// Send Login Packet from Main
			outputStream.writeObject(packet);
			outputStream.flush();

			// if Login is successful....
			Scanner scanner = new Scanner(System.in);
			while (clientSocket.isConnected() ) {
				String messageToSend = scanner.nextLine();
				// Construct Message
				Message message = new Message(packet.getUser().getAcctNum(), packet.getChat().getChatID(), messageToSend);
				// Construct Message Packet
				Packet messagePacket = new Packet(PacketType.REQUEST, RequestType.SEND_MESSAGE_CHAT, packet.getUser(), packet.getChat(), message);

				outputStream.writeObject(messagePacket);
				outputStream.flush();

				bufferedWriter.write(username + "; " + messageToSend);
				bufferedWriter.newLine();
				bufferedWriter.flush();
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

