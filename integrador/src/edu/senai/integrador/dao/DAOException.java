package edu.senai.integrador.dao;

import edu.senai.integrador.logs.GeraLog;

public class DAOException extends Exception {

	private static final long serialVersionUID = 1L;

	public DAOException(EDaoErros erro, String mensagem, Class<?> classe) {
		super(erro.getMensagem() + classe.getName() + "#" + mensagem);
		GeraLog log = new GeraLog();
		log.escreveLogArquivo(erro, classe.getName(), mensagem);
	}
}
