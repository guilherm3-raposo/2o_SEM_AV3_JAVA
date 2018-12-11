package edu.senai.integrador.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.senai.integrador.bancodedados.conexao.Conexao;
import edu.senai.integrador.bancodedados.conexao.ConexaoException;
import edu.senai.integrador.beans.academia.Modalidade;
import edu.senai.integrador.beans.academia.ModalidadeException;
import edu.senai.integrador.dao.sql.ColunasModalidade;
import edu.senai.integrador.dao.sql.ComandosModalidade;
import edu.senai.integrador.dao.sql.SqlSintaxe;
import edu.senai.integrador.dao.sql.SqlTabelas;
import edu.senai.integrador.logs.GeraLog;

public class ModalidadeDAO implements ICRUDPadraoDAO<Modalidade, String> {
	SqlTabelas tabelas = new SqlTabelas();
	SqlSintaxe sq = new SqlSintaxe();
	ColunasModalidade colunas = new ColunasModalidade();
	ComandosModalidade comandos = new ComandosModalidade();

	
	private Modalidade constroiModalidade(ResultSet rs) throws ModalidadeException {
		Modalidade modalidade = new Modalidade();
		try {
			modalidade = new Modalidade(rs.getString(colunas.ID_MODALI), 
										rs.getString(colunas.SEMANA),
										rs.getInt(colunas.MIN_PARTIC));
		} catch (SQLException e) {
			new GeraLog().escreveLogArquivo(e);
		}
		return modalidade;
	}
	
	private String constroiInsert (Modalidade modalidade) throws ModalidadeException{
		return 	 sq.INSERT + 
			     sq.INTO +
		    tabelas.MODALIDADE + sq.OPEN_PAR + 
		    colunas.ID_MODALI + sq.COMMA +
		    colunas.SEMANA + sq.COMMA +
		    colunas.MIN_PARTIC + sq.CLOSE_PAR + " " +
		  	     sq.VALUES + sq.OPEN_PAR + sq.VARCHAR + 
		 modalidade.getIdModalidade() + sq.VARCHAR + sq.COMMA + sq.VARCHAR + 
		 modalidade.getSemana() + sq.VARCHAR + sq.COMMA +
		 modalidade.getMinimoParticipantes() + sq.CLOSE_PAR + sq.SEMI_COLON;
	}
	
	private String constroiUpdate(Modalidade modalidade) throws ModalidadeException {
		return 	sq.UPDATE +
		   tabelas.MODALIDADE +
				sq.SET +
	       colunas.SEMANA 	  			+ sq.EQUALS + sq.VARCHAR + 
	    modalidade.getSemana() 			+ sq.VARCHAR + sq.COMMA +
		   colunas.MIN_PARTIC + 
		        sq.EQUALS + 
		modalidade.getMinimoParticipantes()  + 
				sq.WHERE + 
		   colunas.ID_MODALI + 
		   		sq.EQUALS + sq.VARCHAR + 
		   		   modalidade.getIdModalidade() + sq.VARCHAR + sq.SEMI_COLON;
	}

	@Override
	public Modalidade consulta(String modalidade) throws ConexaoException, DAOException, SQLException {
		Connection conexao = Conexao.abreConexao();
		try {
			Statement st = conexao.createStatement();
			String sqlSt = sq.SELECT +
						   sq.ALL + 
						   sq.FROM + 
					  tabelas.MODALIDADE +
					  	   sq.WHERE + 
					  colunas.ID_MODALI + 
					  	   sq.EQUALS + sq.VARCHAR +
					  	      modalidade + sq.VARCHAR + sq.SEMI_COLON;
			ResultSet rs = st.executeQuery(sqlSt);
			return rs.first() ? constroiModalidade(rs) : null;
		} catch (ModalidadeException | SQLException e) {
			throw new DAOException(EDAOErros.CONSULTA_DADO);
		}finally {
			Conexao.fechaConexao();
		}
		
	}

