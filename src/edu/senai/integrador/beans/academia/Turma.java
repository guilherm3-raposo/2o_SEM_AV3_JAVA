package edu.senai.integrador.beans.academia;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import edu.senai.integrador.logs.GeraLog;

public class Turma {
	private int idTurma;
	private LocalTime horarioInicio;
	private int duracao;
	private Participantes participantes;
	private Modalidade modalidade;

	public Turma() {
	}

	public Turma(int idTurma, LocalTime horarioInicio, int duracao, Participantes participantes,
			Modalidade modalidade) throws TurmaException{
		setIdTurma(idTurma);
		setHorarioInicio(horarioInicio);
		setDuracao(duracao);
		setParticipantes(participantes);
		setModalidade(modalidade);
	}

	public int getIdTurma() throws TurmaException {
		return idTurma;
	}

	public void setIdTurma(int idTurma) throws TurmaException {
		this.idTurma = idTurma;
	}

	public LocalTime getHorarioInicio() throws TurmaException {
		return horarioInicio;
	}

	public void setHorarioInicio(LocalTime horarioInicio) throws TurmaException{
		this.horarioInicio = horarioInicio;
	}

	public int getDuracao() throws TurmaException {
		return duracao;
	}

	public void setDuracao(int duracao) throws TurmaException {
		this.duracao = duracao;
	}

	public Participantes getParticipantes() throws TurmaException {
		return participantes;
	}

	public void setParticipantes(Participantes participantes) throws TurmaException {
		this.participantes = participantes;
	}
	
	public Modalidade getModalidade() throws TurmaException {
		return modalidade;
	}

	public void setModalidade(Modalidade modalidade) throws TurmaException {
		this.modalidade = modalidade;
	}

	private String getHorario() throws TurmaException {
		return "Início: " + getHorarioInicio().format(DateTimeFormatter.ofPattern("HH:mm")) + "h"
				+ " - Fim:    "
				+ getHorarioInicio().plusMinutes(getDuracao()).format(DateTimeFormatter.ofPattern("HH:mm")) + "h";
	}
	
	@Override
	public String toString() {
		try {
			String turma = "\nTURMA: " + getIdTurma()
					+ " | MODALIDADE: " + getModalidade().getIdModalidade().toUpperCase()
					+ "\nHorário_________ " + getHorario()
					+ "\n\nDias____________________________________________\n"
					+ getModalidade().getSemanaFormatada() 
					+ (participantes == null || 
							getParticipantes().getMinistrantes().size() < 2 ? 
					  "\nMinistrante_____________________________________\n\n": 
					  "\nMinistrantes____________________________________\n\n") 
					+ (participantes != null ? participantes.toStringFuncionarios() : 
							"Não existem professores cadastrados.\n\n")
					+ "Alunos__________________________________________\n\n"
					+ (participantes != null ? participantes.getParticipantes().size() : 
						"0") + " de " + getModalidade().getMinimoParticipantes()
					+ " Alunos Necessários\n\n"
					+ (participantes != null ? participantes.toStringAlunos() :
							"Não existem alunos cadastrados.\n")+ "\n" +

					"________________________________________________\n";
			return turma;
		} catch (TurmaException | ModalidadeException e) {
			new GeraLog().escreveLogArquivo(e);
		} 
		return "Erro ao exportar os dados!";
	}
}
