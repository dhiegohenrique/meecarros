"use strict";

angular.module("meecarros").controller("carListController", ["$scope", "$state", "$stateParams", "$rootScope", "carService", "personService", "$q", "colorService", "loadingService", carListController]);

function carListController($scope, $state, $stateParams, $rootScope, carService, personService, $q, colorService, loadingService) {
    var persons;
    var colors;
    var car = $stateParams.car;
    if (car) {
        console.log("chegou: " + JSON.stringify(car));

        var index = getCarIndex(car.id.id);
        if (index < 0) {
            $scope.cars.push(car);
        } else {
            $scope.cars.splice(index, 1, car);
        }
    }

    init();

    $scope.showCar = function(id, $event) {
        if ($rootScope.isCarEdit) {
            showConfirmModal(id, $event);
            return;
        }

        if (isDeletingCar($event)) {
            deleteCar(id);
            return;
        }

        $state.go("car", {"id" : id, "persons" : persons, "colors" : colors});
    };

    function isDeletingCar($event) {
        if (!$event) {
            return false;
        }

        var idElement = $event.target.id;
        if (!idElement) {
            return false;
        }

        return idElement == "delete";
    }

    function showConfirmModal(id, $event) {
        $('#modal-confirm')
            .modal('show')
            .on('click', '#yes', function(e) {
                if (isDeletingCar($event)) {
                    deleteCar(id);
                    return;
                }

                $rootScope.isCarEdit = false;
                $state.go("car", {"id" : id, "persons" : persons, "colors" : colors});
            });
    };

    function getCarIndex(id) {
        console.log("vai pesquisar id: " + id);
        console.log("modelos atuais: " + $scope.cars);

        for (var index = 0; index < $scope.cars.length; index++) {
            if (id == $scope.cars[index].id) {
                return index;
            }
        }

        return -1;
    };

    function deleteCar(id) {
        // loadingService.openModal();
        // carService.deleteCar(id)
        //     .then(function(response) {
        //         var index = getCarIndex(id);
        //         $scope.cars.splice(index, 1);
        //         $state.go("carlist");
        //     })
        //     .finally(function() {
        //         loadingService.closeModal();
        //     });

        $("#modal-logoff")
            .modal("show")
            .on('click', '#yes', function(e) {
                console.log("deletar o carro: " + id);
            });
    };

    function init() {
        var promiseCarsModels = carService.getCarsModels();
        var promisePersons = personService.getPersons();
        var promiseColors = colorService.getColors();

        loadingService.openModal();
        $q.all([promiseCarsModels, promisePersons, promiseColors])
        .then(function(results) {
            $scope.cars = results[0];
            console.log("carros carregados: " + $scope.cars);

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