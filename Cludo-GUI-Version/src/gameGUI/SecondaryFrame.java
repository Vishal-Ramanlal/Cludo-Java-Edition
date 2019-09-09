package gameGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import game.Cluedo;

public class SecondaryFrame extends JDialog implements WindowListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public final String characterNames[] = { "mrsWhite", "mrGreen", "mrsPeacock", "colMustard", "profPlum",
			"missScarlett" };
	public final String weaponNames[] = { "CandleStick", "Dagger", "LeadPipe", "Revolver", "Rope", "Spanner" };
	public final String roomNames[] = { "Kitchen", "BallRoom", "Conservatory", "DiningRoom", "BillardRoom", "Library",
			"Lounge", "Hall", "Study" };

	private MainFrame mainPanel;
	private BufferedImage accuseBackground;
	private BufferedImage suggestBackground;
	private String option;
	private Cluedo game;

	private String roomGuess;
	private String characterGuess;
	private String weaponGuess;

	public SecondaryFrame(String option, MainFrame mainPanel, Cluedo game) {
		this.option = option;
		this.mainPanel = mainPanel;
		this.game = game;

		if (option.equals("ACCUSE")) {
			this.setTitle("Make an Accusation!");
		} else {
			this.setTitle("Make a Suggestion!");
		}

		this.setSize(665, 530);

		// Center window in screen
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension size = toolkit.getScreenSize();
		setBounds((size.width - getWidth()) / 2, (size.height - getHeight()) / 2, getWidth(), getHeight());

		// Load the backgrounds.
		try {
			this.accuseBackground = ImageIO.read(new File("src/resources/img/backgrounds/accusationbg.png"));
			this.suggestBackground = ImageIO.read(new File("src/resources/img/backgrounds/suggestionbg.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(this);

		this.setUpTaskPanel();

		setVisible(true);
	}

	public void setUpTaskPanel() {

		JPanel taskPanel = new JPanel(null) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);

				if (option.equals("ACCUSE")) {
					g.drawImage(accuseBackground, 0, 0, null); // Draw background of the jpanel
				} else {
					g.drawImage(suggestBackground, 0, 0, null); // Draw background of the jpanel
				}

			}
		};

		// Create the room choice panel.
		JPanel roomSelectPanel = this.createRoomChoicePanel();
		roomSelectPanel.setBounds(23, 105, 180, 287);

		// Create the character choice panel.
		JPanel characterSelectPanel = this.createCharacterChoicePanel();
		characterSelectPanel.setBounds(235, 105, 180, 287);

		// Create the weapon choice panel.
		JPanel weaponSelectPanel = this.createWeaponChoicePanel();
		weaponSelectPanel.setBounds(445, 105, 180, 287);

		this.add(roomSelectPanel);
		this.add(characterSelectPanel);
		this.add(weaponSelectPanel);
		this.add(taskPanel);
		this.addButtons();
	}

	public void addButtons() {
		JPanel south = new JPanel();
		south.setBackground(new Color(32, 32, 32));
		JButton submitButton = new JButton("Submit");
		submitButton.setToolTipText("Submit");

		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				buttonPressed();
			}
		});

		JButton cancelButton = null;

		if (this.option.equals("ACCUSE")) {
			cancelButton = new JButton("Cancel Accusation");
			cancelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					dispose(); // remove dialog box
				}
			});

			cancelButton.setToolTipText("Press to Cancel Accusation");
		}

		south.add(submitButton);

		if (this.option.equals("ACCUSE")) {
			south.add(cancelButton);
		}

		this.add(south, BorderLayout.SOUTH);
	}

	//upon button press
	public void buttonPressed() {
		mainPanel.rollButton.setEnabled(true);

		if (this.roomGuess == null || this.characterGuess == null || this.weaponGuess == null) {
			JOptionPane.showMessageDialog(this, "Please select a Room, Character, and Weapon to Continue.");
			return;
		}

		if (this.option.equals("ACCUSE")) {
			this.dispose(); // Close the window.
			this.mainPanel.sendAccusation(this.roomGuess, this.characterGuess, this.weaponGuess);
		} else {
			this.dispose(); // Close the window.
			this.mainPanel.sendSuggestion(this.roomGuess, this.characterGuess, this.weaponGuess);

		}
	}

	/**
	 * Creates a panel with radio buttons consisting of room choices.
	 * 
	 * @return panel with room choice radio buttons.
	 */
	public JPanel createRoomChoicePanel() {
		final JRadioButton[] roomButtons = new JRadioButton[this.roomNames.length];
		ButtonGroup group = new ButtonGroup();

		for (int i = 0; i != roomNames.length; ++i) {
			roomButtons[i] = new JRadioButton(roomNames[i]);

			roomButtons[i].setAction(new AbstractAction(roomNames[i]) {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				public void actionPerformed(ActionEvent e) {
					AbstractButton selectedRadio = (AbstractButton) e.getSource();
					roomGuess = selectedRadio.getText();

				}
			});

			// Disable room boxes
			if (this.option.equals("SUGGEST")) {
				roomButtons[i].setEnabled(false);
				//System.out.println(game.currentPlayer.getName());
				if (game.currentPlayer.getRoom() == null) {
				//System.out.println("NO ROOM");
				}

				if (roomButtons[i].getText().equals(game.currentPlayer.getRoom().getName())) {
					roomButtons[i].setSelected(true);
				}
			}

			roomButtons[i].setOpaque(false);
			roomButtons[i].setForeground(Color.WHITE);
			group.add(roomButtons[i]);
		}

		// sets the current players room as the suggestion.
		if (this.option.equals("SUGGEST")) {
			//System.out.println(this.game.currentPlayer.getRoom().getName());
			this.roomGuess = this.game.currentPlayer.getRoom().getName();
		}

		JPanel roomChoicePanel = new JPanel(new GridLayout(9, 1));
		for (JRadioButton box : roomButtons) {
			roomChoicePanel.add(box);
		}

		roomChoicePanel.setOpaque(false);
		return roomChoicePanel;
	}

	public JPanel createCharacterChoicePanel() {
		final JRadioButton[] characterButtons = new JRadioButton[this.characterNames.length];
		ButtonGroup group = new ButtonGroup();

		for (int i = 0; i != this.characterNames.length; ++i) {
			characterButtons[i] = new JRadioButton(this.characterNames[i]);

			characterButtons[i].setAction(new AbstractAction(this.characterNames[i]) {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				public void actionPerformed(ActionEvent e) {
					AbstractButton selectedRadio = (AbstractButton) e.getSource();
					characterGuess = selectedRadio.getText();

				}
			});

			characterButtons[i].setOpaque(false);
			characterButtons[i].setForeground(Color.WHITE);
			group.add(characterButtons[i]);
		}

		JPanel characterChoicePanel = new JPanel(new GridLayout(6, 1));
		for (JRadioButton box : characterButtons) {
			characterChoicePanel.add(box);
		}

		characterChoicePanel.setOpaque(false);
		return characterChoicePanel;
	}

	public JPanel createWeaponChoicePanel() {
		final JRadioButton[] weaponButtons = new JRadioButton[this.weaponNames.length];
		ButtonGroup group = new ButtonGroup();

		for (int i = 0; i != this.weaponNames.length; ++i) {

			weaponButtons[i] = new JRadioButton(this.weaponNames[i]);

			weaponButtons[i].setAction(new AbstractAction(this.weaponNames[i]) {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				public void actionPerformed(ActionEvent e) {
					AbstractButton selectedRadio = (AbstractButton) e.getSource();
					weaponGuess = selectedRadio.getText();
				}
			});

			weaponButtons[i].setOpaque(false);
			weaponButtons[i].setForeground(Color.WHITE);
			group.add(weaponButtons[i]);
		}

		JPanel weaponChoicePanel = new JPanel(new GridLayout(6, 1));
		for (JRadioButton box : weaponButtons) {
			weaponChoicePanel.add(box);
		}

		weaponChoicePanel.setOpaque(false);
		return weaponChoicePanel;
	}

	@Override
	public void windowClosing(WindowEvent we) {
		this.closeWindow();
	}

	public void closeWindow() {
		if (this.option.equals("SUGGEST")) {
			JOptionPane.showMessageDialog(this, "You must make a suggestion!");
			return;
		}

		this.dispose();
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
	}

}
