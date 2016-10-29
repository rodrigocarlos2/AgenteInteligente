

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main{
	
	static boolean matou=false;
	
	static char ambiente2[][] = new char[4][4];
	// Ambiente2 guarda o ambiente inicial/ Aquele sem ocorrer nenhuma movimentação do Agente
	
	static char brizas[][] = new char[4][4];
	// Esta matriz fica responsável por armazenar as brisas
	
	static char fedor[][] = new char[4][4];
	// Esta matriz fica responsável por armazenar as brisas
	
	static char imaginacaoWumpus[][] = new char[4][4];
	// Aqui é uma matriz que mostra a imaginação do Agente, o que o Agente tem de interpretação do
	// cenário está aqui!
	
	static ArrayList<Position> posicoes = new ArrayList<Position>();
	// ArrayList auxiliar para chamada durante o programa
	
	static ArrayList<Position> areas_visitadas = new ArrayList<Position>();
	// Aqui são armazenadas as áreas já visitadas pelo agente
	// Vale lembrar que armazena áreas anteriores.
	
	static ArrayList<Position> areas_perigosas = new ArrayList<Position>();
	// Aqui são armazenadas áreas perigosas descobertas pelo agente
	// Vale lembrar que armazena áreas anteriores.
	
	static ArrayList<Position> areas_seguras = new ArrayList<Position>();
	// Aqui ficam as áreas consideradas seguras para o agente.
	// Vale lembrar que armazena áreas anteriores.
	
	static ArrayList<Position> areas_possiveis = new ArrayList<Position>();
	// Neste array ficam as possíveis jogadas dos usuários.
	
	static Scanner ent = new Scanner(System.in);
	
	public static int random(int max){
		
		Random r =  new Random();
		return r.nextInt(max);
		
	}
	
	// Função initAmbiente: inicia o ambiente, colocando o Wumpus, Agente, Brizas, Fedor e Poços nos seus lugares.
	public static void initAmbiente(char ambiente[][]){
		
		for(int i=0; i<4; i++){
			for(int j=0; j<4; j++){
				ambiente[i][j]='-';
				// Caractere - é considerado nada no cenário
			}
		}
		
		ambiente[3][0] = 'G';
		// G é o guerreiro, nome dado ao agente inteligente
		
		int quant_pocos=0;
		int quant_wumpus=0;
		int quant_ouro=0;
		
		// G de Guerreiro
		
		while(quant_pocos<3){
			
			int row = random(4);
			int col = random(4);
			
			if(row==3 && col==0){
				continue;
			}
			else{
				
				if(ambiente[row][col]=='-'){
					ambiente[row][col] = 'P';
					// Um novo poço foi inserido
					quant_pocos++;
				}
				
			}
			
		}
		
		while(quant_wumpus!=1){
			
			int row = random(4);
			int col = random(4);
			
			if(row==3 && col==0){
				continue;
			}
			else{
				
				if(ambiente[row][col]=='-'){
					
					ambiente[row][col] = 'W';
					// Um Wumpus foi inserido
					quant_wumpus++;
					
				}
				
			}
			
		}
		
		while(quant_ouro!=1){
			
			int row = random(4);
			int col = random(4);
			
			if(row==3 && col==0){
				continue;
			}
			else{
				
				if(ambiente[row][col]=='-'){
					ambiente[row][col] = 'O';
					// Um ouro foi inserido
					quant_ouro++;
				}
				
			}
			
		}
		
		// Inserindo Fedor
		
		int rowWumpus=0, colWumpus=0;
		
		for(int i=0; i<4; i++){
			for(int j=0; j<4; j++){
				
				if(ambiente[i][j]=='W'){
					rowWumpus = i;
					colWumpus = j;
					break;
				}
				
			}
		}
		
		// Condicionais para montar fedor
		if(colWumpus>0){
			
			if(ambiente[rowWumpus][colWumpus-1]=='-'){
				ambiente[rowWumpus][colWumpus-1] = 'F';
				// Fedor inserido no cenário
			}
			
		}
		if(colWumpus<3){
			
			if(ambiente[rowWumpus][colWumpus+1]=='-'){
				ambiente[rowWumpus][colWumpus+1] = 'F';
				// Fedor inserido no cenário
			}
			
		}
		if(rowWumpus>0){
			
			if(ambiente[rowWumpus-1][colWumpus]=='-'){
				ambiente[rowWumpus-1][colWumpus] = 'F';
				// Fedor inserido no cenário
			}
			
		}
		if(rowWumpus<3){
			
			if(ambiente[rowWumpus+1][colWumpus]=='-'){
				ambiente[rowWumpus+1][colWumpus] = 'F';
				// Fedor inserido no cenário
			}
			
		}
		
		// Condicionais para montar as brizas
		
		for(int i=0; i<4; i++){
			
			boolean existe = false;
			
			int rowPoco=0;
			int colPoco=0;
			
			for(int j=0; j<4; j++){
				
				if(ambiente[i][j]=='P'){
					rowPoco = i;
					colPoco = j;
					existe = true;
				}
				
				if(existe){
					
					if(colPoco>0){

						if(ambiente[rowPoco][colPoco-1]!='P')
							brizas[rowPoco][colPoco-1] = 'B';
						// Brizas inserido no cenário
						
						if(ambiente[rowPoco][colPoco-1]=='-'){
							ambiente[rowPoco][colPoco-1] = 'B';
							// Brizas inserido no cenário
						}
						
					}
					if(colPoco<3){

						if(ambiente[rowPoco][colPoco+1]!='P')
							brizas[rowPoco][colPoco+1] = 'B';
						// Brizas inserido no cenário
						
						if(ambiente[rowPoco][colPoco+1]=='-'){
							ambiente[rowPoco][colPoco+1] = 'B';
							// Brizas inserido no cenário
						}
						
					}
					if(rowPoco>0){

						if(ambiente[rowPoco-1][colPoco]!='P')
							brizas[rowPoco-1][colPoco] = 'B';
						// Brizas inserido no cenário
						
						if(ambiente[rowPoco-1][colPoco]=='-'){
							ambiente[rowPoco-1][colPoco] = 'B';
							// Brizas inserido no cenário
						}
						
					}
					if(rowPoco<3){
						
						if(ambiente[rowPoco+1][colPoco]!='P')
							brizas[rowPoco+1][colPoco] = 'B';
						// Brizas inserido no cenário
						
						if(ambiente[rowPoco+1][colPoco]=='-'){
							ambiente[rowPoco+1][colPoco] = 'B';
							// Brizas inserido no cenário
						}
						
					}
					
					existe = false;
					
				}
				
			}
			
		}
		
		// Condicionais para montar os locais com fedor
		
		for(int i=0; i<4; i++){
			
			boolean existe = false;
			
			int rowLocalWumpus=0;
			int colLocalWumpus=0;
			
			for(int j=0; j<4; j++){
				
				if(ambiente[i][j]=='W'){
					rowLocalWumpus = i;
					colLocalWumpus = j;
					existe = true;
				}
				
				if(existe){
					
					if(colLocalWumpus>0){

						if(ambiente[rowLocalWumpus][colLocalWumpus-1]!='W')
							fedor[rowLocalWumpus][colLocalWumpus-1] = 'F';
						
						if(ambiente[rowLocalWumpus][colLocalWumpus-1]=='-'){
							ambiente[rowLocalWumpus][colLocalWumpus-1] = 'F';
						}
						
					}
					if(colLocalWumpus<3){

						if(ambiente[rowLocalWumpus][colLocalWumpus+1]!='W')
							fedor[rowLocalWumpus][colLocalWumpus+1] = 'F';
						
						if(ambiente[rowLocalWumpus][colLocalWumpus+1]=='-'){
							ambiente[rowLocalWumpus][colLocalWumpus+1] = 'F';
						}
						
					}
					if(rowLocalWumpus>0){

						if(ambiente[rowLocalWumpus-1][colLocalWumpus]!='W')
							fedor[rowLocalWumpus-1][colLocalWumpus] = 'F';
						
						if(ambiente[rowLocalWumpus-1][colLocalWumpus]=='-'){
							ambiente[rowLocalWumpus-1][colLocalWumpus] = 'F';
						}
						
					}
					if(rowLocalWumpus<3){
						
						if(ambiente[rowLocalWumpus+1][colLocalWumpus]!='W')
							fedor[rowLocalWumpus+1][colLocalWumpus] = 'F';
						
						if(ambiente[rowLocalWumpus+1][colLocalWumpus]=='-'){
							ambiente[rowLocalWumpus+1][colLocalWumpus] = 'F';
						}
						
					}
					
					existe = false;
					
				}
				
			}
			
		}
		
	}
	
	// Função showAmbiente: apresente a matriz do ambiente, pode ser usado para mostrar outras matrizes trabalhadas
	public static void showAmbiente(char ambiente[][]){
		
		for(int i=0; i<4; i++){
			for(int j=0; j<4; j++){
				System.out.print("| "+ambiente[i][j]+" |");
			}
			System.out.println(" ");
		}
		
	}
	
	// Função compare: compara um objeto Position com os pertecentes de um ArrayList
	// retorna true se encontrar, false caso contrário
	public static boolean compare(ArrayList<Position> tals, Position tal){
		
		int i;
		
		for(i=0; i<tals.size(); i++){
			
			Position temp = tals.get(i);
			
			if(temp.row==tal.row && temp.col==tal.col){
				return true;
			}
			
		}
		
		return false;
		
	}
	
	// Função start: aqui funciona a lógica do agente
	public static char[][] start(char ambiente[][]){
		
		int r=-1, c=-1;
		
		for(int i=0; i<4; i++){
			for(int j=0; j<4; j++){
				if(ambiente[i][j]=='G'){
					r = i;
					c = j;
				}
			}
		}
		
		Position p = new Position();
		
		p.row=r;
		p.col=c;
		
		if(brizas[p.row][p.col]=='B'){
			
			if(!compare(areas_seguras, p)){
				
				areas_seguras.add(p);
				
				Position te = new Position();
				
				te.row = p.row-1;
				te.col = p.col;
				
				if(p.row>0 && !compare(areas_visitadas, te)){
					
					if(!compare(areas_perigosas, te)){
						areas_perigosas.add(te);
					}
					
				}
				
				te = new Position();
				
				te.row = p.row+1;
				te.col = p.col;
				
				if(p.row<3 && !compare(areas_visitadas, te)){
					
					if(!compare(areas_perigosas, te)){
						areas_perigosas.add(te);
					}
					
				}
				
				te = new Position();
				
				te.col = p.col-1;
				te.row = p.row;
				
				if(p.col>0 && !compare(areas_visitadas, te)){
					
					if(!compare(areas_perigosas, te)){
						areas_perigosas.add(te);
					}
					
				}
				
				te = new Position();
				
				te.col = p.col+1;
				te.row = p.row;
				
				if(p.col<3 && !compare(areas_visitadas, te)){
					
					if(!compare(areas_perigosas, te)){
						areas_perigosas.add(te);
					}
					
				}
				
				te = new Position();
				
			}
		}
		
		
		
		if(brizas[p.row][p.col]=='F'){
			if(!compare(areas_seguras, p)){
				areas_seguras.add(p);
			}
		}
		
		if(brizas[p.row][p.col]!='B' && ambiente[p.row][p.col]!='F' && brizas[p.row][p.col]=='-'){
			
			if(!compare(areas_seguras, p)){
				areas_seguras.add(p);
			}
			
			// Pode-se fazer algumas inferências com o ambiente
			
			if(p.col>0){
				
				Position temp = new Position();
				
				temp.col = p.col-1;
				temp.row = p.row;
				
				if(!compare(areas_seguras, temp)){
					areas_seguras.add(temp);
				}
				
			}
			if(p.col<3){
				
				Position temp = new Position();
				
				temp.col = p.col+1;
				temp.row = p.row;
				
				if(!compare(areas_seguras, temp)){
					areas_seguras.add(temp);
				}
				
			}
			if(p.row>0){
				
				Position temp = new Position();
				
				temp.col = p.col;
				temp.row = p.row-1;
				
				if(!compare(areas_seguras, temp))
					areas_seguras.add(temp);
				
			}
			if(p.row<3){
				
				Position temp = new Position();
				
				temp.col = p.col;
				temp.row = p.row+1;
				
				if(!compare(areas_seguras, temp))
					areas_seguras.add(temp);
				
			}
		}
		else{
			
			if(brizas[p.row][p.col]=='B'){
				
				if(p.col>0){
					
					Position temp = new Position();
					
					temp.col = p.col-1;
					temp.row = p.row;
					
					if(!compare(areas_perigosas, temp))
						areas_perigosas.add(temp);
					
				}
				if(p.col<3){
					
					Position temp = new Position();
					
					temp.col = p.col+1;
					temp.row = p.row;
					
					if(!compare(areas_perigosas, temp))
						areas_perigosas.add(temp);
					
				}
				if(p.row>0){
					
					Position temp = new Position();
					
					temp.col = p.col;
					temp.row = p.row-1;
					
					if(!compare(areas_perigosas, temp))
						areas_perigosas.add(temp);
					
				}
				if(p.row<3){
					
					Position temp = new Position();
					
					temp.col = p.col;
					temp.row = p.row+1;
					
					if(!compare(areas_perigosas, temp))
						areas_perigosas.add(temp);
					
				}
				
			}
			
			for(int i=0; i<areas_perigosas.size(); i++){
				
				Position temp = areas_perigosas.get(i);
				
				for(int j=0; j<areas_visitadas.size(); j++){
					
					Position temp2 = areas_visitadas.get(j);
					
					if(temp.row==temp2.row && temp.col==temp2.col){
						areas_perigosas.remove(temp);
					}
					
				}
				
			}
			
		}
		
		for(int i=0; i<4; i++){
			
			for(int j=0; j<4; j++){
				
				if(ambiente2[i][j]=='-' && ambiente[i][j]=='G'){
						
						if(p.col>0){
							
							Position temp = new Position();
							
							temp.col = p.col-1;
							temp.row = p.row;
							
							if(!compare(areas_seguras, temp))
								areas_seguras.add(temp);
							
						}
						if(p.col<3){
							
							Position temp = new Position();
							
							temp.col = p.col+1;
							temp.row = p.row;
							
							if(!compare(areas_seguras, temp))
								areas_seguras.add(temp);
							
						}
						if(p.row>0){
							
							Position temp = new Position();
							
							temp.col = p.col;
							temp.row = p.row-1;
							
							if(!compare(areas_seguras, temp))
								areas_seguras.add(temp);
							
						}
						if(p.row<3){
							
							Position temp = new Position();
							
							temp.col = p.col;
							temp.row = p.row+1;
							
							if(!compare(areas_seguras, temp))
								areas_seguras.add(temp);
							
						}
				}
			}
			
		}
		
		if(fedor[p.row][p.col]=='F'){
			
			imaginacaoWumpus[p.row][p.col]='F';
			
			if(p.row>0 && imaginacaoWumpus[p.row-1][p.col]=='0'){
				imaginacaoWumpus[p.row-1][p.col] = 'W';
			}
			
			if(p.row<3 && imaginacaoWumpus[p.row+1][p.col]=='0'){
				imaginacaoWumpus[p.row+1][p.col] = 'W';
			}
			
			if(p.col>0 && imaginacaoWumpus[p.row][p.col-1]=='0'){
				imaginacaoWumpus[p.row][p.col-1] = 'W';
			}
			
			if(p.col<3 && imaginacaoWumpus[p.row][p.col+1]=='0'){
				imaginacaoWumpus[p.row][p.col+1] = 'W';
			}
			
		}
		
		if(ambiente2[p.row][p.col]=='-'){
			imaginacaoWumpus[p.row][p.col] = '-';
		}
		
		if(ambiente2[p.row][p.col]=='B' && fedor[p.row][p.col]!='F'){
			imaginacaoWumpus[p.row][p.col] = '-';
		}
		else if(ambiente2[p.row][p.col]=='B' && fedor[p.row][p.col]=='F'){
			imaginacaoWumpus[p.row][p.col] = 'F';
		}
		
		for(int i=0; i<4; i++){
			for(int j=0; j<4; j++){
				if(imaginacaoWumpus[i][j]=='-'){
					
					if(j<3 && imaginacaoWumpus[i][j+1]=='0'){
						imaginacaoWumpus[i][j+1] = 'N';
					}
					
					if(j>0 && imaginacaoWumpus[i][j-1]=='0'){
						imaginacaoWumpus[i][j-1] = 'N';
					}
					
					if(i>0 && imaginacaoWumpus[i-1][j]=='0'){
						imaginacaoWumpus[i-1][j] = 'N';
					}
					
					if(i<3 && imaginacaoWumpus[i+1][j]=='0'){
						imaginacaoWumpus[i+1][j] = 'N';
					}	
				}	
			}
		}
		
		if(ambiente2[p.row][p.col]=='F'){
			
			if(p.row>0){
				
				Position tal = new Position();
				
				tal.row=p.row-1;
				tal.col=p.col;
				
				if(!compare(areas_perigosas, tal)){
					areas_perigosas.add(tal);
				}
				
			}
			if(p.row<3){
				
				Position tal = new Position();
				
				tal.row=p.row+1;
				tal.col=p.col;
				
				if(!compare(areas_perigosas, tal)){
					areas_perigosas.add(tal);
				}
				
			}
			if(p.col>0){
	
				Position tal = new Position();
				
				tal.row=p.row;
				tal.col=p.col-1;
				
				if(!compare(areas_perigosas, tal)){
					areas_perigosas.add(tal);
				}
	
			}
			
			if(p.col<3){
				
				Position tal = new Position();
				
				tal.row=p.row;
				tal.col=p.col+1;
				
				if(!compare(areas_perigosas, tal)){
					areas_perigosas.add(tal);
				}
				
			}
			
		}
			
		for(int i=0; i<areas_perigosas.size(); i++){
				
				Position temp = areas_perigosas.get(i);
				
				for(int j=0; j<areas_visitadas.size(); j++){
					
					Position temp2 = areas_visitadas.get(j);
					
					if(temp.row==temp2.row && temp.col==temp2.col){
						areas_perigosas.remove(temp);
					}
					
				}
				
		}
		
		if(!compare(areas_visitadas, p))
			areas_visitadas.add(p);
		
		// Então agora vamos movimentar o agente
		
		int countWumpus=0;
		int xWumpus=-1;
		int yWumpus=-1;
		
		for(int i=0; i<4; i++){
			
			for(int j=0; j<4; j++){
				
				if(imaginacaoWumpus[i][j]=='W'){
					countWumpus++;
					xWumpus =  i;
					yWumpus = j;
				}
				
			}
			
		}
		
		if(countWumpus==1){
			
			System.out.println("Rodrigo, eu sei onde está o Wumpus. Vou matá-lo!");
			
			ambiente[p.row][p.col] = ambiente2[p.row][p.col];
			
			ambiente[xWumpus][yWumpus] = 'G';
			
			matou=true;
			
			Position Wumpus = new Position();
			Wumpus.row = xWumpus;
			Wumpus.col = yWumpus;
			
			areas_seguras.add(Wumpus);
			
			ambiente2[xWumpus][yWumpus] = '-';
			
			for(int i=0; i<4; i++){
				
				for(int j=0; j<4; j++){
					
					fedor[i][j]='-';
					
					if(ambiente[i][j]=='F'){
						
						Position novo = new Position();
						
						novo.row = i;
						novo.col = j;
						
						areas_seguras.add(novo);
						
						ambiente[i][j] = '-';
						ambiente2[i][j] = '-';
						
					}
					
				}
				
			}
			
			for(int i=0; i<4; i++){
				
				for(int j=0; j<4; j++){
					
					imaginacaoWumpus[i][j]='0';
					
				}
				
			}
			
			return ambiente;
			
		}
		
		if(fedor[p.row][p.col]=='F'){
			
			if(p.row>0){
				
				Position t = new Position();
				
				t.row = p.row-1;
				t.col = p.col;
				
				if(compare(areas_seguras, t)){
					
					for(int i=0; i<areas_seguras.size(); i++){
						if(areas_seguras.get(i).row==t.row && areas_seguras.get(i).col==t.col){
							areas_seguras.remove(i);
							break;
						}
					}
					
				}
				
			}
			if(p.row<3){
				
				Position t = new Position();
				
				t.row = p.row+1;
				t.col = p.col;
				
				if(compare(areas_seguras, t)){
					
					for(int i=0; i<areas_seguras.size(); i++){
						if(areas_seguras.get(i).row==t.row && areas_seguras.get(i).col==t.col){
							areas_seguras.remove(i);
							break;
						}
					}
					
				}
				
			}
			if(p.col>0){
				
				Position t = new Position();
				
				t.row = p.row;
				t.col = p.col-1;
				
				if(compare(areas_seguras, t)){
					
					for(int i=0; i<areas_seguras.size(); i++){
						if(areas_seguras.get(i).row==t.row && areas_seguras.get(i).col==t.col){
							areas_seguras.remove(i);
							break;
						}
					}
					
				}
				
			}
			
			if(p.col<3){
				
				Position t = new Position();
				
				t.row = p.row;
				t.col = p.col+1;
				
				if(compare(areas_seguras, t)){
					
					for(int i=0; i<areas_seguras.size(); i++){
						if(areas_seguras.get(i).row==t.row && areas_seguras.get(i).col==t.col){
							areas_seguras.remove(i);
							break;
						}
					}
					
				}
				
			}
			
		}
		
		if(brizas[p.row][p.col]=='B'){
			
			if(p.row>0){
				
				Position t = new Position();
				
				t.row = p.row-1;
				t.col = p.col;
				
				if(compare(areas_seguras, t)){
					
					for(int i=0; i<areas_seguras.size(); i++){
						if(areas_seguras.get(i).row==t.row && areas_seguras.get(i).col==t.col){
							areas_seguras.remove(i);
							break;
						}
					}
					
				}
				
			}
			if(p.row<3){
				
				Position t = new Position();
				
				t.row = p.row+1;
				t.col = p.col;
				
				if(compare(areas_seguras, t)){
					
					for(int i=0; i<areas_seguras.size(); i++){
						if(areas_seguras.get(i).row==t.row && areas_seguras.get(i).col==t.col){
							areas_seguras.remove(i);
							break;
						}
					}
					
				}
				
			}
			if(p.col>0){
				
				Position t = new Position();
				
				t.row = p.row;
				t.col = p.col-1;
				
				if(compare(areas_seguras, t)){
					
					for(int i=0; i<areas_seguras.size(); i++){
						if(areas_seguras.get(i).row==t.row && areas_seguras.get(i).col==t.col){
							areas_seguras.remove(i);
							break;
						}
					}
					
				}
				
			}
			
			if(p.col<3){
				
				Position t = new Position();
				
				t.row = p.row;
				t.col = p.col+1;
				
				if(compare(areas_seguras, t)){
					
					for(int i=0; i<areas_seguras.size(); i++){
						if(areas_seguras.get(i).row==t.row && areas_seguras.get(i).col==t.col){
							areas_seguras.remove(i);
							break;
						}
					}
					
				}
				
			}
		}
		
		for(int i=0; i<areas_visitadas.size(); i++){
			
			Position t = new Position();
			
			t = areas_visitadas.get(i);
			
			if(compare(areas_perigosas, t)){
				
				for(int j=0; j<areas_perigosas.size(); j++){
					
					if(areas_perigosas.get(j).row==t.row && areas_perigosas.get(j).col==t.col){
						areas_perigosas.remove(j);
					}
					
				}
				
			}
			
		}
		
		System.out.print("Áreas visitadas: ");
		for(int i=0; i<areas_visitadas.size(); i++){
			System.out.print("Position: "+areas_visitadas.get(i).row+" - "+areas_visitadas.get(i).col+" / ");
		}
		System.out.println(" ");
		
		System.out.print("Áreas seguras: ");
		for(int i=0; i<areas_seguras.size(); i++){
			System.out.print("Position: "+areas_seguras.get(i).row+" - "+areas_seguras.get(i).col+" / ");
		}
		System.out.println(" ");
		
		System.out.print("Áreas perigosas: ");
		for(int i=0; i<areas_perigosas.size(); i++){
			System.out.print("Position: "+areas_perigosas.get(i).row+" - "+areas_perigosas.get(i).col+" / ");
		}
		System.out.println(" ");
		
		// Então agora vamos movimentar o agente
		
				Position novo = new Position();
				
				novo.row=-1;
				novo.col=-1;
				
				areas_possiveis.clear();
				
				if(p.row>0){
					
					novo.col = p.col;
					novo.row = p.row-1;
					
					areas_possiveis.add(novo);
					
				}
				
				novo = new Position();
				
				if(p.col<3){
					
					novo.col = p.col+1;
					novo.row = p.row;
					
					areas_possiveis.add(novo);
					
				}
				
				novo = new Position();
				
				if(p.row<3){
					
					novo.col = p.col;
					novo.row = p.row+1;
					
					areas_possiveis.add(novo);
					
				}
				if(p.col>0){
					
					novo.col = p.col-1;
					novo.row = p.row;
					
					areas_possiveis.add(novo);
					
				}
				
				for(int i=0; i<areas_seguras.size(); i++){
					
					Position tal = new Position();
					
					tal = areas_seguras.get(i);
					
					if(!compare(areas_possiveis, tal)){
						areas_possiveis.add(tal);
					}
					
				}
				
				System.out.print("Áreas possíveis de movimentação: ");
				
				for(int i=0; i<areas_possiveis.size(); i++){
					System.out.print(" Position: "+areas_possiveis.get(i).row+" - "+areas_possiveis.get(i).col+" / ");
				}
				
				System.out.println(" ");
				
				// Agora basta encontrar a próxima área para movimentação
				
				for(int i=0; i<areas_possiveis.size(); i++){
					
					Position current = areas_possiveis.get(i);
					
					if(!compare(areas_visitadas, current) && compare(areas_seguras, current) && !compare(areas_perigosas, current)){
						
						ambiente[p.row][p.col] = ambiente2[p.row][p.col];
						ambiente[current.row][current.col] = 'G';
						
						break;
						
					}
					else{
						
						System.out.println("Rodrigo não sei para onde ir!");
						
						if(areas_visitadas.size()==areas_seguras.size() || areas_visitadas.size()>areas_seguras.size()){
						
							System.out.println("Vou escolher ao acaso. Então me deseja sorte.");
							
							int pos = random(areas_possiveis.size());
							
							Position nova = areas_possiveis.get(pos);
							
							ambiente[p.row][p.col] = ambiente2[p.row][p.col];
							ambiente[nova.row][nova.col] = 'G';
						
						}
						else{
							
							// Encontrar a posição final segura
							Position term = new Position();
							
							term.row=-1;
							term.col=-1;
							
							for(int t=0; t<areas_seguras.size(); t++){
								
								Position temp = new Position();
								
								temp = areas_seguras.get(t);
								
								if(!compare(areas_visitadas, temp)){
									
									term.row = temp.row;
									term.col = temp.col;
									
									break;
									
								}
								
							}
							
							// Viajar até a posição final segura
							
							ambiente[p.row][p.col] = ambiente2[p.row][p.col];
							ambiente[term.row][term.col] = 'G';
							
						}
						
						break;	
					}
				}
			
			return ambiente;
		
	}
	
	public static boolean verify(char ambiente[][]){
		
		int row=-1, col=-1;
		
		for(int i=0; i<4; i++){
			for(int j=0; j<4; j++){
				if(ambiente[i][j]=='G'){
					row = i;
					col = j;
					break;
				}	
			}
		}
		
		if(ambiente[row][col]=='G' && ambiente2[row][col]=='W' && matou==false){
			System.out.println("Wumpus matou o guerreiro!");
			return true;
		}
		if(ambiente[row][col]=='G' && ambiente2[row][col]=='P'){
			System.out.println("Guerreiro caiu no poço!");
			return true;
		}
		if(ambiente[row][col]=='G' && ambiente2[row][col]=='O'){
			System.out.println("Guerreiro encontrou o ouro!");
			return true;
		}
		
		return false;
		
	}
	
	public static void main(String args[]){
		
		char ambiente[][] = new char[4][4];
		
		// Iniciar os ambientes: brizas, fedor e imaginacaoWumpus
		
		for(int i=0; i<4; i++){
			for(int j=0; j<4; j++){
				brizas[i][j] = '-';
			}
		}
		
		for(int i=0; i<4; i++){
			for(int j=0; j<4; j++){
				fedor[i][j] = '-';
			}
		}
		
		for(int i=0; i<4; i++){
			for(int j=0; j<4; j++){
				imaginacaoWumpus[i][j] = '0';
			}
		}
		
		// Iniciando o ambiente Wumpus´s World
		initAmbiente(ambiente);
		
		// Nestes for's um ambiente estático é criado para a aplicação
		// ambiente background
		for(int i=0; i<4; i++){
			for(int j=0; j<4; j++){
				if(i==3 && j==0){
					ambiente2[i][j] = '-';
				}
				else{
					ambiente2[i][j] = ambiente[i][j];
				}
			}
		}
		
		showAmbiente(ambiente);
		// Ambiente completamente montado.
		
		System.out.println(" ");
		
		while(true){
			
			ambiente = start(ambiente);
			
			System.out.println(" ");
			System.out.println("--------------------");
			showAmbiente(ambiente);
			System.out.println("--------------------");
			//showAmbiente(brizas);
			//showAmbiente(fedor);
			//showAmbiente(imaginacaoWumpus);
			
			ent.nextLine();
			
			if(verify(ambiente)){
				break;
			}
			
		}
		
	}

}