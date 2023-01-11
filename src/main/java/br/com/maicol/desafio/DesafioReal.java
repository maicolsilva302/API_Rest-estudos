package br.com.maicol.desafio;

import static io.restassured.RestAssured.given;

import org.junit.Test;

public class DesafioReal {
	
	@Test
	public void naoDeveAcessarAPIsemToken() {
		
//		baseURI = "http://seubarriga.wcaquino.me/contas";
//		basePath = "v1"; Versão da Aplicação
//		port = 80;
		
		given()
			.log().all()
		.when()
			.get("http://seubarriga.wcaquino.me/contas")
		.then()
			.log().all()
//			.statusCode(400)
		;
	}

}
