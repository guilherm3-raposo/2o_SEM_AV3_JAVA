package edu.senai.integrador.a.view;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import edu.senai.integrador.bancodedados.conexao.ConexaoException;
import edu.senai.integrador.beans.cadastro.EUsuarioErro;
import edu.senai.integrador.beans.cadastro.Usuario;
import edu.senai.integrador.beans.cadastro.UsuarioException;
import edu.senai.integrador.beans.pessoa.EEscolaridade;
import edu.senai.integrador.beans.pessoa.EEstadoCivil;
import edu.senai.integrador.beans.pessoa.ESexo;
import edu.senai.integrador.beans.pessoa.Funcionario;
import edu.senai.integrador.beans.pessoa.Pessoa;
import edu.senai.integrador.beans.pessoa.PessoaException;
import edu.senai.integrador.dao.DAOException;
import edu.senai.integrador.dao.EDAOErros;
import edu.senai.integrador.dao.FuncionarioDAO;
import edu.senai.integrador.dao.UsuarioDAO;
import edu.senai.integrador.logs.GeraLog;

public class MenuUsuario extends Executavel {
	public UsuarioDAO usuarioDAO = new UsuarioDAO();
	public Usuario usuario = new Usuario();
	
	
	public boolean cadastraUsuario(Pessoa pessoa) throws InterruptedException {

		while (true) {
			try {
				printaVerde(text.LOGIN_INSERE + text.LOGIN_CRIA_USR);
				lePreto();
				usuario.setUsuario(leitor.leString(3));
				break;
			} catch (Exception e) {
				printaWarning(EUsuarioErro.USUARIO_INVALIDO.toString(), e);
				continue;
			}
		}
		while (true) {
			limpaConsole();
			try {
				printaVerde(text.CRIA_SENHA_5_20);
				lePreto();
				usuario.setSenha(leitor.leString(2));
				usuario.setCpf(pessoa.getCPF());
				usuario.setPermissao(1);
				break;
			} catch (Exception e) {
				printaWarning(EUsuarioErro.SENHA_TAMANHO.toString(), e);
				continue;
			}
		}
		try {
			printaPreto("\n" + usuario.toString());
			printaVermelho(text.CONFIRMA_INSERT);
			lePreto();
			if (leitor.leBoolean().matches("true"))
				usuarioDAO.insere(usuario);
			else
				return false;
		} catch (ConexaoException | LeituraException | DAOException e2) {
			printaWarning(e2.getMensagem().toString(), e2);
			return false;
		} catch (Exception e) {
			printaWarning(EDAOErros.INSERE_DADO.toString(), e);
			return false;
		}
		printaAzulln(text.USR_SUCESSO);
		return true;
	}
	
	public boolean alteraUsuario (Pessoa pessoa) throws InterruptedException {
		Usuario usuarioAlterado = null;
		try {
			usuarioAlterado = usuarioDAO.consultaCpf(pessoa.getCPF());
		} catch (ConexaoException | DAOException | SQLException | PessoaException e1) {
			printaWarning(EDAOErros.CONSULTA_DADO.toString(), e1);;
		}
		while (true) {
			try {
				printaVerde(usuarioAlterado.toString() + "\n" +
						text.LOGIN_ALTERA + text.LOGIN_CRIA_USR);
				lePreto();
				usuarioAlterado.setUsuario(leitor.leUsuario(6, 30));
			} catch (Exception e) {
				printaWarning(ELeituraErro.VALOR_INVALIDO.toString(), e);
			}
			break;
		}
		while (true) {
			try {
				printaVerde(text.LOGIN_ALTERA + text.LOGIN_SENHA);
				lePreto();
				usuarioAlterado.setSenha(leitor.leString(2));
				printaAzulln(usuarioAlterado.toString());
			} catch (Exception e) {
				printaWarning(ELeituraErro.VALOR_INVALIDO.toString(), e);
			}
			break;
		}
		while (true) {
			if (permissao == 0) {
				printaVerde(text.LOGIN_PERMISSAO);
				lePreto();
				try {
					int novaPermissao = leitor.leBoolean() == "true" ? 2 : 1;
					usuarioAlterado.setPermissao(novaPermissao);
				} catch (Exception e) {
					printaWarning(ELeituraErro.VALOR_INVALIDO.toString(), e);
				}
			} else usuarioAlterado.setPermissao(permissao+1);
			break;
		}
		try {
			usuarioDAO.altera(usuarioAlterado);
			printaAzulln(text.ALTERA_SUCESSO);
		} catch (ConexaoException | DAOException e) {
			printaWarning(e.getMensagem().toString(), e);
		} catch (Exception e) {
			printaWarning(EDAOErros.ALTERA_DADO.toString(), e);
		}
		return true;
	}
	public static boolean cadastraAdmin() throws InterruptedException {
		FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
		Funcionario funcionario = new Funcionario();
		try {
			funcionario.setCPF("00000000000");
			funcionario.setCtps("000000");
			funcionario.setDataDeNascimento(LocalDate.now());
			funcionario.setEscolaridade(EEscolaridade.SUPERIOR_INCOMPLETO);
			funcionario.setEstadoCivil(EEstadoCivil.NAO_INFORMADO);
			funcionario.setSexo(ESexo.NAO_INFORMADO);
			funcionario.setNome("ADMINISTRADOR");
			funcionario.setAtivo(true);
			funcionarioDAO.insere(funcionario);
		} catch (Exception e) {
			new GeraLog().escreveLogArquivo(e);
		}
		
		loginDao = new UsuarioDAO();
		while (true) {
			try {
				limpaConsole();
				printaVerde(text.MENU_USUA + "\n" + text.USUARIO_6_20);
				lePreto();
				login.setUsuario(leitor.leUsuario(6,30));
				login.setPermissao(0);
				break;
			} catch (UsuarioException e) {
				printaWarning(e.getMensagem().toString(), e);
				continue;
			} catch (IOException e) {
				printaWarning(EDAOErros.INSERE_DADO.toString(), e);
				continue;
			}
		}
		while (true) {
			try {
				limpaConsole();
				printaVerde(text.SENHA_5_20);
				lePreto();
				login.setCpf("00000000000");
				login.setSenha(leitor.leSenha(5));
				loginDao.insere(login);
				limpaConsole();
				printaAzulln(text.USR_SUCESSO);
				break;
			} catch (UsuarioException e) {
				printaWarning(e.getMensagem().toString(), e);
				continue;
			} catch (Exception e) {
				printaWarning(EDAOErros.INSERE_DADO.toString(), e);
				continue;
			} 
		}
		return true;
	}
	public boolean excluiUsuario(String cpf) throws InterruptedException {
		try {
			loginDao = new UsuarioDAO();
			loginDao.exclui(cpf);
		} catch (Exception e) {
			printaWarning(EDAOErros.EXCLUI_DADO.toString(), e);
		}
		return true;
	}
}
