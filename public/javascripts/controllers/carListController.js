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

    $scope.showCar = function(id, $event) {
        if ($rootScope.isCarEdit) {
            showConfirmModal();
            return;
        }

        if ($event) {
            var idElement = $event.target.id;
            if (idElement && idElement == "delete") {
                deleteCar();
                return;
            }
        }

        //passar o id por parâmetro
        $state.go("car");
    };

    function showConfirmModal() {
        $('#modal-confirm')
            .modal('show')
            .on('click', '#yes', function(e) {
                //pegar o id do carro clicado
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

    function deleteCar(id) {
        $("#modal-logoff")
            .modal("show")
            .on('click', '#yes', function(e) {
                //pegar o id do carro clicado
                console.log("confirmou");
            });
    };
};