package et.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import et.Gramatica;
import et.Problema;
import et.Producao;

public class InputParser {
	
	private static final int LINHA_RAIZ = 1;
	private static final int LINHA_VARIAVEIS = 2;
	private static final int LINHA_TERMINAIS = 3;
	private static final int LINHA_PRODUCOES = 4;
	private static final int LINHA_PARAVRAS = 5;
	
	/**
	 * CASO 1:
	 * 
	  	S			-> Na primeira linha de cada teste aparece o símbolo raiz, que sempre será uma letra maiúscula.
		SAB			-> Na segunda linha, o conjunto V será fornecido como uma palavra composta apenas por letras maiúsculas. Cada letra dessa palavra será identificada como um membro de V . 
		ab			-> O conjunto T será dado como uma palavra de caracteres imprimíveis (com exceção de # e caracteres em branco) na terceira linha. Cada caractere dessa palavra será identificada como um membro de T . 
		S -> AB			-> A seguir, serão fornecidas várias linhas, que descreverão as regras de composição para a instância atual. 
		A -> a			-> A seguir, serão fornecidas várias linhas, que descreverão as regras de composição para a instância atual.
		B -> b			-> A seguir, serão fornecidas várias linhas, que descreverão as regras de composição para a instância atual.
		# -> #			-> Uma regra de composição na forma # -> # indica o fim da lista de regras de composição. 
		ab				-> Por fim, são fornecidas várias linhas, cada uma contendo uma palavra que desejamos saber se pode ou não ser produzida a partir da raiz por meio das regras de composição.
		a				-> Por fim, são fornecidas várias linhas, cada uma contendo uma palavra que desejamos saber se pode ou não ser produzida a partir da raiz por meio das regras de composição.
	  	#				-> A lista de palavras termina com uma linha contendo # na primeira coluna. 
	  
	 */

	public List<Problema> parseInput( Reader input ) throws IOException {
		
		List<Problema> problemas = new ArrayList<>();
		
		int tipoLinha = LINHA_RAIZ;
		Gramatica g = null;
		List<String> palavras = new ArrayList<>();
		
		BufferedReader reader = new BufferedReader(input);
		while ( reader.ready() ) {
			String line = reader.readLine();
			
			//raiz
			if (tipoLinha == LINHA_RAIZ) {
				g = new Gramatica();
				g.setEstadoInicial(line);
				tipoLinha = LINHA_VARIAVEIS;
			}
			
			//variaveis
			else if (tipoLinha == LINHA_VARIAVEIS) {
				g.setVariaveis( Arrays.asList( line.toCharArray() )
										.stream()
										.map(String::valueOf)
										.collect(Collectors.toList()) 
								);
				tipoLinha = LINHA_TERMINAIS;
			}
			
			//terminais
			else if (tipoLinha == LINHA_TERMINAIS) {
				g.setTerminais( Arrays.asList( line.toCharArray() )
										.stream()
										.map(String::valueOf)
										.collect(Collectors.toList()) 
								);
				tipoLinha = LINHA_PRODUCOES;
			}
			
			//producoes
			else if (tipoLinha == LINHA_PRODUCOES) {
				if ( "# -> #".equalsIgnoreCase(line) ) {
					tipoLinha = LINHA_PARAVRAS;
				}
				else {
					g.addProducao( new Producao(line));
				}
			}
			
			//palavras
			else if (tipoLinha == LINHA_PARAVRAS) {
				if ( "#".equalsIgnoreCase(line) ) {
					tipoLinha = LINHA_RAIZ;
					
					Problema p = new Problema();
					p.setGramatica(g);
					p.setParavrasParaTestar(palavras);
					problemas.add(p);
					palavras = new ArrayList<>();
				}
				else {
					palavras.add(line);
				}
			}
			
		}
		
		
		return problemas;
	}
}
