package edu.senai.integrador.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import edu.senai.integrador.bancodedados.conexao.ConexaoException;

public interface ICRUDPadraoDAO<T, O> {
	public abstract T consulta(O codigo) throws ConexaoException, DAOException, SQLException;

	public abstract Map<O, T> consultaTodos() throws ConexaoException, DAOException, SQLException;

	public abstract List<T> consultaFaixa(O... codigos) throws ConexaoException, DAOException, SQLException;

	public abstract boolean insere(T objeto) throws ConexaoException, DAOException, SQLException;

	public abstract List<T> insereVarios(Map<O, T> objetos) throws ConexaoException, DAOException, SQLException;

	public abstract List<T> insereVarios(List<T> objetos) throws ConexaoException, DAOException, SQLException;

	public abstract boolean altera(T objeto) throws ConexaoException, DAOException, SQLException;

	public abstract boolean exclui(O objeto) throws ConexaoException, DAOException, SQLException;
}
