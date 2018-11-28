package edu.senai.integrador.beans;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import edu.senai.integrador.bancodedados.conexao.ConexaoException;
import edu.senai.integrador.dao.DAOException;
import edu.senai.integrador.dao.TurmaDAO;
import edu.senai.integrador.z.populadores.Instanciador;

public class Turma {
	private int idTurma;
	private LocalTime horarioInicio;
	private int duracao;
	private Map<String, Funcionario> ministrantes;
	private Map<String, Aluno> participantes;
	private Modalidade modalidade;

	public Turma() {
	}

	public Turma(int idTurma, LocalTime horarioInicio, int duracao, Map<String, Funcionario> ministrantes,
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

	public LocalTime getHorarioInicio() {
		return horarioInicio;
	}

	public void setHorarioInicio(LocalTime horarioInicio) {
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
			  "\nDias____________________________________________\n\n" + getModalidade().getSemanaFormatada() +
				(getMinistrantes().size() == 1 ? 
			  "\nMINISTRANTE_____________________________________\n\n" :
			  "\nMINISTRANTES____________________________________\n\n") + getNomeMinistrantes() + "\n" + 
			  "\nPARTICIPANTES___________________________________\n\n" + getParticipantes().size() + " de " + 
			  getModalidade().getMinimoParticipantes()+" Alunos Necessários\n\n"
			  + getNomeParticipantes() + "\n" +
			  "________________________________________________" ;
	}
	
	public static void main(String[] args) throws ConexaoException, DAOException {
		Turma turma = new Turma(); 
		Modalidade modalidade = new Modalidade();
		modalidade.setIdModalidade("zumba");
		modalidade.setSemana("snsnsns");
		modalidade.setMinimoParticipantes(5);
		Map<String, Aluno> alunos = new HashMap<String, Aluno>();
		Map<String, Funcionario> funcionarios = new HashMap<String, Funcionario>();
		for (int i = 0; i < 3; i++) {
			alunos.put(i+"", Instanciador.criaAluno());
			funcionarios.put(i+"",Instanciador.criaFuncionario());
		}
		turma.setDuracao(1);
		turma.setHorarioInicio(LocalTime.now());
		turma.setIdTurma(1);
		turma.setModalidade(modalidade);
		turma.setMinistrantes(funcionarios);
		turma.setParticipantes(alunos);
		
		TurmaDAO turmaDAO = new TurmaDAO();
		turmaDAO.insere(turma);
		
		System.out.println(turma.toString());
	}
}
