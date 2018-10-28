import java.time.LocalDate;

public class Female extends Person {
	
	private double weight;
	private double height;
	
	public Female( String name, LocalDate dateOfBirth, Male father, Female mother ) {
		super( name, dateOfBirth, father, mother );
	}
	public Female( String name, LocalDate dateOfBirth, Male father, Female mother, double weight, double height ) {
		super( name, dateOfBirth, father, mother );
		this.setWeight( weight );
		this.setHeight( height );
	}
	
	public void registerChild( Male father, Person child ) {
		if( child.getMother() != null )
			throw new RuntimeException( child.getName() + " is already associated to a mother!" );
		if( child.getFather() != null && child.getFather() != father )
			throw new RuntimeException( child.getName() + " is already associated to a father!" );
		child.setFather( father );
		child.setMother( this );
		father.getChildrenList().add( child );
		this.getChildrenList().add( child );
	}
	
	public double calcIMC() {
		return( this.weight / Math.sqrt(this.height) );
	}
	
	public double getWeight() {
		return weight;
	}
	public void setWeight( double weight ) {
		this.weight = weight;
	}
	public double getHeight() {
		return height;
	}
	public void setHeight( double height ) {
		this.height = height;
	}
	
}