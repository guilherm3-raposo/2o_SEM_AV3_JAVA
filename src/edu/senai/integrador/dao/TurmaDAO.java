package edu.senai.integrador.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.senai.integrador.bancodedados.conexao.Conexao;
import edu.senai.integrador.bancodedados.conexao.ConexaoException;
import edu.senai.integrador.beans.Turma;
import edu.senai.integrador.dao.sql.ColunasTurma;
import edu.senai.integrador.dao.sql.SqlComandos;
import edu.senai.integrador.dao.sql.SqlSintaxe;

public class TurmaDAO implements ICRUDPadraoDAO<Turma, Integer> {
	SqlSintaxe sq = new SqlSintaxe();
	SqlComandos comandos = new SqlComandos();
	ColunasTurma colunas = new ColunasTurma();
	
	
	public Turma constroiTurma(ResultSet rs, int idTurma) {
		try {
			ParticipantesDAO participantesDAO = new ParticipantesDAO();
			ModalidadeDao modalidadeDao = new ModalidadeDao();

			if(rs.first()) {
				Turma turma = new Turma(idTurma,
					LocalDateTime.parse(rs.getString(colunas.HORA_INICIO.toString()),DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
				   					    rs.getInt(colunas.DURACAO.toString()), 
					 					participantesDAO.consultaFuncionarios(idTurma), 
					 					participantesDAO.consultaAlunos(idTurma),
				 modalidadeDao.consulta(rs.getString(colunas.ID_MODALIDADE.toString())));
			return turma;	
			}
		} catch (SQLException | ConexaoException | DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Turma consulta(Integer codigo) throws ConexaoException, DAOException {
		Connection conexao = Conexao.getConexao();
		try {
			PreparedStatement pst = conexao.prepareStatement(comandos.SELECT_TURMA.toString());
			pst.setInt(1, codigo);
			ResultSet rs = pst.executeQuery();
			Turma turma = constroiTurma(rs, codigo);
			return turma;
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
			ResultSet rs = st.executeQuery(comandos.SELECT_TURMAS.toString());
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean insere(Turma objeto) throws ConexaoException, DAOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Turma> insereVarios(List<Turma> objetos) throws ConexaoException, DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Turma> insereVarios(Map<Integer, Turma> objetos) throws ConexaoException, DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean insereVariosTransacao(List<Turma> objetos) throws ConexaoException, DAOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean altera(Turma objeto) throws ConexaoException, DAOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean exclui(Integer codigo) throws ConexaoException, DAOException {
		// TODO Auto-generated method stub
		return false;
	}

//	@Override
	public boolean exclui(Turma objeto) throws ConexaoException, DAOException {
		// TODO Auto-generated method stub
		return false;
	}

}
