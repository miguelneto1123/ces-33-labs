import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServer {
	private String _ip = "localhost";
	private int _port = 22222;	
	private ServerSocket _serverSocket;
	
	
	public GameServer(String ip, int port) {
		_ip = ip;
		_port = port;
		
	}
	
	public GameServer () {
		
	}

	public void initializeServer() {
		try {
			_serverSocket = new ServerSocket(_port, 8, InetAddress.getByName(_ip));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void listenForServerRequest(GameClient gameClient) {
		Socket socket = null;
		try {
			socket = _serverSocket.accept();
			gameClient.setDataOutputStream(new DataOutputStream(socket.getOutputStream()));
			gameClient.setDataInputStream(new DataInputStream(socket.getInputStream()));
			gameClient.setAccepted(true);
			System.out.println("CLIENT HAS REQUESTED TO JOIN, AND WE HAVE ACCEPTED");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	

}
