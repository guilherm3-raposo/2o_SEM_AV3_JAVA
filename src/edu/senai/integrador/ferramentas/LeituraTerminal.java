package edu.senai.integrador.ferramentas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;

import edu.senai.integrador.beans.enumeradores.EEndereco;
import edu.senai.integrador.beans.enumeradores.EEscolaridade;
import edu.senai.integrador.beans.enumeradores.EEstadoCivil;
import edu.senai.integrador.beans.enumeradores.ESexo;
import edu.senai.integrador.beans.exception.PessoaException;

public class LeituraTerminal {
	private static BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));

	public static String leString() {
		while (true) {
			try {
				String leitura = teclado.readLine();
				Validacoes.validaVazio(leitura);
				return leitura;
			} catch (PessoaException | IOException e) {
				System.out.println("Digite um valor válido");
			}
		}
	}

	public static String leNome() {
		while (true) {
			try {
				String leitura = teclado.readLine();
				Validacoes.validaVazio(leitura);
				Validacoes.validaNome(leitura);
				return leitura;
			} catch (PessoaException | IOException e) {
				System.out.println("Digite um valor válido");
			}
		}
	}

	public static int leInt() {
		while (true) {
			try {
				return Integer.parseInt(teclado.readLine());
			} catch (NumberFormatException | IOException e) {
				System.out.println("Digite um número inteiro válido");
			}
		}
	}

	public static double leDouble() {
		while (true) {
			try {
				return Double.parseDouble(teclado.readLine());
			} catch (NumberFormatException | IOException e) {
				System.out.println(e);
			}
		}
	}

	public static String leCpf() {
		while (true) {
			try {
				String leitura = teclado.readLine();
				Validacoes.validaVazio(leitura);
				Validacoes.validaCPF(leitura);
				return leitura;
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	public static LocalDate leData(boolean aluno) {
		while (true) {
			try {
				String leitura = teclado.readLine();
				Validacoes.validaTamanhoData(leitura);
				Validacoes.validaDia(Integer.valueOf(leitura.substring(0, 2)));
				Validacoes.validaMes(Integer.valueOf(leitura.substring(2, 4)));
				Validacoes.validaAno(Integer.valueOf(leitura.substring(4, 8)));
				LocalDate data = LocalDate.of(Integer.valueOf(leitura.substring(4, 8)),
											  Integer.valueOf(leitura.substring(2, 4)), 
											  Integer.valueOf(leitura.substring(0, 2)));
				if (aluno)
					Validacoes.validaIdadeAluno(data);
				else
					Validacoes.validaIdadeFuncionario(data);
				return data;
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	public static ESexo leSexo() {
		while (true) {
			try {
				return Validacoes.validaSexo(teclado.readLine());
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	public static EEndereco leLogradouro() {
		while (true) {
			try {
				return Validacoes.validaLogradouro(teclado.readLine().charAt(0));
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	public static EEstadoCivil leEstadoCivil() {
		while (true) {
			try {
				return Validacoes.validaEstadoCivil(teclado.readLine().charAt(0));
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	public static EEscolaridade leEscolaridade() {
		while (true) {
			try {
				return Validacoes.validaEscolaridade(teclado.readLine().charAt(0));
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
}