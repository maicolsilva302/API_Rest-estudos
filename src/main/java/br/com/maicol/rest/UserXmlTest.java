package br.com.maicol.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;

import org.hamcrest.Matchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;

public class UserXmlTest {
	
	@BeforeClass
	public static void setup() {
		
		RestAssured.baseURI = "http://restapi.wcaquino.me";
		RestAssured.port = 80;
//		RestAssured.basePath = "v1"; //Vers�o da Aplica��o
		
	}
	
//	USANDO O XML, TUDO SE TORNA STRING, PORTANTO DEVE-SE COLOCAR AS ASPAS.
	
	@Test
	public void devoTrabalharComXml() {
		given()
			.log().all()
		.when()
			.get("/usersXML/3")
		.then()
			.statusCode(200)
			.body("user.name", is("Ana Julia"))
			.body("user.@id", is("3"))//REFERENCIAR DEVE COLOCAR O '@' SEM ISSO N�O FUNCIONA
			.body("user.filhos.name.size()", is(2))// EST� SEM AS ASPAS PORQUE '2' � O TAMANHO DA LISTA
			.body("user.filhos.name[0]", is("Zezinho"))
			.body("user.filhos.name[1]", is("Luizinho"))
			.body("user.filhos.name", hasItem("Luizinho"))
			.body("user.filhos.name", Matchers.hasItems("Zezinho","Luizinho"))
			;
	}
	
	@Test
	public void devoTrabalharComXmlAvancado() {
		given()
			.log().all()
		.when()
			.get("/usersXML/3")
		.then()
			.statusCode(200)
			.rootPath("user")//COLOCANDO O ROOTPATH,N�O PRECISA DECLARAR O N� DA RAIZ QUE EST� DECLARADA 
			.body("name", is("Ana Julia"))
			.body("@id", is("3"))//REFERENCIAR DEVE COLOCAR O '@' SEM ISSO N�O FUNCIONA
			.body("filhos.name.size()", is(2))// EST� SEM AS ASPAS PORQUE '2' � O TAMANHO DA LISTA
			.body("filhos.name[0]", is("Zezinho"))
			.body("filhos.name[1]", is("Luizinho"))
			.body("filhos.name", hasItem("Luizinho"))
			.body("filhos.name", Matchers.hasItems("Zezinho","Luizinho"))
			;
	}
	
	@Test
	public void devoTrabalharComXmlAvancadoFilhos() {
		given()
			.log().all()
		.when()
			.get("/usersXML/3")
		.then()
			.statusCode(200)
			.rootPath("user.filhos")//COLOCANDO O ROOTPATH,N�O PRECISA DECLARAR O N� DA RAIZ QUE EST� DECLARADA 
			.body("name.size()", is(2))// EST� SEM AS ASPAS PORQUE '2' � O TAMANHO DA LISTA
			.body("name[0]", is("Zezinho"))
			.body("name[1]", is("Luizinho"))
			.body("name", hasItem("Luizinho"))
			.body("name", Matchers.hasItems("Zezinho","Luizinho"))
			;
	}
	
	@Test
	public void devoTrabalharComXmlRemocao() {
		given()
			.log().all()
		.when()
			.get("/usersXML/3")
		.then()
			.statusCode(200)
			.rootPath("user")
			.body("name", is("Ana Julia"))
			.body("@id", is("3"))//REFERENCIAR DEVE COLOCAR O '@' SEM ISSO N�O FUNCIONA
			
			.rootPath("user.filhos")
			.body("name.size()", is(2))// EST� SEM AS ASPAS PORQUE '2' � O TAMANHO DA LISTA
			.body("name[0]", is("Zezinho"))
			.body("name[1]", is("Luizinho"))
			.body("name", hasItem("Luizinho"))
			.body("name", hasItems("Zezinho","Luizinho"))
			
			.detachRootPath("filhos")//RETIRA A OP��O DE N�O COLOCAR O FILTRO
			.body("filhos.name.size()", is(2))
			.body("filhos.name[0]", is("Zezinho"))
			.body("filhos.name[1]", is("Luizinho"))
			.body("filhos.name", hasItem("Luizinho"))
			.body("filhos.name", hasItems("Zezinho","Luizinho"))
			;
	}

}
