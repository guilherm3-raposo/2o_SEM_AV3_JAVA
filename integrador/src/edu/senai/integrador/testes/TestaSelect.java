package edu.senai.integrador.testes;

import edu.senai.integrador.bancodedados.conexao.ConexaoException;
import edu.senai.integrador.beans.exception.AlunoException;
import edu.senai.integrador.beans.exception.FuncionarioException;
import edu.senai.integrador.beans.exception.PessoaException;
import edu.senai.integrador.dao.DAOException;

public class TestaSelect {
	public static void main(String[] args)
			throws PessoaException, ConexaoException, DAOException, AlunoException, FuncionarioException {
//		Aluno xanaina = new Aluno("99988877766", "Xanaina", LocalDate.of(1999, 12, 01), ESexo.FEMININO, 1.6, 48,
//				EEstadoCivil.SOLTEIRO);
//		
//		Aluno aluno = new AlunoDAO().consulta("99988877701");
//		System.out.println(aluno.toString());
//		
//		
//		Map<Integer, Aluno> alunos = new HashMap<Integer, Aluno>(new AlunoDAO().consultaTodos());
//
//		for (int i = 0; i < alunos.size(); i++) {
//			System.out.println(alunos.get(i).toString() + "\n\n");
//		}
//		AlunoDAO alunoDAO = new AlunoDAO();
//		alunoDAO.insere(xanaina);
//		TurmaDAO turmaDAO = new TurmaDAO();
//		Map<Long, Turma> turmas = new HashMap<Long, Turma>();
//		turmas = turmaDAO.consultaTodos();
//		Turma turma = turmas.get(1);
//		Aluno aluno = turma.getParticipantes().get(99988877700L);
//		System.out.println(aluno.toString());
	}
}
