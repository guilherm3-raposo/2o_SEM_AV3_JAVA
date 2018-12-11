package edu.senai.integrador.beans.academia;

public enum EExercicioErro {
	EXERCICIO_INVALIDO("O exercicio informado é inválido!"),
	SERIE_INVALIDA("Os valores informados são inválidos!"),
	REPETICAO_INVALIDA("Os valores informados são inválidos!"),
	CARGA_INVALIDA("Os valores informados são inválidos!"),
	TREINO_INVALIDO("Os valores informados são inválidos!");

	private String erro;

	private EExercicioErro(String erro) {
		this.erro = erro;
	}

	public String getErro() {
		return erro;
	}
}
