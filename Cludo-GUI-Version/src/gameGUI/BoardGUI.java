package gameGUI;

//import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import game.Board;
import game.Player;
import game.Room;
import game.Weapon;
import game.Cluedo;
import gameGUI.MainFrame;
//import tiles.RoomTile;

public class BoardGUI extends JPanel implements MouseMotionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage boardImage;
	public ArrayList<BufferedImage> playerImages = new ArrayList<>(); // Array of buffered images for each player.
	private BufferedImage[] weaponImages = new BufferedImage[6];
	//private Cluedo game;
	private Board board;
	private MainFrame mainPanel;
	private final int offSetX = 44; // offsets to match board image.
	private final int offSetY = 31;

	public BoardGUI(MainFrame main, Cluedo game, Board b) {
		this.setMainPanel(main);
		//this.game = game;
		this.board = b;
		this.addMouseMotionListener(this);

		this.setLayout(null);
		this.setBounds(8, 8, 700, 700);

		try {
			this.boardImage = ImageIO.read(new File("src/resources/img/backgrounds/board.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(this.boardImage, 0, 0, 700, 700, null);

		// Draw Actual board.

		/*
		 * g.setColor(Color.white); for (int col = 0; col < 24; col++) { for (int row =
		 * 0; row < 25; row++) { g.drawRect(this.offSetX + col * 25, this.offSetY + row
		 * * 25, 25, 25); } } char[][] gameBoard = board.getGameBoard();
		 * 
		 * g.setColor(Color.GREEN); //g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
		 * for (int i = 0; i < 24; i++) { for (int j = 0; j < 25; j++) {
		 * g.drawString(String.valueOf(gameBoard[j][i]), this.offSetX + i * 25,
		 * this.offSetY + j * 26);
		 * 
		 * } }
		 */

		// Draw players on their positions.
		for (int i = 0; i < this.board.getListOfPlayers().size(); i++) {
			Player p = this.board.getListOfPlayers().get(i);
			g.drawImage(this.playerImages.get(i), this.offSetX + p.getY() * 25, this.offSetY + p.getX() * 25, 25, 25, null);
		}

		// Display weapons on their positions.
		int i = 0;
		for (Room r : this.board.getRooms().values()) {
			if (r.getWeapon() != null) {
				Weapon w = r.getWeapon();
				g.drawImage(this.weaponImages[i], this.offSetX + w.getY() * 25, this.offSetY + w.getX() * 25, 25, 25,
						null);
				i++;
			}

		}
	}


	public void loadImages() {
		for (int i = 0; i < this.board.getListOfPlayers().size(); i++) {
			try {
				this.playerImages.add((ImageIO.read(new File(
						"src/resources/img/playerIcons/" + this.board.getListOfPlayers().get(i).getName() + ".png"))));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// Load weapon tokens
		int i = 0;
		for (Room r : this.board.getRooms().values()) {
			if (r.getWeapon() != null) {
				Weapon w = r.getWeapon();
				try {
					this.weaponImages[i] = ImageIO
							.read(new File("src/resources/img/weaponIcons/" + w.getName() + ".png"));
					i++;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	public void updateBoardPanel() {
		this.repaint();
	}

	public void updateBoardNow() {
		this.paintImmediately(0, 0, 700, 700);
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}


	
	public MainFrame getMainPanel() {
		return mainPanel;
	}

	public void setMainPanel(MainFrame mainPanel) {
		this.mainPanel = mainPanel;
	}

}
