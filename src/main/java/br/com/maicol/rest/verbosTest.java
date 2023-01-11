package br.com.maicol.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import java.util.HashMap;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import io.restassured.http.ContentType;

public class verbosTest {
	
	@Test
	public void deveSalvarUsuario() {
		given()
			.log().all()
			.contentType("application/json")
			.body("{\"name\": \"Julios\", \"age\": 25}")
		.when()
			.post("http://restapi.wcaquino.me/users")
		.then()
			.log().all()
			.statusCode(201)
			.body("id", is(notNullValue()))
			.body("name", is("Julios"))
			.body("age", is(25))
		;
	}
	@Test
	public void deveSalvarUsuarioUsandoMap() {
		
		Map<String , Object> parametros = new HashMap<String, Object>();
		parametros.put("name", "Julios via map");
		parametros.put("age", 25);
		
		given()
			.log().all()
			.contentType(ContentType.JSON)
			.body(parametros)
		.when()
			.post("http://restapi.wcaquino.me/users")
		.then()
			.log().all()
			.statusCode(201)
			.body("id", is(notNullValue()))
			.body("name", is("Julios via map"))
			.body("age", is(25))
		;
	}
	@Test
	public void deveSalvarUsuarioUsandoObjeto() {
	
	User user = new User("Cirilo", 13);
			given()
				.log().all()
				.contentType("application/json")
				.body(user)
			.when()
				.post("http://restapi.wcaquino.me/users")
			.then()
				.log().all()
				.statusCode(201)
				.body("id", is(notNullValue()))
				.body("name", is("Cirilo via objeto"))
				.body("age", is(13))
			;
		
	}
	
	@Test
	public void deveDesecerializarUsuarios() {
	
	User user = new User("Cirilo", 13);
			User usuarioInserido = given()
				.log().all()
				.contentType("application/json")
				.body(user)
			.when()
				.post("http://restapi.wcaquino.me/users")
			.then()
				.log().all()
				.statusCode(201)
				.extract().body().as(User.class)
			;
		System.out.println(usuarioInserido);
		Assert.assertThat(usuarioInserido.getId(), notNullValue());
		Assert.assertEquals("Cirilo", usuarioInserido.getNome());
		Assert.assertThat(usuarioInserido.getIdade(), Matchers.is(13));
	}
	
	@Test
	public void naoDeveSalvarUsuarioSemNome() {
		given()
			.log().all()
			.contentType(ContentType.JSON)
			.body("{\"age\": 25}")
		.when()
			.post("http://restapi.wcaquino.me/users")
		.then()
			.log().all()
			.statusCode(400)
			.body("id", is(nullValue()))
			.body("error", is("Name é um atributo obrigatório"))
			.body("error", containsString("obrigatório"))
		;
	}
	
	@Test
	public void deveSalvarUsuarioViaXML() {
		/**
		 * SEMPRE QUE FOR USAR O PUT COM XML, DEVE-SE COLOCAR A ESTRUTURA DO BODY
		 * NO CASO O 'USER'
		 */
		given()
			.log().all()
			.contentType(ContentType.XML)
			.body("<user><name>Suco de fruta</name><age>29</age></user>")
		.when()
			.post("http://restapi.wcaquino.me/usersXML")
		.then()
			.log().all()
			.statusCode(201)
			.rootPath("user")
			.body("id", is(notNullValue()))
			.body("name", is("Suco de fruta"))
			.body("age", is("29"))
		;
	}
	@Test
	public void deveSerealizarUsuarioViaXML() {
		
		User user = new User("Suco de fruta via XML", 29);
		
		User usuarioInserido = given()
			.log().all()
			.contentType(ContentType.XML)
			.body(user)
		.when()
			.post("http://restapi.wcaquino.me/usersXML")
		.then()
			.log().all()
			.statusCode(201)
			.extract().body().as(User.class)
		;
		System.out.println(usuarioInserido);
		Assert.assertThat(usuarioInserido.getId(), notNullValue());
		Assert.assertEquals("Cirilo", usuarioInserido.getNome());
		Assert.assertThat(usuarioInserido.getIdade(), is(13));
	}
	
	@Test
	public void deveAltearUsuario() {
		given()
			.log().all()
			.contentType(ContentType.JSON)
			.body("{\"name\": \"Usuário Maikao\", \"age\": 99}")
		.when()
			.put("http://restapi.wcaquino.me/users/1")
		.then()
			.log().all()
			.statusCode(200)
			.body("id", is(1))
			.body("name", is("Usuário Maikao"))
			.body("name", containsString("Maikao"))
			.body("age", is(99))
			.body("age", greaterThan(90))
			.body("age", lessThan(100))
			.body("salary", is(1234.5678f))
			;
	}
	
	@Test
	public void devoCustomizarUrlParte1() {
		given()
			.log().all()
			.contentType(ContentType.JSON)
			.body("{\"name\": \"Usuário Maikao\", \"age\": 99}")
		.when()
			.put("http://restapi.wcaquino.me/{entidade}/{userId}", "users", "1")
		.then()
			.log().all()
			.statusCode(200)
			.body("id", is(1))
			.body("name", is("Usuário Maikao"))
			.body("name", containsString("Maikao"))
			.body("age", is(99))
			.body("age", greaterThan(90))
			.body("age", lessThan(100))
			.body("salary", is(1234.5678f))
			;
	}
	
	@Test
	public void devoCustomizarUrlParte2() {
		given()
			.log().all()
			.contentType(ContentType.JSON)
			.body("{\"name\": \"Usuário Maikao\", \"age\": 99}")
			.pathParam("entidade","users")
			.pathParam("userId","1")
		.when()
			.put("http://restapi.wcaquino.me/{entidade}/{userId}")
		.then()
			.log().all()
			.statusCode(200)
			.body("id", is(1))
			.body("name", is("Usuário Maikao"))
			.body("name", containsString("Maikao"))
			.body("age", is(99))
			.body("age", greaterThan(90))
			.body("age", lessThan(100))
			.body("salary", is(1234.5678f))
			;
	}
	
	@Test
	public void deveRemoverUsuario() {
		
		given()
			.log().all()
		.when()
			.delete("http://restapi.wcaquino.me/users/1")
		.then()
			.log().all()
			.statusCode(204)
		;
	}
	
	@Test
	public void naoDeveRemoverUsuarioInexistente() {
		
		given()
			.log().all()
		.when()
			.delete("http://restapi.wcaquino.me/users/10")
		.then()
			.log().all()
			.statusCode(400)
			.body("error", is("Registro inexistente"))
			.body("error", containsString("inexistente"))
		;
	}
}




