package edu.senai.integrador.ferramentas;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FormataDadosTestes {

	@BeforeEach
	void setUp() throws Exception {
		
	}

	@AfterEach
	void breakDown() throws Exception {
		
	}
	
	@Test
	void testaFormataCpfBasico() {
		String resultado = "133.333.421-22";
		assertEquals(resultado, FormataDados.formataCpf("13333342122"));
	}
	
	@Test
	void testaFormataTelefoneBasico() {
		String resultado = "(XX)998-899-222";
		assertEquals(resultado, FormataDados.formataTelefone("998899222"));
	}
	
	@Test
	void testaFormataAlturaBasico() {
		String resultado = "1 metro e 70 centímetros";
		assertEquals(resultado, FormataDados.formataAltura(1.70));
	}
	
	@Test
	void testeFormataPesoBasico() {
		String resultado = "70.00Kg";
		assertEquals(resultado, FormataDados.formataPeso(70));
	}
}
