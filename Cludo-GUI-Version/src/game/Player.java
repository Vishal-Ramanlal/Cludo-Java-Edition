package game;
import java.awt.Point;
import java.util.ArrayList;

/**
 * @author Vishal Ramanlal & Ritesh Patel
 * This is the player object class. A player is identifed by its name and location
 */
public class Player {
	private String name;
	private String playerName;
	public String getPlayerName() {
		return playerName;
	}

	private Point location;
	private ArrayList<Card> playerCards = new ArrayList<>();
	private Room room; // which room player is in
	public int Accusations = 1;
	/**
	 * Returns the name of the player
	 * @return
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Card> getPlayerCards() {
		return playerCards;
	}

	public void setPlayerCards(ArrayList<Card> playerCards) {
		this.playerCards = playerCards;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public Player(String playerName,Point startPosition, String name) {
		location = startPosition;
		this.name = name;
		this.playerName = playerName;
	}

	public Point getLocation() {
		return location;
	}

	public void setLocation(Point location) {
		this.location = location;
	}
	public int getX() {
		int x = (int) location.getX();
		return x;
	}
	public int getY() {
		int y = (int) location.getY();
		return y;
	}
}
