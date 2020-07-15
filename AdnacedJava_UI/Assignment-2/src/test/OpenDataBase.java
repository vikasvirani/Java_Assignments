package test;

import org.hsqldb.util.DatabaseManagerSwing;

public class OpenDataBase {

	public static void main(String[] args) 
	{
		DatabaseManagerSwing.main(new String[] { 
				"--url", "jdbc:hsqldb:file:database/testDB"});
	}

}