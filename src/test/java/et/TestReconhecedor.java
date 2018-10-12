package et;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import et.util.InputParser;

public class TestReconhecedor  {
	
	List<Problema> problemas;
	
	@Before
	public void setUp() throws FileNotFoundException, IOException {
		InputParser parser = new InputParser();
		this.problemas = parser.parseInput( new FileReader("src/main/resources/et.in")  );
	}

	@Test
	public void testa_parser_de_entrada() {
		problemas.forEach(System.out::println);
	}
	
	@Test
	public void testa_primeiro_problema()  {
		Problema p = problemas.get(0);
		System.out.println( p );
		
		ReconhecedorLLC reconhecedor = new ReconhecedorLLC();
		for( String palavra: p.getParavrasParaTestar() ) {
			reconhecedor.reconhece(p.getGramatica(), palavra);
			reconhecedor.printDetails();
		}
	}
	
	@Test
	public void testa_segundo_problema() {
		Problema p = problemas.get(1);
		System.out.println( p );
		
		ReconhecedorLLC reconhecedor = new ReconhecedorLLC();
		for( String palavra: p.getParavrasParaTestar() ) {
			reconhecedor.reconhece(p.getGramatica(), palavra);
			reconhecedor.printDetails();
		}
	}
	
	@Test
	public void testa_terceiro_problema() {
		Problema p = problemas.get(2);
		System.out.println( p );
		
		ReconhecedorLLC reconhecedor = new ReconhecedorLLC();
		for( String palavra: p.getParavrasParaTestar() ) {
			reconhecedor.reconhece(p.getGramatica(), palavra);
			reconhecedor.printDetails();
		}
	}
	
	@Test
	public void testa_quarto_problema() {
		Problema p = problemas.get(3);
		System.out.println( p );
		
		ReconhecedorLLC reconhecedor = new ReconhecedorLLC();
		for( String palavra: p.getParavrasParaTestar() ) {
			reconhecedor.reconhece(p.getGramatica(), palavra);
			reconhecedor.printDetails();
		}
	}
	
	@Test
	public void testa_quinto_problema() {
		Problema p = problemas.get(4);
		System.out.println( p );
		
		ReconhecedorLLC reconhecedor = new ReconhecedorLLC();
		for( String palavra: p.getParavrasParaTestar() ) {
			reconhecedor.reconhece(p.getGramatica(), palavra);
			reconhecedor.printDetails();
		}
	}
}
