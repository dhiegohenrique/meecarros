"use strict";

angular.module("meecarros").service("carService", carService);

function carService($http, $q, localStorageService) {
    function getCarsModels() {
        var token = localStorageService.get("token");
        var deferred = $q.defer();

        var config = {
            headers: {
                "token": token
            }
        };

        $http.get("/cars/models", config)
            .then(function(response) {
                deferred.resolve(response.data);
            }, function(error) {
                deferred.reject(error);
            });

        return deferred.promise;
    };

    function getCarById(id) {
        var token = localStorageService.get("token");
        var deferred = $q.defer();

        var config = {
            headers: {
                "token": token
            }
        };

        $http.get("/cars/" + id, config)
            .then(function(response) {
                deferred.resolve(response.data);
            }, function(error) {
                deferred.reject(error);
            });

        return deferred.promise;
    };

    function insertUpdate(car) {
        var token = localStorageService.get("token");
        var deferred = $q.defer();

        var req = {
            method: (!car.id ? "POST" : "PUT"),
            url: (!car.id ? "/cars" : "/cars/" + car.id),
            headers: {
                "token": token
            },
            data: car
        };

        $http(req)
            .then(function(response) {
                deferred.resolve(response.data);
            }, function(error) {
                deferred.reject(error);
            });

        return deferred.promise;
    };

    function deleteCar(id) {
        var token = localStorageService.get("token");
        var deferred = $q.defer();

        var config = {
            headers: {
                "token": token
            }
        };

        $http.delete("/cars/" + id, config)
            .then(function(response) {
                deferred.resolve(response.data);
            }, function(error) {
                deferred.reject(error);
            });

        return deferred.promise;
    };

    return {
        "getCarsModels" : getCarsModels,
        "getCarById" : getCarById,
        "insertUpdate" : insertUpdate,
        "deleteCar" : deleteCar,
    }
}