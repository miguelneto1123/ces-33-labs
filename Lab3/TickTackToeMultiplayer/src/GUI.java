import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class GUI {
	private final int WIDTH = 506;
	private final int HEIGHT = 527;
	private final int lengthOfSpace = 160;
	private final String waitingString = "Waiting for another player";
	private final String unableToCommunicateWithOpponentString = "Unable to communicate with opponent.";
	private final String wonString = "You won!";
	private final String enemyWonString = "Opponent won!";
	private final String tieString = "Game ended in a tie.";
	
	private BufferedImage board;
	private BufferedImage redX;
	private BufferedImage blueX;
	private BufferedImage redCircle;
	private BufferedImage blueCircle;
	
	private Font font = new Font("Verdana", Font.BOLD, 32);
	private Font smallerFont = new Font("Verdana", Font.BOLD, 20);
	private Font largerFont = new Font("Verdana", Font.BOLD, 50);
	
	private int _firstSpot;
	private int _secondSpot;
	
	private JFrame _frame;
	
	private Painter _painter;
	private GameClient _gameClient;
	
	public GUI(GameClient gameClient) {
		_gameClient = gameClient;
		loadImages();
		_painter = new Painter();
		_painter.setPreferredSize(new Dimension(WIDTH, HEIGHT));
	}

	private void loadImages() {
		try {
			board = ImageIO.read(getClass().getResourceAsStream("res/board.png"));
			redX = ImageIO.read(getClass().getResourceAsStream("res/redX.png"));
			redCircle = ImageIO.read(getClass().getResourceAsStream("res/redCircle.png"));
			blueX = ImageIO.read(getClass().getResourceAsStream("res/blueX.png"));
			blueCircle = ImageIO.read(getClass().getResourceAsStream("res/blueCircle.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setFrame() {
		_frame = new JFrame();
		_frame.setTitle("Tic-Tac-Toe");
		_frame.setContentPane(_painter);
		_frame.setSize(WIDTH, HEIGHT);
		_frame.setLocationRelativeTo(null);
		_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		_frame.setResizable(false);
		_frame.setVisible(true);
		
	}

	public void setFirstSpot(int i) {
		_firstSpot = i;
		
	}
	
	public void setSecondSpot(int i) {
		_secondSpot = i;
	}
	
	private void render(Graphics g) {
		g.drawImage(board, 0, 0, null);
		if (_gameClient.isUnnableToCommunicateWithOpponent()) {
			g.setColor(Color.RED);
			g.setFont(smallerFont);
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			int stringWidth = g2.getFontMetrics().stringWidth(unableToCommunicateWithOpponentString);
			g.drawString(unableToCommunicateWithOpponentString, WIDTH / 2 - stringWidth / 2, HEIGHT / 2);
			return;
		}

		if (_gameClient.isAccepted()) {
			String[] spaces = _gameClient.getBoard();
			for (int i = 0; i < spaces.length; i++) {
				if (spaces[i] != null) {
					if (spaces[i].equals("X")) {
						if (_gameClient.isCircle()) {
							g.drawImage(redX, (i % 3) * lengthOfSpace + 10 * (i % 3), (int) (i / 3) * lengthOfSpace + 10 * (int) (i / 3), null);
						} else {
							g.drawImage(blueX, (i % 3) * lengthOfSpace + 10 * (i % 3), (int) (i / 3) * lengthOfSpace + 10 * (int) (i / 3), null);
						}
					} else if (spaces[i].equals("O")) {
						if (_gameClient.isCircle()) {
							g.drawImage(blueCircle, (i % 3) * lengthOfSpace + 10 * (i % 3), (int) (i / 3) * lengthOfSpace + 10 * (int) (i / 3), null);
						} else {
							g.drawImage(redCircle, (i % 3) * lengthOfSpace + 10 * (i % 3), (int) (i / 3) * lengthOfSpace + 10 * (int) (i / 3), null);
						}
					}
				}
			}
			if (_gameClient.hasWon() || _gameClient.hasEnemyWon()) {
				Graphics2D g2 = (Graphics2D) g;
				g2.setStroke(new BasicStroke(10));
				g.setColor(Color.BLACK);
				g.drawLine(_firstSpot % 3 * lengthOfSpace + 10 * _firstSpot % 3 + lengthOfSpace / 2, (int) (_firstSpot / 3) * lengthOfSpace + 10 * (int) (_firstSpot / 3) + lengthOfSpace / 2, _secondSpot % 3 * lengthOfSpace + 10 * _secondSpot % 3 + lengthOfSpace / 2, (int) (_secondSpot / 3) * lengthOfSpace + 10 * (int) (_secondSpot / 3) + lengthOfSpace / 2);

				g.setColor(Color.RED);
				g.setFont(largerFont);
				if (_gameClient.hasWon()) {
					int stringWidth = g2.getFontMetrics().stringWidth(wonString);
					g.drawString(wonString, WIDTH / 2 - stringWidth / 2, HEIGHT / 2);
				} else if (_gameClient.hasEnemyWon()) {
					int stringWidth = g2.getFontMetrics().stringWidth(enemyWonString);
					g.drawString(enemyWonString, WIDTH / 2 - stringWidth / 2, HEIGHT / 2);
				}
			}
			if (_gameClient.hasTie()) {
				Graphics2D g2 = (Graphics2D) g;
				g.setColor(Color.BLACK);
				g.setFont(largerFont);
				int stringWidth = g2.getFontMetrics().stringWidth(tieString);
				g.drawString(tieString, WIDTH / 2 - stringWidth / 2, HEIGHT / 2);
			}
		} else {
			g.setColor(Color.RED);
			g.setFont(font);
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			int stringWidth = g2.getFontMetrics().stringWidth(waitingString);
			g.drawString(waitingString, WIDTH / 2 - stringWidth / 2, HEIGHT / 2);
		}

	}
	
	public void tick(GameClient gameClient) {
		if (gameClient.getErrors() >= 10) gameClient.setCommunicationState(true);

		if (!gameClient.isYourTurn() && !gameClient.isUnnableToCommunicateWithOpponent()) {
			try {
				int space = gameClient.getInputStream().readInt();
				if (gameClient.isCircle()) gameClient.setBoard(space, "X");
				else gameClient.setBoard(space, "O");
				_gameClient.getGameEngine().checkForEnemyWin(gameClient);
				_gameClient.getGameEngine().checkForTie(gameClient);
				gameClient.setTurn(true);
			} catch (IOException e) {
				e.printStackTrace();
				gameClient.addErrors();
			}
		}
		
	}
	
	class Painter extends JPanel implements MouseListener {
		private static final long serialVersionUID = 1L;

		public Painter() {
			setFocusable(true);
			requestFocus();
			setBackground(Color.WHITE);
			addMouseListener(this);
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			render(g);
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if (_gameClient.isAccepted()) {
				if (_gameClient.isYourTurn() && !_gameClient.isUnnableToCommunicateWithOpponent() 
						&& !_gameClient.hasWon() && !_gameClient.hasEnemyWon()) {
					int x = e.getX() / lengthOfSpace;
					int y = e.getY() / lengthOfSpace;
					y *= 3;
					int position = x + y;

					if (_gameClient.getBoard()[position] == null) {
						if (!_gameClient.isCircle()) _gameClient.setBoard(position, "X");
						else _gameClient.setBoard(position, "O");
						_gameClient.setTurn(false);
						repaint();
						Toolkit.getDefaultToolkit().sync();

						try {
							_gameClient.getOutputStream().writeInt(position);
							_gameClient.getOutputStream().flush();
						} catch (IOException e1) {
							_gameClient.addErrors();
							e1.printStackTrace();
						}

						System.out.println("DATA WAS SENT");
						_gameClient.getGameEngine().checkForWin(_gameClient);
						_gameClient.getGameEngine().checkForTie(_gameClient);

					}
				}
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {

		}

		@Override
		public void mouseReleased(MouseEvent e) {

		}

		@Override
		public void mouseEntered(MouseEvent e) {

		}

		@Override
		public void mouseExited(MouseEvent e) {

		}

	}


	public Painter getPainter() {
		return _painter;
	}

}
