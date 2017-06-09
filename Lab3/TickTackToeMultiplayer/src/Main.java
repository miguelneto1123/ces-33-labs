import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		String ip; int port;
		Scanner scanner = new Scanner(System.in);
		System.out.println("IP: ");
		ip = scanner.nextLine();
		System.out.println("port: ");
		port = scanner.nextInt();
		while (port < 1024 || port > 65535) {
			System.out.println("The port you entered was invalid, please input another port: ");
			port = scanner.nextInt();
		}
		GameServer gameServer = new GameServer(ip, port);
		
		GameClient gameClient = new GameClient(ip, port, gameServer);
		
		if (!gameClient.connect()) {
			gameServer.initializeServer();
			gameClient.setTurn(true);
			gameClient.setCircle(false);
		}
		
		
		
		gameClient.start();
		
	}

}
