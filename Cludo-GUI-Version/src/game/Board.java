package game;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

public class Board {
	private int boardRows = 25;
	private int boardCols = 24;
	public char gameBoard[][] = new char[boardRows][boardCols];
	public char drawBoard[][] = gameBoard;	
	public char defaultBoard[][] = new char[boardRows][boardCols];

	public HashMap<String, Room> mapsOfRooms = new HashMap<>();
	public ArrayList<Character> moveableTiles = new ArrayList<Character>();
	public ArrayList<Character>roomDoors = new ArrayList<Character>();
	public ArrayList<Character> roomTiles = new ArrayList<Character>();
	public ArrayList<Character> tiles = new ArrayList<Character>();
	public ArrayList<Character> startTiles = new ArrayList<Character>();
	private ArrayList<Player> listOfPlayers = new ArrayList<>();

	public ArrayList<Player> getListOfPlayers() {
		return listOfPlayers;
	}

	public void setListOfPlayers(ArrayList<Player> listOfPlayers) {
		this.listOfPlayers = listOfPlayers;
	}

	public HashMap<String, Room> getRooms() {
		return mapsOfRooms;
	}

	// Rooms
	char kitchen = 'k', ballRoom = 'b', conservatory = 'c', diningRoom = 'd', billardRoom = 'b', library = 'r',
			lounge = 'l', hall = 'h', study = 's', accusation = '+';

	// Room Doors - Unique identifiers for each room.
	char kitchenD = 'K', ballRoomD = 'B', conservatoryD = 'C', diningRoomD = 'D', billardRoomD = 'P', libraryD = 'R',
			loungeD = 'L', hallD = 'H', studyD = 'S', accusationD = 'A';

	// Start Positions

	char mrsWhite = '1', mrGreen = '2', mrsPeacock = '3', colMustard = '4', profPlum = '5', missScarlett = '6';

	// Game Tiles
	char blank = '.', door = 'D', wall = '*', roomWall = '*';

	public char[][] getGameBoard() {
		return gameBoard;
	}
	public char[][] getGameDrawBoard() {
		return drawBoard;
	}

	public void setGameBoard(char[][] gameBoard) {
		this.gameBoard = gameBoard;
	}

	public Board() {
	}

	public void initialiseBoard() {
		for (int i = 0; i < boardRows; i++) {
			for (int j = 0; j < boardCols; j++) {
				gameBoard[i][j] = blank;
			}
		}
	}

	public void drawActualBoard() { // draws the actual board of where each tile is.
		System.out.println("\n");
		for (int i = 0; i < boardRows; i++) {
			System.out.print("\n");
			for (int j = 0; j < boardCols; j++) {
				System.out.print(gameBoard[i][j]);
			}
		}
	}

