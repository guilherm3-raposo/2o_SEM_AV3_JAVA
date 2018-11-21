package edu.senai.integrador.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.senai.integrador.bancodedados.conexao.Conexao;
import edu.senai.integrador.bancodedados.conexao.ConexaoException;
import edu.senai.integrador.beans.Aluno;
import edu.senai.integrador.beans.Funcionario;
import edu.senai.integrador.beans.Turma;
import edu.senai.integrador.dao.sql.ColunasModalidade;
import edu.senai.integrador.dao.sql.ColunasParticipantes;
import edu.senai.integrador.dao.sql.ColunasTurma;
import edu.senai.integrador.dao.sql.SqlComandos;
import edu.senai.integrador.dao.sql.SqlSintaxe;
import edu.senai.integrador.dao.sql.SqlTabelas;

public class TurmaDAO implements ICRUDPadraoDAO<Turma, Integer> {
	SqlSintaxe sq = new SqlSintaxe();
	SqlComandos comandos = new SqlComandos();
	SqlTabelas tabelas = new SqlTabelas();
	ColunasTurma colunas = new ColunasTurma();
	ColunasModalidade colMod = new ColunasModalidade();
	ColunasParticipantes colPart = new ColunasParticipantes();
	
	
	public Turma constroiTurma(ResultSet rs, int idTurma) {
		try {
			ModalidadeDao modalidadeDao = new ModalidadeDao();
			Map<String, Funcionario> ministrantes = new HashMap<String, Funcionario>();
			Map<String, Aluno> participantes = new HashMap<String, Aluno>();
			ParticipantesDAO participantesDAO = new ParticipantesDAO();
			Turma turma = new Turma();

			while(rs.next()) {
				turma.setIdTurma(idTurma);
				turma.setHorarioInicio(LocalTime.parse(rs.getString(colunas.HORA_INICIO), DateTimeFormatter.ofPattern("HH:mm:ss")));
				turma.setModalidade(modalidadeDao.consulta(rs.getString(colMod.ID_MODALI)));
				if(colPart.ID_ALUNO != null) participantes.put(rs.getString("CPFAluno"), null);
				if(colPart.ID_FUNCI != null) ministrantes.put(rs.getString("CPFFuncionario"), null);
			}
			turma.setMinistrantes(participantesDAO.consultaMinistrantes(ministrantes));
			turma.setParticipantes(participantesDAO.consultaParticipantes(participantes));
			
			return turma;	
		} catch (SQLException | ConexaoException | DAOException e) {
			// TODO Auto-generated catch block
		}
		return null;
	}
	
	private String constroiInsert (Turma turma){
		String insert = sq.INSERT + 
						sq.INTO + 
				   tabelas.TURMA + " " +
				   		sq.VALUES + sq.OPEN_PAR +
					 turma.getIdTurma() + sq.COMMA + sq.VARCHAR + 
	 turma.getModalidade().getIdModalidade() + sq.VARCHAR + sq.COMMA + sq.VARCHAR + 
	 				 turma.getHorarioInicio().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + sq.VARCHAR + sq.COMMA + 
	 				 turma.getDuracao() + sq.CLOSE_PAR + sq.SEMI_COLON;
		return insert;
	}
	
	@Override
	public Turma consulta(Integer codigo) throws ConexaoException, DAOException {
		Connection conexao = Conexao.getConexao();
		try {
			PreparedStatement pst = conexao.prepareStatement(comandos.SELECT_TURMA);
			pst.setInt(1, codigo);
			ResultSet rs = pst.executeQuery();
			return constroiTurma(rs, codigo);
		} catch (Exception e) {
			throw new DAOException(EDaoErros.CONSULTA_DADO, e.getMessage(), this.getClass());
		} finally {
			Conexao.fechaConexao();
		}
	}

	@Override
	public Map<Integer, Turma> consultaTodos() throws ConexaoException, DAOException {
		Connection conexao = Conexao.getConexao();
		Map<Integer, Turma> turmas = new HashMap<Integer, Turma>();
		
		try {
			Statement st = conexao.createStatement();
			ResultSet rs = st.executeQuery(comandos.SELECT_TURMAS);
			int idTurma = 1;
			while (rs.next()) {
				turmas.put(idTurma, consulta(idTurma));
				idTurma++;
			}
			return turmas;
		} catch (Exception e) {
			throw new DAOException(EDaoErros.CONSULTA_DADO, e.getMessage(), this.getClass());
		} finally {
			Conexao.fechaConexao();
		}
	}

