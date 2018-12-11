package edu.senai.integrador.a.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import edu.senai.integrador.beans.cadastro.ContatoException;
import edu.senai.integrador.beans.cadastro.EContatoErro;
import edu.senai.integrador.beans.cadastro.EEndereco;
import edu.senai.integrador.beans.cadastro.EEnderecoErro;
import edu.senai.integrador.beans.cadastro.EUsuarioErro;
import edu.senai.integrador.beans.cadastro.EnderecoException;
import edu.senai.integrador.beans.cadastro.UsuarioException;
import edu.senai.integrador.beans.pessoa.AlunoException;
import edu.senai.integrador.beans.pessoa.EAlunoErro;
import edu.senai.integrador.beans.pessoa.EEscolaridade;
import edu.senai.integrador.beans.pessoa.EEstadoCivil;
import edu.senai.integrador.beans.pessoa.EFuncionarioErro;
import edu.senai.integrador.beans.pessoa.EPessoaErro;
import edu.senai.integrador.beans.pessoa.ESexo;
import edu.senai.integrador.beans.pessoa.FuncionarioException;
import edu.senai.integrador.beans.pessoa.PessoaException;

public class LeituraTerminal {
	private BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));

	public String leString(int minimo) throws IOException, LeituraException {
		String leitura = teclado.readLine();
		if (leitura.trim().isEmpty() || leitura.length() < minimo)
			throw new LeituraException(ELeituraErro.VALOR_VAZIO);
		return leitura;
	}

	public String leNome() throws IOException, PessoaException {
		String nome = teclado.readLine().toLowerCase();
		if (nome.length() < 6 || nome.length() > 45) {
			throw new PessoaException(EPessoaErro.NOME_TAMANHO);
		}
		if (!nome.matches("[a-zÀ-ÿ\\s]{6,45}"))
			throw new PessoaException(EPessoaErro.NOME_CARACTERES_INVALIDOS);
		return titleCase(nome);
	}

	public String leUsuario(int minimo, int maximo) throws UsuarioException, IOException {
		String leitura = teclado.readLine();
		if (leitura.length() < minimo || leitura.length() > maximo)
			throw new UsuarioException(EUsuarioErro.USUARIO_TAMANHO);
		if (!leitura.matches("[A-Za-z0-9-@_\\.\\s]{4,30}"))
			throw new UsuarioException(EUsuarioErro.USUARIO_INVALIDO);
		return leitura;
	}

	public String leSenha(int minimo) throws  UsuarioException, IOException {
		String leitura = teclado.readLine();
		if (leitura.length() > 30 || leitura.length() < minimo)
			throw new UsuarioException(EUsuarioErro.SENHA_TAMANHO);
		return leitura;
	}

	public String leCtps() throws LeituraException, IOException {
		String leitura = teclado.readLine().trim();
		if (leitura.matches("\\d{6,20}")) return leitura;
		else throw new LeituraException(ELeituraErro.NUMERO_INVALIDO);
	}
	public int leInt() throws NumberFormatException, IOException {
		return Integer.parseInt(teclado.readLine().trim());
	}

	public double leAltura() throws IOException, AlunoException {
		String leitura = teclado.readLine().replaceAll(",.", "");
		if (!leitura.matches("[1-2].?[0-9]?[0-9]?") || Double.parseDouble(leitura) > 3)
			throw new AlunoException(EAlunoErro.ALTURA_INVALIDA);
		return Double.parseDouble(leitura);
	}

	public double lePeso() throws IOException, AlunoException {
		double leitura = Double.valueOf(teclado.readLine());
		if (leitura < 40 && leitura > 300)
			throw new AlunoException(EAlunoErro.PESO_INVALIDO);
		else return leitura;
	}

	public String leCpf() throws IOException, PessoaException {
		String leitura = teclado.readLine();
		StringBuffer builder = new StringBuffer();
		if (!leitura.matches("\\d{3}[.,\\-_]?\\d{3}[.,\\-_]?\\d{3}[.,\\-_]?\\d{2}"))
			throw new PessoaException(EPessoaErro.CPF_FORMATO_INVALIDO);
		for (int i = 0; i < leitura.length(); i++) {
			if (String.valueOf(leitura.charAt(i)).matches("\\d"))
				builder.append(leitura.charAt(i));
		}
		return builder.toString();
	}

	public String leTelefone() throws IOException, ContatoException {
		String leitura = teclado.readLine();
		if (!leitura.matches("\\(?\\d{2}[\\)\\s\\-]?\\d[\\s\\-]?\\d{2}[\\-\\s]?\\d{2}[\\-\\s]?\\d[\\-\\s]?\\d{3}"))
			throw new ContatoException(EContatoErro.NUMERO_INVALIDO);
		return leitura;
	}

	public LocalDate leData() throws IOException, NumberFormatException {
		String leitura = teclado.readLine();
		LocalDate data = null;
		if (leitura.matches("\\d{1,2}[/.-]?\\d{2}[/.-]?\\d{2,4}")) {
			data = LocalDate.of(Integer.valueOf(leitura.substring(4, 8)), Integer.valueOf(leitura.substring(2, 4)),
					Integer.valueOf(leitura.substring(0, 2)));
		}
		return data;
	}
	
	public LocalTime leHora() throws IOException, NumberFormatException {
		String leitura = teclado.readLine();
		if (leitura.matches("\\d?\\d:?\\d\\d")) {
			leitura.replace(':',' ');
			leitura.trim();
			if(leitura.length() < 4) leitura = "0" + leitura;
			if(Integer.parseInt(leitura) < 2359) {
				return LocalTime.parse(leitura, DateTimeFormatter.ofPattern("HHmm"));
			}
		} 
		throw new NumberFormatException();
	}

	public LocalDate validaPeriodo(int teste) throws IOException, AlunoException, FuncionarioException {
		LocalDate data = leData();
		if (Period.between(data, LocalDate.now()).getYears() < teste && teste == 18)
			throw new FuncionarioException(EFuncionarioErro.DATA_IDADE_INSUFICIENTE);
		if (Period.between(data, LocalDate.now()).getYears() < teste && teste == 16)
			throw new AlunoException(EAlunoErro.DATA_IDADE_INSUFICIENTE);
		return data;
	}

	public String leEndereco() throws IOException, PessoaException{
		return leNome();
	}

	public String leEmail() throws IOException, ContatoException {
		String leitura = teclado.readLine().toLowerCase();
		if (!leitura.matches(
				"([a-z][a-z0-9!#$%&'*+-/=?^_`{|}~]*|[\"[(),:;<>@\\]\"]*[a-z0-9]*[\\._-]?[a-z]*@[a-z0-9]*\\.[a-z]{2,3}(\\.[a-z]{0,3})?)"))
			throw new ContatoException(EContatoErro.EMAIL_INVALIDO);
		return leitura;
	}

	public String leIP() throws IOException, LeituraException {
		String leitura = teclado.readLine().toLowerCase();
		if(leitura.matches("localhost:(\\d{2,6})")) 
			return leitura+"/";
		else if (leitura.matches("[0-1]?[0-9]?[0-8]\\.?[0-9]?[0-9]?[0-9]\\.?[0-9]?[0-9]?[0-9]\\.?[0-9]?[0-9]?[0-9]\\:[0-9]{2,6}")) {
			return leitura + "/";
		}			
		else
			throw new LeituraException(ELeituraErro.IP_INVALIDO);
	}

	public String leBoolean() throws IOException, LeituraException {
		char leitura = teclado.readLine().toLowerCase().charAt(0);
		switch (leitura) {
		case 's':
			return "true";
		case 'n':
			return "false";
		default:
			throw new LeituraException(ELeituraErro.SIM_NAO);
		}
	}

	public ESexo leSexo() throws PessoaException, IOException {
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

	public EEstadoCivil leEstadoCivil() throws PessoaException, IOException {
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

	public EEscolaridade leEscolaridade() throws IOException, FuncionarioException  {
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

	public String titleCase(String string) {
		String[] lista = string.split(" ");
		string = "";
		for (int i = 0; i < lista.length; i++) {
			if (lista[i].length() < 3) {
				string += lista[i] + " ";
			} else
			if (lista[i].length() > 2) {
				lista[i] = Character.toUpperCase(lista[i].charAt(0)) + lista[i].substring(1, lista[i].length()) + " ";
				string += lista[i];
			}
		}
		return string;
	}
}