package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class DialogController implements Initializable
{
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
        //When button clicked, load window and pass data
        
    }
	

	public class CancelController implements EventHandler<ActionEvent> 
	{
		@Override // Override the handle method
		public void handle(ActionEvent e) 
		{
			//Object source = e.getSource();		
			//Button pressed = (Button)e.getSource();
			System.exit(0);
		}
	}
	
	
	public class AddVehicleController implements EventHandler<ActionEvent> 
	{
		@Override // Override the handle method
		public void handle(ActionEvent e) 
		{			
			//Button pressed = (Button)e.getSource();          
		}
		
		
		public String addCar(String idField,String yearField,
				String makeField,String modelField,String numSeatsField,String image) 
		{
			
			if(idField.isEmpty() || yearField.isEmpty() || makeField.isEmpty() || modelField.isEmpty() || numSeatsField.isEmpty() || image.isEmpty()) 
			{
				return null;
			}
			
			model.ThriftyRentSystem trs = new model.ThriftyRentSystem();
			String message = trs.addCar(idField,yearField,makeField,modelField,Integer.valueOf(numSeatsField),null,image);

			return message;
						
		}
		
		
		public String addVan(String idField,String yearField,
				String makeField,String modelField,String numSeatsField,String maintenance_date_field,String image) 
		{
			
			if(idField.isEmpty() || yearField.isEmpty() || makeField.isEmpty() || modelField.isEmpty() || numSeatsField.isEmpty() || maintenance_date_field.length() != 10 || image.isEmpty()) 
			{
				return null;
			}
			
			model.ThriftyRentSystem trs = new model.ThriftyRentSystem();
			String message = trs.addVan(idField,yearField,makeField,modelField,Integer.valueOf(numSeatsField),null,maintenance_date_field,image);

			return message;
		}
	}
	
	
	public class VehicleOperationsController implements EventHandler<ActionEvent> 
	{
		@Override // Override the handle method
		public void handle(ActionEvent e) 
		{			
			//Button pressed = (Button)e.getSource();          
		}
		
		
		public String rentVehicle(String idField,String cusField,String numDaysField, String Rent_date_field) 
		{
			
			if(idField.isEmpty() || cusField.isEmpty() || numDaysField.isEmpty() || Rent_date_field.length() != 10) 
			{
				return null;
			}
			
			model.ThriftyRentSystem trs = new model.ThriftyRentSystem();
			String message = trs.rentVehicle(idField, cusField, numDaysField, Rent_date_field);
			return message;
						
		}
		
		
		public String returnVehicle(String idField, String Return_date_field) 
		{
			
			if(idField.isEmpty() || Return_date_field.length() != 10) 
			{
				return null;
			}
			
			model.ThriftyRentSystem trs = new model.ThriftyRentSystem();
			String message = trs.returnVehicle(idField, Return_date_field);
			return message;
			
		}
		
		
		public String performMaintenance(String idField) 
		{
			
			if(idField.isEmpty()) 
			{
				return null;
			}
			
			model.ThriftyRentSystem trs = new model.ThriftyRentSystem();
			String message = trs.performMaintenance(idField);
			return message;
						
		}
		
		
		public String completeMaintenance(String idField,String maintenance_date_field) 
		{
			
			if(idField.isEmpty()) 
			{
				return null;
			}
			else
			{
				if(idField.startsWith("V_") && maintenance_date_field.length() != 10) 
				{
					return null;
				}
			}
			
			model.ThriftyRentSystem trs = new model.ThriftyRentSystem();
			String message = trs.completeMaintenance(idField, maintenance_date_field);
			return message;
		}
	}
	
	
	public class FilterController implements EventHandler<ActionEvent> 
	{
		@Override // Override the handle method
		public void handle(ActionEvent e) 
		{
			//Button pressed = (Button)e.getSource();          
		}
		
		
		public ObservableList<model.Vehicle> handleFilter(String type,int seats,
				String status,String make) 
		{
			
			model.ThriftyRentSystem trs = new model.ThriftyRentSystem();
			
			try 
			{
				ObservableList<model.Vehicle> Observable_vehicles = FXCollections.observableArrayList(trs.handleFilter(type,seats,status,make));
				return Observable_vehicles;
			}
			catch(Exception e) 
			{
				return null;
			}
		}
	}
	
	
	public class ImportController implements EventHandler<ActionEvent> 
	{
		@Override // Override the handle method
		public void handle(ActionEvent e) 
		{
			//Button pressed = (Button)e.getSource();
		}
		
		
		public String handleImport(String file) 
		{	
			model.ThriftyRentSystem trs = new model.ThriftyRentSystem();
			
			try 
			{
				String result = trs.handleImport(file);
				return result;
			}
			catch(Exception e) 
			{
				return e.getMessage();
			}
		}
	}
	
	
	public class DemoController implements EventHandler<ActionEvent> 
	{
		@Override // Override the handle method
		public void handle(ActionEvent e) 
		{
			//Button pressed = (Button)e.getSource();
		}
		
		
		public String CreateRecords() 
		{	
			try 
			{
				model.ConnectToDB.createRecords();
				return "Success";
			}
			catch(Exception e) 
			{
				return e.getMessage();
			}
		}
		
		
		public String DeleteRecords() 
		{	
			try 
			{
				model.ConnectToDB.deleteRecords();
				return "Success";
			}
			catch(Exception e) 
			{
				return e.getMessage();
			}
		}
		
	}
	
	
	public class ExportController implements EventHandler<ActionEvent> 
	{
		@Override // Override the handle method
		public void handle(ActionEvent e) 
		{
			//Button pressed = (Button)e.getSource();	
		}
		
		
		public String handleExport(String path) 
		{
			model.ThriftyRentSystem trs = new model.ThriftyRentSystem();			
					
			try 
			{
				String result = trs.handleExport(path);
				return result;
			}
			catch(Exception e) 
			{
				return e.getMessage();
			}
		}
	}

}
