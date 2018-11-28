package edu.senai.integrador.beans;

public class Exercicio {
	
	private String exercicio;
	private String serie;
	private String repeticao;
	private String carga;
	private String treino;
	
	public Exercicio() {
		
	}
	
	public Exercicio (String exercicio, String serie, String repeticao,
		String carga, String treino) {
		setExercicio(exercicio);
		setSerie(serie);
		setRepeticao(repeticao);
		setCarga(carga);
		setTreino(treino);
	}
	
	public String getExercicio() {
		return exercicio;
	}
	public void setExercicio(String exercicio) {
		this.exercicio = exercicio;
	}
	public String getSerie() {
		return serie;
	}
	public void setSerie(String serie) {
		this.serie = serie;
	}
	public String getRepeticao() {
		return repeticao;
	}
	public void setRepeticao(String repeticao) {
		this.repeticao = repeticao;
	}
	public String getCarga() {
		return carga;
	}
	public void setCarga(String carga) {
		this.carga = carga;
	}
	
	public String getTreino() {
		return treino;
	}
	public void setTreino(String treino) {
		this.treino = treino;
	}
	
	@Override
	public String toString() {
		return "Exercício:			" + this.getExercicio() + 
			   "Série:				" + this.getSerie() +
			   "Repeticao:			" + this.getRepeticao() +
			   "Carga:				" + this.getCarga() +
			   "Treino:				" + this.getTreino();
	}

}
