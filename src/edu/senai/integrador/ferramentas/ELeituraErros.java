package edu.senai.integrador.ferramentas;

public enum ELeituraErros {
	SENHA_TAMANHO		("Senha" , "A senha informada deve conter entre 6 e 20 caracteres!"),
	USUARIO_INVALIDO	("Nome" , "O nome informado cont�m caracteres inv�lidos!"),
	USUARIO_TAMANHO		("Nome", "O nome informado deve conter entre 6 e 30 caracteres!"),
	NOME_INVALIDO		("Nome" , "O nome informado deve conter apenas letras e espa�os!"),
	NUMERO_INVALIDO		("Numero" , "Digite um n�mero inteiro v�lido!"),
	VALOR_VAZIO			("String" , "Campo n�o pode estar vazio!"),
	VALOR_INVALIDO		("Valor" , "Valor informado � inv�lido!"),
	IP_INVALIDO			("IP" , "O IP informado � inv�lido! (XXX.XXX.X.X.X??"), 
	SIM_NAO				("S/N" , "Valor informado inv�lido! Digite sim, s, nao, n, yes, no, 1 ou 0"), 
	DOUBLE_INVALIDO		("Double" , "");

	
	String campo;
	String descricaoErro;
	
	private ELeituraErros(String campo, String erro) {
		this.descricaoErro = erro;
		this.campo = campo;
	}

	public String getDescricaoErro() {
		return descricaoErro;
	}

	public String getCampo() {
		return campo;
	}
}
