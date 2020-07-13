package thriftyRent;

import java.util.ArrayList;
import java.util.Collections;

import util.DateTime;

/**
 * @author Jigar Mangukiya
 * 
 *  Abstract class Vehicle implements IVehicle <br><br>
 * 
 * @param vehicleId : String - vehicleId of object<br>
 * @param year : String - year of Manufacture<br>
 * @param make : String - maker company of vehicle<br>
 * @param model : String - model of vehicle<br>
 * @param numberOfSeats : Integer - number of seats in the vehicle<br>
 * @param vehicleType : VehicleType (ENUM) - type of vehicle 'car' or 'van'<br>
 * @param vehicleStatus : VehicleStatus (ENUM) - current status of vehicle 'available', 'rented', 'underMaintenance'<br>
 * @param rentalRecords : ArrayList<RentalRecord> - collection of current and historical rental records associated with vehicle<br>
 * 
 * Major Method/s -<br>
 * 		1. addRentalRecord : boolean<br>
 * 		2. toString : String<br><br>
 * 
 * Auxiliary Method/s<br>
 * 		Getters & Setter for all of the properties of the vehicle class
 */
public abstract class Vehicle implements IVehicle {
	
	private String vehicleId;
	private String year;
	private String make;
	private String model;
	private int numberOfSeats;
	private VehicleType vehicleType;
	private VehicleStatus vehicleStatus;
	private ArrayList<RentalRecord> rentalRecords;
	

	 /**
	    * Class constructor 
	    * Sets value of class properties and initializes rentalRecord collection with a default size of 10
	    *  
		* @param vehicleId : String - vehicleId of vehicle
		* @param year : Integer - Year of vehicle manufacture
		* @param make : String - Make of vehicle
		* @param model : String - Model of vehicle
		* @param numOfSeats : Integer - number of seats of car object
		* @param vehicleStatus : VehicleStatus - Status of new vehicle - default value - available
	    */
	public Vehicle(String vehicleId, String year, String make, String model, int numberOfSeats, VehicleType vehicleType) {
		setVehicleId(vehicleId);
		setYear(year);
		setMake(make);
		setModel(model);
		setVehicleType(vehicleType);
		setNumberOfSeats(numberOfSeats);
		setVehicleStatus(VehicleStatus.available);
		this.rentalRecords = new ArrayList<RentalRecord>(10);
	}

	
	public String getVehicleId() {
		return vehicleId;
	}

	
	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}

	
	public String getYear() {
		return year;
	}

	
	public void setYear(String year) {
		this.year = year;
	}

	
	public String getMake() {
		return make;
	}

	
	public void setMake(String make) {
		this.make = make;
	}

	
	public String getModel() {
		return model;
	}

	
	public void setModel(String model) {
		this.model = model;
	}

	
	public int getNumberOfSeats() {
		return numberOfSeats;
	}

	
	public void setNumberOfSeats(int numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
	}

	
	public VehicleType getVehicleType() {
		return vehicleType;
	}

	
	public void setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}

	
	public VehicleStatus getVehicleStatus() {
		return vehicleStatus;
	}

	
	public void setVehicleStatus(VehicleStatus vehicleStatus) {
		this.vehicleStatus = vehicleStatus;
	}

	
	public ArrayList<RentalRecord> getRentalRecords() {
		return rentalRecords;
	}

	
	public void setRentalRecords(ArrayList<RentalRecord> rentalRecords) {
		this.rentalRecords = rentalRecords;
	}	
	
	
	//Method overloads adds a rental record to the collection's beginning
	public void addRentalRecord(RentalRecord rentalRecord) {
		if(this.getRentalRecords().size() < 10)
			this.rentalRecords.add(0, rentalRecord);
		else {
			Collections.sort(this.getRentalRecords());
			this.rentalRecords.remove(9);
			this.rentalRecords.add(0, rentalRecord);
		}
	}
	
	/**
	 * Method overloads the default toString() method to get details of object in a pre-defined format
	 * <br>
	 *  @return String formatted string to print vehicle details without rental records 
	 */	
	public String toString() {
		return this.getVehicleId() +";"+
				this.getYear() +";"+
				this.getMake() +";"+
				this.getModel() +";"+
				this.getNumberOfSeats() +";"+
				this.getVehicleStatus();
	}
}
