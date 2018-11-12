package edu.senai.integrador.beans.enumeradores;

public enum EModalidadeErro {
	MODALIDADE_INVALIDA("A modalidade informada � inv�lida.");

	private String erro;

	private EModalidadeErro(String erro) {
		this.erro = erro;
	}

	public String getErro() {
		return erro;
	}
}
