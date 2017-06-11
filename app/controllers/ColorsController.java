package controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import comparators.ColorsComparator;
import enuns.ColorEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import models.Color;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

@Api(value = "Cores")
public class ColorsController extends Controller {
	
	private ColorsComparator colorsComparator;

	public ColorsController() {
		this.colorsComparator = new ColorsComparator();
	}
	
	@ApiOperation(
			value = "Retorna as cores dos carros", consumes = "application/json", produces = "application/json",
			response = Color.class
			)
	@ApiResponses(value = { 
			@ApiResponse(code = HttpServletResponse.SC_OK, message = "Cores retornadas com sucesso.", response = Color.class, responseContainer = "List")
	})
    public Result colors() {
    	List<Color> listColors = new ArrayList<>();
    	for (ColorEnum colorEnum : ColorEnum.values()) {
    		Color color = new Color();
    		color.setId(colorEnum.getId());
    		color.setName(colorEnum.getColor());
    		
    		listColors.add(color);
    	}
    	
    	listColors.sort(this.colorsComparator);
    	return ok(Json.toJson(listColors));
    }
}