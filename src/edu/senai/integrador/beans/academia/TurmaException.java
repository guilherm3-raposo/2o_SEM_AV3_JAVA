package edu.senai.integrador.beans.academia;

import edu.senai.integrador.logs.GeraLog;

public class TurmaException extends Exception {
	private static final long serialVersionUID = 1L;

	
	private final ETurmaErro mensagem;
	
	public TurmaException(ETurmaErro erro) {
		mensagem = erro;
		GeraLog log = new GeraLog();
		log.escreveLogArquivo(erro);
	}
	
	public ETurmaErro getMensagem() {
		return this.mensagem;
	}
}
