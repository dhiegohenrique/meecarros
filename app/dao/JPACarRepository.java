package dao;

import static java.util.concurrent.CompletableFuture.supplyAsync;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;
import java.util.stream.Stream;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.fasterxml.jackson.databind.node.ObjectNode;

import models.Car;
import net.jodah.failsafe.CircuitBreaker;
import net.jodah.failsafe.Failsafe;
import play.db.jpa.JPAApi;

@Singleton
public class JPACarRepository implements ICarRepository {

    private final JPAApi jpaApi;
    
    private final CarExecutionContext executionContext;
    
    private final CircuitBreaker circuitBreaker = new CircuitBreaker().withFailureThreshold(1).withSuccessThreshold(3);
    
    private final String selectQuery = "SELECT c FROM Car c";
    private final String selectQueryModels = "SELECT c.id, c.model FROM Car c";
    private final String queryGetById = " where c.id = ";
    private final String queryDeleteById = "delete Car where id = :ID";
    private final String queryOrderById = " order by c.id";

    @Inject
    public JPACarRepository(JPAApi api, CarExecutionContext executionContext) {
        this.jpaApi = api;
        this.executionContext = executionContext;
    }

    @Override
    public CompletionStage<Stream<Car>> getCars() {
        return supplyAsync(() -> this.wrap(em -> this.select(em, this.selectQuery)), this.executionContext);
    }
    
    @Override
    public CompletionStage<Stream<Car>> getCarsModels() {
        return supplyAsync(() -> this.wrap(em -> this.selectModels(em)), this.executionContext);
    }
    
    @Override
	public CompletionStage<Stream<Car>> getCarById(Long id) {
    	final String query = this.selectQuery + this.queryGetById + id;
    	return supplyAsync(() -> this.wrap(em -> this.select(em, query)), this.executionContext);
	}
    
    private Stream<Car> select(EntityManager entityManager, String query) {
    	query+= this.queryOrderById;
    	TypedQuery<Car> typedQuery = entityManager.createQuery(query, Car.class);
        return typedQuery.getResultList().stream();
    }
    
    private Stream<Car> selectModels(EntityManager entityManager) {
    	String selectQuery = this.selectQueryModels + this.queryOrderById;
    	Query query = entityManager.createQuery(selectQuery);
    	
    	List<?> resultList = query.getResultList();
    	List<Car> listCarsModels = new ArrayList<>();
    	for (Object result : resultList) {
    		Object[] arrayResult = (Object[]) result;
    		Car car = new Car();
    		car.id = Long.valueOf(arrayResult[0].toString());
    		car.model = arrayResult[1].toString();
    		
			listCarsModels.add(car);
		}
    	
    	return listCarsModels.stream();
    }
    
    @Override
    public CompletionStage<ObjectNode> insert(Car carData) {
        return supplyAsync(() -> this.wrap(em -> this.insert(em, carData)), this.executionContext);
    }
    
    private ObjectNode insert(EntityManager entityManager, Car carData) {
    	Car carMerge = entityManager.merge(carData);
		ObjectNode newObject = play.libs.Json.newObject();
		newObject.put("id", carMerge.id);
        return newObject;
    }

    @Override
    public CompletionStage<Optional<Car>> update(Long id, Car carData) {
        return supplyAsync(() -> this.wrap(em -> Failsafe.with(this.circuitBreaker).get(() -> this.update(em, id, carData))), this.executionContext);
    }
    
    private Optional<Car> update(EntityManager entityManager, Long id, Car carData) throws InterruptedException {
        final Car data = entityManager.find(Car.class, id);
        if (data != null) {
        	data.year = carData.year;
        	data.personId = carData.personId;
        	data.colorId = carData.colorId;
        	data.model = carData.model;
        }
        Thread.sleep(10000L);
        return Optional.ofNullable(data);
    }
    
    @Override
    public CompletionStage<Optional<Integer>> delete(Long id) {
        return supplyAsync(() -> this.wrap(em -> Failsafe.with(this.circuitBreaker).get(() -> this.delete(em, id))), this.executionContext);
    }
    
    private Optional<Integer> delete(EntityManager entityManager, Long id) {
    	Query query = entityManager.createQuery(this.queryDeleteById);
    	query.setParameter("ID", id);
    	
    	Integer executeUpdate = query.executeUpdate();
    	return Optional.ofNullable(executeUpdate);
    }
    
    private <T> T wrap(Function<EntityManager, T> function) {
        return this.jpaApi.withTransaction(function);
    }
}