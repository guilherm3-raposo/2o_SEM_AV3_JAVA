package edu.senai.integrador.a.view;

import static org.fusesource.jansi.Ansi.ansi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.fusesource.jansi.Ansi.Color;
import org.fusesource.jansi.AnsiConsole;

import edu.senai.integrador.bancodedados.conexao.Conexao;
import edu.senai.integrador.bancodedados.conexao.ConexaoException;
import edu.senai.integrador.bancodedados.conexao.EConexaoErro;
import edu.senai.integrador.beans.cadastro.EUsuarioErro;
import edu.senai.integrador.beans.cadastro.Usuario;
import edu.senai.integrador.beans.cadastro.UsuarioException;
import edu.senai.integrador.dao.DAOException;
import edu.senai.integrador.dao.UsuarioDAO;
import edu.senai.integrador.ferramentas.Configuracoes;
import edu.senai.integrador.ferramentas.LeitorArquivo;
import edu.senai.integrador.logs.GeraLog;
import edu.senai.integrador.seguranca.Seguranca;
import edu.senai.integrador.seguranca.SegurancaException;
import edu.senai.integrador.z.populadores.Instalador;
import edu.senai.integrador.z.populadores.Populador;

public abstract class Executavel {
	public static ItemsMenu text = new ItemsMenu();
	public static List<String> argumentos = new ArrayList<String>();
	public static Configuracoes config = new Configuracoes();
	public static LeituraTerminal leitor = new LeituraTerminal();
	public static UsuarioDAO loginDao;
	public static Usuario login = new Usuario();
	public static boolean console;
	public static int permissao;
	
	public static void main(String[] args) throws InterruptedException {
		while (true) {
			try {LeitorArquivo.criaArquivos();break;
			} catch (Exception e) {continue;}
		}
		argumentos = leParametros(args);
		console = argumentos.contains("-w");
		AnsiConsole.systemInstall();
		printaVerdeln(cabecalho());
		
		setUp();
		try {
			Conexao.abreConexao();
			Conexao.fechaConexao();
		} catch (Exception e) {
			loginDao = new UsuarioDAO();
			printaWarning(EConexaoErro.ABRE_CONEXAO.getMensagem().toString(), e);
			cadastraBD();
		}
		try {
			if (config.carrega(false).get("admin.usr").toString().isEmpty()) {
				MenuUsuario.cadastraAdmin();
			}
		} catch (Exception e) {//try vai gerar null pointer exception na first run
		}
		permissao = validaUsuario();
		if(permissao == 0) {
			while (true) {
				try {
					printaVerde("Deseja popular a base de dados? (S/N) ");
					lePreto();
					if (leitor.leBoolean().matches("true")) {
						printaVerde("Quantos registros devo criar? ");
						lePreto();
						Populador.populaBase(leitor.leInt());
						printaPreto("Populando base de dados, aguarde...");
					} else {
						break;
					}
				} catch (Exception e) {
				}
				printaAzul("Base de dados populada com sucesso");
				break;
			}
		}
		menuPrincipal(permissao);
	}

	public static boolean setUp() throws InterruptedException {
		if (config.carrega(true).getProperty("seguranca.isRegistered").matches("false"))
			if(menuCadastroSistema())
				config.carrega(true).setProperty("seguranca.isRegistered", "true");

		if (config.carrega(false).getProperty("base.nome").isEmpty()) {
			while (true) {
				if(cadastraBD())
					break;
				else {
					printaVermelho(EConexaoErro.ABRE_CONEXAO.toString() + "\n");
					continue;
				}
			}
			MenuUsuario.cadastraAdmin();
		}
		return true;
	}
	
