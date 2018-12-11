package edu.senai.integrador.a.view;

import edu.senai.integrador.beans.pessoa.Aluno;
import edu.senai.integrador.beans.pessoa.AlunoException;
import edu.senai.integrador.beans.pessoa.Funcionario;
import edu.senai.integrador.beans.pessoa.FuncionarioException;
import edu.senai.integrador.beans.pessoa.Pessoa;
import edu.senai.integrador.beans.pessoa.PessoaException;

public class MenuPessoa extends Executavel {
	MenuUsuario menuUsuario = new MenuUsuario();
	Funcionario funcionario = new Funcionario();
	Aluno aluno = new Aluno();
	
	public Funcionario criaFuncionario() throws InterruptedException {
		limpaConsole();
		printaVerdeln(text.FUNC_CADASTRA);
		criaPessoa(funcionario, false);
		return funcionario;
	}
	
	public Aluno criaAluno() throws InterruptedException {
		limpaConsole();
		printaVerdeln(text.ALUNO_CADASTRA);
		criaPessoa(aluno, true);
		return aluno;
	}	
	
	public Pessoa criaPessoa(Pessoa pessoa, boolean isAluno) throws InterruptedException {
		while (true) {
			try {
				printaVerde(text.NOME_5_30);
				lePreto();
				pessoa.setNome(leitor.leNome());
				break;
			} catch (PessoaException e) {
				printaWarning(e.getMensagem().toString(), e);
				limpaConsole();
				printaVerdeln(isAluno?text.MENU_ALUNOS : text.MENU_FUNCIONARIOS);
				continue;
			} catch (Exception e) {
				printaWarning(ELeituraErro.NUMERO_INVALIDO.toString(), e);
				continue;
			}
		}
		while(true) {
			limpaConsole();
			try {
				printaVerde(text.CPF);
				lePreto();
				pessoa.setCPF(leitor.leCpf());
				break;
			} catch (PessoaException e) {
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
				printaVerde(text.DATA_NASC);
				lePreto();
				pessoa.setDataDeNascimento(leitor.validaPeriodo(18));
				break;
			} catch (PessoaException | AlunoException | FuncionarioException e) {
				printaWarning(e.getMensagem().toString(), e);
				continue;
			} catch (Exception e) {
				printaWarning(ELeituraErro.NUMERO_INVALIDO.toString(), e);
				continue;
			}
		}
		while(true) {
			limpaConsole();
			try {
				printaVerde(text.SEXO);
				lePreto();
				pessoa.setSexo(leitor.leSexo());
				break;
			} catch (PessoaException e) {
				printaWarning(e.getMensagem().toString(), e);
				continue;
			} catch (Exception e) {
				printaWarning(ELeituraErro.NUMERO_INVALIDO.toString(), e);
				continue;
			}
		}
		while(true) {
			limpaConsole();
			printaVerde(text.ESTADO_CIVIL);
			lePreto();
			try {
				pessoa.setEstadoCivil(leitor.leEstadoCivil());
				pessoa.setAtivo(true);
			} catch (PessoaException e) {
				printaWarning(e.getMensagem().toString(), e);
				continue;
			} catch (Exception e) {
				printaWarning(ELeituraErro.VALOR_INVALIDO.toString(), e);
				continue;
			}
			break;
		}
		return pessoa;
	}
	
	public void alteraNome(Pessoa pessoa) throws InterruptedException {
		while (true) {
			try {
				printaVerde(pessoa instanceof Aluno ? 
						text.ALUNO_ALTERA + text.NOME_5_30 : text.FUNCIO_ALTERA + text.NOME_5_30);
				lePreto();
				pessoa.setNome(leitor.leNome());
			} catch (PessoaException e) {
				printaWarning(e.getMensagem().toString(), e);
				continue;
			} catch (Exception e) {
				printaWarning(ELeituraErro.VALOR_INVALIDO.toString(), e);
				continue;
			}
			break;
		}
	}
	
	public void alteraEstadoCivil(Pessoa pessoa) throws InterruptedException {
		while (true) {
			try {
				printaVerde((pessoa instanceof Funcionario ? 
						text.FUNCIO_ALTERA : 
							text.ALUNO_ALTERA) + text.ESTADO_CIVIL);
				lePreto();
				pessoa.setEstadoCivil(leitor.leEstadoCivil());
			} catch (PessoaException e) {
				printaWarning(e.getMensagem().toString(), e);
				continue;
			} catch (Exception e) {
				printaWarning(ELeituraErro.VALOR_INVALIDO.toString(), e);
				continue;
			}
			break;
		}
	}
	public void alteraSexo(Pessoa pessoa) throws InterruptedException {
		while (true) {
			try {
				printaVerde((pessoa instanceof Funcionario ? 
						text.FUNCIO_ALTERA : 
							text.ALUNO_ALTERA) + text.SEXO);
				lePreto();
				aluno.setSexo(leitor.leSexo());
			} catch (PessoaException e) {
				printaWarning(e.getMensagem().toString(), e);
				continue;
			} catch (Exception e) {
				printaWarning(ELeituraErro.VALOR_INVALIDO.toString(), e);
				continue;
			}
			break;
		}
	}
	public void alteraNascimento(Pessoa pessoa) throws InterruptedException {
		while (true) {
			try {
				printaVerde((pessoa instanceof Funcionario ? 
						text.FUNCIO_ALTERA : 
							text.ALUNO_ALTERA) + text.DATA_NASC);
				lePreto();
				aluno.setDataDeNascimento(leitor.leData());
			} catch (PessoaException e) {
				printaWarning(e.getMensagem().toString(), e);
				continue;
			} catch (Exception e) {
				printaWarning(ELeituraErro.VALOR_INVALIDO.toString(), e);
				continue;
			}
			break;
		}
	}
}
