package edu.senai.integrador.a.view;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import edu.senai.integrador.bancodedados.conexao.ConexaoException;
import edu.senai.integrador.beans.academia.ESemana;
import edu.senai.integrador.beans.academia.Modalidade;
import edu.senai.integrador.beans.academia.ModalidadeException;
import edu.senai.integrador.beans.pessoa.PessoaException;
import edu.senai.integrador.dao.DAOException;
import edu.senai.integrador.dao.EDAOErros;
import edu.senai.integrador.dao.ModalidadeDAO;

public class MenuModalidade extends Executavel {
	private ModalidadeDAO modalidadeDao = new ModalidadeDAO();	
	private Modalidade modalidade = new Modalidade();
	private Map<String, Modalidade> modalidades = new HashMap<String, Modalidade>();
	
	public MenuModalidade() {
	}

	public boolean principal() throws InterruptedException {
		while (true) {
			try {
				limpaConsole();
				printaVerde(text.MENU_MODALIDADES
							   + "1. Cadastra Modalidades\n"
							   + "2. Consulta Modalidades\n"
							   + "3. Altera   Modalidades\n"
							   + "4. Exclui   Modalidades\n"
							   + "5. Voltar\n"
							   + "Digite a opção desejada: ");
				lePreto();
				switch (leitor.leInt()) {
				default:
					printaWarning(text.OPCAO_INVALIDA, new LeituraException(ELeituraErro.VALOR_INVALIDO));
					continue;
				case 1:
					cadastraModalidades();
					continue;
				case 2:
					consultaModalidades();
					continue;
				case 3:
					alteraModalidades();
					continue;
				case 4:
					excluiModalidades();
					continue;
				case 5:
					break;
				}
				break;
			} catch (Exception e) {
				printaWarning(ELeituraErro.VALOR_INVALIDO.toString(), e);
			}
		}
		return false;
	}
	
	private boolean cadastraModalidades() throws InterruptedException {
		printaVerdeln(text.MOD_CADASTRA);
		while (true) {
			limpaConsole();
			try {
				printaVerde(text.MOD_ID);
				lePreto();
				modalidade.setIdModalidade(leitor.leNome());
			} catch (PessoaException | ModalidadeException e) {
				printaWarning(e.getMensagem().toString(), e);
				continue;
			} catch (Exception e) {
				printaWarning(ELeituraErro.VALOR_INVALIDO.toString(), e);
				continue;
			}
			break;
		}
		while (true) {
			limpaConsole();
			try {
				printaVerde(text.MOD_MINIMO);
				lePreto();
				modalidade.setMinimoParticipantes(leitor.leInt());
			} catch (ModalidadeException e) {
				printaWarning(e.getMensagem().toString(), e);
				continue;
			} catch (Exception e) {
				printaWarning(ELeituraErro.VALOR_INVALIDO.toString(), e);
				continue;
			}
			break;
		}
		String diasSemana = "";
		int semana = 0;
		while (semana < 7) {
			limpaConsole();
			try {
				printaVerde(text.MOD_DIA_DA + ESemana.values()[semana] +
							  text.MOD_SEMANA);
				lePreto();
				diasSemana += leitor.leBoolean().matches("true")? 's':'n';
				if(semana == 6) modalidade.setSemana(diasSemana);
			} catch (ModalidadeException | LeituraException e) {
				printaWarning(e.getMensagem().toString(), e);
				continue;
			} catch (Exception e) {
				printaWarning(ELeituraErro.VALOR_INVALIDO.toString(), e);
				continue;
			}
			semana++;
		}
		try {
			printaPretoln("\n" + modalidade.toString());
			printaVermelholn(text.CONFIRMA_INSERT);
			lePreto();
			if(leitor.leBoolean().matches("true"))
				modalidadeDao.insere(modalidade);
			else return false;
		} catch(ConexaoException | LeituraException e2) {
			printaWarning(e2.getMensagem().toString(), e2);
			return false;
		} catch (Exception e2) {
			printaWarning(EDAOErros.INSERE_DADO.toString(), e2);
		}
		printaVerde(text.MOD_SUCESSO);
		return true;
	}
	
