package services;

import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.node.ObjectNode;

import dao.ICarRepository;
import models.Car;
import play.libs.concurrent.HttpExecutionContext;

public class CarService {

    private final ICarRepository repository;
    
    private final HttpExecutionContext executionContext;

    @Inject
    public CarService(ICarRepository repository, HttpExecutionContext executionContext) {
        this.repository = repository;
        this.executionContext = executionContext;
    }

    public CompletionStage<Stream<Car>> getCars() {
    	return this.repository.getCars().thenApplyAsync(carStream -> {
    		return carStream;
    	}, this.executionContext.current());
    }
    
    public CompletionStage<Stream<Car>> getCarsModels() {
    	return this.repository.getCarsModels().thenApplyAsync(carStream -> {
    		return carStream;
    	}, this.executionContext.current());
    }
    
    public CompletionStage<Stream<Car>> getCarById(Long id) {
        return this.repository.getCarById(id).thenApplyAsync(carStream -> {
        	return carStream;
        }, this.executionContext.current());
    }
    
    public CompletionStage<ObjectNode> insert(Car car) {
        return this.repository.insert(car).thenApplyAsync(savedData -> {
            return savedData;
        }, this.executionContext.current());
    }

    public CompletionStage<Optional<Car>> update(Long id, Car carData) {
        return this.repository.update(id, carData).thenApplyAsync(optionalData -> {
        	return optionalData;
        }, this.executionContext.current());
    }
    
    public CompletionStage<Optional<Integer>> delete(Long id) {
    	return this.repository.delete(id).thenApplyAsync(optionalData -> {
    		return optionalData;
    	}, this.executionContext.current());
    }
}