
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

/**
 * @author Vishal Ramanlal & Ritesh Patel
 *	Cluedo class includes main methods and functionality of the the cluedo game
 */
public class Cluedo {
	private static String listOfWeapons[] = { "CandleStick", "Dagger", "LeadPipe", "Revolver", "Rope", "Spanner" };
	private static String placeArray[] = { "Kitchen", "BallRoom", "Conservatory", "DiningRoom", "BillardRoom",
			"Library", "Lounge", "Hall", "Study" };
	private static ArrayList<Card> listOfAllCards = new ArrayList<>();
	private static ArrayList<Card> listOfCards = new ArrayList<>();
	private static ArrayList<Card> accusationList = new ArrayList<>();
	private static HashMap<Integer, String> listOfPlaces = new HashMap<>();
	private static ArrayList<Card> listOfSuggestions = new ArrayList<>();
	private static ArrayList<Card> listRefutes = new ArrayList<>();
	private static boolean isGameWon = false;
	private static int dicerollNumber = 0;
	private static int numberOfPlayers = 0;
	private static boolean inRoom;

	/**
	 * Main method to run program
	 * @param args
	 */
	public static void main(String[] args) {

		Board b = new Board();

		b.initialiseBoard();
		b.setupBoard();
		b.setupRooms();
		initWeapons(b);

		b.drawBoard();
		System.out.print("\n");
		int num1 = 0;
		int counter = 0;
		do {
			Scanner sc = new Scanner(System.in);
			if (counter != 0) {
				System.out.print("Invalid input, Please eneter the number of players 3-6\n");
			} else {
				System.out.print("Please eneter the number of players 3-6\n");
				counter++;
			}
			while (!sc.hasNextInt()) {
				sc.next();
				System.out.print("Not a vaild number Please write a number between 3-6\n");
				// continue;
			}
			num1 = sc.nextInt();
			// sc.close();
		} while ((num1 > 6) || (num1 < 3));
		numberOfPlayers = num1;
		b.setupPlayer(num1);
		makeCards(b.getListOfPlayers());
		makeFinalDrawList();
		// printBoardNotationGuide();
		giveCards(b);

		Boolean GameIsRunning = true;
		Boolean PlayersTurn = true;
		int number;

		while (GameIsRunning) {
			for (int i = 0; i < num1; i++) {
				int isGameOver = 0;
				for(Player checkP : b.getListOfPlayers()) {
					if(checkP.Accusations > 0) {
						isGameOver ++;
					}
				}
				if(isGameWon) {
					break;
				}
				else if(isGameOver == 0){
					System.out.println("No one has Accusations anymore! Game Over! You all lost!");
					isGameWon = true;
					break;
				}
				System.out.flush();
				System.out.println("\n");
				Player p = b.getListOfPlayers().get(i);
				b.drawBoard();
				System.out.println("\n" + p.getName() + " Turn!");
				number = firstOption(p);
				int tally = 0;
				if (number != 4) {
				
					while (PlayersTurn) {

						if (number == 1 && tally == 0) {
							number = diceChoice(b, p);
							tally++;
						} else if (number == 1) {
							makeMove(dicerollNumber, b, p);
							break;
						}
						/*
						 * Suggestion starts here
						 */
						else if (number == 2) {
							makeSuggestion(b, p);
							
							// end suggestion
							listRefutes.clear();
							ArrayList<Card> playerSuggestions = new ArrayList<>();
							for (Player newP : b.getListOfPlayers()) {
								if (!newP.equals(p)) {
									String tempWord;
									Boolean booleanTemp = true;
									int localCount = 0;

									do {
										if (localCount > 0) {
											System.out.println("No matches please type again");
										}
										Scanner sc = new Scanner(System.in);
										System.out.println("\n");
										System.out.println(newP.getName()
												+ ": Here are the following cards you can use to refute "); // print out
																											// name and
																											// line
										System.out.println("Possible Cards: ");
										int t = 0;

										for (Card Pcards : newP.getPlayerCards()) {// gets players cards
											for (Card Scards1 : listOfSuggestions) {// gets suggestions made by origin
																					// player
												if (Pcards.getName().equals(Scards1.getName())) { // if player card
																									// contains a
																									// suggestion card
																									// print
													System.out.print(Pcards.getName() + ",");
													playerSuggestions.add(Pcards);
													t++;
												}
											}
										}
										if (t == 0) {
											System.out.println("Sorry there are no options for you");
											booleanTemp = false;
											break;
										}
										tempWord = sc.nextLine(); // gets player input
										for (int k = 0; k < playerSuggestions.size(); k++) {
											if (tempWord.equalsIgnoreCase(playerSuggestions.get(k).getName())) {
												listRefutes.add(playerSuggestions.get(k));
												booleanTemp = false;
												break;
											}
										}
										localCount++;
									} while (booleanTemp);
								}

							}
							System.out.println("Other players have refuted these cards: ");
							for (Card printsR : listRefutes) {
								System.out.print(printsR.getName());
							}
							break;

						} else if (p.Accusations > 0 && number == 3) {
							Boolean accusation = makeAccusation(b, p);
							if(accusation == true) {
								System.out.println("Congrats You WON the Game!!");
								isGameWon = true;
								GameIsRunning = false;
								break;
							}
							else{
								System.out.println("Incorrect! You cannot accuse anymore");
								break;
							}
						} else if (number == 4) {
							break;
						}
						

					}
				}
				

			}
			if (isGameWon == true) {
				break;
			}
		}
	}

