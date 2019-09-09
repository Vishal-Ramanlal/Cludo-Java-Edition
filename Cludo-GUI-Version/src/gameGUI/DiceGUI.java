package gameGUI;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

	public class DiceGUI extends JPanel {
		private static final long serialVersionUID = 1L;
		BufferedImage[] diceImages = new BufferedImage[7];
		private int currentRollA = 1;
		private int currentRollB = 1;

		public DiceGUI() {
			
			this.setBounds(580, 720, 275, 300);
			this.setLayout(new BorderLayout());
			this.setOpaque(false);
			

			this.setToolTipText("Dice Roll");

			
			for (int i = 0; i < 7; i++) {
				int diceNum = i + 1;
				try {
					this.diceImages[i] = ImageIO.read(new File("src/resources/img/dice/" + diceNum + ".png"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		public void displayRoll(int rollA, int rollB) {
			this.currentRollA = rollA;
			this.currentRollB = rollB;
			this.repaint();

		}
		
		public void displayRollNow() {
			this.paintImmediately(0,0,80, 10);
		}

		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(this.diceImages[this.currentRollA - 1], 10, 0, null);
			g.drawImage(this.diceImages[this.currentRollB - 1], 10, 100, null);
		}

	}

