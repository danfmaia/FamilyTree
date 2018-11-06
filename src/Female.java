import java.time.LocalDate;

public final class Female extends Person implements WeightControl {
	
	private double weight;
	private double height;
	
	public Female( String name, LocalDate dateOfBirth, Female mother, Male father ) {
		super( name, dateOfBirth, mother, father );
	}
	public Female( String name, LocalDate dateOfBirth, Female mother, Male father, double weight, double height ) {
		super( name, dateOfBirth, mother, father );
		this.weight = weight;
		this.height = height;
	}
	
	@Override
	public double calculateBMI() {
		return( this.weight / (this.height * this.height) );
	}
	
//	*** ACCESS METHODS ***
	
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