	/**
	 * Distributes cards to players
	 * @param b
	 */
	private static void giveCards(Board b) {

		int i = 0;
		int j = 0;
		while (i < listOfCards.size()) {
			if (j == numberOfPlayers) {
				j = 0;
			}
			Player p = b.getListOfPlayers().get(j);
			Card c = listOfCards.get(i);
			p.getPlayerCards().add(c);

			i++;
			j++;
		}
	}

	/**
	 * Makes a list of cards to initiate.
	 * @param arrayList
	 */
	private static void makeCards(ArrayList<Player> arrayList) {
		// int size = + placeArray.length + characterArray.length;
		// System.out.println(listOfWeapons.length + " " + placeArray.length + " " +
		// characterArray.length);
		for (int i = 0; i < 6; i++) {
			listOfAllCards.add(i, new Card(i, "Weapon", listOfWeapons[i]));

		}
		for (int i = 0; i < 9; i++) {
			listOfAllCards.add(i, new Card(i, "Room", placeArray[i]));
		}
		for (int i = 0; i < arrayList.size(); i++) {
			listOfAllCards.add(i, new Card(i, "Player", arrayList.get(i).getName()));
		}
		Collections.shuffle(listOfAllCards);
	

	}

	/**
	 * Makes player move on the board. Can go in rooms and quit using commands
	 * @param dice
	 * @param b
	 * @param p
	 */
	private static void makeMove(int dice, Board b, Player p) {
		char board[][] = b.getGameBoard();
		char temp2;
		char temp1;
		int moveCounter = dice;
		Scanner sc = new Scanner(System.in);
		while (moveCounter > 0) {
			System.out.println("\nPlease make a move: | W=UP | S=DOWN | A=LEFT | D = RIGHT | Q = QUIT MOVE");
			char c = sc.next().charAt(0);
			if (c == 'Q' || c == 'q') {
				break;
			}
			if (c == 'W' || c == 'w' && b.canMoveNorth(p) == true) {
				getPlayerRoom(b, p);
				if (b.roomDoors.contains(board[p.getX() - 1][p.getY()])) { // If going through door
					if (b.roomTiles.contains(board[p.getX() - 2][p.getY()]) && p.getRoom() == null) { // if entering
																										// room
						System.out.println("entering room");
						temp1 = board[p.getX()][p.getY()]; // Current player = temp1
						board[p.getX()][p.getY()] = '.'; // current position = '.'
						Point pt = new Point(p.getX() - 2, p.getY()); // new player position = inside room;
						p.setLocation(pt);
						getPlayerRoom(b, p); // sets current room to player.
						board[p.getX()][p.getY()] = temp1; // player is placed in new position
					} else if (board[p.getX() - 2][p.getY()] == '.' && p.getRoom() != null) { // If leaving the room
						System.out.println("leaving room");
						getPlayerRoom(b, p);
						temp1 = board[p.getX()][p.getY()]; // Current player = temp1
						board[p.getX()][p.getY()] = p.getRoom().getRoomTile(b); // Sets current position = 'rTile'
						Point pt = new Point(p.getX() - 2, p.getY()); // new player position = outside room;
						p.setLocation(pt);
						board[p.getX()][p.getY()] = temp1;
						getPlayerRoom(b, p);
					}

				} else {
					inRoom = false;
					temp2 = board[p.getX() - 1][p.getY()];
					board[p.getX() - 1][p.getY()] = board[p.getX()][p.getY()];
					Point pt = new Point(p.getX() - 1, p.getY());
					p.setLocation(pt);
					board[p.getX() + 1][p.getY()] = temp2;
				}
				moveCounter--;
				b.drawBoard();

			} else if (c == 'S' || c == 's' && b.canMoveSouth(p) == true) {
				getPlayerRoom(b, p);
				if (b.roomDoors.contains(board[p.getX() + 1][p.getY()])) {
					if (b.roomTiles.contains(board[p.getX() + 2][p.getY()]) && p.getRoom() == null) { // if entering
																										// room
						System.out.println("entering room");
						temp1 = board[p.getX()][p.getY()]; // Current player = temp1
						board[p.getX()][p.getY()] = '.'; // current position = '.'
						Point pt = new Point(p.getX() + 2, p.getY()); // new player position = inside room;
						p.setLocation(pt);
						getPlayerRoom(b, p); // sets current room to player.
						board[p.getX()][p.getY()] = temp1; // player is placed in new position
					} else if (board[p.getX() + 2][p.getY()] == '.' && p.getRoom() != null) { // If leaving the room
						System.out.println("leaving room");
						getPlayerRoom(b, p);
						temp1 = board[p.getX()][p.getY()]; // Current player = temp1
						board[p.getX()][p.getY()] = p.getRoom().getRoomTile(b); // Sets current position = 'rTile'
						Point pt = new Point(p.getX() + 2, p.getY()); // new player position = outside room;
						p.setLocation(pt);
						board[p.getX()][p.getY()] = temp1;
						getPlayerRoom(b, p);
					}
				} else {
					inRoom = false;
					temp2 = board[p.getX() + 1][p.getY()];
					board[p.getX() + 1][p.getY()] = board[p.getX()][p.getY()];
					Point pt = new Point(p.getX() + 1, p.getY());
					p.setLocation(pt);
					board[p.getX() - 1][p.getY()] = temp2;
				}
				moveCounter--;
				b.drawBoard();

			} else if (c == 'D' || c == 'd' && b.canMoveEast(p) == true) {
				if (b.roomDoors.contains(board[p.getX()][p.getY() + 1])) {
					if (b.roomTiles.contains(board[p.getX()][p.getY() + 2]) && p.getRoom() == null) { // if entering
																										// room
						System.out.println("entering room");
						temp1 = board[p.getX()][p.getY()]; // Current player = temp1
						board[p.getX()][p.getY()] = '.'; // current position = '.'
						Point pt = new Point(p.getX(), p.getY() + 2); // new player position = inside room;
						p.setLocation(pt);
						getPlayerRoom(b, p); // sets current room to player.
						board[p.getX()][p.getY()] = temp1; // player is placed in new position
					} else if (board[p.getX()][p.getY() + 2] == '.' && p.getRoom() != null) { // If leaving the room
						System.out.println("leaving room");
						getPlayerRoom(b, p);
						temp1 = board[p.getX()][p.getY()]; // Current player = temp1
						board[p.getX()][p.getY()] = p.getRoom().getRoomTile(b); // Sets current position = 'rTile'
						Point pt = new Point(p.getX(), p.getY() + 2); // new player position = outside room;
						p.setLocation(pt);
						board[p.getX()][p.getY()] = temp1;
						getPlayerRoom(b, p);
					}
				} else {
					inRoom = false;
					temp2 = board[p.getX()][p.getY() + 1];
					board[p.getX()][p.getY() + 1] = board[p.getX()][p.getY()];
					Point pt = new Point(p.getX(), p.getY() + 1);
					p.setLocation(pt);
					board[p.getX()][p.getY() - 1] = temp2;
				}
				moveCounter--;
				b.drawBoard();

			} else if (c == 'A' || c == 'a' && b.canMoveWest(p) == true) {
				if (b.roomDoors.contains(board[p.getX()][p.getY() - 1])) {
					if (b.roomTiles.contains(board[p.getX()][p.getY() - 2]) && p.getRoom() == null) { // if entering 
																										// room
						System.out.println("entering room");
						temp1 = board[p.getX()][p.getY()]; // Current player = temp1
						board[p.getX()][p.getY()] = '.'; // current position = '.'
						Point pt = new Point(p.getX(), p.getY() - 2); // new player position = inside room;
						p.setLocation(pt);
						getPlayerRoom(b, p); // sets current room to player.
						board[p.getX()][p.getY()] = temp1; // player is placed in new position
					} else if (board[p.getX()][p.getY() - 2] == '.' && p.getRoom() != null) { // If leaving the room
						System.out.println("leaving room");
						getPlayerRoom(b, p);
						temp1 = board[p.getX()][p.getY()]; // Current player = temp1
						board[p.getX()][p.getY()] = p.getRoom().getRoomTile(b); // Sets current position = 'rTile'
						Point pt = new Point(p.getX(), p.getY() - 2); // new player position = outside room;
						p.setLocation(pt);
						board[p.getX()][p.getY()] = temp1;
						getPlayerRoom(b, p);
					}
				} else {
					inRoom = false;
					temp2 = board[p.getX()][p.getY() - 1];
					board[p.getX()][p.getY() - 1] = board[p.getX()][p.getY()];
					Point pt = new Point(p.getX(), p.getY() - 1);
					p.setLocation(pt);
					board[p.getX()][p.getY() + 1] = temp2;
				}
				moveCounter--;
				b.drawBoard();

			} else if(c != 'Q' || c != 'q'){
				System.out.println("\nCan't move here, try again ");
			}
		}
		
	}

