package br.com.maicol.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import io.restassured.path.xml.XmlPath.CompatibilityMode;

public class AuthTest {
	
	@Test
	public void deveAcessarSW_Api() {
		given()
			.log().all()
		.when()
			.get("https://swapi.dev/api/people/1/")
		.then()
			.log().all()
			.statusCode(200)
			.body("name", is("Luke Skywalker"))
		;
	}
	
	@Test
	public void naoDeveAcessarSemSenha() {
		given()
			.log().all()
		.when()
			.get("https://restapi.wcaquino.me/basicauth")
		.then()
			.log().all()
			.statusCode(401)
		;
	}
	
	@Test
	public void deveAcessarAutenticacaoBasica() {
		given()
			.log().all()
		.when()
			.get("https://admin:senha@restapi.wcaquino.me/basicauth")
		.then()
			.log().all()
			.statusCode(200)
			.body("status", is("logado"))
		;
	}
	
	@Test
	public void deveAcessarAutenticacaoBasica2() {
		given()
			.log().all()
			.auth().basic("admin", "senha")
		.when()
			.get("https://restapi.wcaquino.me/basicauth")
		.then()
			.log().all()
			.statusCode(200)
			.body("status", is("logado"))
		;
	}
	@Test
	public void deveAcessarAutenticacaoBasicaChallenge() {
		given()
			.log().all()
			.auth().preemptive().basic("admin", "senha")
		.when()
			.get("https://restapi.wcaquino.me/basicauth2")
		.then()
			.log().all()
			.statusCode(200)
			.body("status", is("logado"))
		;
	}
	
//	teste123@m.com senha: 123teste
	@Test
	public void deveAcessarAutenticacaoBasicaSeuBarriga() {
		
		Map<String, String> login = new HashMap<String, String>();
		login.put("email", "teste123@m.com");
		login.put("senha", "123teste");
		
		String token = given()
			.log().all()
			.body(login)
			.contentType(ContentType.JSON)
		.when()
			.post("http://barrigarest.wcaquino.me/signin")
		.then()
			.log().all()
			.statusCode(200)
			.body("nome", containsString("123"))
			.extract().path("token")
		;
		
		given()
		.log().all()
		.header("Authorization", "JWT " + token)
	.when()
		.get("http://barrigarest.wcaquino.me/contas")
	.then()
		.log().all()
		.statusCode(200)
		;
	}
	
	@Test
	public void deveAcessarAplicacoesWeb() {
		//LOGIN
		String cookie =	given()
				.log().all()
				.formParam("email", "teste123@m.com")
				.formParam("senha", "123teste")
				.contentType(ContentType.URLENC.withCharset("UTF-8"))
			.when()
				.post("http://seubarriga.wcaquino.me/logar")
			.then()
				.log().all()
				.statusCode(200)
				.extract().header("set-cookie")
			;
		cookie = cookie.split("=")[1].split(";")[0];
		System.out.println("\n COOKIE EXTRAÍDO: " + cookie);
		
		//OBTER CONTA
		String body = given()
				.log().all()
				.cookie("connect.sid", cookie)
			.when()
				.get("http://seubarriga.wcaquino.me/contas")
			.then()
				.log().all()
				.statusCode(200)
				.body("html.body.table.tbody.tr[0].td[0]", is("maikao"))
				.extract().body().asString()
			;
		
		System.out.println("---------------------");
		XmlPath xmlPath = new XmlPath(CompatibilityMode.HTML, body);
		System.out.println("Conta extraída: " + xmlPath.getString("html.body.table.tbody.tr[0].td[0]"));
		
	}

}
