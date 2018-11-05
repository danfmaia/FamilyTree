import java.time.LocalDate;

public class Main {

	public static void main(String[] args) {
		
		// Ver como pôr a carga do BD em outra classe/método.
		
		Female pDonaNeves = PersonManager.addPerson( new Female( "Dona Neves", LocalDate.of(1930,1,1), null, null ) );
		Male pSirNeves = PersonManager.addPerson( new Male( "Sir Neves", LocalDate.of(1930,1,1), null, null ) );
		Female pDonaMadruga = PersonManager.addPerson( new Female( "Dona Madruga", LocalDate.of(1930,1,1), null, null ) );
		Male pSirMadruga = PersonManager.addPerson( new Male( "Sir Madruga", LocalDate.of(1930,1,1), null, null ) );
		Male pQuicoPai = PersonManager.addPerson( new Male( "Quico Pai", LocalDate.of(1960,1,1), pSirNeves, pDonaNeves ) );
		Female pDonaFlorinda = PersonManager.addPerson( new Female( "Dona Florinda", LocalDate.of(1960,1,1), null, null ) );
		Male pProfGirafales = PersonManager.addPerson( new Male( "Professor Girafales", LocalDate.of(1960,1,1), null, null ) );
		Female pMaeChiquinha = PersonManager.addPerson( new Female( "Mae da Chiquinha", LocalDate.of(1960,1,1), pSirNeves, pDonaNeves ) );
		Male pSeuMadruga = PersonManager.addPerson( new Male( "Seu Madruga", LocalDate.of(1960,1,1), pSirMadruga, pDonaMadruga ) );
		Female pBruxa71 = PersonManager.addPerson( new Female( "Bruxa do 71", LocalDate.of(1960,1,1), null, null ) );
		Male pQuico = PersonManager.addPerson( new Male( "Quico", LocalDate.of(1990,1,1), null, null ) );
		Female pKika = PersonManager.addPerson( new Female( "Kika", LocalDate.of(1990,1,1), pQuicoPai, pDonaFlorinda ) );
		Female pChiquinha = PersonManager.addPerson( new Female( "Chiquinha", LocalDate.of(1990,1,1), pSeuMadruga, pMaeChiquinha ) );
		Male pChaves = PersonManager.addPerson( new Male( "Chaves", LocalDate.of(1990,1,1), pSeuMadruga, pBruxa71 ) );
		pQuicoPai.registerChild( pDonaFlorinda, pQuico );
		
		System.out.println( "teste1 " + pDonaNeves.getSpouse() );
		System.out.println( "teste2 " + pSirNeves.getSpouse() );
		
		System.out.println( "teste3 " + pDonaNeves.getMaritalStatus() );
		System.out.println( "teste4 " + pSirNeves.getMaritalStatus() );
		
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
		PersonManager.listMarriages( pSeuMadruga );
			
		pChaves.checkKinship( pChaves );
		pChaves.checkKinship( pDonaFlorinda );
		pQuicoPai.checkKinship( pQuico );
		pDonaFlorinda.checkKinship( pQuico );
		pProfGirafales.checkKinship( pDonaFlorinda );
		// RESULTADO ERRADO!
		pDonaFlorinda.checkKinship( pProfGirafales );
		pChaves.checkKinship( pSeuMadruga );
		pChiquinha.checkKinship( pSeuMadruga );
		pSirNeves.checkKinship( pChiquinha );
		pDonaNeves.checkKinship( pChiquinha );
		pQuicoPai.checkKinship( pChiquinha );
		pMaeChiquinha.checkKinship( pQuico );
		pQuico.checkKinship( pMaeChiquinha );
		pChiquinha.checkKinship( pQuicoPai );
		
	}

}