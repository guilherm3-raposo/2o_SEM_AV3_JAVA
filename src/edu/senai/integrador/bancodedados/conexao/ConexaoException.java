package edu.senai.integrador.bancodedados.conexao;

import edu.senai.integrador.logs.GeraLog;

public class ConexaoException extends Exception {

	private static final long serialVersionUID = 1L;

	public ConexaoException(EConexaoErro erro, String mensagem) {
		super(erro.getMensagem());
		GeraLog log = new GeraLog();
		log.escreveLogArquivo(erro, mensagem);
	}
}
