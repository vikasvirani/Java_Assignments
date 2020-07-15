package test;


import util.DateTime;

public class DateTimeTest {
	public static void main(String[] args) {
		// create a datetime object to store today's date
		DateTime today = new DateTime();
		System.out.println(today); // output: current date
		 
		//create any day by passing day month year as integers
		//to a constructor of the DateTime class
		DateTime goodFriday = new DateTime(19, 4, 2019);
		System.out.println(goodFriday); // output: 19/04/2019
		
		//print a datetime in the format suitable for use when constructing
		//a rental record id (more details in assignment specs)
		System.out.println(today.getEightDigitDate()); //output: current date
		System.out.println(goodFriday.getEightDigitDate()); //output: 19042019
		
		//get the name of day of a datetime
		System.out.println(today.getNameOfDay()); // name of the current day
		System.out.println(goodFriday.getNameOfDay()); // Friday
		
		//3 days from now
		DateTime threeDaysFromNow = new DateTime(today, 3);
		System.out.println(threeDaysFromNow);

		//You're encouraged to explore other constructors and methods
		//of the DateTime class
		
		DateTime today1 = new DateTime();
		System.out.println(today1);
		DateTime threeDaysFromNow1 = new DateTime(today1, 3);
		System.out.println(threeDaysFromNow1);
		DateTime goodFriday1 = new DateTime(14, 2, 2019);
		System.out.println(goodFriday1);
		DateTime threeDaysFromNow_1 = new DateTime(goodFriday1, 3);
		System.out.println(threeDaysFromNow_1);
		DateTime goodFriday2 = new DateTime(15, 2, 2019);
		System.out.println(goodFriday2);
		System.out.println(DateTime.diffDays(threeDaysFromNow_1,goodFriday1));
		
	}
}
