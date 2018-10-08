package et;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.junit.Test;

import et.util.InputParser;

public class TestReconhecedor  {

	@Test
	public void testa_parser_de_entrada() throws FileNotFoundException, IOException {
		
		InputParser parser = new InputParser();
		
		List<Problema> problemas = parser.parseInput( new FileReader("src/main/resources/et.in")  );
		problemas.forEach(System.out::println);
		
	}
	
	@Test
	public void testa_primeiro_problema() throws FileNotFoundException, IOException {
		
		InputParser parser = new InputParser();
		
		List<Problema> problemas = parser.parseInput( new FileReader("src/main/resources/et.in")  );
		
		Problema p = problemas.get(0);
		System.out.println( p );
		
		ReconhecedorLLC reconhecedor = new ReconhecedorLLC();
		for( String palavra: p.getParavrasParaTestar() ) {
			reconhecedor.reconhece(p.getGramatica(), palavra);
		}
	}
	
	@Test
	public void testa_segundo_problema() throws FileNotFoundException, IOException {
		
		InputParser parser = new InputParser();
		
		List<Problema> problemas = parser.parseInput( new FileReader("src/main/resources/et.in")  );
		
		Problema p = problemas.get(1);
		System.out.println( p );
		
		ReconhecedorLLC reconhecedor = new ReconhecedorLLC();
		for( String palavra: p.getParavrasParaTestar() ) {
			reconhecedor.reconhece(p.getGramatica(), palavra);
		}
	}
	
	@Test
	public void testa_terceiro_problema() throws FileNotFoundException, IOException {
		
		InputParser parser = new InputParser();
		
		List<Problema> problemas = parser.parseInput( new FileReader("src/main/resources/et.in")  );
		
		Problema p = problemas.get(2);
		System.out.println( p );
		
		ReconhecedorLLC reconhecedor = new ReconhecedorLLC();
		for( String palavra: p.getParavrasParaTestar() ) {
			reconhecedor.reconhece(p.getGramatica(), palavra);
		}
	}
	
	@Test
	public void testa_quarto_problema() throws FileNotFoundException, IOException {
		
		InputParser parser = new InputParser();
		
		List<Problema> problemas = parser.parseInput( new FileReader("src/main/resources/et.in")  );
		
		Problema p = problemas.get(3);
		System.out.println( p );
		
		ReconhecedorLLC reconhecedor = new ReconhecedorLLC();
		for( String palavra: p.getParavrasParaTestar() ) {
			reconhecedor.reconhece(p.getGramatica(), palavra);
		}
	}
	
	@Test
	public void testa_quinto_problema() throws FileNotFoundException, IOException {
		
		InputParser parser = new InputParser();
		
		List<Problema> problemas = parser.parseInput( new FileReader("src/main/resources/et.in")  );
		
		Problema p = problemas.get(4);
		System.out.println( p );
		
		ReconhecedorLLC reconhecedor = new ReconhecedorLLC();
		for( String palavra: p.getParavrasParaTestar() ) {
			reconhecedor.reconhece(p.getGramatica(), palavra);
		}
	}
}