	private static void makeFinalDrawList() {
		for (Card c : listOfAllCards) {
			listOfCards.add(c);
		}

		Boolean a = true;
		for (int i = 0; a; i++) {
			if (listOfCards.get(i).getType().equals("Player")) {
				accusationList.add(listOfCards.get(i));
				listOfCards.remove(i);
				a = false;
			}

		}

		a = true;

		for (int i = 0; a; i++) {
			if (listOfCards.get(i).getType().equals("Weapon")) {
				accusationList.add(listOfCards.get(i));
				listOfCards.remove(i);
				a = false;
			}

		}
		a = true;
		for (int i = 0; a; i++) {
			if (listOfCards.get(i).getType().equals("Room")) {
				accusationList.add(listOfCards.get(i));
				listOfCards.remove(i);
				a = false;
			}

		}

	}

	/**
	 * First option when players turn
	 * @param p
	 * @return
	 */
	private static int firstOption(Player p) {
		int counter = 0;
		int num1 = 0;
		do {
			Scanner sc = new Scanner(System.in);
			if (counter != 0) {
				System.out.print("Please eneter a number 1 2,,3 or 4");
			} else {

				System.out.println("-----------------");
				System.out.println("1) Role the dice");
				if (p.getRoom() != null) {
					System.out.println("2) Make a suggestion");
				}
				if (p.Accusations > 0) {
					System.out.println("3) Make a Accusation");
				}
				System.out.println("4) Skip turn");
				System.out.println("-----------------");

				counter = 0;
				counter++;
			}
			while (!sc.hasNextInt()) {
				sc.next();
				System.out.print("Not a number Please write a number between 1, 3,4\n");
				// continue;
			}
			num1 = sc.nextInt();
			// sc.close();
		} while ((num1 > 4) || (num1 < 1));

		return num1;
	}

