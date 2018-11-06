import java.time.LocalDate;

public class Main {

	public static void main(String[] args) {
		
		// Ver como pôr a carga do BD em outra classe/método.
		
		Female pDonaNeves = PersonManager.addPerson( new Female( "Dona Neves", LocalDate.of(1930,1,1), null, null ) );
		Male pSirNeves = PersonManager.addPerson( new Male( "Sir Neves", LocalDate.of(1930,1,1), null, null ) );
		Female pDonaMadruga = PersonManager.addPerson( new Female( "Dona Madruga", LocalDate.of(1930,1,1), null, null ) );
		Male pSirMadruga = PersonManager.addPerson( new Male( "Sir Madruga", LocalDate.of(1930,1,1), null, null ) );
		Male pQuicoPai = PersonManager.addPerson( new Male( "Quico Pai", LocalDate.of(1960,1,1), pDonaNeves, pSirNeves ) );
		Female pDonaFlorinda = PersonManager.addPerson( new Female( "Dona Florinda", LocalDate.of(1960,1,1), null, null ) );
		Male pProfGirafales = PersonManager.addPerson( new Male( "Professor Girafales", LocalDate.of(1960,1,1), null, null ) );
		Female pMaeChiquinha = PersonManager.addPerson( new Female( "Mae da Chiquinha", LocalDate.of(1960,1,1), pDonaNeves, pSirNeves ) );
		Male pSeuMadruga = PersonManager.addPerson( new Male( "Seu Madruga", LocalDate.of(1960,1,1), pDonaMadruga, pSirMadruga ) );
		Female pBruxa71 = PersonManager.addPerson( new Female( "Bruxa do 71", LocalDate.of(1960,1,1), null, null ) );
		Male pQuico = PersonManager.addPerson( new Male( "Quico", LocalDate.of(1990,1,1), null, null ) );
		Female pKika = PersonManager.addPerson( new Female( "Kika", LocalDate.of(1990,1,1), pDonaFlorinda, pQuicoPai ) );
		Female pChiquinha = PersonManager.addPerson( new Female( "Chiquinha", LocalDate.of(1990,1,1), pMaeChiquinha, pSeuMadruga ) );
		Male pChaves = PersonManager.addPerson( new Male( "Chaves", LocalDate.of(1990,1,1), pBruxa71, pSeuMadruga ) );
		PersonManager.registerChild( pDonaFlorinda, pQuicoPai, pQuico );
		
		System.out.println( "test1 " + pDonaNeves.getSpouse() );
		System.out.println( "test2 " + pSirNeves.getSpouse() );
		
		System.out.println( "test3 " + pDonaNeves.getMaritalStatus() );
		System.out.println( "test4 " + pSirNeves.getMaritalStatus() );
		
		PersonManager.marry( pDonaNeves, pSirNeves, LocalDate.now() );
		PersonManager.marry( pDonaMadruga, pSirMadruga, LocalDate.now() );
		PersonManager.marry( pDonaFlorinda, pQuicoPai, LocalDate.now() );
		PersonManager.divorce( pDonaFlorinda, LocalDate.now() );
		PersonManager.marry( pDonaFlorinda, pProfGirafales, LocalDate.now() );
		PersonManager.marry( pSeuMadruga, pMaeChiquinha, LocalDate.now() );
		PersonManager.divorce( pSeuMadruga, LocalDate.now() );
		PersonManager.marry( pSeuMadruga, pBruxa71, LocalDate.now() );
		
		// Retorna RuntimeException se não comentado.
//		PersonManager.passAway( pMaeChiquinha, LocalDate.of(1950,1,1) );
		
		// Implementar limite mínimo para data de nascimento?? Data de nascimento da mãe?
		PersonManager.passAway( pMaeChiquinha, LocalDate.of(1995,1,1) );
		
		PersonManager.listMarriages( pDonaFlorinda );
		PersonManager.listMarriages( pProfGirafales );
		PersonManager.listMarriages( pSeuMadruga );
		
		System.out.println( "Chaves' wife: " + pChaves.getSpouse() );
		System.out.println( "Quico Pai's husband: " + pQuicoPai.getSpouse() );
		System.out.println( "Dona Florinda's husband: " + pDonaFlorinda.getSpouse().getName() );
		System.out.println( "Prof Girafales' wife: " + pProfGirafales.getSpouse().getName() );
			
		PersonManager.checkKinship( pChaves, pChaves );
		PersonManager.checkKinship( pChaves, pDonaFlorinda );
		PersonManager.checkKinship( pQuicoPai, pQuico );
		PersonManager.checkKinship( pDonaFlorinda, pQuico );
		PersonManager.checkKinship( pProfGirafales, pDonaFlorinda );
		PersonManager.checkKinship( pDonaFlorinda, pProfGirafales );
		PersonManager.checkKinship( pChaves, pSeuMadruga );
		PersonManager.checkKinship( pChiquinha, pSeuMadruga );
		PersonManager.checkKinship( pSirNeves, pChiquinha );
		PersonManager.checkKinship( pDonaNeves, pChiquinha );
		PersonManager.checkKinship( pQuicoPai, pChiquinha );
		PersonManager.checkKinship( pMaeChiquinha, pQuico );
		PersonManager.checkKinship( pQuico, pMaeChiquinha );
		PersonManager.checkKinship( pChiquinha, pQuicoPai );
		
	}

}