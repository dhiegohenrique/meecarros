package controllers;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import services.PipeDriveService;

@Api(value = "Login")
public class LoginController extends Controller {
	
	private HttpExecutionContext executionContext;
	
	@Inject
	private PipeDriveService pipeDriveService;
	
	@Inject
    public LoginController(HttpExecutionContext executionContext) {
        this.executionContext = executionContext;
    }

	@ApiOperation(
			value = "Realiza o login", consumes = "application/json", produces = "application/json",
			response = String.class
			)
	@ApiResponses(value = { 
			@ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "O token informado é inválido."),
			@ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Informe o token."),
			@ApiResponse(code = HttpServletResponse.SC_OK, message = "Login efetuado com sucesso.")
	})
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "token", value = "Token do Pipedrive", required = true, paramType = "header", dataType = "string")
	  })
	public CompletionStage<Result> login() {
		JsonNode jsonNode = request().body().asJson();
    	if (jsonNode == null || !jsonNode.has("token")) {
    		return CompletableFuture.supplyAsync(() -> "")
    	            .thenApply(result -> unauthorized("Informe o token."));
    	}
		
    	JsonNode jsonNodeToken = jsonNode.get("token");
    	String token = jsonNodeToken.asText();
    	
    	return this.pipeDriveService.validateToken(token).thenApplyAsync(response -> {
    		JsonNode jsonResponse = response.asJson();
			boolean success = jsonResponse.get("success").asBoolean();
			if (!success) {
				return unauthorized("O token informado é inválido.");
			}
			
			ObjectNode objectNode = Json.newObject();
			objectNode.put("token", token);
			
//			session().clear();
//			session("token", token);
//			return ok();
			return ok(objectNode);
        }, this.executionContext.current());
	}
}