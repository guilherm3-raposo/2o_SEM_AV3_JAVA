package edu.senai.integrador.main;

import static org.fusesource.jansi.Ansi.ansi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.fusesource.jansi.*;
import org.fusesource.jansi.Ansi.Color;

import edu.senai.integrador.dao.LoginDAO;
import edu.senai.integrador.ferramentas.Configuracoes;
import edu.senai.integrador.ferramentas.LeituraTerminal;

public class UsaSistema {
	public static void main(String[] args) {
		AnsiConsole.systemInstall();
		String teclado = null;

		System.out.println(verdePreto(cabecalho()).reset() + "\n");
		System.out.print(verdePreto(menuPrincipalSU()).reset());
		System.out.print(" >");
		for (int i = 0; i < 1 || i > 6;) {
			i = LeituraTerminal.leInt();
			
			if (i < 1 || i > 6) {
				System.out.print(vermelhoPreto("Digite um número entre 1 e 6").reset().cursorUp(1)
						.cursorLeft(String.valueOf(i).length() + 2) + " " + ansi().cursorLeft(1));
			}
		}
	}

	public static List<String> leParametros(String[] args) {
		List<String> param = new ArrayList<String>();
		for (int i = 0; i < args.length; i++) {
			param.add(args[i]);
		}
		return param;
	}

	public static boolean validaRegistro() {
		Properties prop = Configuracoes.Carrega();
		return prop.get("registrado") == "true";
	}

	public static boolean validaUsuario() {
		LoginDAO loginDAO = new LoginDAO();
		System.out.println("Digite o ");
		return true;
	}

	public static void limpaConsole() {
		try {
			if (System.getProperty("os.name").contains("Windows"))
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
		} catch (IOException | InterruptedException ex) {
		}
	}

	public static String cabecalho() {
		final String cabecalho = "==================================\n";
		final String separador = "|                                |\n";
		final String titulo = "|       FOCA NA BALANÇA!!!       |\n";
		return cabecalho + separador + titulo + separador + cabecalho;
	}

	public static String menuPrincipalSU() {
		final String menu = "1. Funcionários\n" + "2. Alunos\n" + "3. Modalidades\n" + "4. Turmas\n" + "5. Permissões\n"
				+ "6. Configurações\n\n" + "Digite a opção desejada";
		return menu;
	}

	private static Ansi verdePreto(String texto) {
		Ansi textoFormatado = ansi().fgBright(Color.GREEN).bold().a(texto);
		return textoFormatado;
	}
	
	private static Ansi vermelhoPreto(String texto) {
		Ansi textoFormatado = ansi().fgBright(Color.RED).bold().a(texto);
		return textoFormatado;
	}
}