	private void consultaModalidades() throws InterruptedException {
		while (true) {
			try {
				modalidades = modalidadeDao.consultaTodos();
			} catch(ConexaoException | DAOException | SQLException e) {
				printaWarning(EDAOErros.CONSULTA_TODOS.getMensagem().toString(), e);
			}
			try {
				limpaConsole();
				printaVerde(text.MOD_CONSULTA  
						  + "\n1. Por nome\n"
						  + "2. Ver todos\n"
						  + "3. Voltar\n"
						  + "Digite a opÃ§Ã£o desejada: ");
				lePreto();
				switch(leitor.leInt()) {
				default:
					printaWarning(text.OPCAO_INVALIDA, new LeituraException(ELeituraErro.VALOR_INVALIDO));
					continue;
				case 1: 
					limpaConsole();
					printaVerde(text.MOD_ID);
					lePreto();
					String filtro = leitor.leString(1).toLowerCase();
					modalidades.forEach((string, modal) -> {
						try {
							printaAzulln(modal.getIdModalidade().toLowerCase().contains(filtro)	?  
										 "\n" + modal.toString() : "");
						} catch (ModalidadeException e) {
							printaVermelholn(e.getMensagem().toString());
						}
					});
					continue;
				case 2: 
					limpaConsole();
					printaVerde(text.MOD_LISTA);
					lePreto();
					modalidades.forEach((id, modal) -> printaVerde("\n" + modal.toString() + "\n"));
					continue;
				case 3:
					break;
				}
				break;
			} catch (Exception e) {
				printaWarning(ELeituraErro.VALOR_INVALIDO.toString(), e);
				continue;
			}
		}
	}
	
	private boolean alteraModalidades() throws InterruptedException {
		try {
			while (true) {
				limpaConsole();
				printaVerde(text.MOD_ALTERA + 
					"\nDigite o nome da modalidade a ser alterada: ");
				lePreto();
				try {
					modalidade = modalidadeDao.consulta(leitor.leString(5));
				} catch (LeituraException | DAOException e) {
					printaWarning(e.getMensagem().toString(), e);
					continue;
				} catch (SQLException | ConexaoException e) {
					printaWarning(e.getMessage().toString(), e);
					continue;
				}
				break;
			}
			while (true) {
				limpaConsole();
				printaVerde(text.MOD_ALTERA + "\n" + modalidade.toString() 
						  + "1. Alterar Mínimo de participantes\n"
						  + "2. Alterar Dias da Semana\n"
					      + "3. Voltar\n"
					      + "4. Confirmar\n"
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
							printaVerde(text.MOD_MINIMO);
							lePreto();
							modalidade.setMinimoParticipantes(leitor.leInt());
						} catch (IOException e) {
							printaWarning(ELeituraErro.VALOR_INVALIDO.toString(), e);
							continue;
						} catch (ModalidadeException e) {
							printaWarning(e.getMensagem().toString(), e);
							continue;
						}
						break;
					}
					continue;
				case 2:
					String diasSemana = "";
					int semana = 0;
					while (semana < 7) {
						limpaConsole();
						try {
							printaVerde(text.MOD_DIA_DA + ESemana.values()[semana] +
										  text.MOD_SEMANA);
							lePreto();
							diasSemana += leitor.leBoolean().matches("true")? 's':'n';
							if(semana == 6) modalidade.setSemana(diasSemana);
						} catch (IOException e) {
							printaWarning(ELeituraErro.VALOR_INVALIDO.toString(), e);
							continue;
						} catch (ModalidadeException | LeituraException e) {
							printaWarning(e.getMensagem().toString(), e);
							continue;
						}
						semana++;
					}
					continue;
				case 3:
					break;
				case 4:
					try {
						printaVerde(text.CONFIRMA_UPDATE);
						lePreto();
						if(leitor.leBoolean().matches("true")) modalidadeDao.altera(modalidade);
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
	
	public boolean excluiModalidades() throws InterruptedException {
		while (true) {
			limpaConsole();
			printaVerdeln(text.MOD_EXCLUI + text.MOD_ID);
			try {
				lePreto();
				modalidade = modalidadeDao.consulta(leitor.leString(5));
				printaPreto(modalidade.toString());
				printaVermelho(text.CONFIRMA_DELETE);
				lePreto();
				if(leitor.leBoolean().matches("true")) modalidadeDao.exclui(modalidade);
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