	public static boolean menuPrincipal(int i) throws InterruptedException {
		MenuFuncionario menusFuncionarios = new MenuFuncionario();
		MenuAluno menuAluno = new MenuAluno();
		MenuModalidade menuModalidade = new MenuModalidade();
		MenuTurma menuTurma = new MenuTurma();
		MenuExercicio menuExercicio = new MenuExercicio();
		while (true) {
			try {
				limpaConsole();
				printaVerde(text.MENU_PRINCIPAL +
						(i == 0 ? "1. Funcionários\n" : "")
					  + (i == 0 ? "2. " : i == 1 ? "1. ":""   ) + (i<2? "Alunos\n":"")
					  + (i == 0	? "3. " : i == 1 ? "2. ":"1. ") +       "Modalidades\n"
					  + (i == 0	? "4. " : i == 1 ? "3. ":"2. ") +       "Turmas\n"
					  + (i == 0 ? "5. " : i == 1 ? "4. ":""   ) + (i<2? "Exercícios\n":"")
					  +			  "9. Sair\n"
					  + "Digite a opção desejada: ");
				lePreto();
				switch (leitor.leInt()) {
				default:
					printaWarning(text.OPCAO_INVALIDA.toString(), new LeituraException(ELeituraErro.VALOR_INVALIDO));
					continue;
				case 1:
					if (i == 0) menusFuncionarios.principal();
			   else if (i == 1) menuAluno.principal();
			   else	if (i == 2) menuModalidade.principal();
					continue;
				case 2:
					if (i == 0) menuAluno.principal();
			   else if (i == 1) menuModalidade.principal();
			   else if (i == 2) menuTurma.principal();
					continue;
				case 3:
					if (i == 0) menuModalidade.principal();
			   else if (i == 1) menuTurma.principal();
			   else if (i == 2) 	;
					continue;
				case 4:
					if (i == 0) menuTurma.principal();
			   else if (i == 1) menuExercicio.principal();
			   else if (i == 2) ;
					continue;
				case 5:
					if(i == 0) menuExercicio.principal();
					continue;
				case 9:
					break;						
				}
				limpaConsole();
				printaAzulln("Até a próxima!!!");
				TimeUnit.SECONDS.sleep(2);;
				return false;
			} catch (Exception e) {
				printaWarning(ELeituraErro.VALOR_INVALIDO.toString(), e);
			}
		}
	}

	public static boolean menuCadastroSistema() throws InterruptedException {
		while (true) {
			try {
				printaVerdeln(text.CADAST_SIST);
				printaVerde(text.SISTEMA_SERIAL);
				lePreto();
				Seguranca.confereSerial(leitor.leString(12));
				limpaConsole();
				printaVerde(text.SISTEMA_REGISTRO);
				lePreto();
				break;
			} catch (SegurancaException | LeituraException e) {
				printaWarning(e.getMensagem().toString(), e);
				continue;
			} catch (IOException e) {
				printaWarning(ELeituraErro.VALOR_INVALIDO.toString(), e);
				continue;
			} 
		}
		return true;
	}

	public static boolean cadastraBD() throws InterruptedException {
		Properties prop = config.carrega(false);
		while (true) {
			try {
				limpaConsole();
				printaVerde(text.SISTEMA_NOME);
				lePreto();
				prop.setProperty("sis.a.nome", leitor.leUsuario(6,30));
				break;
			} catch (UsuarioException e) {
				printaWarning(e.getMensagem(), e);
				continue;
			} catch (IOException e) {
				printaWarning(ELeituraErro.VALOR_INVALIDO.toString(), e);
				continue;
			}
		}
		while (true) {
			try {
				limpaConsole();
				printaVerde(text.BD_IP);
				lePreto();
				prop.setProperty("base.ip", leitor.leIP());
				break;
			} catch (LeituraException e) {
				printaWarning(e.getMensagem().toString(), e);
				continue;
			} catch (IOException e) {
				printaWarning(ELeituraErro.VALOR_INVALIDO.toString(), e);
				continue;
			}
		}
		while (true) {
			try {
				limpaConsole();
				printaVerde(text.BD_USR);
				lePreto();
				prop.setProperty("base.usr", leitor.leUsuario(4,30));
				break;
			} catch (UsuarioException e) {
				printaWarning(e.getMensagem().toString(), e);
				continue;
			} catch (IOException e) {
				printaWarning(ELeituraErro.VALOR_INVALIDO.toString(), e);
				continue;
			}
		}
		while (true) {
			try {
				limpaConsole();
				printaVerde(text.BD_PWD);
				lePreto();
				prop.setProperty("base.pwd", leitor.leSenha(5));
				break;
			} catch (UsuarioException e) {
				printaWarning(e.getMensagem().toString(), e);
				continue;
			} catch (IOException e) {
				printaWarning(ELeituraErro.VALOR_INVALIDO.toString(), e);
				continue;
			}
		}
		while (true) {
			try {
				limpaConsole();
				printaVerde(text.BD_SSL);
				lePreto();
				prop.setProperty("base.ssl", leitor.leBoolean());
				break;
			} catch (LeituraException e) {
				printaWarning(e.getMensagem().toString(), e);
				continue;
			} catch (IOException e) {
				printaWarning(ELeituraErro.VALOR_INVALIDO.toString(), e);
				continue;
			}
		}
		
		try {
			config.salva(prop, false);
			config.carrega(false);
			Conexao.abreConexao();
		} catch (ConexaoException e) {
			printaWarning(EConexaoErro.ABRE_CONEXAO.toString(), e);
			return false;
		}
		while (true) {
			try {
				limpaConsole();
				printaVerde(text.BD_NOME);
				lePreto();
				prop.setProperty("base.nome", leitor.leUsuario(4, 30));
				printaAzulln("\n\nInstalando a base de dados, aguarde!");
				Instalador.instalaBanco(prop.getProperty("base.nome"));
				config.salva(prop, false);
				break;
			} catch (UsuarioException e) {
				printaWarning(e.getMensagem().toString(), e);
				continue;
			} catch (Exception e) {
				printaWarning(ELeituraErro.VALOR_INVALIDO.toString(), e);
				continue;
			}
		}
		return true;
	}

