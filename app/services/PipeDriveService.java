package services;

import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

import com.google.common.net.MediaType;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import play.libs.ws.WSClient;
import play.libs.ws.WSRequest;
import play.libs.ws.WSResponse;

public class PipeDriveService {

	private Config config = ConfigFactory.load();
	
	private final String baseUrl = this.config.getString("pipedrive.baseUrl");
	private final String apiToken = this.config.getString("pipedrive.apiToken");
	
	@Inject
	private WSClient wsClient;
	
	private String getUrlDeal() {
		String deals = this.config.getString("pipedrive.deals");
		return this.baseUrl + deals + this.apiToken;
	}
	
	private String getPersonsUrl() {
		String persons = this.config.getString("pipedrive.persons");
		return this.baseUrl+ persons + this.apiToken;
	}
	
	public CompletionStage<WSResponse> validateToken(String token) {
		String url = this.getUrlDeal() + token;
		return this.getResponse(url);
	}
	
	public CompletionStage<WSResponse> getPersons(String token) {
		String url = this.getPersonsUrl() + token;
		return this.getResponse(url);
	}
	
	private CompletionStage<WSResponse> getResponse(String url) {
		WSRequest wsRequest = this.wsClient.url(url);
    	wsRequest.setContentType(MediaType.JSON_UTF_8.toString());
		
    	CompletionStage<WSResponse> completionStage = wsRequest.get();
    	return completionStage.thenApply((response) -> {
    		return response;
    	});
	}
}