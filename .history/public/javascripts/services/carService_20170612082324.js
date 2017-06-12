"use strict";

angular.module("meecarros").service("carService", carService);

function carService($http, $q) {
    function getCarsModels() {
        var deferred = $q.defer();

        var token = {
            "token" : city
        }

        $http.get("/cars/models", token)
            .then(function(response) {
                deferred.resolve(response.data);
            }, function(error) {
                deferred.reject(error);
            });

        return deferred.promise;
    };

    return {
        "getCarsModels" : getCarsModels
    }
}