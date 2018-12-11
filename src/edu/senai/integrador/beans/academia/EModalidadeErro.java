package edu.senai.integrador.beans.academia;

public enum EModalidadeErro {
	MODALIDADE_INVALIDA("A modalidade informada é inválida!"),
	SEMANA_INVALIDA("Os valores informados são inválidos!"),
	MINIMO_INVALIDO("O valor informado é inválido!");

	private String erro;

	private EModalidadeErro(String erro) {
		this.erro = erro;
	}

	public String getErro() {
		return erro;
	}
}
