import java.time.LocalDate;

public class Male extends Person {

	public Male( String name, LocalDate dateOfBirth, Male father, Female mother ) {
		super( name, dateOfBirth, father, mother );	
	}
	
	public void registerChild( Female mother, Person child ) {
		if( child.getFather() != null )
			throw new RuntimeException( child.getName() + " is already associated to a father!" );
		if( child.getMother() != null && child.getMother() != mother )
			throw new RuntimeException( child.getName() + " is already associated to a mother!" );
		child.setFather( this );
		child.setMother( mother );
		this.getChildrenList().add( child );
		mother.getChildrenList().add( child );
	}

}
