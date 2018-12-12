package edu.senai.integrador.dao;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.senai.integrador.beans.cadastro.Contato;
import edu.senai.integrador.beans.pessoa.EEscolaridade;
import edu.senai.integrador.beans.pessoa.EEstadoCivil;
import edu.senai.integrador.beans.pessoa.ESexo;
import edu.senai.integrador.beans.pessoa.Funcionario;

class ContatoDAOTestes {

	private Funcionario funcionario;
	private Contato contatoFuncionario;
	private ContatoDAO contatoDao;

	@Before
	void setUp() throws Exception {
		contatoDao = new ContatoDAO();
		
	}
	
	@BeforeEach
	void creating() throws Exception {
		funcionario = new Funcionario("47331233882", 
				"32132122", 
				EEscolaridade.SUPERIOR_INCOMPLETO, 
				"Funcionario 1",
				LocalDate.of(1998, 11, 01),
				ESexo.MASCULINO,
				EEstadoCivil.SOLTEIRO,
				true);
		contatoFuncionario = new Contato(funcionario.getCPF(), "4733678990", "47996366020", funcionario.getNome().toLowerCase() + "@hotmail.com");
	}

	@After
	void tearDown() throws Exception {
	}

	@Test
	void testaInsercaoDeContatoParaOFuncionario() {
		boolean resultado = true;
		try{
			assertEquals(resultado, contatoDao.insere(contatoFuncionario));
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	void testaAlteracaoDeContatoParaOFuncionario() {
		boolean resultado = true;
		try {
			contatoFuncionario.setTelefoneFixo("4733909090");
			assertEquals(resultado, contatoDao.altera(contatoFuncionario));
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	void testaConsultandoUmContatoESeRealmenteRetornaUmContato() {
		try {
			assertEquals(contatoFuncionario, contatoDao.consulta(funcionario.getCPF()));
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
