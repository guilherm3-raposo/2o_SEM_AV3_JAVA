package edu.senai.integrador.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.senai.integrador.bancodedados.conexao.Conexao;
import edu.senai.integrador.bancodedados.conexao.ConexaoException;
import edu.senai.integrador.beans.Modalidade;
import edu.senai.integrador.dao.sql.EXmlColunas;
import edu.senai.integrador.dao.sql.EXmlComandos;
import edu.senai.integrador.dao.sql.EXmlSintaxe;

public class ModalidadeDao implements ICRUDPadraoDAO<Modalidade, String> {

	private Modalidade constroiModalidade(ResultSet rs) {
		Modalidade modalidade = new Modalidade();
		try {
			String semana = rs.getString(EXmlColunas.SEMANA.toString());
			
			modalidade = new Modalidade(rs.getString(EXmlColunas.ID_MODALI.toString()), 
										semana, 
										rs.getInt(EXmlColunas.MIN_PARTIC.toString()));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return modalidade;
	}

	@Override
	public Modalidade consulta(String codigo) throws ConexaoException, DAOException {
		Connection conexao = Conexao.getConexao();
		try {
			Statement st = conexao.createStatement();
			String sqlSt = EXmlComandos.SELECT_MODALIDADE + codigo + EXmlSintaxe.VARCHAR + EXmlSintaxe.CLOSE;
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
				modalidades.put(rs.getString(EXmlColunas.ID_MODALI.toString()), constroiModalidade(rs));
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean insere(Modalidade objeto) throws ConexaoException, DAOException {
		// TODO Auto-generated method stub
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
}