	public void drawBoard() { // This method doesn't manipulate the board. Replaces outer room tiles with
		// walls(only for display)
		
		moveableTiles.add(blank);
		moveableTiles.add(ballRoomD);
		moveableTiles.add(conservatoryD);
		moveableTiles.add(diningRoomD);
		moveableTiles.add(billardRoomD);
		moveableTiles.add(libraryD);
		moveableTiles.add(hallD);
		moveableTiles.add(studyD);
		moveableTiles.add(accusationD);
		
		roomDoors.add(ballRoomD);
		roomDoors.add(conservatoryD);
		roomDoors.add(diningRoomD);
		roomDoors.add(billardRoomD);
		roomDoors.add(libraryD);
		roomDoors.add(hallD);
		roomDoors.add(studyD);
		roomDoors.add(accusationD);
		
		roomTiles.add(kitchen);
		roomTiles.add(ballRoom);
		roomTiles.add(conservatory);
		roomTiles.add(diningRoom);
		roomTiles.add(diningRoom);
		roomTiles.add(billardRoom);
		roomTiles.add(library);
		roomTiles.add(lounge);
		roomTiles.add(hall);
		roomTiles.add(study);

		tiles.add(blank);
		tiles.add(wall);
		tiles.add(accusation);

		startTiles.add(mrsWhite);
		startTiles.add(mrGreen);
		startTiles.add(mrsPeacock);
		startTiles.add(colMustard);
		startTiles.add(profPlum);
		startTiles.add(missScarlett);

		for (int i = 0; i < boardRows; i++) {
			for (int j = 0; j < boardCols; j++) {
				gameBoard[3][3] = (mapsOfRooms.get("Kitchen").getWeaponChar());
				gameBoard[4][12] = (mapsOfRooms.get("BallRoom").getWeaponChar());			
				gameBoard[2][21] = (mapsOfRooms.get("Conservatory").getWeaponChar());			
				gameBoard[12][3] = (mapsOfRooms.get("DiningRoom").getWeaponChar());			
				gameBoard[10][21] = (mapsOfRooms.get("BillardRoom").getWeaponChar());			
				gameBoard[16][21] = (mapsOfRooms.get("Library").getWeaponChar());				
				gameBoard[22][3] = (mapsOfRooms.get("Lounge").getWeaponChar());				
				gameBoard[21][11] = (mapsOfRooms.get("Hall").getWeaponChar());				
				gameBoard[22][21] = (mapsOfRooms.get("Study").getWeaponChar());
			}
			
		}
		
		
	/*	for (int i = 0; i < boardRows; i++) {
			System.out.print("\n");
			for (int j = 0; j < boardCols; j++) {
				if (i == 1 && roomTiles.contains(drawBoard[i][j])) {
					System.out.print(roomWall);
				} else if (i < 23 && roomTiles.contains(drawBoard[i][j]) && tiles.contains(drawBoard[i + 1][j])
						) {
					System.out.print(roomWall);
				} else if (roomTiles.contains(drawBoard[i][j]) && tiles.contains(drawBoard[i - 1][j])
						) {
					System.out.print(roomWall);
				} else if (j < 23 && roomTiles.contains(drawBoard[i][j]) && tiles.contains(drawBoard[i][j + 1])
						) {
					System.out.print(roomWall);
				} else if (j > 0 && roomTiles.contains(drawBoard[i][j]) && tiles.contains(drawBoard[i][j - 1])
						) {
					System.out.print(roomWall);
				} else if ((j == 0 || j == 23 || i == 24) && roomTiles.contains(drawBoard[i][j])) {
					System.out.print(roomWall);
				} else if (roomTiles.contains(gameBoard[i][j])) {
					System.out.print(' ');
				} else {
					System.out.print(drawBoard[i][j]);
				}
			}
		}*/

	}
	
	public void setWeaponLocations() {
		
		for(Room r : mapsOfRooms.values()) {
			if(r.getWeapon() != null) { // if the room has a weapon
				for(int i = 0; i < boardRows; i++) { //search through the board for weapon location
					for(int j = 0; j < boardCols; j++) {
						if(gameBoard[i][j] == r.getWeaponChar()) {
							Weapon w = r.getWeapon();
							w.setLocation(new Point(i,j));
						}
					}
				}
			}
		}
		
	}

