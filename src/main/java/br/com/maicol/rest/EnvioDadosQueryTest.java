package br.com.maicol.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

import org.junit.Test;

import io.restassured.http.ContentType;

public class EnvioDadosQueryTest {
	
	@Test
	public void envioDeDadosQuaryXML() {
		
		given()
			.log().all()
		.when()
			.get("https://restapi.wcaquino.me/v2/users?format=xml")
		.then()
			.log().all()
			.statusCode(200)
			.contentType(ContentType.XML)
		;
	}
	
	@Test
	public void envioDeDadosQuaryJSON() {
		
		given()
			.log().all()
		.when()
			.get("https://restapi.wcaquino.me/v2/users?format=json")
		.then()
			.log().all()
			.statusCode(200)
			.contentType(ContentType.JSON)
		;
	}
	
	@Test
	public void enviodeQuaryViaParemetrosJSON() {
		given()
		.log().all()
		.queryParam("format", "json")
	.when()
		.get("https://restapi.wcaquino.me/v2/users")
	.then()
		.log().all()
		.statusCode(200)
		.contentType(ContentType.JSON)
	;

	}
	
	@Test
	public void enviodeQuaryViaParemetrosXML() {
		given()
		.log().all()
		.queryParam("format", "xml")
	.when()
		.get("https://restapi.wcaquino.me/v2/users")
	.then()
		.log().all()
		.statusCode(200)
		.contentType(ContentType.XML)
		.contentType(containsString("utf-8"))
	;

	}
	
	@Test
	public void envioDeDadosViaHeader() {
		
		given()
			.log().all()
			.accept(ContentType.JSON)
		.when()
			.get("https://restapi.wcaquino.me/v2/users")
		.then()
			.log().all()
			.statusCode(200)
//			.contentType(ContentType.HTML)
			.contentType(containsString("utf-8"))
		;
	}
}
