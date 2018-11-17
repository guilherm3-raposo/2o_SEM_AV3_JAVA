package edu.senai.integrador.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.senai.integrador.bancodedados.conexao.Conexao;
import edu.senai.integrador.bancodedados.conexao.ConexaoException;
import edu.senai.integrador.beans.Aluno;
import edu.senai.integrador.beans.enumeradores.EEstadoCivil;
import edu.senai.integrador.beans.enumeradores.ESexo;
import edu.senai.integrador.beans.exception.AlunoException;
import edu.senai.integrador.beans.exception.FuncionarioException;
import edu.senai.integrador.beans.exception.PessoaException;
import edu.senai.integrador.dao.sql.ColunasAluno;
import edu.senai.integrador.dao.sql.SqlComandos;
import edu.senai.integrador.dao.sql.SqlSintaxe;
import edu.senai.integrador.dao.sql.SqlTabelas;

public class AlunoDAO implements ICRUDPadraoDAO<Aluno, String> {
	SqlSintaxe sq = new SqlSintaxe();
	SqlComandos comandos = new SqlComandos();
	SqlTabelas tabelas = new SqlTabelas();
	ColunasAluno colunas = new ColunasAluno();
	
	private Aluno constroiAluno(ResultSet rs) throws DAOException {
		try {
			return new Aluno(				rs.getString(colunas.CPF.toString()),
											rs.getString(colunas.NOME.toString()),
							LocalDate.parse(rs.getString(colunas.DATA_NASC.toString())),
			ESexo.values()[Integer.parseInt(rs.getString(colunas.SEXO.toString()))],
						   Float.parseFloat(rs.getString(colunas.ALTURA.toString())),
						   Float.parseFloat(rs.getString(colunas.PESO.toString())),
	 EEstadoCivil.values()[Integer.parseInt(rs.getString(colunas.ESTAD0_CIVIL.toString()))]);
		} catch (PessoaException | AlunoException | FuncionarioException e) {
			throw new DAOException(EDaoErros.CONSULTA_DADO, e.getMessage(), this.getClass());
		} catch (NumberFormatException e) {
			throw new DAOException(EDaoErros.NUMERO_INVALIDO, e.getMessage(), this.getClass());
		} catch (SQLException e) {
			throw new DAOException(EDaoErros.SQL_INVALIDO, e.getMessage(), this.getClass());
		}
	}
	
	private String[] constroiInsert (Aluno aluno){
		String[] insert = new String[2];

		insert[0] = comandos.INSERT_PESSOA + sq.VARCHAR +
					aluno.getCPF() + sq.VARCHAR + sq.COMMA + sq.VARCHAR +
					aluno.getNome() + sq.VARCHAR + sq.COMMA + 
					aluno.getEstadoCivil().ordinal() + sq.COMMA + 
					aluno.getSexo().ordinal() + sq.COMMA + sq.VARCHAR +
	   Date.valueOf(aluno.getDataDeNascimento()) + sq.VARCHAR + sq.COMMA +
	   			   (aluno.isAtivo() ? 1 : 0) + sq.CLOSE_PAR + sq.SEMI_COLON;
		
		insert[1] = comandos.INSERT_ALUNO.toString() + sq.VARCHAR + 
					aluno.getCPF() + sq.VARCHAR + sq.COMMA +
					aluno.getAltura() + sq.COMMA + 
					aluno.getPeso() + sq.CLOSE_PAR + sq.SEMI_COLON;
		System.out.println(insert[0]);
		System.out.println(insert[1]);
		return insert;
	}
	
	private String[] constroiUpdate (Aluno aluno) {
		String[] update = {sq.UPDATE, sq.UPDATE};

		update[0] += tabelas.ALUNO + 
					 	  sq.SET + 
					 colunas.ALTURA + sq.EQUALS + 
					   aluno.getAltura() + sq.COMMA + 
					 colunas.PESO + sq.EQUALS + 
					   aluno.getPeso() + 
					   	  sq.WHERE +  
					 colunas.CPF + sq.EQUALS + sq.VARCHAR + 
					   aluno.getCPF() + sq.VARCHAR + sq.SEMI_COLON;

		update[1] += tabelas.PESSOA +
					 	  sq.SET + 
					 colunas.NOME + sq.EQUALS + sq.VARCHAR + 
					   aluno.getNome() + sq.VARCHAR + sq.COMMA + 
					 colunas.DATA_NASC + sq.EQUALS + sq.VARCHAR + 
		  Date.valueOf(aluno.getDataDeNascimento()) + sq.VARCHAR + sq.COMMA + 
					 colunas.ESTAD0_CIVIL + sq.EQUALS + 
					   aluno.getEstadoCivil().ordinal() + sq.COMMA + 
					 colunas.SEXO + sq.EQUALS + 
					   aluno.getSexo().ordinal() + 
					   	  sq.WHERE + 
					 colunas.CPF + sq.EQUALS + sq.VARCHAR + 
					   aluno.getCPF() + sq.VARCHAR + sq.SEMI_COLON;
		return update;
	}
	
