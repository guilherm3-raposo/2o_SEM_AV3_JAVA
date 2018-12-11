package edu.senai.integrador.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.senai.integrador.bancodedados.conexao.Conexao;
import edu.senai.integrador.bancodedados.conexao.ConexaoException;
import edu.senai.integrador.beans.academia.Participantes;
import edu.senai.integrador.beans.pessoa.Aluno;
import edu.senai.integrador.beans.pessoa.Funcionario;
import edu.senai.integrador.dao.sql.ColunasParticipantes;
import edu.senai.integrador.dao.sql.SqlSintaxe;
import edu.senai.integrador.dao.sql.SqlTabelas;

public class ParticipantesDAO {
	SqlSintaxe sq = new SqlSintaxe();
	SqlTabelas tabelas = new SqlTabelas();
	ColunasParticipantes colunas = new ColunasParticipantes();
	
	private Participantes constroiParticipantes(ResultSet rs) throws SQLException, DAOException {
		FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
		AlunoDAO alunoDAO = new AlunoDAO();
		List<Aluno> alunos = new ArrayList<Aluno>();
		List<Funcionario> funcionarios = new ArrayList<Funcionario>();
		
		while (rs.next()) {
			try {
				if (rs.getString(colunas.ID_FUNCI) != null) 
					funcionarios.add(funcionarioDAO.consulta(rs.getString(colunas.ID_FUNCI)));
				if (rs.getString(colunas.ID_ALUNO) != null) 
					alunos.add(alunoDAO.consulta(rs.getString(colunas.ID_ALUNO)));
			} catch (ConexaoException e) {
				throw new DAOException(EDAOErros.CONSULTA_DADO);
			}
		}
		
		return new Participantes(alunos, funcionarios);
	}
	
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

	public List<String> constroiUpdate(int idTurma, List<String> pessoas, boolean aluno) {
		List<String> update = new ArrayList<String>();
		
		pessoas.forEach(cpf -> {
			update.add(sq.UPDATE + 
				  tabelas.PARTICIPANTES + 
				  	   sq.SET + 
		 (aluno ? colunas.ID_ALUNO 
				: colunas.ID_FUNCI) + 
				  	   sq.EQUALS + sq.VARCHAR +
				  	      cpf + sq.VARCHAR + " " +
				  	   sq.WHERE + 
				  colunas.ID_TURMA + 
				  	   sq.EQUALS + 
				  	      idTurma+ sq.SEMI_COLON);
		});
		return update;
	}

	public String constroiDelete (String cpf, int idTurma, boolean aluno) {
		return sq.DELETE + 
				   sq.FROM +
			  tabelas.PARTICIPANTES + 
			  	   sq.WHERE +
	 (aluno ? colunas.ID_ALUNO : colunas.ID_FUNCI) + 
				   sq.EQUALS + sq.VARCHAR +
				      cpf + sq.VARCHAR + sq.AND +
			  colunas.ID_TURMA + 
				   sq.EQUALS +
				   	  idTurma + sq.SEMI_COLON;
	}
	public Participantes consultaParticipantes (int idTurma) throws SQLException, ConexaoException, DAOException {
		Connection conexao = Conexao.abreConexao();
		Statement st = conexao.createStatement();
		ResultSet rs = st.executeQuery(sq.SELECT + 
									   sq.ALL + 
									   sq.FROM + 
								  tabelas.PARTICIPANTES + 
								   	   sq.WHERE + 
								  colunas.ID_TURMA + 
								   	   sq.EQUALS + 
								   		  idTurma + sq.SEMI_COLON);
		return constroiParticipantes(rs);
	}

	public boolean insereParticipantes(int idTurma, List<String> funcionarios, List<String> alunos) throws ConexaoException, SQLException {
		Connection conexao = Conexao.abreConexao();
		Statement st = conexao.createStatement();
		List<String> insertList = new ArrayList<String>();
		if(funcionarios!=null) {
			insertList = constroiInsert(idTurma, funcionarios, false);
			for (String insert : insertList) {
				st.execute(insert);
			}
		}
		if(alunos!=null) {
			insertList = constroiInsert(idTurma, alunos, true);
			for (String insert : insertList) {
				st.execute(insert);
			}
		}
		return true;
	}
	
	public boolean alteraParticipantes(int idTurma, List<String> funcionarios, List<String> alunos) throws SQLException, ConexaoException {
		Connection conexao = Conexao.abreConexao();
		Statement st = conexao.createStatement();
		List<String> updateList = new ArrayList<String>();
		if(funcionarios!=null) {
			updateList = constroiUpdate(idTurma, funcionarios, false);
			for (String update : updateList) {
				st.execute(update);
			}
		}
		if(alunos!=null) {
			updateList = constroiUpdate(idTurma, alunos, true);
			for (String update : updateList) {
				st.execute(update);
			}
		}
		return true;
	}
	
	public boolean exclui(int idTurma, String cpfAluno, String cpfFuncionario) throws ConexaoException, SQLException {
		Connection conexao = Conexao.abreConexao();
		Statement st = conexao.createStatement();
		if(cpfAluno != null) {
			st.execute(constroiDelete(cpfAluno, idTurma, true));
		}
		if(cpfFuncionario != null) {
			st.execute(constroiDelete(cpfFuncionario, idTurma, false));
		}
		return false;
	}
}
