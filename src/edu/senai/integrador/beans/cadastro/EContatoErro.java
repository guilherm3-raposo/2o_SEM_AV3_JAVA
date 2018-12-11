package edu.senai.integrador.beans.cadastro;

public enum EContatoErro {
	EMAIL_INVALIDO("O valor informado não corresponde a um email válido"),
	NUMERO_INVALIDO("O número informado é invalido");

	private String erro;

	private EContatoErro(String erro) {
		this.erro = erro;
	}

	public String getErro() {
		return erro;
	}
}
