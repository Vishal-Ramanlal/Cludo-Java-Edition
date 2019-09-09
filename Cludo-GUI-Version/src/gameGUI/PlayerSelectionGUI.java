package gameGUI;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JCheckBox;
//import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import game.Cluedo;

public class PlayerSelectionGUI extends JPanel {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage playerSelectionBackground;
	private JPanel contentPane;
	CardLayout cardLayout;
	private Cluedo game;
//	private BufferedImage[] playerAvatars = new BufferedImage[6];
//	private int offSetY = 50;
//	private int offSetX = 100;

	public PlayerSelectionGUI(JPanel contentPane, Cluedo game) {
		super.setLayout(null);

		this.contentPane = contentPane;
		this.game = game;

		try {
			this.playerSelectionBackground = ImageIO.read(new File("src/resources/img/backgrounds/playerSelect.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * for (int i = 0; i < characterNames.length; i++) { //formating sucks String
		 * charactersName = characterNames[i]; try { this.playerAvatars[i] =
		 * ImageIO.read(new File("src/resources/img/player_avatars/" + charactersName +
		 * ".png")); } catch (IOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } }
		 */
		this.createSelectionInterface();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(playerSelectionBackground, 0, 0, null);
		/*
		 * for(int i = 0; i < playerAvatars.length; i++) {
		 * g.drawImage(this.playerAvatars[i], offSetX, offSetY, 100, 100, null); offSetY
		 * += 50; offSetX += 0; }
		 */
	}

	/**
	 * Create the interface for the player selection screen.
	 */
	public void createSelectionInterface() {

		// Set up the selection panel
		JPanel selectionPanel = new JPanel();
		selectionPanel.setBounds(-50, 150, 800, 800);
		selectionPanel.setLayout(new GridLayout(3, 2, 5, 5));
		// selectionPanel.setAlignmentY(JComponent.LEFT_ALIGNMENT);
		selectionPanel.setOpaque(false);

		// Create panels for each character choice
		JPanel mrsWhitePanel = new JPanel();
		mrsWhitePanel.setOpaque(false);
		JTextField mrsWhiteField = new JTextField("Player 1", 10);
		JCheckBox mrsWhiteCheck = new JCheckBox("Mrs. White", true);
		mrsWhiteCheck.setBackground(new Color(51, 50, 50));
		mrsWhiteCheck.setForeground(Color.WHITE);
		mrsWhitePanel.add(mrsWhiteCheck);
		mrsWhitePanel.add(mrsWhiteField);

		JPanel mrGreenPanel = new JPanel();
		mrGreenPanel.setOpaque(false);
		JTextField mrGreenPanelField = new JTextField("Player 2", 10);
		JCheckBox mrGreenPanelCheck = new JCheckBox("Mr. Green", true);
		mrGreenPanelCheck.setBackground(new Color(51, 50, 50));
		mrGreenPanelCheck.setForeground(Color.WHITE);
		mrGreenPanel.add(mrGreenPanelCheck);
		mrGreenPanel.add(mrGreenPanelField);

		JPanel mrsPeacockPanel = new JPanel();
		mrsPeacockPanel.setOpaque(false);
		JTextField mrsPeacockTF = new JTextField("Player 3", 10);
		JCheckBox mrsPeacockBox = new JCheckBox("Mrs. Peacock", true);
		mrsPeacockBox.setBackground(new Color(51, 50, 50));
		mrsPeacockBox.setForeground(Color.WHITE);
		mrsPeacockPanel.add(mrsPeacockBox);
		mrsPeacockPanel.add(mrsPeacockTF);

		JPanel colMustardPanel = new JPanel();
		colMustardPanel.setOpaque(false);
		JCheckBox colMustardFieldCheck = new JCheckBox("Colonel Mustard", false);
		JTextField colMustardField = new JTextField("Player 4", 10);
		colMustardFieldCheck.setBackground(new Color(51, 50, 50));
		colMustardFieldCheck.setForeground(Color.WHITE);
		colMustardPanel.add(colMustardFieldCheck);
		colMustardPanel.add(colMustardField);

		JPanel profPlumPanel = new JPanel();
		profPlumPanel.setOpaque(false);
		JTextField profPlumPanelField = new JTextField("Player 5", 10);
		JCheckBox profPlumPanelCheck = new JCheckBox("Professor Plum", false);
		profPlumPanelCheck.setBackground(new Color(51, 50, 50));
		profPlumPanelCheck.setForeground(Color.WHITE);
		profPlumPanel.add(profPlumPanelCheck);
		profPlumPanel.add(profPlumPanelField);

		JPanel missScarlettPanel = new JPanel();
		missScarlettPanel.setOpaque(false);
		JCheckBox missScarlettBox = new JCheckBox("Miss Scarlett", false);
		JTextField missScarlettTF = new JTextField("Player 6", 10);
		missScarlettBox.setBackground(new Color(51, 50, 50));
		missScarlettBox.setForeground(Color.WHITE);
		missScarlettPanel.add(missScarlettBox);
		missScarlettPanel.add(missScarlettTF);

		selectionPanel.add(mrsWhitePanel);
		selectionPanel.add(mrGreenPanel);
		selectionPanel.add(mrsPeacockPanel);
		selectionPanel.add(colMustardPanel);
		selectionPanel.add(profPlumPanel);
		selectionPanel.add(missScarlettPanel);

		this.add(selectionPanel);

		JButton continueButton = new JButton("Continue");
		continueButton.setToolTipText("Click here once you have selected three or more players");
		continueButton.setMnemonic(KeyEvent.VK_ENTER);

		continueButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {

				ArrayList<Integer> countList = new ArrayList<Integer>();
				if (mrsWhiteCheck.isSelected()) {
					game.getBoards().setupPlayer(mrsWhiteField.getText(), "mrsWhite");
					countList.add(1);
				}

				if (mrGreenPanelCheck.isSelected()) {
					game.getBoards().setupPlayer(mrGreenPanelField.getText(), "mrGreen");
					countList.add(1);
				}

				if (mrsPeacockBox.isSelected()) {
					game.getBoards().setupPlayer(mrsPeacockTF.getText(), "mrsPeacock");
					countList.add(1);
				}

				if (colMustardFieldCheck.isSelected()) {
					game.getBoards().setupPlayer(colMustardField.getText(), "colMustard");
					countList.add(1);
				}

				if (profPlumPanelCheck.isSelected()) {
					game.getBoards().setupPlayer(profPlumPanelField.getText(), "profPlum");
					countList.add(1);
				}

				if (missScarlettBox.isSelected()) {
					game.getBoards().setupPlayer(missScarlettTF.getText(), "missScarlett");
					countList.add(1);
				}

				int playerCount = 0;

				for (Integer i : countList) {
					if (i == 1) {
						playerCount++;
					}
				}

				if (playerCount < 3 || playerCount > 6) {
					JOptionPane.showMessageDialog(null, "Minimum 3 Players Required.");
					return;
				}
				game.setupCards();
				game.setCurrentPlayer();
				/*
				 * for( Player x : game.currentBoard.getListOfPlayers()) {
				 * System.out.println(x.getName()); }
				 */

				contentPane.add(new MainFrame(game, game.currentBoard), "MainGameScreen");
				cardLayout = (CardLayout) contentPane.getLayout();
				cardLayout.next(contentPane); // GOTO: next panel
			}
		});

		continueButton.setPreferredSize(new Dimension(150, 50));

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(continueButton);
		buttonPanel.setBounds(250, 850, 200, 250);
		buttonPanel.setOpaque(false);

		this.add(buttonPanel);

	}

}
