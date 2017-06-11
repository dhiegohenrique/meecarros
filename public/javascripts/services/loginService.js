"use strict";

angular.module("meecarros").service("loginService", loginService);

function loginService($http, $q) {
    function validate(city) {
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