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
import edu.senai.integrador.beans.Modalidade;
import edu.senai.integrador.dao.sql.ColunasModalidade;
import edu.senai.integrador.dao.sql.SqlComandos;
import edu.senai.integrador.dao.sql.SqlSintaxe;
import edu.senai.integrador.dao.sql.SqlTabelas;

public class ModalidadeDao implements ICRUDPadraoDAO<Modalidade, String> {
	SqlTabelas tabelas = new SqlTabelas();
	SqlSintaxe sq = new SqlSintaxe();
	SqlComandos comandos = new SqlComandos();
	ColunasModalidade colunas = new ColunasModalidade();

	private Modalidade constroiModalidade(ResultSet rs) {
		Modalidade modalidade = new Modalidade();
		try {
			modalidade = new Modalidade(rs.getString(colunas.ID_MODALI), 
										rs.getString(colunas.SEMANA),
										rs.getInt(colunas.MIN_PARTIC));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return modalidade;
	}
	
	private String constroiInsert (Modalidade modalidade){
		System.out.println(sq.INSERT + 
			     sq.INTO +
		    tabelas.MODALIDADE + sq.OPEN_PAR + 
		    colunas.ID_MODALI + sq.COMMA +
		    colunas.SEMANA + sq.COMMA +
		    colunas.MIN_PARTIC + sq.COMMA +
		    colunas.ATIVO +sq.CLOSE_PAR + " " +
		  	     sq.VALUES + sq.OPEN_PAR + sq.VARCHAR + 
		 modalidade.getIdModalidade() + sq.VARCHAR + sq.COMMA + sq.VARCHAR + 
		 modalidade.getSemana() + sq.VARCHAR + sq.COMMA +
		 modalidade.getMinimoParticipantes() + sq.COMMA +
		(modalidade.isAtivo()?1:0) + sq.CLOSE_PAR + sq.SEMI_COLON);
		
		return 	 sq.INSERT + 
			     sq.INTO +
		    tabelas.MODALIDADE + sq.OPEN_PAR + 
		    colunas.ID_MODALI + sq.COMMA +
		    colunas.SEMANA + sq.COMMA +
		    colunas.MIN_PARTIC + sq.COMMA +
		    colunas.ATIVO +sq.CLOSE_PAR + " " +
		  	     sq.VALUES + sq.OPEN_PAR + sq.VARCHAR + 
		 modalidade.getIdModalidade() + sq.VARCHAR + sq.COMMA + sq.VARCHAR + 
		 modalidade.getSemana() + sq.VARCHAR + sq.COMMA +
		 modalidade.getMinimoParticipantes() + sq.COMMA +
		(modalidade.isAtivo()?1:0) + sq.CLOSE_PAR + sq.SEMI_COLON;	
	}
	
	private String constroiUpdate(Modalidade modalidade) {
		return 	sq.UPDATE +
		   tabelas.MODALIDADE +
				sq.SET +
		   colunas.ID_MODALI  +	sq.EQUALS + sq.VARCHAR + modalidade.getIdModalidade() 		 + sq.VARCHAR + sq.COMMA +	
	       colunas.SEMANA 	  + sq.EQUALS + sq.VARCHAR + modalidade.getSemana() 			 + sq.VARCHAR + sq.COMMA +
		   colunas.MIN_PARTIC + sq.EQUALS + modalidade.getMinimoParticipantes() + sq.SEMI_COLON;
	}

	@Override
	public Modalidade consulta(String modalidade) throws ConexaoException, DAOException {
		Connection conexao = Conexao.abreConexao();
		try {
			Statement st = conexao.createStatement();
			String sqlSt = comandos.SELECT_MODALIDADE;
			ResultSet rs = st.executeQuery(sqlSt);
			return rs.first() ? constroiModalidade(rs) : null;
		} catch (SQLException e) {
			throw new DAOException(EDaoErros.CONSULTA_DADO, e.getMessage(), this.getClass());
		}finally {
			Conexao.fechaConexao();
		}
		
	}
	

	@Override
	public Map<String, Modalidade> consultaTodos() throws ConexaoException, DAOException {
		Connection conexao = Conexao.abreConexao();
		Map<String, Modalidade> modalidades = new HashMap<String, Modalidade>();
		try {
			Statement st = conexao.createStatement();
			ResultSet rs = st.executeQuery("select * from modalidade");
			while (rs.next()) {
				modalidades.put(rs.getString(colunas.ID_MODALI.toString()), constroiModalidade(rs));
			}
			return modalidades;
		} catch (SQLException e) {
			throw new DAOException(EDaoErros.CONSULTA_DADO, e.getMessage(), this.getClass());
		} finally {
			Conexao.fechaConexao();
		}
	}

	@Override
	public List<Modalidade> consultaFaixa(String... codigos) throws ConexaoException, DAOException {
		List<Modalidade> modalidades = new ArrayList<Modalidade>();
		for (String i : codigos) {
			modalidades.add(consulta(i));
		}
		return modalidades;
	}

	@Override
	public boolean insere(Modalidade modalidade) throws ConexaoException, DAOException {
		Connection conexao = Conexao.abreConexao();
		try {
			Statement st = conexao.createStatement();
			st.execute(constroiInsert(modalidade));
		} catch (SQLException e) {
			throw new DAOException(EDaoErros.CONSULTA_DADO, e.getMessage(), this.getClass());
		} finally {
			Conexao.fechaConexao();
		}
		return true;
	}
	
	@Override
	public List<Modalidade> insereVarios(Map<String, Modalidade> modalidades) throws ConexaoException, DAOException {
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
				} catch (SQLException e) {
					naoInseridos.add(naoInserido);
				}
			});
		} catch (Exception e) {
			throw new DAOException(EDaoErros.INSERE_DADO, e.getMessage(), this.getClass());
		} finally {
			Conexao.fechaConexao();
		}
		return naoInseridos;
	}
	
	
	@Override
	public List<Modalidade> insereVarios(List<Modalidade> modalidades) throws ConexaoException, DAOException {
		Connection conexao = Conexao.abreConexao();
		Modalidade naoInserido = new Modalidade();
		List<Modalidade> naoInseridos = new ArrayList<Modalidade>();
		try {
			for (Modalidade modalidade : modalidades) {
				Statement st = conexao.createStatement();
				st.execute(constroiInsert(modalidade));
				naoInserido = modalidade;
			}
		} catch (SQLException e) {
			naoInseridos.add(naoInserido);
			throw new DAOException(EDaoErros.INSERE_DADO, e.getMessage(), this.getClass());
		} finally {
			Conexao.fechaConexao();
		}
		return naoInseridos;
	}

	@Override
	public boolean altera(Modalidade modalidade) throws ConexaoException, DAOException {
		Connection conexao = Conexao.abreConexao();
		try {
			String update = constroiUpdate(modalidade);
			Statement st = conexao.createStatement();
			st.execute(update);		
		} catch (SQLException e) {
			throw new DAOException(EDaoErros.INSERE_DADO, e.getMessage(), this.getClass());
		} finally {
			Conexao.fechaConexao();
		}
		return true;
	}

	@Override
	public boolean exclui(String modalidades) throws ConexaoException, DAOException {
		if (consulta(modalidades) instanceof Modalidade) {
			Connection conexao = Conexao.abreConexao();
			try {
				Statement st = conexao.createStatement();
				st.execute(sq.DELETE);
			} catch (SQLException e) {
				throw new DAOException(EDaoErros.INSERE_DADO, e.getMessage(), this.getClass());
			} finally {
				Conexao.fechaConexao();
			}
		}
		return true;

	}
	 
	public static void main(String[] args) throws ConexaoException, DAOException {
		
		ModalidadeDao modalidadeDao = new ModalidadeDao();
		Modalidade modalidade = modalidadeDao.consulta("muaythay");
//		modalidadeDao.print(modalidade);
		modalidadeDao.insere(modalidade);
		System.out.println("done");
		
	}
}
