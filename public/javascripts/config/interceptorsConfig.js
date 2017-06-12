"use strict";

angular.module("meecarros").config(["$httpProvider", interceptorsConfig]);

function interceptorsConfig($httpProvider) {
    $httpProvider.interceptors.push("errorInterceptor");
};