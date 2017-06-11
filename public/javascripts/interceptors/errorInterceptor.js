"use strict";

angular.module("meecarros").factory("errorInterceptor", ["$q", "$location", "$rootScope", errorInterceptor]);

function errorInterceptor($q, $location, $rootScope) {
    return {
        responseError: function(rejection) {
            $rootScope.statusCode = rejection.status;
            $rootScope.errorMessage = rejection.data;
            $location.path("/error");
            return $q.reject(rejection);
        }
    };
}