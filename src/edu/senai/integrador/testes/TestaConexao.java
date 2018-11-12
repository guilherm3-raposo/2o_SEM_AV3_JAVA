package edu.senai.integrador.testes;

import java.sql.SQLException;

import edu.senai.integrador.bancodedados.conexao.Conexao;
import edu.senai.integrador.bancodedados.conexao.ConexaoException;
import edu.senai.integrador.dao.DAOException;

public class TestaConexao {
	public static void main(String[] args) throws SQLException, ConexaoException, DAOException {
//		System.out.println("Inicio");
//		try {
//			System.out.println("Abrindo...");
//			Conexao.abreConexao();
//		} catch (ConexaoException e) {
//			System.out.println(e.getMessage());
//		}
//		try {
//			System.out.println("Fechando...");
//			Conexao.fechaConexao();
//			System.out.println("Sucesso...");
//		} catch (ConexaoException e) {
//			System.out.println(e.getMessage());
//		}
//		System.out.println("Fim");
//		TurmaDAO turmaDAO = new TurmaDAO();
//		Map<Integer, Turma> turmas = new HashMap<>();
//		turmas = turmaDAO.consultaTodos();
//		turmas.forEach((id,turma) -> System.out.println(turma.toString()));
//		Turma turma = turmaDAO.consulta(4);
//		System.out.println(turma);
//		AlunoDAO alunoDAO = new AlunoDAO();
//		Aluno aluno	= alunoDAO.consulta("99988877700");
//		alunoDAO.altera(aluno);
//		System.out.println(LocalDateTime.now());
//		ModalidadeDao modalidadeDao = new ModalidadeDao();
//		Modalidade modalidade = modalidadeDao.consulta("spinning");
//		System.out.println(modalidade.getIdModalidade());
//		Map<String, Modalidade> modalidade = new HashMap<>();
//		modalidade = modalidadeDao.consultaTodos();
//		System.out.println(modalidade.get("spinning").getIdModalidade());
//		ParticipantesDAO participantesDAO = new ParticipantesDAO();
//		List<Participantes> participantes = participantesDAO.consulta((long) 1);
//		ParticipantesDAO participantesDAO = new ParticipantesDAO();
//		System.out.println(participantesDAO.consultaAlunos(6).get("99988877705").toString());
		System.out.println(Conexao.getConexao().getAutoCommit());
	}
}
