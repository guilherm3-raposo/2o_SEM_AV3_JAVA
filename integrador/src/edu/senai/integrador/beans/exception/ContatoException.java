package edu.senai.integrador.beans.exception;

import edu.senai.integrador.beans.enumeradores.EContatoErro;

public class ContatoException extends Exception {

	private static final long serialVersionUID = 1L;

	public ContatoException(EContatoErro erro) {
		super(erro.getErro());
	}
}
