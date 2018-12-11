package edu.senai.integrador.z.populadores;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.senai.integrador.bancodedados.conexao.ConexaoException;
import edu.senai.integrador.beans.academia.ExercicioException;
import edu.senai.integrador.beans.academia.Modalidade;
import edu.senai.integrador.beans.academia.ModalidadeException;
import edu.senai.integrador.beans.academia.Turma;
import edu.senai.integrador.beans.academia.TurmaException;
import edu.senai.integrador.beans.cadastro.Contato;
import edu.senai.integrador.beans.cadastro.ContatoException;
import edu.senai.integrador.beans.cadastro.Endereco;
import edu.senai.integrador.beans.cadastro.EnderecoException;
import edu.senai.integrador.beans.pessoa.Aluno;
import edu.senai.integrador.beans.pessoa.Funcionario;
import edu.senai.integrador.beans.pessoa.PessoaException;
import edu.senai.integrador.dao.AlunoDAO;
import edu.senai.integrador.dao.ContatoDAO;
import edu.senai.integrador.dao.DAOException;
import edu.senai.integrador.dao.EnderecoDAO;
import edu.senai.integrador.dao.ExercicioDAO;
import edu.senai.integrador.dao.FuncionarioDAO;
import edu.senai.integrador.dao.ModalidadeDAO;
import edu.senai.integrador.dao.ParticipantesDAO;
import edu.senai.integrador.dao.TurmaDAO;
import edu.senai.integrador.dao.UsuarioDAO;

public class Populador {
	private static AlunoDAO alunoDAO = new AlunoDAO();
	private static Aluno aluno;
	private static FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
	private static Funcionario funcionario;
	private static ModalidadeDAO modalidadeDAO = new ModalidadeDAO();
	private static TurmaDAO turmaDAO = new TurmaDAO();
	private static Turma turma;
	private static UsuarioDAO usuarioDAO = new UsuarioDAO();
	private static ParticipantesDAO participantesDAO = new ParticipantesDAO();
	private static ContatoDAO contatoDAO = new ContatoDAO();
	private static EnderecoDAO enderecoDAO = new EnderecoDAO();
	private static int numeroRegistros;
	private static int j = 0;
	private static Contato contato;
	private static Endereco endereco;

	public static boolean populaBase(int numeroCriados)
			throws ConexaoException, DAOException, SQLException, PessoaException, ContatoException, EnderecoException,
			ModalidadeException, TurmaException, ExercicioException, IOException {
		numeroRegistros = numeroCriados;

		criaExercicio();
		criaAlunos();
		criaFuncionarios();
		criaUsuarios();
		criaTurmas();
		return true;
	}

	public static boolean criaTurmas()
			throws ModalidadeException, ConexaoException, DAOException, SQLException, TurmaException, PessoaException, IOException {
		List<Modalidade> modali = Instanciador.criaModalidade();
		List<Turma> turmas = new ArrayList<Turma>();
		List<String> idsAlu;
		List<String> idsFun;
		for (j = 0; j < numeroRegistros; j++) {
			int k = 1;
			for (Modalidade modal : modali) {
				modalidadeDAO.insere(modal);
			}
			for (Modalidade modal : modali) {
				turma = Instanciador.criaTurma(k, modal);
				turmaDAO.insere(turma);
				idsAlu = new ArrayList<String>();
				for (Aluno aluno : turma.getParticipantes().getParticipantes()) {
					idsAlu.add(aluno.getCPF());
				}
				idsFun = new ArrayList<String>();
				for (Funcionario funcionario : turma.getParticipantes().getMinistrantes()) {
					idsFun.add(funcionario.getCPF());
				}
				k++;
				turmas.add(turma);
				participantesDAO.insereParticipantes(turma.getIdTurma(), idsFun, idsAlu);
			}
		}
		return true;

	}

	public static boolean criaFuncionarios()
			throws ContatoException, PessoaException, ConexaoException, DAOException, SQLException, EnderecoException, IOException {
		for (j = 0; j < numeroRegistros; j++) {
			funcionario = Instanciador.criaFuncionario();
			contato = Instanciador.criaContato(funcionario);
			endereco = Instanciador.criaEndereco(funcionario);
			if (funcionarioDAO.insere(funcionario)) {
				contatoDAO.insere(contato);
				enderecoDAO.insere(endereco);
			}
		}
		return true;
	}

	public static boolean criaAlunos()
			throws ConexaoException, DAOException, SQLException, PessoaException, ContatoException, EnderecoException, IOException {
		for (j = 0; j < numeroRegistros; j++) {
			aluno = Instanciador.criaAluno();
			contato = Instanciador.criaContato(aluno);
			endereco = Instanciador.criaEndereco(aluno);

			if (alunoDAO.insere(aluno)) {
				contatoDAO.insere(contato);
				enderecoDAO.insere(endereco);
			}
		}
		return true;
	}

	public static boolean criaUsuarios() throws ConexaoException, SQLException, DAOException, PessoaException {
		for (int j = 0; j < numeroRegistros; j++) {
			Instanciador.criaUsuarios().forEach(usuario -> {
				try {
					usuarioDAO.insere(usuario);
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			;
		}
		return true;
	}

	public static boolean criaExercicio() throws ConexaoException, DAOException, SQLException, ExercicioException, IOException {
		ExercicioDAO exercicioDAO = new ExercicioDAO();
		exercicioDAO.insereVarios(Instanciador.criaExericios());
		return true;
	}
}
