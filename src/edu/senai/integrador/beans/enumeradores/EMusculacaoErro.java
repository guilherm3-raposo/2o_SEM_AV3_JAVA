package edu.senai.integrador.beans.enumeradores;

public enum EMusculacaoErro {
	CAMPO_VAZIO ("Este campo n�o pode ficar vazio"),
	OBJETIVO_INVALIDO ("O objetivo descrito n�o � v�lido");
	
	
	private String erro;

	private EMusculacaoErro(String erro) {
		this.erro = erro;
	}

	public String getErro() {
		return erro;
	}
}
