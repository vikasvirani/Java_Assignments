package model;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

import util.DateTime;

public class ThriftyRentSystem
{
	//private static final int total_vehicles = 50;
	private HashMap<String,Vehicle> VehicleMap = new HashMap<String,Vehicle>(ConnectToDB.fetchRecords());
	private ObservableList<model.Vehicle> Observable_vehicles = FXCollections.observableArrayList(VehicleMap.values());
	
	public ObservableList<model.Vehicle> getObservable_vehicles() 
	{
		return this.Observable_vehicles;
	}


	public void setObservable_vehicles(ObservableList<model.Vehicle> observable_vehicles) 
	{
		Observable_vehicles = observable_vehicles;
	}

	
	public HashMap<String, Vehicle> getVehicleMap() 
	{
		return VehicleMap;
	}


	public void setVehicleMap(HashMap<String, Vehicle> vehicleMap) 
	{
		VehicleMap = vehicleMap;
	}
	
	
	//method to perform renting vehicle operation
	public String rentVehicle(String idField,String cusField,String numDaysField, String Rent_date_field)
	{
		String message = "";
		try
		{
			if(!VehicleMap.containsKey(idField) || !VehicleMap.get(idField).getVehicle_status().equals("Available"))
			{
				throw new VehicleIdNotAvailableException("Vehicle : "+VehicleMap.get(idField).getModel()+" is Not Available to Rent");
			}

			String[] arrOfStr1 = Rent_date_field.split("-");
	
			DateTime rentDate_final = new DateTime(Integer.valueOf(arrOfStr1[2]),Integer.valueOf(arrOfStr1[1]),Integer.valueOf(arrOfStr1[0]));
				
			//calling overridden rent() method
			VehicleMap.get(idField).rent(cusField, rentDate_final, Integer.valueOf(numDaysField));
			
			return "Success";
				 
		}
		catch(VehicleIdNotAvailableException e)
		{
			message = "Error:"+e.getReason();
		}
		catch(GeneralVehicleException e)
		{
	    	message = "Error:"+e.getReason();
		}
	    catch(VehicleIdAlreadyOcuupiedException e)
		{
	    	message = "Error:"+e.getReason();
		}
		catch(Exception e)
		{
			message = "Error:"+e.getMessage();
		}
		
		return message;
	}
	
	
	//method to perform returning vehicle operation
	public String returnVehicle(String idField, String Return_date_field)
	{
		String message = "";
		try
		{
			// check if Vehicle Id available
			if(!VehicleMap.containsKey(idField))
			{
				throw new VehicleIdNotAvailableException("Vehicle : "+VehicleMap.get(idField).getModel()+" is Not available");
			}

			String[] arrOfStr2 = Return_date_field.split("-");
	
			DateTime returnDate_final = new DateTime(Integer.valueOf(arrOfStr2[2]),Integer.valueOf(arrOfStr2[1]),Integer.valueOf(arrOfStr2[0]));
				
			//calling overridden returnVehicle() method
			VehicleMap.get(idField).returnVehicle(returnDate_final);
			
			return "Success";

		}
		catch(VehicleIdNotAvailableException e)
		{
			message = "Error:"+e.getReason();
		}
		catch(GeneralVehicleException e)
		{
	    	message = "Error:"+e.getReason();
		}
	    catch(VehicleIdAlreadyOcuupiedException e)
		{
	    	message = "Error:"+e.getReason();
		}
		catch(Exception e)
		{
			message = "Error:"+e.getMessage();
		}
		
		return message;
	}
	
	
	//method to perform vehicle maintenance operation
	public String performMaintenance(String idField)
	{
		String message = "";
		try
		{	
			if(!VehicleMap.containsKey(idField))
			{
				throw new VehicleIdNotAvailableException("Vehicle : "+VehicleMap.get(idField).getModel()+" is Not Available");
			}
				
			//calling performMaintenance() method of Vehicle Object
			VehicleMap.get(idField).performMaintenance();
			
			return "Success";
				
		}
		catch(VehicleIdNotAvailableException e)
		{
			message = "Error:"+e.getReason();
		}
		catch(VehicleIdAlreadyOcuupiedException e)
		{
			message = "Error:"+e.getReason();
		}
		catch(Exception e)
		{
			message = "Error:"+e.getMessage();
		}
		
		return message;
	}
	
	
	//method to perform completing vehicle maintenance operation
	public String completeMaintenance(String idField,String maintenance_date_field)
	{	
		String message = "";
		try
		{
			// check if Vehicle Id available
			if(!VehicleMap.containsKey(idField))
			{	
				throw new VehicleIdNotAvailableException("Vehicle : "+VehicleMap.get(idField).getModel()+" is Not Available");
			}
			
			String[] arrOfStr4 = new String[3];
			
			if(idField.startsWith("V_") && maintenance_date_field.length() == 10) 
			{
				arrOfStr4 = maintenance_date_field.split("-");
				
				DateTime maintenanceCompletionDate_final = new DateTime(Integer.valueOf(arrOfStr4[2]),Integer.valueOf(arrOfStr4[1]),Integer.valueOf(arrOfStr4[0]));
				
				//calling completeMaintenance() method of Vehicle Object
				VehicleMap.get(idField).completeMaintenance(maintenanceCompletionDate_final);
			}
			else 
			{
				//calling completeMaintenance() method of Vehicle Object
				VehicleMap.get(idField).completeMaintenance(null);
			}
			
			
			
			return "Success";
								
		}
		catch(VehicleIdNotAvailableException e)
		{
			message = "Error:"+e.getReason();
		}
		catch(VehicleIdAlreadyOcuupiedException e)
		{
	    	message = "Error:"+e.getReason();
		}
		catch(Exception e)
		{
			message = "Error:"+e.getMessage();
		}
		
		return message;
	}
	
	
	// method to add Car vehicle object
	public String addCar(String Vehicle_id,String Year,String Make,String Model,int Number_of_passengers,String Vehicle_status,String image) //throws VehicleIdAlreadyExistException,GeneralVehicleException
	{
		String message = "";
		try
		{
			// check if Vehicle Id already exist & valid
			if(VehicleMap.containsKey(Vehicle_id))
			{
				throw new VehicleIdAlreadyExistException("Vehicle With this Id Already Exist");
			}
			else if(!Vehicle_id.startsWith("C_")) 
			{
				throw new GeneralVehicleException("Vehicle Id for Car should Start with \"C_\"");
			}

			
			if(Number_of_passengers != 4 && Number_of_passengers != 7)
			{	
				throw new GeneralVehicleException("Invalid Passenger Capacity for Car : It Must be Either 4 or 7");
			}
			
			String Vehicle_type = "Car";
			Vehicle_status = "Available";

			ConnectToDB.createCarRecord(Vehicle_id,Year,Make,Model,Number_of_passengers,Vehicle_type,Vehicle_status,image);
				
			return "Success";

		}
		catch(GeneralVehicleException e)
		{
			message = "Error:"+e.getReason();
		}
		catch(VehicleIdAlreadyExistException e)
		{
			message = "Error:"+e.getReason();
		}
		catch(Exception e)
		{
			message = "Error:"+e.getMessage();
		}

		return message;
	}
	
	
	// method to add Van vehicle object
	public String addVan(String Vehicle_id,String Year,String Make,String Model,int Number_of_passengers,String Vehicle_status,String maintenanceDate,String image) //throws VehicleIdAlreadyExistException,GeneralVehicleException
	{
		String message = "";
		try
		{
			// check if Vehicle Id already exist & valid
			if(VehicleMap.containsKey(Vehicle_id))
			{
				throw new VehicleIdAlreadyExistException("Vehicle With this Id Already Exist");
			}
			else if(!Vehicle_id.startsWith("V_")) 
			{
				throw new GeneralVehicleException("Vehicle Id for Van should Start with \"V_\"");
			}
				
			
			if(Number_of_passengers != 15)
			{	
				throw new GeneralVehicleException("Invalid Passenger Capacity for Van : It Must be 15");
			}
			
			String Vehicle_type = "Van";
			Vehicle_status = "Available";
			
			ConnectToDB.createVanRecord(Vehicle_id,Year,Make,Model,Vehicle_type,Vehicle_status,maintenanceDate,image);
			
			return "Success";

		}
		catch(GeneralVehicleException e)
		{
			message = "Error:"+e.getReason();
		}
		catch(VehicleIdAlreadyExistException e)
		{
			message = "Error:"+e.getReason();
		}
		catch(Exception e)
		{
			message = "Error:"+e.getMessage();
		}
		
		return message;
	}
	
	
	//method to perform filtering of vehicle operation
	public ObservableList<model.Vehicle> handleFilter(String type,int seats,String status,String make)
	{
		Observable_vehicles.clear();
		
		for(Vehicle ve : VehicleMap.values()) 
		{
			if((type.equalsIgnoreCase("All") || ve.getVehicle_type().equalsIgnoreCase(type)) && (seats == 0 || ve.getNumber_of_passengers() == seats) && (status.equalsIgnoreCase("All") || ve.getVehicle_status().equalsIgnoreCase(status)) && (make.equalsIgnoreCase("All") || ve.getMake().equalsIgnoreCase(make))) 
			{
				Observable_vehicles.add(ve);
			}
		}
		
		return Observable_vehicles;
	}
	
	
	//method to perform exporting all vehicles data with rental records into an export_data.txt file operation
	public String handleExport(String path)
	{
		String message = "";
		PrintWriter writer = null;

		try {
			writer = new PrintWriter(path+"\\export_data.txt", "UTF-8");
		
			for(Vehicle ve : VehicleMap.values()) 
			{				
				writer.println(ve.toString().trim());
				
				ArrayList<Rental_Record> records = new ArrayList<Rental_Record>();
		        	try 
		        	{  		
		        		if(!ve.getRentals().isEmpty() || ve.getRentals() != null) 
		        		{
		        			records = ve.getRentals();
		        			for(int i = 0;i < records.size(); i++)
		        			{	
		        				Rental_Record rec = records.get(i);
		        				writer.println(rec.toString().trim());
		        			}
			        	}
		        	}
		        	catch(Exception e) 
		        	{
		        		message = "Error:"+e.getMessage();
		    			return message;
		        	}
			}
			
			return null;
		}
		catch(IOException ex) 
		{
			message = "Error:"+ex.getMessage();
			return message;
		}
		catch(Exception e) 
		{
			message = "Error:"+e.getMessage();
			return message;
    	}
		finally 
		{
		   try{writer.close();} catch (Exception ex) {/*ignore*/}
		}
		
	}
	
	
	//method to perform importing all vehicles data with rental records from a text file to database operation
	public String handleImport(String file)
	{
		String message = "";
			
		BufferedReader br = null;
		
			try 
			{				
				br = new BufferedReader(new FileReader(file));
				
			    StringBuilder sb = new StringBuilder();
		    	String line = br.readLine();

			    while (line != null) {
			    	line.trim();
			    	  if(line != null && !line.isEmpty())
				      {	  
			    		sb.append(line);
				    	sb.append(System.lineSeparator());
				      }
		    		
			        line = br.readLine();
			    }
		    	String everything = sb.toString().trim();
	    		
				String[] arrofline = everything.split("\n");
				
				for(int i=0; i<=arrofline.length-1; i++) 
				{
					String[] veh = arrofline[i].split(":");
					if(veh.length > 6 && (Integer.valueOf(veh[4]) == 4 || Integer.valueOf(veh[4]) == 7)) 
					{
						veh[6] = veh[6].replace("\n", "").replace("\r", "");
						
						ConnectToDB.createCarRecord(veh[0],veh[1],veh[2],veh[3],Integer.valueOf(veh[4]), "CAR",veh[5],veh[6]);

					}
					else if(veh.length > 6 && (Integer.valueOf(veh[4]) == 15))
					{						
						veh[7] = veh[7].replace("\n", "").replace("\r", "");

						String[] arrOfStr = new String[3];
						
						arrOfStr = String.valueOf(veh[6]).split("/");					
						String maintenanceDate_str = arrOfStr[2]+"-"+arrOfStr[1]+"-"+arrOfStr[0];
						
						ConnectToDB.createVanRecord(veh[0],veh[1],veh[2],veh[3], "VAN",veh[5],maintenanceDate_str,veh[7]);

					}

				      for(int j=i+1; j<=arrofline.length-1; j++) 
				      {	
					      if(arrofline[j].split(":")[0].startsWith(arrofline[i].split(":")[0])) 
					      {  	
					    		  String[] rental = arrofline[j].split(":");
					    		  	
					    		  String[] arrOfStr_id = rental[0].split("_");
					    		  String[] arrOfStr_rent = rental[1].split("/");

					  			  String rentDate_final = arrOfStr_rent[2]+"-"+arrOfStr_rent[1]+"-"+arrOfStr_rent[0];
					  			
					  			  if(rental.length == 3) 
					  			  {
					  				  rental[2] = rental[2].replace("\n", "").replace("\r", "");
						    		  String[] arrOfStr_est_ret = rental[2].split("/");
						  			  String EstretDate_final = arrOfStr_est_ret[2]+"-"+arrOfStr_est_ret[1]+"-"+arrOfStr_est_ret[0];
						    		  
						  			  ConnectToDB.createRentals(rental[0], 
						    				  	arrOfStr_id[0]+"_"+arrOfStr_id[1],
						    				  	arrOfStr_id[2]+"_"+arrOfStr_id[3],
						    				  	rentDate_final,
						    				  	EstretDate_final,
						    				  	null,
						    				  	null,
						    				  	null);
					  				
					  			  }
					  			  else 
					  			  {
					  				  rental[5] = rental[5].replace("\n", "").replace("\r", "");
					  				  String[] arrOfStr_est_ret = rental[2].split("/");
						  			  String EstretDate_final = arrOfStr_est_ret[2]+"-"+arrOfStr_est_ret[1]+"-"+arrOfStr_est_ret[0];
						  			  
					  				  String[] arrOfStr_return = rental[3].split("/");
					  				  String returnDate_final = arrOfStr_return[2]+"-"+arrOfStr_return[1]+"-"+arrOfStr_return[0];
						  			  
					  				  ConnectToDB.createRentals(rental[0], 
					    				  	arrOfStr_id[0]+"_"+arrOfStr_id[1],
					    				  	arrOfStr_id[2]+"_"+arrOfStr_id[3],
					    				  	rentDate_final,
					    				  	EstretDate_final,
					    				  	returnDate_final,
					    				  	Double.valueOf(rental[4]),
					    				  	Double.valueOf(rental[5]));
					  				
					  			  }
					  			  
					  			  if(j == arrofline.length-1) 
					  			  {
					  				i = j;
							    	break;
					  			  }
						  }
					      else 
					      {
					    	  i = j-1;
					    	  break;
					      }
					  }
				      
				}
				
				return message;
			}
			catch(IOException ex) 
			{
				message = "Error:"+ex.getMessage();
				return message;
			} 
			catch(Exception e) 
			{
				message = "Error:"+e.getMessage();
				return message;
	    	}
		    finally 
		    {
		    	try{br.close();} catch (Exception ex) {/*ignore*/}
			}

	}	
	
}