	@Override
	public Aluno consulta(String codigo) throws ConexaoException, DAOException {
		Connection conexao = Conexao.getConexao();
		try {
			PreparedStatement pst = conexao.prepareStatement(comandos.SELECT_ALUNO.toString());
			pst.setString(1, codigo);
			ResultSet rs = pst.executeQuery();
			if (rs.first())
				return constroiAluno(rs);
		} catch (SQLException e) {
			throw new DAOException(EDaoErros.SQL_INVALIDO, e.getMessage(), this.getClass());
		} finally {
			Conexao.fechaConexao();
		}
		return null;
	}

	@Override
	public Map<String, Aluno> consultaTodos() throws ConexaoException, DAOException {
		Connection conexao = Conexao.getConexao();
		try {
			Map<String, Aluno> pessoas = new HashMap<String, Aluno>();
			Statement st = conexao.createStatement();
			ResultSet rs = st.executeQuery(comandos.SELECT_ALUNOS.toString());
			while (rs.next()) {
				pessoas.put(rs.getString(colunas.CPF.toString()), constroiAluno(rs));
			}
			return pessoas;
		} catch (SQLException e) {
			throw new DAOException(EDaoErros.SQL_INVALIDO, e.getMessage(), this.getClass());
		} finally {
			Conexao.fechaConexao();
		}
	}

	@Override
	public List<Aluno> consultaFaixa(String... faixa) throws ConexaoException, DAOException {
		List<Aluno> pessoas = new ArrayList<Aluno>();
		for (String i : faixa) {
			pessoas.add(consulta(i));
		}
		return pessoas;
	}

	@Override
	public boolean insere(Aluno aluno) throws ConexaoException, DAOException {
		Connection conexao = Conexao.getConexao();
		try {
			Statement st = conexao.createStatement();
			String[] insert = constroiInsert(aluno);
			st.execute(insert[0]);
			st.execute(insert[1]);			
			return true;
		} catch (SQLException e) {
			throw new DAOException(EDaoErros.SQL_INVALIDO, e.getMessage(), this.getClass());
		} finally {
			Conexao.fechaConexao();
		}
	}

	@Override
	public List<Aluno> insereVarios(List<Aluno> alunos) throws ConexaoException, DAOException {
		Connection conexao = Conexao.getConexao();
		Aluno naoInserido = new Aluno();
		List<Aluno> naoInseridos = new ArrayList<Aluno>();
		try {
			for (Aluno aluno : alunos) {
				Statement st = conexao.createStatement();
				String[] insert = constroiInsert(aluno);
				st.execute(insert[0]);
				st.execute(insert[1]);
				naoInserido = aluno;
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
	public List<Aluno> insereVarios(Map<String, Aluno> alunos) throws ConexaoException, DAOException {
		Connection conexao = Conexao.getConexao();
		List<Aluno> naoInseridos = new ArrayList<Aluno>();
		try {
			alunos.forEach((cpf,aluno) -> {
				Aluno naoInserido = new Aluno();
				Statement st;
				try {
					st = conexao.createStatement();
					String[] insert = constroiInsert(aluno);
					st.execute(insert[0]);
					st.execute(insert[1]);
					naoInserido = aluno;
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
	public boolean insereVariosTransacao(List<Aluno> objetos) throws ConexaoException, DAOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean altera(Aluno aluno) throws ConexaoException, DAOException {
		Connection conexao = Conexao.getConexao();
		String[] update = constroiUpdate(aluno);
		try {
			Statement st = conexao.createStatement();
			st.execute(update[0]);
			st.execute(update[1]);
		} catch (SQLException e) {
			throw new DAOException(EDaoErros.ALTERA_DADO, e.getMessage(), this.getClass());
		} finally {
			Conexao.fechaConexao();
		}
		return true;
	}

	@Override
	public boolean exclui(String codigo) throws ConexaoException, DAOException {
		if (consulta(codigo) instanceof Aluno) {
			Connection conexao = Conexao.getConexao();
			try {
				Statement st = conexao.createStatement();
				st.execute(sq.UPDATE +
					  tabelas.PESSOA + 
					  	   sq.SET +
					  colunas.ATIVO + sq.EQUALS +
					  	   	  0 +
					  	   sq.WHERE +
					  colunas.CPF + sq.EQUALS + 
				 sq.VARCHAR + codigo + sq.VARCHAR + sq.SEMI_COLON);
			} catch (SQLException e) {
				throw new DAOException(EDaoErros.EXCLUI_DADO, e.getMessage(), this.getClass());
			} finally {
				Conexao.fechaConexao();
			}
		}
		return true;
	}

//	@Override
	public boolean exclui(Aluno aluno) throws ConexaoException, DAOException {
		return exclui(aluno.getCPF());
	}
}