package edu.senai.integrador.main;

import java.util.List;

import edu.senai.integrador.beans.Funcionario;
import edu.senai.integrador.dao.FuncionarioDAO;
import edu.senai.integrador.ferramentas.LeituraTerminal;

public class Menus {
	public static List<String> argumentos = UsaSistema.argumentos;
	private static ItemsMenu text = new ItemsMenu();
	private static LeituraTerminal leitor = new LeituraTerminal();
	
	public static boolean menuPrincipal(int i) {
		while (true) {
			try {
				UsaSistema.limpaConsole();
				System.out.println(
						i == 0 ? "1. Funcionários\n" : ""
								+ (i < 2 ? "2. Alunos\n" : "")
								+ "3. Modalidades\n" 
								+ "4. Turmas\n" 
								+ (i == 0 ? "5. Permissões\n" : "")
								+ (i == 0 ? "6. Configurações\n" : "\n")
								+ "7. Sair"
								+ "Digite a opção desejada: ");
				leitor.leInt();
				switch (leitor.leInt()) {
				default:
					UsaSistema.printaWarning(text.OPCAO_INVALIDA);
				case 1:
					menuFuncionarios();
				case 2:
					menuAlunos();
				case 3:
					
				case 4:
					
				case 5:
					
				case 6:
					
				case 7:
					break;						
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	public static boolean menuFuncionarios() {
		while (true) {
			UsaSistema.limpaConsole();
			try {
				System.out.println("1. Cadastra Funcionários\n"
								 + "2.  Altera  Funcionários\n"
								 + "3.  Exclui  Funcionários\n"
								 + "4.	Voltar");
				switch (leitor.leInt()) {
				default:
					UsaSistema.printaWarning(text.BD_DRIVER);
				case 1:
					cadastraFuncionarios();
				case 2:
					
				case 3:
					
				case 4:
					break;
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	public static boolean cadastraFuncionarios() {
		Funcionario funcionario = new Funcionario();
		FuncionarioDAO dao = new FuncionarioDAO();
		
		while (true) {
			UsaSistema.limpaConsole();
			try {
				System.out.println(text.USUARIO);
				funcionario.setNome(leitor.leNome());

				System.out.println(text.SENHA);
				funcionario.setCPF(leitor.leCpf());

				System.out.println("0- Fundamental Incompleto\n1- Fundamental Completo" 
								 + "2- Médio Imcompleto\n3- Médio Completo\n"
								 + "4- Superior Incompleto\n5- Superior Completo\n\n"
								 + "Insira a escolaridade_______________________ ");
				funcionario.setEscolaridade(leitor.leEscolaridade());
				
				dao.insere(funcionario);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	
	public static boolean menuAlunos() {
		while (true) {
			UsaSistema.limpaConsole();
			try {
				
				
				
				switch(leitor.leInt()) {
				default:
					UsaSistema.printaWarning(text.NUM_INVALIDO);
				case 1:
					
				case 2:
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	
}
