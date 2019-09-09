package gameGUI;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import game.Cluedo;
import gameGUI.CardsGUI;

public class PlayerGUI extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Cluedo game;
	JTextField nameField;
	JButton viewCardsButton;
	public ArrayList<BufferedImage> playerImages = new ArrayList<>();


	public PlayerGUI(Cluedo game) {
		this.setLayout(null);
		this.setOpaque(false);
		this.setBounds(0, 700, 275, 300);
		this.game = game;

		
		this.nameField = new JTextField("WELCOME");
		this.nameField.setHorizontalAlignment(SwingConstants.CENTER);
		this.nameField.setBounds(22, 25, 230, 30);
		this.nameField.setEditable(false);
		this.add(this.nameField);
		
		
		
		this.viewCardsButton = new JButton("Show Cards");
		this.viewCardsButton.setBounds(45, 190, 175, 40);
		this.viewCardsButton.setMnemonic(KeyEvent.VK_V);
		this.add(this.viewCardsButton);
		this.viewCardsButton.setToolTipText("Shows your cards");

		this.viewCardsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				new CardsGUI(game.currentPlayer.getName(), game.currentPlayer.getPlayerCards(),
						game.getListOfCards());
			}
		});

	}


	public void loadPlayerDisplayImg() {
		for (int i = 0; i < this.game.currentBoard.getListOfPlayers().size(); i++) {
			String charactersName = this.game.currentBoard.getListOfPlayers().get(i).getName();
			try {
				this.playerImages.add( ImageIO.read(new File("src/resources/img/playerDisplay/" + charactersName + ".png")));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


	public void updatePlayerStatusPanel() {
		if (this.game.currentPlayer == null) {
			this.nameField.setText("GAME OVER"); //EVERYONE MADE WRONG ACCUSATIONS
		} else {
			this.nameField.setText(this.game.currentPlayer.getName() + "'s Turn ("
					+ this.game.currentPlayer.getPlayerName() + ")");
		}

		//this.repaint();
		this.paintImmediately(0, 0, 1000, 1000); //Forcing repaint
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (!this.game.isGameOver()) {
			g.drawImage(this.playerImages.get(game.getBoards().getListOfPlayers().indexOf(game.currentPlayer)), 85, 70,
					100, 100, null); 
		} 

	}
}
