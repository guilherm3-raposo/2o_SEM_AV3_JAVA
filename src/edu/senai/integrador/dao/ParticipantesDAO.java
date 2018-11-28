package edu.senai.integrador.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.senai.integrador.bancodedados.conexao.Conexao;
import edu.senai.integrador.bancodedados.conexao.ConexaoException;
import edu.senai.integrador.beans.Aluno;
import edu.senai.integrador.beans.Funcionario;
import edu.senai.integrador.dao.sql.ColunasParticipantes;
import edu.senai.integrador.dao.sql.SqlSintaxe;
import edu.senai.integrador.dao.sql.SqlTabelas;

public class ParticipantesDAO {
	SqlSintaxe sq = new SqlSintaxe();
	SqlTabelas tabelas = new SqlTabelas();
	ColunasParticipantes colunas = new ColunasParticipantes();
	
	private List<String> constroiInsert(int idTurma, List<String> pessoas, boolean aluno) {
		List<String> insert = new ArrayList<String>();
		
		pessoas.forEach(cpf -> {
			insert.add(sq.INSERT + 
					   sq.INTO + 
				  tabelas.PARTICIPANTES + " " + sq.OPEN_PAR + 
				  colunas.ID_TURMA + sq.COMMA + 
		 (aluno ? colunas.ID_ALUNO : colunas.ID_FUNCI) + sq.CLOSE_PAR + " " +
				  	   sq.VALUES +
		    sq.OPEN_PAR + idTurma + sq.COMMA +
			 sq.VARCHAR + cpf + sq.VARCHAR + sq.CLOSE_PAR + sq.SEMI_COLON);
		});
		return insert;
	}

	public Map<String, Funcionario> consultaMinistrantes(Map<String, Funcionario> ministrantes)
			throws ConexaoException, DAOException {

		FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
		ministrantes.forEach((cpf, ministrante) -> {
			try {
				ministrantes.replace(cpf, funcionarioDAO.consulta(cpf));
			} catch (ConexaoException | DAOException e) {
			}
		});
		ministrantes.remove(null);
		return ministrantes;
	}
	
	public Map<String, Aluno> consultaParticipantes(Map<String, Aluno> participantes)
			throws ConexaoException, DAOException {
		
		AlunoDAO alunoDAO = new AlunoDAO();
		participantes.forEach((cpf, participante) -> {
			try {
				participantes.replace(cpf, alunoDAO.consulta(cpf));
			} catch (ConexaoException e) {
			}
		});
		participantes.remove(null);
		return participantes;
	}

	public boolean insereParticipantes(int idTurma, List<String> funcionarios, List<String> alunos) {
		try {
			Connection conexao = Conexao.abreConexao();
			Statement st = conexao.createStatement();
			List<String> insertList = constroiInsert(idTurma, funcionarios, false);
			for (String insert : insertList) {
				st.execute(insert);
			}
			insertList = constroiInsert(idTurma, alunos, true);
			for (String insert : insertList) {
				st.execute(insert);
			}
		} catch (ConexaoException e) {
			// TODO Auto-generated catch block
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		}
		return true;
	}
}