	/**
	 * Rolls the dice
	 * @return
	 */
	private static int rollDice() {
		int a, b, c;
		a = (int) (Math.random() * 6) + 1;
		b = (int) (Math.random() * 6) + 1;
		c = a + b;
		return c;

	}

	/**
	 * Once the dice is rolled choices are layed out.
	 * @param b
	 * @param p
	 * @return int
	 */
	private static int diceChoice(Board b, Player p) {
		int counter = 0;
		int num1 = 0;
		do {
			Scanner sc = new Scanner(System.in);
			if (counter != 0) {
				System.out.print("Please eneter a number between 1-4");
			} else {

				int dice = rollDice();
				dicerollNumber = dice;
				System.out.println("You rolled a " + dice);
				System.out.println("-----------------");
				System.out.println("1) Make a move");
				if (p.getRoom() != null) {
					System.out.println("2) Make a suggestion");
				}
				if (p.Accusations > 0) {
					System.out.println("3) Make a Accusation");
				}
				System.out.println("4) Skip turn");
				System.out.println("-----------------");

				counter = 0;
				counter++;
			}
			while (!sc.hasNextInt()) {
				sc.next();
				System.out.print("Not a number Please write a number between 1-4\n");
				// continue;
			}
			num1 = sc.nextInt();
		} while ((num1 > 4) || (num1 < 1));

		return num1;

	}



