package edu.senai.integrador.ferramentas;

public enum ELeituraErro {
	NOME_INVALIDO("Nome deve conter apenas letras e espa�os"),
	NUMERO_INVALIDO("Digite um n�mero inteiro v�lido"),
	VALOR_INVALIDO("Valor informado � inv�lido"),
	IP_INVALIDO("O IP informado � inv�lido! (XXX.XXX.X.X.X??"), 
	SIM_NAO("Valor informado inv�lido! Digite sim, s, nao, n, yes, no, 1 ou 0"), 
	DOUBLE_INVALIDO("");
	
	String erro;

	private ELeituraErro(String erro) {
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
