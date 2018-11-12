package edu.senai.integrador.beans;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class Turma {
	private int idTurma;
	private LocalDateTime horarioInicio;
	private int duracao;
	private Map<String, Funcionario> ministrantes;
	private Map<String, Aluno> participantes;
	private Modalidade modalidade;

	public boolean cadastraAtividade() {
		return true;
	}

	public Turma() {
	}

	public Turma(int idTurma, LocalDateTime horarioInicio, int duracao, Map<String, Funcionario> ministrantes,
			Map<String, Aluno> participantes, Modalidade modalidade) {
		setIdTurma(idTurma);
		setHorarioInicio(horarioInicio);
		setDuracao(duracao);
		setMinistrantes(ministrantes);
		setParticipantes(participantes);
		setModalidade(modalidade);
	}

	public int getIdTurma() {
		return idTurma;
	}

	public void setIdTurma(int idTurma) {
		this.idTurma = idTurma;
	}

	public LocalDateTime getHorarioInicio() {
		return horarioInicio;
	}

	public void setHorarioInicio(LocalDateTime horarioInicio) {
		this.horarioInicio = horarioInicio;
	}

	public int getDuracao() {
		return duracao;
	}

	public void setDuracao(int duracao) {
		this.duracao = duracao;
	}

	public Map<String, Funcionario> getMinistrantes() {
		return ministrantes;
	}

	public void setMinistrantes(Map<String, Funcionario> ministrantes) {
		this.ministrantes = ministrantes;
	}

	public Map<String, Aluno> getParticipantes() {
		return participantes;
	}

	public void setParticipantes(Map<String, Aluno> participantes) {
		this.participantes = participantes;
	}

	public Modalidade getModalidade() {
		return modalidade;
	}

	public void setModalidade(Modalidade modalidade) {
		this.modalidade = modalidade;
	}

	private String getNomeMinistrantes() {
		StringBuffer nomes = new StringBuffer();
		getMinistrantes().forEach((cpf, pessoa) -> nomes.append(pessoa.getNome() +"\n"));
		return nomes + "";
	}

	private String getNomeParticipantes() {
		StringBuffer nomes = new StringBuffer();
		getParticipantes().forEach((cpf, pessoa) -> nomes.append(pessoa.getNome() + "\n"));
		return nomes + "";
	}

	private String getHorario() {
		return "Início: " + getHorarioInicio().format(DateTimeFormatter.ofPattern("HH:mm")) + "h"
				+ " - Fim:    "
				+ getHorarioInicio().plusMinutes(getDuracao()).format(DateTimeFormatter.ofPattern("HH:mm")) + "h";
	}
	
	@Override
	public String toString() {
		return "TURMA: " + getIdTurma() + " | MODALIDADE: " + 
				getModalidade().getIdModalidade().toUpperCase() + 
			  "\n________________________________________________\n" + 
			  "\nHorário_________ " + getHorario() + "\n" +
			  "\nDias____________________________________________\n\n" + getModalidade().getSemana() +
				(getMinistrantes().size() == 1 ? 
			  "\nMINISTRANTE_____________________________________\n\n" :
			  "\nMINISTRANTES____________________________________\n\n") + getNomeMinistrantes() + "\n" + 
			  "\nPARTICIPANTES___________________________________\n\n" + getParticipantes().size() + " de " + 
			  getModalidade().getMinimoParticipantes()+" Alunos Necessários\n\n"
			  + getNomeParticipantes() + "\n" +
			  "________________________________________________" ;
	}
}
