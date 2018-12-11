package edu.senai.integrador.dao;

import edu.senai.integrador.logs.GeraLog;
import edu.senai.integrador.seguranca.IMensagemException;

public class DAOException extends Exception implements IMensagemException {

	private static final long serialVersionUID = 1L;
	
	private final EDAOErros mensagem;
	
	public DAOException(EDAOErros erro) {
		mensagem = erro;
		GeraLog log = new GeraLog();
		log.escreveLogArquivo(erro);
	}
	
	public String getMensagem() {
		return this.mensagem.toString();
	}
}
