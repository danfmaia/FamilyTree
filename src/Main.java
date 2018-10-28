import java.time.LocalDate;

public class Main {

	public static void main(String[] args) {
		
		// Ver como pôr a carga do BD em outra classe/método.
		
		Female pDonaNeves = new Female( "Dona Neves", LocalDate.of(1930,1,1), null, null );
		
		Male pSirNeves = new Male( "Sir Neves", LocalDate.of(1930,1,1), null, null );
		
		Female pDonaMadruga = new Female( "Dona Madruga", LocalDate.of(1930,1,1), null, null );
		
		Male pSirMadruga = new Male( "Sir Madruga", LocalDate.of(1930,1,1), null, null );
		
		Male pQuicoPai = new Male( "Quico Pai", LocalDate.of(1960,1,1), pSirNeves, pDonaNeves );
		
		Female pDonaFlorinda = new Female( "Dona Florinda", LocalDate.of(1960,1,1), null, null );
		
		Male pProfGirafales = new Male( "Professor Girafales", LocalDate.of(1960,1,1), null, null );
		
		Female pMaeChiquinha = new Female( "Mae da Chiquinha", LocalDate.of(1960,1,1), pSirNeves, pDonaNeves );
		
		Male pSeuMadruga = new Male( "Seu Madruga", LocalDate.of(1960,1,1), pSirMadruga, pDonaMadruga );
		
		Female pBruxa71 = new Female( "Bruxa do 71", LocalDate.of(1960,1,1), null, null );
		
		Male pQuico = new Male( "Quico", LocalDate.of(1990,1,1), null, null );
		
		Female pKika = new Female( "Kika", LocalDate.of(1990,1,1), pQuicoPai, pDonaFlorinda );
		
		Female pChiquinha = new Female( "Chiquinha", LocalDate.of(1990,1,1), pSeuMadruga, pMaeChiquinha );
		
		Male pChaves = new Male( "Chaves", LocalDate.of(1990,1,1), pSeuMadruga, pBruxa71 );
		
		pQuicoPai.registerChild( pDonaFlorinda, pQuico );
		
		pDonaFlorinda.marry( pQuicoPai );
		pDonaFlorinda.divorciar();
		pDonaFlorinda.marry( pProfGirafales );
		
		pSeuMadruga.marry( pMaeChiquinha );
		
		// Retorna RuntimeException se não comentado.
//		pMaeChiquinha.falecer( LocalDate.of(1950,1,1) );
		
		// Implementar limite mínimo para data de nascimento?? Data de nascimento da mãe?
		pMaeChiquinha.passAway( LocalDate.of(1995,1,1) );
		
		pDonaFlorinda.listMaritalStatusChanges();;
		pSeuMadruga.listMaritalStatusChanges();;
			
		pChaves.checkKinship( pChaves );
		pChaves.checkKinship( pDonaFlorinda );
		pQuicoPai.checkKinship( pQuico );
		pDonaFlorinda.checkKinship( pQuico );
		pProfGirafales.checkKinship( pDonaFlorinda );
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