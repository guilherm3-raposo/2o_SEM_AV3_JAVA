package edu.senai.integrador.dao;

import java.util.List;
import java.util.Map;

import edu.senai.integrador.bancodedados.conexao.ConexaoException;

public interface ICRUDPadraoDAO<T, O> {
	public abstract T consulta(O codigo) throws ConexaoException, DAOException;

	public abstract Map<O, T> consultaTodos() throws ConexaoException, DAOException;

	public abstract List<T> consultaFaixa(O... codigos) throws ConexaoException, DAOException;

	public abstract boolean insere(T objeto) throws ConexaoException, DAOException;

	public abstract List<T> insereVarios(Map<O, T> objetos) throws ConexaoException, DAOException;

	public abstract List<T> insereVarios(List<T> objetos) throws ConexaoException, DAOException;

	public abstract boolean insereVariosTransacao(List<T> objetos) throws ConexaoException, DAOException;

	public abstract boolean altera(T objeto) throws ConexaoException, DAOException;

	public abstract boolean exclui(O objeto) throws ConexaoException, DAOException;
}