	/**
	 * Returns the room player is in and sets room in player to null or room
	 * @param b
	 * @param p 
	 * @return Room object
	 */
	private static Room getPlayerRoom(Board b, Player p) {
		for (Room r : b.getRooms().values()) {
			for (int i = 0; i < r.getRoomTiles().size(); i++) {
				if (p.getLocation().equals(r.getRoomTiles().get(i))) {
					p.setRoom(r);
					return r;
				} else {
					p.setRoom(null);
				}
			}
		}
		return null;

	}
	
	/**
	 * Make suggestion creates a list of suggestions to ask other players
	 * @param b
	 * @param p
	 */
	private static void makeSuggestion(Board b, Player p) {
		listOfSuggestions.clear();
		
		System.out.println("Here is a list of your cards: ");
		for (int i = 0; i < p.getPlayerCards().size(); i++) {
			System.out.print(p.getPlayerCards().get(i).getName() + ", ");
		}
		System.out.println("\n\nMake a suggestion. Choose one Weapon and one Player ");

		System.out.println("Weapons: ");
		for (int i = 0; i < 6; i++) {
			System.out.print(" " + listOfWeapons[i] + ", ");

		}
		Scanner sc;
		String tempWord;
		Boolean booleanTemp = true;
		int localCount = 0;

		do {
			if (localCount > 0) {
				System.out.println("No matches please type again");
			}
			sc = new Scanner(System.in);
			System.out.println("\n Enter a Weapon");
			tempWord = sc.nextLine();
			for (int i = 0; i < listOfCards.size(); i++) {
				if (listOfAllCards.get(i).getName().equalsIgnoreCase(tempWord)
						&& listOfAllCards.get(i).getType().equals("Weapon")) {
					listOfSuggestions.add(listOfAllCards.get(i));
					booleanTemp = false;
					break;
				}
			}
			localCount++;

		} while (booleanTemp);

		System.out.println("List of Players: ");
		for (int i = 0; i < numberOfPlayers; i++) {
			System.out.print(" " + b.getListOfPlayers().get(i).getName() + ", ");
		}

		String tempWord1;
		Boolean booleanTemp1 = true;
		int localCount1 = 0;
		do {
			if (localCount1 > 0) {
				System.out.println("No matches please try again");
			}
			sc = new Scanner(System.in);
			System.out.println("\n Enter a Player Name: ");
			tempWord1 = sc.nextLine();
			for (int i = 0; i < listOfCards.size(); i++) {
				if (listOfAllCards.get(i).getName().equalsIgnoreCase(tempWord1)
						&& listOfAllCards.get(i).getType().equals("Player")) {
					listOfSuggestions.add(listOfAllCards.get(i));
					booleanTemp1 = false;
					break;
				}
			}
			localCount1++;

		} while (booleanTemp1);
		for(int i = 0; i < listOfAllCards.size();i++) {
			if(p.getRoom().getName().equals(listOfAllCards.get(i).getName())) {
				listOfSuggestions.add(listOfAllCards.get(i));
				break;
			}
		}
		

	}

