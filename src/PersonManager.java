import java.util.ArrayList;

public class PersonManager {
	
	private static ArrayList<Integer> code = new ArrayList<Integer>();
	private static ArrayList<Person> person = new ArrayList<Person>();
	
	public static Person getPerson( int code ) {
		return PersonManager.person.get( code );
	}
	public static void addPerson( Person person ) {
		PersonManager.code.add( person.getCode() );
		PersonManager.person.add( person );
	}
	
}