	public void setupBoard() {

		// Kitchen = 6*6, StartPos = (1,0), Wall at (6,0), Door at (6,4)
		for (int i = 1; i < 7; i++) {
			for (int j = 0; j < 6; j++) {
				if (i == 6 && j == 0) {
					gameBoard[i][j] = wall;
				} else if (i == 6 && j == 4) {
					gameBoard[i][j] = kitchenD;
				} else {
					gameBoard[i][j] = kitchen;
				}
			}
		}

		// Ball Room = 7*8, StartPos = (1,8), Pathway at (1,8), (1,9), (1,14) (1,15),
		// Door at (5, 8), (7, 9), (10, 14), (7, 15)
		for (int i = 1; i < 8; i++) {
			for (int j = 8; j < 16; j++) {
				if (i == 1 && j == 8 || i == 1 && j == 9 || i == 1 && j == 14 || i == 1 && j == 15) {
					gameBoard[i][j] = blank;
				} else if (i == 5 && j == 8 || i == 5 && j == 15 || i == 7 && j == 9 || i == 7 && j == 14) {
					gameBoard[i][j] = ballRoomD;
				} else {
					gameBoard[i][j] = ballRoom;
				}
			}
		}

		// Conservatory = 5*6, StartPos = (1,18), Pathway at (5,18), (0,9), (0,14)
		// (0,15), Door at (4, 8), (6, 9), (9, 14), (6, 15)
		for (int i = 1; i < 6; i++) {
			for (int j = 18; j < 24; j++) {
				if (i == 5 && j == 18) {
					gameBoard[i][j] = blank;
				} else if (i == 5 && j == 23) {
					gameBoard[i][j] = wall;
				} else if (i == 4 && j == 18) {
					gameBoard[i][j] = conservatoryD;
				} else {
					gameBoard[i][j] = conservatory;
				}
			}
		}

		// Dining Room = 7*8, StartPos = (9,0)
		for (int i = 9; i < 16; i++) {
			for (int j = 0; j < 8; j++) {
				if (i == 9 && j == 5 || i == 9 && j == 6 || i == 9 && j == 7) {
					gameBoard[i][j] = blank;
				} else if (i == 12 && j == 7 || i == 15 && j == 6) {
					gameBoard[i][j] = diningRoomD;
				} else {
					gameBoard[i][j] = diningRoom;
				}
			}
		}

		// Center = 7*5, StartPos = (10, 10)
		for (int i = 10; i < 17; i++) {
			for (int j = 10; j < 15; j++) {
				if (i == 16 && j == 12) {
					gameBoard[i][j] = accusationD;
				} else {
					gameBoard[i][j] = accusation;
				}
			}
		}

		// Billard Room = 5*6, StartPos = (8,18)
		for (int i = 8; i < 13; i++) {
			for (int j = 18; j < 24; j++) {
				if (i == 9 && j == 18 || i == 12 && j == 22) {
					gameBoard[i][j] = billardRoomD;
				} else {
					gameBoard[i][j] = billardRoom;
				}
			}
		}

		// Library = 5*7, StartPos = (14,17)
		for (int i = 14; i < 19; i++) {
			for (int j = 17; j < 24; j++) {
				if (i == 14 && j == 17 || i == 18 && j == 17) {
					gameBoard[i][j] = blank;
				} else if (i == 14 && j == 20) {
					gameBoard[i][j] = libraryD;
				} else if (i == 14 && j == 23 || i == 18 && j == 23) {
					gameBoard[i][j] = wall;
				} else {
					gameBoard[i][j] = library;
				}
			}
		}

		// Lounge = 6*7, StartPos = (19,0)
		for (int i = 19; i < 25; i++) {
			for (int j = 0; j < 7; j++) {
				if (i == 19 && j == 6) {
					gameBoard[i][j] = loungeD;
				} else if (i == 24 && j == 6) {
					gameBoard[i][j] = wall;
				} else {
					gameBoard[i][j] = lounge;
				}
			}
		}

		// Hall = 7*6, StartPos = (18,9)
		for (int i = 18; i < 25; i++) {
			for (int j = 9; j < 15; j++) {
				if (i == 18 && j == 11 || i == 18 && j == 12 || i == 20 && j == 14) {
					gameBoard[i][j] = hallD;
				} else {
					gameBoard[i][j] = hall;
				}
			}
		}

		// Study = 4*7, StartPos = (21,17)
		for (int i = 21; i < 25; i++) {
			for (int j = 17; j < 24; j++) {
				if (i == 21 && j == 17) {
					gameBoard[i][j] = studyD;
				} else if (i == 24 && j == 17) {
					gameBoard[i][j] = wall;
				} else {

					gameBoard[i][j] = study;
				}
			}
		}

		// Start positions and walls
		for (int i = 0; i < boardRows; i++) {
			for (int j = 0; j < boardCols; j++) {
				if (i == 0 && j == 9) {
					gameBoard[i][j] = mrsWhite;
				} else if (i == 0 && j == 14) {
					gameBoard[i][j] = mrGreen;
				} else if (i == 6 && j == 23) {
					gameBoard[i][j] = mrsPeacock;
				} else if (i == 17 && j == 0) {
					gameBoard[i][j] = colMustard;
				} else if (i == 19 && j == 23) {
					gameBoard[i][j] = profPlum;
				} else if (i == 24 && j == 7) {
					gameBoard[i][j] = missScarlett;
				}
				else if(i == 0) {
					gameBoard[i][j] = wall;
				}

				else if (i == 24 && j == 8 || i == 24 && j == 15 || i == 8 && j == 0 || i == 16 && j == 0
						|| i == 18 && j == 0 || i == 7 && j == 23 || i == 13 && j == 23 || i == 14 && j == 23
						|| i == 18 && j == 23 || i == 20 && j == 23) {
					gameBoard[i][j] = wall;
				}
			}
		}
	}

