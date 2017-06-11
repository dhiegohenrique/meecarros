"use strict";

angular.module("meusComponentes", [])
    .directive("card", card)
    .directive("cardRow", cardRow);

function card() {
    var directive = {}

    directive.restrict = "E";

    directive.scope = {
        title: "@",
        icon: "@"
    };

    directive.transclude = true;

    directive.templateUrl = "public/js/directives/card.html";

    return directive;
};

function cardRow() {
    var directive = {}

    directive.restrict = "E";

    directive.scope = {
        title: "@",
        text: "@"
    };

    directive.transclude = true;

    directive.templateUrl = "public/js/directives/cardRow.html";

    return directive;
};