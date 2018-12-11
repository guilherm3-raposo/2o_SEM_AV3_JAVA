package edu.senai.integrador.a.view;

import java.sql.SQLException;

import edu.senai.integrador.bancodedados.conexao.ConexaoException;
import edu.senai.integrador.beans.academia.Exercicio;
import edu.senai.integrador.dao.DAOException;
import edu.senai.integrador.dao.EDAOErros;
import edu.senai.integrador.dao.ExercicioDAO;

public class MenuExercicio extends Executavel {
	Exercicio exercicio = new Exercicio();
	ExercicioDAO exerciciodao = new ExercicioDAO();
	
	public MenuExercicio() {
	}

	public boolean principal() throws InterruptedException {
		while (true) {
			try {
				limpaConsole();
				printaVerde(text.MENU_EXERCICIO + "1. Cadastra Exercicio\n"
												+ "2. Consulta Exercicio\n"
												+ "3. Altera   Exercicio\n" 
												+ "4. Exclui   Exercicio\n" 
												+ "5. Voltar\n"
												+ "Digite a opção desejada: ");
				lePreto();
				switch (leitor.leInt()) {
				default:
					printaWarning(text.OPCAO_INVALIDA, new LeituraException(ELeituraErro.VALOR_INVALIDO));
					continue;
				case 1:
					cadastraExercicios();
					continue;
				case 2:
					consultaExercicios();
					continue;
				case 3:
					alteraExercicio();
					continue;
				case 4:
					 excluiExercicio();
					continue;
				case 5:
					menuPrincipal(1);
					break;
				}
				break;
			} catch (Exception e) {
				printaWarning(ELeituraErro.VALOR_INVALIDO.toString(), e);
			}
		}
		return false;
	}

	private boolean cadastraExercicios() throws InterruptedException {
		while (true) {
			try {
				limpaConsole();
				printaVerde(text.EXERCICIO);
				lePreto();
				exercicio.setExercicio(leitor.leString(1));
			} catch (LeituraException e) {
				printaWarning(e.getMensagem().toString(), e);
				break;
			} catch (Exception e) {
				printaWarning(ELeituraErro.VALOR_INVALIDO.toString(), e);
				continue;
			}
			break;
		}
		while (true) {
			limpaConsole();
			try {
				printaVerde(text.SERIE);
				lePreto();
				exercicio.setSerie(leitor.leInt());
				break;
			} catch (Exception e) {
				printaWarning(ELeituraErro.NUMERO_INVALIDO.toString(), e);
				continue;
			}
		}

		while (true) {
			limpaConsole();
			try {
				printaVerde(text.REPETICAO);
				lePreto();
				exercicio.setRepeticao(leitor.leInt());
				break;
			} catch (Exception e) {
				printaWarning(ELeituraErro.NUMERO_INVALIDO.toString(), e);
				continue;
			}
		}

		while (true) {
			limpaConsole();
			try {
				printaVerde(text.CARGA);
				lePreto();
				exercicio.setCarga(leitor.leInt());
				break;
			} catch (Exception e) {
				printaWarning(ELeituraErro.NUMERO_INVALIDO.toString(), e);
				continue;
			}
		}

		while (true) {
			limpaConsole();
			try {
				printaVerde(text.TREINO);
				lePreto();
				exercicio.setTreino(leitor.leInt());
				break;
			} catch (Exception e) {
				printaWarning(ELeituraErro.NUMERO_INVALIDO.toString(), e);
				continue;
			}
		}
		try {
			printaPretoln("\n" + exercicio.toString());
			exerciciodao.insere(exercicio);
		} catch (ConexaoException | DAOException e) {
			printaWarning(e.getMensagem().toString(), e);
			return false;
		} catch (SQLException e) {
			printaWarning(EDAOErros.INSERE_DADO.toString(), e);
		}
		printaVerde(text.EXER_SUCESSO);
		return true;
	}

	private void consultaExercicios() throws InterruptedException {
		while (true) {
			try {
				limpaConsole();
				printaVerde(text.EXERCICIO);
				lePreto();
				exercicio = exerciciodao.consulta(leitor.leString(5));
				printaPreto(exercicio.toString());
				break;
			} catch (Exception e) {
				printaWarning(ELeituraErro.VALOR_INVALIDO.toString(), e);
				continue;
			}
		}
	}

