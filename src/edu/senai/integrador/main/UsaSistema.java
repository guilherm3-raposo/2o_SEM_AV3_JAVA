package edu.senai.integrador.main;

import static org.fusesource.jansi.Ansi.ansi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.Ansi.Color;
import org.fusesource.jansi.AnsiConsole;

import edu.senai.integrador.bancodedados.conexao.ConexaoException;
import edu.senai.integrador.beans.Login;
import edu.senai.integrador.beans.enumeradores.EPessoaErro;
import edu.senai.integrador.beans.exception.PessoaException;
import edu.senai.integrador.dao.DAOException;
import edu.senai.integrador.dao.LoginDAO;
import edu.senai.integrador.ferramentas.Configuracoes;
import edu.senai.integrador.ferramentas.LeitorArquivo;
import edu.senai.integrador.ferramentas.LeituraException;
import edu.senai.integrador.ferramentas.LeituraTerminal;
import edu.senai.integrador.logs.GeraLog;
import edu.senai.integrador.seguranca.Seguranca;
import edu.senai.integrador.seguranca.SegurancaException;

public class UsaSistema {
	public static ItemsMenu text = new ItemsMenu();
	public static List<String> argumentos = new ArrayList<String>();
	private static Configuracoes config = new Configuracoes();
	private static LeituraTerminal leitor = new LeituraTerminal();

	public static void main(String[] args) throws Exception {
		argumentos = leParametros(args);
		AnsiConsole.systemInstall();
		limpaConsole();
		setUp();
		int i = validaUsuario();
		Menus.menuPrincipal(i);
	}

//	_________________________________________________________________________________________

	public static boolean setUp() throws Exception {
		LeitorArquivo.criaArquivos();
		menuCadastroSistema();
		menuCadastraUsuario();
		menuCadastraSenha();

		cadastraBD(text.NOME_SISTEMA);
		cadastraBD(text.BD_NOME);
		cadastraBD(text.BD_SENHA);
		cadastraBD(text.BD_IP);
		cadastraBD(text.BD_SSL);
		cadastraBD(text.BD_USR);
		cadastraBD(text.BD_PWD);
		return true;
	}

	public static boolean menuCadastroSistema() throws InterruptedException, IOException {
		limpaConsole();
		while (true) {
			try {
				System.out.println(text.CADAST_SIST);
				System.out.print(text.SERIAL + preto());
				Seguranca.confereSerial(leitor.leString());
				limpaConsole();
				System.out.println(text.REGISTRO + preto());
				TimeUnit.SECONDS.sleep(1);
				break;
			} catch (SegurancaException e) {
				printaWarning(e.getDescricaoErro());
				continue;
			} catch (LeituraException e) {
				new GeraLog().escreveLogArquivo(e);
				System.out.println(e.getErro().getDescricaoErro());
				continue;
			}
		}
		return true;
	}

	public static boolean menuCadastraUsuario() throws InterruptedException {
		Properties prop = new Configuracoes().carrega(true);
		String superUserId = "";

		while (true) {
			limpaConsole();
			try {
				System.out.println(text.CADAST_USUA);
				System.out.print(text.USUARIO + preto());
				superUserId = leitor.leUsuario();
				prop.setProperty("superUser.id", superUserId);
				config.salva(prop, true);
			} catch (LeituraException e) {
				printaWarning(e.getErro().getDescricaoErro());
				new GeraLog().escreveLogArquivo(e);
			} catch (Exception e) {
				new GeraLog().escreveLogArquivo(e);
			}
			break;
		}
		return true;
	}

	public static boolean menuCadastraSenha() throws InterruptedException {
		Properties prop = new Configuracoes().carrega(true);
		String superUserPwd = "";

		while (true) {
			limpaConsole();
			try {
				System.out.print(text.SENHA + preto());
				superUserPwd = leitor.leSenha();
				prop.setProperty("superUser.pwd", superUserPwd);
				config.salva(prop, true);
				System.out.println(text.USR_SUCESSO);
				TimeUnit.SECONDS.sleep(1);
				limpaConsole();
			} catch (LeituraException e) {
				printaWarning(e.getErro().getDescricaoErro());
				new GeraLog().escreveLogArquivo(e);
			} catch (Exception e) {
				new GeraLog().escreveLogArquivo(e);
			}
			break;
		}
		return true;
	}

	private static boolean cadastraBD(String item) throws InterruptedException {
		while (true) {
			try {
				limpaConsole();
				Properties prop = config.carrega(false);
				System.out.print(item + preto());
				if (item.matches(text.BD_NOME))
					prop.setProperty("sistema", leitor.leNome());
				if (item.matches(text.BD_IP))
					prop.setProperty("sistema", leitor.leIP());
				if (item.matches(text.BD_SSL))
					prop.setProperty("sistema", leitor.leSim());
				if (item.matches(text.BD_USR))
					prop.setProperty("sistema", leitor.leString());
				if (item.matches(text.BD_PWD))
					prop.setProperty("sistema", leitor.leString());
				config.salva(prop, false);
				break;
			} catch (LeituraException e) {
				System.out.println(verde() + e.getMessage() + preto());
				TimeUnit.SECONDS.sleep(1);
			} catch (PessoaException e) {
				System.out.println(verde() + e.getMessage() + preto());
				TimeUnit.SECONDS.sleep(1);
			} catch (Exception e) {
				printaWarning(EPessoaErro.NOME_VAZIO.getErro());
			}
			limpaConsole();
		}
		return true;
	}

	public static int validaUsuario() {
		LoginDAO loginDAO = new LoginDAO();
		while (true) {
			try {
				System.out.println(text.LOGIN_USR);
				String usuario = leitor.leString();
				Login login = loginDAO.consulta(usuario);
				System.out.println(text.LOGIN_SENHA);
				usuario = leitor.leString();
				if (login.getSenha().matches(usuario))
					System.out.println(text.LOGIN_SUCESSO);
				return login.getPermissao();

			} catch (DAOException e) {
				System.out.println(e.getMessage());
			} catch (ConexaoException e) {
				System.out.println(e.getMessage());
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

//	_________________________________________________________________________________

	public static List<String> leParametros(String[] args) {
		List<String> param = new ArrayList<String>();
		for (int i = 0; i < args.length; i++) {
			param.add(args[i]);
		}
		return param;
	}

	public static String cabecalho() {
		final String cabecalho = "==================================\n";
		final String separador = "|                                |\n";
		final String titulo = "|       FOCA NA BALANÇA!!!       |\n";
		return cabecalho + separador + titulo + separador + cabecalho;
	}

	public static Ansi preto() {
		return ansi().fg(Color.BLACK).a("");
	}

	public static Ansi verde() {
		return ansi().fgBlack().a("");
	}

	public static void printaWarning(String texto) throws InterruptedException {
		System.out.println(ansi().fgBright(Color.RED).bold().a(texto).fgBlack());
		TimeUnit.SECONDS.sleep(1);
	}

	public static void limpaConsole() {
		try {
			verde();
			if (System.getProperty("os.name").contains("Windows") && argumentos.contains("-w"))
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
		} catch (IOException | InterruptedException ex) {
		}
	}
}
