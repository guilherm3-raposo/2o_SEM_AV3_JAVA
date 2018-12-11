package edu.senai.integrador.dao;

import java.sql.Connection;
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
import edu.senai.integrador.beans.academia.ModalidadeException;
import edu.senai.integrador.beans.academia.Turma;
import edu.senai.integrador.beans.academia.TurmaException;
import edu.senai.integrador.dao.sql.ColunasModalidade;
import edu.senai.integrador.dao.sql.ColunasParticipantes;
import edu.senai.integrador.dao.sql.ColunasTurma;
import edu.senai.integrador.dao.sql.ComandosTurma;
import edu.senai.integrador.dao.sql.SqlSintaxe;
import edu.senai.integrador.dao.sql.SqlTabelas;
import edu.senai.integrador.logs.GeraLog;

public class TurmaDAO implements ICRUDPadraoDAO<Turma, Integer> {
	SqlSintaxe sq = new SqlSintaxe();
	SqlTabelas tabelas = new SqlTabelas();
	ColunasTurma colunas = new ColunasTurma();
	ColunasModalidade colMod = new ColunasModalidade();
	ColunasParticipantes colPart = new ColunasParticipantes();
	ComandosTurma comandos = new ComandosTurma();
	
	
	public Turma constroiTurma(ResultSet rs) throws TurmaException {
		try {
			Turma turma = new Turma();
			if(rs.first()) {
				turma.setIdTurma(rs.getInt(colunas.ID_TURMA));
				turma.setHorarioInicio(LocalTime.parse(rs.getString(colunas.HORA_INICIO), DateTimeFormatter.ofPattern("HH:mm:ss")));
				turma.setModalidade(new ModalidadeDAO().consulta(rs.getString(colMod.ID_MODALI)));
				turma.setParticipantes(new ParticipantesDAO().consultaParticipantes(rs.getInt(colunas.ID_TURMA)));
			}
			return turma;	
		} catch (SQLException | ConexaoException | DAOException e) {
			new GeraLog().escreveLogArquivo(e);
		}
		return null;
	}
	
	public String constroiInsert (Turma turma) throws TurmaException, ModalidadeException{
		String insert = sq.INSERT 				+ 
						sq.INTO 				+ 
				   tabelas.TURMA 				+ sq.OPEN_PAR +
				   colunas.ID_TURMA				+ sq.COMMA +
				   colunas.ID_MODALIDADE		+ sq.COMMA +
				   colunas.HORA_INICIO			+ sq.COMMA +
				   colunas.DURACAO				+ sq.CLOSE_PAR + " " +
				   		sq.VALUES 				+ sq.OPEN_PAR + 
					 turma.getIdTurma() 		+ sq.COMMA + sq.VARCHAR + 
	 turma.getModalidade().getIdModalidade() 	+ sq.VARCHAR + sq.COMMA + sq.VARCHAR + 
	 				 turma.getHorarioInicio()   .format(DateTimeFormatter.ofPattern("HH:mm:ss")) + sq.VARCHAR + sq.COMMA + 
	 				 turma.getDuracao() 		+ sq.CLOSE_PAR + sq.SEMI_COLON;
		return insert;
	}
	
	@Override
	public Turma consulta(Integer codigo) throws ConexaoException, DAOException, SQLException {
		Connection conexao = Conexao.abreConexao();
		try {
			Statement st = conexao.createStatement();
			ResultSet rs = st.executeQuery(sq.SELECT + 
										   sq.ALL + 
										   sq.FROM +
									  tabelas.TURMA + 
										   sq.WHERE +
									  colunas.ID_TURMA + 
									       sq.EQUALS +
									     	  codigo + sq.SEMI_COLON);
			return constroiTurma(rs);
		} catch (Exception e) {
			throw new DAOException(EDAOErros.CONSULTA_DADO);
		} finally {
			Conexao.fechaConexao();
		}
	}

