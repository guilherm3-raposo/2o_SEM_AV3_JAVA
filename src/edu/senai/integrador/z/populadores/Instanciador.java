package edu.senai.integrador.z.populadores;

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
	public static Aluno criaAluno() {
		Random random = new Random();
		try {
			Aluno aluno = new Aluno(

					"99988877" + random.nextInt(3),

					ENomes.values()[random.nextInt(10)].getNome() + " "
							+ ENomes.values()[random.nextInt(10)].getSegundoNome() + " "
							+ ENomes.values()[random.nextInt(10)].getTerceiroNome(),

					LocalDate.of(random.nextInt(100) + 1900, random.nextInt(11) + 1, random.nextInt(27) + 1),

					ESexo.values()[random.nextInt(1)],

					random.nextDouble() + 1,

					Double.valueOf(random.nextInt(40) + 40),

					EEstadoCivil.values()[random.nextInt(4)]);
			aluno.setAtivo(true);
			return aluno;
		} catch (PessoaException | AlunoException | FuncionarioException e) {
			
		}
		return null;
	}

	public static Funcionario criaFuncionario(int i) {
		Random random = new Random();
		try {
			Funcionario funcionario = new Funcionario(
					"88877766" + random.nextInt(3),
					"000111223334445" + i,

					EEscolaridade.values()[random.nextInt(5)],

					ENomes.values()[random.nextInt(10)].getNome() + " "
							+ ENomes.values()[random.nextInt(10)].getSegundoNome() + " "
							+ ENomes.values()[random.nextInt(10)].getTerceiroNome(),

					LocalDate.of(random.nextInt(100) + 1900, random.nextInt(11) + 1, random.nextInt(27) + 1),

					ESexo.values()[random.nextInt(1)],

					EEstadoCivil.values()[random.nextInt(4)]);
			funcionario.setAtivo(true);
			return funcionario;
		} catch (PessoaException | FuncionarioException e) {
			
		}
		return null;
	}
}
