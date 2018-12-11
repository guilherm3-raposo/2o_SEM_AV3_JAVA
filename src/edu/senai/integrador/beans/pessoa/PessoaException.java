package edu.senai.integrador.beans.pessoa;

import edu.senai.integrador.logs.GeraLog;
import edu.senai.integrador.seguranca.IMensagemException;

public class PessoaException extends Exception implements IMensagemException {

	private static final long serialVersionUID = 1L;

	
	private final EPessoaErro mensagem;
	
	public PessoaException(EPessoaErro erro) {
		mensagem = erro;
		GeraLog log = new GeraLog();
		log.escreveLogArquivo(erro);
	}
	
	public String getMensagem() {
		return this.mensagem.toString();
	
	}
}