	@Override
	public List<Turma> consultaFaixa(Integer... faixa) throws ConexaoException, DAOException {
		List<Turma> turmas = new ArrayList<Turma>();
		for (int i : faixa) {
			turmas.add(consulta(i));
		}
		return turmas;
	}

	@Override
	public boolean insere(Turma turma) throws ConexaoException, DAOException {
		Connection conexao = Conexao.getConexao();
		try {
			ModalidadeDao modalidadeDao = new ModalidadeDao();
			ParticipantesDAO participantesDAO = new ParticipantesDAO();
			modalidadeDao.insere(turma.getModalidade());
			Statement st = conexao.createStatement();
			String insert = constroiInsert(turma);
			st.execute(insert);
			List<String> funcionarios = new ArrayList<>();
			List<String> alunos = new ArrayList<>();
			turma.getMinistrantes().forEach((cpf, funcionario) -> funcionarios.add(cpf));
			turma.getParticipantes().forEach((cpf, aluno) -> alunos.add(cpf));
			participantesDAO.insereParticipantes(turma.getIdTurma(), funcionarios, alunos);
			return true;
		} catch (SQLException e) {
			throw new DAOException(EDaoErros.SQL_INVALIDO, e.getMessage(), this.getClass());
		} finally {
			Conexao.fechaConexao();
		}
	}

	@Override
	public List<Turma> insereVarios(List<Turma> turmas) throws ConexaoException, DAOException {
		Connection conexao = Conexao.getConexao();
		List<Turma> naoInseridos = new ArrayList<Turma>();
		Turma naoInserido = new Turma();
		try {
			Statement st = conexao.createStatement();
			for (Turma turma : turmas) {
				st.execute(constroiInsert(turma));
				naoInserido = turma;
			}
		} catch (SQLException e) {
			naoInseridos.add(naoInserido);
		}
		return naoInseridos;
	}

	@Override
	public List<Turma> insereVarios(Map<Integer, Turma> turmas) throws ConexaoException, DAOException {
		Connection conexao = Conexao.getConexao();
		List<Turma> naoInseridos = new ArrayList<Turma>();
		try {
			Statement st = conexao.createStatement();
			turmas.forEach((id, turma) -> {
				Turma naoInserido = new Turma();
				try {
					st.execute(constroiInsert(turma));
					naoInserido = turma;
				} catch (SQLException e) {
					naoInseridos.add(naoInserido);
				}
			});
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		} finally {
			Conexao.fechaConexao();
		}
		return naoInseridos;
	}

	@Override
	public boolean altera(Turma turma) throws ConexaoException, DAOException {
		Connection conexao = Conexao.getConexao();
		try {
			Statement st = conexao.createStatement();
			return st.execute(sq.UPDATE +
						 tabelas.TURMA +
						  	  sq.SET + 
					  	 colunas.ID_MODALIDADE + sq.EQUALS + 
			  sq.VARCHAR + turma.getModalidade().getIdModalidade() + sq.VARCHAR + sq.COMMA +
					  	 colunas.HORA_INICIO + sq.EQUALS + 
			  sq.VARCHAR + turma.getHorarioInicio() + sq.VARCHAR + sq.COMMA +
					 	 colunas.DURACAO + sq.EQUALS +
					 	   turma.getDuracao() + " " + 
					 	  	  sq.WHERE + 
					 	 colunas.ID_TURMA + sq.EQUALS + 
					 	   turma.getIdTurma() + sq.SEMI_COLON);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		}
		return false;
	}

	@Override
	public boolean exclui(Integer codigo) throws ConexaoException, DAOException {
		if (consulta(codigo) instanceof Turma) {
			Connection conexao = Conexao.getConexao();
			try {
				Statement st = conexao.createStatement();
				st.execute(sq.UPDATE +
					  tabelas.TURMA + 
						   sq.SET +
					  colunas.ATIVO + sq.EQUALS +
						 	  0 +
						   sq.WHERE +
					  colunas.ID_TURMA + sq.EQUALS + 
							  codigo + sq.SEMI_COLON);
			} catch (SQLException e) {
				throw new DAOException(EDaoErros.EXCLUI_DADO, e.getMessage(), this.getClass());
			} finally {
				Conexao.fechaConexao();
			}
		}
		return true;
	}

//	@Override
	public boolean exclui(Turma turma) throws ConexaoException, DAOException {
		return exclui(turma.getIdTurma());
	}
}
