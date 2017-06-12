"use strict";

angular.module("meecarros").service("carService", carService);

function carService($http, $q, localStorageService) {
    function getPersons() {
        var token = localStorageService.get("token");
        var deferred = $q.defer();

        var config = {
            headers: {
                "token": token
            }
        };

        $http.get("/persons", config)
            .then(function(response) {
                deferred.resolve(response.data);
            }, function(error) {
                deferred.reject(error);
            });

        return deferred.promise;
    };

    return {
        "getPersons" : getPersons
    }
}