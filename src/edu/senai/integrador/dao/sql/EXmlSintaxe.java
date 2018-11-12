package edu.senai.integrador.dao.sql;

public enum EXmlSintaxe {
	 CHAR		(LeXml.getTag("sintaxe")[0]),
	 VARCHAR	(LeXml.getTag("sintaxe")[1]),
	 DATE		(LeXml.getTag("sintaxe")[2]),
	 DATETIME	(LeXml.getTag("sintaxe")[3]),
	 COMMA		(LeXml.getTag("sintaxe")[4]),
	 IN			(LeXml.getTag("sintaxe")[5]),
	 ON			(LeXml.getTag("sintaxe")[6]),
	 NOT		(LeXml.getTag("sintaxe")[7]),
	 AND		(LeXml.getTag("sintaxe")[8]),
	 NULL		(LeXml.getTag("sintaxe")[9]),
	 WHERE		(LeXml.getTag("sintaxe")[10]),
	 VALUES		(LeXml.getTag("sintaxe")[11]),
	 INNER		(LeXml.getTag("sintaxe")[12]),
	 LEFT		(LeXml.getTag("sintaxe")[13]),
	 RIGHT		(LeXml.getTag("sintaxe")[14]),
	 FULL		(LeXml.getTag("sintaxe")[15]),
	 SELF		(LeXml.getTag("sintaxe")[16]),
	 JOIN		(LeXml.getTag("sintaxe")[17]),
	 GROUP		(LeXml.getTag("sintaxe")[18]),
	 ORDER		(LeXml.getTag("sintaxe")[19]),
	 BY			(LeXml.getTag("sintaxe")[20]),
	 HAVING		(LeXml.getTag("sintaxe")[21]),
	 EXISTS		(LeXml.getTag("sintaxe")[22]),
	 ANY		(LeXml.getTag("sintaxe")[23]),
	 ALL		(LeXml.getTag("sintaxe")[24]),
	 CASE		(LeXml.getTag("sintaxe")[25]),
	 FROM		(LeXml.getTag("sintaxe")[26]),
	 INSERT		(LeXml.getTag("sintaxe")[27]),
	 CREATE		(LeXml.getTag("sintaxe")[28]),
	 SELECT		(LeXml.getTag("sintaxe")[29]),
	 UPDATE		(LeXml.getTag("sintaxe")[30]),
	 DELETE		(LeXml.getTag("sintaxe")[31]),
	 INTO		(LeXml.getTag("sintaxe")[32]),
	 DATABASE	(LeXml.getTag("sintaxe")[33]),
	 SET		(LeXml.getTag("sintaxe")[34]),
	 EQUALS		(LeXml.getTag("sintaxe")[35]),
	 END_LINE	(LeXml.getTag("sintaxe")[36]),
	 CLOSE		(LeXml.getTag("sintaxe")[37]);
	

	private String campo;

	private EXmlSintaxe(String campo) {
		this.campo = campo;
	}

	public String getCampo() {
		return campo;
	}
	
	@Override
	public String toString() {
		return this.getCampo();
	}
}
