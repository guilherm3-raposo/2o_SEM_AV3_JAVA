package edu.senai.integrador.beans.enumeradores;

public enum EPessoaErro {
	CPF_FORMATO_INVALIDO("O CPF informado é inválido"), 
	NOME_INVALIDO("O nome informado contém caracteres inválidos"),
	NOME_VAZIO("O nome informado não pode estar vazio"),
	DATA_FORMATO_INVALIDO("A data informada deve estar no formato DDMMAAAA"),
	DATA_ANO_INVALIDO("O ano informado deve ser maior que 1900"),
	DATA_MES_INVALIDO("O mes informado deve estar entre 1 e 12"),
	DATA_DIA_INVALIDO("O dia informado deve estar entre 1 e 31"), 
	SEXO_INVALIDO("O sexo informado deve  ser M ou F"),
	VALOR_VAZIO("Valor informado não pode ser vazio"),
	ESTADO_CIVIL_INVALIDO("Estado Civil Inválido: O valor informado não corresponde a nenhuma das opções");

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
