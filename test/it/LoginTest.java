package it;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static play.test.Helpers.POST;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.route;

import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.node.ObjectNode;

import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;

public class LoginTest extends WithApplication {
	
	private final String token = "32fffa616b7fb3d2940e99fd06423e04db4591cb";
	
	private Http.RequestBuilder request;

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder().build();
    }
    
    @Before
    public void setUp() {
    	this.request = new Http.RequestBuilder();
    	this.request.method(POST);
    	this.request.uri("/login");
    }
    
    @Test
    public void shouldBeReturnStatus401WhenTokenIsEmpty() {
    	Result result = route(this.app, this.request);
    	assertEquals(HttpServletResponse.SC_UNAUTHORIZED, result.status());
    }
    
    @Test
    public void shouldBeReturnStatus401WhenTokenIsInvalid() {
    	this.request.bodyJson(this.getRequestBody("asdfasdfasdf"));
    	
    	Result result = route(this.app, this.request);
    	assertEquals(HttpServletResponse.SC_UNAUTHORIZED, result.status());
    }
    
    @Test
    public void shouldBeReturnToken() {
    	this.request.bodyJson(this.getRequestBody(this.token));
    	Result result = route(this.app, this.request);
    	
    	String body = contentAsString(result);
    	assertTrue(body.contains("token"));
    }
    
    private ObjectNode getRequestBody(String token) {
    	ObjectNode requestBody = Json.newObject();
    	requestBody.put("token", token);
    	return requestBody;
    }
}