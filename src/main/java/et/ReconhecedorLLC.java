package et;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

import et.util.PrettyPrinter;

public class ReconhecedorLLC {

	private Gramatica gramatica;
	
	private String[][] tabela;
	private String cadeia;

	public void reconhece( Gramatica g, String palavra ) {
		this.gramatica = g;
		this.cadeia = palavra;
		this.cyk();
	}
	
	public void printDetails() {
		PrettyPrinter printer = new PrettyPrinter(this);
		printer.printDetail();
	}
	
	public void printResult() {
		PrettyPrinter printer = new PrettyPrinter(this);
		printer.printResult();
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
		return this.tabela[this.tabela.length-1][this.tabela[this.tabela.length-1].length-1].contains(this.getGramatica().getEstadoInicial());
	}
	
	public Gramatica getGramatica() {
		return gramatica;
	}
	public void setGramatica(Gramatica gramatica) {
		this.gramatica = gramatica;
	}
	public String[][] getTabela() {
		return tabela;
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
	
}
