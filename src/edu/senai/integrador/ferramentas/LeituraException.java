package edu.senai.integrador.ferramentas;

public class LeituraException extends Exception {
	private static final long serialVersionUID = 1L;
	
	ELeituraErros erro;

	public LeituraException(ELeituraErros erro) {
		this.erro = erro;
	}
	
	public ELeituraErros getErro() {
		return erro;
	}
	
	@Override
	public String toString() {
		return this.erro.getCampo() + ";" + this.erro.getDescricaoErro();
	}
}
