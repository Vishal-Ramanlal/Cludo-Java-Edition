package gameGUI;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import game.Cluedo;
//import game.Board;

public class CluedoGUI extends JFrame implements WindowListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public CluedoGUI(String title) {
		super(title); // new window with set title

		// Set game icon on the title bar.
		ImageIcon icon = new ImageIcon(this.getClass().getResource("/resources/img/backgrounds/windowIcon.png"));
		setIconImage(icon.getImage());

		setSize(722, 1000); // MAIN SIZE OF WINDOW
		this.setResizable(false); // rezizing causes bugs :(

		this.setUpMenuBar(); // navbar
		this.setUpGamePanels(); // all other panes
		this.addWindowListener(this);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // stop from immediate close

		// Centers the whole window in the middle of any display
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension size = toolkit.getScreenSize();
		setBounds((size.width - getWidth()) / 2, (size.height - getHeight()) / 2, getWidth(), getHeight());

		this.setContentPane(this.contentPane);

		this.setVisible(true);
	}

	public void setUpGamePanels() {
		this.contentPane = new JPanel();
		this.contentPane.setLayout(new CardLayout());

		Cluedo game = new Cluedo();
		// Board b = game.getBoards();

		contentPane.add(new PlayerSelectionGUI(contentPane, game), "PlayerSelectionScreen");

	}

	public void setUpMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		this.setJMenuBar(menuBar);

		JMenuItem newGame = new JMenuItem(new AbstractAction("New Game") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent event) {
				new CluedoGUI("Cluedo Makes Me Angery");
				dispose();

			}
		});

		JMenuItem exit = new JMenuItem(new AbstractAction("Quit Game") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent event) {
				closeWindow();
			}
		});

		fileMenu.add(newGame);
		fileMenu.add(exit);
	}

	@Override
	public void windowClosing(WindowEvent we) {
		this.closeWindow();
	}

	public void closeWindow() {
		String optionButtons[] = { "Yes", "No" };
		int PromptResult = JOptionPane.showOptionDialog(null, "Are you sure you want to quit?", "Close Cluedo?",
				JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, optionButtons, optionButtons[1]);
		if (PromptResult == 0)
			System.exit(0);
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
