package edu.senai.integrador.beans.exception;

import edu.senai.integrador.beans.enumeradores.EPessoaErro;

public class PessoaException extends Exception {

	private static final long serialVersionUID = 1L;

	public PessoaException(EPessoaErro erro) {
		super(erro.getErro());
	}

}
