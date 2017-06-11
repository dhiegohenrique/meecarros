"use strict";

angular.module("meecarros").controller("carListController", ["$scope", "$state", "$stateParams", "$rootScope", carListController]);

function carListController($scope, $state, $stateParams, $rootScope) {
    $scope.cars = [
        {
            "id" : "1",
            "model" : "modelo1"
        },
        {
            "id" : "2",
            "model" : "modelo2"
        },
        {
            "id" : "3",
            "model" : "modelo3"
        }
    ];
    
    var weatherConditions = $stateParams.weatherData;
    if (weatherConditions) {
        var index = getCarIndex(weatherConditions.id);
        if (index < 0) {
            $scope.cars.push(weatherConditions);
        } else {
            console.log("adiciona no índice: " + index);
            $scope.cars.splice(index, 1, weatherConditions);
        }
    }

    $scope.showCar = function(id) {
        if (!$rootScope.isCarEdit) {
            $state.go("car");
            return;
        }

        $('#modal-confirm')
            .modal('show')
            .on('click', '#yes', function(e) {
                $state.go("car");
            });
    };

    function getCarIndex(id) {
        for (var index = 0; index < $scope.cars.length; index++) {
            if (id == $scope.cars[index].id) {
                return index;
            }
        }

        return -1;
    };
};