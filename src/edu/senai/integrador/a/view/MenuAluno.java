package edu.senai.integrador.a.view;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import edu.senai.integrador.bancodedados.conexao.ConexaoException;
import edu.senai.integrador.beans.pessoa.Aluno;
import edu.senai.integrador.beans.pessoa.AlunoException;
import edu.senai.integrador.beans.pessoa.PessoaException;
import edu.senai.integrador.dao.AlunoDAO;
import edu.senai.integrador.dao.DAOException;
import edu.senai.integrador.dao.EDAOErros;
import edu.senai.integrador.dao.sql.SqlSintaxe;

public class MenuAluno extends MenuPessoa {
	private AlunoDAO alunoDAO = new AlunoDAO();
	
	public boolean principal() throws InterruptedException {
		while (true) {
			try {
				limpaConsole();
				printaVerde(text.MENU_ALUNOS + "\n"
							   + "1. Cadastra Alunos\n"
							   + "2. Consulta Alunos\n"
							   + "3. Altera   Alunos\n"
							   + "4. Exclui   Alunos\n"
							   + "5. Voltar\n"
							   + "Digite a opção desejada: ");
				lePreto();
				switch (leitor.leInt()) {
				default:
					printaWarning(text.OPCAO_INVALIDA.toString(), 
							new LeituraException(ELeituraErro.VALOR_INVALIDO));
					continue;
				case 1:
					cadastraAlunos();
					menuUsuario.cadastraUsuario(aluno);
					continue;
				case 2:
					consultaAlunos();
					continue;
				case 3:
					alteraAluno();
					continue;
				case 4:
					excluiAluno();
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
	public boolean cadastraAlunos() throws InterruptedException {
		aluno = new MenuPessoa().criaAluno();
		
		while (true) {
			limpaConsole();
			try {
				printaVerde(text.ALTURA);
				lePreto();
				aluno.setAltura(leitor.leAltura());
				break;
			} catch (PessoaException | AlunoException e) {
				printaWarning(e.getMensagem().toString(), e);
				continue;
			} catch (Exception e) {
				printaWarning(ELeituraErro.NUMERO_INVALIDO.toString(), e);
				continue;
			}
		}
		while (true) {
			limpaConsole();
			try {
				printaVerde(text.PESO);
				lePreto();
				aluno.setPeso(leitor.lePeso());
				break;
			} catch (PessoaException | AlunoException e) {
				printaWarning(e.getMensagem().toString(), e);
				continue;
			} catch (Exception e) {
				printaWarning(ELeituraErro.NUMERO_INVALIDO.toString(), e);
				continue;
			}
		}
		try {
			printaPreto("\n" + aluno.toString());
			printaVermelho(text.CONFIRMA_INSERT);
			lePreto();
			if(leitor.leBoolean().matches("true"))
				alunoDAO.insere(aluno);
			else return false;
		} catch(ConexaoException |LeituraException e2) {
			printaWarning(e2.getMensagem().toString(), e2);
			return false;
		} catch (SQLException | DAOException | IOException e2) {
			try {
				if(((SQLException)e2).getErrorCode() == Integer.parseInt(
						new SqlSintaxe().DUPLICATE_PK))
					if (!aluno.isAtivo()) {
						printaWarning(EDAOErros.REGISTRO_DUPLICADO.getMensagem().toString(), e2);
						return false;
					} else {
						printaVerde(EDAOErros.REGISTRO_INATIVO.getMensagem().toString());
						while (true) {
							try {
								if (leitor.leBoolean().matches("true")) {
									aluno.setAtivo(true);
									alunoDAO.altera(aluno);
								}
								else return false;
								 limpaConsole();
							} catch (IOException | SQLException e) {
								printaWarning(ELeituraErro.VALOR_INVALIDO.toString(), e);
							} catch (ConexaoException | LeituraException | DAOException e) {
								printaWarning(e.getMensagem().toString(), e);
							}
							break;
						}
					}
			} catch (Exception e1) {
				printaWarning(EDAOErros.INSERE_DADO.toString(), e1);
			} 
		}
		printaVerde(text.USR_SUCESSO);
		return true;
	}
	public boolean consultaAlunos() throws InterruptedException {
		Map<String, Aluno> alunos = null;
		try {
			alunos = alunoDAO.consultaTodos();
		} catch (ConexaoException e) {
			printaWarning(EDAOErros.CONSULTA_TODOS.getMensagem().toString(), e);
		} catch (Exception e) {
			printaWarning(EDAOErros.CONSULTA_DADO.toString(), e);
		}
		while (true) {
			try {
				limpaConsole();
				printaVerde(text.ALUNO_CONSULTA 
							   + "1. Por CPF\n"
							   + "2. Por nome\n"
							   + "3. Ver todos\n"
							   + "4. Voltar\n"
							   + "Digite a opção desejada: ");
				lePreto();
				switch (leitor.leInt()) {
				default:
					printaWarning(text.OPCAO_INVALIDA.toString(), new LeituraException(ELeituraErro.VALOR_INVALIDO));
					continue;
				case 1:
					limpaConsole();
					printaVerde(text.CPF);
					lePreto();
					printaPretoln(alunos.get(leitor.leCpf()).toString());
					continue;
				case 2:
					limpaConsole();
					printaVerde(text.USUARIO_6_20);
					lePreto();
					String filtro = leitor.leString(1).toLowerCase();
					alunos.forEach((string, teste) -> {
						try {
							printaVerdeln(
									teste.getNome().toLowerCase().contains(filtro)? 
											teste.toString() : "");
						} catch (PessoaException e) {
							printaVermelholn(e.getMensagem().toString());
						}
					});
					continue;
				case 3:
					limpaConsole();
					printaVerde(text.ALUNO_LISTA);
					try {
						alunoDAO.consultaTodos().forEach((cpf, aluno) -> printaVerde("\n" + aluno.toString() + "\n"));
					} catch(ConexaoException e) {
						printaWarning(e.getMensagem().toString(), e);
					} catch(Exception e) {
						printaWarning(ELeituraErro.VALOR_INVALIDO.toString(), e);
					}
					continue;
				case 4:
					break;
				}
				break;
			} catch (PessoaException | LeituraException e) {
				printaWarning(e.getMensagem().toString(), e);
			} catch (Exception e) {
				printaWarning(ELeituraErro.VALOR_INVALIDO.toString(), e);
			}
		}
		return true;
	}
	public boolean alteraAluno() throws InterruptedException {
		while (true) {
			try {
				limpaConsole();
				printaVerde(text.ALUNO_ALTERA + "\nDigite o CPF do aluno a ser alterado: ");
				lePreto();
				aluno = alunoDAO.consulta(leitor.leCpf());
			} catch (PessoaException e) {
				printaWarning(e.getMensagem().toString(), e);
				continue;
			} catch (Exception e) {
				printaWarning(ELeituraErro.VALOR_INVALIDO.toString(), e);
				continue;
			}
			break;
		}
		while (true) {
			try {
				limpaConsole();
				printaVerde(text.ALUNO_ALTERA + "\n" +
							aluno.toString() + "\n"
						  + "1. Alterar Nome\n"
						  + "2. Alterar Sexo\n"
						  + "3. Alterar Altura\n"
						  + "4. Alterar Peso\n"
						  + "5. Alterar Estado Civil\n"
					      + "6. Alterar Usuario\n"
					      + "7. Voltar\n"
					      + "8. Confirmar\n"
					      + "Digite a opção desejada: ");
				 lePreto();
				int i = leitor.leInt();
				 limpaConsole();
				switch (i) {
				default:
					printaWarning(text.OPCAO_INVALIDA.toString(), new LeituraException(ELeituraErro.VALOR_INVALIDO));
					continue;
				case 1:
					alteraNome(aluno);
					continue;
				case 2:
					alteraSexo(aluno);
					continue;
				case 3:
					while (true) {
						try {
							printaVerde(text.ALUNO_ALTERA + text.ALTURA);
							lePreto();
							aluno.setAltura(leitor.leAltura());
						} catch (PessoaException | AlunoException e) {
							printaWarning(e.getMensagem().toString(), e);
							continue;
						}
						break;
					}
					continue;
				case 4:
					try {
						printaVerde(text.ALUNO_ALTERA + text.PESO);
						lePreto();
						aluno.setPeso(leitor.lePeso());
					} catch (PessoaException | AlunoException e) {
						printaWarning(e.getMensagem().toString(), e);
						continue;
					}
					continue;
				case 5:
					alteraEstadoCivil(aluno);
					continue;
				case 6:
					menuUsuario.alteraUsuario(aluno);
					continue;
				case 7:
					break;
				case 8:
					try {
						alunoDAO.altera(aluno);
						printaAzulln(text.ALTERA_SUCESSO);
						continue;
					} catch (ConexaoException | DAOException e) {
						printaWarning(e.getMensagem().toString(), e);
						continue;
					} catch (SQLException e) {
						printaWarning(EDAOErros.ALTERA_DADO.toString(), e);
						continue;
					}
				}
				if (i == 7) break;
			} catch (Exception e) {
				printaWarning(ELeituraErro.VALOR_INVALIDO.toString(), e);
			}
		}
		return true;
	}
	public boolean excluiAluno() throws InterruptedException {
		while (true) {
			limpaConsole();
			printaVerde(text.ALUNO_EXCLUI + text.CPF);
			lePreto();
			try {
				aluno = alunoDAO.consulta(leitor.leCpf());
				lePreto();
				leitor.leCpf();
				printaVermelho(text.CONFIRMA_DELETE);
				lePreto();
				if(leitor.leBoolean().matches("true")) {
					menuUsuario.excluiUsuario(aluno.getCPF());
					alunoDAO.exclui(aluno);
					printaAzulln(text.EXCLUI_SUCESSO);
					return true;
				} else {
					printaVermelholn(EDAOErros.EXCLUI_DADO.toString());
					return false;
				}
			} catch (ConexaoException | DAOException | PessoaException | LeituraException e) {
				printaWarning(e.getMensagem().toString(), e);
				continue;
			} catch (Exception  e) {
				printaWarning(ELeituraErro.VALOR_INVALIDO.toString(), e);
				continue;
			}
		}
	}
}
