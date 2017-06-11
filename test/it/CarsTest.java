package it;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static play.test.Helpers.DELETE;
import static play.test.Helpers.GET;
import static play.test.Helpers.POST;
import static play.test.Helpers.PUT;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.route;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;

import models.Car;
import play.Application;
import play.api.Play;
import play.db.jpa.JPAApi;
import play.inject.guice.GuiceApplicationBuilder;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;

public class CarsTest extends WithApplication {
	
	private final String token = "32fffa616b7fb3d2940e99fd06423e04db4591cb";
	
	private Http.RequestBuilder request;
	
	private List<Car> listCars;

	private Gson gson;

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder()
        		.build();
    }
    
    @Before
    public void setUp() {
    	this.request = new Http.RequestBuilder();
    	this.request.uri("/cars");
    	
    	this.listCars = new ArrayList<>();
    	this.gson = new Gson();
    }
    
    @Test
    public void shouldBeReturnStatus401WhenRecoveringCarsAndTokenIsEmpty() {
    	this.request.method(GET);
    	
    	Result result = route(this.app, this.request);
    	assertEquals(HttpServletResponse.SC_UNAUTHORIZED, result.status());
    }
    
    @Test
    public void shouldBeReturnStatus401WhenRecoveringCarByIdAndTokenIsEmpty() {
    	this.request.uri(this.request.uri() + "/1");
    	this.request.method(GET);
    	
    	Result result = route(this.app, this.request);
    	assertEquals(HttpServletResponse.SC_UNAUTHORIZED, result.status());
    }
    
    @Test
    public void shouldBeReturnStatus401WhenSaveNewCarAndTokenIsEmpty() {
    	this.request.method(POST);
    	
    	Result result = route(this.app, this.request);
    	assertEquals(HttpServletResponse.SC_UNAUTHORIZED, result.status());
    }
    
    @Test
    public void shouldBeReturnStatus401WhenEditingACarAndTokenIsEmpty() {
    	this.request.uri(this.request.uri() + "/1");
    	this.request.method(PUT);
    	
    	Result result = route(this.app, this.request);
    	assertEquals(HttpServletResponse.SC_UNAUTHORIZED, result.status());
    }
    
    @Test
    public void shouldBeReturnStatus401WhenDeletingACarAndTokenIsEmpty() {
    	this.request.uri(this.request.uri() + "/1");
    	this.request.method(DELETE);
    	
    	Result result = route(this.app, this.request);
    	assertEquals(HttpServletResponse.SC_UNAUTHORIZED, result.status());
    }
    
    @Test
    public void shouldBeReturnStatus500WhenSaveNewCarWithoutRequestBody() {
    	this.request.method(POST);
    	this.request.header("token", this.token);
    	
    	Result result = route(this.app, this.request);
    	assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, result.status());
    }
    
    @Test
    public void shouldBeSaveNewCar() {
    	Car car = new Car();
    	car.model = "modelo1";
    	car.year = 2000;
    	car.personId = 1L;
    	car.colorId = "#FFFFFF";
    	
    	this.request.method(POST);
    	this.request.header("token", this.token);
    	this.request.bodyJson(Json.toJson(car));
    	
    	Result result = route(this.app, this.request);
    	String body = contentAsString(result);
    	
    	assertEquals(HttpServletResponse.SC_CREATED, result.status());
    	assertTrue(body.contains("id"));
    }
    
    @Test
    public void shouldBeReturnStatus500WhenSaveNewCarAndRequiredFieldIsBlank() {
    	Car car = new Car();
    	car.model = "modelo1";
    	car.personId = 1L;
    	car.colorId = "#FFFFFF";
    	
    	this.request.method(POST);
    	this.request.header("token", this.token);
    	this.request.bodyJson(Json.toJson(car));
    	
    	Result result = route(this.app, this.request);
    	assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, result.status());
    }
    
    @Test
    public void shouldBeReturnStatus500WhenSaveNewCarOlderThan30Years() {
    	Car car = new Car();
    	car.model = "modelo1";
    	car.personId = 1L;
    	car.colorId = "#FFFFFF";

    	int currentYear = Calendar.getInstance().get(Calendar.YEAR);
    	car.year = (currentYear - 31);
    	
    	this.request.method(POST);
    	this.request.header("token", this.token);
    	this.request.bodyJson(Json.toJson(car));
    	
    	Result result = route(this.app, this.request);
    	String body = contentAsString(result);
    	
    	assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, result.status());
    	assertTrue(body.contains("year"));
    }
    
    @Test
    public void shouldBeReturnCarById() {
    	this.insertCars();
    	Car car = this.listCars.get(0);

    	this.request.method(GET);
    	this.request.header("token", this.token);
		this.request.uri(this.request.uri() + "/" + car.id);
		
		Result result = route(this.app, this.request);
		Car carResult = this.gson.fromJson(contentAsString(result), Car.class);
		
		this.assertCarEquals(car, carResult);
    }
    
    @Test
    public void shouldBeReturnAllCars() {
    	this.insertCars();
    	
    	this.request.method(GET);
    	this.request.header("token", this.token);
    	
    	Result result = route(this.app, this.request);
    	Car[] cars = this.gson.fromJson(contentAsString(result), Car[].class);
    	List<Car> listCarsResult = Arrays.asList(cars);
    	
    	assertEquals(this.listCars.size(), listCarsResult.size());
    	
    	for (int index = 0; index < this.listCars.size(); index++) {
    		this.assertCarEquals(this.listCars.get(index), listCarsResult.get(index));
    	}
    }
    
    @Test
    public void shouldBeReturnCarModels() {
    	this.insertCars();
    	
    	this.request.method(GET);
    	this.request.header("token", this.token);
    	this.request.uri(this.request.uri() + "/models");
    	
    	Result result = route(this.app, this.request);
    	Car[] carsModels = this.gson.fromJson(contentAsString(result), Car[].class);
    	List<Car> listCarsModels = Arrays.asList(carsModels);
    	
    	assertEquals(this.listCars.size(), listCarsModels.size());
    	
    	listCarsModels.forEach((car) -> {
    		assertTrue((car.id != null && !car.id.equals(0L)));
    		assertTrue(!StringUtils.isBlank(car.model));
    	});
    }
    
    @Test
    public void shouldBeReturnStatus500WhenEditingACarWithoutRequestBody() throws IOException, ServletException {
    	this.insertCars();
    	Car car = this.listCars.get(0);
    	car.year = (car.year + 10);
    	car.colorId = "#FFFFFF";
    	car.personId = car.personId + 1L;
    	car.model = car.model + " - editado";
    	
    	this.request.method(PUT);
    	this.request.header("token", this.token);
    	this.request.uri(this.request.uri() + "/" + car.id);
    	
    	Result result = route(this.app, this.request);
    	assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, result.status());
	}
    
    @Test
    public void shouldBeReturnStatus404WhenEditingACarNotFound() throws IOException, ServletException {
    	this.insertCars();
    	Car car = this.listCars.get(0);
    	car.year = (car.year + 10);
    	car.colorId = "#FFFFFF";
    	car.personId = car.personId + 1L;
    	car.model = car.model + " - editado";
    	
    	this.request.method(PUT);
    	this.request.header("token", this.token);
    	this.request.bodyJson(Json.toJson(car));
    	this.request.uri(this.request.uri() + "/123454");
    	
    	Result result = route(this.app, this.request);
    	assertEquals(HttpServletResponse.SC_NOT_FOUND, result.status());
	}
    
    @Test
    public void shouldBeReturnStatus204WhenEditingACar() {
    	this.insertCars();
    	Car car = this.listCars.get(0);
    	car.year = (car.year + 10);
    	car.colorId = "#FFFFFF";
    	car.personId = car.personId + 1L;
    	car.model = car.model + " - editado";
    	
    	this.request.method(PUT);
    	this.request.header("token", this.token);
    	this.request.bodyJson(Json.toJson(car));
    	this.request.uri(this.request.uri() + "/" + car.id);
    	
    	Result result = route(this.app, this.request);
    	assertEquals(HttpServletResponse.SC_NO_CONTENT, result.status());
    }
    
    @Test
    public void shouldBeReturnStatus404WhenDeletingACarNotFound() {
    	this.insertCars();
    	
    	this.request.method(DELETE);
    	this.request.header("token", this.token);
    	this.request.uri(this.request.uri() + "/123456");
    	
    	Result result = route(this.app, this.request);
    	assertEquals(HttpServletResponse.SC_NOT_FOUND, result.status());
    }
    
    @Test
    public void shouldBeReturnStatus204WhenDeletingACar() {
    	this.insertCars();
    	Car car = this.listCars.get(0);
    	
    	this.request.method(DELETE);
    	this.request.header("token", this.token);
    	this.request.uri(this.request.uri() + "/" + car.id);
    	
    	Result result = route(this.app, this.request);
    	assertEquals(HttpServletResponse.SC_NO_CONTENT, result.status());
    }
    
    private void insertCars() {
    	JPAApi api = Play.current().injector().instanceOf(JPAApi.class);
    	
    	api.withTransaction(() -> {
    		EntityManager entityManager = api.em();
    		for (int index = 0; index < 3; index++) {
    			Car car = this.getCar(index);
    			this.listCars.add(index, entityManager.merge(car));
    		}
    	});
    }
    
    private void assertCarEquals(Car car0, Car car1) {
    	assertEquals(car0.id, car1.id);
		assertEquals(car0.year, car1.year);
		assertEquals(car0.colorId, car1.colorId);
		assertEquals(car0.personId, car1.personId);
    }
    
    private Car getCar(int index) {
    	Car car = new Car();
    	car.model = "modelo" + index;
    	car.colorId = "#FFFFFF";
    	car.personId = Long.valueOf(index);
    	car.year = (2000 + index);
    	return car;
    }
}