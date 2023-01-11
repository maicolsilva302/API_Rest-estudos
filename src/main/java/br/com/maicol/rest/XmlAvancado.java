package br.com.maicol.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;

public class XmlAvancado {
	
	@BeforeClass
	public static void setup() {
		
		RestAssured.baseURI = "http://restapi.wcaquino.me";
		RestAssured.port = 80;
//		RestAssured.basePath = "v1"; //Versão da Aplicação
		
	}
	
	@Test
	public void deveTrabalharComXmlAvancados() {
		given()
			.log().all()
		.when()
			.get("/usersXML")
		.then()
			.statusCode(200)
			.rootPath("user.user")
			.body("size()", is(3))
			.body("findAll{it.age.toInteger() <=25}.size()", is(2))
			.body("@id", hasItems("1", "2", "3"))
			.body("find{it.age == 25}.name", is("Maria Joaquina"))
			.body("findAll{it.name.toString().contains('n')}.name", hasItems("Ana Julia", "Maria Joaquina"))
			.body("salary.find{it != null}", is("1234.5678"))
			.body("salary.find{it != null}.toDouble()", is(1234.5678d))//Para tratar o valor como número, deve-se converter para double e informar no parametro
			.body("age.collect{it.toInteger() * 2}", hasItems(40, 50, 60))
			;
	}
	
	@Test
	public void devoFazerPesquisarAvancadasComXmlJava() {
		String nome = given()
				.log().all()
		.when()
			.get("/usersXML")
		.then()
			.statusCode(200)
			.extract().path("users.user.name.findAll{it.toString().startsWith('Maria')}")
			;
//		System.out.println(nome.toString());
		Assert.assertEquals("Maria Joaquina".toUpperCase(), nome.toUpperCase());
	}

}
