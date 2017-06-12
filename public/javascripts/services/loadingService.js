"use strict";

angular.module("meecarros").service("loadingService", ["$uibModal", "$templateCache", "$uibModalStack", loadingService]);

function loadingService($uibModal, $templateCache, $uibModalStack) {
    var service = {};
    var instance;
    var isOpen = false;

    service.openModal = function openModal() {
        if (isOpen) {
            return;
        }

        instance = $uibModal.open({
            template: $templateCache.get("public/javascripts/directives/loadingModal.html"),
            size : "sm",
            backdrop : "static"
        });

        isOpen = true;
        instance.result.finally(function () {
            isOpen = false;
        });
    };

    service.closeModal = function closeModal() {
        if (isOpen) {
            instance.close();
        }
    }

    return service;
}