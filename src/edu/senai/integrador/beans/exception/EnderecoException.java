package edu.senai.integrador.beans.exception;

import edu.senai.integrador.beans.enumeradores.EEnderecoErro;

public class EnderecoException extends Exception {

	private static final long serialVersionUID = 1L;

	public EnderecoException(EEnderecoErro erro) {
		super(erro.getErro());

	}
}
