package controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.JsonNode;

import authenticator.SecuredAuthenticator;
import comparators.PersonsComparator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import models.Person;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import services.PipeDriveService;

@Api(value = "Pessoas")
public class PersonsController extends Controller {

	@Inject
	private PipeDriveService pipeDriveService;
	
	private HttpExecutionContext executionContext;

	private PersonsComparator personsComparator;
	
	@Inject
    public PersonsController(HttpExecutionContext executionContext) {
        this.executionContext = executionContext;
        this.personsComparator = new PersonsComparator();
    }
	
	@ApiOperation(
			value = "Retorna as pessoas do Pipedrive para o token informado", consumes = "application/json", produces = "application/json",
			response = Person.class
			)
	@ApiResponses(value = { 
			@ApiResponse(code = HttpServletResponse.SC_OK, message = "Pessoas retornadas com sucesso.", response = Person.class, responseContainer = "List"),
			@ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Acesso não autorizado. Informe um token válido.")
	})
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "token", value = "Token do Pipedrive", required = true, paramType = "header", dataType = "string")
	  })
	@Security.Authenticated(SecuredAuthenticator.class)
	public CompletionStage<Result> persons() {
		String token = request().getHeader("token");
    	
    	return this.pipeDriveService.getPersons(token).thenApplyAsync(response -> {
    		JsonNode jsonResponse = response.asJson();
			JsonNode jsonNode = jsonResponse.get("data");
			
			List<Person> listPersons = new ArrayList<>();
			Iterator<JsonNode> iterator = jsonNode.elements();

			while (iterator.hasNext()) {
				JsonNode next = iterator.next();
				Person person = Json.fromJson(next, Person.class);
				listPersons.add(person);
			}
			
			listPersons.sort(this.personsComparator);
			return ok(Json.toJson(listPersons));
        }, this.executionContext.current());
	}
}