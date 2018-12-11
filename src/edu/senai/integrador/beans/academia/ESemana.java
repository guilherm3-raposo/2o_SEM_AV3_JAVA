package edu.senai.integrador.beans.academia;

public enum ESemana {
	DOMINGO("Domingo", "Dom"), 
	SEGUNDA("Segunda", "Seg"), 
	TERCA("Terça", "Ter"), 
	QUARTA("Quarta", "Qua"),
	QUINTA("Quinta", "Qui"), 
	SEXTA("Sexta", "Sex"), 
	SABADO("Sábado", "Sab");

	private String descricao;
	private String abreviatura;

	private ESemana(String descricao, String abreviatura) {
		this.descricao = descricao;
		this.abreviatura = abreviatura;
	}

	public String getDescricao() {
		return descricao;
	}

	public String getAbreviatura() {
		return abreviatura;
	}
	
	@Override
	public String toString() {
		return getDescricao();
	}
}
