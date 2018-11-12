//	TODO ROGERIO REVISAR EXCECOES;
//	TODO CRIAR O RESTO DAS VALIDACOES;
//	TODO REVISAR E SEPARAR OS ENUMERADORES;
//	TODO CRIAR OS OUTROS DAOS;
//	TODO REVISAR iCRUD E O CRUD;
//	TODO REVISAR A CONSISTENCIA DOS CAMPOS DO BD
//	TODO CRIAR O LEITOR DO XML PRA POPULAR
//	TODO PESQUISAR SOBRE O INSTALADOR
// 	TODO ESTUDAR REGEX E XML;
//	TODO OTIMIZAR VALIDACOES PRA REGEX
//	TODO SEPARAR OS TIPOS DE LOG

package edu.senai.integrador.ferramentas;

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

public class Validacoes {
	public static boolean validaVazio(String leitura) throws PessoaException {
		if (leitura.trim().isEmpty())
			throw new PessoaException(EPessoaErro.VALOR_VAZIO);
		return true;
	}

	public static String validaCPF(String leitura) throws PessoaException {
		validaVazio(leitura);
		if (!leitura.matches("\\d{11}"))
			throw new PessoaException(EPessoaErro.CPF_FORMATO_INVALIDO);
		return leitura;
	}

	public static boolean validaNome(String leitura) throws PessoaException {
		if (leitura.matches(".*^\\d+.*"))
			throw new PessoaException(EPessoaErro.NOME_INVALIDO);
		return true;
	}

	public static ESexo validaSexo(String leitura) throws PessoaException {
		switch (leitura.toLowerCase().charAt(0)) {
		case 'f':
			return ESexo.FEMININO;
		case 'm':
			return ESexo.MASCULINO;
		default:
			throw new PessoaException(EPessoaErro.SEXO_INVALIDO);
		}
	}

	public static double validaAltura(double leitura) throws AlunoException {
		if (leitura > 3 || leitura <= 0)
			throw new AlunoException(EAlunoErro.ALTURA_INVALIDA, Aluno.class, "");
		return leitura;
	}

	public static double validaPeso(double leitura) throws AlunoException {
		if (leitura < 40 && leitura > 300)
			throw new AlunoException(EAlunoErro.PESO_INVALIDO, Aluno.class, "");
		return leitura;
	}

	public static EEndereco validaLogradouro(char leitura) throws EnderecoException {
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

	public static boolean validaEndereco(String leitura) throws EnderecoException {
		if (leitura.matches(".*\\d+.*"))
			throw new EnderecoException(EEnderecoErro.ENDERECO_INVALIDO);
		return true;
	}

	public static boolean validaNumero(int leitura) throws EnderecoException {
		if (leitura < 1 || leitura > Integer.MAX_VALUE)
			throw new EnderecoException(EEnderecoErro.NUMERO_INVALIDO);
		return true;
	}

	public static String validaTelefone(String leitura) throws ContatoException {
		if (!leitura.matches("\\d{2}+9\\d{8}") && !leitura.matches("9\\d{8}"))
			throw new ContatoException(EContatoErro.NUMERO_INVALIDO);
		return leitura;
	}

	public static String validaEmail(String leitura) throws ContatoException {
		leitura.toLowerCase();
		if (leitura.matches(
				"^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$")) {
			int teste = 0;
			for (int i = 0; i < leitura.length(); i++) {
				if (leitura.charAt(i) == '@')
					teste++;
				if (teste > 1)
					throw new ContatoException(EContatoErro.EMAIL_INVALIDO);
			}
			return leitura;
		} else
			throw new ContatoException(EContatoErro.EMAIL_INVALIDO);
	}

	public static EEstadoCivil validaEstadoCivil(char leitura) throws PessoaException {
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

	public static boolean validaIdadeAluno(LocalDate dataDeNascimento) throws AlunoException {
		if (Period.between(dataDeNascimento, LocalDate.now()).getYears() < 16) {
			throw new AlunoException(EAlunoErro.DATA_IDADE_INSUFICIENTE, Aluno.class, "");
		}
		return true;
	}

	public static boolean validaIdadeFuncionario(LocalDate dataDeNascimento) throws FuncionarioException {
		if (Period.between(dataDeNascimento, LocalDate.now()).getYears() < 18) {
			throw new FuncionarioException(EFuncionarioErro.DATA_IDADE_INSUFICIENTE);
		}
		return true;
	}

	public static boolean validaCtps(String leitura) throws FuncionarioException {
		if (!leitura.matches("\\d{8,15}"))
			throw new FuncionarioException(EFuncionarioErro.CTPS_INVALIDA);
		return true;
	}

	public static EEscolaridade validaEscolaridade(char leitura) throws FuncionarioException {
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

	public static boolean validaTamanhoData(String leitura) throws PessoaException {
		leitura = leitura.length() == 7 ? 0 + leitura : leitura;
		if (leitura.length() > 8 || leitura.length() < 7)
			throw new PessoaException(EPessoaErro.DATA_FORMATO_INVALIDO);
		return true;
	}
}