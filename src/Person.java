import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public abstract class Person {
	
	public static enum MilitarSituation {
		NOTCALLED,
		CALLED,
		EXEMPTED,
		MILITARY,
		OUTOFTIME
	}
	
	private static int currentCode = -1;
	private int code = ++currentCode;
	private String name;
	private LocalDate dateOfBirth;
	private LocalDate dateOfDeath = null;
	private Male father;
	private Female mother;
	private MilitarSituation militarSituation = MilitarSituation.NOTCALLED;
	
	public Person( String name, LocalDate birthDate, Female mother, Male father ) {
		this.name = name;
		this.dateOfBirth = birthDate;
		this.father = father;
		if( this.father != null ) this.father.getChildrenList().add( this );
		this.mother = mother;
		if( this.mother != null ) this.mother.getChildrenList().add( this );
		System.out.println( this.name + " is " + this.getAge() + " years old and " );
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
	public MilitarSituation getMilitarSituation() {
		return militarSituation;
	}
	public void setMilitarSituation( MilitarSituation militarSituation ) {
		int period = LocalDate.now().getYear() - this.getDateOfBirth().getYear();

		if( militarSituation != Person.MilitarSituation.NOTCALLED && period < 18 )
			throw new RuntimeException( this.getName() + " is not old enough for military service yet." );
		if( militarSituation != Person.MilitarSituation.NOTCALLED && period < 18 )
			throw new RuntimeException( this.getName() + " is not old enough for military service yet." );
		
		this.militarSituation = militarSituation;
	}
	
//	*** DEPENDENT METHODS ***
	
	public int getAge() {
		return Period.between( this.dateOfBirth, LocalDate.now() ).getYears();
	}
	
	public Person getSpouse() {
		List<Marriage> list = PersonManager.getMarriagesList();
		for( int i = list.size() - 1; i>=0; i-- ) {
			if( list.get(i).getPerson1() == this )
				if( list.get(i).getStatus() == Marriage.Status.MARRIED )
					return list.get(i).getPerson2();
				else
					return null;
			else if ( list.get(i).getPerson2() == this )
				if( list.get(i).getStatus() == Marriage.Status.MARRIED )
					return list.get(i).getPerson1();
				else
					return null;
		}
		return null;
	}
	
	public Marriage.Status getMaritalStatus() {
		if( this.getDateOfDeath() != null )
			return Marriage.Status.DECEASED;
			
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
