package com.nisum.services.api_automation.utils;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


public class RestUtils {
	
	@Test(description="Validation of GET API Request",priority=1)
	public void get() {
		RestAssured.baseURI = "https://reqres.in/api/users";

		RequestSpecification httpRequest = RestAssured.given();

		Response response = httpRequest.request(Method.GET, "?page=2");
		System.out.println(response.asPrettyString());
		Assert.assertEquals(response.statusCode(), 200);
		String firstArray=response.jsonPath().get("data[0].email");
		System.out.println("firstArray-------->"+firstArray);
	}

	
	  @Test(description="Validation of POST API Request",priority=2) public void
	  post() {
	  
	  RestAssured.baseURI = "https://reqres.in/api/"; RequestSpecification
	  httpRequest = RestAssured.given(); JSONObject requestParam = new
	  JSONObject(); requestParam.put("name", "Ramesh"); requestParam.put("job",
	  "Tech Lead"); httpRequest.header("Content-Type", "application/json");
	  httpRequest.body(requestParam.toJSONString()); Response response =
	  httpRequest.request(Method.POST, "users");
	  System.out.println(response.asPrettyString());
	  Assert.assertEquals(response.statusCode(), 201); String
	  name=response.jsonPath().get("name"); Assert.assertEquals(name, "Ramesh"); }
	 
	
}
