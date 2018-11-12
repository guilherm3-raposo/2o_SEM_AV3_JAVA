package edu.senai.integrador.beans;

import java.util.List;

public class Participantes {
	private int idTurma;
	List<Aluno> participantes;
	List<Funcionario> ministrantes;

	public Participantes(int idTurma, List<Aluno> participantes, List<Funcionario> ministrantes) {
		setIdTurma(idTurma);
		setParticipantes(participantes);
		setMinistrantes(ministrantes);
	}

	public int getIdTurma() {
		return idTurma;
	}

	public void setIdTurma(int idTurma) {
		this.idTurma = idTurma;
	}

	public List<Aluno> getParticipantes() {
		return participantes;
	}
	
	public void setParticipantes(List<Aluno> participantes) {
		this.participantes = participantes;
	}

	public List<Funcionario> getMinistrantes() {
		return ministrantes;
	}

	public void setMinistrantes(List<Funcionario> ministrantes) {
		this.ministrantes = ministrantes;
	}
}
