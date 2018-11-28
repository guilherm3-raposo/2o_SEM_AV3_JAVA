package edu.senai.integrador.beans;

import java.time.LocalTime;

public class Modalidade {
	private String idModalidade;
	private String semana;
	private int minimoParticipantes;

	public Modalidade() {
	}

	public Modalidade(String idModalidade, String semana, int minimoParticipantes) {
		setIdModalidade(idModalidade);
		setSemana(semana);
		setMinimoParticipantes(minimoParticipantes);
	}

	public String getIdModalidade() {
		return idModalidade;
	}

	public void setIdModalidade(String idModalidade) {
		this.idModalidade = idModalidade;
	}

	
	public String getSemana() {
		return semana;
	}

	public String getSemanaFormatada() {
		String semanaFormatada = "";
		char[] dias = semana.toCharArray();
		if (dias[0] == 's') semanaFormatada += "Segunda\n";
		if (dias[1] == 's') semanaFormatada += "Terça\n";
		if (dias[2] == 's') semanaFormatada += "Quarta\n";
		if (dias[3] == 's') semanaFormatada += "Quinta\n";
		if (dias[4] == 's') semanaFormatada += "Sexta\n";
		if (dias[5] == 's') semanaFormatada += "Sábado\n";
		if (dias[6] == 's') semanaFormatada += "Domingo\n";
		return semanaFormatada;
	}

	public void setSemana(String semana) {
		this.semana = semana;
	}

	public int getMinimoParticipantes() {
		return minimoParticipantes;
	}

	public void setMinimoParticipantes(int minimoParticipantes) {
		this.minimoParticipantes = minimoParticipantes;
	}
}
