package edu.senai.integrador.beans.academia;

import edu.senai.integrador.logs.GeraLog;

public class Exercicio {
	
	private String exercicio;
	private int serie;
	private int repeticao;
	private int carga;
	private int treino;
	
	public Exercicio() {
		
	}
	
	public Exercicio (String exercicio, int serie, int repeticao,
		int carga, int treino) throws ExercicioException {
		setExercicio(exercicio);
		setSerie(serie);
		setRepeticao(repeticao);
		setCarga(carga);
		setTreino(treino);
	}
	
	public String getExercicio()  throws ExercicioException {
		return exercicio;
	}
	public void setExercicio(String exercicio) throws ExercicioException  {
		this.exercicio = exercicio;
	}
	public int getSerie() throws ExercicioException  {
		return serie;
	}
	public void setSerie(int serie) throws ExercicioException  {
		this.serie = serie;
	}
	public int getRepeticao() throws ExercicioException  {
		return repeticao;
	}
	public void setRepeticao(int repeticao) throws ExercicioException  {
		this.repeticao = repeticao;
	}
	public int getCarga() throws ExercicioException  {
		return carga;
	}
	public void setCarga(int carga) throws ExercicioException  {
		this.carga = carga;
	}
	
	public int getTreino() throws ExercicioException  {
		return treino;
	}
	public void setTreino(int treino) throws ExercicioException  {
		this.treino = treino;
	}
	
	@Override
	public String toString() {
		try {
			return "\nExercício:			" + this.getExercicio() + 
				   "\nSérie:				" + this.getSerie() +
				   "\nRepeticao:			" + this.getRepeticao() +
				   "\nCarga:				" + this.getCarga() +
				   "\nTreino:				" + this.getTreino();
		} catch (Exception e) {
			new GeraLog().escreveLogArquivo(e);
		}
		return "Erro ao exportar os dados!";
	}

}
