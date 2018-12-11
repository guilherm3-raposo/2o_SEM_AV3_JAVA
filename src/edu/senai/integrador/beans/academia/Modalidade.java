package edu.senai.integrador.beans.academia;

public class Modalidade {
	private String idModalidade;
	private String semana;
	private int minimoParticipantes;

	public Modalidade() {
	}

	public Modalidade(String idModalidade, String semana, int minimoParticipantes) throws ModalidadeException {
		setIdModalidade(idModalidade);
		setSemana(semana);
		setMinimoParticipantes(minimoParticipantes);
	}

	public String getIdModalidade() throws ModalidadeException  {
		return idModalidade;
	}

	public void setIdModalidade(String idModalidade) throws ModalidadeException  {
		this.idModalidade = idModalidade;
	}

	
	public String getSemana() {
		return semana;
	}
	

	public String getSemanaFormatada() {
		String semanaFormatada = "";
		char[] dias = semana.toCharArray();
		if (dias[0] == 's') semanaFormatada += "Segunda, ";
		if (dias[1] == 's') semanaFormatada += "Terça, ";
		if (dias[2] == 's') semanaFormatada += "Quarta, ";
		if (dias[3] == 's') semanaFormatada += "Quinta,\n";
		if (dias[4] == 's') semanaFormatada += "Sexta, ";
		if (dias[5] == 's') semanaFormatada += "Sábado, ";
		if (dias[6] == 's') semanaFormatada += "Domingo, ";
		return semanaFormatada;
	}

	public void setSemana(String semana)  throws ModalidadeException {
		this.semana = semana;
	}

	public int getMinimoParticipantes() throws ModalidadeException  {
		return minimoParticipantes;
	}

	public void setMinimoParticipantes(int minimoParticipantes) throws ModalidadeException  {
		this.minimoParticipantes = minimoParticipantes;
	}
	
	@Override
	public String toString() {
		try {
			return "Modalidade:" + this.getIdModalidade() + 
				   "\nDias: " + getSemanaFormatada() +
				   "Mínimo de Participantes: " + getMinimoParticipantes(); 
		} catch (ModalidadeException e) {
		}
		return "Erro ao exportar os dados!";
	}
}
