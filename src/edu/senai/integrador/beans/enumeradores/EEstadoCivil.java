package edu.senai.integrador.beans.enumeradores;

public enum EEstadoCivil {
	NAO_INFORMADO("Não informado", "Não informado"), SOLTEIRO("Solteiro", "Solteira"), CASADO("Casado", "Casada"),
	SEPARADO("Separado", "Separada"), DIVORCIADO("Divorciado", "Divorciada");

	private final String masculino;
	private final String feminino;

	private EEstadoCivil(String masculino, String feminino) {
		this.masculino = masculino;
		this.feminino = feminino;
	}

	public String getMasculino() {
		return masculino;
	}

	public String getFeminino() {
		return feminino;
	}
}
