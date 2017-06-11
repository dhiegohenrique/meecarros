package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;
import play.data.validation.Constraints.Required;
import validators.IYearValidator;

@Entity
@Table(name = "cars")
public class Car {

	@ApiModelProperty(example = "22")
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public Long id;
    
	@ApiModelProperty(example = "2000")
    @IYearValidator
    public int year;
    
	@ApiModelProperty(example = "Palio")
    @Required(message = "Informe o modelo.")
    public String model;
    
	@ApiModelProperty(example = "10")
    @Required(message = "Informe a pessoa.")
    public Long personId;
    
	@ApiModelProperty(example = "#FFFFFF")
    @Required(message = "Informe a cor.")
    public String colorId;
}