import java.time.LocalDate;

public class Main_testChange {

	public static void main(String[] args) {
		
		// Ver como pôr a carga do BD em outra classe/método. Por ex.: JSON.
		
		Female pDonaNeves = PersonManager.addPerson( new Female( "Dona Neves", LocalDate.of(1940,1,1), null, null ) );
		Male pSirNeves = PersonManager.addPerson( new Male( "Sir Neves", LocalDate.of(1940,1,1), null, null, Person.MilitarSituation.NOTCALLED) );
		Female pDonaMadruga = PersonManager.addPerson( new Female( "Dona Madruga", LocalDate.of(1940,1,1), null, null ) );
		Male pSirMadruga = PersonManager.addPerson( new Male( "Sir Madruga", LocalDate.of(1940,1,1), null, null, Person.MilitarSituation.NOTCALLED) );
		Male pQuicoPai = PersonManager.addPerson( new Male( "Quico Pai", LocalDate.of(1970,1,1), pDonaNeves, pSirNeves, Person.MilitarSituation.NOTCALLED) );
		Female pDonaFlorinda = PersonManager.addPerson( new Female( "Dona Florinda", LocalDate.of(1970,1,1), null, null ) );
		Male pProfGirafales = PersonManager.addPerson( new Male( "Professor Girafales", LocalDate.of(1970,1,1), null, null, Person.MilitarSituation.NOTCALLED) );
		Female pMaeChiquinha = PersonManager.addPerson( new Female( "Mae da Chiquinha", LocalDate.of(1970,1,1), pDonaNeves, pSirNeves ) );
		Male pSeuMadruga = PersonManager.addPerson( new Male( "Seu Madruga", LocalDate.of(1970,1,1), pDonaMadruga, pSirMadruga, Person.MilitarSituation.NOTCALLED) );
		Female pBruxa71 = PersonManager.addPerson( new Female( "Bruxa do 71", LocalDate.of(1970,1,1), null, null ) );
		Male pQuico = PersonManager.addPerson( new Male( "Quico", LocalDate.of(2000,1,1), null, null, Person.MilitarSituation.NOTCALLED) );
		Female pKika = PersonManager.addPerson( new Female( "Kika", LocalDate.of(2005,1,1), pDonaFlorinda, pQuicoPai ) );
		Female pChiquinha = PersonManager.addPerson( new Female( "Chiquinha", LocalDate.of(2005,1,1), pMaeChiquinha, pSeuMadruga ) );
		Male pChaves = PersonManager.addPerson( new Male( "Chaves", LocalDate.of(2005,1,1), pBruxa71, pSeuMadruga, Person.MilitarSituation.NOTCALLED) );
		PersonManager.registerChild( pDonaFlorinda, pQuicoPai, pQuico );
		
		PersonManager.marry( pDonaNeves, pSirNeves, LocalDate.now() );
		PersonManager.marry( pDonaMadruga, pSirMadruga, LocalDate.now() );
		PersonManager.marry( pDonaFlorinda, pQuicoPai, LocalDate.now() );
		PersonManager.divorce( pDonaFlorinda, LocalDate.now() );
		PersonManager.marry( pDonaFlorinda, pProfGirafales, LocalDate.now() );
		PersonManager.marry( pSeuMadruga, pMaeChiquinha, LocalDate.now() );
		PersonManager.divorce( pSeuMadruga, LocalDate.now() );
		PersonManager.marry( pSeuMadruga, pBruxa71, LocalDate.now() );
		
		// Implementar limite mínimo para data de nascimento?? Data de nascimento da mãe?
		PersonManager.passAway( pBruxa71, LocalDate.of(2010,1,1) );
		PersonManager.checkMaritalStatus( pSeuMadruga );
		
// Checking militarSituation before and after [Male].updateMilitaryPendency call.
		System.out.println( "\n" + "*** TESTING updateMilitaryPendency ***");
		
		System.out.println( "Seu Madruga was " + pSeuMadruga.getMilitarSituation() );
		System.out.println( "Quico was " + pQuico.getMilitarSituation() );
		System.out.println( "Chaves was " + pChaves.getMilitarSituation() );
		
		pSeuMadruga.updateMilitarSituation();
		pQuico.updateMilitarSituation();
		pChaves.updateMilitarSituation();
		
		System.out.println( "Seu Madruga is now " + pSeuMadruga.getMilitarSituation() );
		System.out.println( "Quico is now " + pQuico.getMilitarSituation() );
		System.out.println( "Chaves is now " + pChaves.getMilitarSituation() );
		
	}

}