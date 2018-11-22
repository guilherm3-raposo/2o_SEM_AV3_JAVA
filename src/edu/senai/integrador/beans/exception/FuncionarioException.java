package edu.senai.integrador.beans.exception;

import edu.senai.integrador.beans.enumeradores.EFuncionarioErro;

public class FuncionarioException extends Exception {

	private static final long serialVersionUID = 1L;

	public FuncionarioException(EFuncionarioErro erro) {
		super(erro.getErro());
	}
}
