package model;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import util.DateTime;


public class ConnectToDB {
    
	
//    public static void main(String[] args)
//    {
//    	try 
//    	{
//    		//setupTables();	
//        	createRecords();
//    	}
//    	catch(Exception e) 
//    	{
//    		System.out.println(e.getMessage());
//    	}
//    }
    
    
	//Method to check if Table Exists or not
    public static void checkTables() throws Exception
    {
    	final String DB_NAME = "testDB";
		final String TABLE_NAME_VEHICLE = "VEHICLES";
    	final String TABLE_NAME_RENTAL = "RENTAL_RECORDS";
    	
    	   try(Connection conn = ConnectToDB.getConnection(DB_NAME);
   				Statement stmt = conn.createStatement();
    	   ) 
    	   {
    	      
    		    DatabaseMetaData dbm = conn.getMetaData();
	   			ResultSet tables = dbm.getTables(null, null, TABLE_NAME_VEHICLE.toUpperCase(), null);
	   			
	   			if(tables != null) 
	   			{
	   				if (tables.next()) 
	   				{
	   					//Table Exist
	   					//System.out.println("Table " + TABLE_NAME_VEHICLE + " exists.");
	   				}
	   				else 
	   				{
	   					//Table does not Exist, Create Table
	   					String sql = "CREATE TABLE VEHICLES " +
	     	                   "(Vehicle_id VARCHAR(255) not NULL, " +
	     	                   " Image_name VARCHAR(255), " +
	     	                   " Year INTEGER, " + 
	     	                   " Make VARCHAR(255), " + 
	     	                   " Model VARCHAR(255), " + 
	     	                   " Number_of_passengers INTEGER, " + 
	     	                   " Vehicle_type VARCHAR(255), " +
	     	                   " Vehicle_status VARCHAR(255), " + 
	     	                   " Last_maintenance_date DATE, " +
	     	                   " PRIMARY KEY ( Vehicle_id ))"; 

	   					stmt.executeUpdate(sql);
	   				}
	   				
	   				tables.close();
	   			} 
	   			else 
	   			{
	   				throw new Exception("Problem with retrieving Database Metadata");
	   			}
	   			
	   			
	   			DatabaseMetaData dbm1 = conn.getMetaData();
	   			ResultSet tables1 = dbm1.getTables(null, null, TABLE_NAME_RENTAL.toUpperCase(), null);
	   			
	   			if(tables1 != null) 
	   			{
	   				if (tables1.next()) 
	   				{
	   					//Table Exist
	   					//System.out.println("Table " + TABLE_NAME_RENTAL + " exists.");
	   				}
	   				else 
	   				{
	   					//Table does not Exist, Create Table
	   					String sql1 = "CREATE TABLE RENTAL_RECORDS " +
	   		                   "(record_id VARCHAR(255) not NULL, " +
	   		                   " Vehicle_id VARCHAR(255), " + 
	   		                   " Customer_id VARCHAR(255), " + 
	   		                   " rent_date DATE, " + 
	   		                   " estimated_return_date DATE, " +
	   		                   " actual_return_date DATE, " + 
	   		                   " rental_fee DECIMAL, " +
	   		                   " late_fee DECIMAL, " +
	   		                   " PRIMARY KEY ( record_id ), " +
	   		                   " Foreign Key (Vehicle_id) REFERENCES VEHICLES(Vehicle_id))"; 

	   					stmt.executeUpdate(sql1);
	   				}
	   				
	   				tables1.close();
	   			} 
	   			else 
	   			{
	   				throw new Exception("Problem with retrieving Database Metadata");
	   			}
    	      
    	   }
    	   catch(SQLException se)
    	   {
    	      //Handle errors for JDBC
    	      //se.printStackTrace();
    	      throw se;
    	   }
    	   catch(Exception e)
    	   {
    	      //Handle errors for Class.forName
    	      //e.printStackTrace();
    	      throw e;
    	   }
    }
    
    
	//Backup Method to Create Table if needed
    public static void setupTables() throws Exception
    {
    	final String DB_NAME = "testDB";
    	
    	   try(Connection conn = ConnectToDB.getConnection(DB_NAME);
   				Statement stmt = conn.createStatement();
    	   ) 
    	   {

    	      String sql = "CREATE TABLE VEHICLES " +
    	                   "(Vehicle_id VARCHAR(255) not NULL, " +
    	                   " Image_name VARCHAR(255), " +
    	                   " Year INTEGER, " + 
    	                   " Make VARCHAR(255), " + 
    	                   " Model VARCHAR(255), " + 
    	                   " Number_of_passengers INTEGER, " + 
    	                   " Vehicle_type VARCHAR(255), " +
    	                   " Vehicle_status VARCHAR(255), " + 
    	                   " Last_maintenance_date DATE, " +
    	                   " PRIMARY KEY ( Vehicle_id ))"; 

    	      stmt.executeUpdate(sql);
    	      
    	      String sql1 = "CREATE TABLE RENTAL_RECORDS " +
	                   "(record_id VARCHAR(255) not NULL, " +
	                   " Vehicle_id VARCHAR(255), " + 
	                   " Customer_id VARCHAR(255), " + 
	                   " rent_date DATE, " + 
	                   " estimated_return_date DATE, " +
	                   " actual_return_date DATE, " + 
	                   " rental_fee DECIMAL, " +
	                   " late_fee DECIMAL, " +
	                   " PRIMARY KEY ( record_id ), " +
	                   " Foreign Key (Vehicle_id) REFERENCES VEHICLES(Vehicle_id))"; 

    	      stmt.executeUpdate(sql1);
    	      
    	   }
    	   catch(SQLException se)
    	   {
    	      //Handle errors for JDBC
    	      //se.printStackTrace();
    	      throw se;
    	   }
    	   catch(Exception e)
    	   {
    	      //Handle errors for Class.forName
    	      //e.printStackTrace();
    		   throw e;
    	   }
    }
    
    
    //Method to Update Rental Records in the Database
    public static void updateRentalRecord(String record_id,String date,Double rent_fee, Double late_fee) throws Exception
    {
    	
    	final String DB_NAME = "testDB";
    	final String TABLE_NAME_RENTAL = "RENTAL_RECORDS";
		
		//use try-with-resources Statement
		try (Connection con = ConnectToDB.getConnection(DB_NAME);
				Statement stmt = con.createStatement();
		) 
		{
			String[] queries_rentals;
			
			if(date != null) 
			{
				queries_rentals = new String[] {"UPDATE " + TABLE_NAME_RENTAL + " SET rental_fee="+ rent_fee+",late_fee="+ late_fee+",actual_return_date='"+date+"' WHERE record_id='"+record_id+"'"};
			}
			else 
			{
				queries_rentals = new String[] {"UPDATE " + TABLE_NAME_RENTAL + " SET rental_fee="+ rent_fee+",late_fee="+ late_fee+" WHERE record_id='"+record_id+"'"};
			}
			
			for(String st : queries_rentals) 
			{
				stmt.executeUpdate(st);
			}
			
			con.commit();
			
		} 
		catch (Exception e) 
		{
			throw new Exception(e); 
		}
    	
    }

    
    //Method to Update Vehicle Records in the Database
    public static void updateVehicleRecord(String Vehicle_id,String status,String date) throws Exception
    {
    	
    	final String DB_NAME = "testDB";
		final String TABLE_NAME_VEHICLE = "VEHICLES";
		
		//use try-with-resources Statement
		try (Connection con = ConnectToDB.getConnection(DB_NAME);
				Statement stmt = con.createStatement();
		) 
		{
			String[] queries_vehicle;
			
			if(date != null && !date.isEmpty()) 
			{
				queries_vehicle = new String[] {"UPDATE " + TABLE_NAME_VEHICLE + " SET Vehicle_status='"+ status+"', Last_maintenance_date='"+date+"' WHERE Vehicle_id='"+Vehicle_id+"'"};
			}
			else 
			{
				queries_vehicle = new String[] {"UPDATE " + TABLE_NAME_VEHICLE + " SET Vehicle_status='"+ status+"' WHERE Vehicle_id='"+Vehicle_id+"'"};
			}
			
			for(String st : queries_vehicle) 
			{
				stmt.executeUpdate(st);
			}
			
			con.commit();

		} 
		catch (Exception e) 
		{
			throw new Exception(e);
		}
    	
    }


