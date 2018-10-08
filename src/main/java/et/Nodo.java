package et;

import java.util.Queue;

public class Nodo {
	
	private Queue<Nodo> filhos;
	private Producao quemMeProduziu;
	private String valor;
	
	private boolean visitado;
	
	public Nodo(Producao quemMeProduziu, String valor) {
		this.quemMeProduziu = quemMeProduziu;
		this.valor = valor;
	}
	
	@Override
	public String toString() {
		return "Nodo [filhos=" + filhos + ", quemMeProduziu=" + quemMeProduziu + ", valor=" + valor + "]";
	}

	public Queue<Nodo> getFilhos() {
		return filhos;
	}
	public void setFilhos(Queue<Nodo> filhos) {
		this.filhos = filhos;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public Producao getQuemMeProduziu() {
		return quemMeProduziu;
	}
	public void setQuemMeProduziu(Producao quemMeProduziu) {
		this.quemMeProduziu = quemMeProduziu;
	}
	public boolean isVisitado() {
		return visitado;
	}
	public void setVisitado(boolean visitado) {
		this.visitado = visitado;
	}
	
}
