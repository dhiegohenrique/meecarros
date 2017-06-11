package models;

import io.swagger.annotations.ApiModelProperty;

public class Color {

	@ApiModelProperty(example ="#FFFFFF")
	private String id;
	
	@ApiModelProperty(example = "Branco")
	private String name;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
}