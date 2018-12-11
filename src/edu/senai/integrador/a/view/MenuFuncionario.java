package edu.senai.integrador.a.view;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import edu.senai.integrador.bancodedados.conexao.ConexaoException;
import edu.senai.integrador.beans.pessoa.Funcionario;
import edu.senai.integrador.beans.pessoa.FuncionarioException;
import edu.senai.integrador.beans.pessoa.PessoaException;
import edu.senai.integrador.dao.DAOException;
import edu.senai.integrador.dao.EDAOErros;
import edu.senai.integrador.dao.FuncionarioDAO;
import edu.senai.integrador.dao.sql.SqlSintaxe;

public class MenuFuncionario extends MenuPessoa {
	private FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
	
	public MenuFuncionario() {
	}

	public boolean principal() throws InterruptedException {
		while (true) {
			try {
				limpaConsole();
				printaVerde(text.MENU_FUNCIONARIOS 
					    + "1. Cadastra Funcionários\n"
						+ "2. Consulta Funcionários\n"
						+ "3. Altera   Funcionários\n"
						+ "4. Exclui   Funcionários\n"
						+ "5. Voltar\n"
						+ "Digite a opção desejada: ");
				lePreto();
				switch (leitor.leInt()) {
				default:
					printaWarning(text.OPCAO_INVALIDA, new LeituraException(ELeituraErro.VALOR_INVALIDO));
					continue;
				case 1:
					cadastraFuncionarios();
					menuUsuario.cadastraUsuario(funcionario);
					continue;
				case 2:
					consultaFuncionarios();
					continue;
				case 3:
					alteraFuncionario();
					continue;
				case 4:
					excluiFuncionario();
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
	
	private boolean consultaFuncionarios() throws InterruptedException {
		Map<String, Funcionario> funcionarios = null;
		while (true) {
			try {
				funcionarios = funcionarioDAO.consultaTodos();
			} catch (ConexaoException | DAOException e1) {
				printaWarning(e1.getMensagem(), e1);
			} catch (SQLException e) {
				printaWarning(EDAOErros.CONSULTA_TODOS.getMensagem(), e);
			}
			try {
				limpaConsole();
				printaVerde(text.FUNC_CONSULTA
					  + "\n1. Por CPF\n"
						+ "2. Por nome\n"
						+ "3. Ver Todos\n"
						+ "4. Voltar\n"
						+ "Digite a opção desejada: ");
				lePreto();
				switch (leitor.leInt()) {
				default:
					printaWarning(text.OPCAO_INVALIDA, new LeituraException(ELeituraErro.VALOR_INVALIDO));
					continue;
				case 1:
					limpaConsole();
					printaVerde(text.CPF);
					lePreto();
					printaAzulln(funcionarios.get(leitor.leCpf()).toString());
					continue;
				case 2:
					limpaConsole();
					printaVerde(text.USUARIO_6_20);
					lePreto();
					String filtro = leitor.leString(1).toLowerCase();
					funcionarios.forEach((string, teste) -> {
						try {
							printaAzulln(
								teste.getNome().toLowerCase().contains(filtro)? 
									teste.toString() : "");
						} catch (PessoaException e) {
							printaVermelholn(e.getMensagem().toString());
						}
					});
					continue;
				case 3:
					limpaConsole();
					printaVerde(text.FUNC_LISTA);
					lePreto();
					try {
						funcionarios.forEach((cpf, func) -> printaVerde("\n" + func.toString() + "\n"));
					} catch(Exception e) {
						printaVermelholn(e.getMessage().toString());
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

	private boolean excluiFuncionario() throws InterruptedException {
		while (true) {
			limpaConsole();
			printaVerde(text.FUNCIO_EXCLUI + text.CPF);
			lePreto();
			try {
				funcionario = funcionarioDAO.consulta(leitor.leCpf());
				printaPreto(funcionario.toString());
				printaVermelho(text.CONFIRMA_DELETE);
				lePreto();
				if(leitor.leBoolean().matches("true")) {
					menuUsuario.excluiUsuario(funcionario.getCPF());
					funcionarioDAO.exclui(funcionario.getCPF());
					printaAzulln(text.EXCLUI_SUCESSO);
					return true;
				} else {
					printaVermelholn(EDAOErros.EXCLUI_DADO.toString());
					return false;
				}
				
			} catch (ConexaoException | DAOException | PessoaException | LeituraException e) {
				printaWarning(e.getMensagem().toString(), e);
				continue;
			} catch (Exception e) {
				printaWarning(ELeituraErro.VALOR_INVALIDO.toString(), e);
				continue;
			}
		}
	}

	private boolean cadastraFuncionarios() throws InterruptedException {
		funcionario = new MenuPessoa().criaFuncionario();
		
		while (true) {
			limpaConsole();
			try {
				printaVerde(text.ESCOLARIDADE);
				lePreto();
				funcionario.setEscolaridade(leitor.leEscolaridade());
				break;
			} catch (PessoaException | FuncionarioException e) {
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
				printaVerde(text.CTPS);
				lePreto();
				funcionario.setCtps(leitor.leCtps());
				break;
			} catch (LeituraException | PessoaException | FuncionarioException e) {
				printaWarning(e.getMensagem().toString(), e);
				continue;
			} catch (Exception e) {
				printaWarning(ELeituraErro.NUMERO_INVALIDO.toString(), e);
				continue;
			}
		}
		try {
			printaPretoln("\n" + funcionario.toString());
			printaVermelho(text.CONFIRMA_INSERT);
			lePreto();
			if(leitor.leBoolean().matches("true"))
				funcionarioDAO.insere(funcionario);
			else return false;		
			} catch(ConexaoException | LeituraException e2) {
				printaWarning(e2.getMensagem().toString(), e2);
				return false;
		} catch (SQLException | DAOException | IOException e2) {
			try {
				if(((SQLException)e2).getErrorCode() == Integer.parseInt(new SqlSintaxe().DUPLICATE_PK))
					if (!funcionario.isAtivo()) {
						printaWarning(EDAOErros.REGISTRO_DUPLICADO.getMensagem().toString(), e2);
						return false;
					} else {
						printaVermelholn(EDAOErros.REGISTRO_INATIVO.getMensagem().toString());
						while (true) {
							try {
								if (leitor.leBoolean().matches("true")) {
									funcionario.setAtivo(true);
									funcionarioDAO.altera(funcionario);
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
			} catch (NumberFormatException | PessoaException e) {
				printaWarning(ELeituraErro.VALOR_INVALIDO.toString(), e);
			} catch (Exception e) {
				printaWarning(EDAOErros.INSERE_DADO.toString(), e);
			}
		}
		printaAzulln(text.USR_SUCESSO);
		return true;
	}
	
	private boolean alteraFuncionario() throws InterruptedException, ConexaoException, DAOException, SQLException {
		while (true) {
			try {
				limpaConsole();
				printaVerde(text.FUNCIO_ALTERA + "\nDigite o CPF do funcionário a ser alterado: ");
				lePreto();
				funcionario = funcionarioDAO.consulta(leitor.leCpf());
			} catch (DAOException | PessoaException e) {
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
				printaVerde(text.FUNCIO_ALTERA + "\n" +
							funcionario.toString() 
							+ "\n1. Alterar Nome\n"
							+ "2. Alterar Data de Nascimento\n"
							+ "3. Alterar CTPS\n"
							+ "4. Alterar Sexo\n"
							+ "5. Alterar Estado Civil\n"
							+ "6. Alterar Escolaridade\n"
							+ "7. Alterar Usuario\n"
							+ "8. Voltar\n"
							+ "0. Confirmar\n\n"
							+ "Digite a opção desejada: ");
				lePreto();
				int i = leitor.leInt();
				limpaConsole();
				switch (i) {
				default:
					printaWarning(text.OPCAO_INVALIDA, new LeituraException(ELeituraErro.VALOR_INVALIDO));
					continue;
				case 1:
					alteraNome(funcionario);
					continue;
				case 2:
					alteraNascimento(funcionario);
					continue;
				case 3:
					while (true) {
						try {
							printaVerde(text.FUNCIO_ALTERA + text.CTPS + "\n");
							lePreto();
							funcionario.setCtps(leitor.leCtps());
						} catch (PessoaException | FuncionarioException | LeituraException e) {
							printaWarning(e.getMensagem().toString(), e);
							continue;
						} catch (Exception e) {
							printaWarning(ELeituraErro.VALOR_INVALIDO.toString(), e);
						}
						break;
					}
					continue;
				case 4:
					alteraSexo(funcionario);
					continue;
				case 5:
					alteraEstadoCivil(funcionario);
					continue;
				case 6:
					while (true) {
						try {
							printaVerde(text.FUNCIO_ALTERA + text.ESCOLARIDADE);
							lePreto();
							funcionario.setEscolaridade(leitor.leEscolaridade());
						} catch (PessoaException | FuncionarioException e) {
							printaWarning(e.getMensagem().toString(), e);
							continue;
						} catch (Exception e) {
							printaWarning(ELeituraErro.VALOR_INVALIDO.toString(), e);
						}
						break;
					}
					continue;
				case 7: 
					menuUsuario.alteraUsuario(funcionario);
					continue;
				case 8:
					break;
				case 0:
					try {
						funcionarioDAO.altera(funcionario);
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
			} catch (Exception e) {
				printaWarning(ELeituraErro.VALOR_INVALIDO.toString(), e);
			}
		return true;
		}
	}
}
