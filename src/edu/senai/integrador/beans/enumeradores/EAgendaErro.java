package edu.senai.integrador.beans.enumeradores;

public enum EAgendaErro {
	NOME_ATIVIDADE_INVALIDO("O nome informado para a atividade � inv�lido."),
	HORARIO_INDISPONIVEL("O hor�rio informado para a atividade � inv�lido."),
	DATA_INDISPONIVEL("A data informada para a atividade n�o est� dispon�vel.");

	private String erro;

	private EAgendaErro(String erro) {
		this.erro = erro;
	}

	public String getErro() {
		return erro;
	}

}
