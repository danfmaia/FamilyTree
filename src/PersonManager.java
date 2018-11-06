import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class PersonManager {
	
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
	
	public static void registerChild( Female mother, Male father, Person child ) {
		if( child.getMother() != null )
			throw new RuntimeException( child.getName() + " is already associated to a mother!" );
		if( child.getFather() != null && child.getFather() != father )
			throw new RuntimeException( child.getName() + " is already associated to a father!" );
		child.setFather( father );
		child.setMother( mother );
		father.getChildrenList().add( child );
		mother.getChildrenList().add( child );
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
		
		System.out.println( p.getName() + " passed away =/" );
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
		
		System.out.println( "List of Marriages of " + p.getName() + ":" );
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
		
		switch ( maritalStatus ) {
		case NOTMARRIED :
			System.out.println( p.getName() + " is not married." );
			return;
		case MARRIED :
			System.out.println( p.getName() + " is married." );
			return;
		case DIVORCED :
			System.out.println( p.getName() + " is divorced." );
			return;
		case WIDOWER :
			if( p instanceof Male ) {
				System.out.println( p.getName() + " is widow." );
				return;
			} else {
				System.out.println( p.getName() + " is widower." );
				return;
			}
		case DECEASED :
			System.out.println( p.getName() + " already passed away." );
		}
	}
	
	public static void listChildren( Person p ) {
		List<Person> childrenList = p.getChildrenList();
		System.out.println( "Children of " + p.getName() + ":");
		for( Person child : childrenList ) {
			System.out.println("- " + child.getName() );
		}
	}

	public static void listBrethren( Person p ) {
		Set<Person> brethrenSet = new HashSet<Person>();
		if( p.getFather() != null ) brethrenSet.addAll( p.getFather().getChildrenList() );
		if( p.getMother() != null ) brethrenSet.addAll( p.getMother().getChildrenList() );
		brethrenSet.remove( p );
		
		System.out.println( "Brethren of " + p.getName() + ":");
		for( Person sibling : brethrenSet ) {
			System.out.println( sibling.getName() );
		}
	}
	
// *** KINSHIP METHODS ***
	
	public static void checkKinship( Person p1, Person p2 ) {
		if( p1 == p2 ) {
			if( p1 instanceof Male )
				System.out.println( p2.getName() + " is the person himself!" );
			else if( p1 instanceof Female )
				System.out.println( p2.getName() + " is the person herself!" );
			return;
		}
		
		String msg = string_checkKinship( p1, p2 );
		
		if( msg != " ascends from " && msg != " descends from " && msg != " is not related to " ) {
			if( ! p2.getName().endsWith("s") ) {
				System.out.println( p1.getName() + " is " + p2.getName() + "'s " + msg + "." );
			} else {
				System.out.println( p1.getName() + " is " + p2.getName() + "' " + msg + "." );
			}
		} else if( msg != " is not related to " ) {
			System.out.println( p1.getName() + msg + p2.getName() + " with degree " + degree + "." );
		} else {
			System.out.println( p1.getName() + msg + p2.getName() + "." );
		}
	}
	
	private static String string_checkKinship( Person p1, Person p2 ) {
		boolean m;
		if( p1 instanceof Male ) m = true;
		else					 m = false;
		
		if( p1.getSpouse() != null & p1.getSpouse() == p2 ) {
			if( m ) return "husband";
			else	return "wife";
		}
		
		degree = isSiblingOf( p1, p2 );
		
		if( degree == 2 ) {
			if( m ) return "brother";
			else	return "sister";
		}
		if( degree == 1 ) {
			if( m ) return "half brother";
			else	return "half sister";
		}
		if( isUncleOrAuntOf( p1, p2 ) == true ) {
			if( m ) return "uncle";
			else	return "aunt";
		}
		if( isNephewOrNieceOf( p1, p2 ) == true ) {
			if( m ) return "nephew";
			else	return "niece";
		}
		if( isCousinOf( p1, p2 ) == true ) {
			return "cousin";
		}
		
		degree = ascendsFrom( p1, p2 );
		
		if( degree == 1 ) {
			if( m ) return "father";
			else	return "mother";
		}
		if( degree == 2 ) {
			if( m ) return "grandfather";
			else	return "grandmother";
		}
		if( degree == 3 ) {
			if( m ) return "great grandfather";
			else	return "great grandmother";
		}
		if( degree > 3 ) {
			return " ascends from ";			
		}
		
		degree = descendsFrom( p1, p2 );
		
		if( degree == 1 ) {
			if( m ) return "son";
			else	return "daughter";
		}
		if( degree == 2 ) {
			if( m ) return "grandson";
			else	return "granddaughter";
		}
		if( degree == 3 ) {
			if( m ) return "great-grandson";
			else	return "great-granddaughter";
		}
		if( degree > 3 ) {
			return " descends from ";			
		}
		
		return " is not related to ";
	}
	
	private static int age;
	private static int degree;
	
	private static int rec_ascendsFrom( Person p1, Person p2 ) {
		if( age <= p2.getAge() ) return 0;
			
		if( p1 == p2.getFather() || p1 == p2.getMother() ) {
			degree += 1;
			return degree;
		}
		if( p2.getFather() != null && rec_ascendsFrom( p1, p2.getFather()) >= 1 ) {
			return degree += 1;
		}
		if( p2.getMother() != null && rec_ascendsFrom( p1, p2.getMother()) >= 1) {
			return degree += 1;
		}
	
		return 0;	
	}
	
	private static int ascendsFrom( Person p1, Person p2 ) {
		age = p1.getAge();
		degree = 0;
		return rec_ascendsFrom( p1, p2 );
	}
	private static int descendsFrom( Person p1, Person p2 ) {
		age = p2.getAge();
		degree = 0;
		return rec_ascendsFrom( p2, p1 );
	}
	
	// Retorna 2 para irmão, 1 para meio-irmão e 0 caso não seja irmào.
	private static int isSiblingOf( Person p1, Person p2 ) {
		if( (p1.getFather() == null && p1.getMother() == null) ||
				  (p2.getFather() == null && p2.getMother() == null) )	return 0;
		
		ArrayList<Person> parentsList = new ArrayList<Person>();
		if( p1.getFather() != null ) parentsList.add( p1.getFather() );
		if( p1.getMother() != null ) parentsList.add( p1.getMother() );
		if( p2.getFather() != null ) parentsList.add( p2.getFather() );
		if( p2.getMother() != null ) parentsList.add( p2.getMother() );
		
		Set<Person> parentsSet = new HashSet<Person>();
		parentsSet.addAll( parentsList );
		
		return parentsList.size() - parentsSet.size();
	}
	
	private static boolean isUncleOrAuntOf( Person p1, Person p2 ) {
		if( p1.getFather() == null && p1.getMother() == null ) return false;
		if( p2.getFather() == null && p2.getMother() == null ) return false;
		
		if( p1 instanceof Male ) {
			if( p1 == p2.getFather() ) return false;
		} else {
			if( p1 == p2.getMother() ) return false;
		}
		
		if( isSiblingOf( p1, p2.getFather() ) > 0 ) return true;
		if( isSiblingOf( p1, p2.getMother() ) > 0 ) return true;
		
		return false;
	}
	
	private static boolean isNephewOrNieceOf( Person p1, Person p2 ) {
		return isUncleOrAuntOf( p2, p1 );
	}
	
	public static boolean isCousinOf( Person p1, Person p2 ) {
		if( isSiblingOf( p1, p2 ) > 0 ) return false;
		
		ArrayList<Person> grandparentsList = new ArrayList<Person>();
		Person obj = p1;
		Person father;
		Person mother;
		
		for( int i=0; i<=1; i++ ) {
			if( obj.getFather() != null ) {
				father = obj.getFather();
				if( father.getFather() != null ) grandparentsList.add( father.getFather() );
				if( father.getMother() != null ) grandparentsList.add( father.getMother() );
			}
			if( obj.getMother() != null ) {
				mother = obj.getMother();
				if( mother.getFather() != null ) grandparentsList.add( mother.getFather() );
				if( mother.getMother() != null ) grandparentsList.add( mother.getMother() );
			}
			obj = p2;
		}
		
		Set<Person> grandparentsSet = new HashSet<Person>( grandparentsList );
		if( grandparentsSet.size() < grandparentsList.size() ) return true;
		return false;
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
