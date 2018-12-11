package edu.senai.integrador.z.populadores;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import edu.senai.integrador.bancodedados.conexao.ConexaoException;
import edu.senai.integrador.beans.academia.Exercicio;
import edu.senai.integrador.beans.academia.ExercicioException;
import edu.senai.integrador.beans.academia.Modalidade;
import edu.senai.integrador.beans.academia.ModalidadeException;
import edu.senai.integrador.beans.academia.Participantes;
import edu.senai.integrador.beans.academia.Turma;
import edu.senai.integrador.beans.academia.TurmaException;
import edu.senai.integrador.beans.cadastro.Contato;
import edu.senai.integrador.beans.cadastro.ContatoException;
import edu.senai.integrador.beans.cadastro.EEndereco;
import edu.senai.integrador.beans.cadastro.Endereco;
import edu.senai.integrador.beans.cadastro.EnderecoException;
import edu.senai.integrador.beans.cadastro.Usuario;
import edu.senai.integrador.beans.pessoa.Aluno;
import edu.senai.integrador.beans.pessoa.AlunoException;
import edu.senai.integrador.beans.pessoa.EEscolaridade;
import edu.senai.integrador.beans.pessoa.EEstadoCivil;
import edu.senai.integrador.beans.pessoa.ESexo;
import edu.senai.integrador.beans.pessoa.Funcionario;
import edu.senai.integrador.beans.pessoa.FuncionarioException;
import edu.senai.integrador.beans.pessoa.Pessoa;
import edu.senai.integrador.beans.pessoa.PessoaException;
import edu.senai.integrador.dao.AlunoDAO;
import edu.senai.integrador.dao.DAOException;
import edu.senai.integrador.dao.FuncionarioDAO;
import edu.senai.integrador.ferramentas.LeTxt;

public class Instanciador {
	private static Random random = new Random();

