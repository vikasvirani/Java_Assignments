package thriftyRent;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.UUID;

import util.DateTime;

/**
 * @author Jigar Mangukiya
 * 
 * Concrete class Utilities <br><br>
 * Implements auxiliary static methods support smoother operation of application and better modularity of code
 * Aims to increase cohesion and reduce code duplication as well as coupling 
 * 
 * Major Method/s -<br>
 * 		1. tabularize : String<br>
 * 		2. dividingDashLine : boolean<br>
 * 		3. randomIdGenerator : boolean<br>
 * 		4. menuViewGenerator : boolean<br>
 * 		5. enumToStringArray : String<br>
 * 		6. getIntInput : String<br>
 * 		7. getStringInput : String<br>
 * 		8. getDateInput : String<br>
 */
public class Utilities {
	/**
	 * Method generates unique alphanumeric Id of specific length<br>
	 *
	 *  @param numberOfChars Integer - number of characters in the ID to be returned
	 *  @return alphanumeric string    
	 */
	public static String randomIdGenerator(int numberOfChars) {
		return UUID.randomUUID().toString().substring(9,9+numberOfChars);
	}
	
	
	/**
	 * Method is used to generate CLI based menus with proper title & options dynamically
	 *
	 *  @param title : String - title of menu to be generated
	 *  @param items : String[] - array of strings consisting menu items
	 *  @return formatted string print menu    
	 */
	public static String menuViewGenerator(String title, String[] items) {
		String menuContent = "";
		String startingLine = "";
		String startingLine1 = "";
		String startingLine2 = "";
		String endingLine = "";
		int maxItemLength = 0;
		int counter = 1; 
		
		for(String item : items) {
			menuContent = menuContent.concat(Utilities.tabularize(item, Integer.toString((counter))));
			counter += 1;
			if(item.length() > maxItemLength) {
				maxItemLength = item.length();
			}
		}
		
		for(int i = 0; i < (maxItemLength - title.length())/2; i++) {
			startingLine1 = startingLine1 + "* ";
			startingLine2 = startingLine2 + " *";
		}
		startingLine = Math.abs(title.length() - maxItemLength) <= 4 ?  ("* * * * * " + title + " * * * *" + "\n") : (startingLine1 + title + startingLine2 + "\n");
		
		for(int i = 0; i < startingLine.length()/2; i++) {
			endingLine = endingLine.concat("* ");
		}
		
		return Utilities.dividingDashLine(startingLine.length())
				.concat(startingLine)
				.concat(Utilities.dividingDashLine(startingLine.length()))
				.concat(menuContent)
				.concat("\n")
				.concat(Utilities.dividingDashLine(startingLine.length()))
				.concat(endingLine +"\n")
				.concat(Utilities.dividingDashLine(startingLine.length()) + "\n");
	}
	
	
	/**
	 *  Generic method is used to convert all values of any enumeration to a string[] 
	 *
	 *  @param values : T[] - collection of enumeration values. (Can be fetched my <Enum-Name>.values(); method call
	 *  @return String[] - consisting a string entry corresponding to each value of enumeration    
	 */
	public static <T extends Enum<T>> String[] enumToStringArray(T[] values) {
	    int i = 0;
	    String[] result = new String[values.length];
	    for (T value: values) {
	        result[i++] = (value.toString());
	    }
	    return result;
	}
	
	
	/**
	 *  Method is used to get Integer input and validate to confirm proper input
	 *  method loops till user enter a valid Integer 
	 *
	 *  @param queryString : String - Prints the proper message while prompting user for input
	 *  @return Integer - corresponding Integer of user input    
	 */
	public static int getIntInput(String queryString) {
		String errorString = "";
		do
		{
			System.out.println(errorString);
			errorString = "";
			System.out.print(queryString+": ");
			try {
				Scanner sc = new Scanner(System.in);
				int input = sc.nextInt();
				sc.close();
				return input;
			}
			catch(Exception ex){
				errorString = "Invalid Input, Please try again";
			}
		}while(true);
	}
	
	
	/**
	 *  Method is used to get Integer input and validate to confirm proper input
	 *  method loops till user enter a valid Integer 
	 *
	 *  @param queryString : String - Prints the proper message while prompting user for input
	 *  @param startRange : Integer - starting range of acceptable values if distinct is false OR First acceptable distinct value if distinct is true
	 *  @param endRange : Integer - ending range of acceptable values if distinct is false OR Second acceptable distinct value if distinct is true
	 *  @param distinct : boolean - if true, indicates that parameters startRange & endRange are 2 acceptable distinct values
	 *  							if false, indicates that parameters are starting and ending values of acceptable range of Integer values   
	 *  @return Integer - corresponding Integer of user input    
	 */
	public static int getIntInput(String queryString, int startRange, int endRange, boolean distinct) {
		String errorString = "";
		do
		{
			System.out.println(errorString);
			errorString = "";
			System.out.print(queryString+": ");
			@SuppressWarnings("resource")
			Scanner sc = new Scanner(System.in);
			try {
				
				int input = sc.nextInt();
				if(!distinct &&(input < startRange || input > endRange)) {
					throw new Exception();
				}
				if(distinct && (input != startRange && input != endRange)){
					throw new Exception();
				}
				return input;
			}
			catch(Exception ex){
				errorString = "Invalid Input, Please try again";
			}
		}while(true);
	}	
	
	
	/**
	 *  Method is used to get String input and validate to confirm proper input (No empty, null or whitespace string)
	 *  method loops till user enter a valid String
	 *
	 *  @param queryString : String - Prints the proper message while prompting user for input
	 *  @return Integer - corresponding String of user input    
	 */
	public static String getStringInput(String queryString) {
		String errorString = "";
		do
		{
			@SuppressWarnings("resource")
			Scanner sc = new Scanner(System.in);
			System.out.println(errorString);
			errorString = "";
			System.out.print(queryString+": ");
			try {
				String input = sc.nextLine();
				if(input.isEmpty() || input.trim().isEmpty() || input.equals(" ") || input.equals("") ) {
					throw new Exception();
				}
				return input;
			}
			catch(Exception ex){
				errorString = "Invalid Input, Please try again";
			}
		}while(true);
	}
	
	
//	public static Double getDoubleInput(String queryString) {
//		String errorString = "";
//		do
//		{
//			@SuppressWarnings("resource")
//			Scanner sc = new Scanner(System.in);
//			System.out.println(errorString);
//			errorString = "";
//			System.out.println(queryString+": ");
//			try {
//				Double input = sc.nextDouble();
//				return input;
//			}
//			catch(Exception ex){
//				errorString = "Invalid Input, Please try again";
//			}
//		}while(true);
//	}
	
	
	/**
	 *  Method is used to get DateTime input in format 'dd/MM/yyyy' and validate to confirm proper input
	 *  <br><br>
	 *  Declaration -
	 *  				1. for months with 30 days, if user enters 31, method will return date corresponding to day=30
	 *  				2. for month February with 28 or 29 days, if user enter 30,31 or 29 days(suggesting wrong leap year assumption by user),
	 *  				   method will return largest valid date (28 or 29)   
	 *  
	 *  method loops till user enter a valid Date
	 *
	 *  @param queryString : String - Prints the proper message while prompting user for input
	 *  @return util.DateTime - corresponding Date of user input    
	 */
	public static DateTime getDateInput(String queryString) {
		String errorString = "";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		do
		{
			@SuppressWarnings("resource")
			Scanner sc = new Scanner(System.in);
			System.out.println(errorString);
			errorString = "";
			System.out.println(queryString+": ");
			try {
				String input = sc.nextLine();
				LocalDate date = formatter.parse(input, LocalDate::from);
				DateTime dt = new DateTime(date.getDayOfMonth(),date.getMonthValue(),date.getYear());
				return dt;
			}
			catch(Exception ex){
				errorString = "Invalid Input, Please try again";
			}
		}while(true);
	}
	
	
	/**
	 * Method arranges 2 input strings in a table-like format<br>
	 * <br>
	 *  @param val1 String value-1 (left column value)  
	 *  @param val2 String value-2 (right column value)
	 *  @return formatted string with dynamically inserted tabs  
	 */
	public static String tabularize(String val1, String val2) {
		return val1 + ":\t" + (val1.length() > 6 ? "" : "\t").toString() + (val1.length() > 14 ? "" : "\t").toString() + val2 + "\n";
	}
	
	
	/**
	 * Method returns a string with line made up of '-'<br>
	 * Should be used to maintain consistent look of menus
	 *
	 *  @return formatted string with dynamically inserted tabs  
	 */
	public static String dividingDashLine() {
		return "---------------------------------\n";
	}
	
	
	/**
	 * Method overloads dividingDashLine() with one integer argument<br>
	 * Should be used to maintain consistent look of menus
	 *
	 *  @param length Integer - length of the line to be returned
	 *  @return formatted string with dynamically inserted tabs  
	 */
	public static String dividingDashLine(int length) {
		String line = "";
		for(int i = 0; i < length ; i++ ) {
			line = line.concat("-");
		}
		return line+"\n";
	}
}