	public void setupRooms() {
		mapsOfRooms.put("Kitchen", new Room("Kitchen"));
		mapsOfRooms.put("BallRoom", new Room("BallRoom"));
		mapsOfRooms.put("Conservatory", new Room("Conservatory"));
		mapsOfRooms.put("DiningRoom", new Room("DiningRoom"));
		mapsOfRooms.put("BillardRoom", new Room("BillardRoom"));
		mapsOfRooms.put("Library", new Room("Library"));
		mapsOfRooms.put("Lounge", new Room("Lounge"));
		mapsOfRooms.put("Hall", new Room("Hall"));
		mapsOfRooms.put("Study", new Room("Study"));
		mapsOfRooms.put("Accusation", new Room("Accusation"));

		for (int i = 0; i < boardRows; i++) {
			for (int j = 0; j < boardCols; j++) {
				if (gameBoard[i][j] == kitchen) {

					Point tilePt = new Point(i, j);
					mapsOfRooms.get("Kitchen").getRoomTiles().add(tilePt);

					if (gameBoard[i][j] == kitchenD) {

						Point doorPt = new Point(i, j);
						mapsOfRooms.get("Kitchen").getRoomDoors().add(doorPt);

					}

				} else if (gameBoard[i][j] == ballRoom) {

					Point tilePt = new Point(i, j);
					mapsOfRooms.get("BallRoom").getRoomTiles().add(tilePt);
					if (gameBoard[i][j] == ballRoomD) {

						Point doorPt = new Point(i, j);
						mapsOfRooms.get("BallRoom").getRoomDoors().add(doorPt);
					}

				} else if (gameBoard[i][j] == conservatory) {

					Point tilePt = new Point(i, j);
					mapsOfRooms.get("Conservatory").getRoomTiles().add(tilePt);
					if (gameBoard[i][j] == conservatoryD) {

						Point doorPt = new Point(i, j);
						mapsOfRooms.get("Conservatory").getRoomDoors().add(doorPt);
					}

				} else if (gameBoard[i][j] == diningRoom) {

					Point tilePt = new Point(i, j);
					mapsOfRooms.get("DiningRoom").getRoomTiles().add(tilePt);
					if (gameBoard[i][j] == diningRoomD) {

						Point doorPt = new Point(i, j);
						mapsOfRooms.get("DiningRoom").getRoomDoors().add(doorPt);
					}

				} else if (gameBoard[i][j] == billardRoom) {

					Point tilePt = new Point(i, j);
					mapsOfRooms.get("BillardRoom").getRoomTiles().add(tilePt);
					if (gameBoard[i][j] == billardRoomD) {

						Point doorPt = new Point(i, j);
						mapsOfRooms.get("BillardRoom").getRoomDoors().add(doorPt);
					}

				} else if (gameBoard[i][j] == library) {
					Point tilePt = new Point(i, j);
					mapsOfRooms.get("Library").getRoomTiles().add(tilePt);
					if (gameBoard[i][j] == libraryD) {
						Point doorPt = new Point(i, j);
						mapsOfRooms.get("Library").getRoomDoors().add(doorPt);
					}
				} else if (gameBoard[i][j] == lounge) {
					Point tilePt = new Point(i, j);
					mapsOfRooms.get("Lounge").getRoomTiles().add(tilePt);
					if (gameBoard[i][j] == loungeD) {
						Point doorPt = new Point(i, j);
						mapsOfRooms.get("Lounge").getRoomDoors().add(doorPt);
					}
				} else if (gameBoard[i][j] == hall) {
					Point tilePt = new Point(i, j);
					mapsOfRooms.get("Hall").getRoomTiles().add(tilePt);
					if (gameBoard[i][j] == hallD) {
						Point doorPt = new Point(i, j);
						mapsOfRooms.get("Hall").getRoomDoors().add(doorPt);
					}
				} else if (gameBoard[i][j] == study) {
					Point tilePt = new Point(i, j);
					mapsOfRooms.get("Study").getRoomTiles().add(tilePt);
					if (gameBoard[i][j] == studyD) {
						Point doorPt = new Point(i, j);
						mapsOfRooms.get("Study").getRoomDoors().add(doorPt);
					}
				} else if (gameBoard[i][j] == accusation) {
					Point tilePt = new Point(i, j);
					mapsOfRooms.get("Accusation").getRoomTiles().add(tilePt);
					if (gameBoard[i][j] == accusationD) {
						Point doorPt = new Point(i, j);
						mapsOfRooms.get("Accusation").getRoomDoors().add(doorPt);
					}

				}
			}
		}
		
		
		
		for(int i=0; i<gameBoard.length; i++) {
			  for(int j=0; j<gameBoard[i].length; j++) {
			    defaultBoard[i][j]=gameBoard[i][j];
			  }
			   }
	}


