package br.com.maicol.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class UserJsonTest {
	
	public static RequestSpecification reqSpecification;
	public static ResponseSpecification resSpecification;
	
	@BeforeClass
	public static void setup() {
		
		RestAssured.baseURI = "http://restapi.wcaquino.me";
//		RestAssured.port = 80;
//		RestAssured.basePath = "v1"; //Versão da Aplicação
		
		RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
		reqBuilder.log(LogDetail.ALL);
		reqSpecification = reqBuilder.build();
		
		ResponseSpecBuilder resBuilder = new ResponseSpecBuilder();
		resBuilder.expectStatusCode(200);
		resSpecification = resBuilder.build();
		
		RestAssured.requestSpecification = reqSpecification;
		RestAssured.responseSpecification = resSpecification;
		
	}
	
	@Test
	public void JsonPrimeiroNivel() {
		
		given()
		.when()
			.get("/users/1")
		.then()
			.body("id", is(1))
			.body("name", containsString("Silva"))
			.body("age", greaterThan(18));
		
	}
	
	@Test
	public void devoVerificarPrimeiroNivelDeOutrasFormas() {
		Response response = RestAssured.request(Method.GET, "/users/1");
		
		response.path("id");
		response.path("age");
		response.path("name");
		response.path("salary");
		
		Assert.assertEquals("João da Silva", response.path("name"));
		Assert.assertEquals(new Integer(1), response.path("id"));
		Assert.assertEquals(new Integer(1), response.path("%s", "id"));
		Assert.assertEquals(30, response.path("age"));
		
//		PATH
		Assert.assertEquals(new Integer(1), response.path("id"));
		Assert.assertEquals(new Integer(1), response.path("%s", "id"));
		
//		JSON_PATH
		JsonPath jpath = new JsonPath(response.asString());
		Assert.assertEquals(1, jpath.getInt("id"));
		
//		FROM
		int id = JsonPath.from(response.asString()).getInt("id");
		Assert.assertEquals(1, id);
		
	}
	
	@Test
	public void deveVerificarSegundoNivel() {
		
		given()
		.when()
			.get("/users/2")
		.then()
			.body("id", is(2))
			.body("name", containsString("Maria"))
			.body("age", greaterThan(18))
			.body("age", is(25))
			.body("endereco.rua", is("Rua dos bobos"))
			.body("endereco.numero", is(0))
			;
		
	}
	
	@Test
	public void deveVerificarListas() {
		
		given()
		.when()
			.get("/users/3")
		.then()
			.body("id", is(3))
			.body("name", containsString("Ana"))
			.body("age", greaterThan(18))
			.body("age", lessThan(21))
			.body("age", is(20))
			.body("filhos", hasSize(2))
			.body("filhos.name", containsInAnyOrder("Zezinho", "Luizinho"))
			.body("filhos[0].name", is("Zezinho"))
			.body("filhos[1].name", containsString("Luiz"))
			;
		
	}
	
	@Test
	public void deveRetornarUsuarioInexistente() {
		
		given()
		.when()
			.get("/users/4")
		.then()
			.statusCode(404)
			.body("error", is("Usuário inexistente"))
			.body("error", containsString("inexistente"))
			;
	}
	
	@Test
	public void deveVerificarListaNaRaiz() {
		
		given()
		.when()
			.get("/users")
		.then()
			.body("$", hasSize(3))
			.body("name", hasItems("João da Silva","Maria Joaquina","Ana Júlia"))
			.body("age[1]", is(25))
			.body("filhos.name", hasItem(Arrays.asList("Zezinho","Luizinho")))
//			.body("salary", contains(1234.5678f, 2500, null))
			;
		
	}
	
	@Test
	public void devoFazerVrificacoesAvancadas() {
		
		given()
		.when()
			.get("/users")
		.then()
			.body("$", hasSize(3))
			.body("age.findAll{it <= 25}.size()", is(2))
			.body("age.findAll{it <= 25 && it > 20}.size()", is(1))
			.body("findAll{it.age <= 25}[0].name", is("Maria Joaquina"))
			.body("find{it.age<=25}.name", is("Maria Joaquina"))
			.body("findAll{it.name.contains('n')}.name", hasItems("Maria Joaquina","Ana Júlia"))
			.body("findAll{it.name.length() > 10}.name", hasItems("João da Silva","Maria Joaquina")) //Funciona Independente da ordem que os nomes estiverem
			.body("name.collect{it.toUpperCase()}", hasItem("MARIA JOAQUINA"))
			.body("name.collect{it.toUpperCase()}", hasItems("MARIA JOAQUINA", "JOÃO DA SILVA", "ANA JÚLIA"))
			
			;
		
	}

}