    //Method to Create Car Records in the Database
    public static void createCarRecord(String Vehicle_id,String Year,String Make,String Model,int Number_of_passengers,String Vehicle_type,String Vehicle_status,String image) throws Exception
    {
    	
    	final String DB_NAME = "testDB";
		final String TABLE_NAME_VEHICLE = "VEHICLES";
		
		//use try-with-resources Statement
		try (Connection con = ConnectToDB.getConnection(DB_NAME);
				Statement stmt = con.createStatement();
		) 
		{
			String[] queries_vehicle = new String[] {"INSERT INTO " + TABLE_NAME_VEHICLE + " VALUES ('"+Vehicle_id+"',"+ Year+", '"+Make+"','"+Model+"',"+Number_of_passengers+", '"+Vehicle_type+"', '"+Vehicle_status+"',NULL,'"+image+"')"};
			
			for(String st : queries_vehicle) 
			{
				stmt.executeUpdate(st);
			}
			
			con.commit();

		} 
		catch (Exception e) 
		{
			throw new Exception(e);
		}
    	
    }
    

    //Method to Create Van Records in the Database
	public static void createVanRecord(String Vehicle_id,String Year,String Make,String Model,String Vehicle_type,String Vehicle_status,String maintenanceDate,String image) throws Exception
	{
		
		final String DB_NAME = "testDB";
		final String TABLE_NAME_VEHICLE = "VEHICLES";
		
		//use try-with-resources Statement
		try (Connection con = ConnectToDB.getConnection(DB_NAME);
				Statement stmt = con.createStatement();
		) 
		{
			String[] queries_vehicle = new String[] {"INSERT INTO " + TABLE_NAME_VEHICLE + " VALUES ('"+Vehicle_id+"',"+ Year+", '"+Make+"','"+Model+"',15, '"+Vehicle_type+"', '"+Vehicle_status+"', '"+maintenanceDate+"','"+image+"')"};
			
			for(String st : queries_vehicle) 
			{
				stmt.executeUpdate(st);
			}
			
			con.commit();
	
		} 
		catch (Exception e) 
		{
			throw new Exception(e);
		}
		
	}
	
    
	//Method to Create Rental Records in the Database
	public static void createRentals(String record_id, String Vehicle_id,String Customer_id,String rent_date, String estimated_return_date,String actual_return_date, Double rental_fee, Double late_fee) throws Exception
	{
    	
    	final String DB_NAME = "testDB";
		final String TABLE_NAME_RENTAL = "RENTAL_RECORDS";
		//use try-with-resources Statement
		try (Connection con = ConnectToDB.getConnection(DB_NAME);
				Statement stmt = con.createStatement();
		) 
		{
			String[] queries_rentals;
			
			if(actual_return_date != null && !actual_return_date.isEmpty()) 
			{
				queries_rentals = new String[] {"INSERT INTO " + TABLE_NAME_RENTAL + " VALUES ('"+record_id+"','"+ Vehicle_id+"', '"+Customer_id+"','"+rent_date+"','"+estimated_return_date+"', '"+actual_return_date+"', "+rental_fee+","+late_fee+")"};
			}
			else 
			{
				queries_rentals = new String[] {"INSERT INTO " + TABLE_NAME_RENTAL + " VALUES ('"+record_id+"','"+ Vehicle_id+"', '"+Customer_id+"','"+rent_date+"','"+estimated_return_date+"', "+actual_return_date+", "+rental_fee+","+late_fee+")"};
			}
					
			
			for(String st : queries_rentals) 
			{
				if(st != null) 
				{
					stmt.executeUpdate(st);
				}
			}
			
			con.commit();

		} 
		catch (Exception e) 
		{
			throw new Exception(e);
		}
    	
    }

	
	//Method to Delete Current Records in the Database to Demonstrate IMPORT DATA Functionality
    public static void deleteRecords() throws Exception
    {
    	
    	final String DB_NAME = "testDB";
		final String TABLE_NAME_RENTAL = "RENTAL_RECORDS";
		final String TABLE_NAME_VEHICLE = "VEHICLES";
		//use try-with-resources Statement
		try (Connection con = ConnectToDB.getConnection(DB_NAME);
				Statement stmt = con.createStatement();
		) 
		{
			
			String[] queries = new String[] {"DELETE FROM " + TABLE_NAME_RENTAL};
			
			for(String st : queries) 
			{
				stmt.executeUpdate(st);
			}
			
			
			String[] queries_vehicle = new String[] {"DELETE FROM " + TABLE_NAME_VEHICLE};
			
			for(String st : queries_vehicle) 
			{
				stmt.executeUpdate(st);
			}
			
			con.commit();

		} 
		catch (Exception e) 
		{
			throw new Exception(e);
		}
    	
    }
    
    
	//Method to Create Demo Records in the Database
    public static void createRecords() throws Exception
    {
    	
    	final String DB_NAME = "testDB";
		final String TABLE_NAME_RENTAL = "RENTAL_RECORDS";
		final String TABLE_NAME_VEHICLE = "VEHICLES";
		//use try-with-resources Statement
		try (Connection con = ConnectToDB.getConnection(DB_NAME);
				Statement stmt = con.createStatement();
		) 
		{
			String[] queries_vehicle = new String[] {"INSERT INTO " + TABLE_NAME_VEHICLE + " VALUES ('C_001', 2012, 'Toyota', 'Corolla', 4, 'CAR', 'Available',NULL,'Corolla.jpg')",
					"INSERT INTO " + TABLE_NAME_VEHICLE + " VALUES ('C_002', 2013, 'Honda', 'City', 4, 'CAR', 'Available',NULL,'City.jpg')",
					"INSERT INTO " + TABLE_NAME_VEHICLE + " VALUES ('C_003', 2014, 'Nissan', 'Pathfinder', 7, 'CAR', 'Available',NULL,'Pathfinder.jpg')",
					"INSERT INTO " + TABLE_NAME_VEHICLE + " VALUES ('C_004', 2015, 'Hyundai', 'Elantra', 4, 'CAR', 'Available',NULL,'Elantra.jpg')",
					"INSERT INTO " + TABLE_NAME_VEHICLE + " VALUES ('C_005', 2016, 'Toyota', 'Camry', 4, 'CAR', 'Available',NULL,'Camry.jpg')",
					"INSERT INTO " + TABLE_NAME_VEHICLE + " VALUES ('C_006', 2017, 'Audi', 'Q7', 7, 'CAR', 'Available',NULL,'Q7.jpg')",
					"INSERT INTO " + TABLE_NAME_VEHICLE + " VALUES ('C_007', 2018, 'Mazda', 'CX5', 4, 'CAR', 'Available',NULL,'CX5.jpg')",
					"INSERT INTO " + TABLE_NAME_VEHICLE + " VALUES ('C_008', 2019, 'Mazda', 'Mazda6', 4, 'CAR', 'Available',NULL,'Mazda6.jpg')",
					"INSERT INTO " + TABLE_NAME_VEHICLE + " VALUES ('C_009', 2006, 'Audi', 'A8', 4, 'CAR', 'Available',NULL,'A8.jpg')",
					"INSERT INTO " + TABLE_NAME_VEHICLE + " VALUES ('C_010', 2008, 'Mazda', 'CX9', 7, 'CAR', 'Available',NULL,'CX9.jpg')",
					"INSERT INTO " + TABLE_NAME_VEHICLE + " VALUES ('V_001', 2012, 'Mercedez', 'Sprinter', 15, 'VAN', 'Available','2019-05-05','Sprinter.jpg')",
					"INSERT INTO " + TABLE_NAME_VEHICLE + " VALUES ('V_002', 2008, 'Renault', 'Trafic', 15, 'VAN', 'Available','2019-05-05','Trafic.jpg')",
					"INSERT INTO " + TABLE_NAME_VEHICLE + " VALUES ('V_003', 2009, 'Volkswagen', 'Caddy', 15, 'VAN', 'Available','2019-05-05','Caddy.jpg')",
					"INSERT INTO " + TABLE_NAME_VEHICLE + " VALUES ('V_004', 2015, 'Volkswagen', 'Transporter', 15, 'VAN', 'Available','2019-05-05','Transporter.jpg')",
					"INSERT INTO " + TABLE_NAME_VEHICLE + " VALUES ('V_005', 2017, 'Ford', 'Transit', 15, 'VAN', 'Available','2019-05-05','Transit.jpg')"};
			
			for(String st : queries_vehicle) 
			{
				stmt.executeUpdate(st);
			}
			
			
			String[] queries = new String[] {"INSERT INTO " + TABLE_NAME_RENTAL + " VALUES ('C_001_CUS_001_2019-05-03', 'C_001', 'CUS_001', '2019-05-03', '2019-05-05', '2019-05-05', 156.0, 0.0)",
					"INSERT INTO " + TABLE_NAME_RENTAL + " VALUES ('C_001_CUS_002_2019-05-01', 'C_001', 'CUS_002', '2019-05-01', '2019-05-02', '2019-05-02', 78.0, 0.0)",
					"INSERT INTO " + TABLE_NAME_RENTAL + " VALUES ('C_001_CUS_003_2019-04-25', 'C_001', 'CUS_003', '2019-04-25', '2019-04-30', '2019-04-30', 390.0, 0.0)",
					"INSERT INTO " + TABLE_NAME_RENTAL + " VALUES ('C_002_CUS_004_2019-05-01', 'C_002', 'CUS_004', '2019-05-01', '2019-05-02', '2019-05-02', 78.0, 0.0)",
					"INSERT INTO " + TABLE_NAME_RENTAL + " VALUES ('C_002_CUS_005_2019-05-03', 'C_002', 'CUS_005', '2019-05-03', '2019-05-05', '2019-05-05', 156.0, 0.0)",
					"INSERT INTO " + TABLE_NAME_RENTAL + " VALUES ('C_003_CUS_006_2019-05-03', 'C_003', 'CUS_006', '2019-05-03', '2019-05-05', '2019-05-05', 226.0, 0.0)",
					"INSERT INTO " + TABLE_NAME_RENTAL + " VALUES ('C_003_CUS_007_2019-05-01', 'C_003', 'CUS_007', '2019-05-01', '2019-05-02', '2019-05-02', 113.0, 0.0)",
					"INSERT INTO " + TABLE_NAME_RENTAL + " VALUES ('C_004_CUS_001_2019-05-03', 'C_004', 'CUS_001', '2019-05-03', '2019-05-05', '2019-05-05', 156.0, 0.0)",
					"INSERT INTO " + TABLE_NAME_RENTAL + " VALUES ('C_004_CUS_008_2019-05-01', 'C_004', 'CUS_008', '2019-05-01', '2019-05-02', '2019-05-02', 78.0, 0.0)",
					"INSERT INTO " + TABLE_NAME_RENTAL + " VALUES ('C_004_CUS_009_2019-04-25', 'C_004', 'CUS_009', '2019-04-25', '2019-04-30', '2019-04-30', 390.0, 0.0)",
					"INSERT INTO " + TABLE_NAME_RENTAL + " VALUES ('C_005_CUS_010_2019-05-03', 'C_005', 'CUS_010', '2019-05-03', '2019-05-05', '2019-05-05', 156.0, 0.0)",
					"INSERT INTO " + TABLE_NAME_RENTAL + " VALUES ('C_005_CUS_011_2019-05-01', 'C_005', 'CUS_011', '2019-05-01', '2019-05-02', '2019-05-02', 78.0, 0.0)",
					"INSERT INTO " + TABLE_NAME_RENTAL + " VALUES ('C_005_CUS_012_2019-04-25', 'C_005', 'CUS_012', '2019-04-25', '2019-04-30', '2019-04-30', 390.0, 0.0)",
					"INSERT INTO " + TABLE_NAME_RENTAL + " VALUES ('C_006_CUS_013_2019-05-03', 'C_006', 'CUS_013', '2019-05-03', '2019-05-05', '2019-05-05', 226.0, 0.0)",
					"INSERT INTO " + TABLE_NAME_RENTAL + " VALUES ('C_006_CUS_014_2019-05-01', 'C_006', 'CUS_014', '2019-05-01', '2019-05-02', '2019-05-02', 113.0, 0.0)",
					"INSERT INTO " + TABLE_NAME_RENTAL + " VALUES ('C_007_CUS_015_2019-05-03', 'C_007', 'CUS_015', '2019-05-03', '2019-05-05', '2019-05-05', 156.0, 0.0)",
					"INSERT INTO " + TABLE_NAME_RENTAL + " VALUES ('C_007_CUS_016_2019-05-01', 'C_007', 'CUS_016', '2019-05-01', '2019-05-02', '2019-05-02', 78.0, 0.0)",
					"INSERT INTO " + TABLE_NAME_RENTAL + " VALUES ('C_008_CUS_017_2019-05-03', 'C_008', 'CUS_017', '2019-05-03', '2019-05-05', '2019-05-05', 156.0, 0.0)",
					"INSERT INTO " + TABLE_NAME_RENTAL + " VALUES ('C_008_CUS_018_2019-05-01', 'C_008', 'CUS_018', '2019-05-01', '2019-05-02', '2019-05-02', 78.0, 0.0)",
					"INSERT INTO " + TABLE_NAME_RENTAL + " VALUES ('C_009_CUS_019_2019-05-03', 'C_009', 'CUS_019', '2019-05-03', '2019-05-05', '2019-05-05', 156.0, 0.0)",
					"INSERT INTO " + TABLE_NAME_RENTAL + " VALUES ('C_009_CUS_020_2019-05-01', 'C_009', 'CUS_020', '2019-05-01', '2019-05-02', '2019-05-02', 78.0, 0.0)",
					"INSERT INTO " + TABLE_NAME_RENTAL + " VALUES ('C_010_CUS_021_2019-05-03', 'C_010', 'CUS_021', '2019-05-03', '2019-05-05', '2019-05-05', 156.0, 0.0)",
					"INSERT INTO " + TABLE_NAME_RENTAL + " VALUES ('C_010_CUS_022_2019-05-01', 'C_010', 'CUS_022', '2019-05-01', '2019-05-02', '2019-05-02', 78.0, 0.0)",
					"INSERT INTO " + TABLE_NAME_RENTAL + " VALUES ('C_010_CUS_023_2019-04-25', 'C_010', 'CUS_023', '2019-04-25', '2019-04-30', '2019-04-30', 390.0, 0.0)",
					"INSERT INTO " + TABLE_NAME_RENTAL + " VALUES ('V_001_CUS_024_2019-05-03', 'V_001', 'CUS_024', '2019-05-03', '2019-05-05', '2019-05-05', 470.0, 0.0)",
					"INSERT INTO " + TABLE_NAME_RENTAL + " VALUES ('V_001_CUS_025_2019-05-01', 'V_001', 'CUS_025', '2019-05-01', '2019-05-02', '2019-05-02', 235.0, 0.0)",
					"INSERT INTO " + TABLE_NAME_RENTAL + " VALUES ('V_002_CUS_026_2019-05-03', 'V_002', 'CUS_026', '2019-05-03', '2019-05-05', '2019-05-05', 470.0, 0.0)",
					"INSERT INTO " + TABLE_NAME_RENTAL + " VALUES ('V_002_CUS_027_2019-05-01', 'V_002', 'CUS_027', '2019-05-01', '2019-05-02', '2019-05-02', 235.0, 0.0)",
					"INSERT INTO " + TABLE_NAME_RENTAL + " VALUES ('V_003_CUS_028_2019-05-03', 'V_003', 'CUS_028', '2019-05-03', '2019-05-05', '2019-05-05', 470.0, 0.0)",
					"INSERT INTO " + TABLE_NAME_RENTAL + " VALUES ('V_003_CUS_029_2019-05-01', 'V_003', 'CUS_029', '2019-05-01', '2019-05-02', '2019-05-02', 235.0, 0.0)",
					"INSERT INTO " + TABLE_NAME_RENTAL + " VALUES ('V_003_CUS_030_2019-04-25', 'V_003', 'CUS_030', '2019-04-25', '2019-04-30', '2019-04-30', 1175.0, 0.0)",
					"INSERT INTO " + TABLE_NAME_RENTAL + " VALUES ('V_004_CUS_031_2019-05-03', 'V_004', 'CUS_031', '2019-05-03', '2019-05-05', '2019-05-05', 470.0, 0.0)",
					"INSERT INTO " + TABLE_NAME_RENTAL + " VALUES ('V_004_CUS_032_2019-05-01', 'V_004', 'CUS_032', '2019-05-01', '2019-05-02', '2019-05-02', 235.0, 0.0)",
					"INSERT INTO " + TABLE_NAME_RENTAL + " VALUES ('V_005_CUS_033_2019-05-03', 'V_005', 'CUS_033', '2019-05-03', '2019-05-05', '2019-05-05', 470.0, 0.0)",
					"INSERT INTO " + TABLE_NAME_RENTAL + " VALUES ('V_005_CUS_034_2019-05-01', 'V_005', 'CUS_034', '2019-05-01', '2019-05-02', '2019-05-02', 235.0, 0.0)",
					"INSERT INTO " + TABLE_NAME_RENTAL + " VALUES ('V_005_CUS_035_2019-04-25', 'V_005', 'CUS_035', '2019-04-25', '2019-04-30', '2019-04-30', 1175.0, 0.0)"};
			
			for(String st : queries) 
			{
				stmt.executeUpdate(st);
			}
			
			con.commit();

		} 
		catch (Exception e) 
		{
			throw new Exception(e);
		}
    	
    }
    
    
    //Method to Fetch All Records of Vehicles & Rentals from the Database
    public static HashMap<String,Vehicle> fetchRecords()
    {
    	final String DB_NAME = "testDB";
    	final String TABLE_NAME_RENTAL = "RENTAL_RECORDS";
		final String TABLE_NAME_VEHICLE = "VEHICLES";
		
    	try (Connection con = ConnectToDB.getConnection(DB_NAME);
				Statement stmt = con.createStatement();
		) 
    	{			
	   				// select everything
	   				PreparedStatement pst = con.prepareStatement("select * from "+TABLE_NAME_VEHICLE);
	   		        pst.clearParameters();
	   		        ResultSet rs = pst.executeQuery();
	   		        
	   		        PreparedStatement pst1 = con.prepareStatement("select * from "+TABLE_NAME_RENTAL+" order by Actual_return_date NULLS Last");
	   		        pst1.clearParameters();
	   		        ResultSet rs1 = pst1.executeQuery();
	   		        
	   		        HashMap<String,ArrayList<Rental_Record>> rentals = new HashMap<String,ArrayList<Rental_Record>>();
	   		        
	   		        while(rs1.next())
	   		        {
	   		        	String[] arrOfStr1 = rs1.getString(4).split("-");
	   		        	DateTime rentDate_final = new DateTime(Integer.valueOf(arrOfStr1[2]),Integer.valueOf(arrOfStr1[1]),Integer.valueOf(arrOfStr1[0]));
	   		        		   		        
	   		        	String[] arrOfStr2 = rs1.getString(5).split("-");
	   		        	DateTime EstretDate_final = new DateTime(Integer.valueOf(arrOfStr2[2]),Integer.valueOf(arrOfStr2[1]),Integer.valueOf(arrOfStr2[0]));
	   		        	
	   		          if(rs1.getString(6) == null) 
	   		          {
	   		        	
	   		        	if(rentals.containsKey(rs1.getString(2))) 
	   		        	{
	   		        		ArrayList<Rental_Record> recs = rentals.get(rs1.getString(2));
	   		        		
	   		        		Rental_Record rec = new Rental_Record(
						   		        			rs1.getString(1), 
						   		        			rs1.getString(2), 
						   		        			rs1.getString(3),
						   		        			rentDate_final,
						   		        			EstretDate_final,
						   		        			null,
						   		        			null,
						   		        			null
						   		        		);
	   		        		
		   		        	recs.add(rec);
		   		        	rentals.put(rs1.getString(2), recs);
	   		        	}
	   		        	else 
	   		        	{
	   		        		ArrayList<Rental_Record> recs = new ArrayList<Rental_Record>();
	   		        		
	   		        		Rental_Record rec = new Rental_Record(
						   		        			rs1.getString(1), 
						   		        			rs1.getString(2), 
						   		        			rs1.getString(3),
						   		        			rentDate_final,
						   		        			EstretDate_final,
						   		        			null,
						   		        			null,
						   		        			null
						   		        		);
	   		        		
		   		        	recs.add(rec);
		   		        	rentals.put(rs1.getString(2), recs);
	   		        	}
	   		          }
	   		          else 
	   		          {
	   		        	
	   		        	String[] arrOfStr3 = rs1.getString(6).split("-");
	   		        	DateTime ActretDate_final = new DateTime(Integer.valueOf(arrOfStr3[2]),Integer.valueOf(arrOfStr3[1]),Integer.valueOf(arrOfStr3[0]));
	   		        	
	   		        	if(rentals.containsKey(rs1.getString(2))) 
	   		        	{
	   		        		ArrayList<Rental_Record> recs = rentals.get(rs1.getString(2));
	   		        		
	   		        		Rental_Record rec = new Rental_Record(
						   		        			rs1.getString(1), 
						   		        			rs1.getString(2), 
						   		        			rs1.getString(3),
						   		        			rentDate_final,
						   		        			EstretDate_final,
						   		        			ActretDate_final,
						   		        			Double.valueOf(rs1.getString(7)),
						   		        			Double.valueOf(rs1.getString(8))
						   		        		);
	   		        		
		   		        	recs.add(rec);
		   		        	rentals.put(rs1.getString(2), recs);
	   		        	}
	   		        	else 
	   		        	{
	   		        		ArrayList<Rental_Record> recs = new ArrayList<Rental_Record>();
	   		        		
	   		        		Rental_Record rec = new Rental_Record(
						   		        			rs1.getString(1), 
						   		        			rs1.getString(2), 
						   		        			rs1.getString(3),
						   		        			rentDate_final,
						   		        			EstretDate_final,
						   		        			ActretDate_final,
						   		        			Double.valueOf(rs1.getString(7)),
						   		        			Double.valueOf(rs1.getString(8))
						   		        		);
	   		        		
		   		        	recs.add(rec);
		   		        	rentals.put(rs1.getString(2), recs);
	   		        	}  
	   		          }
	   		        }
	   		        
	   		        ArrayList<Vehicle> vehicles = new ArrayList<>();
	   		        HashMap<String,Vehicle> VehicleMap = new HashMap<String,Vehicle>();
	   		     
	   		        while(rs.next())
	   		        {
	   		          Vehicle v;
	   		          if(rs.getString(8) == null) 
	   		          {
	   		        	ArrayList<Rental_Record> rents = rentals.containsKey(rs.getString(1)) ? rentals.get(rs.getString(1)) : new ArrayList<Rental_Record>();
	   		        	v = new Car(
	   		        			rs.getString(1), 
	   		        			rs.getString(2), 
	   		        			rs.getString(3),
	   		        			rs.getString(4),
	   		        			Integer.valueOf(rs.getString(5)),
	   		        			rs.getString(6),
	   		        			rs.getString(7),
	   		        			rents,
	   		        			rs.getString(9)
	   		        			);		
		   		        			
	   		        	vehicles.add(v);
	   		        	VehicleMap.put(rs.getString(1), v);
	   		          }
	   		          else 
	   		          {
	   		        	ArrayList<Rental_Record> rents = rentals.containsKey(rs.getString(1)) ? rentals.get(rs.getString(1)) : new ArrayList<Rental_Record>();
	   		        	String[] arrOfStr1 = rs.getString(8).split("-");
	   		        	DateTime maintenanceDate_final = new DateTime(Integer.valueOf(arrOfStr1[2]),Integer.valueOf(arrOfStr1[1]),Integer.valueOf(arrOfStr1[0]));
	   		        	
	   		        	v = new Van(
	   		        			rs.getString(1), 
	   		        			rs.getString(2), 
	   		        			rs.getString(3),
	   		        			rs.getString(4),
	   		        			rs.getString(6),
	   		        			rs.getString(7),
	   		        			maintenanceDate_final,
	   		        			rents,
	   		        			rs.getString(9)
	   		        			);
	   		        	
	   		        	vehicles.add(v);
	   		        	VehicleMap.put(rs.getString(1), v);
	   		          }
	   		        }
	   				
	   		     con.close();
	   		  
	   		     return VehicleMap;
	   	} 
	    catch (Exception e) 
	    {
	   		 return null;
	   	}
    }
    
    
    //Method to Create a Connection to the Database
    public static Connection getConnection(String dbName)
   				 throws SQLException, ClassNotFoundException {
   	 //Registering the HSQLDB JDBC driver
   	 Class.forName("org.hsqldb.jdbc.JDBCDriver");
   		 
   	 /* Database files will be created in the "database"
   	  * folder in the project. If no username or password is
   	  * specified, the default SA user and an empty password are used */
   	 Connection con = DriverManager.getConnection("jdbc:hsqldb:file:database/" + dbName, "SA", "");
   	 return con;
    }
}
