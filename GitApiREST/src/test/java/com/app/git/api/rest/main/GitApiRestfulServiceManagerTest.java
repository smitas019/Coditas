package com.app.git.api.rest.main;

import org.junit.Test;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import junit.framework.TestCase;
import static io.restassured.RestAssured.*;

public class GitApiRestfulServiceManagerTest extends TestCase {
	 	
	@Test
	public void testForValidUser() {
		testGitUrl("http://localhost:8082/GitApiREST/git/smitas019/get",200).
	    and().
	        contentType(ContentType.JSON);
	}
	
	@Test
	public void testUserProjectNotPresent() {
		testGitUrl("http://localhost:8082/GitApiREST/git/shweta/get",202)
		.and().
        contentType(ContentType.TEXT);
	}
	
	@Test
	public void testUserNotPresent() {
		testGitUrl("http://localhost:8082/GitApiREST/git/suhasrg0/get",404)
		.and().
        contentType(ContentType.TEXT);
	}

	/**
	 * @return
	 */
	private ValidatableResponse testGitUrl(String url,Integer statusCode) {
		return given().
	    when().
	        get(url).
	    then().
	        assertThat().
	        statusCode(statusCode);
	}

}
