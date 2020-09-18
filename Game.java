// Ben Yuly				11/6/2010
// version 0.1

import java.util.Scanner; // For user input
import java.util.Random; // for random number generation
import java.util.InputMismatchException; // for wrong user input


public class Game
{

private static final String VERSION = "0.1"; // version
private static Scanner input = new Scanner(System.in); // for user input
private static int money = 100000; // initial amount of money
private static int factories = 1; // initial number of factories
private static int inventory = 0; // initial number of product in inventory
private static int price = 5; // initial price of product
private static int demand = 1; // initial consumer demand for product			Note: 100 is added to demand when calculated
private static int turn = 0; // initial turn number (is incremented at beginning of each turn)
private static String playername; // player's name
private static String companyname; // player's company's name
private static String product; // player's company's product's name

	public static void main( String[] args ) // This method calls game's methods
	{
	welcomeMessage();
	gameSetup();

		for( int counter = 0; counter <= 999999; counter++)
		turn();
	}


	public static void welcomeMessage() // displays welcome message to the program and asks user if he wants instructions.
	{

	System.out.printf("Welcome to Manufacturing Tycoon v %s", VERSION );

	System.out.println();

	}


	public static void gameSetup()  // initializes playername, companyname, and product. Also displays a
					// welcome message welcoming the user to the game.
	{

	System.out.print("Game Setup:\nPlease enter your name here: "); // inputs user's name
	playername = input.nextLine();

	System.out.print("Please enter your company's name here: "); // inputs user's company's name
	companyname = input.nextLine();

	System.out.print("Please enter the name of the product your company produces here: "); // inputs the name of the product which the company produces
	product = input.nextLine();

	System.out.printf("\n\nHello, %s! Welcome to Manufacturing Tycoon version %s.\nYou have been hired as the manager of %s, which produces %s.\n\n", playername, VERSION, companyname, product ); // outputs a personalized welcome message
	}


	public static void turn() // turn logic
	{

	int activefactories = 0; // initializes activefactories
	int buildfactories = 0; // initializes buildfactories
	int advertising = 0; // initializes advertising (the amount of money spent on advertising this turn)
	int productstosell = 0; // initializes productstosell (the number of products from inventory to sell this turn)


	turn++; // increments turn counter


	String stringtoprint = String.format("\nTurn: %d\n%sMoney: %d\nFactories: %d\nInventory: %d", turn, "Assets:\n", money, factories, inventory );
	System.out.println(stringtoprint); // outputs this turn's information


	// inputs and validates the number of factories to run this turn
	System.out.print("\nHow many factories do you want to run this turn (factories produce 100 units each; each unit costs $5 to build)?");
	activefactories = input.nextInt();

		while (activefactories > factories)
		{
		System.out.print("\nYou can't run more factories than you have!!! Please try again: ");
		activefactories = input.nextInt();
		}


	// inputs the number of factories to build this turn
	System.out.print("\nHow many factories do you want to build this turn (factories cost $10000 each)? ");
	buildfactories = input.nextInt();


	// inputs the amount of money to spend on advertising
	System.out.print("\nHow much money do you want to spend on advertising this turn? ");
	advertising = input.nextInt(); 


	// inputs the price of the product
	System.out.print("\nWhat do you want the price of your product to be this turn (they cost $5 each to produce)? ");
	price = input.nextInt();

		while(price == 0)
		{
		System.out.print("You cannot set the price of your product to $0! Please try again: ");
		price = input.nextInt();
		}


	// inputs and validates the number of units from the inventory to sell this turn
	String promptstring = String.format("\nHow many units of your product (%s) in your inventory do you want to sell this turn? ", product);
	System.out.print(promptstring);
	productstosell = input.nextInt();

		while (productstosell > inventory)
		{
		System.out.print("You cannot sell more products than are in your inventory! Please try again: ");
		productstosell = input.nextInt(); 
		}



	// Calculate Turn Summary
	int moneyold = money; // moneyold is amount of money before expenses
	int expenses = ((activefactories * 100) * 5) + (buildfactories * 10000) + (advertising);
	money = money - expenses;


	// calculates demand
	Random randomgenerator = new Random();
	double fluctuations = (double) randomgenerator.nextInt(demand) - ( (double) demand/2 );
	System.out.printf("Fluctuations: %f ", fluctuations);


	//			     This demand means demand from last turn (the value of variable demand has not changed yet)
	//				      |
	double calculateddemand = (double) demand + ( (double) advertising/(price) ) + fluctuations;
	// note: fluctuations could be positive or negative.

		if (calculateddemand < 0.0) // if calculated demand is less than 0, then set it to 2.
		calculateddemand = 2;

		System.out.printf("calculateddemand: %f ", calculateddemand);


	demand = (int) calculateddemand;
	System.out.printf("Demand: %d\n", demand);

	// updates inventory for new units produced
	inventory = inventory + (activefactories * 100);

	// updates number of factories for new factories built
	factories = buildfactories + factories;


	// calculates revenue
	int revenue = 0;
	int productssold = 0;
		if (productstosell >= demand)
		{
		inventory = inventory - demand;
		revenue =  demand * price;
		productssold = demand;
		demand = 0; // everyone who wanted a product got one. Demand is recalculated next turn.
		}

		if (productstosell < demand)
		{
		inventory = inventory - productstosell;
		revenue = productstosell * price;
		productssold = productstosell;

		// update demand (demand this turn was not met, thus people are still waiting for their product)
		demand = demand + (demand - productstosell);
		}


	money = money + revenue; // calculate new money

		if( money < 0)
		EndGameBankrupt();	


	// turn summary
	stringtoprint = String.format("\nTurn (%d) Summary:\nExpenses: %d\nProducts sold: %d\nRevenue: %d\nMoney (old): %d\nMoney (new): %d\n", turn, expenses, productssold, revenue, moneyold, money);
	System.out.println(stringtoprint);
	}

	public static void EndGameBankrupt()
	{
	System.out.printf("\nYou have spent all of your investor's money, and now have $%d!\n", money);
	System.out.println("Your investors are demanding a return for their money! Having nothing to");
	System.out.println("give them, they fire you and you never get a job again. Have fun");
	System.out.println("dumpster diving in the slums of NYC!");
	}

}
