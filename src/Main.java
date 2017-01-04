
// Autor: Rodrigo Carlos
// E-mail: rocaleal98@gmail.com
// OBS: se quiser utilizar o c�digo, envie-me uma mensagem.

// Author: Rodrigo Carlos
// E-mail: rocaleal98@gmail.com
// PS: if want use the code, send me a message.

// Projeto desenvolvido no Eclipse Luna Mars.
// Project developed in Eclipse Luna Mars.

// Um algoritmo � uma sequ�ncia de passos finitos e bem definidos que visam solucionar
// um problema.

import java.awt.Image;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main{
	
	static boolean grito=false;
	// Vari�vel respons�vel por armazenar a morte ou vida do Wumpus.
	// grito = false -> Wumpus vivo.
	// grito = true -> Wumpus morto.
	
	// Medida de desempenho
	static int status = 0;
	
	// -1 : movimento do agente
	// +1000: encontrou ouro
	// -1000: morte do agente caindo no po�o, ou encontrando o Wumpus.
	// -10: uso da flecha do agente para derrotar o Wumpus.
	
	static int xInicial = -1;
	static int yInicial = -1;
	static int xFinal = -1;
	static int yFinal = -1;
	
	static char ambiente2[][] = new char[4][4];
	// Ambiente2 guarda o ambiente inicial/ Aquele sem ocorrer nenhuma movimenta��o do Agente.
	
	static char brisas[][] = new char[4][4];
	// Esta matriz fica respons�vel por armazenar as brisas.
	
	static char fedor[][] = new char[4][4];
	// Esta matriz fica respons�vel por armazenar os fedores.
	
	static char brilho[][] = new char[4][4];
	// Esta matriz fica respons�vel por armazenar os brilhos.
	
	static char imaginacaoWumpus[][] = new char[4][4];
	// Aqui � uma matriz que mostra a imagina��o do Agente, o que o Agente tem de interpreta��o do
	// cen�rio sobre a localiza��o do Wumpus est� aqui!
	
	static char imaginacaoOuro[][] = new char[4][4];
	// Aqui � uma matriz que mostra a imagina��o do Agente, o que o Agente tem de interpreta��o do
	// cen�rio sobre a localiza��o do Ouro est� aqui!
	
	static char imaginacaoPocos[][] = new char[4][4];
	// Aqui � uma matriz que mostra a imagina��o do Agente, o que o Agente tem de interpreta��o do
	// cen�rio sobre a localiza��o dos po�os est� aqui!
	
	static ArrayList<Position> posicoes = new ArrayList<Position>();
	// ArrayList auxiliar para chamada durante o programa.
	
	static ArrayList<Position> areas_visitadas = new ArrayList<Position>();
	// Aqui s�o armazenadas as �reas j� visitadas pelo agente.
	
	static ArrayList<Position> areas_perigosas = new ArrayList<Position>();
	// Aqui s�o armazenadas �reas perigosas descobertas pelo agente.
	
	static ArrayList<Position> areas_seguras = new ArrayList<Position>();
	// Aqui ficam as �reas consideradas seguras para o agente.
	
	static ArrayList<Position> areas_possiveis = new ArrayList<Position>();
	// Neste array ficam as poss�veis jogadas ou movimentos do agente.
	
	static Scanner ent = new Scanner(System.in);
	
	// Fun��o para retornar um n�mero rand�mico (aleat�rio).
	// Usada para retornar a posi��o de po�os, e do Wumpus que devem variar aleatoriamente.
	public static int random(int max){
		
		Random r =  new Random();
		return r.nextInt(max);
		
	}
	
	// Fun��o init: inicia o ambiente, colocando o Wumpus, Agente, brisas, Fedor e Po�os nos seus lugares.
	// O ambiente � gerado randomicamente, conforme pedido feito na descri��o do trabalho na disciplina.
	public static void init(char ambiente[][]){
		
		for(int i=0; i<4; i++){
			for(int j=0; j<4; j++){
				ambiente[i][j]='-';
				// Caractere - � considerado nada no cen�rio
			}
		}
		
		ambiente[3][0] = 'G';
		// G � o guerreiro, nome dado ao agente inteligente
		
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
					// Um novo po�o foi inserido
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
				// Fedor inserido no cen�rio
			}
			
		}
		if(colWumpus<3){
			
			if(ambiente[rowWumpus][colWumpus+1]=='-'){
				ambiente[rowWumpus][colWumpus+1] = 'F';
				// Fedor inserido no cen�rio
			}
			
		}
		if(rowWumpus>0){
			
			if(ambiente[rowWumpus-1][colWumpus]=='-'){
				ambiente[rowWumpus-1][colWumpus] = 'F';
				// Fedor inserido no cen�rio
			}
			
		}
		if(rowWumpus<3){
			
			if(ambiente[rowWumpus+1][colWumpus]=='-'){
				ambiente[rowWumpus+1][colWumpus] = 'F';
				// Fedor inserido no cen�rio
			}
			
		}
		
		// Condicionais para montar as brisas
		
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
							brisas[rowPoco][colPoco-1] = 'B';
						// brisas inserido no cen�rio
						
						if(ambiente[rowPoco][colPoco-1]=='-'){
							ambiente[rowPoco][colPoco-1] = 'B';
							// brisas inserido no cen�rio
						}
						
					}
					if(colPoco<3){

						if(ambiente[rowPoco][colPoco+1]!='P')
							brisas[rowPoco][colPoco+1] = 'B';
						// brisas inserido no cen�rio
						
						if(ambiente[rowPoco][colPoco+1]=='-'){
							ambiente[rowPoco][colPoco+1] = 'B';
							// brisas inserido no cen�rio
						}
						
					}
					if(rowPoco>0){

						if(ambiente[rowPoco-1][colPoco]!='P')
							brisas[rowPoco-1][colPoco] = 'B';
						// brisas inserido no cen�rio
						
						if(ambiente[rowPoco-1][colPoco]=='-'){
							ambiente[rowPoco-1][colPoco] = 'B';
							// brisas inserido no cen�rio
						}
						
					}
					if(rowPoco<3){
						
						if(ambiente[rowPoco+1][colPoco]!='P')
							brisas[rowPoco+1][colPoco] = 'B';
						// brisas inserido no cen�rio
						
						if(ambiente[rowPoco+1][colPoco]=='-'){
							ambiente[rowPoco+1][colPoco] = 'B';
							// brisas inserido no cen�rio
						}
						
					}
					
					existe = false;
					
				}
				
			}
			
		}
		
		for(int i=0; i<4; i++){
			
			boolean existe = false;
			
			int rowOuro=0;
			int colOuro=0;
			
			for(int j=0; j<4; j++){
				
				if(ambiente[i][j]=='O'){
					rowOuro = i;
					colOuro = j;
					existe = true;
				}
				
				if(existe){
					
					if(colOuro>0){
						
						brilho[rowOuro][colOuro-1] = 'R';
						
						if(ambiente[rowOuro][colOuro-1]=='-'){
							ambiente[rowOuro][colOuro-1] = 'R';
							// brilho inserido no cen�rio
						}
						
					}
					if(colOuro<3){

						brilho[rowOuro][colOuro+1] = 'R';
						
						if(ambiente[rowOuro][colOuro+1]=='-'){
							ambiente[rowOuro][colOuro+1] = 'R';
							// brilho inserido no cen�rio
						}
						
					}
					if(rowOuro>0){

						brilho[rowOuro-1][colOuro] = 'R';
						
						if(ambiente[rowOuro-1][colOuro]=='-'){
							ambiente[rowOuro-1][colOuro] = 'R';
							// brilho inserido no cen�rio
						}
						
					}
					if(rowOuro<3){
						
						brilho[rowOuro+1][colOuro] = 'R';
						
						if(ambiente[rowOuro+1][colOuro]=='-'){
							ambiente[rowOuro+1][colOuro] = 'R';
							// brilho inserido no cen�rio
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
	// Fim da fun��o init
	
	// Se os dois arrays passados por par�metro forem diferentes, a fun��o retorna true.
	// Sen�o retorna false.
	public static boolean is_different(ArrayList<Position> p1, ArrayList<Position> p2){
		
		boolean is;

		is = true;
		
		for(int i=0; i<p1.size(); i++){
			
			Position temp_p1 = p1.get(i);
			
			is = true;
			
			for(int j=0; j<p2.size(); j++){
				
				Position temp_p2 = p2.get(j);
				
				if(temp_p1.row==temp_p2.row && temp_p1.col==temp_p2.col){
					is = false;
					break;
				}
			}
			
		}
		
		return is;
		
	}
	
	// Fun��o show: apresente a matriz passada por par�metro.
	public static void show(char ambiente[][]){
		
		for(int i=0; i<4; i++){
			for(int j=0; j<4; j++){
				System.out.print("| "+ambiente[i][j]+" |");
			}
			System.out.println(" ");
		}
		
	}
	
	// Fun��o compare: compara um objeto Position com os pertecentes de um ArrayList
	// retorna true se encontrar, false caso contr�rio.
	// Usado para saber por exemplo se a posi��o (3, 0) pertence ao ArrayList areas_seguras.
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
	
	// Fun��o start: aqui funciona o racioc�nio do agente.
	public static char[][] start(char ambiente[][]){
		
		int r=-1, c=-1;
		
		for(int i=0; i<4; i++){
			for(int j=0; j<4; j++){
				if(ambiente[i][j]=='G'){
					r = i;
					c = j;
					// Obtido a posi��o atual do agente.
				}
			}
		}
		
		Position p = new Position();
		
		p.row=r;
		p.col=c;
		
		if(brisas[p.row][p.col]=='B'){
			// Se na posi��o do agente existe uma brisa, essa condi��o � aceita.
			
			if(!compare(areas_seguras, p)){
				
				areas_seguras.add(p);
				
				// Se tem uma brisa, deve-se ent�o supor �reas perigosas, � o que vai ser feito abaixo.
				
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
		
		if(brisas[p.row][p.col]=='F'){
			if(!compare(areas_seguras, p)){
				areas_seguras.add(p);
			}
		}
		
		if(brisas[p.row][p.col]!='B' && ambiente[p.row][p.col]!='F' && brisas[p.row][p.col]=='-'){
			
			// Na aceita��o dessa condi��o, tem-se que a posi��o atual do agente tem '-', que �
			// um s�mbolo que representa nenhum perigo, mas sim �rea segura.
			
			if(!compare(areas_seguras, p)){
				areas_seguras.add(p);
			}
			
			// Como a posi��o atual tem '-', pode-se fazer algumas infer�ncias com o ambiente,
			// encontrando novas posi��es seguras conforme a posi��o atual.
			
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
			
			if(brisas[p.row][p.col]=='B'){
				// Inserir �reas perigosas
				
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
					
						// Adicionando �reas seguras.
						
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
		
		if(brisas[p.row][p.col]=='B'){
			// Dentro desse condicional, � criada a imagina��o dos po�os.
			
			imaginacaoPocos[p.row][p.col]='B';
			
			if(p.row>0 && imaginacaoPocos[p.row-1][p.col]=='0'){
				imaginacaoPocos[p.row-1][p.col] = 'P';
			}
			
			if(p.row<3 && imaginacaoPocos[p.row+1][p.col]=='0'){
				imaginacaoPocos[p.row+1][p.col] = 'P';
			}
			
			if(p.col>0 && imaginacaoPocos[p.row][p.col-1]=='0'){
				imaginacaoPocos[p.row][p.col-1] = 'P';
			}
			
			if(p.col<3 && imaginacaoPocos[p.row][p.col+1]=='0'){
				imaginacaoPocos[p.row][p.col+1] = 'P';
			}
			
		}
		
		// Estrutura de condicional composta voltada para construir a imagina��o do agente sobre
		// os po�os.
		if(ambiente2[p.row][p.col]=='-'){
			imaginacaoPocos[p.row][p.col] = '-';
		}
		
		if(ambiente2[p.row][p.col]=='F' && brisas[p.row][p.col]!='B'){
			imaginacaoPocos[p.row][p.col] = '-';
		}
		else if(ambiente2[p.row][p.col]=='F' && brisas[p.row][p.col]=='B'){
			imaginacaoPocos[p.row][p.col] = 'B';
		}
		
		for(int i=0; i<4; i++){
			
			for(int j=0; j<4; j++){
				
				if(imaginacaoPocos[i][j]=='-'){
					
					if(j<3 && imaginacaoPocos[i][j+1]=='0'){
						// O caractere '0' representa d�vida
						imaginacaoPocos[i][j+1] = 'N';
						// N de nada na posi��o.
					}
					
					if(j>0 && imaginacaoPocos[i][j-1]=='0'){
						imaginacaoPocos[i][j-1] = 'N';
						// N de nada na posi��o.
					}
					
					if(i>0 && imaginacaoPocos[i-1][j]=='0'){
						imaginacaoPocos[i-1][j] = 'N';
						// N de nada na posi��o.
					}
					
					if(i<3 && imaginacaoPocos[i+1][j]=='0'){
						imaginacaoPocos[i+1][j] = 'N';
						// N de nada na posi��o.
					}	
				}	
			}
		}
		
		if(fedor[p.row][p.col]=='F'){
			
			// Neste condicional � montada a imagina��o do agente sobre o Wumpus
			
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
		
		// Estrutura de condicional composta voltada para construir a imagina��o do agente sobre
		// a localiza��o do Wumpus.
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
						// O caractere '0' representa posi��o em d�vida.
						imaginacaoWumpus[i][j+1] = 'N';
						// J� o caractere 'N' representa que na posi��o n�o tem nada.
					}
					
					if(j>0 && imaginacaoWumpus[i][j-1]=='0'){
						imaginacaoWumpus[i][j-1] = 'N';
						// J� o caractere 'N' representa que na posi��o n�o tem nada.
					}
					
					if(i>0 && imaginacaoWumpus[i-1][j]=='0'){
						imaginacaoWumpus[i-1][j] = 'N';
						// J� o caractere 'N' representa que na posi��o n�o tem nada.
					}
					
					if(i<3 && imaginacaoWumpus[i+1][j]=='0'){
						imaginacaoWumpus[i+1][j] = 'N';
						// J� o caractere 'N' representa que na posi��o n�o tem nada.
					}	
				}	
			}
		}
		
		if(brilho[p.row][p.col]=='R'){
			
			// Neste condicional � montada a imagina��o do agente sobre o Ouro
			
			imaginacaoOuro[p.row][p.col]='R';
			
			if(p.row>0 && imaginacaoOuro[p.row-1][p.col]=='0'){
				imaginacaoOuro[p.row-1][p.col] = 'W';
			}
			
			if(p.row<3 && imaginacaoOuro[p.row+1][p.col]=='0'){
				imaginacaoOuro[p.row+1][p.col] = 'W';
			}
			
			if(p.col>0 && imaginacaoOuro[p.row][p.col-1]=='0'){
				imaginacaoOuro[p.row][p.col-1] = 'W';
			}
			
			if(p.col<3 && imaginacaoOuro[p.row][p.col+1]=='0'){
				imaginacaoOuro[p.row][p.col+1] = 'W';
			}
			
		}
		
		// Estrutura de condicional composta voltada para construir a imagina��o do agente sobre
		// a localiza��o do Ouro.
		if(ambiente2[p.row][p.col]=='-'){
			imaginacaoOuro[p.row][p.col] = '-';
		}
		
		if(ambiente2[p.row][p.col]=='B' && brilho[p.row][p.col]!='R'){
			imaginacaoOuro[p.row][p.col] = '-';
		}
		else if(ambiente2[p.row][p.col]=='F' && brilho[p.row][p.col]!='R'){
			imaginacaoOuro[p.row][p.col] = '-';
		}
		
		for(int i=0; i<4; i++){
			for(int j=0; j<4; j++){
				if(imaginacaoOuro[i][j]=='-'){
					
					if(j<3 && imaginacaoOuro[i][j+1]=='0'){
						// O caractere '0' representa posi��o em d�vida.
						imaginacaoOuro[i][j+1] = 'N';
						// J� o caractere 'N' representa que na posi��o n�o tem nada.
					}
					
					if(j>0 && imaginacaoOuro[i][j-1]=='0'){
						imaginacaoOuro[i][j-1] = 'N';
						// J� o caractere 'N' representa que na posi��o n�o tem nada.
					}
					
					if(i>0 && imaginacaoOuro[i-1][j]=='0'){
						imaginacaoOuro[i-1][j] = 'N';
						// J� o caractere 'N' representa que na posi��o n�o tem nada.
					}
					
					if(i<3 && imaginacaoOuro[i+1][j]=='0'){
						imaginacaoOuro[i+1][j] = 'N';
						// J� o caractere 'N' representa que na posi��o n�o tem nada.
					}	
				}	
			}
		}
		
		if(ambiente2[p.row][p.col]=='R'){
			
			// Se for encontrado um brilho, ent�o se pode encontrar e indicar �reas seguras
			// conforme a posi��o atual do agente.
			
			if(p.row>0){
				
				Position tal = new Position();
				
				tal.row=p.row-1;
				tal.col=p.col;
				
				if(!compare(areas_seguras, tal)){
					areas_seguras.add(tal);
				}
				
			}
			if(p.row<3){
				
				Position tal = new Position();
				
				tal.row=p.row+1;
				tal.col=p.col;
				
				if(!compare(areas_seguras, tal)){
					areas_seguras.add(tal);
				}
				
			}
			if(p.col>0){
	
				Position tal = new Position();
				
				tal.row=p.row;
				tal.col=p.col-1;
				
				if(!compare(areas_seguras, tal)){
					areas_seguras.add(tal);
				}
	
			}
			
			if(p.col<3){
				
				Position tal = new Position();
				
				tal.row=p.row;
				tal.col=p.col+1;
				
				if(!compare(areas_seguras, tal)){
					areas_seguras.add(tal);
				}
				
			}
			
		}

		if(!compare(areas_visitadas, p))
			areas_visitadas.add(p);
		// Adiciona-se a posi��o atual nas �reas visitadas.
		
		// Neste la�o, remove-se as �reas que est�o no array areas_perigosas que
		// j� foram visitadas.
		for(int i=0; i<areas_perigosas.size(); i++){
				
				Position temp = areas_perigosas.get(i);
				
				for(int j=0; j<areas_visitadas.size(); j++){
					
					Position temp2 = areas_visitadas.get(j);
					
					if(temp.row==temp2.row && temp.col==temp2.col){
						areas_perigosas.remove(temp);
					}
					
				}
		}
		
		// A partir de agora, vamose movimentar o agente.
		
		int countWumpus=0;
		int count_fedor=0;
		
		ArrayList<Position> pontos = new ArrayList<Position>();
		
		int xWumpus=-1;
		int yWumpus=-1;
		
		for(int i=0; i<4; i++){
			
			for(int j=0; j<4; j++){
				
				if(imaginacaoWumpus[i][j]=='F'){
					count_fedor++;
					Position temp = new Position();
					temp.row=i;
					temp.col=j;
					pontos.add(temp);
				}
				
			}
			
		}
		
		if(count_fedor==2){
			
			// Com identifica��o de duas posi��es com fedor, o agente j� pode descobrir a posi��o do Wumpus.
			
			Position ponto1 = pontos.get(0);
			Position ponto2 = pontos.get(1);
			
			if(imaginacaoWumpus[ponto2.row][ponto1.col]=='W' && (imaginacaoWumpus[ponto1.row][ponto2.col]!='0' && imaginacaoWumpus[ponto1.row][ponto2.col]!='W')){
				
				for(int i=0; i<4; i++){
					
					for(int j=0; j<4; j++){
						
						if(imaginacaoWumpus[i][j]=='W' && (i!=ponto2.row || j!=ponto1.col)){
							imaginacaoWumpus[i][j]='N';
						}
						
					}
					
				}
				
			}
			else if(imaginacaoWumpus[ponto1.row][ponto2.col]=='W' && (imaginacaoWumpus[ponto2.row][ponto1.col]!='0' && imaginacaoWumpus[ponto2.row][ponto1.col]!='W')){
				
				for(int i=0; i<4; i++){
					
					for(int j=0; j<4; j++){
						
						if(imaginacaoWumpus[i][j]=='W' && (i!=ponto1.row || j!=ponto2.col)){
							imaginacaoWumpus[i][j]='N';
						}
						
					}
					
				}
				
			}
			
			// Ap�s essa fun��o, apenas um s�mbolo 'W' estar� presente na matriz imaginacaoWumpus.
			
		}
		
		int count_ouro = 0;
		
		int xOuro = -1;
		int yOuro = -1;
		
		for(int i=0; i<4; i++){
			
			for(int j=0; j<4; j++){
				
				if(imaginacaoWumpus[i][j]=='W'){
					countWumpus++;
					xWumpus =  i;
					yWumpus = j;
					// Pega a posi��o do Wumpus
				}
				
			}
			
		}
		
		for(int i=0; i<4; i++){
			
			for(int j=0; j<4; j++){
				
				if(imaginacaoOuro[i][j]=='W'){
					count_ouro++;
					xOuro =  i;
					yOuro = j;
					// Pega a posi��o do Wumpus
				}
				
			}
			
		}
		
		if(count_ouro==1){
			
			System.out.println("Eu sei onde est� o Ouro. Vou peg�-lo!");
			
			ambiente[p.row][p.col] = ambiente2[p.row][p.col];
			
			ambiente[xOuro][yOuro] = 'G';
			
			xInicial = p.row;
			yInicial = p.col;
			xFinal = xOuro;
			yFinal = yOuro;
			
			return ambiente;	
		}
		
		if(countWumpus==1){
			
			System.out.println("Eu sei onde est� o Wumpus. Vou mat�-lo!");
			
			System.out.println("Grito do Wumpus.");
			
			ambiente[p.row][p.col] = ambiente2[p.row][p.col];
			
			ambiente[xWumpus][yWumpus] = 'G';
			// Guerreiro fica onde o Wumpus estava anteriormente.
			
			grito=true;
			
			Position Wumpus = new Position();
			Wumpus.row = xWumpus;
			Wumpus.col = yWumpus;
			
			areas_seguras.add(Wumpus);
			
			// A posi��o do Wumpus morto ganha o s�mbolo '-', assim como as posi��es adjacentes.
			
			if(brisas[xWumpus][yWumpus]=='B'){
				ambiente2[xWumpus][yWumpus] = 'B';
			}
			else
				ambiente2[xWumpus][yWumpus] = '-';
			
			for(int i=0; i<4; i++){
				
				for(int j=0; j<4; j++){
					
					fedor[i][j]='-';
					
					if(ambiente[i][j]=='F' || ambiente2[i][j]=='F'){
						
						Position novo = new Position();
						
						novo.row = i;
						novo.col = j;
						
						areas_seguras.add(novo);
						
						if(brisas[i][j]=='B'){
							ambiente[i][j] = 'B';
							ambiente2[i][j] = 'B';
						}
						else{
							ambiente[i][j] = '-';
							ambiente2[i][j] = '-';
						}
						
					}
					
				}
				
			}
			
			for(int i=0; i<4; i++){
				
				for(int j=0; j<4; j++){
					
					imaginacaoWumpus[i][j]='0';
					// O vetor imaginacaoWumpus � zerado, pois n�o ser� mais necess�rio.
					
				}
				
			}
			
			xInicial = p.row;
			yInicial = p.col;
			xFinal = xWumpus;
			yFinal = yWumpus;
			
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
		
		if(brisas[p.row][p.col]=='B'){
			
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
		
		// Neste for removemos �reas seguras que podem estar nas �reas consideradas perigosas.
		
		for(int i=0; i<areas_seguras.size(); i++){
			
			Position temp = new Position();
			
			temp.row = areas_seguras.get(i).row;
			temp.col = areas_seguras.get(i).col;
				
			for(int j=0; j<areas_perigosas.size(); j++){
				if(areas_perigosas.get(j).row==temp.row && areas_perigosas.get(j).col==temp.col){
					areas_perigosas.remove(j);
				}
			}
			
		}
		
		// Impress�o feita ao usu�rio.
		
		System.out.println("Medida de Desempenho: "+status);
		
		System.out.print("�reas j� visitadas: ");
		for(int i=0; i<areas_visitadas.size(); i++){
			System.out.print("Posi��o: "+areas_visitadas.get(i).row+" - "+areas_visitadas.get(i).col+" / ");
		}
		System.out.println(" ");
		
		System.out.print("�reas consideradas seguras: ");
		for(int i=0; i<areas_seguras.size(); i++){
			System.out.print("Posi��o: "+areas_seguras.get(i).row+" - "+areas_seguras.get(i).col+" / ");
		}
		System.out.println(" ");
		
		System.out.print("�reas consideradas perigosas: ");
		for(int i=0; i<areas_perigosas.size(); i++){
			System.out.print("Posi��o: "+areas_perigosas.get(i).row+" - "+areas_perigosas.get(i).col+" / ");
		}
		System.out.println(" ");
		
		// Vamos movimentar a posi��o do agente.
		
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
				
				// Depois de encontrada as �reas poss�veis de movimenta��o, elas s�o mostradas para o usu�rio.
				
				System.out.print("�reas poss�veis de movimenta��o: ");
				
				for(int i=0; i<areas_possiveis.size(); i++){
					System.out.print(" Posi��o: "+areas_possiveis.get(i).row+" - "+areas_possiveis.get(i).col+" / ");
				}
				
				System.out.println(" ");
				System.out.println(" ");
				
				// Agora basta encontrar a pr�xima �rea para movimenta��o
				for(int i=0; i<areas_possiveis.size(); i++){
					
					Position current = areas_possiveis.get(i);
					
					if(!compare(areas_visitadas, current) && compare(areas_seguras, current) && !compare(areas_perigosas, current)){
						
						// Se a posi��o poss�vel n�o � uma �rea visitada, e � uma �rea segura, ou seja, n�o considerada perigosa, ent�o o agente se locomove para
						// essa posi��o poss�vel.
						
						System.out.println("Estou me movimentando para uma �rea que sei que � segura.");
						
						ambiente[p.row][p.col] = ambiente2[p.row][p.col];
						ambiente[current.row][current.col] = 'G';
						
						xInicial = p.row;
						yInicial = p.col;
						xFinal = current.row;
						yFinal = current.col;
						
						return ambiente;
						// Neste momento, esta fun��o termina.
						
					}
				}
					
				System.out.println("Neste momento n�o sei para onde ir!");
				
				// Guerreiro n�o sabe para onde ir
				// Ent�o ele escolhe uma das �reas poss�veis de forma aleat�ria.
					
				if(!is_different(areas_seguras, areas_possiveis)){
					
					// Se ass �reas seguras s�o iguais �s �reas poss�veis, ou seja,
					// n�o se tem mais uma �rea considerada segura que n�o foi visitada,
					// ent�o s� resta o agente escolher ao acaso, uma nova posi��o.
					
					System.out.println("Vou escolher ao acaso. Ent�o me deseje sorte.");
					
					for(int i=0; i<areas_visitadas.size(); i++){
						
						Position temp = new Position();
						
						temp.row = areas_visitadas.get(i).row;
						temp.col = areas_visitadas.get(i).col;
						
						for(int j=0; j<areas_possiveis.size(); j++){
							
							if(areas_possiveis.get(j).row==temp.row && areas_possiveis.get(j).col==temp.col){
								areas_possiveis.remove(j);
								break;
							}
							
						}
						
					}
					
					if(areas_possiveis.size()==0){
						int pos = random(areas_perigosas.size());
						areas_possiveis.add(areas_perigosas.get(pos));
					}
							
					int pos = random(areas_possiveis.size());
					// Posi��o aleat�ria.
							
					Position nova = areas_possiveis.get(pos);
					// Nova posi��o escolhida para o usu�rio.
					
					if(nova.row==p.row && nova.col==p.col){
						
						pos = random(areas_possiveis.size());
						
						nova = areas_possiveis.get(pos);
						
						while(nova.row==p.row && nova.col==p.col){
							pos = random(areas_possiveis.size());
							nova = areas_possiveis.get(pos);
						}
						
					}
							
					ambiente[p.row][p.col] = ambiente2[p.row][p.col];
					ambiente[nova.row][nova.col] = 'G';
					
					xInicial = p.row;
					yInicial = p.col;
					xFinal = nova.row;
					yFinal = nova.col;
					
				}
				else{
						
						// Encontrar a posi��o final segura
						// O guerreiro ainda tem uma posi��o segura em que ainda n�o foi visitada.
					
						System.out.println("Mas existe ainda uma posi��o segura que conhe�o.");
					
						Position term = new Position();
						
						for(int i=0; i<areas_possiveis.size(); i++){
							
							Position temp = new Position();
							
							temp.row = areas_possiveis.get(i).row;
							temp.col = areas_possiveis.get(i).col;
							
							for(int j=0; j<areas_visitadas.size(); j++){
								
								if(areas_visitadas.get(j).row==temp.row && areas_visitadas.get(j).col==temp.col){
									areas_possiveis.remove(i);
								}
								
							}
							
						}
						
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
						
						// Viajar at� a posi��o final segura
						ambiente[p.row][p.col] = ambiente2[p.row][p.col];
						ambiente[term.row][term.col] = 'G';
						
						xInicial = p.row;
						yInicial = p.col;
						xFinal = term.row;
						yFinal = term.col;
						
				}
			
			return ambiente;
		
	}
	
	// Esta fun��o fica respons�vel por atualizar o status (medida de 
	// desempenho) do jogo.
	// Al�m de verificar se o guerreiro encontrou o ouro (teste de objetivo).

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
		
		if(ambiente[row][col]=='G' && ambiente2[row][col]=='W' && grito==false){
			System.out.println("Wumpus grito o guerreiro!");
			status = status-1000;
			return true;
		}
		if(ambiente[row][col]=='G' && ambiente2[row][col]=='P'){
			System.out.println("Guerreiro caiu no po�o!");
			status = status-1000;
			return true;
		}
		if(ambiente[row][col]=='G' && ambiente2[row][col]=='O'){
			System.out.println("Guerreiro encontrou o ouro!");
			status = status+1000;
			return true;
		}
		
		if(grito){
			status = status - 10;
		}
		
		return false;
	}
	
	public static void main(String args[]){
		
		char ambiente[][] = new char[4][4];
		
		// Iniciar os ambientes: brisas, fedor, imaginacaoWumpus e imaginacaoPocos.
		for(int i=0; i<4; i++){
			for(int j=0; j<4; j++){
				brisas[i][j] = '-';
			}
		}
		
		for(int i=0; i<4; i++){
			for(int j=0; j<4; j++){
				brilho[i][j] = '-';
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
		
		for(int i=0; i<4; i++){
			for(int j=0; j<4; j++){
				imaginacaoOuro[i][j] = '0';
			}
		}
		
		for(int i=0; i<4; i++){
			for(int j=0; j<4; j++){
				imaginacaoPocos[i][j] = '0';
			}
		}
		
		// Iniciando o ambiente Wumpus�s World
		init(ambiente);
		
		// Nestes for's um ambiente est�tico � criado para a aplica��o
		// ambiente background
		// O ambiente2 � uma c�pia do ambiente no estado inicial.
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
		
		show(ambiente);
		// Ambiente completamente montado.
		
		System.out.println(" ");
		
		while(true){
			
			// Aqui o movimento do agente 'Guerreiro' � realizado
			ambiente = start(ambiente);
							
			if(ambiente[3][0]!='G'){
				
				if(fedor[3][0]=='F'){
					ambiente[3][0]  = 'F';
				}
				else if(brisas[3][0]=='B'){
					ambiente[3][0]  = 'B';
				}
				else if(brilho[3][0]=='R'){
					ambiente[3][0] = 'R';
				}
				
			}
			
			int valor1;
			int valor2;
			
			if(yInicial>yFinal)
				valor1 = yFinal-yInicial;
			else
				valor1 = yInicial-yFinal;
			if(xInicial>xFinal)
				valor2 = xFinal-xInicial;
			else
				valor2 = xInicial-xFinal;
			
			int valorComp = valor2+valor1;
			
			status=status+valorComp;
			
			// Apresenta��o do ambiente
			System.out.println(" ");
			System.out.println("--------------------");
			show(ambiente);
			System.out.println("--------------------");
			
			//show(brisas);
			//show(fedor);
			//show(brilho);
			//show(imaginacaoWumpus);
			//show(imaginacaoPocos);
			//show(imaginacaoOuro);
			
			ent.nextLine();
			
			// Nesta fun��o, verifica-se se o agente ganhou (encontrou o ouro) ou perdeu (caiu no po�o ou foi morto pelo Wumpus)
			if(verify(ambiente)){
				System.out.println(" ");
				System.out.println("Medida de Desempenho: "+status);
				System.out.println(" ");
				break;
			}
			
		}
		
	}

}