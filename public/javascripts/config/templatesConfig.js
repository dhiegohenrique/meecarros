angular.module('meecarros').run(['$templateCache', function($templateCache) {
  'use strict';

  $templateCache.put('public/js/directives/card.html',
    "<div class=\"content-cel-middle\" style=\"padding: 20px;\">\n" +
    "    <div class=\"row\">\n" +
    "        <div class=\"col-xs-12 col-sm-12 col-md-12 col-lg-12\">\n" +
    "            <div class=\"text-center\">\n" +
    "                <i class=\"fa {{icon}} fa-2x\" aria-hidden=\"true\"></i>\n" +
    "            </div>\n" +
    "        </div>\n" +
    "\n" +
    "        <div class=\"col-xs-12 col-sm-12 col-md-12 col-lg-12\">\n" +
    "            <h3 class=\"text-center\" translate=\"{{title}}\"></h3>\n" +
    "        </div>\n" +
    "    </div>\n" +
    "\n" +
    "    <div ng-transclude style=\"padding-top: 10px\">\n" +
    "    </div>\n" +
    "</div>"
  );

  $templateCache.put('public/js/directives/cardRow.html',
    "<div class=\"vertical-align\">\n" +
    "    <div class=\"col-xs-6 col-sm-6 col-md-6 col-lg-6 no-padding\">\n" +
    "        <h4 class=\"text-right\" translate=\"{{title}}\"></h4>\n" +
    "    </div>\n" +
    "        \n" +
    "    <div class=\"col-xs-6 col-sm-6 col-md-6 col-lg-6\">\n" +
    "        <h4 class=\"text-left\">{{text}}</h4>\n" +
    "    </div>\n" +
    "</div>"
  );

  $templateCache.put('public/javascripts/directives/loadingModal.html',
    "<div class=\"modal-body\">\r" +
    "\n" +
    "    <div class=\"row\">\r" +
    "\n" +
    "        <div class=\"col-xs-12 col-sm-12 col-md-11 col-lg-12\">\r" +
    "\n" +
    "            <h4 class=\"text-center\">Carregando...</h4>\r" +
    "\n" +
    "        </div>\r" +
    "\n" +
    "    </div>\r" +
    "\n" +
    "    <div class=\"row\">\r" +
    "\n" +
    "        <div class=\"col-xs-12 col-sm-12 col-md-11 col-lg-12 text-center\">\r" +
    "\n" +
    "            <i class=\"fa fa-spinner fa-pulse fa-3x\"></i>\r" +
    "\n" +
    "        </div>\r" +
    "\n" +
    "    </div>\r" +
    "\n" +
    "</div>"
  );

}]);
