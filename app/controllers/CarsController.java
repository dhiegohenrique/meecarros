package controllers;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;

import authenticator.SecuredAuthenticator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import models.Car;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import services.CarService;

@Api(value = "Carros")
public class CarsController extends Controller {

    private HttpExecutionContext executionContext;
    
    @Inject
    private CarService carService;
    
    @Inject
	private FormFactory formFactory;
    
    private final String msgCarroNaoEncontrado = "Carro não encontrado.";

    @Inject
    public CarsController(HttpExecutionContext executionContext) {
        this.executionContext = executionContext;
    }

    @ApiOperation(
			value = "Retorna os carros cadastrados", consumes = "application/json", produces = "application/json",
			response = Car.class
    		)
	@ApiResponses(value = { 
			@ApiResponse(code = HttpServletResponse.SC_OK, message = "Carros retornados com sucesso.", response = Car.class, responseContainer = "List"),
			@ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Acesso não autorizado. Informe um token válido.")
	})
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "token", value = "Token do Pipedrive", required = true, paramType = "header", dataType = "string")
	  })
    @Security.Authenticated(SecuredAuthenticator.class)
    public CompletionStage<Result> cars() {
        return this.carService.getCars().thenApplyAsync(cars -> {
            List<Car> listCars = cars.collect(Collectors.toList());
            return ok(this.toJSON(listCars));
        }, this.executionContext.current());
    }
    
    @ApiOperation(
			value = "Retorna os modelos dos carros cadastrados", consumes = "application/json", produces = "application/json",
			response = Car.class
    		)
	@ApiResponses(value = { 
			@ApiResponse(code = HttpServletResponse.SC_OK, message = "Modelos retornados com sucesso.", response = Car.class, responseContainer = "List"),
			@ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Acesso não autorizado. Informe um token válido.")
	})
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "token", value = "Token do Pipedrive", required = true, paramType = "header", dataType = "string")
	  })
    @Security.Authenticated(SecuredAuthenticator.class)
    public CompletionStage<Result> carsModels() {
        return this.carService.getCarsModels().thenApplyAsync(cars -> {
            List<Car> listCars = cars.collect(Collectors.toList());
            return ok(Json.toJson(listCars));
        }, this.executionContext.current());
    }

    @ApiOperation(
			value = "Retorna um carro pelo ID", consumes = "application/json", produces = "application/json",
			response = Car.class
			)
	@ApiResponses(value = { 
			@ApiResponse(code = HttpServletResponse.SC_OK, message = "Carro retornado com sucesso.", response = Car.class),
			@ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Acesso não autorizado. Informe um token válido."),
			@ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Carro não encontrado.")
	})
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "token", value = "Token do Pipedrive", required = true, paramType = "header", dataType = "string")
	  })
    @Security.Authenticated(SecuredAuthenticator.class)
    public CompletionStage<Result> carById(@ApiParam(name = "id", value = "ID do carro", required = true) Long id) {
    	 return this.carService.getCarById(id).thenApplyAsync(cars -> {
    		 Optional<Car> findFirst = cars.findFirst();
    		 if (!findFirst.isPresent()) {
    			 return notFound(this.msgCarroNaoEncontrado);
    		 }
    		 
    		 Car carResource = findFirst.get();
    		 return ok(this.toJSON(carResource));
         }, this.executionContext.current());
    }

    @ApiOperation(
			value = "Atualiza um carro", consumes = "application/json", produces = "application/json",
			code = HttpServletResponse.SC_NO_CONTENT,
			response = Car.class
			)
	@ApiResponses(value = { 
			@ApiResponse(code = HttpServletResponse.SC_NO_CONTENT, message = "Carro atualizado com sucesso."),
			@ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Acesso não autorizado. Informe um token válido."),
			@ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Carro não encontrado."),
			@ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Campos obrigatórios não preenchidos ou idade maior ou igual a 30 anos.")
	})
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "token", value = "Token do Pipedrive", required = true, paramType = "header", dataType = "string"),
	    @ApiImplicitParam(name = "car", value = "Dados do carro", required = true, paramType = "body", dataType = "models.Car")
	  })
    @Security.Authenticated(SecuredAuthenticator.class)
    public CompletionStage<Result> update(@ApiParam(name = "id", value = "ID do carro", required = true) Long id) {
    	Form<Car> form = this.formFactory.form(Car.class).bindFromRequest();
    	if (form.hasErrors()) {
    		return CompletableFuture.supplyAsync(() -> "")
    				.thenApplyAsync(result -> internalServerError(form.errorsAsJson()), this.executionContext.current());
    	}
    	
        JsonNode json = request().body().asJson();
        Car resource = Json.fromJson(json, Car.class);
        return this.carService.update(id, resource).thenApplyAsync(optionalResource -> {
        	if (!optionalResource.isPresent()) {
        		return notFound(this.msgCarroNaoEncontrado);
        	}
        	
        	return noContent();
        }, this.executionContext.current());
    }
    
    @ApiOperation(
			value = "Deleta um carro", consumes = "application/json", produces = "application/json",
			code = HttpServletResponse.SC_NO_CONTENT,
			response = Car.class
			)
	@ApiResponses(value = { 
			@ApiResponse(code = HttpServletResponse.SC_NO_CONTENT, message = "Carro deletado com sucesso."),
			@ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Acesso não autorizado. Informe um token válido."),
			@ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Carro não encontrado.")
	})
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "token", value = "Token do Pipedrive", required = true, paramType = "header", dataType = "string")
	  })
    @Security.Authenticated(SecuredAuthenticator.class)
    public CompletionStage<Result> delete(@ApiParam(name = "id", value = "ID do carro", required = true) Long id) {
    	return this.carService.delete(id).thenApplyAsync(optionalResource -> {
    		if (!optionalResource.isPresent() || optionalResource.get() == 0) {
    			return notFound(this.msgCarroNaoEncontrado);
    		}
    		
    		return noContent();
    	}, this.executionContext.current());
    }

    @ApiOperation(
			value = "Cadastra um carro", consumes = "application/json", produces = "application/json",
			code = HttpServletResponse.SC_CREATED,
			response = Car.class
			)
	@ApiResponses(value = { 
			@ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Acesso não autorizado. Informe um token válido."),
			@ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Campos obrigatórios não preenchidos ou idade maior ou igual a 30 anos."),
			@ApiResponse(code = HttpServletResponse.SC_CREATED, message = "Carro cadastrado com sucesso."),
	})
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "token", value = "Token do Pipedrive", required = true, paramType = "header", dataType = "string"),
	    @ApiImplicitParam(name = "car", value = "Dados do carro", required = true, paramType = "body", dataType = "models.Car")
	  })
    @Security.Authenticated(SecuredAuthenticator.class)
    public CompletionStage<Result> insert() {
    	Form<Car> form = this.formFactory.form(Car.class).bindFromRequest();
    	if (form.hasErrors()) {
    		return CompletableFuture.supplyAsync(() -> "")
    				.thenApplyAsync(result -> internalServerError(form.errorsAsJson()), this.executionContext.current());
    	}
    	
    	JsonNode jsonNode = request().body().asJson();
    	final Car car = Json.fromJson(jsonNode, Car.class);
        return this.carService.insert(car).thenApplyAsync(response -> {
            return created(Json.toJson(response));
        }, this.executionContext.current());
    }
    
    private String toJSON(Object object) {
    	GsonBuilder gsonBuilder = new GsonBuilder();
    	gsonBuilder.setLongSerializationPolicy( LongSerializationPolicy.STRING );
		Gson gson = gsonBuilder.create();
		return gson.toJson(object); 
    }
}