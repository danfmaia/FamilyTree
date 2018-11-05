import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class Person {
	
	private static int currentCode = -1;
	private int code = ++currentCode;
	private String name;
	private LocalDate dateOfBirth;
	private LocalDate dateOfDeath = null;
	
	private Male father;
	private Female mother;
	
	public Person( String name, LocalDate birthDate, Male father, Female mother ) {
		this.name = name;
		this.dateOfBirth = birthDate;
		this.father = father;
		if( this.father != null ) this.father.getChildrenList().add( this );
		this.mother = mother;
		if( this.mother != null ) this.mother.getChildrenList().add( this );
		System.out.println( this.name + " is " + this.getAge() + " years old." );
	}

	public void checkKinship( Person p ) {
		if( this == p ) {
			if( this instanceof Male )
				System.out.println( p.getName() + " is the person himself!" );
			else if( this instanceof Female )
				System.out.println( p.getName() + " is the person herself!" );
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
		if( this.getSpouse() == p ) return true;
		return false;
	}
	
	private static int age;
	private static int degree;
	
	private int rec_ascendsFrom( Person p ) {
		if( Person.age <= p.getAge() ) return 0;
			
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
		Person.age = this.getAge();
		Person.degree = 0;
		return this.rec_ascendsFrom( p );
	}
	private int descendsFrom( Person p ) {
		Person.age = p.getAge();
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
	
//	*** ACCESS METHODS ***	

	public int getCode() {
		return code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfDeath(LocalDate dateOfDeath) {
		this.dateOfDeath = dateOfDeath;
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
	
//	*** DEPENDENT METHODS ***
	
	public int getAge() {
		return Period.between( this.dateOfBirth, LocalDate.now() ).getYears();
	}
	
	public Person getSpouse() {
		for( Marriage marriage : PersonManager.getMarriagesList() ) {
			if( marriage.getPerson1() == this )
				return marriage.getPerson2();
			else if ( marriage.getPerson2() == this )
				return marriage.getPerson1();
		}
		return null;
	}
	
	public Marriage.Status getMaritalStatus() {
		List<Marriage> list = PersonManager.getMarriagesList();
		for( int i = list.size() - 1; i>=0; i-- ) {
			if( list.get(i).getPerson1() == this || list.get(i).getPerson2() == this )
				return list.get(i).getStatus();
		}
		return Marriage.Status.NOTMARRIED;
	}
	
	public List<Person> getChildrenList() {
		List<Person> childrenList = new ArrayList<Person>();
		if( this instanceof Male ) {
			for( Person p : PersonManager.getPeopleList() ) {
				if( p.getFather() == this )
					childrenList.add( p );		
			}
		} else if ( this instanceof Female ) {
			for( Person p : PersonManager.getPeopleList() ) {
				if( p.getMother() == this )
					childrenList.add( p );		
			}
		}
		return childrenList;
	}
	
}
