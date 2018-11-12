package edu.senai.integrador.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import edu.senai.integrador.bancodedados.conexao.Conexao;
import edu.senai.integrador.bancodedados.conexao.ConexaoException;
import edu.senai.integrador.beans.Aluno;
import edu.senai.integrador.beans.Funcionario;
import edu.senai.integrador.dao.sql.EXmlColunas;
import edu.senai.integrador.dao.sql.EXmlComandos;

public class ParticipantesDAO {

	public Map<String, Aluno> consultaAlunos(int codigo) throws ConexaoException, DAOException {
		Connection conexao = Conexao.getConexao();
		Map<String, Aluno> participantes = new HashMap<String, Aluno>();
		AlunoDAO alunoDAO = new AlunoDAO();

		try {
			PreparedStatement pst = conexao.prepareStatement(EXmlComandos.SELECT_PARTICIPANTES.toString());
			pst.setInt(1, codigo);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
					participantes.put(rs.getString(EXmlColunas.ID_ALUNO.toString()),
							alunoDAO.consulta(rs.getString(EXmlColunas.ID_ALUNO.toString())));
			}
		} catch (Exception e) {
			throw new DAOException(EDaoErros.CONSULTA_DADO, e.getMessage(), this.getClass());
		} finally {
			Conexao.fechaConexao();
		}
		return participantes;
	}
	
	public Map<String, Funcionario> consultaFuncionarios(int codigo) throws ConexaoException, DAOException {
		Connection conexao = Conexao.getConexao();
		Map<String, Funcionario> ministrantes = new HashMap<String, Funcionario>();
		FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

		try {
			PreparedStatement pst = conexao.prepareStatement(EXmlComandos.SELECT_MINISTRANTES.toString());
			pst.setInt(1, codigo);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
					ministrantes.put(rs.getString(EXmlColunas.ID_FUNCIO.toString()),
							funcionarioDAO.consulta(rs.getString(EXmlColunas.ID_FUNCIO.toString())));
			}
		} catch (Exception e) {
			throw new DAOException(EDaoErros.CONSULTA_DADO, e.getMessage(), this.getClass());
		} finally {
			Conexao.fechaConexao();
		}
		return ministrantes;
	}
	
}
