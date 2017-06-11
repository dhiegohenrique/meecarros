"use strict";

angular.module("meecarros").controller("errorMessageController", ["$scope", "$rootScope", errorMessageController]);

function errorMessageController($scope, $rootScope) {
    if (!$rootScope.statusCode) {
        return;
    }

    $scope.errorMessage = $rootScope.errorMessage;
    $scope.errorClass = "alert-danger";
    if ($rootScope.statusCode === 401) {
        $scope.errorClass = "alert-warning";
    }
};