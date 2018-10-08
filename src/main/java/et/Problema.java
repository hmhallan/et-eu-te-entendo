package et;

import java.util.List;

public class Problema {

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
