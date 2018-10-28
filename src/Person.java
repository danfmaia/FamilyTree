import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public abstract class Person {
	
	private static enum MaritalStatus { NOTMARRIED, MARRIED, DIVORCED, WIDOWER }
	
	private static int currentCode = -1;
	private int code = ++currentCode;
	private String name;
	private LocalDate dateOfBirth;
	private LocalDate dateOfDeath = null;
	
	private Male father;
	private Female mother;
	private Person spouse = null;
	private MaritalStatus maritalStatus = Person.MaritalStatus.NOTMARRIED;
	private ArrayList<Person> childrenList = new ArrayList<Person>();
	private ArrayList<int[]> maritalStatusChangesList = new ArrayList<int[]>();
	
	public Person( String name, LocalDate birthDate, Male father, Female mother ) {
		this.name = name;
		this.dateOfBirth = birthDate;
		this.father = father;
		if( this.father != null ) this.father.getChildrenList().add( this );
		this.mother = mother;
		if( this.mother != null ) this.mother.getChildrenList().add( this );
		PersonManager.addPerson( this );
		System.out.println( this.name + " is " + this.calcAge() + " years old." );
	}
	
	public void marry( Person p ) {
		if( this == p )
			throw new RuntimeException( "Can't marry him/herself!" );
		if( this.calcAge() < 16 || p.calcAge() < 16 )
			throw new RuntimeException( "One of the persons is minor!" );
		if( this.spouse != null || p.spouse != null )
			throw new RuntimeException( "One of the persons is already married!" );
		if( this.dateOfDeath != null || p.dateOfDeath != null )
			throw new RuntimeException( "One of the persons already passed away!" );
		
		this.setSpouse( p );
		this.setMaritalStatus( Person.MaritalStatus.MARRIED );
		
		p.setSpouse( this );
		p.setMaritalStatus( Person.MaritalStatus.MARRIED );
		
		int list[] = new int[2];
		list[0] = 0;
		list[1] = this.code;
		this.maritalStatusChangesList.add( list );
		list[1] = p.code;
		p.maritalStatusChangesList.add( list );
		
		System.out.println( this.name + " and " + p.getName() + " just got married!" );
	}
	
	public void divorciar() {
		if( this.dateOfDeath != null )
			throw new RuntimeException( "Person already passed away!" );
	    if( this.spouse == null )
	        throw new RuntimeException( "Person not married!" );
	    
		int list[] = new int[2];
		list[0] = 1;
		list[1] = this.code;
		this.maritalStatusChangesList.add( list );
		list[1] = this.getSpouse().code;
		this.getSpouse().maritalStatusChangesList.add( list );
		
		System.out.println( this.name + " and " + this.getSpouse().name + " just divorced!" );
		
		this.spouse.setMaritalStatus( Person.MaritalStatus.DIVORCED );
		this.spouse.setSpouse( null );
		
		this.setMaritalStatus( Person.MaritalStatus.DIVORCED );
		this.setSpouse( null );
	}
	
	public void passAway( LocalDate dateOfDeath ) {
		if( dateOfDeath.isBefore(this.dateOfBirth) )
			throw new RuntimeException( "Date of death prior to date of birth!" );
		
		if( this.childrenList.size() > 0 ) {
			for( Person child : this.childrenList ) {
				if( dateOfDeath.isBefore(child.betDateOfBirth()) ) {
					throw new RuntimeException( "Date of death prior to the date of birth of one his/her children!" );
				}
			}
		}
		
		int list[] = new int[2];
		list[0] = 2;
		list[1] = this.code;
		this.getSpouse().maritalStatusChangesList.add( list );
		
		this.dateOfDeath = dateOfDeath;
		this.setMaritalStatus( null );
		this.getSpouse().setMaritalStatus( Person.MaritalStatus.WIDOWER );
		this.getSpouse().setSpouse( null );
		this.spouse = null;
	}
	
	public void checkKinship( Person p ) {
		if( this == p ) {
			System.out.println( p.getName() + " is the person him/herself!" );
			return;
		}
		
		String msg = string_checkKinship(p);
		
		if( msg != " ascends from " && msg != " descends from " && msg != " is not related to " ) {
			if( ! p.getName().endsWith("s") ) {
				System.out.println( this.name + " is " + p.getName() + "'s " + msg + "." );
			} else {
				System.out.println( this.name + " is " + p.getName() + "' " + msg + "." );
			}
		} else if( msg != " is not related to " ) {
			System.out.println( this.name + msg + p.getName() + " with degree " + degree + "." );
		} else {
			System.out.println( this.name + msg + p.getName() + "." );
		}
	}
	
	private String string_checkKinship( Person p ) {
		boolean m;
		if( this instanceof Male ) m = true;
		else					   m = false;
		
		if( this.isSpouseOf(p) == true ) {
			if( m ) return "husband";
			else	return "wife";
		}
		
		degree = this.isSiblingOf(p);
		
		if( degree == 2 ) {
			if( m ) return "brother";
			else	return "sister";
		}
		if( degree == 1 ) {
			if( m ) return "half brother";
			else	return "half sister";
		}
		if( this.isUncleOrAuntOf(p) == true ) {
			if( m ) return "uncle";
			else	return "aunt";
		}
		if( this.isNephewOrNieceOf(p) == true ) {
			if( m ) return "nephew";
			else	return "niece";
		}
		if( this.isCousinOf(p) == true ) {
			return "cousin";
		}
		
		degree = this.ascendsFrom(p);
		
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
		
		degree = this.descendsFrom(p);
		
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
	
	private boolean isSpouseOf( Person p ) {
		if( this.spouse == p ) return true;
		return false;
	}
	
	private static int age;
	private static int degree;
	
	private int rec_ascendsFrom( Person p ) {
		if( Person.age <= p.calcAge() ) return 0;
			
		if( this == p.getFather() || this == p.getMother() ) {
			Person.degree += 1;
			return Person.degree;
		}
		if( p.getFather() != null && this.rec_ascendsFrom(p.getFather()) >= 1 ) {
			return Person.degree += 1;
		}
		if( p.getMother() != null && this.rec_ascendsFrom(p.getMother()) >= 1) {
			return Person.degree += 1;
		}
	
		return 0;	
	}
	
	private int ascendsFrom( Person p ) {
		Person.age = this.calcAge();
		Person.degree = 0;
		return this.rec_ascendsFrom( p );
	}
	private int descendsFrom( Person p ) {
		Person.age = p.calcAge();
		Person.degree = 0;
		return p.rec_ascendsFrom( this );
	}
	
	// Retorna 2 para irmão, 1 para meio-irmão e 0 caso não seja irmào.
	private int isSiblingOf( Person p ) {
		if( (this.getFather() == null && this.getMother() == null) ||
				  (p.getFather() == null && p.getMother() == null) )	return 0;
		
		ArrayList<Person> parentsList = new ArrayList<Person>();
		if( this.father != null ) parentsList.add( this.father );
		if( this.mother != null ) parentsList.add( this.mother );
		if( p.getFather() != null ) parentsList.add( p.getFather() );
		if( p.getMother() != null ) parentsList.add( p.getMother() );
		
		Set<Person> parentsSet = new HashSet<Person>();
		parentsSet.addAll( parentsList );
		
		return parentsList.size() - parentsSet.size();
	}
	
	private boolean isUncleOrAuntOf( Person p ) {
		if( this.father == null && this.mother == null ) return false;
		if( p.getFather() == null && p.getMother() == null ) return false;
		
		if( this instanceof Male ) {
			if( this == p.getFather() ) return false;
		} else {
			if( this == p.getMother() ) return false;
		}
		
		if( this.isSiblingOf( p.getFather() ) > 0 ) return true;
		if( this.isSiblingOf( p.getMother() ) > 0 ) return true;
		
		return false;
	}
	
	private boolean isNephewOrNieceOf( Person p ) {
		return p.isUncleOrAuntOf( this );
	}
	
	public boolean isCousinOf( Person p ) {
		if( this.isSiblingOf(p) > 0 ) return false;
		
		ArrayList<Person> grandparentsList = new ArrayList<Person>();
		Person obj = this;
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
			obj = p;
		}
		
		Set<Person> grandparentsSet = new HashSet<Person>( grandparentsList );
		if( grandparentsSet.size() < grandparentsList.size() ) return true;
		return false;
	}
	
	public int getCode() {
		return code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocalDate betDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public LocalDate getDateOfDeath() {
		return dateOfDeath;
	}
	public Male getFather() {
		return father;
	}
	public void setFather(Male father) {
		this.father = father;
	}
	public Female getMother() {
		return mother;
	}
	public void setMother(Female mother) {
		this.mother = mother;
	}
	public Person getSpouse() {
		return spouse;
	}
	public void setSpouse(Person spouse) {
		this.spouse = spouse;
	}
	public MaritalStatus getMaritalStatus() {
		return maritalStatus;
	}
	public void setMaritalStatus(MaritalStatus maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	public ArrayList<Person> getChildrenList() {
		return childrenList;
	}
	public void setChildrenList(ArrayList<Person> childrenList) {
		this.childrenList = childrenList;
	}
	
	public int calcAge() {
		return Period.between(this.dateOfBirth, LocalDate.now()).getYears();
	}
	public void listChildren() {
		System.out.println( "Children of " + this.name + ":");
		for( Person child : this.childrenList ) {
			System.out.println("- " + child.getName() );
		}
	}
	public void listBrethren() {
		Set<Person> brethrenSet = new HashSet<Person>();
		if( this.father != null ) brethrenSet.addAll( this.father.getChildrenList() );
		if( this.mother != null ) brethrenSet.addAll( this.mother.getChildrenList() );
		brethrenSet.remove( this );
		
		System.out.println( "Brethren of " + this.name + ":");
		for( Person sibling : brethrenSet ) {
			System.out.println( sibling.getName() );
		}
	}
	
	// Array de strings usado no método listarMudancasEstadoCivil()
	private final static String[] string_maritalStatus = new String[] {
			"- Married ",
			"- Divorced ",
			"- Widowed "
	};
	public void listMaritalStatusChanges() {
		System.out.println( "List of Marital Status Changes of " + this.name + ":" );
		for( int[] tuple : this.maritalStatusChangesList ) {
			System.out.println( Person.string_maritalStatus[tuple[0]] + PersonManager.getPerson(tuple[1]).getName() );
		}
	}
	
	public void checkMaritalStatus() {
		MaritalStatus martialStatus = this.getMaritalStatus();
		String msg;
		
		if( martialStatus == Person.MaritalStatus.NOTMARRIED ) {
			msg = this.name + " is not married.";
		} else if( martialStatus == Person.MaritalStatus.MARRIED ) {
			msg = this.name + " is married.";
		} else if( martialStatus == Person.MaritalStatus.DIVORCED ) {
			msg = this.name + " is divorced.";
		} else if( martialStatus == Person.MaritalStatus.WIDOWER ) {
			if( this instanceof Male ) msg = this.name + " is widow.";
			else					   msg = this.name + " is widower.";
		} else {
			msg = this.name + " already passed away.";
		}
		
		System.out.println( msg );
	}
	
}
