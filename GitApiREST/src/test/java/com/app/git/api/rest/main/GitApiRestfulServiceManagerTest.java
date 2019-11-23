package com.app.git.api.rest.main;

import org.junit.Test;

import io.restassured.http.ContentType;
import junit.framework.TestCase;
import static io.restassured.RestAssured.*;

public class GitApiRestfulServiceManagerTest extends TestCase {
	 	
	@Test
	public void test() {
		given().
	    when().
	        get("http://localhost:8082/GitApiREST/git/smitas019/get").
	    then().
	        assertThat().
	        statusCode(200).
	    and().
	        contentType(ContentType.XML);
	}

}
