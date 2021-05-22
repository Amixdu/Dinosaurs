package game;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import edu.monash.fit2099.engine.*;

/**
 * The main class for the Jurassic World game.
 * @author Abhishek Shrestha
 * @author Amindu Kaushal Kumarasinghe
 */
public class Application {

	/**
	 * more sophisticated game driver
	 * menu to choose challenge mode or sandbox mode
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		boolean quit = false;
		while (!quit) {
			// initial menu
			System.out.println("\n----------------------");
			System.out.println("Jurassic World Game");
			System.out.println("----------------------");
			System.out.println("What would like to do?");
			System.out.println("1. Play Sandbox Mode");
			System.out.println("2. Play Challenge Mode");
			System.out.println("3. Quit");
			System.out.print("Choose option (1/2/3): ");
			try {
				int choice = Integer.parseInt(scanner.nextLine());
				switch (choice) {
					case 1 -> {
						// Sandbox mode
						System.out.println("Starting SandBox Mode\n");
						runGame(false, 0, 0);
					}
					case 2 -> {
						// challenge mode
						System.out.print("Number of moves (non negative) to win: ");
						int moves = Integer.parseInt(scanner.nextLine());
						System.out.print("Number of EcoPoints (non negative) to win: ");
						int target = Integer.parseInt(scanner.nextLine());
						if (moves > 0 && target > 0) {
							System.out.println("Starting Challenge Mode\n");
							runGame(true, moves, target);
						} else {
							System.out.println("Moves and ecopoints should be non negative. Please Try again");
						}
					}
					case 3 -> quit = true;
				}
			} catch (NumberFormatException e){
				System.out.println("invalid input");
			} catch (Exception e){
				System.out.println(e.getMessage());
			}
		}
		System.out.println("Thank you for trying Jurassic World Game!");
	}

	/**
	 * Initializes the maps, adds actors to maps and runs the game
	 * connects the 2 maps and allows actors to traverse both maps (park2 is north of park1)
	 * @param challengeMode
	 * @param targetMoves
	 * @param targetEcoPoints
	 */
	private static void runGame(boolean challengeMode, int targetMoves, int targetEcoPoints){
		World world = new World(new Display());

		FancyGroundFactory groundFactory = new FancyGroundFactory(new Dirt(), new Wall(),
				new Floor(), new Tree(), new VendingMachine(), new Lake());

		List<String> map = Arrays.asList(
				"..............................................+++......................~~~~~....",
				"..................................................++++++........................",
				".....#######..X................++++++++++.......................................",
				".....#_____#..................+++++++++++++++...................................",
				".....#_____#..............................++++........................~~~~~~~~~~",
				".....###.###........+++...........................................~~~~~~~~~~~~~~",
				"......................++..........................................~~~~~~~~~~~~~~",
				"......................................+++............................~~~~~~~~~~~",
				"........~~~~~~...............................++++....................~~~~~~~~~~~",
				"........~~~~~~...........................+++++..................................",
				"......~~~~~~.............................++++++.................................",
				".....~~~~...............................+++.....................................",
				".....................................+++++++++..................................",
				".........................................++++..................~~~~~~...........",
				"............+++........................+++++...................~~~~~~...........",
				".............+++++..................~~~~~~~~~~..................................",
				".......++++........++...................~~~~~~~~~~.....................+++++....",
				".............+++...............~~~~~~~~~~~~~~~.....................++++++++.....",
				"............+++++++................~~~~~~~~~~~~..........................+++....",
				"................................................................................",
				"....++....................~~~~~~~.....................+++.....................++",
				"....+..................~~~~~~~~~.....................+++++................++..++",
				"............++........~~~~~~~..........................+...............++++.....",
				"...........+++..............................................................++++",
				"...........++++.................................................................");
		ParkGameMap park1 = new ParkGameMap(groundFactory, map );
		world.addGameMap(park1);

		Player player = new Player("Player", '@', 100);

		// new player position
		world.addPlayer(player, park1.at(9,1));

		// Place a pair of stegosaurs in the middle of the map
		park1.at(30, 12).addActor(new Stegosaur("Stegosaur A (M)", Sex.Male, AgeGroup.ADULT));
		park1.at(31, 13).addActor(new Stegosaur("Stegosaur B (F)", Sex.Female, AgeGroup.ADULT));
		// place a herd of brachiosaurs in the map
		park1.at(24, 14).addActor(new Brachiosaur("Brachiosaur A (M)", Sex.Male, AgeGroup.ADULT));
		park1.at(25, 16).addActor(new Brachiosaur("Brachiosaur B (F)", Sex.Female, AgeGroup.ADULT));
		park1.at(24, 15).addActor(new Brachiosaur("Brachiosaur C (M)", Sex.Male, AgeGroup.ADULT));
		park1.at(26, 17).addActor(new Brachiosaur("Brachiosaur D (F)", Sex.Female, AgeGroup.ADULT));


		// add second GameMap map2 as park2
		List<String> map2 = Arrays.asList(
				"~~~~~~~~~~~~................................+++++++++++++++++++++++++...........",
				"...~~~~~~~~~~~~~............................................++++++++++++++++....",
				".....#######~~~~~~~~~~..................................+++++++++++++++++.......",
				".....#_____#......++++++++++++................................+++++++++++++.....",
				".....#_____#.........+++++......................................................",
				"......................#______________#..................................._______",
				"......................#______________#..................................._______",
				".......................##############...............+++.........................",
				"~~~~~~~~~~~~~......................##############................++++...........",
				"~~~~~~~~~~.......................##############...........+++++.................",
				"~~~~~~~~~~.......................####~~~~~~~~~~....++++++...~~~~~~~~~~~~........",
				"~~~~~~~~~~~.......................####~~~~~~~~~~....++....~~~~~~~~~~~~~~~~~.....",
				".......................####~~~~~~~~~~..............+++............~~~~~~~~~~~~~~",
				".......................####~~~~~~~~~~............................~~~~~~~~~~~~~~~",
				"............+++........####~~~~~~~~~~.............+++++++++++...................",
				".............++++++++++++++.......................+++++++++++++++...............",
				"...............++++++++++++...................................+++++++++.........",
				".............++++++++++++++....................................++++++++.........",
				"#######.........+++++++~~~~~~~~~~~~~~~~~########.........+++....................",
				"______#..................~~~~~~~~~~~~~~~~~~.................#######.............",
				"______#...............~~~~~~~~~~~~~~~~~~~~.......................########..+++..",
				"______#.................~~~~~~~~~~~~~~~~~~~.......................++....++.++...",
				"______#.........................~~~~~~~~~~...............................++++...",
				"X.......................~~~~~~~..........................++..........#######....",
				"...........................~~~~~~~~~~~~~~~~~~~~.................................");

		ParkGameMap park2 = new ParkGameMap(groundFactory,map2);
		world.addGameMap(park2);

		// index of last row for second map
		int bottomRow = map2.size() - 1;
		// index of first row for first map
		int topRow = 0;
		for (int x : park2.getXRange()){
			Location map1Location = park1.at(x, topRow);
			Location map2Location = park2.at(x, bottomRow);

			// add exit from top row of map1 to bottom row of map2
			park2.addExitFromHereToAnotherMap(map2Location, x, topRow, "South to Park 1", "2", park1);
			park1.addExitFromHereToAnotherMap(map1Location,x, bottomRow, "North to Park 2", "8", park2);
		}

		// reset ecoPoints for player
		Player.reduceEcoPoints(player.getEcoPoints());

		if (challengeMode){
			player.addCapability(GameMode.CHALLENGE);
			player.setTargetEcoPoints(targetEcoPoints);
			player.setTargetMoves(targetMoves);
		} else{
			player.addCapability(GameMode.SANDBOX);
		}
		world.run();
	}
}
