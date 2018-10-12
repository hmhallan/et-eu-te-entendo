package et;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import et.util.InputParser;

public class TestaSaida {

	List<Problema> problemas;

	@Before
	public void setUp() throws FileNotFoundException, IOException {
		InputParser parser = new InputParser();
		this.problemas = parser.parseInput( new FileReader("src/main/resources/et.in")  );
	}

	@Test
	public void testa_arquivo_completo() {
		int count = 1;
		ReconhecedorLLC reconhecedor = new ReconhecedorLLC();
		
		for (Problema p: problemas){
			System.out.println("Instancia " + (count++) );
			for( String palavra: p.getParavrasParaTestar() ) {
				reconhecedor.reconhece(p.getGramatica(), palavra);
				reconhecedor.printResult();
			}
			System.out.println();
		};
	}
	
	@Test
	public void testa_os_2_primeiros_problemas() {
		ReconhecedorLLC reconhecedor = new ReconhecedorLLC();
		
		for (int i = 0; i < 2; i++){
			Problema p = problemas.get(i++);
			System.out.println("Instancia " + (i) );
			for( String palavra: p.getParavrasParaTestar() ) {
				reconhecedor.reconhece(p.getGramatica(), palavra);
				reconhecedor.printResult();
			}
			System.out.println();
		};
	}

}
