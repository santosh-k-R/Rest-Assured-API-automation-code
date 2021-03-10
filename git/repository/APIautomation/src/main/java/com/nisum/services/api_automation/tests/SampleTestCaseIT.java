package com.nisum.services.api_automation.tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.nisum.services.api_automation.utils.RestUtils;
import com.nisum.services.api_automation.utils.XLUtils;

import io.qameta.allure.Feature;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

@Feature("Sample API Test")
public class SampleTestCaseIT {

	@Test(description = "Verifying Sample  API")
	public void SampleTestCase() throws IOException {
		
		/*
		 * FileInputStream fis = new FileInputStream(new
		 * File("D:\\APIautomation\\src\\test\\resources\\APIProperties.properties"));
		 * Properties ps=new Properties(); ps.load(fis);
		 */
		
		Response response = (Response) RestUtils.get("/Vnsane-to-done-I-set-I-praten-Gods-nor-thy-Mind-","d=www.safeway.com");
		System.out.println("API Response:-"+response.getBody().prettyPrint());
		Assert.assertEquals(response.statusCode(), 200, "Status Code ::");
	}
	
	
	@Test(description = "Verifying Sample POST API",dataProvider ="empdataprovider")
	void postNewEmployees(String ename,String eage,String esal) throws IOException
	{
		FileInputStream fis = new FileInputStream(new File("D:\\APIautomation\\src\\test\\resources\\APIProperties.properties"));
		Properties ps=new Properties();
		ps.load(fis);
		//System.getProperty(ps.getProperty("postURL"));
		RestAssured.baseURI=ps.getProperty("postURL");
		RequestSpecification httpRequest=RestAssured.given();
		JSONObject requestparams=new JSONObject();
		requestparams.put("name",ename);
		requestparams.put("salary",esal);
		requestparams.put("age",eage);
		httpRequest.header("content-type","application/json");
		httpRequest.body(requestparams.toJSONString());
		Response response=httpRequest.request(Method.POST,"/v1/create");
		String responseBody=response.getBody().asString();
		//System.out.println("responseBody is"+responseBody);
		int statusCode=response.getStatusCode();
		System.out.println("statusCode is"+statusCode);
		//Assert.assertEquals(statusCode, 200);
	}
	
	@Test(description="Verifying the Sample PUT API",priority=3)    public void samplePutApi() throws IOException {
        FileInputStream fis = new FileInputStream(new File("D:\\APIautomation\\src\\test\\resources\\ReqBody.json"));
        Response response=RestUtils.put("/api/users", fis,"page=2");
        System.out.println("API Response:-" + response.getBody().asString());
        Assert.assertEquals(response.statusCode(), 200, "Status Code ::");
       
    }
    @Test(description="Verifying the Sample DELETE API",priority=4)
    public void sampleDeletePost() throws IOException {
        Response response=RestUtils.delete("api/users");
        System.out.println("API Response:-" + response.getBody().asString());
        Assert.assertEquals(response.statusCode(), 204, "Status Code ::");
    }
	
	@DataProvider(name="empdataprovider")
	String[][] getEmpData() throws IOException
	{
		String path="D:\\APIautomation\\src\\test\\resources\\EMPDATA.xlsx";
		int rownum=XLUtils.getRowCount(path,"EMP");
		int colcount=XLUtils.getCellCount(path, "EMP", 1);

		String[][] empdata=new String[rownum][colcount];
        for(int i=1;i<=rownum;i++)
        {
        	for(int j=0;j<colcount;j++)
        	{
        		empdata[i-1][j]=XLUtils.getCellData(path, "EMP", i, j);
        	}
        }

		return empdata;
	}
	
	
	
}