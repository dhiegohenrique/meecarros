package models;

import io.swagger.annotations.ApiModelProperty;

public class Person {

	@ApiModelProperty(example = "123")
	private Long id;
	
	@ApiModelProperty(example = "Maria")
	private String name;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
}