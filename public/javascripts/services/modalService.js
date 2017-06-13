"use strict";

angular.module("meecarros").service("modalService", ["$ngConfirm", modalService]);

function modalService($ngConfirm) {
    var service = {};

    service.openConfirmModal = function openConfirmModal(options, callBackYes) {
        var modalConfig = {
            title: options.title,
            content: options.content,
            buttons: {
                no: {
                    text: "Não",
                    btnClass: 'btn-default'
                },
                yes: {
                    text: 'Sim',
                    btnClass: 'btn-primary',
                    action: function() {
                        if (callBackYes) {
                            callBackYes();
                        }
                    }
                }
            }
        };

        $ngConfirm(modalConfig);
    };

    return service;
}