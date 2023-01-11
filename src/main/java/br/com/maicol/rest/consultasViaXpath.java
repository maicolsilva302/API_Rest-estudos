package br.com.maicol.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasXPath;
import static org.hamcrest.Matchers.is;

import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;

public class consultasViaXpath {
	
	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = "http://restapi.wcaquino.me";
		RestAssured.port = 80;
//		RestAssured.basePath = "v2";
	}
	
	@Test
	public void devoFAzerConsultasViaXpath() {
		
		given()
			.log().all()
		.when()
			.get("/usersXML")
		.then()
			.statusCode(200)
			.body(hasXPath("count(/users/user)", is("3")))
			.body(hasXPath("/users/user[@id = '1']"))
			.body(hasXPath("//*[contains(text(),'1')]"))
			.body(hasXPath("//name[text() = 'Luizinho']/../../name", is("Ana Julia")))
			.body(hasXPath("//name[text()='Ana Julia']/following-sibling::filhos", allOf(containsString("Zezinho"), containsString("Luizinho"))))
			.body(hasXPath("/users/user[2]/name", is("Maria Joaquina")))
			.body(hasXPath("/users/user[last()]/name", is("Ana Julia")))
			.body(hasXPath("//name", is("João da Silva")))
			.body(hasXPath("count(/users/user/name[contains(., 'n')])", is("2")))
			.body(hasXPath("//user[age < 24]/name", is("Ana Julia")))
			.body(hasXPath("//user[age > 20 and age < 30]/name", is("Maria Joaquina")))
			.body(hasXPath("//user[age > 20][age < 30]/name", is("Maria Joaquina")))
			
			;
		
	}

}
