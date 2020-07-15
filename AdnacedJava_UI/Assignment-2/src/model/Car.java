package model;

import java.util.ArrayList;

import util.DateTime;

public class Car extends Vehicle
{

	private static final double four_seater_rent = 78.00;
	private static final double seven_seater_rent = 113.00;
	private static final double penalty_rate = 1.25;
	private static final int max_rent_days = 14;
	
	// Constructor to initialize values
	public Car(String Vehicle_id,String Year,String Make,String Model,int Number_of_passengers,String Vehicle_type,String Vehicle_status,ArrayList<Rental_Record> rentals,String image)
	{
		super(Vehicle_id,Year,Make,Model,Number_of_passengers,Vehicle_type,Vehicle_status,rentals,image);
		
	}
	
	
	// Constructor to initialize values
	public Car(String Vehicle_id,String Year,String Make,String Model,int Number_of_passengers,String Vehicle_type,String Vehicle_status,String image)
	{
		super(Vehicle_id,Year,Make,Model,Number_of_passengers,Vehicle_type,Vehicle_status,image);
		
	}
	
	
	// method to Rent a Car
	public void rent(String customerId, DateTime rentDate, int numOfRentDay) throws GeneralVehicleException,VehicleIdAlreadyOcuupiedException,Exception
	{
			if(this.getVehicle_status().equals("Available"))
			{
				String rentDay = rentDate.getNameOfDay();
				int minRentDay;
				
				if(!customerId.contains("_"))
				{
					throw new GeneralVehicleException("Invalid Customer ID : Please Enter Id which includes \"_\" in it, i.e. Format \"ANYTHING_ANYTHING\"");
				}
				
				if(rentDay.equals("Friday") || rentDay.equals("Saturday"))
				{
					minRentDay = 3;
				}
				else 
				{
					minRentDay = 2;
				}
				
				if(numOfRentDay >= minRentDay && numOfRentDay <= max_rent_days)
				{
					Rental_Record rental = new Rental_Record(this.getVehicle_id(),customerId,rentDate,numOfRentDay);
					
					DateTime dt = new DateTime(rentDate, numOfRentDay);
					
					String[] arrOfStr_rent = String.valueOf(rentDate).split("/");
	  				String rentDate_final = arrOfStr_rent[2]+"-"+arrOfStr_rent[1]+"-"+arrOfStr_rent[0];
	  				String[] arrOfStr_est = String.valueOf(dt).split("/");
	  				String EstDate_final = arrOfStr_est[2]+"-"+arrOfStr_est[1]+"-"+arrOfStr_est[0];
	  				
	  				ConnectToDB.createRentals(rental.getRecord_id(),this.getVehicle_id(),customerId,String.valueOf(rentDate_final),
							String.valueOf(EstDate_final),
	    				  	null,
	    				  	null,
	    				  	null);
	  				
					ArrayList<Rental_Record> rentals = this.getRentals();
					 
					if(rentals.size() == last_N_rentals)
						rentals.remove(0);
					
					rentals.add(rental);
					
					this.setRentals(rentals);
					this.setVehicle_status("Rented");
					
					ConnectToDB.updateVehicleRecord(this.getVehicle_id(),this.getVehicle_status(),
	    				  	null);
					
				}
				else 
				{
					throw new GeneralVehicleException("Invalid Rent days for the Vehicle as per Minimum (2 or 3 based on weekdays) OR Maximum (14) Rent Days allowed ");
				}
			}
			else
			{
				throw new VehicleIdAlreadyOcuupiedException("Vehicle : "+this.getModel()+" Could not be Rented as it is Already Occupied & Not Available");
			}

	}
	
	
	// method to Return a Car
	public void returnVehicle(DateTime returnDate) throws GeneralVehicleException,VehicleIdAlreadyOcuupiedException,Exception
	{	
			if(this.getVehicle_status().equals("Rented"))
			{
				ArrayList<Rental_Record> rentals = this.getRentals();
				Rental_Record rental = rentals.get(rentals.size()-1);
						
				if(DateTime.diffDays(returnDate, rental.getRent_date()) > 0)
				{
					Double rental_fee = 0.0;
					Double late_fee = 0.0;
					
					rental.setActual_return_date(returnDate);
					
					if(DateTime.diffDays(rental.getEstimated_return_date(), returnDate) >= 0) 
					{
						if(this.getNumber_of_passengers() == 4)
						{
							rental_fee += DateTime.diffDays(returnDate,rental.getRent_date()) * four_seater_rent;
						}
						else 
						{
							rental_fee += DateTime.diffDays(returnDate,rental.getRent_date()) * seven_seater_rent;
						}
					}
					else
					{
						if(this.getNumber_of_passengers() == 4)
						{
							rental_fee += DateTime.diffDays(rental.getEstimated_return_date(),rental.getRent_date()) * four_seater_rent;
							
							late_fee += DateTime.diffDays(rental.getActual_return_date(), rental.getEstimated_return_date()) * (penalty_rate * four_seater_rent);
						}
						else  
						{
							rental_fee += DateTime.diffDays(rental.getEstimated_return_date(),rental.getRent_date()) * seven_seater_rent;
							
							late_fee += DateTime.diffDays(rental.getActual_return_date(), rental.getEstimated_return_date()) * (penalty_rate * seven_seater_rent);
						}
					}
					
					
					rental.setRental_fee(rental_fee);
					rental.setLate_fee(late_fee);
					
					rentals.set(rentals.size()-1,rental);
					
					
					String[] arrOfStr_ret = String.valueOf(returnDate).split("/");
	  				String retDate_final = arrOfStr_ret[2]+"-"+arrOfStr_ret[1]+"-"+arrOfStr_ret[0];
	  				
	  				ConnectToDB.updateRentalRecord(rental.getRecord_id(),retDate_final,
							rental_fee,late_fee);
					
					this.setVehicle_status("Available");
					
					ConnectToDB.updateVehicleRecord(this.getVehicle_id(),this.getVehicle_status(),
	    				  	null);
					
				}
				else
				{
					throw new GeneralVehicleException("Return Date should be greater than Rent Date");
				}
			}
			else
			{
				throw new VehicleIdAlreadyOcuupiedException("Vehicle : "+this.getModel()+" Could not be Returned As it is not currently Rented");
			}

	}
	
	
	// method to Complete Maintenance
	public void completeMaintenance(DateTime completionDate) throws VehicleIdAlreadyOcuupiedException,Exception
	{	
			if(this.getVehicle_status().equals("Maintenance")) 
			{	
				this.setVehicle_status("Available");
				
				ConnectToDB.updateVehicleRecord(this.getVehicle_id(),this.getVehicle_status(),
    				  	null);
				
			}
			else
			{
				throw new VehicleIdAlreadyOcuupiedException("Vehicle : "+this.getModel()+" is Not in Maintenance, So Could not Complete a Maintenance");
			}
		
	}		
		
		
	// overridden method to return formatted string to get details of Vehicle object
	public String toString() 
	{
		String detailRecord = this.getVehicle_id()+':'+this.getYear()+':'+this.getMake()+':'+this.getModel()+':'+this.getNumber_of_passengers()+':'+this.getVehicle_status()+":"+this.getImage();
		
		return detailRecord;
	}
	
}
