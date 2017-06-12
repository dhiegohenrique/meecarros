"use strict";

angular.module("meecarros").config(["$stateProvider", "$urlRouterProvider", meecarrosState]);

function meecarrosState($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise("/");

    $stateProvider
    .state("error", {
        url: "/error",
        templateUrl: "./../partials/errorMessage.html",
        controller: "errorMessageController"
    })
    .state("carlist", {
        url: "/carlist",
        templateUrl: "./../partials/carList.html",
        controller: "carListController",
        params: {"weatherData": null}
    })
    .state("car", {
        url: "/car",
        parent: "carlist",
        templateUrl: "./../partials/carForm.html",
        controller: "carController",
        params: {"weatherData": null}
    });
}