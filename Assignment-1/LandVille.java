/* Class - LandVille
 *  
 *	Contains operations to perform tasks like initializing the land, clearing the land & building house etc..
 */
class LandVille {
	
	// Variable to hold the land array
	private int[][] land;

	// added boolean variable hasHouse
	public boolean hasHouse = false;

	// Constructor to initialize land with size of rows and columns
	// Initiaize each value of the land array with value 0
	public LandVille(int maxRows, int maxColumns) {
		land = new int[maxRows][maxColumns];

		for (int i = 0; i < land.length; i++) {
			for (int j = 0; j < land[i].length; j++) {
				land[i][j] = 0;
			}
		}
	}

	
	// Prints the land grid
	public void displayLand() {
		for (int i = 0; i < land.length; i++) {
			for (int j = 0; j < land[i].length; j++) {
				System.out.print(land[i][j] + " ");
			}
			System.out.println();
		}
	}

	// Method to clear the land
	// Changes the value of each element of land to 0 and hasHouse to False
	public void clearLand() {
		for (int i = 0; i < land.length; i++) {
			for (int j = 0; j < land[i].length; j++) {
				land[i][j] = 0;
			}
		}

		hasHouse = false;
		System.out.println("Land Cleared");
	}


	// "Build" the house on the land (using the character '8') surrounded
	public void buildHouse(int rows, int columns) {
		
		// Build a fence
		for (int i = 0; i < rows + 2; i++) {
			for (int j = 0; j < columns + 2; j++) {
				land[i][j] = 1;
			}
		}
		
		// Build House
		for (int i = 1; i <= rows; i++) {
			for (int j = 1; j <= columns; j++) {
				land[i][j] = 8;
			}
		}

		// Update 'hasHouse' and call on displayLand().
		hasHouse = true;
		displayLand();

	}

}
