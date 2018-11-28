package edu.senai.integrador.beans.exception;

import edu.senai.integrador.beans.enumeradores.EMusculacaoErro;

public class MusculacaoException extends Exception {
	private static final long serialVersionUID = 1L;

	public MusculacaoException(EMusculacaoErro erro) {
		super(erro.getErro());

	}
}