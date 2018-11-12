package edu.senai.integrador.beans;

import edu.senai.integrador.ferramentas.FormataDados;

public class Contato {
	private String telefoneFixo;
	private String telefoneMovel1;
	private String telefoneMovel2;
	private String email;

	public Contato(String telefoneFixo, String telefoneMovel1, String telefoneMovel2, String email) {
		setTelefoneFixo(telefoneFixo);
		setTelefoneMovel1(telefoneMovel1);
		setTelefoneMovel2(telefoneMovel2);
		setEmail(email);
	}

	public String getTelefoneFixo() {
		return telefoneFixo;
	}

	public void setTelefoneFixo(String telefoneFixo) {
		this.telefoneFixo = telefoneFixo;
	}

	public String getTelefoneMovel1() {
		return telefoneMovel1;
	}

	public void setTelefoneMovel1(String telefoneMovel1) {
		this.telefoneMovel1 = telefoneMovel1;
	}

	public String getTelefoneMovel2() {
		return telefoneMovel2;
	}

	public void setTelefoneMovel2(String telefoneMovel2) {
		this.telefoneMovel2 = telefoneMovel2;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "\nTelefone Fixo_____ " + FormataDados.formataTelefone(getTelefoneFixo()) + "\nTelefone Móvel____ "
				+ FormataDados.formataTelefone(getTelefoneMovel1()) + "\nTelefone Móvel 2__ "
				+ FormataDados.formataTelefone(getTelefoneMovel2()) + "\nEmail_____________ " + getEmail();

	}

}
