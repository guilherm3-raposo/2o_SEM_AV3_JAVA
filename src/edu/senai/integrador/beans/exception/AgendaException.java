package edu.senai.integrador.beans.exception;

import edu.senai.integrador.beans.enumeradores.EAgendaErro;

public class AgendaException extends Exception {

	private static final long serialVersionUID = 1L;

	public AgendaException(EAgendaErro erro) {
		super(erro.getErro());
	}
}
