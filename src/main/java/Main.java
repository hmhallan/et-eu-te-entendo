

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Main {
	
	private Gramatica gramatica;
	
	//cyk
	private String[][] tabela;
	private String cadeia;
	
	public static void main(String [] args) throws FileNotFoundException, IOException {
		
		List<Problema> problemas = parseInput( new Scanner(System.in) );
//		List<Problema> problemas = parseInput( new Scanner( new File("src/main/resources/et.in") ) );
		
		int count = 1;
		for (Problema p: problemas) {
			System.out.println("Instancia " + (count++) );
			Main sol = new Main();
			for (String s: p.getParavrasParaTestar()) {
				boolean ok = sol.reconhece( p.getGramatica(), s);
				if(ok){
					System.out.println(s + " e uma palavra valida");
				}else{
					System.out.println(s + " nao e uma palavra valida");
				}
			}
			System.out.println();
		}
	}

	public boolean reconhece( Gramatica g, String palavra ) {
		this.gramatica = g;
		this.cadeia = palavra;
		this.cyk();
		return this.pertence();
	}
	
	public void cyk() {
		//inicializa a tabela com a cadeia
		this.criarTabela();
		
		//roda o algoritmo CYK na tabela
		this.doCyk();
	}
	
	private void criarTabela(){
		int length = this.cadeia.length();
		this.tabela = new String[length + 1][];
		this.tabela[0] = new String[length];

		for(int i = 1; i < this.tabela.length; i++){
			this.tabela[i] = new String[length - (i - 1)];
		}
		
		for(int i = 1; i < this.tabela.length; i++){
			for(int j = 0; j < this.tabela[i].length; j++){
				this.tabela[i][j] = "";
			}
		}
	}

	public void doCyk(){
		
		//Passo 1: Preenche o cabeçalho da tabela com a cadeia (linha 0 da tabela)
		for(int i = 0; i < this.tabela[0].length; i++){
			this.tabela[0][i] = Character.toString( this.cadeia.charAt(i) );
		}
		
		//Passo 2: Busca as produções dos terminais (linha 1 da tabela)
		for(int i = 0; i < this.tabela[1].length; i++){
			String[] validCombinations = this.verificaSeProduz( this.tabela[0][i] );
			this.tabela[1][i] = this.toString(validCombinations);
		}
		if( this.cadeia.length() <= 1) { return; }
		
		//Passo 3: Busca produções de subcadeias de tamanho 2
		for(int i = 0; i < this.tabela[2].length; i++){
			String[] downwards = toArray(this.tabela[1][i]);
			String[] diagonal = toArray(this.tabela[1][i+1]);
			String[] validCombinations = this.verificaSeProduz( this.buscaTodasAsCombinacoes(downwards, diagonal));
			this.tabela[2][i] = this.toString(validCombinations);
		}
		if( this.cadeia.length() <= 2){ return; }
		
		//Passo 3: Busca produções de subcadeias de tamanho n
		TreeSet<String> currentValues = new TreeSet<String>();

		for(int i = 3; i < this.tabela.length; i++){
			for(int j = 0; j < this.tabela[i].length; j++){
				for(int compareFrom = 1; compareFrom < i; compareFrom++){
					String[] downwards = this.tabela[compareFrom][j].split("\\s");
					String[] diagonal = this.tabela[i-compareFrom][j+compareFrom].split("\\s");
					String[] combinations = this.buscaTodasAsCombinacoes(downwards, diagonal);
					String[] validCombinations = this.verificaSeProduz(combinations);
					if(this.tabela[i][j].isEmpty()){
						this.tabela[i][j] = toString(validCombinations);
					}else{
						String[] oldValues = toArray(this.tabela[i][j]);
						ArrayList<String> newValues = new ArrayList<String>(Arrays.asList(oldValues));
						newValues.addAll(Arrays.asList(validCombinations));
						currentValues.addAll(newValues);
						this.tabela[i][j] = toString(currentValues.toArray(new String[currentValues.size()]));
					}
				}
				currentValues.clear();
			}
		}
	}

	public String[] verificaSeProduz(String ... toCheck){
		List<String> storage = new ArrayList<>();
		//procura quais producoes terminais produzem 
		for (Producao p: this.gramatica.getProducoes()) {
			//verifica todas as strings que forem passadas por parametro
			for(String current : toCheck){
				if( p.produz(current) ) {
					storage.add( p.getVariavel() );
				}
			}
		}
		
        if(storage.size() == 0) { return new String[] {}; }
        return storage.toArray(new String[storage.size()]);
    }
	
	public String[] buscaTodasAsCombinacoes(String[] from, String[] to){
        int length = from.length * to.length;
        int counter = 0;
        String[] combinations = new String[length];
        if(length == 0){ return combinations; };
        for(int i = 0; i < from.length; i++){
            for(int j = 0; j < to.length; j++){
                combinations[counter] = from[i] + to[j];
                counter++;
            }
        }
        return combinations;
    }
	
	public String toString(String[] input){
        return Arrays.toString(input).replaceAll("[\\[\\]\\,]", "");
    }
	public static String[] toArray(String input){
        return input.split("\\s");
    }

	public boolean pertence() {
		return this.tabela[this.tabela.length-1][this.tabela[this.tabela.length-1].length-1].contains(this.gramatica.getEstadoInicial());
	}
	
	public void setTabela(String[][] tabela) {
		this.tabela = tabela;
	}
	public String getCadeia() {
		return cadeia;
	}
	public void setCadeia(String cadeia) {
		this.cadeia = cadeia;
	}

	
	//*********************************************
	// input parser
	//*********************************************
	private static final int LINHA_RAIZ = 1;
	private static final int LINHA_VARIAVEIS = 2;
	private static final int LINHA_TERMINAIS = 3;
	private static final int LINHA_PRODUCOES = 4;
	private static final int LINHA_PARAVRAS = 5;
	
	public static List<Problema> parseInput( Scanner input ) throws IOException {
		
		List<Problema> problemas = new ArrayList<>();
		
		int tipoLinha = LINHA_RAIZ;
		Gramatica g = null;
		List<String> palavras = new ArrayList<>();
		
		while ( input.hasNextLine() ) {
			String line = input.nextLine();
			
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


//*********************************************
// classes
//*********************************************

class Gramatica {
	
	private String estadoInicial;
	private List<String> variaveis;
	private List<String> terminais;
	private List<Producao> producoes;
	
	public Gramatica() {
		this.producoes = new ArrayList<>();
	}
	
	@Override
	public String toString() {
		return " S=" + estadoInicial + ", V=" + variaveis + ", T=" + terminais + ", Prod=" + producoes;
	}
	
	public void addProducao( Producao producao ) {
		this.producoes.add(producao);
	}
	
	public boolean isVariavel( String caracter ) {
		return this.variaveis.contains(caracter);
	}
	
	public String getValorProduzido( Producao p ) {
		//producao terminal
		if ( p.getProducao()[1] == null ) {
			return this.getValorVariavel(p.getVariavel());
		}
		//producao de variaveis
		else {
			return p.getProducao()[0] + p.getProducao()[1];
		}
		
	}
	private String getValorVariavel( String variavel ) {
		for (Producao p: this.producoes) {
			if ( p.getVariavel().equals(variavel) ) {
				return p.getProducao()[0];
			}
		}
		return null;
	}
	
	public List<Producao> getProducoesDaVariavel( String variavel ){
		List<Producao> lista = new ArrayList<>();
		this.producoes.forEach(p -> {
			if ( p.getVariavel().equals(variavel) ) {
				lista.add(p);
			}
		});
		return lista;
	}
	
	public List<Producao> getProducoesDaVariavel( char variavel ){
		return this.getProducoesDaVariavel( String.valueOf( variavel ) );
	}
	
	public String getEstadoInicial() {
		return estadoInicial;
	}
	public void setEstadoInicial(String estadoInicial) {
		this.estadoInicial = estadoInicial;
	}
	public List<String> getVariaveis() {
		return variaveis;
	}
	public void setVariaveis(List<String> variaveis) {
		this.variaveis = variaveis;
	}
	public List<String> getTerminais() {
		return terminais;
	}
	public void setTerminais(List<String> terminais) {
		this.terminais = terminais;
	}
	public List<Producao> getProducoes() {
		return producoes;
	}
	public void setProducoes(List<Producao> producoes) {
		this.producoes = producoes;
	}

}
class Producao {
	
	private String variavel;
	private String resultado;
	
	private String [] producao;
	
	public Producao(String line) {
		String[] vet = line.split(" -> ");
		this.variavel = vet[0];
		this.resultado = vet[1];
		
		this.producao = new String[2];
		this.producao[0] = String.valueOf( vet[1].charAt(0) );
		if ( vet[1].trim().length() > 1 ) {
			this.producao[1] = String.valueOf( vet[1].charAt(1) );
		}
	}
	
	public boolean produz( String resultado ) {
		return this.resultado.equals(resultado);
	}
	
	public String getProducao(int index) {
		return this.producao[index];
	}
	
	public String getProducaoEsquerda() {
		return this.producao[0];
	}
	
	@Override
	public String toString() {
		return variavel + " -> " + producao[0] + (producao[1] != null ? producao[1] : "");
	}

	public String getVariavel() {
		return variavel;
	}

	public void setVariavel(String variavel) {
		this.variavel = variavel;
	}

	public String[] getProducao() {
		return producao;
	}

	public void setProducao(String[] produz) {
		this.producao = produz;
	}
}
class Problema {

	private Gramatica gramatica;
	private List<String> paravrasParaTestar;
	
	@Override
	public String toString() {
		return "Problema { gramatica: " + gramatica + ", paravrasParaTestar: " + paravrasParaTestar + " }";
	}
	
	public Gramatica getGramatica() {
		return gramatica;
	}
	public void setGramatica(Gramatica gramatica) {
		this.gramatica = gramatica;
	}
	public List<String> getParavrasParaTestar() {
		return paravrasParaTestar;
	}
	public void setParavrasParaTestar(List<String> paravrasParaTestar) {
		this.paravrasParaTestar = paravrasParaTestar;
	}	
}
