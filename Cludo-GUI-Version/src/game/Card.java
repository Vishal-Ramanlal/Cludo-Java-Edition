package game;

import java.awt.Point;

/**
 * @author Vishal Ramanlal & Ritesh Patel
 *
 */
public class Card {
	//int id;
	String type; 
	String name;


	public Card( String t, String n) {
		//id = i;
		type = t;
		name = n;

	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}


	
	
}
