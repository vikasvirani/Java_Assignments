package thriftyRent;

import java.util.ArrayList;
import util.DateTime;

/**
 * @author Jigar Mangukiya<br>
 * 
 * Concrete class ThriftyRentSystem <br><br>
 * This is the application class which is responsible for the flow of application operation
 * maintains a list of vehicle references which can store objects of both type Car & Van
 * 
 * Major Method/s -<br>
 * 		1. run : void<br>
 * 		2. addVehicle : boolean<br>
 * 		3. rentVehicle : boolean<br>
 * 		4. returnVehicle : boolean<br>
 * 		5. vehicleMaintenance : String<br>
 * 		6. completeMaintenance : String<br>
 *  	7. displayAllVehicles : String<br><br>
 *  
 * Auxiliary Method/s<br>
 * 		1. addCar : String<br>
 *  	2. addVan : String<br>
 *  	3. vehicleIdExist : boolean <br>
 *  	4. getVehicle : Vehicle <br>
 */
public class ThriftyRentSystem {
	
	private ArrayList<Vehicle> vehiclesList;

	
	 /**
	    * Class constructor initializes two ArrayLists with fixed capacity of 50 objects each. one of each type Car & Van 
	    */
	public ThriftyRentSystem() {
		this.vehiclesList = new ArrayList<Vehicle>(50);
	}

