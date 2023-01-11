package br.com.maicol.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasXPath;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

import io.restassured.http.ContentType;

public class HTML {

	@Test
	public void devoFazerBuscasEmHTML() {

		given()
			.log().all()
		.when()
			.get("https://restapi.wcaquino.me/v2/users")
		.then()
			.log().all()
			.statusCode(200)
			.contentType(ContentType.HTML)
			.body("html.body.div.table.tbody.tr.size()", is(3))
			.body("html.body.div.table.tbody.tr[1].td[0]", is("2"))
			.body("html.body.div.table.tbody.tr[2].td[1]", containsString("Júlia"))
			.body("html.body.div.table.tbody.tr[2].td[2]", containsString("20"))
			.appendRootPath("html.body.div.table.tbody")
			.body("tr.find{it.toString().startsWith('2')}.td[1]", is("Maria Joaquina"))
		;
	}
	
	@Test
	public void devoFazerBuscasEmXpaths() {

		given()
			.log().all()
		.when()
			.get("https://restapi.wcaquino.me/v2/users?format=clean")
		.then()
			.log().all()
			.statusCode(200)
			.contentType(ContentType.HTML)
			.body(hasXPath("//td[text() = '1']/..//td[2]", is("João da Silva")))
			.body(hasXPath("//td[contains(text(),'Maria Joaquina')]", containsString("Joaquina")))
			
//			.body("html.body.div.table.tbody.tr[1].td[0]", is("2"))
//			.body("html.body.div.table.tbody.tr[2].td[1]", containsString("Júlia"))
//			.body("html.body.div.table.tbody.tr[2].td[2]", containsString("20"))
//			.appendRootPath("html.body.div.table.tbody")
//			.body("tr.find{it.toString().startsWith('2')}.td[1]", is("Maria Joaquina"))
		;
	}

}
