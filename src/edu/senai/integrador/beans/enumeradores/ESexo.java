package edu.senai.integrador.beans.enumeradores;

public enum ESexo {
	FEMININO("Feminino", "Fem", 'F'), MASCULINO("Masculino", "Mas", 'M'), NAO_INFORMADO("Não Informado", "N/I", 'N');

	private final String descricao;
	private final String abreviatura;
	private final char sigla;

	private ESexo(String descricao, String abreviatura, char sigla) {
		this.descricao = descricao;
		this.abreviatura = abreviatura;
		this.sigla = sigla;
	}

	public String getDescricao() {
		return descricao;
	}

	public String getAbreviatura() {
		return abreviatura;
	}

	public char getSigla() {
		return sigla;
	}
}
