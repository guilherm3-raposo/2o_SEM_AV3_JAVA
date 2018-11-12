package edu.senai.integrador.beans.enumeradores;

public enum EAgendaErro {
	NOME_ATIVIDADE_INVALIDO("O nome informado para a atividade é inválido."),
	HORARIO_INDISPONIVEL("O horário informado para a atividade é inválido."),
	DATA_INDISPONIVEL("A data informada para a atividade não está disponível.");

	private String erro;

	private EAgendaErro(String erro) {
		this.erro = erro;
	}

	public String getErro() {
		return erro;
	}

}
