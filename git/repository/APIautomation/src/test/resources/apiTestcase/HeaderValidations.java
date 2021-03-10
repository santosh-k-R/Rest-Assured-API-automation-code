package apiTestcase;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class HeaderValidations {

	
	@Test(description="Validate Response Header using Rest Assured",priority=1)
	public void headerResponseValidation() {
		
		RestAssured.baseURI="https://reqres.in/api/users";
		RequestSpecification request=RestAssured.given();
		Response response=request.get("?page=2");
		String contentType=response.header("Content-Type");
		System.out.println("Content Type is :"+contentType);
		String serverType=response.header("Server");
		System.out.println("Server Type is :"+serverType);
		
	}
	@Test(description="Printing All the Headers from Server",priority=2)
	public void printingAllHeaders()
	{
		RestAssured.baseURI = "https://reqres.in/api/users";
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.get("?page=2");
		Headers allHeaders = response.headers();
		for(Header header : allHeaders)
		{
			System.out.println("Key: " + header.getName() + " Value: " + header.getValue());
		}
	}
	
	@Test(description="Reading all the Nodes Data from Response",priority=3)
	public void get() {
		RestAssured.baseURI = "https://reqres.in/api/users";

		RequestSpecification httpRequest = RestAssured.given();

		Response response = httpRequest.request(Method.GET, "?page=2");
		Assert.assertEquals(response.statusCode(), 200);
		String totalValue=response.jsonPath().get("total").toString();
		System.out.println("total-------->"+Integer.parseInt(totalValue));
		String firstArray=response.jsonPath().get("data[0].email");
		System.out.println("firstArray-------->"+firstArray);
		  String secondArray=response.jsonPath().get("data[1].id").toString();
		  System.out.println("secondArray-------->"+Integer.parseInt( secondArray));
		 
	}
}

