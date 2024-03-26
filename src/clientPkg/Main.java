package clientPkg;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import packetPkg.Packet;
import packetPkg.PacketType;
import packetPkg.RequestType;
import packetPkg.StatusType;


public class Main {

	private static final int PORT = 1234;
	private static final String LOCATION = "localhost";

	public static void main(String[] args) throws IOException {

		Scanner scanner = new Scanner(System.in);
		// Username Input
		System.out.println("Enter username: ");
		String username = scanner.nextLine();
		// Password Input
		System.out.println("Enter password: ");
		String password = scanner.nextLine();

		// Construct Login Packet
		Packet loginPacket = new Packet(PacketType.REQUEST, RequestType.LOGIN, StatusType.PROGRESS, username, password, LOCATION, PORT);

		// Construct Socket
		Socket socket = new Socket(LOCATION, PORT);
		//Construct Client
		Client client = new Client(socket, loginPacket);
		client.listenForMessage();
		client.sendMessage();
	}
}
