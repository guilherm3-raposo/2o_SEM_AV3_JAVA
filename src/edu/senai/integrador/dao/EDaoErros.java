package edu.senai.integrador.dao;

public enum EDaoErros {
	CRIA_TABELA("Erro ao criar a tabela informada: "), 
	EXCLUI_TABELA("Erro ao excluir a tabela informada: "),
	ALTERA_TABELA("Erro ao alterar a tbela informada: "),
	INSERE_DADO("Erro ao inserir o registro na tabela informada: "),
	CONSULTA_DADO("Erro ao consultar o registro na tabela informada: "),
	ALTERA_DADO("Erro ao alterar o registro na tabela informada: "),
	EXCLUI_DADO("Erro ao excluir o registro na tabela informada: "),
	NUMERO_INVALIDO("O Número informado está no formato errado: "),
	SQL_INVALIDO("Houve um problema no SQL inserido: ");

	private final String mensagem;

	public String getMensagem() {
		return mensagem;
	}

	private EDaoErros(String mensagem) {
		this.mensagem = mensagem;
	}

	@Override
	public String toString() {
		return getMensagem();
	}
}
