package edu.senai.integrador.beans.pessoa;

import edu.senai.integrador.logs.GeraLog;
import edu.senai.integrador.seguranca.IMensagemException;

public class AlunoException extends Exception implements IMensagemException {

	private static final long serialVersionUID = 1L;

	private final EAlunoErro mensagem;
	
	public AlunoException(EAlunoErro erro) {
		mensagem = erro;
		GeraLog log = new GeraLog();
		log.escreveLogArquivo(erro);
	}
	
	public String getMensagem() {
		return this.mensagem.toString();
	}
}
