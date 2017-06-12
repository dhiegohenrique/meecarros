"use strict";

angular.module("meecarros").controller("meecarrosController", ["$scope", "loginService", "$state", "$q", "localStorageService", "$window", "$rootScope", meecarrosController]);

function meecarrosController($scope, loginService, $state, $q, localStorageService, $window, $rootScope) {
    $scope.city = "32fffa616b7fb3d2940e99fd06423e04db4591cb";
    
    $scope.submitForm = function(isValid) {
        if (!isValid) {
            return;
        }

        if ($scope.isTokenValid) {
            if ($rootScope.isCarEdit) {
                showConfirmModal();
                return;
            }

            logoff();
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
            });
    };

    function logoff() {
        localStorageService.remove("token");
        $window.location = "/";
    };

    function showConfirmModal() {
        $('#modal-confirm')
            .modal('show')
            .on('click', '#yes', function(e) {
                logoff();
            });
    };
};