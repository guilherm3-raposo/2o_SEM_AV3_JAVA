package edu.senai.projetoModafackaIntegrador.populadores2;

import java.time.LocalDate;
import java.util.Random;

import edu.senai.integrador.beans.Aluno;
import edu.senai.integrador.beans.Funcionario;
import edu.senai.integrador.beans.enumeradores.EEscolaridade;
import edu.senai.integrador.beans.enumeradores.EEstadoCivil;
import edu.senai.integrador.beans.enumeradores.ESexo;
import edu.senai.integrador.beans.exception.AlunoException;
import edu.senai.integrador.beans.exception.FuncionarioException;
import edu.senai.integrador.beans.exception.PessoaException;

public class Instanciador {
	public static Aluno criaAluno(long i) {
		Random random = new Random();

		try {
			Aluno aluno = new Aluno(

					"99988877" + (i < 10 ? "70" + i : i < 100 ? "7" + i : i),

					ENomes.values()[random.nextInt(10)].getNome() + " "
							+ ENomes.values()[random.nextInt(10)].getSegundoNome() + " "
							+ ENomes.values()[random.nextInt(10)].getTerceiroNome(),

					LocalDate.of(random.nextInt(100) + 1900, random.nextInt(11) + 1, random.nextInt(27) + 1),

					ESexo.values()[random.nextInt(1)],

					random.nextDouble() + 1,

					Double.valueOf(random.nextInt(40) + 40),

					EEstadoCivil.values()[random.nextInt(4)]);
			return aluno;
		} catch (PessoaException | AlunoException | FuncionarioException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Funcionario criaFuncionario(int i) {
		Random random = new Random();

		try {
			Funcionario funcionario = new Funcionario("88877766" + (i < 10 ? "60" + i : i < 100 ? "6" + i : i),
					"000111223334445" + i,

					EEscolaridade.values()[random.nextInt(5)],

					ENomes.values()[random.nextInt(10)].getNome() + " "
							+ ENomes.values()[random.nextInt(10)].getSegundoNome() + " "
							+ ENomes.values()[random.nextInt(10)].getTerceiroNome(),

					LocalDate.of(random.nextInt(100) + 1900, random.nextInt(11) + 1, random.nextInt(27) + 1),

					ESexo.values()[random.nextInt(1)],

					EEstadoCivil.values()[random.nextInt(4)]);
			return funcionario;
		} catch (PessoaException | FuncionarioException e) {
			e.printStackTrace();
		}
		return null;
	}
}
