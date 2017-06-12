"use strict";

angular.module("meecarros").service("carService", carService);

function carService($http, $q, localStorageService) {
    function getCarsModels() {
        var token = localStorageService.get("token");

        var config = {
            headers: {
                'token': token
            }
        };

        var deferred = $q.defer();

        // $http.get("/cars/models", config)
        //     .then(function(response) {
        //         deferred.resolve(response.data);
        //     }, function(error) {
        //         deferred.reject(error);
        //     });

        // return deferred.promise;
    };

    return {
        "getCarsModels" : getCarsModels
    }
}