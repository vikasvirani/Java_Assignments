package model;

import util.DateTime;

public class Rental_Record 
{

	private String record_id;
	private String Vehicle_id;
	private String Customer_id;
	private DateTime rent_date;
	private DateTime estimated_return_date;
	private DateTime actual_return_date;
	private Double rental_fee;
	private Double late_fee;
	
	
	// Constructor to initialize values
	public Rental_Record(String record_id, String Vehicle_id,String Customer_id,DateTime rent_date, DateTime estimated_return_date,DateTime actual_return_date, Double rental_fee, Double late_fee) 
	{
		this.record_id = record_id;
		this.Vehicle_id = Vehicle_id;
		this.Customer_id = Customer_id;
		this.rent_date = rent_date;		
		this.estimated_return_date = estimated_return_date;
		this.actual_return_date = actual_return_date;
		this.rental_fee = rental_fee;
		this.late_fee = late_fee;
	}
		
	// Constructor to initialize values
	public Rental_Record(String Vehicle_id,String Customer_id,DateTime rent_date, int numOfRentDay) 
	{
		String[] arrOfStr_rent = String.valueOf(rent_date).split("/");
		String rentDate_final = arrOfStr_rent[2]+"-"+arrOfStr_rent[1]+"-"+arrOfStr_rent[0];
			
		this.record_id = Vehicle_id+"_"+Customer_id+"_"+rentDate_final;
		this.Vehicle_id = Vehicle_id;
		this.Customer_id = Customer_id;
		this.rent_date = rent_date;
		
		DateTime dt = new DateTime(rent_date, numOfRentDay);		
		this.estimated_return_date = dt;
	}
	
	
	// convert Rental Record details into : separated string
	public String toString() 
	{
		String detailRecord = this.record_id+":"+this.rent_date+":"+this.estimated_return_date+":"+this.actual_return_date+":"+this.rental_fee+":"+this.late_fee;

		return detailRecord;
	} 
	
	
	// Getter Setters for all Variables
	public DateTime getActual_return_date() {
		return actual_return_date;
	}


	public void setActual_return_date(DateTime actual_return_date) {
		this.actual_return_date = actual_return_date;
	}


	public DateTime getEstimated_return_date() {
		return estimated_return_date;
	}


	public void setEstimated_return_date(DateTime estimated_return_date) {
		this.estimated_return_date = estimated_return_date;
	}


	public DateTime getRent_date() {
		return rent_date;
	}


	public void setRent_date(DateTime rent_date) {
		this.rent_date = rent_date;
	}


	public String getRecord_id() {
		return record_id;
	}


	public void setRecord_id(String record_id) {
		this.record_id = record_id;
	}


	public Double getRental_fee() {
		return rental_fee;
	}


	public void setRental_fee(Double rental_fee) {
		this.rental_fee = rental_fee;
	}


	public Double getLate_fee() {
		return late_fee;
	}


	public void setLate_fee(Double late_fee) {
		this.late_fee = late_fee;
	}


	public String getCustomer_id() {
		return Customer_id;
	}


	public void setCustomer_id(String customer_id) {
		Customer_id = customer_id;
	}


	public String getVehicle_id() {
		return Vehicle_id;
	}


	public void setVehicle_id(String vehicle_id) {
		Vehicle_id = vehicle_id;
	}
	
}
