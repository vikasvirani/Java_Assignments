package thriftyRent;
/**
 * 
 * @author Jigar Mangukiya
 *
 * Enumeration with values representing the strings for main menu of ThriftyRent Class
 */
public enum SystemMenu {
	addVehicle("Add Vehicle"),
	rentVehicle("Rent Vehicle"),
	returnVehicle("Return Vehicle"),
	vehicleMaintenance("Vehicle Maintenance"),
	completeMaintenance("Complete Maintenance"),
	displayAllVehicles("Display All Vehicles"),
	exitProgram("Exit Program");
	
	private String displayName;
	
	
	SystemMenu(String displayName) {
	    this.displayName = displayName;
	}
	
	@Override 
	public String toString() { return displayName; }

}