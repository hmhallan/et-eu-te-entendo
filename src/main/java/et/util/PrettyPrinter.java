package et.util;

import et.ReconhecedorLLC;

public class PrettyPrinter {

	private ReconhecedorLLC reconhecedor;

	public PrettyPrinter( ReconhecedorLLC reconhecedor ) {
		this.reconhecedor = reconhecedor;
	}

	public void printDetail(){
		System.out.println("======================================================================================");
		System.out.println("Palavra: " + this.reconhecedor.getCadeia() );
		System.out.println("Gramática: " + this.reconhecedor.getGramatica() );
		System.out.println("CYK:");
		this.drawTable(this.reconhecedor.getTabela());
	}
	
	public void printResult(){
		if(this.reconhecedor.pertence()){
			System.out.println(this.reconhecedor.getCadeia() + " e uma palavra valida");
		}else{
			System.out.println(this.reconhecedor.getCadeia() + " nao e uma palavra valida");
		}
	}

	private void drawTable(String[][] cykTable){
		int l = findLongestString(cykTable) + 2;
		String formatString = "| %-" + l + "s ";
		String s = "";
		StringBuilder sb = new StringBuilder();
		//Building Table Structure Modules
		sb.append("+");
		for(int x = 0; x <= l + 2; x++){
			if(x == l + 2){ 
				sb.append("+");
			}else{
				sb.append("-");
			}
		}
		String low = sb.toString();
		sb.delete(0, 1);
		String lowRight = sb.toString();
		//Print Table
		for(int i = 0; i < cykTable.length; i++){
			for(int j = 0; j <= cykTable[i].length; j++){
				System.out.print((j == 0) ? low : (i <= 1 && j == cykTable[i].length - 1) ? "" : lowRight);
			}
			System.out.println();
			for(int j = 0; j < cykTable[i].length; j++){
				s = (cykTable[i][j].isEmpty()) ? "-" : cykTable[i][j];
				System.out.format(formatString, s.replaceAll("\\s", ","));
				if(j == cykTable[i].length - 1) { System.out.print("|"); }
			}
			System.out.println();
		}
		System.out.println(low+"\n");
		
		//Step 4: Evaluate success.
		if(this.reconhecedor.pertence()){
			System.out.println("A palavra \"" + this.reconhecedor.getCadeia() + "\" é um elemento da GLC e pode ser derivada.");
		}else{
			System.out.println("A palavra \"" + this.reconhecedor.getCadeia() + "\" NÃO é um elemento da GLC e NÃO pode ser derivada.");
		}
	}

	private int findLongestString(String[][] cykTable){
		int x = 0;
		for(String[] s : cykTable){
			for(String d : s){
				if(d.length() > x){ x = d.length(); }
			}
		}
		return x;
	}

}
