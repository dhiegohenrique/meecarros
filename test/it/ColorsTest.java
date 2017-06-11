package it;

import static org.junit.Assert.assertTrue;
import static play.test.Helpers.GET;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.route;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;

import models.Color;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;

public class ColorsTest extends WithApplication {
	
	private Http.RequestBuilder request;

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder().build();
    }
    
    @Before
    public void setUp() {
    	this.request = new Http.RequestBuilder();
    	this.request.method(GET);
    	this.request.uri("/colors");
    }
    
    @Test
    public void shouldBeReturnColors() {
    	Result result = route(this.app, this.request);
    	String body = contentAsString(result);

    	Color[] colors = new Gson().fromJson(body, Color[].class);
    	assertTrue(colors.length > 0);
    }
}