package edu.senai.integrador.dao;

public enum EDAOErros {
	CRIA_TABELA("Erro ao criar a tabela informada!"), 
	EXCLUI_TABELA("Erro ao excluir a tabela informada!"),
	ALTERA_TABELA("Erro ao alterar a tabela informada!"),
	INSERE_DADO("Erro ao inserir o registro na tabela informada!"),
	CONSULTA_DADO("Erro ao consultar o registro na tabela informada!"),
	CONSULTA_TODOS("Erro ao consultar os registros!"),
	ALTERA_DADO("Erro ao alterar o registro na tabela informada!"),
	EXCLUI_DADO("Erro ao excluir o registro na tabela informada!"),
	NUMERO_INVALIDO("O Número informado está no formato errado!"),
	SQL_INVALIDO("Houve um problema no SQL inserido!"),
	REGISTRO_INATIVO("O cadastro informado já existe, deseja reativá-lo? (S/N) "),
	REGISTRO_DUPLICADO("O CPF informado já exite na base de dados!"),
	REGISTRO_INVALIDO("A chave informada não existe na base de dados!");

	private final String mensagem;

	private EDAOErros(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getMensagem() {
		return mensagem;
	}
	
	@Override
	public String toString() {
		return this.mensagem.toString();
	}
}
