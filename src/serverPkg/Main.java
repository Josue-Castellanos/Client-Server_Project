package serverPkg;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {
	private static final int PORT = 1234;

	public static void main(String[] args) throws IOException {
		//ComSystem comSystem = new ComSystem();

		// server is listening on port 1234
		ServerSocket listener = new ServerSocket(PORT);

		System.out.println("[SERVER ONLINE] Waiting for client connection...");
		Server server = new Server(listener);
		server.startServer();
	}

}
