package thriftyRent;

import util.DateTime;

/**
 * The Example class provides ...
 */
public interface IVehicle {
	
	public boolean rent(String CustomerId, DateTime rentDate, int numOfRentDays) throws Exception;
	public boolean returnVehicle(DateTime returnDate) throws Exception;
	public boolean performMaintenance() throws Exception;
	public boolean completeMaintenance(DateTime completitionDate) throws Exception;
	public String toString();
	public String getDetails(boolean displayAll);

}
