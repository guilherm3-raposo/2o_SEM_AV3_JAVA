package edu.senai.integrador.beans;

import edu.senai.integrador.ferramentas.FormataDados;

public class Contato {
	
	private String CPF;
	private String fixo;
	private String telefoneMovel;
	private String email;

	public Contato() {
		
	}
	
	public Contato(String CPF, String telefoneFixo, String telefoneMovel, String email) {
		setCPF(CPF);
		setTelefoneFixo(telefoneFixo);
		setTelefoneMovel(telefoneMovel);
		setEmail(email);
	}
	
	public String getCPF() {
		return CPF;
	}
	
	public void setCPF(String CPF) {
		this.CPF = CPF;
	}

	public String getTelefoneFixo() {
		return fixo;
	}

	public void setTelefoneFixo(String telefoneFixo) {
		this.fixo = telefoneFixo;
	}

	public String getTelefoneMovel() {
		return telefoneMovel;
	}

	public void setTelefoneMovel(String telefoneMovel1) {
		this.telefoneMovel = telefoneMovel1;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "\nCPF_______________ " + FormataDados.formataCpf(getCPF()) +
			   "\nTelefone Fixo_____ " + FormataDados.formataTelefone(getTelefoneFixo()) + 
			   "\nTelefone M�vel____ " + FormataDados.formataTelefone(getTelefoneMovel()) + 
			   "\nEmail_____________ " + getEmail();

	}


}