	@Override
	public Map<Integer, Turma> consultaTodos() throws ConexaoException, DAOException, SQLException {
		Connection conexao = Conexao.abreConexao();
		Map<Integer, Turma> turmas = new HashMap<Integer, Turma>();
		
		try {
			Statement st = conexao.createStatement();
			ResultSet rs = st.executeQuery(sq.SELECT + 
										   sq.ALL +
										   sq.FROM + 
									  tabelas.TURMA+ sq.SEMI_COLON);
			while (rs.next()) {
				turmas.put(rs.getInt(colunas.ID_TURMA), consulta(rs.getInt(colunas.ID_TURMA)));
			}
			return turmas;
		} catch (Exception e) {
			throw new DAOException(EDAOErros.CONSULTA_DADO);
		} finally {
			Conexao.fechaConexao();
		}
	}

	@Override
	public List<Turma> consultaFaixa(Integer... faixa) throws ConexaoException, DAOException, SQLException {
		List<Turma> turmas = new ArrayList<Turma>();
		for (int i : faixa) {
			turmas.add(consulta(i));
		}
		return turmas;
	}

	@Override
	public boolean insere(Turma turma) throws ConexaoException, DAOException, SQLException {
		Connection conexao = Conexao.abreConexao();
		try {
			ModalidadeDAO modalidadeDao = new ModalidadeDAO();
			modalidadeDao.insere(turma.getModalidade());
			Conexao.abreConexao();
			Statement st = conexao.createStatement();
			String insert = constroiInsert(turma);
			st.execute(insert);
			return true;
		} catch (DAOException | TurmaException | ModalidadeException e) {
			throw new DAOException(EDAOErros.INSERE_DADO);
		} finally {
			Conexao.fechaConexao();
		}
	}

	@Override
	public List<Turma> insereVarios(List<Turma> turmas) throws ConexaoException, DAOException, SQLException {
		Connection conexao = Conexao.abreConexao();
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
		} catch (TurmaException | ModalidadeException e) {
			new GeraLog().escreveLogArquivo(e);
		}
		return naoInseridos;
	}

	@Override
	public List<Turma> insereVarios(Map<Integer, Turma> turmas) throws ConexaoException, DAOException, SQLException {
		Connection conexao = Conexao.abreConexao();
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
				} catch (TurmaException | ModalidadeException e) {
					new GeraLog().escreveLogArquivo(e);
				}
			});
		} catch (SQLException e) {
			new GeraLog().escreveLogArquivo(e);
		} finally {
			Conexao.fechaConexao();
		}
		return naoInseridos;
	}

	@Override
	public boolean altera(Turma turma) throws ConexaoException, DAOException, SQLException {
		Connection conexao = Conexao.abreConexao();
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
		} catch (SQLException | TurmaException | ModalidadeException e) {
			throw new DAOException(EDAOErros.ALTERA_DADO);
		} finally {
			Conexao.fechaConexao();
		}
	}

	@Override
	public boolean exclui(Integer codigo) throws ConexaoException, DAOException, SQLException {
		if (consulta(codigo) instanceof Turma) {
			Connection conexao = Conexao.abreConexao();
			try {
				Statement st = conexao.createStatement();
				st.execute(sq.DELETE +
						   sq.FROM +
					  tabelas.TURMA +
					  	   sq.WHERE + 
					  colunas.ID_TURMA +
					  	   sq.EQUALS +
					  	      codigo + sq.SEMI_COLON);
			} catch (SQLException e) {
				throw new DAOException(EDAOErros.EXCLUI_DADO);
			} finally {
				Conexao.fechaConexao();
			}
		}
		return true;
	}

//	@Override
	public boolean exclui(Turma turma) throws ConexaoException, DAOException, SQLException {
		try {
			return exclui(turma.getIdTurma());
		} catch (TurmaException e) {
			new GeraLog().escreveLogArquivo(e);
		}
		return false;
	}
}
