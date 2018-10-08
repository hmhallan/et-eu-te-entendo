package et;

public class Producao {
	
	public String variavel;
	
	public String [] producao;
	
	public Producao(String line) {
		String[] vet = line.split(" -> ");
		this.variavel = vet[0];
		
		this.producao = new String[2];
		this.producao[0] = String.valueOf( vet[1].charAt(0) );
		if ( vet[1].trim().length() > 1 ) {
			this.producao[1] = String.valueOf( vet[1].charAt(1) );
		}
	}
	
	//Chomsky só tem 2 produções: 2 variáveis OU 1 terminal
	public boolean isTerminal() {
		return this.producao[1] == null;
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
