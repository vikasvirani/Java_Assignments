
// Import java.util.Scanner package to read input from user
import java.util.Scanner;

/* LandVilleMain class
 * 		Creates the main display component of landVille game.
 * 
 * 		Contains command line menu driven code to initialize the game & take different inputs from use & call internal methods to perform operations.
 * 		methods - 
 * 			main(String[] args)  
 */
public class LandVilleMain {

	public static void main(String[] args) {

		// Integer variables to store rows,coulmns & user input
		int row, column, input = 0;

		// Scanner object for input
		Scanner sc = new Scanner(System.in);

		// User input for number of rows of the land
		System.out.println("Enter number of rows of the land:");
		row = sc.nextInt();

		// Show an error and ask for value again if number of rows are not between 1 to
		// 10
		while (row <= 0 | row > 10) {
			System.out.println("Row should be greater than 0 and less than or equal to 10");

			System.out.println("Enter number of rows of the land:");
			row = sc.nextInt();
		}

		// User input for number of columns of the land
		System.out.println("Enter number of columns of the land:");
		column = sc.nextInt();

		// Show an error and ask for value again if number of columns are not between 1
		// to 10
		while (column <= 0 | column > 10) {
			System.out.println("Column should be greater than 0 and less than or equal to 10");

			System.out.println("Enter number of columns of the land:");
			column = sc.nextInt();
		}

		// Create an object of Landville class and initialize using its constructor
		LandVille land = new LandVille(row, column);

		// Build a loop to display the menu, prompt for input and process it as per
		// requirements.
		while (input != 4) {
			// if entered value is not between 1 & 4, ask user to try again
			do {
				System.out.println("Choose from the menu: 1. Build a house 2. Display land 3. Clear the land 4. Quit");
				input = sc.nextInt();
			} while (input < 1 | input > 4);

			// if user has entered valid inputs, call appropriate method from Landville
			// class to perform operation user requested
			switch (input) {

			case 1:
				int row_house, column_house;

				// check if house already exists
				if (land.hasHouse == true) {
					System.out.println("House Already Exists!!!");
					continue;
				}

				System.out.println("Enter row number of the land:");
				row_house = sc.nextInt();

				// check if row input is valid, if invalid, redirect to main menu
				if (row_house > row - 2) {
					System.out.println("Invalid input. Row of house needs to less than or equal to " + (row - 2)
							+ ". No house is built.");
					continue;
				}

				System.out.println("Enter Column number of the land:");
				column_house = sc.nextInt();

				// check if column input is valid, if invalid, redirect to main menu
				if (column_house > column - 2) {
					System.out.println("Invalid input. Column of house needs to less than or equal to " + (column - 2)
							+ ". No house is built.");
					continue;
				}

				land.buildHouse(row_house, column_house);
				continue;

			case 2:
				land.displayLand();
				continue;

			case 3:
				land.clearLand();
				
				land.displayLand();
				continue;
			}
		}
		// if user has entered 4, end the program
		System.out.println("Program Ends");
		System.out.println();
		
		// Closing Scanner object
		sc.close();
	}
}
