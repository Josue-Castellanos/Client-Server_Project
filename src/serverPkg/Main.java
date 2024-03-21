package serverPkg;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {
	private static final int PORT = 1234;

	public static void main(String[] args) throws IOException {
		// server is listening on port 1234
		ServerSocket serverListener = new ServerSocket(PORT);

		System.out.println("[SERVER ONLINE] Waiting for clients connection...");
		Server server = new Server(serverListener);
		server.startServer();
	}

}
