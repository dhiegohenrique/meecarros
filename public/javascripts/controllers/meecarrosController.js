"use strict";

angular.module("meecarros").controller("meecarrosController", ["$scope", "loginService", "$state", "$q", "localStorageService", "$window", "$rootScope", meecarrosController]);

function meecarrosController($scope, loginService, $state, $q, localStorageService, $window, $rootScope) {
    $scope.city = "32fffa616b7fb3d2940e99fd06423e04db4591cb";
    
    $scope.submitForm = function(isValid) {
        if (!isValid) {
            return;
        }

        if ($scope.isTokenValid) {
            $('#modal-logoff')
                .modal('show')
                .on('click', '#yes', function(e) {
                    logoff();
                });
            return;
        }

        $scope.spinner = true;
        $scope.isTokenValid = false;
        loginService.validate($scope.city)
            .then(function(response) {
                $scope.isTokenValid = true;
                localStorageService.set("token", response.token);
                $state.go("carlist");
            })
            .catch(function() {
                localStorageService.remove("token");
            })
            .finally(function(){
                $scope.spinner = false;
            });
    };

    function logoff() {
        localStorageService.remove("token");
        $window.location = "/";
        // $state.go("/");
        // $state.reload();
        // $location.path("/").replace();
        // $scope.$apply();
        // $scope.city = "";
        // $scope.isTokenValid = false;
        // angular.element("#token").focus();
        // $("#token").focus();
    };
};