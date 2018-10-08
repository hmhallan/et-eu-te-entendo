package et;

import java.util.ArrayList;
import java.util.List;

public class Gramatica {
	
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
