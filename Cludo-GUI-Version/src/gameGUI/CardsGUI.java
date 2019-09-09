package gameGUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import game.Card;

public class CardsGUI extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel cardsPanel;
	private BufferedImage cardPanelbackbround;
	private List<Card> cards = new ArrayList<>();

	public CardsGUI(String title, List<Card> playerCards, List<Card> publicCards) {
		super(title + "'s Cards");

		// System.out.println(playerCards.size()); DEBUGGING

		this.cards.addAll(playerCards);
		if (publicCards != null) {
			this.cards.addAll(publicCards);
		}
		ImageIcon icon = new ImageIcon(this.getClass().getResource("/resources/img/backgrounds/windowIcon.png"));
		setIconImage(icon.getImage());

		setSize(650, 640); //
		this.setResizable(false); // causes bugs

		// Center window in screen
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension scrnsize = toolkit.getScreenSize();
		setBounds((scrnsize.width - getWidth()) / 2, (scrnsize.height - getHeight()) / 2, getWidth(), getHeight());

		try {
			this.cardPanelbackbround = ImageIO.read(new File("src/resources/img/backgrounds/cardsbg.png"));
		} catch (IOException e) {

			e.printStackTrace();
		}

		this.showCards();

		this.setVisible(true);
	}

	public void showCards() {

		this.cardsPanel = new JPanel(null) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(cardPanelbackbround, 0, 0, null);

				int offSetX = 42;
				int offSetY = 60;
				for (int i = 0; i < cards.size(); i++) {
					//System.out.println(cards.get(i).getName());

					BufferedImage currentImg = null;

					try {
						currentImg = ImageIO
								.read(new File("src/resources/img/cards/" + cards.get(i).getName() + ".png"));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					g.drawImage(currentImg, offSetX, offSetY, 155, 230, null);

					offSetX = offSetX + 205;

					if (i == 2) {
						offSetX = 42;
						offSetY = 340;
					}
				}
			}
		};

		this.cardsPanel.setBackground(new Color(32, 32, 32));

		this.add(this.cardsPanel);

	}
}
