package it;

import static org.junit.Assert.assertEquals;
import static play.test.Helpers.GET;
import static play.test.Helpers.route;

import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;

import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;

public class PersonsTest extends WithApplication {
	
	private final String token = "32fffa616b7fb3d2940e99fd06423e04db4591cb";
	
	private Http.RequestBuilder request;

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder().build();
    }
    
    @Before
    public void setUp() {
    	this.request = new Http.RequestBuilder();
    	this.request.method(GET);
    	this.request.uri("/persons");
    }
    
    @Test
    public void shouldBeReturnStatus401WhenRecoveringPersonsAndTokenIsEmpty() {
    	Result result = route(this.app, this.request);
    	assertEquals(HttpServletResponse.SC_UNAUTHORIZED, result.status());
    }
    
    @Test
    public void shouldBeReturnPersons() {
    	this.request.header("token", this.token);
    	
    	Result result = route(this.app, this.request);
    	assertEquals(HttpServletResponse.SC_OK, result.status());
    }
}