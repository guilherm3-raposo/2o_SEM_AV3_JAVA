package edu.senai.integrador.beans.academia;

public enum ETurmaErro {
	ID_INVALIDO("ID informado não consta na base de dados!"),
	HORARIO_INICIO("Horário incorreto!"),
	DURACAO("Duração inválida!");
	
	String mensagem;

	private ETurmaErro(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getMensagem() {
		return mensagem;
	}
}
