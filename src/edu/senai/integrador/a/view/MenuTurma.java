package edu.senai.integrador.a.view;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import edu.senai.integrador.bancodedados.conexao.ConexaoException;
import edu.senai.integrador.beans.academia.Modalidade;
import edu.senai.integrador.beans.academia.Turma;
import edu.senai.integrador.beans.pessoa.Aluno;
import edu.senai.integrador.beans.pessoa.Funcionario;
import edu.senai.integrador.dao.AlunoDAO;
import edu.senai.integrador.dao.DAOException;
import edu.senai.integrador.dao.EDAOErros;
import edu.senai.integrador.dao.FuncionarioDAO;
import edu.senai.integrador.dao.ModalidadeDAO;
import edu.senai.integrador.dao.ParticipantesDAO;
import edu.senai.integrador.dao.TurmaDAO;

public class MenuTurma extends Executavel{
	private Turma turma = new Turma();
	private TurmaDAO turmaDAO = new TurmaDAO();
	
	public void principal() throws InterruptedException {
		while (true) {
			try {
				limpaConsole();
				printaVerde(text.MENU_TURMA
							   + "1. Cadastra Turmas\n"
							   + "2. Consulta Turmas\n"
							   + "3. Altera   Turmas\n"
							   + "4. Exclui   Turmas\n"
							   + "5. Voltar\n"
							   + "Digite a opção desejada: ");
				lePreto();
				switch (leitor.leInt()) {
				default:
					printaWarning(text.OPCAO_INVALIDA, new LeituraException(ELeituraErro.VALOR_INVALIDO));
					continue;
				case 1:
					cadastraTurmas();
					continue;
				case 2:
					consultaTurmas();
					continue;
				case 3:
					alteraTurmas();
					continue;
				case 4:
					excluiTurmas();
					continue;
				case 5:
					break;
				}
				break;
			} catch (Exception e) {
				printaWarning(ELeituraErro.VALOR_INVALIDO.toString(), e);
			}
		}
	}
	
	private boolean cadastraTurmas() throws InterruptedException {
		while (true) {
			limpaConsole();
			try {
				printaVerde(text.TURMA_ID);
				lePreto();
				turma.setIdTurma(leitor.leInt());
			} catch (Exception e) {
				printaWarning(ELeituraErro.VALOR_INVALIDO.toString(), e);
				continue;
			}
			break;
		}
		while (true) {
			try {
				Map<String, Modalidade> modalidades = new TreeMap<String, Modalidade>();
				modalidades = new ModalidadeDAO().consultaTodos();
				limpaConsole();
				modalidades.forEach((nome, mod)->  printaPreto(nome + "\n"));
				printaVerde(text.MOD_ID);
				lePreto();
				turma.setModalidade(modalidades.get(leitor.leString(5)));
			} catch (Exception e) {
				printaWarning(ELeituraErro.VALOR_INVALIDO.toString(), e);
				continue;
			}
			break;
		}
		while (true) {
			limpaConsole();
			try {
				printaVerde(text.TURMA_HORARIO);
				lePreto();
				turma.setHorarioInicio(leitor.leHora());
			} catch (Exception e) {
				printaWarning(ELeituraErro.VALOR_INVALIDO.toString(), e);
				continue;
			}
			break;
		}
		while (true) {
			limpaConsole();
			try {
				printaVerde(text.TURMA_DURACAO);
				lePreto();
				turma.setDuracao(leitor.leInt());
			} catch (Exception e) {
				printaWarning(ELeituraErro.VALOR_INVALIDO.toString(), e);
				continue;
			}
			break;
		}
		try {
			printaPretoln("\n" + turma.toString());
			printaVermelholn(text.CONFIRMA_INSERT);
			lePreto();
			String confirma = leitor.leBoolean();
			if(confirma.matches("true"))
				turmaDAO.insere(turma);
			else return false;
		} catch(ConexaoException | LeituraException e2) {
			printaWarning(e2.getMensagem().toString(), e2);
			return false;
		} catch (Exception e2) {
			printaWarning(EDAOErros.INSERE_DADO.toString(), e2);
			return false;
		}
		printaVerde(text.TURMA_SUCESSO);
		return true;
	}
	
