import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PersonManager {
	
	private static List<Person> peopleList = new ArrayList<Person>();
	private static List<Marriage> marriagesList = new ArrayList<Marriage>();
	
	public static void marry( Person p1, Person p2, LocalDate date ) {
		if( p1 == p2 )
			throw new RuntimeException( "Can't marry one to theirself!" );
		if( p1.getAge() < 16 || p2.getAge() < 16 )
			throw new RuntimeException( "One of the persons is minor!" );
		if( p1.getMaritalStatus() == Marriage.Status.MARRIED || p2.getMaritalStatus() == Marriage.Status.MARRIED )
			throw new RuntimeException( "One of the persons is already married!" );
		if( p1.getDateOfDeath() != null || p2.getDateOfDeath() != null )
			throw new RuntimeException( "One of the persons already passed away!" );
		
		marriagesList.add( new Marriage( p1, p2, date ) );
		
		System.out.println( p1.getName() + " and " + p2.getName() + " just got married!" );
	}
	
	public static void divorce( Person p, LocalDate date ) {
		if( p.getDateOfDeath() != null )
			throw new RuntimeException( "Person already passed away!" );
	    if( p.getMaritalStatus() != Marriage.Status.MARRIED )
	        throw new RuntimeException( "Person not married!" );
	    
	    System.out.println( p.getName() + " and " + p.getSpouse().getName() + " just divorced!" );
	    
	    marriagesList.add( new Marriage( p, p.getSpouse(), date, Marriage.Status.DIVORCED ) );
	}
	
	public static void passAway( Person p, LocalDate date ) {
		if( date.isBefore( p.getDateOfBirth() ) )
			throw new RuntimeException( "Date of death prior to date of birth!" );
		
		if( p.getChildrenList().size() > 0 ) {
			for( Person child : p.getChildrenList() ) {
				if( date.isBefore(child.getDateOfBirth()) ) {
					throw new RuntimeException( "Date of death prior to the date of birth of one his/her children!" );
				}
			}
		}
		
		p.setDateOfDeath( date );
		
		marriagesList.add( new Marriage( p.getSpouse(), p, date, Marriage.Status.WIDOWER ) );
	}
	
	// Returns string regarding marital status for listMarriages method. 
	private final static String string_maritalStatus( Marriage.Status maritalStatus ) {
		switch( maritalStatus ) {
			case MARRIED :
				return "- Married ";
			case DIVORCED :
				return "- Divorced ";
			case WIDOWER :
				return "- Widowed ";
			default:
				throw new RuntimeException( "Invalid marital status." );
		}
	}
	
	public static void listMarriages( Person p ) {
		if( p.getMaritalStatus() == Marriage.Status.NOTMARRIED )
			System.out.println( p.getName() + " never married." );
		
		Person spouse;
		
		System.out.println( "List of Marriages from " + p.getName() + ":" );
		for( Marriage marriage : PersonManager.getMarriagesList() ) {
			if( marriage.getPerson1() == p || marriage.getPerson2() == p ) {
				if( marriage.getPerson1() == p )
					spouse = marriage.getPerson2();
				else
					spouse = marriage.getPerson1();
				System.out.println( string_maritalStatus( marriage.getStatus() ) + spouse.getName() + " in " + marriage.getDate());
			}
		}
	}
	
	public static void checkMaritalStatus( Person p ) {
		Marriage.Status maritalStatus = p.getMaritalStatus();
		String msg;
		
		if( maritalStatus == Marriage.Status.NOTMARRIED ) {
			msg = p.getName() + " is not married.";
		} else if( maritalStatus == Marriage.Status.MARRIED ) {
			msg = p.getName() + " is married.";
		} else if( maritalStatus == Marriage.Status.DIVORCED ) {
			msg = p.getName() + " is divorced.";
		} else if( maritalStatus == Marriage.Status.WIDOWER ) {
			if( p instanceof Male ) msg = p.getName() + " is widow.";
			else					msg = p.getName() + " is widower.";
		} else {
			msg = p.getName() + " already passed away.";
		}
		
		System.out.println( msg );
	}
	
	public void listChildren( Person p ) {
		List<Person> childrenList = p.getChildrenList();
		System.out.println( "Children of " + p.getName() + ":");
		for( Person child : childrenList ) {
			System.out.println("- " + child.getName() );
		}
	}

	public void listBrethren( Person p ) {
		Set<Person> brethrenSet = new HashSet<Person>();
		if( p.getFather() != null ) brethrenSet.addAll( p.getFather().getChildrenList() );
		if( p.getMother() != null ) brethrenSet.addAll( p.getMother().getChildrenList() );
		brethrenSet.remove( this );
		
		System.out.println( "Brethren of " + p.getName() + ":");
		for( Person sibling : brethrenSet ) {
			System.out.println( sibling.getName() );
		}
	}
	
//	*** ACCESS METHODS ***
	
	public static Person getPerson( int code ) {
		for ( Person person : peopleList ) {
			if ( person.getCode() == code )
				return person;
		}
		throw new RuntimeException( "Invalid person code." );
	}
	public static Male addPerson( Male male ) {
		PersonManager.peopleList.add( male );
		return male;
	}
	public static Female addPerson( Female female ) {
		PersonManager.peopleList.add( female );
		return female;
		
	}
	public static List<Person> getPeopleList() {
		return peopleList;
	}
	public static List<Marriage> getMarriagesList() {
		return marriagesList;
	}
	public static void addMarriage( Marriage marriage ) {
		PersonManager.marriagesList.add( marriage );
	}
	
}
