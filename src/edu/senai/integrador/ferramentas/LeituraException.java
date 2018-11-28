package edu.senai.integrador.ferramentas;

public class LeituraException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public LeituraException(ELeituraErro erro) {
		super(erro.getErro());
	}
}
