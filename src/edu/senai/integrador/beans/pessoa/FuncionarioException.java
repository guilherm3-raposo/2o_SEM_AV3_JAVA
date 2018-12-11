package edu.senai.integrador.beans.pessoa;

import edu.senai.integrador.logs.GeraLog;
import edu.senai.integrador.seguranca.IMensagemException;

public class FuncionarioException extends Exception implements IMensagemException {

	private static final long serialVersionUID = 1L;

	private final EFuncionarioErro mensagem;

	public FuncionarioException(EFuncionarioErro erro) {
		mensagem = erro;
		GeraLog log = new GeraLog();
		log.escreveLogArquivo(erro);
	}

	public String getMensagem() {
		return this.mensagem.toString();
	}
}
