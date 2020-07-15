package model;

import java.util.ArrayList;
import util.DateTime;

public abstract class Vehicle
{
	private String Vehicle_id;
	private String Make;
	private String Year;
	private String Model;
	private int Number_of_passengers;
	private String Vehicle_type;
	private String Vehicle_status;
	private ArrayList<Rental_Record> rentals;
	private String image;
	protected static final int last_N_rentals = 10;
		
	protected abstract void rent(String customerId, DateTime rentDate, int numOfRentDay) throws GeneralVehicleException,VehicleIdAlreadyOcuupiedException,Exception;
	protected abstract void returnVehicle(DateTime returnDate) throws GeneralVehicleException,VehicleIdAlreadyOcuupiedException,Exception;
	protected abstract void completeMaintenance(DateTime maintenanceCompletionDate) throws VehicleIdAlreadyOcuupiedException,Exception;
	//public abstract String toString();
	
	
	// Default Constructor
	protected Vehicle()
	{
		
	}
	
	
	// Constructor to initialize values
	protected Vehicle(String Vehicle_id,String Year,String Make,String Model,int Number_of_passengers,String Vehicle_type,String Vehicle_status, ArrayList<Rental_Record> rentals,String image) 
	{
		this.Vehicle_id = Vehicle_id;
		this.Year = Year;
		this.Make = Make;
		this.Model = Model;
		this.Number_of_passengers = Number_of_passengers;
		this.Vehicle_type = Vehicle_type;
		this.Vehicle_status = Vehicle_status;
		this.rentals = rentals;
		this.image = image;
	}
	
		
	// Constructor to initialize values
	protected Vehicle(String Vehicle_id,String Year,String Make,String Model,int Number_of_passengers,String Vehicle_type,String Vehicle_status,String image) 
	{
		this.Vehicle_id = Vehicle_id;
		this.Year = Year;
		this.Make = Make;
		this.Model = Model;
		this.Number_of_passengers = Number_of_passengers;
		this.Vehicle_type = Vehicle_type;
		this.Vehicle_status = Vehicle_status;
		this.rentals = new ArrayList<Rental_Record>();
		this.image = image;
		
	}
	
	
	// method to perform maintenance
	protected void performMaintenance() throws VehicleIdAlreadyOcuupiedException,Exception
	{	
			if(this.getVehicle_status().equals("Available")) 
			{
				this.setVehicle_status("Maintenance");
				
				ConnectToDB.updateVehicleRecord(this.getVehicle_id(),this.getVehicle_status(),
    				  	null);
			}
			else
			{
				throw new VehicleIdAlreadyOcuupiedException("Vehicle : "+this.getModel()+" is Already Occupied & Not Available, So Could not go to Maintenance");
			}

	}	
	
	
	// formatted string to get details of Vehicle object
	public String toString() 
	{
		String detailRecord = this.getVehicle_id()+':'+this.getYear()+':'+this.getMake()+':'+this.getModel()+':'+this.getNumber_of_passengers()+':'+this.getVehicle_status()+":"+this.getImage();
		
		return detailRecord;
	}
		
		
	// Getter Setters for all Variables
	public String getVehicle_id() 
	{
		return Vehicle_id;
	}

	
	public void setVehicle_id(String vehicle_id) 
	{
		this.Vehicle_id = vehicle_id;
	}

	
	public String getYear() 
	{
		return Year;
	}


	public void setYear(String Year) 
	{
		this.Year = Year;
	}
	
	
	public String getMake() 
	{
		return Make;
	}


	public void setMake(String make) 
	{
		this.Make = make;
	}


	public String getModel() 
	{
		return Model;
	}


	public void setModel(String model) 
	{
		this.Model = model;
	}


	public int getNumber_of_passengers() 
	{
		return Number_of_passengers;
	}


	public void setNumber_of_passengers(int number_of_passengers) 
	{
		this.Number_of_passengers = number_of_passengers;
	}


	public String getVehicle_type() 
	{
		return Vehicle_type;
	}


	public void setVehicle_type(String vehicle_type) 
	{
		this.Vehicle_type = vehicle_type;
	}


	public String getVehicle_status() 
	{
		return Vehicle_status;
	}


	public void setVehicle_status(String vehicle_status) 
	{
		this.Vehicle_status = vehicle_status;
	}


	public ArrayList<Rental_Record> getRentals() 
	{
		return rentals;
	}


	public void setRentals(ArrayList<Rental_Record> rentals) 
	{
		this.rentals = rentals;
	}
	
	
	public String getImage() 
	{
		return image;
	}
	
	
	public void setImage(String image) 
	{
		this.image = image;
	}
	
}
