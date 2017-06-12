package models;

import io.swagger.annotations.ApiModelProperty;

public class Color {

	@ApiModelProperty(example ="#FFFFFF")
	private String colorId;
	
	@ApiModelProperty(example = "Branco")
	private String name;

	public String getColorId() {
		return this.colorId;
	}

	public void setColorId(String colorId) {
		this.colorId = colorId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
}