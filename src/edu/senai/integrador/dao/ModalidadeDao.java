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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return modalidade;
	}
	
	private String constroiInsert (Modalidade modalidade){
		return sq.INSERT + 
			   sq.INTO + 
		  tabelas.MODALIDADE + sq.OPEN_PAR + sq.VARCHAR +
		  colunas.ID_MODALI + sq.VARCHAR + sq.COMMA + sq.VARCHAR +
		  colunas.SEMANA + sq.VARCHAR + sq.COMMA + sq.VARCHAR +
		  colunas.MIN_PARTIC + sq.VARCHAR + sq.CLOSE_PAR +
		 " " + sq.VALUES + sq.OPEN_PAR + sq.VARCHAR +
	   modalidade.getIdModalidade() + sq.VARCHAR + sq.COMMA + sq.VARCHAR + 
	   modalidade.getSemana() + sq.VARCHAR + sq.COMMA + 
	   modalidade.getMinimoParticipantes() + sq.CLOSE_PAR + sq.SEMI_COLON;
	}

	@Override
	public Modalidade consulta(String codigo) throws ConexaoException, DAOException {
		Connection conexao = Conexao.getConexao();
		try {
			Statement st = conexao.createStatement();
			String sqlSt = comandos.SELECT_MODALIDADE + codigo + sq.VARCHAR + sq.SEMI_COLON;
			ResultSet rs = st.executeQuery(sqlSt);
			return rs.first() ? constroiModalidade(rs) : null;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Map<String, Modalidade> consultaTodos() throws ConexaoException, DAOException {
		Connection conexao = Conexao.getConexao();
		Map<String, Modalidade> modalidades = new HashMap<String, Modalidade>();
		try {
			Statement st = conexao.createStatement();
			ResultSet rs = st.executeQuery("select * from modalidade");
			while (rs.next()) {
				modalidades.put(rs.getString(colunas.ID_MODALI), constroiModalidade(rs));
			}
			return modalidades;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Modalidade> consultaFaixa(String... codigos) throws ConexaoException, DAOException {
		List<Modalidade> modalidades = new ArrayList<Modalidade>();
		Modalidade modalidade = new Modalidade();
		for (String string : codigos) {
			modalidade = consulta(string);
			modalidades.add(modalidade);
		}
		
		return modalidades;
	}

	@Override
	public boolean insere(Modalidade modalidade) throws ConexaoException, DAOException {
		Connection conexao = Conexao.getConexao();
		try {
			Statement st = conexao.createStatement();
			st.executeQuery(constroiInsert(modalidade));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		}
		return false;
	}

	@Override
	public List<Modalidade> insereVarios(Map<String, Modalidade> objetos) throws ConexaoException, DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Modalidade> insereVarios(List<Modalidade> objetos) throws ConexaoException, DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean insereVariosTransacao(List<Modalidade> objetos) throws ConexaoException, DAOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean altera(Modalidade objeto) throws ConexaoException, DAOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean exclui(String objeto) throws ConexaoException, DAOException {
		// TODO Auto-generated method stub
		return false;
	}
	
	public static void main(String[] args) throws ConexaoException, DAOException {
		ModalidadeDao modalidadeDao = new ModalidadeDao();
		Modalidade modalidade = modalidadeDao.consulta("muaythay");
		modalidadeDao.insere(modalidade);
		System.out.println("done");
		
	}
}
