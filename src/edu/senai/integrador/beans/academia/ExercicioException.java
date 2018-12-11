package edu.senai.integrador.beans.academia;

import edu.senai.integrador.logs.GeraLog;

public class ExercicioException extends Exception {
	private static final long serialVersionUID = 1L;

	private EExercicioErro mensagem;

	public ExercicioException(EExercicioErro erro) {
		mensagem = erro;
		GeraLog log = new GeraLog();
		log.escreveLogArquivo(erro);
	}

	public String getMensagem() {
		return this.mensagem.toString();
	}
}
