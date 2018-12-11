package edu.senai.integrador.beans.cadastro;

public enum EUsuarioErro {
	USUARIO_INVALIDO	("O nome informado contém caracteres inválidos!"),
	USUARIO_TAMANHO		("O nome informado deve conter entre 6 e 30 caracteres!"),
	SENHA_TAMANHO("A senha informada deve conter de 5 a 20 caracteres!"),
	CPF_INVALIDO		("O cpf informado é inválido.!"),
	LOGIN_INVALIDO("O usuário e/ou senha informados são inválidos!");
	
	String erro;

	private EUsuarioErro(String erro) {
		this.erro = erro;
	}

	public String getErro() {
		return erro;
	}
	
	@Override
	public String toString() {
		return this.erro;
	}
}
