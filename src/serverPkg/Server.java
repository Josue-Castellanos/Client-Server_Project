package serverPkg;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;



public class Server {
	
	private final ServerSocket serverSocket;

	// Constructor
	public Server(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}
	
	public void startServer() {
		// try to connect to a client
		try {
			// Listen for connections (clients to connect) on port 1234
			while (!serverSocket.isClosed()) {
				// Will be closed in the ClientHandler
				Socket client = serverSocket.accept();
				System.out.println("[SERVER] Client " + client.getInetAddress().getHostAddress() + " connected waiting for authentication...");
				// create a new thread object
				ClientHandler clientHandler = new ClientHandler(client);
				// This thread will handle the client separately
				Thread thread = new Thread(clientHandler);
				// The start method begins execution of a thread.
				// When you call start() the run method is called.
				// The operating system schedules the threads.
				thread.start();
			}
		}
		catch (IOException e) {
			closeServerSocket();
		}
	}

	// Close the server socket gracefully
	public void closeServerSocket() {
		if (serverSocket != null && !serverSocket.isClosed()) {
			try {
				serverSocket.close();
				System.out.println("[SERVER] Client has disconnected!");
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
		


