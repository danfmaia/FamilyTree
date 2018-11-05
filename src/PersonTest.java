import java.time.LocalDate;

public class PersonTest {

	public static void main(String[] args) {
		
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
		
		PersonManager.marry( pDonaNeves, pSirNeves, LocalDate.now() );
		PersonManager.marry( pDonaMadruga, pSirMadruga, LocalDate.now() );
		PersonManager.marry( pDonaFlorinda, pQuicoPai, LocalDate.now() );
		PersonManager.divorce( pDonaFlorinda, LocalDate.now() );
		PersonManager.marry( pDonaFlorinda, pProfGirafales, LocalDate.now() );
		PersonManager.marry( pSeuMadruga, pMaeChiquinha, LocalDate.now() );
		PersonManager.divorce( pSeuMadruga, LocalDate.now() );
		PersonManager.marry( pSeuMadruga, pBruxa71, LocalDate.now() );
		
		pQuico.listBrethren();
		pKika.listBrethren();
		pChiquinha.listBrethren();
		pChaves.listBrethren();
	}

}