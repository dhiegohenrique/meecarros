"use strict";

angular.module("meecarros").service("loadingService", ["$uibModal", "$templateCache", loadingService]);

function loadingService($uibModal, $templateCache) {
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

    service.closeModal = function closeModal() {
        if (!instance) {
            return;
        }

        instance.close();
        instance = null;
    }

    return service;
}