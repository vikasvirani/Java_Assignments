package thriftyRent;


/**
 * Sub-menu for addVehicle() Method of ThriftyRentSystem class 
 */
public enum AddVehicleEnum {
	Car("Add Car"),
	Van("Add Van");
	
	private String displayName;
	
	
	AddVehicleEnum(String displayName) {
	    this.displayName = displayName;
	}
	
	@Override 
	public String toString() { return displayName; }

}
