import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class GameClient implements Runnable {
	private GameEngine _gameEngine;
	private GUI _clientGUI;
	private GameServer _gameServer;
	
	
	private String _ip;
	private int _port;
	private Socket _socket;
	private DataOutputStream _dos;
	private DataInputStream _dis;
	private boolean _accepted = false;
	private boolean _yourTurn = false;
	private boolean _circle = true;
	private int _gameClientErrors = 0;
	private boolean _unableToCommunicateWithOpponent = false;
	private String[] _board = new String[9];
	public boolean _enemyWon = false;
	public boolean _tie = false;
	public boolean _won = false;
	
	private Thread _thread;
	
	public GameClient(String ip, int port, GameServer gameServer) {
		_ip = ip;
		_port = port;
		
		_gameEngine = new GameEngine();
		
		_gameServer = gameServer;
		_clientGUI = new GUI(this);
		_clientGUI.setFrame();
	}

	public boolean connect() {
		try {
			_socket = new Socket(_ip, _port);
			_dos = new DataOutputStream(_socket.getOutputStream());
			_dis = new DataInputStream(_socket.getInputStream());
			_accepted = true;
		} catch (IOException e) {
			System.out.println("Unable to connect to the address: " + _ip + ":" + _port + " | Starting a server");
			return false;
		}
		System.out.println("Successfully connected to the server.");
		return true;
	}

	public boolean isAccepted() {
		return _accepted;
	}

	

	public void setCircle(boolean b) {
		_circle = b;
		
	}

	public void start() {
		_thread = new Thread(this, "GameClient");
		_thread.start();
		
	}

	@Override
	public void run() {
		while (true) {
			// Aqui demonstra que mesmo com atraso nao ha condicao de disputa
			/*try {
				Thread.sleep(15000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			_clientGUI.tick(this);
			_clientGUI.getPainter().repaint();

			if (!_circle && !_accepted) {
				_gameServer.listenForServerRequest(this);
			}
		}
	}

	public GUI getGUI() {
		// TODO Auto-generated method stub
		return _clientGUI;
	}

	public int getErrors() {
		return _gameClientErrors;
	}

	public void setCommunicationState(boolean b) {
		_unableToCommunicateWithOpponent = true;
		
	}
	
	public boolean isUnnableToCommunicateWithOpponent() {
		return _unableToCommunicateWithOpponent;
	}

	public boolean isYourTurn() {
		return _yourTurn;
	}
	
	public void setTurn(boolean b) {
		_yourTurn = b;
		
	}

	public boolean isCircle() {
		// TODO Auto-generated method stub
		return _circle;
	}
	
	public DataOutputStream getOutputStream() {
		return _dos;
	}
	
	public DataInputStream getInputStream() {
		return _dis;
	}
	
	public String[] getBoard() {
		return _board;
	}
	public void setBoard(int position, String elem) {
		_board[position] = elem;
	}

	public void addErrors() {
		_gameClientErrors++;
		
	}

	public void setEnemyWon(boolean b) {
		_enemyWon = b;
		
	}

	public void setTie(boolean b) {
		_tie = b;
		
	}
	
	public boolean hasTie() {
		return _tie;
	}

	public boolean hasEnemyWon() {
		return _enemyWon;
	}
	
	public boolean hasWon() {
		return _won;
	}
	
	public void setWon(boolean b) {
		_won = b;
	}

	public GameEngine getGameEngine() {
		return _gameEngine;
	}

	public void setDataOutputStream(DataOutputStream dataOutputStream) {
		_dos = dataOutputStream;
		
	}
	
	public void setDataInputStream (DataInputStream dataInputStream) {
		_dis = dataInputStream;
	}

	public void setAccepted(boolean b) {
		_accepted = true;
		
	}
	
	
	

	

}
