package game;

import java.util.Arrays;
import java.util.List;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.FancyGroundFactory;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.World;

/**
 * The main class for the Jurassic World game.
 *
 */
public class Application {

	public static void main(String[] args) {
		World world = new World(new Display());

		FancyGroundFactory groundFactory = new FancyGroundFactory(new Dirt(), new Wall(), new Floor(), new Tree(), new VendingMachine());
		
		List<String> map = Arrays.asList(
		"................................................................................",
		"................................................................................",
		".....#######..X.................................................................",
		".....#_____#....................................................................",
		".....#_____#....................................................................",
		".....###.###....................................................................",
		"................................................................................",
		"......................................+++.......................................",
		".......................................++++.....................................",
		"...................................+++++........................................",
		".....................................++++++.....................................",
		"......................................+++.......................................",
		".....................................+++........................................",
		"................................................................................",
		"............+++.................................................................",
		".............+++++..............................................................",
		"...............++........................................+++++..................",
		".............+++....................................++++++++....................",
		"............+++.......................................+++.......................",
		"................................................................................",
		".........................................................................++.....",
		"........................................................................++.++...",
		".........................................................................++++...",
		"..........................................................................++....",
		"................................................................................");
		GameMap gameMap = new GameMap(groundFactory, map );
		world.addGameMap(gameMap);
		
		Actor player = new Player("Player", '@', 100);

		// Tests created to manually test my portion of the project:

//		 Add a fruit to inventory to test if dropping it causes hungry stegosaurs to follow it and can try to feed it:
//		 player.addItemToInventory(new Fruit());
//		 player.addItemToInventory(new VegetarianMealKit());

//		 Add an allosaur egg to inventory, to see egg hatching and growing into an adult
//		 and looking for meat or eggs when hungry:
//		 player.addItemToInventory(new AllosaurEgg());

//		 Add a carnivore meal kit in map to test feeding allosaurs:
//		 player.addItemToInventory (new CarnivoreMealKit());


//		 Down casting the instance of Actor to player and increase eco points to test buying items from vending machine:
//		Player p = (Player) player;
//		p.increaseEcoPoints(1000);


		world.addPlayer(player, gameMap.at(9, 4));
		
		// Place a pair of stegosaurs in the middle of the map
		gameMap.at(30, 12).addActor(new Stegosaur("Stegosaur A (M)", Sex.Male, AgeGroup.ADULT));
		gameMap.at(31, 12).addActor(new Stegosaur("Stegosaur B (F)", Sex.Female, AgeGroup.ADULT));
		// place a herd of brachiosaurs in the map
		gameMap.at(35, 14).addActor(new Brachiosaur("Brachiosaur A (M)", Sex.Male, AgeGroup.ADULT));
		gameMap.at(33, 14).addActor(new Brachiosaur("Brachiosaur B (F)", Sex.Female, AgeGroup.ADULT));
		gameMap.at(35, 15).addActor(new Brachiosaur("Brachiosaur C (M)", Sex.Male, AgeGroup.ADULT));
		gameMap.at(33, 15).addActor(new Brachiosaur("Brachiosaur D (F)", Sex.Female, AgeGroup.ADULT));



		// Add lakes around the map
		gameMap.at(14, 20).setGround(new Lake());
		gameMap.at(15, 20).setGround(new Lake());
		gameMap.at(14, 21).setGround(new Lake());
		gameMap.at(9, 8).setGround(new Lake());
		gameMap.at(8, 8).setGround(new Lake());
		gameMap.at(8, 9).setGround(new Lake());
		gameMap.at(9, 9).setGround(new Lake());
		gameMap.at(60, 11).setGround(new Lake());
		gameMap.at(61, 11).setGround(new Lake());
		gameMap.at(60, 12).setGround(new Lake());
		gameMap.at(61, 12).setGround(new Lake());
		gameMap.at(60, 13).setGround(new Lake());
		gameMap.at(61, 13).setGround(new Lake());




		world.run();
	}
}
