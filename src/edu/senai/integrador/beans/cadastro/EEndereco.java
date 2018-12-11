package edu.senai.integrador.beans.cadastro;

public enum EEndereco {
	RUA("R.", "Rua"), 
	AVENIDA("Av.", "Avenida"), 
	ALAMEDA("Al.", "Alameda"), 
	RODOVIA("Rd.", "Rodovia"),
	VILA("V.", "Vila");

	private String sigla;
	private String descricao;

	private EEndereco(String sigla, String descricao) {
		this.sigla = sigla;
		this.descricao = descricao;
	}

	public String getSigla() {
		return sigla;
	}

	public String getDescricao() {
		return descricao;
	}

}