//wrapped

(function () {
    'use strict';

    angular.module('app').controller('LayoutController', LayoutController);

    LayoutController.$inject = ['$translate'];

    function LayoutController($translate) {

        var vm = this;

        vm.currentNavItem = 'main';
        vm.changeLanguage = changeLanguage;
        vm.languageIcon = 'icons/USA.svg';

        function changeLanguage(type) {
            if (type === "ru") {
                vm.languageIcon = 'icons/RU.svg';
            } else {
                vm.languageIcon = 'icons/USA.svg';
            }
            $translate.use(type)
        }
    }
}());
