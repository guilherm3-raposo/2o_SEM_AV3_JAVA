package edu.senai.integrador.a.view;

import edu.senai.integrador.logs.GeraLog;
import edu.senai.integrador.seguranca.IMensagemException;

public class LeituraException extends Exception implements IMensagemException {
	private static final long serialVersionUID = 1L;
	
	private final ELeituraErro mensagem;
	
	public LeituraException(ELeituraErro erro) {
		mensagem = erro;
		GeraLog log = new GeraLog();
		log.escreveLogArquivo(erro);
	}
	
	public String getMensagem() {
		return this.mensagem.toString();
	}
}
