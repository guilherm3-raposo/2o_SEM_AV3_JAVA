package edu.senai.integrador.beans.enumeradores;

public enum EPessoaErro {
	CPF_FORMATO_INVALIDO("O CPF informado � inv�lido"), 
	NOME_INVALIDO("O nome informado cont�m caracteres inv�lidos"),
	NOME_VAZIO("O nome informado n�o pode estar vazio"),
	DATA_FORMATO_INVALIDO("A data informada deve estar no formato DDMMAAAA"),
	DATA_ANO_INVALIDO("O ano informado deve ser maior que 1900"),
	DATA_MES_INVALIDO("O mes informado deve estar entre 1 e 12"),
	DATA_DIA_INVALIDO("O dia informado deve estar entre 1 e 31"), 
	SEXO_INVALIDO("O sexo informado deve  ser M ou F"),
	VALOR_VAZIO("Valor informado n�o pode ser vazio"),
	ESTADO_CIVIL_INVALIDO("Estado Civil Inv�lido: O valor informado n�o corresponde a nenhuma das op��es");

	private String erro;

	private EPessoaErro(String erro) {
		this.erro = erro;
	}

	public String getErro() {
		return erro;
	}
	
	@Override
	public String toString() {
		return getErro();
	}

}
