package edu.senai.projetoModafackaIntegrador.populadores2;

public enum ENomes {
	UM("Ana", "Ambrósio", "Aquino"), DOIS("Beatriz", "Berenice", "Barros"), TRES("Carla", "Castro", "Cardoso"),
	QUATRO("Diana", "DiMalo", "Dinklage"), CINCO("Esther", "Elara", "Elsa"), SEIS("Fabiana", "Figueira", "Fogaça"),
	SETE("Gabriela", "Genézio", "Gomes"), OITO("Heloísa", "Hermann", "Hemmer"), NOVE("Ilara", "Ismael", "Irma"),
	DEZ("Janete", "Jonas", "Jill");

	private String nome;
	private String segundoNome;
	private String terceiroNome;

	private ENomes(String nome, String segundoNome, String terceiroNome) {
		this.nome = nome;
		this.segundoNome = segundoNome;
		this.terceiroNome = terceiroNome;
	}

	public String getNome() {
		return nome;
	}

	public String getSegundoNome() {
		return segundoNome;
	}

	public String getTerceiroNome() {
		return terceiroNome;
	}

}
