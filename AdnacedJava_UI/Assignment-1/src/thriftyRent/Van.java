package thriftyRent;

import java.util.Collections;
import util.DateTime;

/**
 * @author Jigar Mangukiya
 * 
 * Concrete class Van extends Abstract class Vehicle. <br><br>
 * 
 * @param lastMaintenanceDate : util.DateTime - Value specifies the last date on which the van finished maintenance<br><br>
 * 
 * Major Method/s -<br>
 * 		1. rent : boolean<br>
 * 		2. returnVehicle : boolean<br>
 * 		3. performMaintenance : boolean<br>
 * 		4. completeMaintenance : boolean<br>
 * 		5. getDetails : String<br>
 * 		6. toString : String<br><br>
 * 
 * Auxiliary Method/s<br>
 * 		1. validateNewBooking(DateTime, Integer) : boolean<br>
 * 		2. validateReturnDate(DateTime) : boolean<br>
 * 		3. calculateFees(RentalRecord, DateTime) : boolean<br><br>
 */
public class Van extends Vehicle{

	private static final int numberOfSeats = 15;
	private static final int perDayRent = 235;
	private static final int lateFeePerDay = 299;
	private static final int maintenanceIntervalInDays = 12;
	
	public DateTime lastMaintenanceDate;
	
	 /**
	    * Class constructor calls super class (Vehicle) constructor 
	    *  
		* @param vehicleId String vehicleId of vehicle
		* @param year Integer Year of vehicle manufacture
		* @param make String Make of vehicle
		* @param model String Model of vehicle
		* @param lastMaintenanceDate util.DateTime Date on which vehicle last completed maintenance
	    */
	public Van(String vehicleId, String year, String make, String model, DateTime lastMaintenanceDate) {
		super(vehicleId, year, make, model, Van.numberOfSeats, VehicleType.Van);
		this.lastMaintenanceDate = lastMaintenanceDate;
	}
	

	// Getter for class property lastMaintenanceDate
	public DateTime getLastMaintenanceDate() {
		return lastMaintenanceDate;
	}

