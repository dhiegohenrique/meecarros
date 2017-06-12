"use strict";

angular.module("meecarros").controller("carController", ["$scope", "$state", "$rootScope", carController]);

function carController($scope, $state, $rootScope, $stateParams) {
    $scope.submitted = false;
    $scope.isValidYear = true;
    var id = $stateParams.id;

    $scope.persons = [
        {
            "personId" : "1",
            "name" : "Maria"
        },
        {
            "personId" : "2",
            "name" : "João"
        },
        {
            "personId" : "3",
            "name" : "José"
        }
    ];

    $scope.car = {
        "id" : "1",
        "personId" : "1",
        "model" : "meuModelo",
        "year" : "1999",
        "colorId" : "#008000"
    };

    var carMaster = angular.copy($scope.car);

    $scope.saveCar = function(isValid) {
        $scope.submitted = true;

        $scope.validateYear();
        if (!isValid || !$scope.isValidYear) {
            return;
        }

        $rootScope.isCarEdit = false;
        $state.go("carlist", {"weatherData" : $scope.car});
    };

    $scope.validateYear = function() {
        if (!$scope.car.year) {
            return;
        }

        var year = String($scope.car.year);
        if (year.length < 4) {
            $scope.car.year = "";
            $scope.isValidYear = false;
            return;
        }

        var currentYear = new Date().getFullYear();
        $scope.isValidYear = (currentYear - parseInt($scope.car.year)) < 30;
    };

    $(document).ready(function(){
        $( "input" ).blur(function($event) {
            var id = $event.target.id;
            if (!id) {
                return;
            }

            var value = $event.target.value;
            if (!value) {
                $scope.car[id] = "";
            }

            $rootScope.isCarEdit = !angular.equals(carMaster, $scope.car);
        });

        $('.combobox').combobox();
    });
};