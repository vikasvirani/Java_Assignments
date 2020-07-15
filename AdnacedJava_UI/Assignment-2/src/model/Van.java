package model;

import java.util.ArrayList;

import util.DateTime;

public class Van extends Vehicle
{
	private DateTime last_maintenance_date;
	private static final double rent_per_day = 235.00;
	private static final double penalty_amount = 299.00;
	private static final int maintenance_interval = 12;
	private static final int passengers_capacity = 15;
	
	// Constructor to initialize values
	public Van(String Vehicle_id,String Year,String Make,String Model,String Vehicle_type,String Vehicle_status,DateTime last_maintenance_date,ArrayList<Rental_Record> rentals,String image)
	{
		super(Vehicle_id,Year,Make,Model,passengers_capacity,Vehicle_type,Vehicle_status,rentals,image);	
		this.last_maintenance_date = 	last_maintenance_date;
	}
	
	
	// Constructor to initialize values
	public Van(String Vehicle_id,String Year,String Make,String Model,String Vehicle_type,String Vehicle_status,DateTime last_maintenance_date,String image)
	{
		super(Vehicle_id,Year,Make,Model,passengers_capacity,Vehicle_type,Vehicle_status,image);	
		this.last_maintenance_date = 	last_maintenance_date;

	}
	
	
	// method to Rent a Car
	public void rent(String customerId, DateTime rentDate, int numOfRentDay) throws GeneralVehicleException,VehicleIdAlreadyOcuupiedException,Exception
	{	
			if(this.getVehicle_status().equals("Available"))
			{
					DateTime returndate = new DateTime(rentDate, numOfRentDay);
					DateTime next_maintenance_date = new DateTime(last_maintenance_date, maintenance_interval);
					
					if(!customerId.contains("_"))
					{
						throw new GeneralVehicleException("Invalid Customer ID : Please Enter Id which includes \"_\" in it, i.e. Format \"ANYTHING_ANYTHING\"");
					}
					
					if(DateTime.diffDays(returndate, next_maintenance_date) <= 0)
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
						throw new GeneralVehicleException("Return Date must be earlier than Next Maintenance date");
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
						rental_fee += DateTime.diffDays(returnDate,rental.getRent_date()) * rent_per_day;
					}
					else
					{
						rental_fee += DateTime.diffDays(rental.getEstimated_return_date(),rental.getRent_date()) * rent_per_day;
				
						late_fee += DateTime.diffDays(rental.getActual_return_date(), rental.getEstimated_return_date()) * penalty_amount;
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
				this.setlast_maintenance_date(completionDate);
				this.setVehicle_status("Available");
				
				String[] arrOfStr_comp = String.valueOf(completionDate).split("/");
  				String compDate_final = arrOfStr_comp[2]+"-"+arrOfStr_comp[1]+"-"+arrOfStr_comp[0];
  				
  				ConnectToDB.updateVehicleRecord(this.getVehicle_id(),this.getVehicle_status(),
						compDate_final);
				
			}
			else
			{
				throw new VehicleIdAlreadyOcuupiedException("Vehicle : "+this.getModel()+" is Not in Maintenance, So Could not Complete a Maintenance");
			}
		
	}
	
	
	// overridden method to return formatted string to get details of Vehicle object
	public String toString() 
	{
		String detailRecord = this.getVehicle_id()+':'+this.getYear()+':'+this.getMake()+':'+this.getModel()+':'+this.getNumber_of_passengers()+':'+this.getVehicle_status()+':'+this.getlast_maintenance_date()+":"+this.getImage();

		return detailRecord;
	} 
	
	
	// Getter Setters for all Variables
	public DateTime getlast_maintenance_date() 
	{
		return last_maintenance_date;
	}

	
	public void setlast_maintenance_date(DateTime last_maintenance_date) 
	{
		this.last_maintenance_date = last_maintenance_date;
	}
	
}
