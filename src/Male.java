import java.time.LocalDate;

public final class Male extends Person {
	
	public Male( String name, LocalDate dateOfBirth, Female mother, Male father ) {
		super( name, dateOfBirth, mother, father );
		this.updateMilitarSituation();
	}
	
	public Male( String name, LocalDate dateOfBirth, Female mother, Male father, Person.MilitarSituation militarSituation ) {
		super( name, dateOfBirth, mother, father );
		this.setMilitarSituation( militarSituation );
	}
	
	// Corrects militarySituation values for males.
	public void updateMilitarSituation() {
		int period = LocalDate.now().getYear() - this.getDateOfBirth().getYear();
		
		if( this.getMilitarSituation() != Person.MilitarSituation.NOTCALLED && period < 18 ) {
			this.setMilitarSituation( Person.MilitarSituation.NOTCALLED );
		}
		
		if( this.getMilitarSituation() == Person.MilitarSituation.NOTCALLED ) {
			if( period == 18 )
				this.setMilitarSituation( Person.MilitarSituation.CALLED );
			if( period > 18 )
				this.setMilitarSituation( Person.MilitarSituation.OUTOFTIME );
		}
	}

	@Override
	public void setMilitarSituation( MilitarSituation militarSituation ) {
		super.setMilitarSituation( militarSituation );
		this.updateMilitarSituation();
	}
	
}