	private void consultaTurmas() throws InterruptedException {
		while (true) {
			try {
				limpaConsole();
				printaVerde(text.TURMA_ID);
				lePreto();
				turma = turmaDAO.consulta(leitor.leInt());
				printaVerde(turma.toString());
				break;
			} catch (Exception e) {
				printaWarning(ELeituraErro.VALOR_INVALIDO.toString(), e);
				continue;
			}
		}
	}
	
	private boolean alteraTurmas() throws InterruptedException {
		try {
			while (true) {
				Map<Integer, Turma> turmas = turmaDAO.consultaTodos();
				turmas.forEach((codigo, turma) -> printaAzulln(turma.toString()));
				limpaConsole();
				
				printaVerde(text.TURMA_ALTERA + text.TURMA_ID);
				lePreto();
				try {
					turma = turmaDAO.consulta(leitor.leInt());
				} catch (DAOException e) {
					printaWarning(e.getMensagem().toString(), e);
					continue;
				} catch (Exception e) {
					printaWarning(ELeituraErro.VALOR_INVALIDO.toString(), e);
					continue;
				}
				break;
			}
			while (true) {
				AlunoDAO alunoDAO = new AlunoDAO();
				Aluno aluno = new Aluno();
				List<String> alunos = new ArrayList<String>();
				FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
				Funcionario funcionario = new Funcionario();
				List<String> funcionarios = new ArrayList<String>();
				ParticipantesDAO participantesDAO = new ParticipantesDAO();
				limpaConsole();
				printaVerde(text.TURMA_ALTERA + "\n" + turma.toString() 
						  + "1. Alterar modalidade\n"
						  + "2. Alterar início da atividade\n"
					      + "3. Aterar duração da atividade\n"
						  + "4. Adicionar participantes\n"
					      + "5. Adicionar ministrantes\n"
						  + "6. Remover participantes\n"
					      + "7. Remover ministrantes\n"
						  + "9. Voltar\n"
					      + "0. Confirmar Alterações\n"
					      + "Digite a opção desejada: ");
				 lePreto();
				int i = leitor.leInt();
				 limpaConsole();
				switch (i) {
				default:
					printaWarning(text.OPCAO_INVALIDA, new LeituraException(ELeituraErro.VALOR_INVALIDO));
					continue;
				case 1:
					while (true) {
						limpaConsole();
						try {
							Map<String, Modalidade> modalidades = new TreeMap<String, Modalidade>();
							modalidades = new ModalidadeDAO().consultaTodos();
							printaVerde(text.TURMA_ALTERA + text.MOD_ID);
							modalidades.forEach((nome, mod)->  printaPreto(nome+ "\n"));
							lePreto();
							turma.setModalidade(modalidades.get(leitor.leString(5)));
						} catch (Exception e) {
							printaWarning(ELeituraErro.VALOR_INVALIDO.toString(), e);
							continue;
						}
						break;
					}
					continue;
				case 2:
					while (true) {
						limpaConsole();
						try {
							printaVerde(text.TURMA_ALTERA + text.TURMA_HORARIO);
							lePreto();
							turma.setHorarioInicio(leitor.leHora());
						} catch (Exception e) {
							printaWarning(ELeituraErro.VALOR_INVALIDO.toString(), e);
							continue;
						}
						break;
					}
					continue;
				case 3:
					while (true) {
						limpaConsole();
						try {
							printaVerde(text.TURMA_ALTERA + text.TURMA_DURACAO);
							lePreto();
							turma.setDuracao(leitor.leInt());
						} catch (Exception e) {
							printaWarning(ELeituraErro.VALOR_INVALIDO.toString(), e);
							continue;
						}
						break;
					}
					continue;
				case 4:
					while (true) {
						limpaConsole();
						try {
							printaVerde(text.TURMA_ALTERA + text.CPF);
							lePreto();
							aluno = alunoDAO.consulta(leitor.leCpf());
							printaAzulln(aluno.toString());
							alunos.add(aluno.getCPF());
							printaVermelho(text.CONFIRMA_INSERT);
							lePreto();
							if (leitor.leBoolean().matches("true"))
								participantesDAO.insereParticipantes(turma.getIdTurma(), null, alunos);
							else break;
							printaAzulln(text.USR_SUCESSO);
							printaVerde(text.TURMA_INSERE_PART);
							lePreto();
							if(leitor.leBoolean().matches("true")) continue;
						} catch (Exception e) {
							printaWarning(ELeituraErro.VALOR_INVALIDO.toString(), e);
						}
						break;
					}
					continue;
				case 5:
					while (true) {
						limpaConsole();
						try {
							printaVerde(text.TURMA_ALTERA + text.CPF);
							lePreto();
							funcionario = funcionarioDAO.consulta(leitor.leCpf());
							printaAzulln(funcionario.toString());
							printaVermelho(text.CONFIRMA_INSERT);
							lePreto();
							funcionarios.add(funcionario.getCPF());
							if (leitor.leBoolean().matches("true"))
								participantesDAO.insereParticipantes(turma.getIdTurma(), funcionarios, null);
							else break;
							printaAzulln(text.USR_SUCESSO);
							printaVerde(text.TURMA_INSERE_PART);
							lePreto();
							if(leitor.leBoolean().matches("true")) continue;
						} catch (Exception e) {
							printaWarning(ELeituraErro.VALOR_INVALIDO.toString(), e);
						}
						break;
					}
					continue;
				case 6:
					while (true) {
						limpaConsole();
						try {
							printaVerde(text.TURMA_ALTERA + text.CPF);
							lePreto();
							aluno = alunoDAO.consulta(leitor.leCpf());
							printaAzulln(aluno.toString());
							printaVermelho(text.CONFIRMA_DELETE);
							lePreto();
							if (leitor.leBoolean().matches("true"))
								participantesDAO.exclui(turma.getIdTurma(), aluno.getCPF(), null);
							else break;
							printaAzulln(text.USR_SUCESSO);
							printaVerde(text.TURMA_INSERE_PART);
							lePreto();
							if(leitor.leBoolean().matches("true")) continue;
						} catch (Exception e) {
							printaWarning(ELeituraErro.VALOR_INVALIDO.toString(), e);
						}
						break;
					}
					continue;
				case 7:
					while (true) {
						limpaConsole();
						try {
							printaVerde(text.TURMA_ALTERA + text.CPF);
							lePreto();
							funcionario = funcionarioDAO.consulta(leitor.leCpf());
							printaAzulln(funcionario.toString());
							printaVermelho(text.CONFIRMA_DELETE);
							lePreto();
							if (leitor.leBoolean().matches("true"))
								participantesDAO.exclui(turma.getIdTurma(), null, funcionario.getCPF());
							else break;
							printaAzulln(text.USR_SUCESSO);
							printaVerde(text.TURMA_INSERE_PART);
							lePreto();
							if(leitor.leBoolean().matches("true")) continue;
						} catch (Exception e) {
							printaWarning(ELeituraErro.VALOR_INVALIDO.toString(), e);
						}
						break;
					}
					continue;
				case 9:
					break;
				case 0:
					try {
						printaVerde(text.CONFIRMA_UPDATE);
						lePreto();
						if(leitor.leBoolean().matches("true")) turmaDAO.altera(turma);
						continue;
					} catch (ConexaoException | DAOException | LeituraException e) {
						printaWarning(e.getMensagem().toString(), e);
						continue;
					} catch (SQLException e) {
						printaWarning(EDAOErros.ALTERA_DADO.toString(), e);
						continue;
					}
				}
				return true;
			}
		} catch (Exception e) {
			printaWarning(ELeituraErro.VALOR_INVALIDO.toString(), e);
		}
		return true;
	}
	
	public boolean excluiTurmas() throws InterruptedException {
		while (true) {
			limpaConsole();
			printaVerde(text.TURMA_EXCLUI + text.TURMA_ID);
			try {
				lePreto();
				turma = turmaDAO.consulta(leitor.leInt());
				printaPreto(turma.toString());
				printaVermelho(text.CONFIRMA_DELETE);
				lePreto();
				if(leitor.leBoolean().matches("true")) turmaDAO.exclui(turma);
				else break;
			} catch (ConexaoException | DAOException | LeituraException e) {
				printaWarning(e.getMensagem().toString(), e);
				continue;
			} catch (Exception  e) {
				printaWarning(ELeituraErro.VALOR_INVALIDO.toString(), e);
				break;
			}
			printaAzulln(text.EXCLUI_SUCESSO);
			break;
		}
		return true;
	}
}
