"use strict";

angular.module("meecarros").service("loadingService", ["$uibModal", "$templateCache", "$uibModalStack", loadingService]);

function loadingService($uibModal, $templateCache, $uibModalStack) {
    var service = {};
    var instance;

    service.openModal = function openModal() {
        if (instance) {
            return;
        }

        instance = $uibModal.open({
            template: $templateCache.get("public/javascripts/directives/loadingModal.html"),
            size : "sm",
            backdrop : "static"
        });
    };

    service.openModalTemplate = function openModal(myTemplate) {
        if (instance) {
            return;
        }

        console.log("myTemplate: " + myTemplate);

        instance = $uibModal.open({
            templateUrl : myTemplate,
            size : "sm",
            backdrop : "static"
        });
    };

    service.closeModal = function closeModal() {
        if (!instance) {
            return;
        }

        instance.close();
        instance = null;
    }

    return service;
}