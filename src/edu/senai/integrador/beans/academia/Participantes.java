package edu.senai.integrador.beans.academia;

import java.util.List;

import edu.senai.integrador.beans.pessoa.Aluno;
import edu.senai.integrador.beans.pessoa.Funcionario;
import edu.senai.integrador.beans.pessoa.PessoaException;

public class Participantes {
	private List<Aluno> participantes;
	private List<Funcionario> ministrantes;
	private String retorno;

	public Participantes() {
	}
	public Participantes(List<Aluno> participantes, List<Funcionario> ministrantes) {
		setParticipantes(participantes);
		setMinistrantes(ministrantes);
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

	public String toStringAlunos() {
		retorno = "";
		this.participantes.forEach(string -> {
			try {retorno += string.getNome() + "\n";} 
			catch (PessoaException e) {}});
		return retorno;
	}
	
	public String toStringFuncionarios() {
		retorno = "";
		this.ministrantes.forEach(string -> {
			try {retorno += string.getNome() + "\n";} 
			catch (PessoaException e) {}});
		return retorno;
	}
}
