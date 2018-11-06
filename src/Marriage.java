import java.time.LocalDate;

public final class Marriage {
	
	public static enum Status {
		NOTMARRIED,
		MARRIED,
		DIVORCED,
		WIDOWER
	}
	
	private Person person1;
	private Person person2;
	private Status status;
	private LocalDate date;
	
	public Marriage( Person person1, Person person2, LocalDate date ) {
		this( person1, person2, date, Status.MARRIED );
	}
	public Marriage( Person person1, Person person2, LocalDate date, Status status ) {
		this.person1 = person1;
		this.person2 = person2;
		this.date = date;
		this.status = status;
	}
	
//	*** ACCESS METHODS ***	
	
	public Person getPerson1() {
		return person1;
	}
	public Person getPerson2() {
		return person2;
	}
	public Status getStatus() {
		return status;
	}
	public LocalDate getDate() {
		return date;
	}
	
}
