package edu.senai.integrador.beans.pessoa;

public enum EPessoaErro {
	CPF_FORMATO_INVALIDO("O CPF informado é inválido!"),
	CPF_TAMANHO_INVALIDO("O CPF informado deve conter 11 caracteres!"), 
	NOME_CARACTERES_INVALIDOS		("O nome informado deve conter apenas letras e espaços!"),
	NOME_TAMANHO("O nome informado dever conter entre 6 e 45 caracteres!"),
	NOME_INVALIDO("O nome informado contém caracteres inválidos!"),
	SEXO_INVALIDO("O sexo informado deve  ser M ou F!"),
	ESTADO_CIVIL_INVALIDO("Estado Civil Inválido: O valor informado não corresponde a nenhuma das opções");

	private String erro;

	private EPessoaErro(String erro) {
		this.erro = erro;
	}

	public String getMensagem() {
		return erro;
	}
}
