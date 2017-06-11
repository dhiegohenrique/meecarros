"use strict";

angular.module("meecarros").controller("modalConfirmController", ["$scope", "$state", "$rootScope", carController]);

function modalConfirmController($scope, $state, $rootScope) {
    $scope.confirm = function() {
        console.log("confirmou");
    };
};