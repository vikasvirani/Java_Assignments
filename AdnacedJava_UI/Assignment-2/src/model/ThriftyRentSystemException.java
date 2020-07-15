package model;

@SuppressWarnings("serial")
class VehicleIdAlreadyExistException extends Exception 
{	
	// Reason of the exception
	private String reason;	
	
	
	// Constructor to set the reason  
	public VehicleIdAlreadyExistException(String reason) 
	{
		this.reason = reason;
	}	
	    
	// Get reason of the exception
	public String getReason() 
	{	
		return reason; 
	}
	   
}	   			
 

@SuppressWarnings("serial")
class VehicleIdAlreadyOcuupiedException extends Exception
{
	// Reason of the exception
	private String reason;
	
	
	// Constructor to set the reason
	public VehicleIdAlreadyOcuupiedException(String reason)
	{
		this.reason = reason;
	}
	
	
	// Get reason of the exception
	public String getReason() 
	{	
		return reason; 
	}
}


@SuppressWarnings("serial")
class VehicleIdNotAvailableException extends Exception
{
	// Reason of the exception
	private String reason;

	
	// Constructor to set the reason
	public VehicleIdNotAvailableException(String reason)
	{
		this.reason = reason;
	}

	
	// Get reason of the exception
	public String getReason() 
	{	
		return reason; 
	}
	
}


@SuppressWarnings("serial")
class GeneralVehicleException extends Exception
{
	// Reason of the exception
	private String reason;

	
	// Constructor to set the reason
	public GeneralVehicleException(String reason)
	{
		this.reason = reason;
	}
	
	
	// Get reason of the exception
	public String getReason() 
	{	
		return reason; 
	}

}