	public static int validaUsuario() throws InterruptedException {
		UsuarioDAO loginDAO = new UsuarioDAO();
		while (true) {
			try {
				limpaConsole();
				printaVerde(text.MENU_LOGIN + text.LOGIN_USR);
				lePreto();
				String usuario = leitor.leUsuario(6, 30);
				printaVerde(text.LOGIN_SENHA);
				lePreto();
				String senha = leitor.leSenha(5);
				login = loginDAO.consulta(usuario);
				if(login == null) throw new UsuarioException(EUsuarioErro.LOGIN_INVALIDO);
				if (login.getSenha().matches(senha)) {
					limpaConsole();
					printaVerdeln(text.LOGIN_SUCESSO);
					return login.getPermissao();
				} else throw new UsuarioException(EUsuarioErro.LOGIN_INVALIDO);
			} catch (ConexaoException | DAOException | UsuarioException e) {
				printaWarning(e.getMensagem().toString(), e);
				continue;
			} catch (Exception e) {
				printaWarning(ELeituraErro.VALOR_INVALIDO.toString(), e);
				continue;
			}
		}
	}
//	_______ METODOS ESTETICOS ______________________________________________________

	public static List<String> leParametros(String[] args) {
		List<String> param = new ArrayList<String>();
		for (int i = 0; i < args.length; i++) {
			param.add(args[i]);
		}
		return param;
	}

	public static String cabecalho() {
		String nome = config.carrega(false).getProperty("sistema.nome");
		String branco = "";
		int space = 32 - nome.length();
		for (int i = 0; i < space/2; i++) {branco+=" ";}
		
		nome = branco + nome + branco + (space%2 == 0 ? "" : " ");
		
		final String cabecalho = "==================================\n";
		final String separador = "|                                |\n";
		final String titulo = "|" + nome + "|\n";
		return cabecalho + separador + titulo + separador + cabecalho;
	}

	public static void lePreto() 
		{System.out.print(trocaCor(Color.BLACK, ""));}
	public static void printaVermelholn(String texto) 
		{System.out.println(trocaCor(Color.RED, texto));}
	public static void printaVermelho(String texto) 
		{System.out.print(trocaCor(Color.RED, texto));}
	public static void printaPretoln(String texto) 
		{System.out.println(trocaCor(Color.BLACK, texto));}
	public static void printaPreto(String texto) 
		{System.out.print(trocaCor(Color.BLACK, texto));}
	public static void printaVerdeln(String texto) 
		{System.out.println(trocaCor(Color.GREEN, texto));}
	public static void printaVerde(String texto) 
		{System.out.print(trocaCor(Color.GREEN, texto));}
	public static void printaAzulln(String texto) 
		{System.out.println(trocaCor(Color.BLUE, texto));}
	public static void printaAzul(String texto) 
		{System.out.print(trocaCor(Color.BLUE, texto));}

	public static String trocaCor(Color cor, String texto) {
		if (System.getProperty("os.name").contains("Windows") && argumentos.contains("-w"))
			return ansi().fgBright(cor).bold().a(texto).reset() + "";
		else return texto;
	}
	
	public static void printaWarning(String texto, Exception e) throws InterruptedException {
		printaVermelholn(texto);
		TimeUnit.SECONDS.sleep(1);
		new GeraLog().escreveLogArquivo(e);
	}

	public static void limpaConsole() {
		try {
			if (System.getProperty("os.name").contains("Windows") && console) {
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
				printaVerdeln(cabecalho());
			}
		} catch (IOException | InterruptedException ex) {
		}
	}
}
