package et;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ReconhecedorLLC {
	
	private Gramatica gramatica;
	private Queue<ArvoreDerivacao> possibilidades;

	public void reconhece( Gramatica g, String palavra ) {
		
		System.out.println("=================================================================");
		System.out.println("Testando " + g + " para a palavra " + palavra );
	
		//passo 1: gerar todas as palavras da gramatica até achar a palavra que se busca 
		//condição de parada: gramatica estar gerando palavras MAIORES que ( palavra atual ou 2N-1 caracteres? Chomsky)
		
		
		//lógica da Ana: ir gerando e enfileirando as possibilidades
		this.possibilidades = new LinkedList<>();
		this.gramatica = g;
		
		//passo 2: buscar todas as producoes possiveis da raiz
		List<Producao> prod = this.gramatica.getProducoesDaVariavel( this.gramatica.getEstadoInicial() );
		
		//para cada producao possivel, gerar uma arvore de derivação e enfileirar
		System.out.println("Produções encontradas para a raiz: " + prod );
		
		prod.forEach( p -> {
			ArvoreDerivacao arvore = new ArvoreDerivacao();
			possibilidades.add(arvore);
			arvore.setRaiz( new Nodo( p, this.gramatica.getValorProduzido(p)) );
		});
		
		this.processaTopoFila();
		
	}
	
	//este processamento deve ser uma busca em largura
	private void processaTopoFila() {
		if ( !this.possibilidades.isEmpty() ) {
			System.out.println("Instancias de arvore na fila: " + this.possibilidades.size());
			
			//processa o proximo elemento da fila
			ArvoreDerivacao a = possibilidades.poll();
			System.out.println("Estado atual da arvore: " + a.getPalavraGerada());
			
			//processa o proximo elemento 'variavel'
			//busca em largura sempre pelo lado ESQUERDO
			
			//o proximo a nao ter filho é o proximo a ser produzido
			Nodo n = a.proximaDerivacao();
			if (n != null) {
				this.derivaNodo(a, n);
			}
			else {
				//finalizou a instancia do nodo: fechou a palavra
				System.out.println("PALAVRA FINALIZADA: " + a.getPalavraGerada() );
			}
			
			this.processaTopoFila();
		}
		else {
			//acabou (somente chega aqui em uma gramatica FINITA)
		}
	}
	
	private void derivaNodo( ArvoreDerivacao arvore, Nodo n ) {
		
		Queue<Nodo> filhos = new LinkedList<>();
		
		//gera nodos de acordo com a produção
		List<Producao> ladoEsq = this.gramatica.getProducoesDaVariavel( n.getValor().charAt(0) );
		System.out.println("Produções encontradas (lado esquerdo) para o nodo [" + n.getValor() + "]" + ladoEsq );
		ladoEsq.forEach( p -> {
			Nodo esq = new Nodo( p, this.gramatica.getValorProduzido(p) );
			arvore.addNodo( esq );
			filhos.add(esq);
		});
		
		//verifica se nodo É terminal ou nao (se tem 2 caracteres)
		if ( n.getValor().length() > 1 ) {
			List<Producao> ladoDir = this.gramatica.getProducoesDaVariavel( n.getValor().charAt(1) );
			System.out.println("Produções encontradas (lado direito) para o nodo [" + n.getValor() + "]" + ladoDir );
			ladoDir.forEach( p -> {
				Nodo dir = new Nodo( p, this.gramatica.getValorProduzido(p) );
				arvore.addNodo( dir );
				filhos.add(dir);
			});
		}
		
		n.setFilhos(filhos);
		
		
		
		//adiciona a arvore na fila
		this.possibilidades.add(arvore);
	}
	
}
