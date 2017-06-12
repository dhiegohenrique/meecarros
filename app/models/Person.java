package models;

import io.swagger.annotations.ApiModelProperty;

public class Person {

	@ApiModelProperty(example = "123")
	private Long personId;
	
	@ApiModelProperty(example = "Maria")
	private String name;

	public Long getPersonId() {
		return this.personId;
	}

	public void setPersonId(Long id) {
		this.personId = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
}