	@Override
	public Map<String, Modalidade> consultaTodos() throws ConexaoException, DAOException, SQLException {
		Connection conexao = Conexao.abreConexao();
		Map<String, Modalidade> modalidades = new HashMap<String, Modalidade>();
		try {
			Statement st = conexao.createStatement();
			ResultSet rs = st.executeQuery(sq.SELECT +
										   sq.ALL +
										   sq.FROM +
									  tabelas.MODALIDADE);
			while (rs.next()) {
				modalidades.put(rs.getString(colunas.ID_MODALI.toString()), constroiModalidade(rs));
			}
			return modalidades;
		} catch (ModalidadeException | SQLException e) {
			throw new DAOException(EDAOErros.CONSULTA_DADO);
		} finally {
			Conexao.fechaConexao();
		}
	}

	@Override
	public List<Modalidade> consultaFaixa(String... codigos) throws ConexaoException, DAOException, SQLException {
		List<Modalidade> modalidades = new ArrayList<Modalidade>();
		for (String i : codigos) {
			modalidades.add(consulta(i));
		}
		return modalidades;
	}

	@Override
	public boolean insere(Modalidade modalidade) throws ConexaoException, DAOException, SQLException {
		Connection conexao = Conexao.abreConexao();
		try {
			Statement st = conexao.createStatement();
			st.execute(constroiInsert(modalidade));
		} catch (ModalidadeException | SQLException e) {
			new GeraLog().escreveLogArquivo(e);
		} finally {
			Conexao.fechaConexao();
		}
		return true;
	}
	
	@Override
	public List<Modalidade> insereVarios(Map<String, Modalidade> modalidades) throws ConexaoException, DAOException, SQLException {
		Connection conexao = Conexao.abreConexao();
		List<Modalidade> naoInseridos = new ArrayList<Modalidade>();
		try {
			modalidades.forEach((id,modalidade) -> {
				Modalidade naoInserido = new Modalidade();
				Statement st;
				try {
					st = conexao.createStatement();
					String insert = constroiInsert(modalidade);
					st.execute(insert);
					naoInserido = modalidade;
				} catch (ModalidadeException | SQLException e) {
					naoInseridos.add(naoInserido);
				}
			});
		} catch (Exception e) {
			throw new DAOException(EDAOErros.INSERE_DADO);
		} finally {
			Conexao.fechaConexao();
		}
		return naoInseridos;
	}
	
	
	@Override
	public List<Modalidade> insereVarios(List<Modalidade> modalidades) throws ConexaoException, DAOException, SQLException {
		Connection conexao = Conexao.abreConexao();
		Modalidade naoInserido = new Modalidade();
		List<Modalidade> naoInseridos = new ArrayList<Modalidade>();
		try {
			for (Modalidade modalidade : modalidades) {
				Statement st = conexao.createStatement();
				st.execute(constroiInsert(modalidade));
				naoInserido = modalidade;
			}
		} catch (ModalidadeException | SQLException e) {
			naoInseridos.add(naoInserido);
			throw new DAOException(EDAOErros.INSERE_DADO);
		} finally {
			Conexao.fechaConexao();
		}
		return naoInseridos;
	}

	@Override
	public boolean altera(Modalidade modalidade) throws ConexaoException, DAOException, SQLException {
		Connection conexao = Conexao.abreConexao();
		try {
			String update = constroiUpdate(modalidade);
			Statement st = conexao.createStatement();
			st.execute(update);		
		} catch (ModalidadeException | SQLException e) {
			throw new DAOException(EDAOErros.INSERE_DADO);
		} finally {
			Conexao.fechaConexao();
		}
		return true;
	}

	@Override
	public boolean exclui(String modalidades) throws ConexaoException, DAOException, SQLException {
			Connection conexao = Conexao.abreConexao();
			try {
				Statement st = conexao.createStatement();
				st.execute(sq.DELETE + 
						   sq.FROM + 
					  tabelas.MODALIDADE + 
						   sq.WHERE + 
					  colunas.ID_MODALI + 
						   sq.EQUALS + sq.VARCHAR+ 
						      modalidades + sq.VARCHAR + sq.SEMI_COLON);
			} catch (SQLException e) {
				throw new DAOException(EDAOErros.INSERE_DADO);
			} finally {
				Conexao.fechaConexao();
			}
		return true;

	}
	
	public boolean exclui(Modalidade modalidades) throws ConexaoException, DAOException, SQLException {
		try {
			exclui(modalidades.getIdModalidade());
		} catch (ModalidadeException e) {
		}
		return true;

	}
}
