import java.io.IOException;

public class GameEngine {
	private final int[][] wins = new int[][] { { 0, 1, 2 }, { 3, 4, 5 }, { 6, 7, 8 }, { 0, 3, 6 }, { 1, 4, 7 }, { 2, 5, 8 }, { 0, 4, 8 }, { 2, 4, 6 } };


	
	public void checkForEnemyWin(GameClient gameClient) {
		for (int i = 0; i < wins.length; i++) {
			String[] spaces = gameClient.getBoard();
			if ((gameClient.isCircle() && spaces[wins[i][0]] == "X" && spaces[wins[i][1]] == "X" && spaces[wins[i][2]] == "X")
					|| (!gameClient.isCircle() && (spaces[wins[i][0]] == "O" && spaces[wins[i][1]] == "O" && spaces[wins[i][2]] == "O"))) {
				gameClient.getGUI().setFirstSpot(wins[i][0]);
				gameClient.getGUI().setSecondSpot(wins[i][2]);
				gameClient.setEnemyWon(true);
			}
		}
	}
	
	public void checkForTie(GameClient gameClient) {
		for (int i = 0; i < gameClient.getBoard().length; i++) {
			if (gameClient.getBoard()[i] == null) {
				return;
			}
		}
		gameClient.setTie(true);
	}

	public void checkForWin(GameClient gameClient) {
		for (int i = 0; i < wins.length; i++) {
			String[] spaces = gameClient.getBoard();
			if ((!gameClient.isCircle() && spaces[wins[i][0]] == "X" && spaces[wins[i][1]] == "X" && spaces[wins[i][2]] == "X")
					|| (gameClient.isCircle() && (spaces[wins[i][0]] == "O" && spaces[wins[i][1]] == "O" && spaces[wins[i][2]] == "O"))) {
				gameClient.getGUI().setFirstSpot(wins[i][0]);
				gameClient.getGUI().setSecondSpot(wins[i][2]);
				gameClient.setWon(true);
			}
		}
		
	}	

}
