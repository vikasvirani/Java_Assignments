package view;

import javafx.geometry.Insets;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;

import javafx.application.Application;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.ConnectToDB;
// beans - for property management
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
// Events
import javafx.scene.control.Alert;
// Controls
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
// Layouts
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
// Scenes
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class MainApp extends Application 
{
	
	private static HashMap<String,model.Vehicle> vehicles = new HashMap<String,model.Vehicle>(ConnectToDB.fetchRecords());
	
	private ObservableList<model.Vehicle> Observable_vehicles = FXCollections.observableArrayList(vehicles.values());
	
	private VBox gridpane_vBox = new VBox();
	private Stage primaryStage_new;
	private Scene scene_home;
	private Scene scene_detail;
	
		
		/*
		 * Override the start method in the Application class
		 */
		@Override 
		public void start(Stage primaryStage) 
		{
			
			primaryStage_new = primaryStage;
			scene_home = homescreen();
			
			primaryStage_new.setTitle("ThriftRent"); // Set the stage title
			primaryStage_new.setScene(scene_home); // Place the scene in the stage
			primaryStage_new.setMaxHeight(1000);
			primaryStage_new.setMaxWidth(1500);
			
			primaryStage_new.show(); // Display the stage
			
		}
		
		
		/*
		 * Method to Generate Home Screen of The Application
		 */
		public Scene homescreen() 
		{
					
			BorderPane border = new BorderPane();
			
			border.setTop(addHBox());
			
			border.setLeft(addVBox());
	  
			border.setCenter(addGridPane_VBox());
			
			Scene scene = new Scene(border, 1400, 900);
			
			return scene;
			
		}
	
	
		/*
		 * Method to Generate Detail Screen of The Application
		 */
		public Scene detailscreen(String id) 
		{
			
			BorderPane border = new BorderPane();
	
			border.setTop(addHBox());
			
			border.setCenter(addGridPane_VBox(id));
			
			Scene scene = new Scene(border, 1400, 900);
			
			return scene;
			
		}
	
		
		/*
		 * Creates a HBox with  Common Buttons & ThriftRent Logo for Top Header Part
		 */
		public HBox addHBox() 
		{
		    HBox hbox = new HBox();
		    hbox.setPadding(new Insets(15, 12, 15, 12));
		    hbox.setSpacing(20);
		    hbox.setStyle("-fx-background-color: #336699;");
		    
			//ImageView imageHouse = new ImageView(new Image(MainApp.class.getResourceAsStream("images/thrifty-car-rental-logo.jpg")));		
			File file = new File("images/thrifty-car-rental-logo.jpg");
			Image image = new Image(file.toURI().toString());
			
			ImageView imageThrifty = new ImageView(image);
			imageThrifty.setFitHeight(150);
			imageThrifty.setFitWidth(300);
			
			Tooltip tooltip1 = new Tooltip("Add New Vehicle to Database");
			Tooltip tooltip2 = new Tooltip("Import Vehicles Data From File to Database");
			Tooltip tooltip3 = new Tooltip("Export Vehicles Data From Database to File");
			Tooltip tooltip4 = new Tooltip("Exit Program !!!");
			Tooltip tooltip5 = new Tooltip("Deletes Current Records for Demo of IMPORT DATA");
			Tooltip tooltip6 = new Tooltip("Create Sample Records for Demo");
			
			Button buttonAdd = new Button("Add vehicle");
			buttonAdd.setStyle("-fx-background-color: #FFFF33; -fx-text-fill: #336699");
			buttonAdd.setPrefSize(100, 100);
			buttonAdd.setTooltip(tooltip1);
		    
		    Button buttonImport = new Button("Import Data");
		    buttonImport.setStyle("-fx-background-color: #00FF00; -fx-text-fill: #336699");
		    buttonImport.setPrefSize(100, 100);
		    buttonImport.setTooltip(tooltip2);
	
		    Button buttonExport = new Button("Export Data");
		    buttonExport.setStyle("-fx-background-color: #00FF00; -fx-text-fill: #336699");
		    buttonExport.setPrefSize(100, 100);
		    buttonExport.setTooltip(tooltip3);
		    
		    Button buttonQuit = new Button("Quit");
		    buttonQuit.setStyle("-fx-background-color: #FF0000; -fx-text-fill: #ffffff");
		    buttonQuit.setPrefSize(100, 100);
		    buttonQuit.setTooltip(tooltip4);	    
		    
		    Button buttonDelete = new Button("Delete Records");
		    buttonDelete.setStyle("-fx-background-color: #FF0000; -fx-text-fill: #ffffff");
		    buttonDelete.setPrefSize(150, 100);
		    buttonDelete.setTooltip(tooltip5);
		    
		    Button buttonCreate = new Button("Create Records");
		    buttonCreate.setStyle("-fx-background-color: #FF0000; -fx-text-fill: #ffffff");
		    buttonCreate.setPrefSize(150, 100);
		    buttonCreate.setTooltip(tooltip6);
		    
		    ToolBar toolBar = new ToolBar();
		    toolBar.setPadding(new Insets(15, 12, 15, 12));
		    
	        toolBar.getItems().add(buttonAdd);
	        toolBar.getItems().add(buttonImport);
	        toolBar.getItems().add(buttonExport);
	        toolBar.getItems().add(buttonDelete);
	        toolBar.getItems().add(buttonCreate);
	        toolBar.getItems().add(buttonQuit);
	        
	        
	        hbox.getChildren().addAll(imageThrifty, toolBar);   
	        
	        // Create a dialog box that has some fields for Adding Vehicle to Database.
	 		Stage dialogBox = new Stage();
	 		dialogBox.setTitle("Car entry dialog box");
	 		dialogBox.setWidth(400);
	 		dialogBox.setHeight(600);		
	 		
	 		// dialog components
	 		Text title_id = new Text("Enter a Vehicle Id:");
	 		TextField idField = new TextField();
	 		idField.setPromptText("Id");
	 		
	 		Text title_year = new Text("Enter a Vehicle Year:");
	 		TextField yearField = new TextField();
	 		yearField.setPromptText("Year");
	 		
	 		Text title_make = new Text("Enter a Vehicle Make:");
	 		TextField makeField = new TextField();
	 		makeField.setPromptText("Make");
	 		
	 		Text title_model = new Text("Enter a Vehicle Model:");
	 		TextField modelField = new TextField();
	 		modelField.setPromptText("Model");
	 		
	 		Text title_seats = new Text("Enter a Vehicle Capacity:");
	 		TextField numSeatsField = new TextField();
	 		numSeatsField.setPromptText("Number of seats for Vehicle Type \"CAR\"");
	 		
	 		Text title_maintenance_date = new Text("Enter a Last Maintenance Date:");
	        // create a date picker 
	        DatePicker maintenance_date_field = new DatePicker();
	        maintenance_date_field.setPromptText("Last Maintenance Date for Vehicle Type \"VAN\"");
	        
	        Text title_image = new Text("Enter an Image File Name:");
	 		TextField imageField = new TextField();
	 		imageField.setPromptText("Name of the Image File for this Vehicle");
	 		
	 		Text title_blank = new Text("");
	 		Button dialogOKButton = new Button("OK");
	 		Button dialogquitButton = new Button("Cancel");
	 		
	 		HBox dialogButtons = new HBox();
	 		dialogButtons.setSpacing(10);
	 		dialogButtons.getChildren().add(dialogquitButton);
	 		dialogButtons.getChildren().add(dialogOKButton);
	 		dialogButtons.setAlignment(Pos.CENTER);
	
	 		Text title_type = new Text("Select a Vehicle Type:");
	        ChoiceBox<String> cb_type = new ChoiceBox<String>(FXCollections.observableArrayList(
	        	    "CAR", "VAN")
	        	);
	        cb_type.getSelectionModel().selectFirst();
	        
	 		// layout the dialog components
	 		VBox dialogVBox = new VBox();
	 		
	 		dialogVBox.getChildren().add(title_type);
	 		dialogVBox.getChildren().add(cb_type);
	 		dialogVBox.getChildren().add(title_id);
	 		dialogVBox.getChildren().add(idField); 
	 		dialogVBox.getChildren().add(title_year);
	 		dialogVBox.getChildren().add(yearField);
	 		dialogVBox.getChildren().add(title_make);
	 		dialogVBox.getChildren().add(makeField);
	 		dialogVBox.getChildren().add(title_model);
	 		dialogVBox.getChildren().add(modelField);		
	 		dialogVBox.getChildren().add(title_seats);
	 		dialogVBox.getChildren().add(numSeatsField);
	 		dialogVBox.getChildren().add(title_maintenance_date);
	 		dialogVBox.getChildren().add(maintenance_date_field);
	 		dialogVBox.getChildren().add(title_image);
	 		dialogVBox.getChildren().add(imageField);
	 		dialogVBox.getChildren().add(title_blank);
	 		dialogVBox.getChildren().add(dialogButtons);
			
	 		Scene anotherScene = new Scene(dialogVBox, 200, 200);
	 		dialogBox.setScene(anotherScene);
	 		
	 		Alert alert1 = new Alert(Alert.AlertType.ERROR);
	        alert1.setHeaderText("Vehicle Id is Inavalid or Already Exist Or Some fields are Blank");
	             
	        DirectoryChooser directoryChooser = new DirectoryChooser();
	        directoryChooser.setInitialDirectory(new File("src"));
	        
	        FileChooser fileChooser = new FileChooser();
	        fileChooser.setInitialDirectory(new File("src"));
	        fileChooser.getExtensionFilters().addAll(
	        	     new FileChooser.ExtensionFilter("Text Files", "*.txt")
	        );
	        
	        
	        buttonImport.setOnAction(
	 				(e) -> {
	 					File selectedFile = fileChooser.showOpenDialog(primaryStage_new);
	 					if(selectedFile == null)
	 					{
	 						alert1.setHeaderText("No File Selected");
	 						alert1.showAndWait();
						}
	 					else
	 					{
							String result = new controller.DialogController().new ImportController().handleImport(selectedFile.getAbsolutePath());
							if(result == null || result.isEmpty() || result.equalsIgnoreCase("Success")) 
							{
								vehicles = ConnectToDB.fetchRecords();
								ObservableList<model.Vehicle> test = FXCollections.observableArrayList(vehicles.values());
		 						Observable_vehicles = test;
		 						
		 						Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
		 				        alert2.setHeaderText("Data Imported Successfully");
		 				        alert2.showAndWait();
		 				       
		 				        scene_home = homescreen();
			        			primaryStage_new.setScene(scene_home);
		 					}
		 					else 
		 					{
		 						alert1.setHeaderText(result);
		 						alert1.showAndWait();
		 					}
						}
	 				}
	 		);
	        
	        
	        buttonExport.setOnAction(
	 				(e) -> {
	 					File selectedDirectory = directoryChooser.showDialog(primaryStage_new);
	
	 					if(selectedDirectory == null)
	 					{
	 					     //No Directory selected
	 					}
	 					else
	 					{
	 						String result = new controller.DialogController().new ExportController().handleExport(selectedDirectory.getAbsolutePath());
	 				        if(result == null || result.isEmpty()) 
	 				        {
		 						Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
		 				        alert2.setHeaderText("Data Exported Successfully");
		 				        alert2.showAndWait();
		 					}
		 					else 
		 					{
		 						alert1.setHeaderText(result);
		 						alert1.showAndWait();
		 					}
	 					}
	 	            	
	 				}
	 		);
	        
	        
	 		buttonAdd.setOnAction(
	 				(e) -> {
	 					dialogBox.show();
	 				}
	 		);
	 		
	 		
	 		dialogOKButton.setOnAction(
	 				(e) -> {
	 					String type = (String) cb_type.getValue();
	 					if(type.equalsIgnoreCase("CAR")) 
	 					{
	 						
	 						String message = new controller.DialogController().new AddVehicleController().addCar(idField.getText(),yearField.getText(),
	 							makeField.getText(),modelField.getText(),numSeatsField.getText(),imageField.getText());
	 						
	 						if(message == null || !message.equalsIgnoreCase("Success"))
	 						{
	 							if(message == null) 
	 							{
	 								alert1.setHeaderText("Input fields are Blank Or Something Went Wrong");
	 								alert1.showAndWait();
	 							}
	 							else 
	 							{
	 								alert1.setHeaderText(message);
	 		 						alert1.showAndWait();
	 							}
		 					}
		 					else 
		 					{
		 						vehicles = ConnectToDB.fetchRecords();
								ObservableList<model.Vehicle> test = FXCollections.observableArrayList(vehicles.values());
		 						Observable_vehicles = test;
		 						dialogBox.close();
		 						scene_home = homescreen();
			        			primaryStage_new.setScene(scene_home);
		 					}
	 					}
	 					else 
	 					{
	 						String message =  new controller.DialogController().new AddVehicleController().addVan(idField.getText(),yearField.getText(),
	 	 							makeField.getText(),modelField.getText(),numSeatsField.getText(),String.valueOf(maintenance_date_field.getValue()),imageField.getText());
	 						
	 						if(message == null || !message.equalsIgnoreCase("Success"))
	 						{
	 							if(message == null) 
	 							{
	 								alert1.setHeaderText("Input fields are Blank Or Something Went Wrong");
	 								alert1.showAndWait();
	 							}
	 							else 
	 							{
	 								alert1.setHeaderText(message);
	 		 						alert1.showAndWait();
	 							}
		 					}
		 					else 
		 					{
		 						vehicles = ConnectToDB.fetchRecords();
								ObservableList<model.Vehicle> test = FXCollections.observableArrayList(vehicles.values());
		 						Observable_vehicles = test;
		 						dialogBox.close();
		 						scene_home = homescreen();
			        			primaryStage_new.setScene(scene_home);
		 					}
	 					}
	 					
	 				}
	 		);
	 		
	 		
	 		dialogquitButton.setOnAction(
	 				(e) -> {
	 					dialogBox.close();
	 				}
	 		);		
		    
	 		
		    buttonQuit.setOnAction(new controller.DialogController().new CancelController());
		    
		    
		    buttonDelete.setOnAction(
	 				(e) -> {

							String result = new controller.DialogController().new DemoController().DeleteRecords();
							if(result.equalsIgnoreCase("Success")) 
							{
								vehicles = ConnectToDB.fetchRecords();
								ObservableList<model.Vehicle> test = FXCollections.observableArrayList(vehicles.values());
		 						Observable_vehicles = test;
		 						
		 						Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
		 				        alert2.setHeaderText("Data Deleted Successfully");
		 				        alert2.showAndWait();
		 				       
		 				        scene_home = homescreen();
			        			primaryStage_new.setScene(scene_home);
		 					}
		 					else 
		 					{
		 						alert1.setHeaderText(result);
		 						alert1.showAndWait();
		 					}
	 				}
	 		);
		    
		    
		    buttonCreate.setOnAction(
	 				(e) -> {

							String result = new controller.DialogController().new DemoController().CreateRecords();
							if(result.equalsIgnoreCase("Success")) 
							{
								vehicles = ConnectToDB.fetchRecords();
								ObservableList<model.Vehicle> test = FXCollections.observableArrayList(vehicles.values());
		 						Observable_vehicles = test;
		 						
		 						Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
		 				        alert2.setHeaderText("Data Created Successfully");
		 				        alert2.showAndWait();
		 				       
		 				        scene_home = homescreen();
			        			primaryStage_new.setScene(scene_home);
		 					}
		 					else 
		 					{
		 						alert1.setHeaderText(result);
		 						alert1.showAndWait();
		 					}
	 				}
	 		);
		    
		    
		    return hbox;
		}
	
	
		/*
		 * Creates a VBox with a Choice Dialogues for Filters for the left region
		 */
	    private VBox addVBox() 
	    {
	        
	    	VBox vbox = new VBox();
	    	vbox.setStyle("-fx-background-color: #ffffff;");
	        vbox.setPadding(new Insets(10)); // Set all sides to 10
	        vbox.setSpacing(10);              // Gap between nodes

	        Text title = new Text("Filter :");
	        title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
	        vbox.getChildren().add(title);
	        
	        Text title_type = new Text("Select a Vehicle Type:");
	        ChoiceBox<String> cb_type = new ChoiceBox<String>(FXCollections.observableArrayList(
	        	    "All", "CAR", "VAN")
	        	);
	        cb_type.getSelectionModel().selectFirst();
	        
	        Text title_seats = new Text("Select Number of Seats:");
	        ChoiceBox<String> cb_seats = new ChoiceBox<String>(FXCollections.observableArrayList(
	        		"All", "4", "7", "15")
	        	);
	        cb_seats.getSelectionModel().selectFirst();
	        
	        Text title_status = new Text("Select a Vehicle Status:");
	        ChoiceBox<String> cb_status = new ChoiceBox<String>(FXCollections.observableArrayList(
	        		"All","Available", "Rented", "Maintenance")
	        	);
	        cb_status.getSelectionModel().selectFirst();
	        
	        
	        ObservableList<String> makes_final = FXCollections.observableArrayList();
	        HashSet<String> makes = new HashSet<String>();
	        makes.add("All");
	        for(model.Vehicle v : vehicles.values()) 
	        {
	        	makes.add(v.getMake());
	        }
	        makes_final.addAll(makes);
	        
	        
	        Text title_make = new Text("Select a Vehicle Make:");
	        ChoiceBox<String> cb_make = new ChoiceBox<String>(makes_final);
	        cb_make.getSelectionModel().selectFirst();
	        
	        Alert alert = new Alert(Alert.AlertType.ERROR);
	        alert.setHeaderText("No Vehicles Returned");
	        	        
	        vbox.getChildren().add(title_type);
	        vbox.getChildren().add(cb_type);
	        
	        vbox.getChildren().add(title_seats);
	        vbox.getChildren().add(cb_seats);
	        
	        vbox.getChildren().add(title_status);
	        vbox.getChildren().add(cb_status);
	        
	        vbox.getChildren().add(title_make);
	        vbox.getChildren().add(cb_make);
	        
	        Button search_button = new Button("Search");
	        search_button.setStyle("-fx-background-color: #336699; -fx-text-fill: #ffffff;");	        
	        Tooltip tooltip1 = new Tooltip("Filter Vehicle Records");
	        search_button.setTooltip(tooltip1);
			
	        search_button.setOnAction(
		        	(e) -> {
		        		int seats = ((String) cb_seats.getValue()) == "All" ? 0 : Integer.valueOf((String) cb_seats.getValue());
		        		ObservableList<model.Vehicle> test = new controller.DialogController().new FilterController().handleFilter((String) cb_type.getValue(),seats,(String) cb_status.getValue(),(String) cb_make.getValue());
	 					if(test.size() == 0) 
	 					{
	 						alert.showAndWait();
	 					}
	 					else 
	 					{
	 						Observable_vehicles = test;
	 						homescreen();
	 					}
	 				}
		    );
	        
	        vbox.getChildren().add(search_button);
	        
	        return vbox;
	    }
	
	    
	    /*
		 * Creates a ScrollPane with VBox which calls "addGridPane_Detail" method for a Vehicle with passed ID to Display details of Vehicle on Detail Screen
		 */
	    private ScrollPane addGridPane_VBox(String id) 
	    { 
			gridpane_vBox.getChildren().clear();
			
			for(int i = 0; i < Observable_vehicles.size(); i++) 
			{
				model.Vehicle v = Observable_vehicles.get(i);
				if(v.getVehicle_id().equalsIgnoreCase(id)) 
				{
					gridpane_vBox.getChildren().add(addGridPane_Detail(v));
					Text title_blank2 = new Text("");
					gridpane_vBox.getChildren().add(title_blank2);
				}
			}

			ScrollPane scrollPane = new ScrollPane(gridpane_vBox);
		    scrollPane.setFitToWidth(true);
		    
		    return scrollPane;
	    }
	    
	    
	    /*
		 * Creates a ScrollPane with VBox which calls "addGridPane" method for each Vehicle to Display details of all Vehicles on Home Screen
		 */
	    private ScrollPane addGridPane_VBox() 
	    {
			gridpane_vBox.getChildren().clear();
			
			for(int i = 0; i < Observable_vehicles.size(); i++) {
				gridpane_vBox.getChildren().add(addGridPane(Observable_vehicles.get(i)));
				Text title_blank2 = new Text("");
				gridpane_vBox.getChildren().add(title_blank2);
			}
			
			ScrollPane scrollPane = new ScrollPane(gridpane_vBox);
		    scrollPane.setFitToWidth(true);
		    
		    return scrollPane;
	    }
	    
	    
	    /*
		 * Creates a Stage which Display a dialog to take user inputs to perform "Rent Vehicle" Operation
		 */
	    private Stage addRentDialog(String idField) 
	    {
	    	
	    	Stage dialogBox = new Stage();
	 		dialogBox.setWidth(300);
	 		dialogBox.setHeight(500);
	 		dialogBox.setTitle("Rent Vehicle dialog box");
	 		
	 		// dialog components
	 		Text title_cus = new Text("Enter a Customer Id:");
	 		TextField cusField = new TextField();
	 		cusField.setPromptText("Customer Id");
	 		
	 		Text title_days = new Text("Enter Number of days for Vehicle to be Rented:");
	 		TextField numdaysField = new TextField();
	 		numdaysField.setPromptText("How Many days");
	 		
	 		Text title_Rent_date = new Text("Enter a Rent Date:");
	        // create a date picker 
	        DatePicker Rent_date_field = new DatePicker();
	        Rent_date_field.setPromptText("Rent Date for Vehicle");
	 		
	 		Text title_blank = new Text("");
	 		Button dialogOKButton = new Button("OK");
	 		Button dialogquitButton = new Button("Cancel");
	 		
	 		HBox dialogButtons = new HBox();
	 		dialogButtons.setSpacing(10);
	 		dialogButtons.getChildren().add(dialogquitButton);
	 		dialogButtons.getChildren().add(dialogOKButton);
	 		dialogButtons.setAlignment(Pos.CENTER); 		

	 		// layout the dialog components
	 		VBox dialogVBox = new VBox();

	 		dialogVBox.getChildren().add(title_cus);
	 		dialogVBox.getChildren().add(cusField);
	 		dialogVBox.getChildren().add(title_days);
	 		dialogVBox.getChildren().add(numdaysField);
	 		dialogVBox.getChildren().add(title_Rent_date);
	 		dialogVBox.getChildren().add(Rent_date_field);
	 		dialogVBox.getChildren().add(title_blank);
	 		dialogVBox.getChildren().add(dialogButtons);
	 		
	 		Scene anotherScene = new Scene(dialogVBox, 200, 200);
	 		dialogBox.setScene(anotherScene);
	 		
	 		Alert alert = new Alert(Alert.AlertType.ERROR);
	        alert.setHeaderText("Input fields are Blank Or Something Went Wrong");
		    		
	 		dialogOKButton.setOnAction(
	 				(e) -> {

	 						String message = new controller.DialogController().new VehicleOperationsController().rentVehicle(idField,cusField.getText(),numdaysField.getText(),String.valueOf(Rent_date_field.getValue()));
	 						if(message == null || !message.equalsIgnoreCase("Success"))  
	 						{
	 							if(message == null)
	 							{
	 								alert.setHeaderText("Input fields are Blank Or Something Went Wrong");
	 								alert.showAndWait();
	 							}
	 							else 
	 							{
	 								alert.setHeaderText(message);
	 		 						alert.showAndWait();
	 							}
		 					}
	 						else 
		 					{
		 						ObservableList<model.Vehicle> test = FXCollections.observableArrayList(ConnectToDB.fetchRecords().values());
		 						Observable_vehicles = test;
		 						dialogBox.close();
		 						detailscreen(idField);
		 					}
	 				}
	 		);
	 				
	 		dialogquitButton.setOnAction(
	 				(e) -> {
	 					dialogBox.close();
	 				}
	 		);
		    
		    return dialogBox;
	    }
	    
	    
	    /*
		 * Creates a Stage which Display a dialog to take user inputs to perform "Return Vehicle" Operation
		 */
	    private Stage addReturnDialog(String idField) 
	    {
	    	
	    	Stage dialogBox = new Stage();
	 		dialogBox.setWidth(300);
	 		dialogBox.setHeight(300);
	 		dialogBox.setTitle("Return Vehicle dialog box");
	 		
	 		// dialog components	 		
	 		Text title_Return_date = new Text("Enter a Return Date:");
	        // create a date picker 
	        DatePicker Return_date_field = new DatePicker();
	        Return_date_field.setPromptText("Return Date for Vehicle");
	 		
	 		Text title_blank = new Text("");
	 		Button dialogOKButton = new Button("OK");
	 		Button dialogquitButton = new Button("Cancel");
	 		
	 		HBox dialogButtons = new HBox();
	 		dialogButtons.setSpacing(10);
	 		dialogButtons.getChildren().add(dialogquitButton);
	 		dialogButtons.getChildren().add(dialogOKButton);
	 		dialogButtons.setAlignment(Pos.CENTER);
	        
	 		// layout the dialog components
	 		VBox dialogVBox = new VBox();
	 		
	 		dialogVBox.getChildren().add(title_Return_date);
	 		dialogVBox.getChildren().add(Return_date_field);
	 		dialogVBox.getChildren().add(title_blank);
	 		dialogVBox.getChildren().add(dialogButtons);
		
	 		Scene anotherScene = new Scene(dialogVBox, 200, 200);
	 		dialogBox.setScene(anotherScene);
	 		
	 		Alert alert = new Alert(Alert.AlertType.ERROR);
	        alert.setHeaderText("Return Date is Blank Or Something Went Wrong");
		    	 		
	 		dialogOKButton.setOnAction(
	 				(e) -> {

	 						String message = new controller.DialogController().new VehicleOperationsController().returnVehicle(idField,String.valueOf(Return_date_field.getValue()));
	 						if(message == null || !message.equalsIgnoreCase("Success")) 
	 						{
	 							if(message == null)
	 							{
	 								alert.setHeaderText("Return Date is Blank Or Something Went Wrong");
	 								alert.showAndWait();
	 							}
	 							else 
	 							{
	 								alert.setHeaderText(message);
	 		 						alert.showAndWait();
	 							}
		 					}
	 						else 
		 					{
		 						ObservableList<model.Vehicle> test = FXCollections.observableArrayList(ConnectToDB.fetchRecords().values());
		 						Observable_vehicles = test;
		 						dialogBox.close();
		 						detailscreen(idField);
		 					}
	 						
	 				}
	 		);
	 				
	 		dialogquitButton.setOnAction(
	 				(e) -> {
	 					dialogBox.close();
	 				}
	 		);
		    
		    return dialogBox;
	    }
	    
	    
	    /*
		 * Creates a Stage which Display a dialog to take user inputs to perform "Perform Maintenance" Operation
		 */
	    private Stage addPerformDialog(String idField) 
	    {
	    	 
	    	Stage dialogBox = new Stage();
	 		dialogBox.setWidth(300);
	 		dialogBox.setHeight(300);
	 		dialogBox.setTitle("Perform Maintenance dialog box");
	 		
	 		// dialog components
	 		Text title_blank = new Text("Perform Maintenance On This Vehicle ?");
	 		Button dialogOKButton = new Button("OK");
	 		Button dialogquitButton = new Button("Cancel");
	 		
	 		HBox dialogButtons = new HBox();
	 		dialogButtons.setSpacing(10);
	 		dialogButtons.getChildren().add(dialogquitButton);
	 		dialogButtons.getChildren().add(dialogOKButton);
	 		dialogButtons.setAlignment(Pos.CENTER);
	        
	 		// layout the dialog components
	 		VBox dialogVBox = new VBox();

	 		dialogVBox.getChildren().add(title_blank);
	 		dialogVBox.getChildren().add(dialogButtons);
	 		
	 		Scene anotherScene = new Scene(dialogVBox, 200, 200);
	 		dialogBox.setScene(anotherScene);
	 		
	 		Alert alert = new Alert(Alert.AlertType.ERROR);
	        alert.setHeaderText("Something Went Wrong");
		    	 		
	 		dialogOKButton.setOnAction(
	 				(e) -> {
	 						String message = new controller.DialogController().new VehicleOperationsController().performMaintenance(idField);
	 						if(message == null || !message.equalsIgnoreCase("Success")) 
	 						{
	 							if(message == null)
	 							{
	 								alert.setHeaderText("Something Went Wrong");
	 								alert.showAndWait();
	 							}
	 							else 
	 							{
	 								alert.setHeaderText(message);
	 		 						alert.showAndWait();
	 							}
		 					}
	 						else 
		 					{
		 						ObservableList<model.Vehicle> test = FXCollections.observableArrayList(ConnectToDB.fetchRecords().values());
		 						Observable_vehicles = test;
		 						dialogBox.close();
		 						detailscreen(idField);
		 					}
	 				}
	 		);
	 				
	 		dialogquitButton.setOnAction(
	 				(e) -> {
	 					dialogBox.close();
	 				}
	 		);		
	 		
		    return dialogBox;
	    }
	    
	    
	    /*
		 * Creates a Stage which Display a dialog to take user inputs to perform "Complete Maintenance" Operation
		 */
	    private Stage addCompleteDialog(String idField) 
	    {
	    	 
	    	Stage dialogBox = new Stage();
	 		dialogBox.setWidth(300);
	 		dialogBox.setHeight(400);
	 		dialogBox.setTitle("Complete Maintenance dialog box");
	 		
	 		// dialog components
	 		Text title_maintenance_date = new Text("Enter a Last Maintenance Date:");
	        // create a date picker 
	        DatePicker maintenance_date_field = new DatePicker();
	        maintenance_date_field.setPromptText("Maintenance Completion Date for Vehicle");
	 		
	 		Text title_blank = new Text("");
	 		Button dialogOKButton = new Button("OK");
	 		Button dialogquitButton = new Button("Cancel");
	 		
	 		HBox dialogButtons = new HBox();
	 		dialogButtons.setSpacing(10);
	 		dialogButtons.getChildren().add(dialogquitButton);
	 		dialogButtons.getChildren().add(dialogOKButton);
	 		dialogButtons.setAlignment(Pos.CENTER);
	        
	 		// layout the dialog components
	 		VBox dialogVBox = new VBox();
	 		 
	 		dialogVBox.getChildren().add(title_maintenance_date);
	 		dialogVBox.getChildren().add(maintenance_date_field);
	 		dialogVBox.getChildren().add(title_blank);
	 		dialogVBox.getChildren().add(dialogButtons);
	 		
	 		Scene anotherScene = new Scene(dialogVBox, 200, 200);
	 		dialogBox.setScene(anotherScene);
	 		
	 		Alert alert = new Alert(Alert.AlertType.ERROR);
	        alert.setHeaderText("Blank Maintenance Date Or Something Went Wrong");
		     		
	 		dialogOKButton.setOnAction(
	 				(e) -> {

	 						String message = new controller.DialogController().new VehicleOperationsController().completeMaintenance(idField,String.valueOf(maintenance_date_field.getValue()));
	 						if(message == null || !message.equalsIgnoreCase("Success")) 
	 						{
	 							if(message == null)
	 							{
	 								alert.setHeaderText("Blank Maintenance Date Or Something Went Wrong");
	 								alert.showAndWait();
	 							}
	 							else 
	 							{
	 								alert.setHeaderText(message);
	 		 						alert.showAndWait();
	 							}
		 					}
		 					else 
		 					{
		 						ObservableList<model.Vehicle> test = FXCollections.observableArrayList(ConnectToDB.fetchRecords().values());
		 						Observable_vehicles = test;
		 						dialogBox.close();
		 						detailscreen(idField);
		 					}
	 				}
	 		);
	 				
	 		dialogquitButton.setOnAction(
	 				(e) -> {
	 					dialogBox.close();
	 				}
	 		);
		    
		    return dialogBox;
	    }
	    
	    
	    /*
		 * Creates a TableView to Display All Rental Records of a Vehicle on Detail Screen
		 */
	    private TableView<model.Rental_Record> createRentalTable() 
	    {
	        TableView<model.Rental_Record> table = new TableView<model.Rental_Record>();
	        table.setPlaceholder(new Text("No Rental Records for this Vehicle"));

	        final Label label = new Label("Rental Records : ");
	        label.setFont(new Font("Arial", 20));
	 
	        table.setEditable(true);
	 
	        TableColumn<model.Rental_Record,String> record_id = new TableColumn<>("Record ID");
	        record_id.setCellValueFactory(
	        	    new PropertyValueFactory<model.Rental_Record,String>("record_id")
	        	);
	        
	        TableColumn<model.Rental_Record,String> Vehicle_id = new TableColumn<>("Vehicle ID");
	        Vehicle_id.setCellValueFactory(
	        	    new PropertyValueFactory<model.Rental_Record,String>("Vehicle_id")
	        	);
	        
	        TableColumn<model.Rental_Record,String> Customer_id = new TableColumn<>("Customer ID");
	        Customer_id.setCellValueFactory(
	        	    new PropertyValueFactory<model.Rental_Record,String>("Customer_id")
	        	);
	        
	        TableColumn<model.Rental_Record,String> rent_date = new TableColumn<>("Rent Date");
	        rent_date.setCellValueFactory(
	        	    new PropertyValueFactory<model.Rental_Record,String>("rent_date")
	        	);
	        
	        TableColumn<model.Rental_Record,String> estimated_return_date = new TableColumn<>("Estimated Return Date");
	        estimated_return_date.setCellValueFactory(
	        	    new PropertyValueFactory<model.Rental_Record,String>("estimated_return_date")
	        	);
	        
	        TableColumn<model.Rental_Record,String> actual_return_date = new TableColumn<>("Actual Return Date");
	        actual_return_date.setCellValueFactory(
	        	    new PropertyValueFactory<model.Rental_Record,String>("actual_return_date")
	        	);
	        
	        TableColumn<model.Rental_Record,String> rental_fee = new TableColumn<>("Rental Fee");
	        rental_fee.setCellValueFactory(
	        	    new PropertyValueFactory<model.Rental_Record,String>("rental_fee")
	        	);
	        
	        TableColumn<model.Rental_Record,String> late_fee = new TableColumn<>("Late Fee");
	        late_fee.setCellValueFactory(
	        	    new PropertyValueFactory<model.Rental_Record,String>("late_fee")
	        	);
	        	
	        
	        table.getColumns().addAll(Customer_id, rent_date, estimated_return_date, actual_return_date, rental_fee, late_fee);
	        
	        return table;
	    }
	    
	    
	    /*
		 * Creates a VBox by joining 3 GridPane into Hbox to show a Vehicle Details & "Back TO Home" button with Iterating over all Rental records of a Vehicle for passed Vehicle ID to Display on Detail Screen
		 */
	    private VBox addGridPane_Detail(model.Vehicle vehicle) 
	    {
	    	
	    	VBox vbox_detail = new VBox();
	    	vbox_detail.setSpacing(20);
	    	vbox_detail.setAlignment(Pos.CENTER);
			
	    	HBox hbox_detail = new HBox();
	    	hbox_detail.setStyle("-fx-background-color: #FFFFFF;");
		    hbox_detail.setPadding(new Insets(15, 12, 15, 12));
		    hbox_detail.setSpacing(20);
		    
		    GridPane grid = new GridPane();
	        grid.setHgap(30);
	        grid.setVgap(30);
	        grid.setPadding(new Insets(0, 10, 0, 10));
	        
	        GridPane grid0 = new GridPane();
	        grid0.setHgap(30);
	        grid0.setVgap(30);
	        grid0.setPadding(new Insets(0, 10, 0, 10));
	        
	        GridPane grid2 = new GridPane();
	        grid2.setHgap(30);
	        grid2.setVgap(30);
	        grid2.setPadding(new Insets(0, 10, 0, 10));

	        // make in column 1, row 1
	        Text make = new Text(vehicle.getMake());
	        make.setFont(Font.font("Arial", FontWeight.BOLD, 30));
	        grid.add(make, 0, 0); 

	        // model in column 2, row 1
	        Text model = new Text(vehicle.getModel());
	        model.setFont(Font.font("Arial", FontWeight.BOLD, 30));
	        grid.add(model, 1, 0);

	        // detail in columns 1-2, row 2
	        Text detail = new Text("Deatils");
	        detail.setFont(Font.font("Arial", FontWeight.BOLD,20));
	        grid.add(detail, 0, 1, 1, 1);

	        String img = "images/" + vehicle.getImage();
	        File file = new File(img);
	        Image image;
	        if(!file.exists())
	        {	
	        	file = new File("images/No_Image.jpg");
	        	image = new Image(file.toURI().toString());
	        }
	        else
	        {
	        	image = new Image(file.toURI().toString());	
	        }
	        ImageView imageCar = new ImageView(image);
	        imageCar.setFitHeight(300);
	        imageCar.setFitWidth(450);
	        grid0.add(imageCar, 0, 0, 1, 2); 
	        
	        if(vehicle.getVehicle_type().equalsIgnoreCase("VAN")) {
		        Text title_mainte_date = new Text("Last Maintenance Date : ");
		        title_mainte_date.setFont(Font.font("Arial", FontWeight.BOLD,20));
		        grid.add(title_mainte_date, 0, 5, 2, 1);
		        
		        model.Van van = (model.Van)vehicle;
		        
		        Text mainte_date = new Text(String.valueOf(van.getlast_maintenance_date()));
		        mainte_date.setFont(Font.font("Arial", FontWeight.BOLD,20));
		        GridPane.setValignment(mainte_date, VPos.BOTTOM);
		        grid.add(mainte_date, 2, 5, 2, 1);
	        }
	        
	        Text title_Status = new Text("Status");
	        title_Status.setFont(Font.font("Arial", FontWeight.BOLD,20));
	        grid.add(title_Status, 0, 2);
	        Text status = new Text(vehicle.getVehicle_status());
	        status.setFont(Font.font("Arial", FontWeight.BOLD,20));
	        GridPane.setValignment(status, VPos.BOTTOM);
	        grid.add(status, 0, 3);
	        
	        Text title_Type = new Text("Type");
	        title_Type.setFont(Font.font("Arial", FontWeight.BOLD,20));
	        grid.add(title_Type, 1, 2);
	        Text type = new Text(vehicle.getVehicle_type());
	        type.setFont(Font.font("Arial", FontWeight.BOLD,20));
	        GridPane.setValignment(type, VPos.BOTTOM);
	        grid.add(type, 1, 3);
	        
	        Text title_Year = new Text("Year");
	        title_Year.setFont(Font.font("Arial", FontWeight.BOLD,20));
	        grid.add(title_Year, 2, 2);
	        Text year = new Text(vehicle.getYear());
	        year.setFont(Font.font("Arial", FontWeight.BOLD,20));
	        GridPane.setValignment(year, VPos.BOTTOM);
	        grid.add(year, 2, 3);
	        
	        Text title_Seats = new Text("Seats");
	        title_Seats.setFont(Font.font("Arial", FontWeight.BOLD,20));
	        grid.add(title_Seats, 3, 2);
	        Text seats = new Text(String.valueOf(vehicle.getNumber_of_passengers()));
	        seats.setFont(Font.font("Arial", FontWeight.BOLD,20));
	        GridPane.setValignment(seats, VPos.BOTTOM);
	        grid.add(seats, 3, 3);
	        
	        File file1 = new File("images/home.png");
			Image image1 = new Image(file1.toURI().toString());
			
			ImageView imageview1 = new ImageView(image1);
			imageview1.setFitHeight(33);
			imageview1.setFitWidth(33);
			
	        Button buttonTohome = new Button("Back To Home",imageview1);
	        buttonTohome.setStyle("-fx-background-color: #336699; -fx-text-fill: #ffffff");
	        buttonTohome.setAlignment(Pos.CENTER);
			buttonTohome.setPrefSize(160, 60);
	        grid2.add(buttonTohome, 2, 4);
			
			buttonTohome.setOnAction(
	        		(e) -> {
	        			scene_home = homescreen();
	        			primaryStage_new.setScene(scene_home);
	        		}
	        );
	        
	        hbox_detail.getChildren().addAll(grid0, grid, grid2);
			
	        vbox_detail.getChildren().addAll(hbox_detail);
	        
	        ObservableList<model.Rental_Record> rentals = FXCollections.observableArrayList();
	        if(vehicle.getRentals() != null && !vehicle.getRentals().isEmpty())
	        	rentals.addAll(vehicle.getRentals());
	        
	        TableView<model.Rental_Record> table = createRentalTable();

	        final Label label = new Label("Rental Records : ");
	        label.setFont(Font.font("Arial", FontWeight.BOLD, 30));
	 
	        VBox table_vbox = new VBox();
	        table_vbox.setSpacing(5);
	        table_vbox.setPadding(new Insets(10, 0, 0, 10));
	        table_vbox.getChildren().addAll(label, table);
	        
	        vbox_detail.getChildren().addAll(table_vbox);
	        
	        if(rentals.size() > 0) 
	        {
				for(int i = rentals.size()-1; i >= 0; i--) 
				{
					table.getItems().add(rentals.get(i));
				}
	        }
			
	        HBox buttonbox = new HBox();
	        buttonbox.setPadding(new Insets(15, 12, 15, 12));
	        buttonbox.setSpacing(30);
		    
	        Button buttonRent = new Button("Rent vehicle");
	        buttonRent.setStyle("-fx-background-color: #336699; -fx-text-fill: #ffffff");
	        buttonRent.setPrefSize(200, 100);
	        Tooltip tooltip1 = new Tooltip("Rent a Vehicle");
	        buttonRent.setTooltip(tooltip1);
		    
		    Button buttonReturn = new Button("Return a Vehicle");
		    buttonReturn.setStyle("-fx-background-color: #336699; -fx-text-fill: #ffffff");
		    buttonReturn.setPrefSize(200, 100);
		    Tooltip tooltip2 = new Tooltip("Filter Vehicle Records");
		    buttonReturn.setTooltip(tooltip2);

		    Button buttonPerform = new Button("Perform Maintenance");
		    buttonPerform.setStyle("-fx-background-color: #336699; -fx-text-fill: #ffffff");
		    buttonPerform.setPrefSize(200, 100);
		    Tooltip tooltip3 = new Tooltip("Perform Maintenance on Vehicle");
		    buttonPerform.setTooltip(tooltip3);
		    
		    Button buttonComplete = new Button("Complete Maintenance");
		    buttonComplete.setStyle("-fx-background-color: #336699; -fx-text-fill: #ffffff");
		    buttonComplete.setPrefSize(200, 100);
		    Tooltip tooltip4 = new Tooltip("Complete Maintenance of Vehicle");
		    buttonComplete.setTooltip(tooltip4);
		    
		    Stage RentDialog = addRentDialog(vehicle.getVehicle_id());
		    Stage ReturnDialog = addReturnDialog(vehicle.getVehicle_id());
		    Stage PerformDialog = addPerformDialog(vehicle.getVehicle_id());
		    Stage CompleteDialog = addCompleteDialog(vehicle.getVehicle_id());
		    		    
		    buttonRent.setOnAction(
	 				(e) -> {
	 					RentDialog.show();
	 				}
	 		);
		    		    
		    buttonReturn.setOnAction(
	 				(e) -> {
	 					ReturnDialog.show();
	 				}
	 		);
		    	    
		    buttonPerform.setOnAction(
	 				(e) -> {
	 					PerformDialog.show();
	 				}
	 		);
		    		    
		    buttonComplete.setOnAction(
	 				(e) -> {
	 					CompleteDialog.show();
	 				}
	 		);
		    
		    buttonbox.getChildren().addAll(buttonRent,buttonReturn,buttonPerform,buttonComplete);
		    buttonbox.setAlignment(Pos.CENTER);
		    
		    vbox_detail.getChildren().addAll(buttonbox);
		    
	        return vbox_detail;
	    }
	    
	    
	    /*
		 * Creates a HBox by joining 3 GridPane to show a Vehicle Details & "Show Details" button on Home Page
		 */
	    private HBox addGridPane(model.Vehicle vehicle) 
	    {
	    	HBox hbox = new HBox();
	    	hbox.setStyle("-fx-background-color: #FFFFFF;");
		    hbox.setPadding(new Insets(15, 12, 15, 12));
		    hbox.setSpacing(10);
		    
		    GridPane grid = new GridPane();
	        grid.setHgap(10);
	        grid.setVgap(30);
	        grid.setPadding(new Insets(0, 10, 0, 10));
	        grid.setMinWidth(500);
	        grid.setMaxWidth(500);
	        
	        GridPane grid0 = new GridPane();
	        grid0.setHgap(30);
	        grid0.setVgap(10);
	        grid0.setPadding(new Insets(0, 10, 0, 10));
	        
	        GridPane grid2 = new GridPane();
	        grid2.setHgap(30);
	        grid2.setVgap(10);
	        grid2.setPadding(new Insets(0, 10, 0, 10));

	        // make in column 1, row 1
	        Text make = new Text(vehicle.getMake());
	        make.setFont(Font.font("Arial", FontWeight.BOLD, 30));
	        grid.add(make, 0, 0); 

	        // model in column 2, row 1
	        Text model = new Text(vehicle.getModel());
	        model.setFont(Font.font("Arial", FontWeight.BOLD, 30));
	        grid.add(model, 1, 0);

	        // detail in columns 1-2, row 2
	        Text detail = new Text("Deatils");
	        detail.setFont(Font.font("Arial", FontWeight.BOLD,20));
	        grid.add(detail, 0, 1, 1, 1);

	        String image_final = vehicle.getImage().replace("\n", "").replace("\r", "");
	        String img = "images/" + image_final;
	        //System.out.println(test+" : "+test.length());
	        File file = new File(img);
	        Image image;
	        if(!file.exists())
	        {	
	        	file = new File("images/No_Image.jpg");
	        	image = new Image(file.toURI().toString());
	        }
	        else
	        {
	        	image = new Image(file.toURI().toString());	
	        }
	        ImageView imageCar = new ImageView(image);
	        imageCar.setFitHeight(200);
	        imageCar.setFitWidth(300);
	        grid0.add(imageCar, 0, 0, 1, 2); 
	        
	        Text title_Status = new Text("Status");
	        grid.add(title_Status, 0, 2);
	        Text status = new Text(vehicle.getVehicle_status());
	        GridPane.setValignment(status, VPos.BOTTOM);
	        grid.add(status, 0, 3);
	        
	        Text title_Type = new Text("Type");
	        grid.add(title_Type, 1, 2);
	        Text type = new Text(vehicle.getVehicle_type());
	        GridPane.setValignment(type, VPos.BOTTOM);
	        grid.add(type, 1, 3);
	        
	        Text title_Year = new Text("Year");
	        grid.add(title_Year, 2, 2);
	        Text year = new Text(vehicle.getYear());
	        GridPane.setValignment(year, VPos.BOTTOM);
	        grid.add(year, 2, 3);
	        
	        Text title_Seats = new Text("Seats");
	        grid.add(title_Seats, 3, 2);
	        Text seats = new Text(String.valueOf(vehicle.getNumber_of_passengers()));
	        GridPane.setValignment(seats, VPos.BOTTOM);
	        grid.add(seats, 3, 3);
	        
	        Button buttonDetails = new Button("Show Details");
	        buttonDetails.setStyle("-fx-background-color: #336699; -fx-text-fill: #ffffff;");
	        buttonDetails.setAlignment(Pos.CENTER);
	        buttonDetails.setPrefSize(150, 60);
	        grid2.add(buttonDetails, 4, 6);
			
	        buttonDetails.setOnAction(
	        		(e) -> {
	        			scene_detail = detailscreen(vehicle.getVehicle_id());
	        			primaryStage_new.setScene(scene_detail);
	        		}
	        );
	        
	        ToolBar toolBar = new ToolBar();
	        toolBar.getItems().addAll(grid0,new Separator(), grid,new Separator(), grid2);
	        
	        hbox.getChildren().addAll(grid0, grid, grid2);
	        
	        return hbox;
	    }
	    
	    
		public static void main(String[] args) throws Exception{
			
			try
	        { 
				model.ConnectToDB.checkTables(); 
	        } 
	        catch(Exception e) 
	        { 
	        	throw e;
	        } 
			
		    Application.launch(args);
		    
		}

}
