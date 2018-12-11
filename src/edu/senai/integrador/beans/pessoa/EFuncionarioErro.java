package edu.senai.integrador.beans.pessoa;

public enum EFuncionarioErro {
	CTPS_INVALIDA("O número da CTPS informado é inválido."),
	ESCOLARIDADE_INVALIDA("Escolaridade inválida: O valor informado não corresponde a nenhuma das opções."),
	DATA_IDADE_INSUFICIENTE("Idade insuficiente: O funcionário deve ter pelo menos 18 anos.");

	private String erro;

	private EFuncionarioErro(String erro) {
		this.erro = erro;
	}

	public String getMensagem() {
		return erro;
	}
}
