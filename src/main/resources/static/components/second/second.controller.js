//wrapped

(function () {
    'use strict';

    angular.module('app').controller('SecondController', SecondController);

    SecondController.$inject = ['$filter', 'getDataFromMrSoft', 'secondService'];

    function SecondController($filter, getDataFromMrSoft) {
        var vm = this;
        vm.byLength = byLength;
        vm.bySubstring = bySubstring;
        vm.searchKey = '';
        vm.sensitive = '';
        vm.filtredData = [];
        var testValues = getDataFromMrSoft;


        function byLength() {
            vm.filtredData = $filter('lengthFilter')(testValues.data, vm.searchKey);
        }

        function bySubstring() {
            if (vm.sensitive) {
                vm.filtredData = $filter('sensitiveFilter')(testValues.data, vm.searchKey);
            } else {
                vm.filtredData = $filter('filter')(testValues.data, vm.searchKey);
            }
        }
    }
}());
