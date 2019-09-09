package gameGUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
//import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
//import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import game.Board;
import game.Cluedo;


import gameGUI.BoardGUI;

public class MainFrame extends JPanel {

	private static final long serialVersionUID = 1L;
	private BufferedImage mainGameBackground;
	private JPanel boardPanel;
	public JPanel playerStatusPanel;
	public int counter;
	private char moveChar;

	public JPanel getPlayerStatusPanel() {
		return playerStatusPanel;
	}

	private JPanel playerOptionPanel;
	private JPanel dicePanel;
	private int currentDieRoll;
	public boolean suggestPressed = false;

	ActionListener buttonListener = new ButtonListener();
	JButton moveButton;
	JButton rollButton;
	JButton suggestButton;
	JButton accuseButton;

	JButton moveUP;
	JButton moveDOWN;
	JButton moveLEFT;
	JButton moveRIGHT;

	private Cluedo game;
	private Board b;
	public char pressedKey;

	public MainFrame(Cluedo cluedoGame, Board b) {
		this.game = cluedoGame;
		this.b = b;
		try {
			this.mainGameBackground = ImageIO.read(new File("src/resources/img/backgrounds/mainbg.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.setLayout(null);

		this.boardPanel = new BoardGUI(this, this.game, b);
		this.playerStatusPanel = new PlayerGUI(this.game);
		this.dicePanel = new DiceGUI();
		this.add(this.boardPanel);
		this.add(this.playerStatusPanel);
		this.add(this.dicePanel);
		((BoardGUI) boardPanel).loadImages();
		((DiceGUI) this.dicePanel).displayRoll(7, 7);
		((PlayerGUI) this.playerStatusPanel).loadPlayerDisplayImg();
		this.setUpActivityPanel();
	}

	public void rollPressed() {
		updateFrameNow();
		((PlayerGUI) this.playerStatusPanel).updatePlayerStatusPanel();
		int[] roll = game.rollDice();
		currentDieRoll = roll[2];
		((DiceGUI) this.dicePanel).displayRoll(roll[0], roll[1]);
		this.rollButton.setEnabled(false);
		this.moveButton.setEnabled(true);
		counter = currentDieRoll;
	}

	public void unlockMove() {
		this.moveRIGHT.setEnabled(true);
		this.moveLEFT.setEnabled(true);
		this.moveUP.setEnabled(true);
		this.moveDOWN.setEnabled(true);
		this.moveButton.setEnabled(false);
	}

	public void movePressed() { // runs once if you press a move button
		this.moveRIGHT.setEnabled(true);
		this.moveLEFT.setEnabled(true);
		this.moveUP.setEnabled(true);
		this.moveDOWN.setEnabled(true);
		this.moveButton.setEnabled(false); // Disable the move button since the current player already chose to move.
		updateFrameNow();

		if (counter > 0) {
			game.makeMove(b, game.currentPlayer, boardPanel, moveChar);
			updateFrameNow();
		//	System.out.println(counter);
		}
		if (this.suggestPressed == true) {
			counter = 0;
			updateFrameNow();
			this.rollButton.setEnabled(false);
			new SecondaryFrame("SUGGEST", this, this.game);
			this.suggestPressed = false;
		}

		if (counter == 0) {
			updateFrameNow();
			this.rollButton.setEnabled(true);
			this.moveRIGHT.setEnabled(false);
			this.moveLEFT.setEnabled(false);
			this.moveUP.setEnabled(false);
			this.moveDOWN.setEnabled(false);
			updateFrameNow();
			nextTurn();
			((PlayerGUI) this.playerStatusPanel).updatePlayerStatusPanel(); // Immediately paint next player
		}

	}
	
	public void refreshDisplay(){
		((DiceGUI) this.dicePanel).displayRoll(7, 7); // for icon dice
		((BoardGUI) boardPanel).updateBoardPanel();
		((PlayerGUI) this.playerStatusPanel).updatePlayerStatusPanel(); // NO
	}

	public void nextTurn() {
		game.setCurrentPlayer();
		((DiceGUI) this.dicePanel).displayRoll(7, 7); // for icon dice
		((BoardGUI) boardPanel).updateBoardPanel();
		((PlayerGUI) this.playerStatusPanel).updatePlayerStatusPanel(); // NO

		if (this.game.currentPlayer == null) { // FINISH GAME
			for (Component c : this.playerOptionPanel.getComponents()) {
				c.setEnabled(false);
			}
			for (Component c : this.playerStatusPanel.getComponents()) {
				c.setEnabled(false);
			}
			return;
		}
	}

	public void sendSuggestion(String roomName, String characterName, String weaponName) {
		// game.moveCounter = 0; //stops turn when in room
		String result = this.game.doSuggestion(roomName, characterName, weaponName);
		((BoardGUI) boardPanel).updateBoardPanel();

		String optionButtons[] = { "Yes", "No" };
		int PromptResult = JOptionPane.showOptionDialog(null, result + "Would you like to make an accusation?",
				"Make an Accusation?", JOptionPane.DEFAULT_OPTION, JOptionPane.DEFAULT_OPTION, null, optionButtons,
				optionButtons[1]);
		if (PromptResult == 0) {
			new SecondaryFrame("ACCUSE", MainFrame.this, this.game);
		}

		//this.nextTurn();
		//refreshDisplay();
		//game.setCurrentPlayer();
		refreshDisplay();
	}

	public void sendAccusation(String roomName, String characterName, String weaponName) {
		counter = 0;
		if (this.game.doAccusation(roomName, characterName, weaponName)) {
			this.announceWinner();
			return;
		}

		JOptionPane.showMessageDialog(
				null, "Accusation Incorrect: " + this.game.currentPlayer.getName() + " ("
						+ this.game.currentPlayer.getName() + ")" + " has been eliminated.",
				"Accusation Incorrect!", JOptionPane.INFORMATION_MESSAGE);

		//this.nextTurn();
		//refreshDisplay();
		game.setCurrentPlayer();
		refreshDisplay();
	}

	public void announceWinner() {
		JOptionPane.showMessageDialog(null, "Accusation Correct: " + this.game.currentPlayer.getName() + " ("
				+ this.game.currentPlayer.getName() + ")" + " Won", "You Win", JOptionPane.INFORMATION_MESSAGE);

		for (Component c : this.playerOptionPanel.getComponents()) {
			c.setEnabled(false);
		}

		((BoardGUI) boardPanel).updateBoardPanel();
	}

	public void setUpActivityPanel() {
		// Set up the player options panel.
		this.playerOptionPanel = new JPanel(null);
		this.playerOptionPanel.setBounds(260, 700, 285, 285);
		this.playerOptionPanel.setOpaque(false);
		this.playerOptionPanel.setBackground(Color.black);

		this.moveUP = new JButton("UP");
		this.moveUP.setBounds(95, 19, 85, 25);
		this.moveUP.setActionCommand("UP");
		this.moveUP.setMnemonic(KeyEvent.VK_W);
		this.moveUP.addActionListener(this.buttonListener);
		this.moveUP.setEnabled(false);
		this.moveUP.setToolTipText("MOVE UP");

		this.moveDOWN = new JButton("DOWN");
		this.moveDOWN.setBounds(95, 71, 85, 25);
		this.moveDOWN.setActionCommand("DOWN");
		this.moveDOWN.setMnemonic(KeyEvent.VK_S);
		this.moveDOWN.addActionListener(this.buttonListener);
		this.moveDOWN.setEnabled(false);
		this.moveDOWN.setToolTipText("MOVE DOWN");

		this.moveLEFT = new JButton("LEFT");
		this.moveLEFT.setBounds(13, 45, 85, 25);
		this.moveLEFT.setActionCommand("LEFT");
		this.moveLEFT.setMnemonic(KeyEvent.VK_A);
		this.moveLEFT.addActionListener(this.buttonListener);
		this.moveLEFT.setEnabled(false);
		this.moveLEFT.setToolTipText("MOVE LEFT");

		this.moveRIGHT = new JButton("RIGHT");
		this.moveRIGHT.setBounds(177, 45, 85, 25);
		this.moveRIGHT.setActionCommand("RIGHT");
		this.moveRIGHT.setMnemonic(KeyEvent.VK_D);
		this.moveRIGHT.addActionListener(this.buttonListener);
		this.moveRIGHT.setEnabled(false);
		this.moveRIGHT.setToolTipText("MOVE RIGHT");

		this.moveButton = new JButton("Move (Move)");
		this.moveButton.setBounds(80, 120, 120, 25);
		this.moveButton.setActionCommand("MOVE");
		this.moveButton.setMnemonic(KeyEvent.VK_M);
		this.moveButton.addActionListener(this.buttonListener);
		this.moveButton.setEnabled(false);
		this.moveButton.setToolTipText("Click here to move the player");

		this.rollButton = new JButton("Roll (Roll)");
		this.rollButton.setBounds(80, 155, 120, 25);
		this.rollButton.setActionCommand("ROLL");
		this.rollButton.setMnemonic(KeyEvent.VK_R);
		this.rollButton.addActionListener(this.buttonListener);
		this.rollButton.setToolTipText("Click here to roll the die");
		/*
		 * this.suggestButton = new JButton("Suggest"); this.suggestButton.setBounds(80,
		 * 120, 120, 25); this.suggestButton.setActionCommand("SUGGEST");
		 * this.suggestButton.setMnemonic(KeyEvent.VK_S);
		 * this.suggestButton.addActionListener(this.buttonListener);
		 * this.suggestButton.setEnabled(false); //Disable button. Only valid in
		 * "Special" circumstances
		 * this.suggestButton.setToolTipText("Click here to make a suggestion");
		 */

		this.accuseButton = new JButton("Accuse");
		this.accuseButton.setBounds(80, 190, 120, 25);
		this.accuseButton.setActionCommand("ACCUSE");
		this.accuseButton.setMnemonic(KeyEvent.VK_A);
		this.accuseButton.addActionListener(this.buttonListener);
		this.accuseButton.setToolTipText("Click here to make an accusation.");

		this.playerOptionPanel.add(this.moveButton);
		this.playerOptionPanel.add(this.rollButton);
		// this.playerOptionPanel.add(this.suggestButton); - FORCED SUGGESTION INSTEAD
		this.playerOptionPanel.add(this.accuseButton);

		this.playerOptionPanel.add(this.moveUP);
		this.playerOptionPanel.add(this.moveDOWN);
		this.playerOptionPanel.add(this.moveLEFT);
		this.playerOptionPanel.add(this.moveRIGHT);

		this.add(this.playerOptionPanel);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(mainGameBackground, 0, 0, null);
	}

	public void updateFrameNow() {
		this.paintImmediately(0, 0, 1500, 1500);
	}

	private class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			switch (event.getActionCommand()) {
			case "MOVE":
				unlockMove();
				break;

			case "ROLL":
				rollPressed();
				break;

			case "ACCUSE":
				new SecondaryFrame("ACCUSE", MainFrame.this, game);
				break;

			case "UP":
				moveChar = 'W';
				movePressed();

				break;

			case "DOWN":
				moveChar = 'S';
				movePressed();

				break;

			case "LEFT":
				moveChar = 'A';
				movePressed();

				break;

			case "RIGHT":
				moveChar = 'D';
				movePressed();

				break;

			}
		}
	}

}
