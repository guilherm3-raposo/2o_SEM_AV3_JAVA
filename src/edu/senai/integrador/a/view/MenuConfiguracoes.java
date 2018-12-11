package edu.senai.integrador.a.view;

import java.util.Map;

import edu.senai.integrador.bancodedados.conexao.ConexaoException;
import edu.senai.integrador.beans.cadastro.Usuario;
import edu.senai.integrador.dao.DAOException;
import edu.senai.integrador.dao.EDAOErros;
import edu.senai.integrador.dao.UsuarioDAO;

public class MenuConfiguracoes extends Executavel {
	private UsuarioDAO usuarioDAO = new UsuarioDAO();
	private Usuario usuario = new Usuario();
	
	public void principal() throws InterruptedException {
		while (true) {
			try {
				limpaConsole();
				printaVerde(text.MENU_CONFIG
							   + "1. Reconfigurar Base de dados\n"
							   + "9. Voltar\n"
							   + "Digite a opção desejada: ");
				lePreto();
				switch (leitor.leInt()) {
				default:
					printaWarning(text.OPCAO_INVALIDA, new LeituraException(ELeituraErro.VALOR_INVALIDO));
					continue;
				case 1:
					cadastraBD();
					continue;
				case 9:
					break;
				}
				break;
			} catch (Exception e) {
				printaWarning(ELeituraErro.VALOR_INVALIDO.toString(), e);
			}
		}
	}
	
	public boolean cadastraUsuario() throws InterruptedException {
		while (true) {
			limpaConsole();
			try {
				printaVerde(text.MENU_USUA + text.CPF);
				lePreto();
				usuario.setCpf(leitor.leCpf());
			} catch (Exception e) {
				printaWarning(ELeituraErro.VALOR_INVALIDO.toString(), e);
				continue;
			}
			break;
		}
		while (true) {
			limpaConsole();
			try {
				printaVerde(text.MENU_USUA + text.NOME_5_30);
				lePreto();
				usuario.setUsuario(leitor.leUsuario(6, 30));
			} catch (Exception e) {
				printaWarning(ELeituraErro.VALOR_INVALIDO.toString(), e);
				continue;
			}
			break;
		}
		while (true) {
			try {
				limpaConsole();
				printaVerde(text.MENU_USUA + text.SENHA_5_20);
				lePreto();
				usuario.setSenha(leitor.leSenha(5));
			} catch (Exception e) {
				printaWarning(ELeituraErro.VALOR_INVALIDO.toString(), e);
				continue;
			}
			break;
		}
		
		if(permissao == 0) {
			while (true) {
				try {
					limpaConsole();
					printaVerde(text.MENU_USUA + text.LOGIN_PERMISSAO);
					lePreto();
					usuario.setPermissao(leitor.leBoolean().matches("true") ? 2 : 1);
				} catch (Exception e) {
					printaWarning(ELeituraErro.VALOR_INVALIDO.toString(), e);
					continue;
				}
				break;
			}
		}
		
		try {
			printaPretoln("\n" + usuario.toString());
			printaVermelholn(text.CONFIRMA_INSERT);
			lePreto();
			String confirma = leitor.leBoolean();
			if(confirma.matches("true"))
				usuarioDAO.insere(usuario);
			else return false;
		} catch(ConexaoException | LeituraException e2) {
			printaWarning(e2.getMensagem().toString(), e2);
			return false;
		} catch (Exception e2) {
			printaWarning(EDAOErros.INSERE_DADO.toString(), e2);
			return false;
		}
		printaAzulln(text.TURMA_SUCESSO);
		return true;
	}
	
	public void consultaUsuario() throws InterruptedException {
		while (true) {
			try {
				limpaConsole();
				printaVerde(text.LOGIN_CONSULTA);
				lePreto();
				usuario = usuarioDAO.consulta(leitor.leUsuario(6, 20));
				printaVerde(usuario.toString());
				break;
			} catch (Exception e) {
				printaWarning(ELeituraErro.VALOR_INVALIDO.toString(), e);
				continue;
			}
		}
	}
	
	public boolean alteraUsuario() throws InterruptedException {
		try {
			while (true) {
				if (permissao == 0) {
					Map<String, Usuario> usuarios = usuarioDAO.consultaTodos();
					usuarios.forEach((codigo, usuario) -> printaPretoln(codigo + usuario.toString()));
				}
				limpaConsole();
				
				printaVerde(text.LOGIN_ALTERA + text.LOGIN_USR);
				lePreto();
				try {
					usuario = usuarioDAO.consulta(leitor.leString(5));
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
				limpaConsole();
				printaVerde(text.LOGIN_ALTERA + login.toString() 
					      + "Digite o novo nome de usuário: ");
				lePreto();
				try {
					printaVerde(text.CONFIRMA_UPDATE);
					lePreto();
					if(leitor.leBoolean().matches("true")) usuarioDAO.altera(usuario);
					break;
				} catch (ConexaoException | DAOException | LeituraException e) {
					printaWarning(e.getMensagem().toString(), e);
					continue;
				}
			}
		} catch (Exception e) {
			printaWarning(EDAOErros.ALTERA_DADO.toString(), e);
		}
		return true;
	}
	
	public boolean excluiUsuario() throws InterruptedException {
		while (true) {
			limpaConsole();
			printaVerde(text.LOGIN_EXCLUI + text.LOGIN_USR);
			try {
				lePreto();
				usuario = usuarioDAO.consulta(leitor.leString(5));
				printaPreto(usuario.toString());
				printaVermelho(text.CONFIRMA_DELETE);
				lePreto();
				if(leitor.leBoolean().matches("true")) usuarioDAO.exclui(usuario.getUsuario());
				else continue;
			} catch (ConexaoException | DAOException | LeituraException e) {
				printaWarning(e.getMensagem().toString(), e);
				continue;
			} catch (Exception  e) {
				printaWarning(ELeituraErro.VALOR_INVALIDO.toString(), e);
				continue;
			}
			printaAzulln(text.EXCLUI_SUCESSO);
			break;
		}
		return true;
	}
}
