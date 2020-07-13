package thriftyRent;

import java.text.DecimalFormat;
import util.DateTime;


/**
 * @author Jigar Mangukiya
 * Concrete class RentalRecord holds details of single rental associated with a vehicle object(car or van).	
 * Implements comparable<Object> interface to facilitate easy sorting of collection of RentalRecords<br>
 * 
 * @param recordId : String - Record Id composed of vehicleId + customerId + rentDate<br>
 * @param rentDate : util.DateTime - rental start date<br>
 * @param estimatedReturnDate : util.DateTime - estimated date of return of vehicle<br>
 * @param actualReturnDate : util.DateTime - actual return date when vehicle is returned<br>
 * @param rentFee : Double - charges associated with number of regular (not late) rental days<br>
 * @param lateFee : Double - charges associated with number of days beyond estimated return date<br>
 * 
 * Major Method/s -<br>
 * 		1. rent : boolean<br>
 * 		5. getDetails : String<br>
 * 		6. toString : String<br><br>
 * 
 * Auxiliary Method/s<br>
 * 		1. validateNewBooking(DateTime, Integer) : boolean<br>
 * 		2. validateReturnDate(DateTime) : boolean<br>
 * 		3. calculateFees(RentalRecord, DateTime) : boolean<br><br>
 */
public class RentalRecord implements Comparable<Object>{

	private static DecimalFormat df = new DecimalFormat("0.00");
	
	private String recordId;
	private DateTime rentDate;
	private DateTime estimatedReturnDate;
	private DateTime actualReturnDate;
	private double rentalFee;
	private double lateFee;
	
	public RentalRecord() {
	}
	
	
	 /**
	    * Class constructor calls super class (Vehicle) constructor 
	    *  
		* @param recordId String recordId of rental
		* @param rentDate DateTime Date of vehicle rental
		* @param numberOfDays Integer number of rental days
	    */
	public RentalRecord(String recordId, DateTime rentDate, int numberOfDays) {
		this.setRecordId(recordId);
		this.setRentDate(rentDate);
		this.setEstimatedReturnDate(numberOfDays);
	}
	
	
	public String getRecordId() {
		return recordId;
	}
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	public DateTime getRentDate() {
		return rentDate;
	}
	public void setRentDate(DateTime rentDate) {
		this.rentDate = rentDate;
	}
	public DateTime getEstimatedReturnDate() {
		return estimatedReturnDate;
	}
	public void setEstimatedReturnDate(int numberOfDays) {
		DateTime dt = new DateTime(this.getRentDate(),numberOfDays);
		this.estimatedReturnDate = dt;
	}
	public DateTime getActualReturnDate() {
		return actualReturnDate;
	}
	public void setActualReturnDate(DateTime actualReturnDate) {
		this.actualReturnDate = actualReturnDate;
	}
	public double getRentalFee() {
		return rentalFee;
	}
	public void setRentalFee(double rentalFee) {
		this.rentalFee = rentalFee;
	}
	public double getLateFee() {
		return lateFee;
	}	
	public void setLateFee(double lateFee) {
		this.lateFee = lateFee;
	}
	
	
	/**
	 * Method overloads the default toString() method to get details of object in a pre-defined format
	 * <br>
	 *  @return String formatted string to print vehicle details without rental records 
	 */
	public String toString() {
		return this.getRecordId().toString()+":"+
				this.getRentDate().toString()+":"+
				this.getEstimatedReturnDate().toString()+":"+
				(this.getActualReturnDate() == null? "none" : this.getActualReturnDate()).toString() +":"+
				(this.getRentalFee()==0?"none":this.getRentalFee()).toString()+":"+
				(this.getLateFee()== 0?"none":this.getLateFee()).toString();
	}
	
	
	/**
	 * Method is used get a formatted string to print vehicle details in a more readable format<br>
	 * <br>
	 *  @return String formatted string to print vehicle details with sorted list of rental records 
	 */
	public String getDetails() {
	
		return Utilities.tabularize("Record ID",this.getRecordId())
		.concat(Utilities.tabularize("Rent Date",this.getRentDate().toString()))
		.concat(Utilities.tabularize("Estimate Return Date",this.getEstimatedReturnDate().toString()))

		.concat(this.getActualReturnDate() == null? "" : 		
				Utilities.tabularize("Acutal Return Date", this.getActualReturnDate().toString())
				+ Utilities.tabularize("Rental Fee",df.format(this.getRentalFee()))
				+ Utilities.tabularize("Late Fee",df.format(this.getLateFee()))
				);						
	}
	
	
	/**
	 * Overrides generic compareTo() to facilitate comparison of 2 RentalRecord objects based on rentDate
	 * 
	 *  @return Integers suggesting relative descending index of 2 objects based on rentDate
	 */
	@Override
	public int compareTo(Object comparestu) {
		DateTime compareDate=((RentalRecord)comparestu).getRentDate();
        return  DateTime.diffDays(compareDate, this.getRentDate());
	}
}
