package edu.senai.integrador.ferramentas;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.Period;

import edu.senai.integrador.beans.Aluno;
import edu.senai.integrador.beans.enumeradores.EAlunoErro;
import edu.senai.integrador.beans.enumeradores.EContatoErro;
import edu.senai.integrador.beans.enumeradores.EEndereco;
import edu.senai.integrador.beans.enumeradores.EEnderecoErro;
import edu.senai.integrador.beans.enumeradores.EEscolaridade;
import edu.senai.integrador.beans.enumeradores.EEstadoCivil;
import edu.senai.integrador.beans.enumeradores.EFuncionarioErro;
import edu.senai.integrador.beans.enumeradores.EPessoaErro;
import edu.senai.integrador.beans.enumeradores.ESexo;
import edu.senai.integrador.beans.exception.AlunoException;
import edu.senai.integrador.beans.exception.ContatoException;
import edu.senai.integrador.beans.exception.EnderecoException;
import edu.senai.integrador.beans.exception.FuncionarioException;
import edu.senai.integrador.beans.exception.PessoaException;

public class LeituraTerminal {
	private static BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));

	public static String leString() throws Exception {
		String leitura = teclado.readLine();
		if (!leitura.trim().isEmpty())
			return leitura;
		throw new PessoaException(EPessoaErro.VALOR_VAZIO);
	}

	public static String leNome() throws Exception {
		String nome = teclado.readLine().toLowerCase();
		if (!nome.trim().matches("[a-z]*"))
			throw new PessoaException(EPessoaErro.NOME_INVALIDO);
		return titleCase(nome);

	}

	public static int leInt() throws Exception {
		return Integer.parseInt(teclado.readLine());
	}

	public static double leAltura() throws Exception {
		String leitura = teclado.readLine();
		if (!leitura.matches("[1-3]"))
			throw new AlunoException(EAlunoErro.ALTURA_INVALIDA, Aluno.class, "");
		return Double.parseDouble(leitura);
	}

	public double lePeso() throws Exception {
		double leitura = Double.valueOf(teclado.readLine());
		if (leitura < 40 && leitura > 300)
			throw new AlunoException(EAlunoErro.PESO_INVALIDO, Aluno.class, "");
		return leitura;
	}

	public static String leCpf() throws Exception {
		String leitura = teclado.readLine();
		StringBuffer builder = new StringBuffer();
		if (!leitura.matches("\\d{3}[.,\\-_]?\\d{3}[.,\\-_]?\\d{3}[.,\\-_]?\\d{2}"))
			throw new PessoaException(EPessoaErro.CPF_FORMATO_INVALIDO);
		for (int i = 0; i < leitura.length(); i++) {
			if (String.valueOf(leitura.charAt(i)).matches("[a-z]"))
				builder.append(leitura.charAt(i));
		}
		return builder.toString();
	}

	public static String leTelefone() throws Exception {
		String leitura = teclado.readLine();
		if (!leitura.matches("\\(?\\d{2}[\\)\\s\\-]?\\d[\\s\\-]?\\d{2}[\\-\\s]?\\d{2}[\\-\\s]?\\d[\\-\\s]?\\d{3}"))
			throw new ContatoException(EContatoErro.NUMERO_INVALIDO);
		return leitura;
	}

	public static LocalDate leData() throws Exception {
		String leitura = teclado.readLine();
		LocalDate data = null;
		if (leitura.matches("\\d{1,2}[/.-]?\\d{2}[/.-]?\\d{2,4}")) {
			validaDia(Integer.valueOf(leitura.substring(0, 2)));
			validaMes(Integer.valueOf(leitura.substring(2, 4)));
			validaAno(Integer.valueOf(leitura.substring(4, 8)));
			data = LocalDate.of(Integer.valueOf(leitura.substring(4, 8)), Integer.valueOf(leitura.substring(2, 4)),
					Integer.valueOf(leitura.substring(0, 2)));

		}
		return data;
	}
	
	public static boolean validaAno(int ano) throws PessoaException {
		if (ano < 1900)
			throw new PessoaException(EPessoaErro.DATA_ANO_INVALIDO);
		return true;
	}

	public static boolean validaMes(int mes) throws PessoaException {
		if (mes < 1 || mes > 12)
			throw new PessoaException(EPessoaErro.DATA_MES_INVALIDO);
		return true;
	}

	public static boolean validaDia(int dia) throws PessoaException {
		if (dia < 1 || dia > 31)
			throw new PessoaException(EPessoaErro.DATA_DIA_INVALIDO);
		return true;
	}

	public static boolean lePeriodo(LocalDate data, int teste) {
		return Period.between(data, LocalDate.now()).getYears() < teste;
	}

	public static String leEndereco() throws Exception {
		return leNome();
	}

	public static String leEmail() throws Exception {
		String leitura = teclado.readLine().toLowerCase();
		if (!leitura.matches(
				"([a-z][a-z0-9!#$%&'*+-/=?^_`{|}~]*|[\"[(),:;<>@\\]\"]*[a-z0-9]*[\\._-]?[a-z]*@[a-z0-9]*\\.[a-z]{2,3}(\\.[a-z]{0,3})?)"))
			throw new ContatoException(EContatoErro.EMAIL_INVALIDO);
		return leitura;
	}

	public static String leIP() throws Exception {
		String leitura = teclado.readLine();
		if (!leitura.matches("(\\d{3}.?)+(\\d{3}.?)+\\d+(.?)+\\d{0,6}"))
			throw new LeituraException(ELeituraErro.IP_INVALIDO);
		return leitura;
	}

	public static String leSim() throws Exception {
		String leitura = teclado.readLine();
		if (leitura.toLowerCase().matches("\\bsim\\b|\\byes\\b|\\bs\\b|\\by\\b|\\b1\\b"))
			return "true";
		else if (leitura.toLowerCase().matches("\\bnao\\b|\\bno\\b|\\bn\\b|\\b0\\b"))
			return "false";
		else
			throw new LeituraException(ELeituraErro.SIM_NAO);
	}

	public static ESexo leSexo() throws Exception {
		char leitura = teclado.readLine().toLowerCase().charAt(0);
		switch (leitura) {
		case 'f':
			return ESexo.FEMININO;
		case 'm':
			return ESexo.MASCULINO;
		default:
			throw new PessoaException(EPessoaErro.SEXO_INVALIDO);
		}
	}

	public static EEstadoCivil leEstadoCivil() throws Exception {
		char leitura = teclado.readLine().toLowerCase().charAt(0);
		switch (leitura) {
		case '1':
			return EEstadoCivil.SOLTEIRO;
		case '2':
			return EEstadoCivil.CASADO;
		case '3':
			return EEstadoCivil.DIVORCIADO;
		case '4':
			return EEstadoCivil.SEPARADO;
		default:
			throw new PessoaException(EPessoaErro.ESTADO_CIVIL_INVALIDO);
		}
	}

	public static EEscolaridade leEscolaridade() throws Exception {
		char leitura = teclado.readLine().toLowerCase().charAt(0);
		switch (leitura) {
		case '1':
			return EEscolaridade.FUNDAMENTAL_INCOMPLETO;
		case '2':
			return EEscolaridade.FUNDAMENTAL_COMPLETO;
		case '3':
			return EEscolaridade.MEDIO_INCOMPLETO;
		case '4':
			return EEscolaridade.MEDIO_COMPLETO;
		case '5':
			return EEscolaridade.SUPERIOR_INCOMPLETO;
		case '6':
			return EEscolaridade.SUPERIOR_COMPLETO;
		default:
			throw new FuncionarioException(EFuncionarioErro.ESCOLARIDADE_INVALIDA);
		}
	}

	public EEndereco leLogradouro() throws Exception {
		char leitura = teclado.readLine().toLowerCase().charAt(0);
		switch (leitura) {
		case '1':
			return EEndereco.RUA;
		case '2':
			return EEndereco.AVENIDA;
		case '3':
			return EEndereco.ALAMEDA;
		case '4':
			return EEndereco.RODOVIA;
		case '5':
			return EEndereco.VILA;
		default:
			throw new EnderecoException(EEnderecoErro.LOGRADOURO_INVALIDO);
		}
	}

	public static String titleCase(String string) {
		String[] lista = string.split(" {2,45}");
		for (int i = 0; i < lista.length; i++) {
			if (lista[i].length() > 2)
				lista[i] = Character.toUpperCase(lista[i].charAt(0)) + lista[i].substring(1, lista[i].length());
			string += lista[i];
		}
		return string;
	}
}