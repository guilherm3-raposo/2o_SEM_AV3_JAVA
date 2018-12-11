package edu.senai.integrador.bancodedados.conexao;

import edu.senai.integrador.logs.GeraLog;
import edu.senai.integrador.seguranca.IMensagemException;

public class ConexaoException extends Exception implements IMensagemException {

	private static final long serialVersionUID = 1L;

	private final EConexaoErro mensagem;
	
	public ConexaoException(EConexaoErro erro) {
		mensagem = erro;
		GeraLog log = new GeraLog();
		log.escreveLogArquivo(erro);
	}
	
	public String getMensagem() {
		return this.mensagem.toString();
	}
}
