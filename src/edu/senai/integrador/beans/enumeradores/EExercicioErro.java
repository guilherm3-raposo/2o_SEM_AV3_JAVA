package edu.senai.integrador.beans.enumeradores;

public enum EExercicioErro {
	CAMPO_VAZIO ("Campo obrigat�rio, n�o pode ficar vazio");
	
	private String erro;

	private EExercicioErro(String erro) {
		this.erro = erro;
	}

	public String getErro() {
		return erro;
	}

}
