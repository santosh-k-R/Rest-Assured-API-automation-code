package com.nisum.services.api_automation.utils;

import static io.restassured.RestAssured.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import javax.xml.ws.Response;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.specification.RequestSpecification;


public class RestUtils {
	//public static final String BASE_URI = System.getenv("baseURI");



	public static  String BASE_URI=null;

	public static Properties readPropertiesFile(String fileName) throws IOException {

		FileInputStream fis = null;

		Properties prop = null;

		try {

			fis = new FileInputStream(fileName);

			// create Properties class object

			prop = new Properties();

			// load properties file into it
			prop.load(fis);
		} catch (FileNotFoundException e) {

			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			fis.close();
		}
		return prop;
	}

	@Step("Get Request with the URL {0} and params {1}")
	public static io.restassured.response.Response get(String URL, String params) throws IOException {

		Properties prop = readPropertiesFile("D:\\APIautomation\\src\\test\\resources\\APIProperties.properties");
		BASE_URI=prop.getProperty("BaseUri");
		System.out.println("BASE_URI"+BASE_URI);
		System.out.println("Running Get Request on the End Point " + URL);
		return
				given()
				.spec(getSpec(BASE_URI))
				.when()
				.get(URL + "?" + params)
				.then()
				.extract().response();

	}
	@Step("Get Request Specification")
	public static RequestSpecification getSpec(String url) {
		return given().baseUri(url).filter(new AllureRestAssured()).header("Content-Type", "application/json")
				.config(RestAssured.config().objectMapperConfig(new ObjectMapperConfig(ObjectMapperType.GSON)));

	}

	@Step("Post Request with the URL {0} , body {1} ")
	public static io.restassured.response.Response post(String URL, FileInputStream body) throws IOException {
		Properties prop = readPropertiesFile("D:\\APIautomation\\src\\test\\resources\\APIProperties.properties");
		String POst_URL=prop.getProperty("URL");
		
		System.out.println("Running Post Request on the End Point " + given().spec(getSpec(POst_URL))
				.body(body).when().post(URL).asString());
		return
				given().spec(getSpec(POst_URL))
				.body(body).when().post(URL).then().extract().response();
	}
	
	
	@Step("Put Request with the URL {0} , body {1}")
    public static io.restassured.response.Response put(String URL, FileInputStream body,String params) throws IOException {
        System.out.println("Running Put Request on the End Point " + URL);
        Properties prop = readPropertiesFile("D:\\APIautomation\\src\\test\\resources\\APIProperties.properties");
		String Put_URL=prop.getProperty("put_url");
		
        return
                given()
                        .spec(getSpec(Put_URL))
                        .body(body)
                        .when()
                        .put(URL +"?" + params).
                        then()
                        .extract().response();
    }
    @Step("Delete Request with the URL {0}")
    public static io.restassured.response.Response delete(String URL) throws IOException {
        System.out.println("Running Delete Request on the End Point " + URL);
        System.out.println("Running Put Request on the End Point " + URL);
        Properties prop = readPropertiesFile("D:\\APIautomation\\src\\test\\resources\\APIProperties.properties");
		String delete_URL=prop.getProperty("put_url");
        return
                given()
                        .spec(getSpec(delete_URL))
                        .when()
                        .delete(URL)
                        .then()
                        .extract().response();
    }
}
