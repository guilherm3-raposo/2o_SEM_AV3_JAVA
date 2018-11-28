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

import edu.senai.integrador.beans.enumeradores.EPessoaErro;
import edu.senai.integrador.beans.exception.PessoaException;
import edu.senai.integrador.ferramentas.Configuracoes;
import edu.senai.integrador.ferramentas.LeitorArquivo;
import edu.senai.integrador.ferramentas.LeituraException;
import edu.senai.integrador.ferramentas.LeituraTerminal;
import edu.senai.integrador.seguranca.Seguranca;

public class UsaSistema {
	public static ItemsMenu text = new ItemsMenu();
	public static List<String> argumentos = new ArrayList<String>();
	private static Configuracoes config = new Configuracoes();

	public static void main(String[] args) throws InterruptedException, LeituraException {
		argumentos = leParametros(args);
		AnsiConsole.systemInstall();
		limpaConsole();
		setUp();
		int i = Menus.validaUsuario();
		Menus.menuPrincipal(i);
	}

//	_________________________________________________________________________________________
	public static boolean setUp() throws InterruptedException, LeituraException {
		LeitorArquivo.criaArquivos();
		while (!Seguranca.getRegistro()) {
			menuCadastroSistema();
		}
		menuCadastraSu();

		cadastraBD(text.NOME_SISTEMA);
		cadastraBD(text.BD_NOME);
		cadastraBD(text.BD_SENHA);
		cadastraBD(text.BD_IP);
		cadastraBD(text.BD_SSL);
		cadastraBD(text.BD_USR);
		cadastraBD(text.BD_PWD);
		return true;
	}

	public static boolean menuCadastroSistema() throws InterruptedException {
		limpaConsole();
		System.out.println(text.CADAST_SIST);
		System.out.print(text.SERIAL + preto());

		try {
			boolean isledo = Seguranca.setRegistro(LeituraTerminal.leString());
			if (isledo) {
				limpaConsole();
				System.out.println(text.REGISTRO + preto());
				TimeUnit.SECONDS.sleep(1);
				return true;
			} else {
				printaWarning(text.SERIAL_INV);
			}
		} catch (Exception e) {
			printaWarning(EPessoaErro.NOME_VAZIO.getErro());
		}
		return false;
	}

	public static boolean menuCadastraSu() throws InterruptedException {
		Configuracoes config = new Configuracoes();
		Properties prop = config.carrega(true);
		String superUserId = "";
		String superUserPwd = "";
		boolean usrValido = false;
		boolean pwdValido = false;

		while (!usrValido || !pwdValido) {
			limpaConsole();
			System.out.println(text.CADAST_USUA);
			System.out.print(text.USUARIO + preto());

			try {
				superUserId = LeituraTerminal.leString();
				usrValido = superUserId.matches("[A-Za-z0-9_\\-.]{6,20}");
				superUserPwd = LeituraTerminal.leString();
				pwdValido = superUserPwd.matches(".{6,20}");

				if (usrValido) {
					prop.setProperty("superUser.id", superUserId);
					prop.setProperty("superUser.pwd", superUserPwd);
					config.salva(prop, true);
					break;
				} else {
					printaWarning(text.CARAC_INVALID);
				}
			} catch (Exception e) {
				printaWarning(EPessoaErro.NOME_VAZIO.toString());
			}
			limpaConsole();
			System.out.println(text.USR_SUCESSO);
		}
		return true;
	}
	private static boolean cadastraBD(String item) throws InterruptedException {
		while (true) {
			try {
				limpaConsole();
				Properties prop = config.carrega(false);
				System.out.print(item + preto());
				if (item.matches(text.BD_NOME)) prop.setProperty("sistema", LeituraTerminal.leNome());
				if (item.matches(text.BD_IP)) 	prop.setProperty("sistema", LeituraTerminal.leIP());
				if (item.matches(text.BD_SSL)) 	prop.setProperty("sistema", LeituraTerminal.leSim());
				if (item.matches(text.BD_USR))	prop.setProperty("sistema", LeituraTerminal.leString());
				if (item.matches(text.BD_PWD))	prop.setProperty("sistema", LeituraTerminal.leString());
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

	public static Ansi preto() {return ansi().fg(Color.BLACK).a("");}
	public static Ansi verde() {return ansi().fgBlack().a("");}
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
