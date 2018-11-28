package edu.senai.integrador.beans;

import edu.senai.integrador.beans.exception.MusculacaoException;

public class Musculacao {
	private String treino;
	private String objetivo;
	private String sessao;
	private String observacao;
	private String cpfaluno;

	public Musculacao() {
		
	}
	
	public Musculacao(String treino, String objetivo, String sessao, String observacao, String cpfaluno) throws MusculacaoException{
		setTreino(treino);
		setObjetivo(objetivo);
		setSessao(sessao);
		setObservacao(observacao);
		setCpfaluno(cpfaluno);
	}

	public String getTreino() {
		return treino;
	}

	public void setTreino(String treino) throws MusculacaoException {
		this.treino = treino;
	}

	public String getObjetivo() {
		return objetivo;
	}
	
	public void setObjetivo(String objetivo) throws MusculacaoException {
		this.objetivo = objetivo;
	}
	
	public String getSessao() {
		return sessao;
	}

	public void setSessao(String sessao) throws MusculacaoException {
		this.sessao = sessao;
	}	

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) throws MusculacaoException {
		this.observacao = observacao;
	}

	public String getCpfaluno() {
		return cpfaluno;
	}

	public void setCpfaluno(String cpfaluno) throws MusculacaoException {
		this.cpfaluno = cpfaluno;
	}
	
	@Override
	public String toString() {
		return   "\nTreino:			" + this.getTreino()
			   + "\nObjetivo:			" + this.getObjetivo()
     		   + "\nSessão:			" + this.getSessao()
		       + "\nObservação:		" + this.getObservacao()
		       + "\nCPF Aluno:			" + this.getCpfaluno();
	}

	
	
}	