	public void setupPlayer(String playerName, String characterName) { //assigns random character
		
				for (int i = 0; i < boardRows; i++) {
					for (int j = 0; j < boardCols; j++) {
						if (gameBoard[i][j] == mrsWhite && characterName == "mrsWhite") {
							Point pt = new Point(i, j);
							Player p1 = new Player(playerName, pt, characterName);
							listOfPlayers.add(p1);

						} else if (gameBoard[i][j] == mrGreen  && characterName == "mrGreen") {
							Point pt = new Point(i, j);
							Player p2 = new Player(playerName, pt, characterName);
							listOfPlayers.add(p2);
						} else if (gameBoard[i][j] == mrsPeacock  && characterName == "mrsPeacock") {
							Point pt = new Point(i, j);
							Player p3 = new Player(playerName, pt, characterName);
							listOfPlayers.add(p3);
						} else if (gameBoard[i][j] == colMustard  && characterName == "colMustard") {
							Point pt = new Point(i, j);
							Player p4 = new Player(playerName, pt, characterName);
							listOfPlayers.add(p4);

						} else if (gameBoard[i][j] == profPlum  && characterName == "profPlum") {
							Point pt = new Point(i, j);
							Player p5 = new Player(playerName, pt, characterName);
							listOfPlayers.add(p5);

						} else if (gameBoard[i][j] == missScarlett  && characterName == "missScarlett") {
							Point pt = new Point(i, j);
							Player p6 = new Player(playerName, pt, characterName);
							listOfPlayers.add(p6);

						}
					}

				}
			}

	public void removePlayerFromBoard(Player p) {
		int playerX = p.getX();
		int playerY = p.getY();
		gameBoard[playerX][playerY] = defaultBoard[playerX][playerY];
		
		
	}

	public boolean canMoveNorth(Player p) {
		int x = p.getX();
		int y = p.getY();
		if (x-1 < 0 || !(moveableTiles.contains(gameBoard[x - 1][y]))) {
			return false;
		} else {
			return true;
		}
	}
	public boolean canMoveSouth(Player p) {
		int x = p.getX();
		int y = p.getY();
		if (x+1 > 24 || !(moveableTiles.contains(gameBoard[x + 1][y]))) {
			return false;
		} else {
			return true;
		}
	}
	public boolean canMoveEast(Player p) {
		int x = p.getX();
		int y = p.getY();
		if (y+1 > 23 || !(moveableTiles.contains(gameBoard[x][y + 1]))) {
			return false;
		} else {
			return true;
		}
	}
	public boolean canMoveWest(Player p) {
		int x = p.getX();
		int y = p.getY();
		if (y-1 < 0 || !(moveableTiles.contains(gameBoard[x][y - 1]))) {
			return false;
		} else {
			return true;
		}
	}
	

}