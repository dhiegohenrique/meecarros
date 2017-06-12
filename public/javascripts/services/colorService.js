"use strict";

angular.module("meecarros").service("colorService", colorService);

function colorService($http, $q, localStorageService) {
    function getColors() {
        var deferred = $q.defer();

        $http.get("/colors")
            .then(function(response) {
                deferred.resolve(response.data);
            }, function(error) {
                deferred.reject(error);
            });

        return deferred.promise;
    };

    return {
        "getColors" : getColors
    }
}