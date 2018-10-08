package et;

import java.util.LinkedList;
import java.util.Queue;

public class ArvoreDerivacao {
	
	Nodo raiz;
	Queue<Nodo> nodos;
	StringBuilder builderPalavra;
	
	public ArvoreDerivacao() {
		this.nodos = new LinkedList<>();
	}
	
	public String getPalavraGerada() {
		this.builderPalavra = new StringBuilder();
		//sempre busca o nodo a esquerda
		this.getValorTerminal(this.raiz);
		return this.builderPalavra.toString();
	}
	
	private void getValorTerminal( Nodo n ) {
		if ( n.getValor().length() > 1  && n.getFilhos() != null) {
			for( Nodo f: n.getFilhos() ) {
				this.getValorTerminal(f);
			}
		}
		else {
			this.builderPalavra.append(n.getValor());
		}
	}
	
	public Nodo proximaDerivacao() {
		if ( raiz.getFilhos() == null ) {
			return raiz;
		}
		else {
			return buscaEmLarguraNavegandoPelaEsquerda( raiz.getFilhos() );
		}
	}
	
	private Nodo buscaEmLarguraNavegandoPelaEsquerda( Queue<Nodo> nodos ) {
		for ( Nodo n: nodos ) {
			if ( ! n.isVisitado() && n.getFilhos() == null ) {
				return n;
			}
		}
		
		//chegou aqui: todos os filhos do nodo foram derivados
		for ( Nodo n: nodos ) {
			if ( n.getFilhos() == null ) {
				return this.buscaEmLarguraNavegandoPelaEsquerda(n.getFilhos());
			}
		}
		return null;
	}
	
	@Override
	public String toString() {
		return "ArvoreDerivacao [" + raiz + "]";
	}
	
	public String estadoDaArvore() {
		StringBuilder sb = new StringBuilder();
		for (Nodo n: this.nodos) {
			sb.append(n.getValor());
		}
		return "[" + sb.toString() + "]";
	}

	public void addNodo(Nodo n) {
		this.nodos.add(n);
	}
	
	public Nodo getRaiz() {
		return raiz;
	}
	public void setRaiz(Nodo raiz) {
		this.raiz = raiz;
	}
	public Queue<Nodo> getNodos() {
		return nodos;
	}
	public void setNodos(Queue<Nodo> nodos) {
		this.nodos = nodos;
	}
	
	
}
