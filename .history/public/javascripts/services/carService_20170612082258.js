"use strict";

angular.module("meecarros").service("carService", carService);

function carService($http, $q) {
    function get(city) {
        var deferred = $q.defer();

        var token = {
            "token" : city
        }

        $http.post("/login", token)
            .then(function(response) {
                deferred.resolve(response.data);
            }, function(error) {
                deferred.reject(error);
            });

        return deferred.promise;
    };

    return {
        "validate" : validate
    }
}