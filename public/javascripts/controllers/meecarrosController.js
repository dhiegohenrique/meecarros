"use strict";

angular.module("meecarros").controller("meecarrosController", ["$scope", "loginService", "$state", "$q", "localStorageService", "$window", "$rootScope", "loadingService", meecarrosController]);

function meecarrosController($scope, loginService, $state, $q, localStorageService, $window, $rootScope, loadingService) {
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

        $scope.isTokenValid = false;
        loadingService.openModal();
        loginService.validate($scope.city)
            .then(function(response) {
                $scope.isTokenValid = true;
                localStorageService.set("token", response.token);
                $state.go("carlist");
            })
            .catch(function() {
                localStorageService.remove("token");
            })
            .finally(function() {
                loadingService.closeModal();
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