	/**
	 * Main method responsible for operation of the system 
	 * loops till user selects 'end program' option from the main menu 
	 */
	public void run() {		
		ThriftyRentSystem ap = new ThriftyRentSystem();

		// infinite loop to print main menu and perform some operation in each iteration 
		do {
		System.out.println(Utilities.menuViewGenerator("Main Menu", Utilities.enumToStringArray(SystemMenu.values())));
		int choice = Utilities.getIntInput("Enter Your Choice", 1, 7, false);
		
		switch(choice) {
		  case 1:
			  ap.addVehicle();
			  break;
		  case 2:
			  ap.rentVehicle();
			  break;
		  case 3:
			  ap.returnVehicle();
			  break;
		  case 4:
			  ap.vehicleMaintenance();
			  break;
		  case 5:
			  ap.completeMaintenance();
			  break;
		  case 6:
			  ap.displayAllVehicles();
			  break;
		  case 7:
			  	return;
			}
		}while(true);
	}
	
	
	/**
	 * Method is used to add a new vehicle to list of corresponding list maintained within the ThriftyRentSystem object <br>
	 * On successful creation a new object will be added to appropriate list of objects (Car or Van).<br>
	 * New vehicle creation can fail if vehicle Id already exist or the list of objects already has 50 objects.<br>
	 */
	public  void addVehicle() {
		try {
			System.out.println(Utilities.menuViewGenerator("Add Vehicle", Utilities.enumToStringArray(AddVehicleEnum.values())));
			int choice = Utilities.getIntInput("Enter Your Choice", 1, 2, false);			
			switch(choice) {
			  case 1:
				this.addCar();
				  break;
				  
			  case 2:
				 this.addVan();
				  break;				  
			}
			System.out.println("\nVehicle created successfully !!!\n");
		}catch(Exception ex) {
			System.out.println(ex.getMessage() + "\nVehicle creation failed !!!\n" );
		}
		
	}
	
	
	/**
	 * Method is used to Create a new Car object and add it to list of Car objects<br>
	 * Validations check if vehicleId already exist or list is full
	 * <br>
	 *  
	 *  @throws if validation fails, corresponding failure message will be thrown along with Exception object 
	 */
	public void addCar() throws Exception{
		if(this.getVehiclesList().size() >= 50 ) {
			throw new Exception("Can not add more than 50 Cars !!!\n"); 
		}
		String vehicleId = this.getNewVehicleId(Car.class);
		System.out.println("Vehicle ID will be '" + vehicleId +"'");	
		int year = Utilities.getIntInput("Enter Year",1900,Integer.parseInt(DateTime.getCurrentTime().split("-")[0]), false);
		String make = Utilities.getStringInput("Enter Make").toUpperCase();
		String model = Utilities.getStringInput("Enter Model").toUpperCase();
		
		System.out.println(Utilities.menuViewGenerator("No. of Seats", new String[] {"4 Seats ","7 Seats "}));
		int choice_I1 = Utilities.getIntInput("Enter your choice", 1, 2,false);
		int seats = choice_I1 == 1 ? 4 : 7;
		
		this.vehiclesList.add(new Car(vehicleId, Integer.toString(year), make, model, seats));
		System.out.println("Vehicle with ID " + vehicleId +" created successfully !!!\n");

	}
	
	
	/**
	 * Method is used to Create a new Van object and add it to list of Van objects.<br>
	 * Validations check if vehicleId already exist or list is full.
	 * <br>
	 *  
	 *  @throws if validation fails, corresponding failure message will be thrown along with Exception object.
	 */
	public void addVan() throws Exception{
		if(this.getVehiclesList().size() >= 50 ) {
			throw new Exception("Can not add more than 50 Vans !!!\n"); 
		}
		
		String vehicleId = this.getNewVehicleId(Van.class);
		System.out.println("Vehicle ID will be '" + vehicleId +"'");
		int year = Utilities.getIntInput("Enter Year",1900,Integer.parseInt(DateTime.getCurrentTime().split("-")[0]), false);
		String make = Utilities.getStringInput("Enter Make").toUpperCase();
		String model = Utilities.getStringInput("Enter Model").toUpperCase();		
		DateTime lastMaintenanceDate = Utilities.getDateInput("Enter Last Maintenance Date of Van (dd/mm/yyyy)");
	 
		this.vehiclesList.add(new Van(vehicleId, Integer.toString(year), make, model, lastMaintenanceDate));
		}
	
	
	/**
	 * Method is used to make a new rental booking for either car or van.<br>
	 * method uses runtime polymorphism to invoke correct method from either Car or Van class.<br>
	 * if method succeeds, a new RentalRecord will be created in corresponding Car or Van object.<br>
	 * else it will print the reason of failure and terminates.
	 */
	public void rentVehicle() {
		try {
			Vehicle vehicle;			
			String vehicleId = Utilities.getStringInput("Enter Vehicle Id").toUpperCase();
			if(!this.vehicleIdExist(vehicleId)) {
				throw new Exception("Vehicle does not exist!!!");
			}
			if(vehicleId.startsWith("C_")) {
				vehicle = (Car) this.getVehicle(vehicleId);
			}
			else {
				vehicle = (Van) this.getVehicle(vehicleId);
			}
			
			String customerId = Utilities.getStringInput("Enter Customer Id").toUpperCase();
			if(!customerId.startsWith("CUS")) 
				customerId = "CUS" + customerId; 
			if(customerId.length() > 7) 
				customerId = customerId.substring(0, 7);
			System.out.println("Customer ID will be '" + customerId +"'");	
			
			DateTime rentDate = Utilities.getDateInput("Enter Rent Date (dd/mm/yyyy)");
			int numberOfDays = Utilities.getIntInput("How many days?  (Car - Min 2 days & Max 14 days,\n\t\t Van - Min 1 day & Max 12 days) ", vehicle instanceof Car ? 2 : 1,vehicle instanceof Car ? 14 : 12, false);
			
			if(vehicle instanceof Car ? ((Car)vehicle).rent(customerId, rentDate, numberOfDays) : ((Van)vehicle).rent(customerId, rentDate, numberOfDays)) {
				System.out.println("Vehicle " + vehicleId + " is now rented by customer " + customerId);
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	
	/**
	 * Method is used to return a car or a van, indicating completion of a rental.<br>
	 * method uses runtime polymorphism to invoke correct method from either Car or Van class.<br>
	 * if method succeeds, Corresponding RentalRecord will be modified and displayed with fees.<br>
	 * else it will print the reason of failure and terminates.
	 */
	public void returnVehicle() {
		try {
			Vehicle vehicle;			
			String vehicleId = Utilities.getStringInput("Enter Vehicle Id").toUpperCase();
			if(!this.vehicleIdExist(vehicleId)) {
				throw new Exception("Vehicle does not exist!!!");
			}
			if(vehicleId.startsWith("C_")) {
				vehicle = (Car) this.getVehicle(vehicleId);
			}
			else {
				vehicle = (Van) this.getVehicle(vehicleId);
			}
			DateTime returnDate = Utilities.getDateInput("Enter Return Date (dd/mm/yyyy)");
			
			if(vehicle instanceof Car ? ((Car)vehicle).returnVehicle(returnDate) : ((Van)vehicle).returnVehicle( returnDate)) {
				System.out.println("Vehicle " + vehicleId + " returned successfully \n");
				System.out.println(vehicle.getDetails(false));
			}
			else {
				throw new Exception("Vehicle can not be renturned !!!");
			}
			
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	
	/**
	 * Method is used to send a vehicle (Car or Van) for maintenance<br>
	 * if successful status of vehicle is changed from 'available' to 'underMaintenance'<br>
	 * Method makes a polymorphic call to perfromMaintenance() method of Car or Van class<br>
	 * 
	 * <br>
	 * Assumptions - Car can only be sent for maintenance if the status is available<br>
	 */
	public void vehicleMaintenance() {
		try {
			Vehicle vehicle;			
			String vehicleId = Utilities.getStringInput("Enter Vehicle Id").toUpperCase();
			if(!this.vehicleIdExist(vehicleId)) {
				throw new Exception("Vehicle does not exist!!!");
			}
			if(vehicleId.startsWith("C_")) {
				vehicle = (Car) this.getVehicle(vehicleId);
			}
			else {
				vehicle = (Van) this.getVehicle(vehicleId);
			}
			if(vehicle instanceof Car ? ((Car)vehicle).performMaintenance() : ((Van)vehicle).performMaintenance()) {
				System.out.println("Vehicle " + vehicleId + " is now under Maintenance \n");
			}
			else {
				throw new Exception("\nVehicle can not be sent for Maintenance !!!\n");
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	
	/**
	 * Method is used to complete maintenance routine on a vehicle (Car or Van) if it is already underMaintenance<br>
	 * if successful status of vehicle is changed from 'underMaintenance' to 'available'<br>
	 * Method makes a polymorphic call to completeMaintenance() method of Car or Van class<br>
	 * Method will ask user for a util.DateTime if operation is to be performed on a Van object
	 * 
	 * <br>
	 * Assumptions - maintenance can only be completed if the status is underMaintenance<br>
	 */
	public boolean completeMaintenance() {
		try {
			Vehicle vehicle;			
			String vehicleId = Utilities.getStringInput("Enter Vehicle Id").toUpperCase();
			if(!this.vehicleIdExist(vehicleId)) {
				throw new Exception("Vehicle does not exist!!!");
			}
			if(vehicleId.startsWith("C_")) {
				vehicle = (Car) this.getVehicle(vehicleId);
			}
			else {
				vehicle = (Van) this.getVehicle(vehicleId);
			}
			DateTime completionDate = Utilities.getDateInput("Enter Completion Date (dd/mm/yyyy)");
			
			if(vehicle instanceof Car ? ((Car)vehicle).completeMaintenance(completionDate) : ((Van)vehicle).completeMaintenance(completionDate)) {
				System.out.println("Vehicle " + vehicleId + " Maintenance completed successfully \n");
				System.out.println(vehicle.getDetails(true));
			}
			else {
				throw new Exception("Vehicle Maintenance can not be completed !!!");
			}
			
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		return true;
	}
	
	
	/**
	 * Method is used to print details of all vehicles<br>
	 */
	public boolean displayAllVehicles() {
		boolean flag = true;
		System.out.println("\n"+Utilities.dividingDashLine());

		for(Vehicle vehicle: this.getVehiclesList()) {
			flag = false;
			System.out.println(vehicle.getDetails(true));
			System.out.println(Utilities.dividingDashLine());
		}
		if(flag) {
			System.out.println("No vehicles added !!!");
			System.out.println(Utilities.dividingDashLine() + "\n");
			
		}
		return true;
	}
	
	
	/**
	 * Method is used to check if vehicleId already exists<br>
	 * 
	 * <br>
	 * @param vehicleId : String vehicleId that is to checked for existence
	 * @return boolean true if vehicleId exists in either CarsList or VansList
	 * 				   false if vehicleId does not exist in either List
	 */
	public boolean vehicleIdExist(String vehicleId) throws Exception{
			for(Vehicle vehicle : this.getVehiclesList()) {
				if(vehicle.getVehicleId().toUpperCase().equals(vehicleId.toUpperCase())) {
					return true;
				}	
			}	
		return false;
	}
	
	
	/**
	 * Method returns a vehicle reference (Either Car or Van) from CarsList or VansList 
	 */
	public Vehicle getVehicle(String vehicleId) throws Exception{
		for(Vehicle vehicle : this.getVehiclesList()) {
			if(vehicle.getVehicleId().toUpperCase().equals(vehicleId.toUpperCase())) {
				return vehicle;
			}	
		}	
		return null;
	}
	
	
	/**
	 * Method returns a vehicle reference (Either Car or Van) from CarsList or VansList 
	 */
	public String getNewVehicleId(Class T) throws Exception{
		String newId = "";
		while(true) {
			if(T.equals(Car.class)) {
				newId = "C_" + Utilities.randomIdGenerator(4).toUpperCase();
			}
			if(T.equals(Van.class)) {
				newId = "V_" + Utilities.randomIdGenerator(4).toUpperCase();
			}
			for(Vehicle vehicle : this.getVehiclesList()) {
				if(vehicle.getVehicleId().equals(newId))
					continue;
			}
			break;
		}
		return newId;
	}
	
	
	//Getters & Setter for ThriftyRent Class
	public ArrayList<Vehicle> getVehiclesList() {
		return this.vehiclesList;
	}


	public void setCarsList(ArrayList<Vehicle> vehiclesList) {
		this.vehiclesList = vehiclesList;
	}
}
