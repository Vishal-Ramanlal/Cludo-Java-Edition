package game;
import java.awt.Point;
import java.util.ArrayList;

/**
 * @author Vishal Ramanlal & Ritesh Patel
 *
 */
public class Room {
	private String name;
	private ArrayList<Point> roomDoors = new ArrayList<Point>();
	private ArrayList<Point> roomTiles = new ArrayList<Point>();
	private Weapon weapon = null;
	private char weaponChar;

	/**
	 * Returns the tiles for each room
	 * @return
	 */
	public ArrayList<Point> getRoomTiles() {
		return roomTiles;
	}

	/**
	 * Constructor to make new room object
	 * @param name
	 */
	public Room(String name) {
		this.name = name;
		
	}
	/**
	 * Returns char character of the weapon to display on board
	 * @return
	 */
	public char getWeaponChar() {
		return weaponChar;
	}
	
	/**
	 * Creates weapon char for weapon
	 * @param weapon
	 */
	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
		if(weapon.getName() == "CandleStick") {
			weaponChar = '/';
		}else if(weapon.getName() == "Dagger"){
			weaponChar ='{';
		}
		else if(weapon.getName() == "LeadPipe"){
			weaponChar ='}';
		}
		else if(weapon.getName() == "Revolver"){
			weaponChar ='&';
		}
		else if(weapon.getName() == "Rope"){
			weaponChar ='@';
		}
		else if(weapon.getName() == "Spanner"){
			weaponChar ='!';
		}
	}
	
	/**
	 * Returns Weapon
	 * @return
	 */
	public Weapon getWeapon() {
		return weapon;
	}



	/**
	 * Returns Name of weapon
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * returns list of doors to the room
	 * @return
	 */
	public ArrayList<Point> getRoomDoors() {
		return roomDoors;
}

	/**
	 * Sets list of doors to room
	 * @param roomDoors
	 */
	public void setRoomDoors(ArrayList<Point> roomDoors) {
		this.roomDoors = roomDoors;
	}
	
	
	/**
	 * Sets room tiles of room
	 * @param roomTiles
	 */
	public void setRoomTiles(ArrayList<Point> roomTiles) {
		this.roomTiles = roomTiles;
	}
	
	/**
	 * returns specific character of board
	 * @param board
	 * @return char
	 */
	public char getRoomTile(Board b){
		char[][] board = b.getGameBoard();
		Point p = roomTiles.get(2);
		int X = (int) p.getX();
		int Y = (int) p.getY();
		char c;
		c = board[X][Y];
		return c;
	}
}
