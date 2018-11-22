package edu.senai.integrador.beans.enumeradores;

public enum EEnderecoErro {
	LOGRADOURO_INVALIDO("O código informado não corresponde a um logradouro"),
	ENDERECO_VAZIO("O endereco informado não pode estar vazio"),
	ENDERECO_INVALIDO("Endereço inválido: Informe números por extenso"),
	COMPLEMENTO_VAZIO("O complemento informado não pode estar vazio"), NUMERO_INVALIDO("O número informado é invalido");

	private String erro;

	private EEnderecoErro(String erro) {
		this.erro = erro;
	}

	public String getErro() {
		return erro;
	}
}
