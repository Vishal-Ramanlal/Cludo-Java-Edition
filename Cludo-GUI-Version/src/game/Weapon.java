package game;

import java.awt.Point;

/**
 * @author Vishal Ramanlal & Ritesh Patel
 * Class that creates a weapon object
 */
public class Weapon {
	int id;
	String name;
	private Point location;
	
	public Point getLocation() {
		return location;
	}

	public void setLocation(Point location) {
		this.location = location;
	}

	/**
	 * Generate new weapon
	 * @param i
	 * @param n
	 */
	public Weapon(int i, String n) {
		id = i;
		name = n;
	}

	/**
	 * returns id of weapon
	 * @return
	 * 
	 */
	public int getId() {
		return id;
	}


	/**
	 * Returns the name of the weapon
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the weapon
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	public int getX() {
		int x = (int) location.getX();
		return x;
	}
	/**
	 * @return
	 */
	public int getY() {
		int y = (int) location.getY();
		return y;
	}
}
	

