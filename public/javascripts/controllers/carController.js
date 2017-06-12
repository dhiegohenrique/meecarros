"use strict";

angular.module("meecarros").controller("carController", ["$scope", "$state", "$rootScope", "$stateParams", "carService", "$q", "loadingService", carController]);

function carController($scope, $state, $rootScope, $stateParams, carService, $q, loadingService) {
    $scope.submitted = false;
    $scope.isValidYear = true;
    $scope.persons = $stateParams.persons;
    $scope.colors = $stateParams.colors;
    $scope.car = {};
    var carMaster;

    fillInCar();

    $scope.saveCar = function(isValid) {
        console.log("isValid: " + isValid);
        $scope.submitted = true;

        $scope.validateYear();
        if (!isValid || !$scope.isValidYear) {
            return;
        }

        loadingService.openModal();
        carService.insertUpdate($scope.car)
            .then(function(response) {
                $rootScope.isCarEdit = false;
                if (response) {
                    $scope.car.id = response;
                }

                $state.go("carlist", {"car" : $scope.car});
            })
            .finally(function() {
                loadingService.closeModal();
            });
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

    function fillInCar() {
        var id = $stateParams.id;
        if (!id) {
            return;
        }

        loadingService.openModal();
        carService.getCarById(id)
            .then(function(response) {
                $scope.car = response;

                if ($scope.car) {
                    carMaster = angular.copy($scope.car);
                }
            })
            .finally(function() {
                loadingService.closeModal();
            });
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

            if (carMaster) {
                $rootScope.isCarEdit = !angular.equals(carMaster, $scope.car);
            }
        });

        $('.combobox').combobox();
    });
};