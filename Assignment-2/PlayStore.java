import java.util.ArrayList;
import java.util.HashMap;

public class PlayStore 
{
	
	private HashMap<String,Content> content;
	private ArrayList<User> users;
	
	
	// Constructor to initialize new Playstore object	
	public PlayStore() 
	{
		this.setContent(new HashMap<String,Content>());
		this.setUsers(new ArrayList<User>());
	}
	
	
	//Getter Methods
	public HashMap<String, Content> getContent() {
		return content;
	}


	public ArrayList<User> getUsers() {
		return users;
	}

	
	//Setter Methods
	public void setContent(HashMap<String, Content> content) {
		this.content = content;
	}


	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}

	
	// Method will add content in the playstore object, if the content id does not already exist
	// If content id already exists in the playstore object, <Custom Exception Name> will be thrown
	public void add(String id, Content content) 
	{
		// Check if the content id already exists
		if(!this.content.keySet().contains(id))
		{
			this.content.put(id, content);
		}
		else
		{
			//Throw Exception - content ID Already exist
		}
	}
	
	
	// Method stores the user in the playstore object, if the same object does not already exist
	// If user already exists in the playstore object, <Custom Exception Name> will be thrown
	public void add(User user) 
	{
		// Check if user already exists
		if(!this.users.contains(user))
		{
			this.users.add(user);
		}
		else
		{
			//Throw Exception - content ID Already exist
		}
		
	}
	
	
	// Method displays Reading content (Books & Magazines) filtered by the genre parameter
	public void showReadingOfGenre(String genre) 
	{
		int counter = 1;
		
		// Tabular format string to print in a proper format
		String format = "%5s%10s%30s%25s";
		System.out.println("Showing the Books/Magazines of "+ genre+" genre");
		System.out.println("----------------------------------------------------------------------");
		System.out.println(String.format(format,"No.","Genre","App Name","Price"));
		System.out.println("----------------------------------------------------------------------");
		
		// Print out the details of Reading content objects that have the same genre as passed in parameter
		for(String key: this.content.keySet())
		{
			if(this.content.get(key).getClass().getSuperclass() == Reading.class)
			{
				Reading tempContent = (Reading) this.content.get(key);
				
				if(tempContent.getGenre().toLowerCase().matches(genre.toLowerCase()))
				{
					System.out.println(String.format(format,counter++,tempContent.getClass().toString().replaceFirst("class",""),tempContent.getApplicationName(),tempContent.getPrice()));
				}
			}
		}
		
		if(counter == 1)
		{
			System.out.println(String.format("%5s%5s%42s%5s"," ", "-------","No Reading content matching given criteria","-------" ));
		}
		
		System.out.println("----------------------------------------------------------------------");
		System.out.println("\n");
	}
	
	
	// Method shows all the content associated with the playstore object
	public void showContent() 
	{
		int counter = 1;
		
		System.out.println("Showing all content of store");
		
		// Tabular format string to print in a proper format
		String format = "%5s%10s%30s%25s";
		System.out.println("----------------------------------------------------------------------");
		System.out.println(String.format(format,"No.","Type","App Name","Price"));
		System.out.println("----------------------------------------------------------------------");
		
		//Loop iterates through all the content object associated with the playstore object and prints details
		for(String key: this.content.keySet())
		{
			Content tempContent = this.content.get(key);
			
			System.out.println(String.format(format,counter++,tempContent.getClass().toString().replaceFirst("class",""),tempContent.getApplicationName(),tempContent.getPrice()));
		}
		
		if(counter == 1)
		{
			System.out.println(String.format("%15s%15s%15s", "-------","No content to display","-------" ));
			
		}
		
		System.out.println("----------------------------------------------------------------------");
		System.out.println("\n");
	}
	
	
	// Method prints out content of a particular class, e.g. Book, Game or Reading
	public void showContentByType(Class<?> c) 
	{
		int counter = 1;

		// Tabular format string to print in a proper format
		String format = "%5s%10s%30s%25s";
		System.out.println("Showing "+ c.getName() + "s");
		System.out.println("----------------------------------------------------------------------");
		System.out.println(String.format(format, "No.", "Type", "App Name", "Price"));
		System.out.println("----------------------------------------------------------------------");
		
		// Print out the details of objects that are of the type which is passed as method parameter
		for(String key: this.content.keySet())
		{
			if(this.content.get(key).getClass() == c)
			{
				Content tempContent = this.content.get(key);
				
				System.out.println(String.format(format, counter++, tempContent.getClass().toString().replaceFirst("class","") ,tempContent.getApplicationName(), tempContent.getPrice()));
			}
		}
		
		if(counter == 1)
		{
			System.out.println(String.format("%5s%5s%35s%5s", " ", "-------", "No content matching given criteria", "-------" ));
		}
		
		System.out.println("----------------------------------------------------------------------");
		System.out.println("\n");
	}
}