	public static Aluno criaAluno() {
		try {
			Aluno aluno = new Aluno(

					"99988877" + random.nextInt(9) + random.nextInt(9) + random.nextInt(9),

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
		} catch (PessoaException | AlunoException e) {

		}
		return null;
	}

	public static Funcionario criaFuncionario() {
		try {
			Funcionario funcionario = new Funcionario(
					"88877766" + +random.nextInt(9) + random.nextInt(9) + random.nextInt(9),
					"0001112233344" + random.nextInt(9) + random.nextInt(9) + random.nextInt(9),

					EEscolaridade.values()[random.nextInt(5)],

					ENomes.values()[random.nextInt(10)].getNome() + " "
							+ ENomes.values()[random.nextInt(10)].getSegundoNome() + " "
							+ ENomes.values()[random.nextInt(10)].getTerceiroNome(),

					LocalDate.of(random.nextInt(100) + 1900, random.nextInt(11) + 1, random.nextInt(27) + 1),

					ESexo.values()[random.nextInt(1)],

					EEstadoCivil.values()[random.nextInt(4)], true);
			funcionario.setAtivo(true);
			return funcionario;
		} catch (PessoaException | FuncionarioException e) {

		}
		return null;
	}

	public static Contato criaContato(Pessoa pessoa) throws ContatoException, PessoaException {
		String email;
		email = pessoa.getNome().substring(0, 15).toLowerCase();
		email = email.replaceAll(" ", "");
		email = email.replaceAll("[\\á\\à\\ã\\â\\ä]", "a");
		email = email.replaceAll("[\\é\\ê]", "e");
		email = email.replaceAll("[\\í]", "i");
		email = email.replaceAll("[\\ó\\õ\\ô]", "o");

		Contato contato = new Contato(pessoa.getCPF(),
				"4733" + random.nextInt(9) + random.nextInt(9) + "2" + random.nextInt(9) + random.nextInt(9)
						+ random.nextInt(9),

				"479" + (random.nextInt(1) + 8) + random.nextInt(9) + random.nextInt(9) + random.nextInt(9)
						+ random.nextInt(9) + random.nextInt(9) + random.nextInt(9) + random.nextInt(9),
				email + "@gmail.com");

		return contato;
	}

	public static Endereco criaEndereco(Pessoa pessoa) throws EnderecoException, PessoaException, IOException {
		List<String> cidades = LeTxt.getLista("/populadorCidades.txt");
		String cep = "";
		for (int i = 0; i < 8; i++) {
			cep += random.nextInt(9);
		}
		List<String> lista = LeTxt.getLista("/populadorRuas.txt");
		String[] num = lista.get(0).split(",");
		String[] dat = lista.get(1).split(",");
		Endereco endereco = new Endereco();
		endereco.setCpf(pessoa.getCPF());
		endereco.setLogradouro(EEndereco.values()[random.nextInt(EEndereco.values().length - 1)]);
		endereco.setVia(num[random.nextInt(num.length - 1)] + "de " + dat[random.nextInt(dat.length - 1)]);
		endereco.setNumero(random.nextInt(100));
		endereco.setComplemento("não informado");
		endereco.setCidade(cidades.get(random.nextInt(cidades.size() - 1)));
		endereco.setEstado("SC");
		endereco.setCep(cep);
		return endereco;
	}

	public static List<Modalidade> criaModalidade() throws ModalidadeException, IOException {
		List<String> idModalidade = LeTxt.getLista("/populadorModalidades.txt");
		List<Modalidade> modalidadesNM = new ArrayList<Modalidade>();
		Modalidade modalidade = new Modalidade();

		for (String idMod : idModalidade) {
			modalidade = new Modalidade();
			String semana = "";
			for (int i = 0; i < 7; i++) {
				semana += (random.nextBoolean() ? "s" : "n");
			}
			modalidade.setIdModalidade(idMod);
			modalidade.setMinimoParticipantes(random.nextInt(9));
			modalidade.setSemana(semana);
			modalidadesNM.add(modalidade);
		}
		return modalidadesNM;
	}

	public static Turma criaTurma(int id, Modalidade modalidade)
			throws TurmaException, ConexaoException, SQLException, DAOException {
		Map<String, Funcionario> funcio = new FuncionarioDAO().consultaTodos();
		List<Funcionario> listaFunc = new ArrayList<Funcionario>();
		Map<String, Aluno> alunos = new AlunoDAO().consultaTodos();
		List<Aluno> listaAlunos = new ArrayList<Aluno>();
		listaAlunos.addAll(alunos.values());
		listaFunc.addAll(funcio.values());
		Participantes participantes = new Participantes(listaAlunos, listaFunc);
		Turma turma = new Turma();
		turma.setIdTurma(id++);
		turma.setModalidade(modalidade);
		turma.setDuracao(15 * random.nextInt(6));
		turma.setHorarioInicio(LocalTime.of(random.nextInt(23), random.nextInt(59)));
		turma.setParticipantes(participantes);

		return turma;
	}

	public static List<Usuario> criaUsuarios() throws ConexaoException, SQLException, DAOException, PessoaException {
		List<Usuario> usuarios = new ArrayList<Usuario>();
		Map<String, Aluno> alunos = new AlunoDAO().consultaTodos();
		Map<String, Funcionario> funcionarios = new FuncionarioDAO().consultaTodos();
		for (Map.Entry<String, Aluno> pessoa : alunos.entrySet()) {
			usuarios.add(new Usuario(pessoa.getValue().getNome().trim(), "13245", pessoa.getKey(), 2));
		}
		for (Map.Entry<String, Funcionario> pessoa : funcionarios.entrySet()) {
			usuarios.add(new Usuario(pessoa.getValue().getNome().trim(), "13245", pessoa.getKey(), 1));
		}
		return usuarios;
	}

	public static List<Exercicio> criaExericios() throws ExercicioException, IOException {
		List<Exercicio> exercicios = new ArrayList<Exercicio>();
		for (String exercicio : LeTxt.getLista("/populadorExercicios.txt")) {
			try {
				exercicios.add(
					new Exercicio(exercicio,random.nextInt(9),random.nextInt(20) + 10, 
										   random.nextInt(50) + 50, random.nextInt(9)));
			} catch (ExercicioException e) {
			}
		}
		return exercicios;
	}
}
