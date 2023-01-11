package br.com.maicol.rest;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.request;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class OlaMundoTest {
	
	@BeforeClass
	public static void setup() {
		
		RestAssured.baseURI = "http://restapi.wcaquino.me";
		RestAssured.port = 80;
//		RestAssured.basePath = "v2";
		
	}
	
	@Test
	public void olaMundoTest() {
		
//		Response response = request(Method.GET, "http://restapi.wcaquino.me:80/ola");
		Response response = request(Method.GET, "/ola");
		Assert.assertTrue(response.getBody().asString().equals("Ola Mundo!"));
		Assert.assertTrue(response.getStatusCode() == 200);
		Assert.assertTrue("A resposta da requisição deveria ser 200", response.getStatusCode() == 200);
		Assert.assertEquals(response.getStatusCode(),200);
		Assert.assertEquals(200, response.getStatusCode());
		Assert.assertEquals("Ola Mundo!", response.getBody().asString());
		
		ValidatableResponse validacao = response.then();
		validacao.statusCode(200);
		
	}
	
	@Test
	public void devoConhecerOutrasFormasDeRestAssured() {
		
		Response response = request(Method.GET, "/ola");
		ValidatableResponse validacao = response.then();
		validacao.statusCode(200);
		
		get("/ola").then().statusCode(200);
		
		given() // Pré condições
			.log().all()
		.when() // Condição
			.get("/ola")
		.then() // Validações/Assertivas
//		.assertThat()
			.statusCode(200);
		
	}
	
	@Test
	public void devoConhecerMethersComHancrest() {
		
		assertThat("Maicol", is("Maicol"));
		assertThat(128, is(128));
		assertThat(128, isA(Integer.class));
		assertThat(128d, isA(Double.class));
		assertThat(128f, isA(Float.class));
		assertThat(128f, greaterThan(120f));
		assertThat(128f, lessThan(200f));
		
		List<Integer> impares = Arrays.asList(1,3,5,7,9);
		assertThat(impares, hasSize(5));
		assertThat(impares, contains(1,3,5,7,9));
		assertThat(impares, hasItem(3));
		assertThat(impares, hasItems(1,3));
		
		assertThat("Maicol", is(not("Maikao")));
		assertThat("Maicol", not("Maikao"));
		assertThat("Maicol", anyOf(is("Maikao"),is("Maicol")));
	}
	
	@Test
	public void devoValidarBody() {
		
		given() // Pré condições
			.log().all()
		.when() // Condição
			.get("/ola")
		.then() // Validações/Assertivas
//		.assertThat()
			.statusCode(200)
			.body(is("Ola Mundo!"))
			.body(containsString("!"));
		
	}

}
