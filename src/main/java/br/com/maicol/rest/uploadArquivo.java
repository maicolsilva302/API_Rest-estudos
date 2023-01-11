package br.com.maicol.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.Test;

public class uploadArquivo {
	
	@Test
	public void deveObrigarEnvioDeArquivo() {
		given()
			.log().all()
		.when()
			.post("http://restapi.wcaquino.me/upload")
		.then()
			.log().all()
			.statusCode(404) // deveria retornar o erro 400
			.body("error", is("Arquivo não enviado"))
		;
	}
	
	@Test
	public void deveFazerUploadDeArquivo() {
		given()
			.log().all()
			.multiPart("arquivo", new File("src/main/resources/variaveis.html"))
		.when()
			.post("http://restapi.wcaquino.me/upload")
		.then()
			.log().all()
			.statusCode(200) 
			.body("name", is("variaveis.html"))
			.body("size", is(240))
		;
	}
	
	@Test
	public void naoDeveFazerUploadDeArquivoGrande() {
		given()
			.log().all()
			.multiPart("arquivo", new File("src/main/resources/chromedriver_win32.zip"))
		.when()
			.post("http://restapi.wcaquino.me/upload")
		.then()
			.log().all()
			.time(lessThan(4000L))
			.statusCode(413) 
		;
	}
	
	@Test
	public void deveBaixarArquivo() throws IOException {
		byte[] imagem = given()
			.log().all()
		.when()
			.get("http://restapi.wcaquino.me/download")
		.then()
//			.log().all()
			.statusCode(200) 
			.extract().asByteArray()
		;
		
		File image = new File("src/main/resources/File.jpg");
		OutputStream out = new FileOutputStream(image);
		out.write(imagem);
		out.close();
		
	}

}