	// Setter for class property lastMaintenanceDate
	public void setLastMaintenanceDate(DateTime lastMaintenanceDate) {
		this.lastMaintenanceDate = lastMaintenanceDate;
	}
	
	
	/**
	 * Method is used to make a new booking on the calling van object if all conditions & assumptions are met<br>
	 * if booking is successful, rental record is added to rental record list & vehicle status is changed to 'rented'<br>
	 * <br>
	 * Assumptions - Van can only be rented on current date or future date<br>
	 *				 Van can only be rented in interval, including [Last Maintenance Date - + 12 Days]<br>
	 *				 Van cannot be rented if there exist a booking in rental record, date span of which, overlaps date span of this invocation<br> 				
	 *  
	 *  @param customerId String customerId of the customer on behalf of which booking is being made
	 *  @param rentDate util.DateTime date on which rental period begins
	 *  @param numOfRentDays Integer span of rental period in days
	 *  @return boolean true indicates successful rental false will be replaced by an Exception 
	 *  
	 *  @throws Exception reflects which validation failed with appropriate error message to be printed
	 */
	public boolean rent(String customerId, DateTime rentDate, int numOfRentDays) throws Exception{
		// if van was not maintenance for more than 12 days, it is not allowed to be rented 
		// and is supposed to be sent for maintenance
		if(DateTime.diffDays(lastMaintenanceDate, new DateTime()) > 12) {
			this.performMaintenance();
			throw new Exception("System has detected that vehicle is due for schedule Maintenance.\n Vehicle is sent for Maintenance now by System !!!\n");
		}
		if(validateNewBooking(rentDate, numOfRentDays)) {
			String recordId = this.getVehicleId()+"_"+customerId+"_"+rentDate.getEightDigitDate();
			RentalRecord rd = new RentalRecord(recordId, rentDate, numOfRentDays);
			this.setVehicleStatus(VehicleStatus.rented);
			this.addRentalRecord(rd);
			Collections.sort(this.getRentalRecords());
			return true;
		}
		else {
			throw new Exception("\nVehicle can not be rented !!!\n");
		}
	}
	
	
	/**
	 * Method is used to validate a new booking inquiry<br>
	 * <br>
	 * Assumptions - Van can only be rented on current date or future date<br>
	 *				 Van can only be rented in interval, including [Last Maintenance Date - + 12 Days]<br>
	 *				 Van can not be rented if there exist a booking in rental record, date span of which, overlaps date span of this invocation<br>
	 *				 if a van has been returned on a date in past, new rental can't be before the latest return date, as it will suggest a booking in past<br>  				
	 *  
	 *  @param rentDate util.DateTime date on which rental period begins
	 *  @param numOfRentDays Integer span of rental period in days
	 *  @return boolean true indicates successful validation result false will be replaced by an Exception 
	 *  
	 *  @throws Exception reflects which validation failed with appropriate error message to be printed
	 */
	public boolean validateNewBooking(DateTime rentDate, int numOfRentDays) throws Exception{	
		DateTime estimatedReturnDate = new DateTime(rentDate,numOfRentDays);
		DateTime lastReturnDate = new DateTime();
		
		if(this.getVehicleStatus() != VehicleStatus.available){
			throw new Exception("Vehicle not currently available for rent !!!\n");
		}
		if(numOfRentDays > Van.maintenanceIntervalInDays - 1 || DateTime.diffDays(rentDate, new DateTime()) < -1) {
			throw new Exception("\nCan not rent vehicle for past dates. Please try with date " + new DateTime().getFormattedDate() + " or after !!!\n");
		}
		if(DateTime.diffDays(estimatedReturnDate, this.lastMaintenanceDate) > Van.maintenanceIntervalInDays - 1 || DateTime.diffDays(rentDate, this.lastMaintenanceDate) > Van.maintenanceIntervalInDays - 1) {
			throw new Exception("Vehicle not available for rent during the period " + rentDate.getFormattedDate() + " - " + (new DateTime(rentDate,numOfRentDays).getFormattedDate()) + " !!!\n");
		}
		for(RentalRecord rentalRecord : this.getRentalRecords()) {
			if(DateTime.diffDays(rentalRecord.getActualReturnDate(),lastReturnDate) > 0 ) {
				lastReturnDate = rentalRecord.getActualReturnDate();
			}
		}
		if(DateTime.diffDays(rentDate, lastReturnDate) < 0 && !lastReturnDate.getFormattedDate().equals(new DateTime().getFormattedDate()))
			throw new Exception("Can not rent vehicles for past dates !!!\n");
		
		return true;
	}
	
	
	/**
	 * Method is used to return a vehicle which is currently rented and calculate fees and appropriately update vehicle status & rental record<br>
	 * <br>
	 *  
	 *  @param renturnDate util.DateTime date of return
	 *  @return boolean true indicates successful return of vehicle & false will be replaced by an Exception 
	 *  
	 *  @throws Exception reflects which validation failed with appropriate error message to be printed
	 */
	public boolean returnVehicle(DateTime returnDate) throws Exception {
		RentalRecord rentalRecord = new RentalRecord();
		
		//Find Rental Record
		for(RentalRecord rentalRecordTemp : this.getRentalRecords()) {
			if(rentalRecordTemp.getActualReturnDate() == null) {
				rentalRecord = rentalRecordTemp;
			}
		}
		//Validate Return Date
		if(!validateReturnDate(rentalRecord, returnDate)) {
			return false;
		}
		//Calculate fees and update rental record accordingly
		// if rental spans less than required days
		if(!this.calculateFees(rentalRecord, returnDate)) {
			return false;
		}
		//update vehicle status
		this.setVehicleStatus(VehicleStatus.available);
		return true;
	}
	
	
	/**
	 * Method is used to calculate fees to be charged for a rental<br>
	 * <br>
	 *  @param rentalRecord RentalRecord Rental Record based on which fees are to be calculated  
	 *  @param renturnDate util.DateTime actual date of return
	 *  @return boolean true indicates successful calculation of fees & modification of rental record & false will be replaced by an Exception 
	 *  
	 *  @throws Exception reflects which validation failed with appropriate error message to be printed
	 */
	public boolean calculateFees(RentalRecord rentalRecord, DateTime actualReturnDate) {
		int rentDays = DateTime.diffDays(actualReturnDate, rentalRecord.getRentDate());
		int estimatedDays = DateTime.diffDays(rentalRecord.getEstimatedReturnDate(), rentalRecord.getRentDate());
		double rentFee = 0;
		double lateFee = 0;
		
		if(rentDays > estimatedDays) {
			rentFee = estimatedDays * Van.perDayRent;
			lateFee = (rentDays - estimatedDays) * Van.lateFeePerDay ;
		}
		else {
			rentFee = estimatedDays * Van.perDayRent;
		}
		
		rentalRecord.setActualReturnDate(actualReturnDate);
		rentalRecord.setRentalFee(rentFee);
		rentalRecord.setLateFee(lateFee);
		return true;
	}
	
	
	/**
	 * Method checks if actual returnDate & rentDate are properly<br>
	 * <br>
	 *  @param rentalRecord RentalRecord Rental Record based on which validation is to be made  
	 *  @param renturnDate util.DateTime actual date of return
	 *  @return boolean true indicates passed validation & false will be replaced by an Exception 
	 *  
	 *  @throws Exception reflects which validation failed with appropriate error message to be printed
	 */
	public boolean validateReturnDate(RentalRecord rentalRecord, DateTime returnDate) throws Exception{
		int rentDays = DateTime.diffDays(returnDate, rentalRecord.getRentDate());
		if(rentDays < 1) {
			throw new Exception("Return date invalid !!!\\n");
		}
		return true;
	}
	
	
	/**
	 * Method is used to send a vehicle for maintenance if it is available and not being rented<br>
	 * <br>
	 *  @return boolean true indicates vehicle is successfully sent for maintenance & false will be replaced by an Exception 
	 *  
	 *  @throws Exception reflects which validation failed with appropriate error message to be printed
	 */
	public boolean performMaintenance() throws Exception{
		if(this.getVehicleStatus() == VehicleStatus.rented) {
			throw new Exception("Vehicle not available for maintenance !!!\\n");
		}
		if(this.getVehicleStatus() == VehicleStatus.underMaintenance) {
			throw new Exception("Vehicle is already under maintenance !!!\\n");
		}
		this.setVehicleStatus(VehicleStatus.underMaintenance);
		return true;
	}
	
	
	/**
	 * Method is used to complete maintenance if it is under maintenance<br>
	 * <br>
	 *  @param completionDate util.DateTime date on which maintenance is completed
	 *  @return boolean true indicates vehicle completed maintenance and available for rent & false will be replaced by an Exception 
	 *  
	 *  @throws Exception reflects which validation failed with appropriate error message to be printed
	 */
	public boolean completeMaintenance(DateTime completionDate) throws Exception{
		if(this.getVehicleStatus() != VehicleStatus.underMaintenance) {
			throw new Exception("Vehicle currently not under maintenance !!!\n");
		}
		this.setLastMaintenanceDate(completionDate);
		this.setVehicleStatus(VehicleStatus.available);
		return true;
	}
	
	
	/**
	 * Method is used get a formatted string to print vehicle details in a more readable format<br>
	 * <br>
	 *  @return String formatted string to print vehicle details with sorted list of rental records 
	 */
	public String getDetails(boolean displayAll) {
		String rentalRecordsSummary = "";
		if(displayAll) {
			if(!this.getRentalRecords().isEmpty()) {
				rentalRecordsSummary = "\nRental Record\n\n" + Utilities.dividingDashLine();
				for(RentalRecord rentalRecord : this.getRentalRecords()) {
					rentalRecordsSummary = rentalRecordsSummary.concat(rentalRecord.getDetails()).concat(Utilities.dividingDashLine());
				}
			}
		}
		else {
			if(!this.getRentalRecords().isEmpty()) {
				rentalRecordsSummary = "\nRental Record\n\n" + Utilities.dividingDashLine();
					Collections.sort(this.getRentalRecords());
					rentalRecordsSummary = rentalRecordsSummary.concat(this.getRentalRecords().get(0).getDetails()).concat(Utilities.dividingDashLine());
			}
			
		}
		
		return Utilities.tabularize("Vehicl ID", this.getVehicleId())
				.concat(Utilities.tabularize("Year", this.getYear()))
				.concat(Utilities.tabularize("Make", this.getMake()))
				.concat(Utilities.tabularize("Model", this.getModel()))
				.concat(Utilities.tabularize("Number of Seats", Integer.toString(this.getNumberOfSeats())))
				.concat(Utilities.tabularize("Status", this.getVehicleStatus().toString()))
				.concat(Utilities.tabularize("Last Maintenance Date", this.getLastMaintenanceDate().toString()))
				.concat(rentalRecordsSummary);
	}
	
	
	/**
	 * Method overloads the default toString() method to get details of object in a pre-defined format
	 * <br>
	 *  @return String formatted string to print vehicle details without rental records 
	 */
	public String toString() {
		return super.toString() +":"+
				this.getLastMaintenanceDate();
	}
}

