package edu.senai.integrador.beans.enumeradores;

public enum EAlunoErro {
	DATA_IDADE_INSUFICIENTE("O aluno deve ter pelo menos 16 anos"), ALTURA_INVALIDA("A altura informada é inválida"),
	PESO_INVALIDO("O peso informado é inválido");

	private String erro;

	private EAlunoErro(String erro) {
		this.erro = erro;
	}

	public String getErro() {
		return erro;
	}
}
