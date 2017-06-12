"use strict";

angular.module("meecarros").controller("carListController", ["$scope", "$state", "$stateParams", "$rootScope", "carService", "personService", "$q", "colorService", "loadingService", carListController]);

function carListController($scope, $state, $stateParams, $rootScope, carService, personService, $q, colorService, loadingService) {
    var persons;
    var colors;
    
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
    
    var car = $stateParams.car;
    if (car) {
        var index = getCarIndex(car.id);
        if (index < 0) {
            $scope.cars.push(car);
        } else {
            console.log("adiciona no índice: " + index);
            $scope.cars.splice(index, 1, car);
        }
    }

    $scope.showCar = function(id, $event) {
        if ($rootScope.isCarEdit) {
            showConfirmModal(id);
            return;
        }

        if ($event) {
            var idElement = $event.target.id;
            if (idElement && idElement == "delete") {
                deleteCar(id);
                return;
            }
        }

        $state.go("car", {"id" : id, "persons" : persons, "colors" : colors});
    };

    function showConfirmModal(id) {
        $('#modal-confirm')
            .modal('show')
            .on('click', '#yes', function(e) {
                $state.go("car", {"id" : id, "persons" : persons, "colors" : colors});
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
                console.log("deletar o carro: " + id);
            });
    };

    init();

    function init() {
        var promiseCarsModels = carService.getCarsModels();
        var promisePersons = personService.getPersons();
        var promiseColors = colorService.getColors();

        loadingService.openModal();
        $q.all([promiseCarsModels, promisePersons, promiseColors])
        .then(function(results) {
            $scope.cars = results[0];
            persons = results[1];
            colors = results[2];

            if ($scope.car) {
                carMaster = angular.copy($scope.car);
            }
        })
        .finally(function() {
            loadingService.closeModal();
        });
    };
};