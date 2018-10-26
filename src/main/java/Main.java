

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class Main {
	
	public static void main(String [] args) throws IOException {
		java.io.Reader input = new InputStreamReader(System.in);
		Main.start(input);
	}

	public static void start( Reader input ) throws FileNotFoundException, IOException {

		char raiz;

		//Leitura do arquivo de entrada
		BufferedReader reader = new BufferedReader(input);

		//Contador de instacias
		int instancia = 1;

		while( reader.ready() ) {

			String linha = reader.readLine();

			//a primeira linha é a raiz           
			raiz = linha.charAt(0);

			//leitura dos não terminal e dos terminais (nao é necessário armazenar)
			//a identificação será feita diretamente pelas regras de produção
			linha = reader.readLine();
			linha = reader.readLine();

			//Listas contendo a gramatica e as cadeias a serem testadas
			List<char[]> regrasTerminais = new ArrayList<>();
			List<char[]> regrasNaoTerminais = new ArrayList<>();     
			List<String> cadeias = new ArrayList<String>();

			//leitura das regras (aqui sim começa o processamento)
			lerRegrasDeProducao(reader, regrasTerminais, regrasNaoTerminais);

			//leitura cas cadeias
			lerCadeias(reader, cadeias);
			
			//inicia o teste de todas as cadeias desta instancia
			testarCadeias(instancia, raiz, cadeias, regrasTerminais, regrasNaoTerminais);

			//Incrementa para a próxima instancia
			instancia += 1;

		}

	}

	private static void lerRegrasDeProducao( BufferedReader reader, List<char[]> regrasTerminais, List<char[]> regrasNaoTerminais ) throws IOException {
		String linha = reader.readLine();

		//converte a regra de produção em um array de char
		char[] vetRegras = linha.toCharArray();

		//pelo tamanho da regra, é possível saber se produz terminal ou não-terminal
		// Tam=7        Tam=6
		// A -> AB      A -> a

		//enquanto não acabar as regras de produção
		while ((vetRegras[0] != '#') && (vetRegras[5] != '#')) {
			if (vetRegras.length == 6) {
				//Lista dos regras não terminais A->a
				regrasTerminais.add(new char[]{vetRegras[0], vetRegras[5]});
			} else {
				//Lista dos regras não terminais A->BC
				regrasNaoTerminais.add(new char[]{vetRegras[0], vetRegras[5], vetRegras[6]});                    
			}
			linha = reader.readLine();
			vetRegras = linha.toCharArray();
		}

	}

	private static void lerCadeias( BufferedReader reader, List<String> cadeias ) throws IOException {
		//leitura das cadeias
		String linha = reader.readLine();
		while (!"#".equalsIgnoreCase(linha)) {
			cadeias.add(linha);
			linha = reader.readLine();
		}

	}


	public static void testarCadeias(int instancia, char raiz, List<String> cadeias, List<char[]> regrasTerminais, List<char[]> regrasNaoTerminais) {
		//mensagem de saída
		System.out.print("\nInstancia " + instancia + "\n");

		//para cada cadeia, efetua a validação
		for (String cadeia : cadeias) {
			if (testarCadeia(raiz, cadeia, regrasTerminais, regrasNaoTerminais)) {
				System.out.print(cadeia + " e uma palavra valida\n");
			} else {
				System.out.print(cadeia + " nao e uma palavra valida\n");
			}
		}
	}

	public static boolean testarCadeia(char raiz, String cadeia, List<char[]> regrasTerminais, List<char[]> regrasNaoTerminais) {
		return derivarRecursivo(raiz, regrasTerminais, regrasNaoTerminais, cadeia.toCharArray(), 0, cadeia.length());
	}

	public static boolean derivarRecursivo(char producaoAtual, List<char[]> regrasTerminais, List<char[]> regrasNaoTerminais, char[] cadeia, int inicio, int fim) {
		// Caso base: S-> a 
		if ((fim - inicio) == 1) {
			//Percorre a lista dos terminais (A->a)
			for (int i = 0; i < regrasTerminais.size(); ++i) {
				//Verifica se a regra do não terminal é uma produção da raiz(S)
				//E se a produção "a" é igual a cadeia do inicio
				if ((regrasTerminais.get(i)[0] == producaoAtual) && (regrasTerminais.get(i)[1] == cadeia[inicio])) {
					return true;
				}
			}
		}
		
		//Outros casos S -> AB
		//Percorre a lista dos não terminais (A->BC)
		for (int i = 0; i < regrasNaoTerminais.size(); ++i) {
			//Somente produções da raiz para derivar
			if (regrasNaoTerminais.get(i)[0] == producaoAtual) {
				//Percorre a cadeia do pivo+1 até o fim
				for (int pivo = inicio + 1; pivo < fim; ++pivo) {
					//Deriva para A e deriva para B
					//Se possivel encontrar um terminal para A e B retorna true
					if (derivarRecursivo(regrasNaoTerminais.get(i)[1], regrasTerminais, regrasNaoTerminais, cadeia, inicio, pivo)
							&& derivarRecursivo(regrasNaoTerminais.get(i)[2], regrasTerminais, regrasNaoTerminais, cadeia, pivo, fim)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
}
