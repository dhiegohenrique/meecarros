"use strict";

angular.module("meecarros").controller("meecarrosController", ["$scope", "loginService", "$state", "$q", "localStorageService", "$window", "$rootScope", "loadingService", "modalService", meecarrosController]);

function meecarrosController($scope, loginService, $state, $q, localStorageService, $window, $rootScope, loadingService, modalService) {
    $scope.token = "32fffa616b7fb3d2940e99fd06423e04db4591cb";
    
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
        loginService.validate($scope.token)
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
        var options = {
            title : "Confirmação",
            content : "Deseja deslogar?"
        };

        function callBackYes() {
            localStorageService.remove("token");
            $window.location = "/";
        };

        modalService.openConfirmModal(options, callBackYes);
    };

    function showConfirmModal() {
        var options = {
            title : "Confirmação",
            content : "O formulário foi editado. Todas as informações não salvas serão perdidas. Deseja prosseguir?"
        };

        function callBackYes() {
            $rootScope.isCarEdit = false;
            logoff();
        };

        modalService.openConfirmModal(options, callBackYes);
    };
};