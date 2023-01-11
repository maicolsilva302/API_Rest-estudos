package br.com.maicol.rest;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class OlaMundo {
	
	public static void main(String[] args) {
		
		RestAssured.baseURI = "http://restapi.wcaquino.me";
		RestAssured.port = 80;
//		RestAssured.basePath = "v2";
		
		Response response = RestAssured.request(Method.GET, "/ola");
		System.out.println(response.getBody().asString().equals("Ola Mundo!"));
		System.out.println(response.statusCode() == 200);
		
		ValidatableResponse validacao = response.then();
		validacao.statusCode(200);
		
		
	}

}
