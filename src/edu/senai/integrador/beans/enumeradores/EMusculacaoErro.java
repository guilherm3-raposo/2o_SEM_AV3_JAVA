package edu.senai.integrador.beans.enumeradores;

public enum EMusculacaoErro {
	CAMPO_VAZIO ("Este campo não pode ficar vazio"),
	OBJETIVO_INVALIDO ("O objetivo descrito não é válido");
	
	
	private String erro;

	private EMusculacaoErro(String erro) {
		this.erro = erro;
	}

	public String getErro() {
		return erro;
	}
}