	/**
	 * collects accusations from players and checks to make sure it is true
	 * @param b
	 * @param p
	 * @return boolean
	 */
	private static Boolean makeAccusation(Board b, Player p) {
		System.out.println("\n Make a accusation: Choose a Player a Weapon and a Room");
		listOfSuggestions.clear();
		/*
		 * Players
		 */

		System.out.println("List of Players: ");
		for (int i = 0; i < numberOfPlayers; i++) {
			System.out.print(" " + b.getListOfPlayers().get(i).getName() + ", ");
		}
		Scanner sc;
		String tempWord1;
		Boolean booleanTemp1 = true;
		int localCount1 = 0;
		do {
			if (localCount1 > 0) {
				System.out.println("No matches please try again");
			}
			sc = new Scanner(System.in);
			System.out.println("\n Enter a Player Name: ");
			tempWord1 = sc.nextLine();
			for (int i = 0; i < listOfAllCards.size(); i++) {
				if (listOfAllCards.get(i).getName().equalsIgnoreCase(tempWord1)
						&& listOfAllCards.get(i).getType().equals("Player")) {
					listOfSuggestions.add(listOfAllCards.get(i));
					booleanTemp1 = false;
				}
			}
			localCount1++;

		} while (booleanTemp1);
		/*
		 * Weapons
		 */

		System.out.println("Weapons: ");
		for (int i = 0; i < 6; i++) {
			System.out.print(" " + listOfWeapons[i] + ", ");

		}

		String tempWord;
		Boolean booleanTemp = true;
		int localCount = 0;

		do {
			if (localCount > 0) {
				System.out.println("No matches please type again");
			}
			sc = new Scanner(System.in);
			System.out.println("\n Enter a Weapon");
			tempWord = sc.nextLine();
			for (int i = 0; i < listOfAllCards.size(); i++) {
				if (listOfAllCards.get(i).getName().equalsIgnoreCase(tempWord)
						&& listOfAllCards.get(i).getType().equals("Weapon")) {
					listOfSuggestions.add(listOfAllCards.get(i));
					booleanTemp = false;
				}
			}
			localCount++;

		} while (booleanTemp);

		System.out.println("Rooms: ");
		for (int i = 0; i < 6; i++) {
			System.out.print(" " + placeArray[i] + ", ");

		}

		String tempWord2;
		Boolean booleanTemp2 = true;
		int localCount2 = 0;

		do {
			if (localCount2 > 0) {
				System.out.println("No matches please type again");
			}
			sc = new Scanner(System.in);
			System.out.println("\n Enter a Room");
			tempWord2 = sc.nextLine();
			for (int i = 0; i < listOfAllCards.size(); i++) {
				if (listOfAllCards.get(i).getName().equalsIgnoreCase(tempWord2) && listOfAllCards.get(i).getType().equals("Room")) {
					listOfSuggestions.add(listOfAllCards.get(i));
					booleanTemp2 = false;
				}
			}
			localCount2++;

		} while (booleanTemp2);
		for(Card ca : listOfSuggestions) {
			System.out.println(ca.getName());
		}
		for (int k = 0; k < accusationList.size(); k++) {
			if (!accusationList.get(k).equals(listOfSuggestions.get(k))) {
				p.Accusations = 0;
				return false;
				
			}
		}
		return true;
	}

	/**
	 * Inits weapons and maps them to a room
	 * @param b
	 */
	private static void initWeapons(Board b) {
		ArrayList<Integer> usedNumber = new ArrayList<Integer>();
		for (int i = 0; i < placeArray.length; i++) {
			listOfPlaces.put(i, placeArray[i]);
		}
		ArrayList<Weapon> w = new ArrayList<>();
		for (int i = 0; i < listOfWeapons.length; i++) {
			w.add(new Weapon(i, listOfWeapons[i]));
		}
		for (int i = 0; i < listOfWeapons.length; i++) {
			int a = (int) (Math.random() * 9) + 0;
			// System.out.println(b.getRooms().get(a));
			if (!(usedNumber.contains(a))) {
				b.getRooms().get(listOfPlaces.get(a)).setWeapon(w.get(i));
				usedNumber.add(a);
				// System.out.println(b.getRooms().get(a));
			} else {
				i--;
			}
		}

		// System.out.println(b.getRooms().size());

	}



}
