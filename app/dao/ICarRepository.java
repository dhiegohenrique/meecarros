package dao;

import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

import com.fasterxml.jackson.databind.node.ObjectNode;

import models.Car;

public interface ICarRepository {

    CompletionStage<Stream<Car>> getCars();
    
    CompletionStage<Stream<Car>> getCarById(Long id);

    CompletionStage<ObjectNode> insert(Car car);

    CompletionStage<Optional<Car>> update(Long id, Car car);
    
    CompletionStage<Optional<Integer>> delete(Long id);

	CompletionStage<Stream<Car>> getCarsModels();
}