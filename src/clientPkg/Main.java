package clientPkg;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import packetPkg.Packet;
import packetPkg.PacketType;
import packetPkg.RequestType;
import serverPkg.User;
import serverPkg.UserStatus;
import serverPkg.UserType;

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
		// Construct New User
		User newUser = new User(username, username, password, UserType.GENERAL, UserStatus.PROGRESS);
		// Construct Login Packet
		Packet loginPacket = new Packet(PacketType.REQUEST, RequestType.LOGIN, newUser);

		Socket socket = new Socket(LOCATION, PORT);

		Client client = new Client(socket, loginPacket);

		client.listenForMessage();
		client.sendPacket();
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