	private boolean alteraExercicio() throws InterruptedException {
		while(true) {
			limpaConsole();
			printaVerdeln(text.ALTERA_EXERCICIO + "\nDigite o exercício que será alterado: ");
			lePreto();
			try {
				exercicio = exerciciodao.consulta(leitor.leString(1));
			} catch (DAOException e) {
				printaWarning(e.getMessage().toString(), e);
				continue;
			} catch (SQLException | ConexaoException e) {
				printaWarning(e.getMessage().toString(), e);
				continue;
			} catch (Exception e) {
				printaWarning(ELeituraErro.VALOR_INVALIDO.toString(), e);
			}
			break;
		}
		
		while(true) {
			try {
				limpaConsole();
				printaVerde(text.ALTERA_EXERCICIO + exercicio.toString() + "\n\n" 
							+ "1. Alterar Número de Série: \n" 
							+ "2. Alterar Número de Repetições: \n"
						    + "3. Alterar Número da Carga: \n" 
							+ "4. Alterar Número do Treino: \n" 
						    + "5. Voltar: \n"
						    + "6. Confirmar\n\n" 
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
						try {
							printaVerde(text.ALTERA_EXERCICIO + text.SERIE);
							lePreto();
							exercicio.setSerie(leitor.leInt());
						} catch (Exception e) {
							printaWarning(ELeituraErro.VALOR_INVALIDO.toString(), e);
							continue;
						}
						break;
				}
				continue;
				
				case 2:
					while (true) {
						try {
							printaVerde(text.ALTERA_EXERCICIO + text.REPETICAO);
							lePreto();
							exercicio.setRepeticao(leitor.leInt());
						} catch (Exception e) {
							printaWarning(ELeituraErro.VALOR_INVALIDO.toString(), e);
							continue;
						}
						break;
				}
				continue;
				
				case 3:
					while (true) {
						try {
							printaVerde(text.ALTERA_EXERCICIO + text.CARGA);
							lePreto();
							exercicio.setCarga(leitor.leInt());
						} catch (Exception e) {
							printaWarning(ELeituraErro.VALOR_INVALIDO.toString(), e);
							continue;
						}
						break;
				}
				continue;
				
				case 4:
					while (true) {
						try {
							printaVerde(text.ALTERA_EXERCICIO + text.TREINO);
							lePreto();
							exercicio.setTreino(leitor.leInt());
						} catch (Exception e) {
							printaWarning(ELeituraErro.VALOR_INVALIDO.toString(), e);
							continue;
						}
						break;
				}
				continue;
				
				case 5:
					break;
				case 6:
					while (true) {
						try {
							exerciciodao.altera(exercicio);
							printaAzulln(text.EXER_SUCESSO);
							break;
						} catch (ConexaoException | DAOException e) {
							printaWarning(e.getMensagem().toString(), e);
							continue;
						} catch (SQLException e) {
							printaWarning(EDAOErros.ALTERA_DADO.toString(), e);
							continue;
						}
					}
				}
				
			} catch (Exception e) {
		     	printaWarning(ELeituraErro.VALOR_INVALIDO.toString(), e);
			}
			break;
		}
		return true;
		
	}	
	private boolean excluiExercicio() throws InterruptedException {
		while (true) {
			limpaConsole();
			printaVerdeln(text.EXCLUIR_EXERCICIO + "\n");
			lePreto();
			try {
				exercicio = exerciciodao.consulta(leitor.leString(1));
				printaVermelho(text.CONFIRMA_DELETE);
				lePreto();
				if(
					leitor.leBoolean().matches("true")) {
					exerciciodao.exclui(exercicio.getExercicio());
					printaAzulln(text.EXCLUI_SUCESSO);
					return true;
				}else {
					printaVermelholn(EDAOErros.EXCLUI_DADO.toString());
					return false;
				}
			} catch (DAOException | LeituraException | ConexaoException e) {
				printaWarning(e.getMensagem(), e);
				continue;
			} catch (Exception  e) {
				printaWarning(ELeituraErro.VALOR_INVALIDO.toString(), e);
				continue;
			}
		}
	}
}
