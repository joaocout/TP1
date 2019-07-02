package locadora;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
		
	//pula linhas e seta o cursor para a primeira linha, primeira coluna
	public static void ClearScreen() {
		for (int i = 0; i < 30; ++i) System.out.println();
	}

	public static void main(String[] args) throws SQLException{
		Scanner reader = new Scanner(System.in);
		Locadora loc = new Locadora(reader);
		
		// menu
		int op = 0;
		Scanner in = new Scanner(System.in);
		while(op!=9) {
			ClearScreen();
			System.out.println("-------Menu-------\n");
			System.out.println("[1] - Cadastrar");
			System.out.println("[2] - Consultar");
			System.out.println("[3] - Alugar");
			System.out.println("[4] - Devolver");
			System.out.println("[9] - Sair");
			System.out.println("Escolha uma opção: ");
			
			if(in.hasNextInt())
				op = in.nextInt();
			else
				in.nextLine();
			
			if(op == 1) {
				//ClearScreen();
				System.out.println("-------Cadastro-------\n");
				System.out.println("[1] - Cadastrar plataforma");
				System.out.println("[2] - Cadastrar jogo");
				System.out.println("[3] - Cadastrar cliente");
				System.out.println("Escolha um opção: ");
				
				op = in.nextInt();
				if(op == 1) {
					//ClearScreen();
					loc.cadastrar_plat();
					System.out.println("\nPressione ENTER para retornar");
					in.nextLine();
					in.nextLine();
				}
				else if(op == 2) {
					ClearScreen();
					loc.cadastrar_jogo();
					System.out.println("\nPressione ENTER para retornar");
					in.nextLine();
					in.nextLine();
				}
				else if(op == 3) {
					ClearScreen();
					loc.cadastrar_cliente();
					System.out.println("\nPressione ENTER para retornar");
					in.nextLine();
					in.nextLine();
				}
			}
			else if(op == 2) {
				//ClearScreen();
				System.out.println("-------Consulta-------\n");
				System.out.println("[1] - Consultar plataforma");
				System.out.println("[2] - Consultar jogo");
				System.out.println("[3] - Consultar cliente");
				System.out.println("Escolha um opção: ");
				
				op = in.nextInt();
				if(op == 1) {
					//ClearScreen();
					loc.consulta_plat();
					System.out.println("\nPressione ENTER para retornar");
					in.nextLine();
					in.nextLine();
				}
				if(op == 2) {
					//ClearScreen();
					loc.consulta_jogo();
					System.out.println("\nPressione ENTER para retornar");
					in.nextLine();
					in.nextLine();
				}
				if(op == 3) {
					//ClearScreen();
					loc.consulta_cliente();
					System.out.println("\nPressione ENTER para retornar");
					in.nextLine();
					in.nextLine();
				}
			}
			else if(op==3){
				//ClearScreen();
				loc.alugar_jogo();
				System.out.println("\nPressione ENTER para retornar");
				in.nextLine();
				in.nextLine();
			}
			else if(op==4){
				//ClearScreen();
				loc.devolver_jogo();
				System.out.println("\nPressione ENTER para retornar");
				in.nextLine();
				in.nextLine();
			}
			else if (op==9) {
				//ClearScreen();
				in.close();
			}
		}
		reader.close();
		System.exit(0);
	}
}
