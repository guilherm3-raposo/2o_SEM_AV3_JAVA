package edu.senai.integrador.beans.cadastro;

import edu.senai.integrador.logs.GeraLog;

public class EnderecoException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private final EEnderecoErro mensagem;
	
	public EnderecoException(EEnderecoErro erro) {
		mensagem = erro;
		GeraLog log = new GeraLog();
		log.escreveLogArquivo(erro);
	}
	
	public EEnderecoErro getMensagem() {
		return this.mensagem;
	}
}
