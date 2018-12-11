package edu.senai.integrador.beans.academia;

import edu.senai.integrador.logs.GeraLog;
import edu.senai.integrador.seguranca.IMensagemException;

public class ModalidadeException extends Exception implements IMensagemException {
	private static final long serialVersionUID = 1L;
	
	private final EModalidadeErro mensagem;
	
	public ModalidadeException(EModalidadeErro erro) {
		mensagem = erro;
		GeraLog log = new GeraLog();
		log.escreveLogArquivo(erro);
	}
	
	public String getMensagem() {
		return this.mensagem.toString();
	}
}
