package edu.senai.integrador.seguranca;

public class SegurancaException extends Exception {
	private static final long serialVersionUID = 1L;
	
	String erro = "Serial informado é inválido!";
	String campo = "Serial";
	
	public String getDescricaoErro() {
		return erro;
	}
	
	@Override
	public String toString() {
		return this.campo + ";" + this.erro; 
	}
}
