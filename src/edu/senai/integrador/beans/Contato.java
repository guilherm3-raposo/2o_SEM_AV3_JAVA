package edu.senai.integrador.beans;

import java.util.ArrayList;
import java.util.List;

public class Contato {
	private List<String> telefoneFixo;
	private String[] telefoneMovel;
	private String[] email;

	public Contato () {
		
	}

	public List<String> getTelefoneFixo() {
		return telefoneFixo;
	}

	public void setTelefoneFixo(String... telefoneFixo) {
		List<String> fixos = new ArrayList<String>();
		for (int i = 0; i < telefoneFixo.length; i++) {
			fixos.add(telefoneFixo[i]);
		}
		this.telefoneFixo = fixos;
	}

	public String[] getTelefoneMovel1() {
		return telefoneMovel;
	}

	public void setTelefoneMovel1(String... telefoneMovel1) {
		this.telefoneMovel = telefoneMovel1;
	}

	public String[] getEmail() {
		return email;
	}

	public void setEmail(String... email) {
		this.email = email;
	}

//	@Override
//	public String toString() {
//		return "\nTelefone Fixo_____ " + FormataDados.formataTelefone(getTelefoneFixo()) + "\nTelefone Móvel____ "
//				+ FormataDados.formataTelefone(getTelefoneMovel1()) + "\nTelefone Móvel 2__ "
//				+ FormataDados.formataTelefone(getTelefoneMovel2()) + "\nEmail_____________ " + getEmail();
//
//	}

}
