//wrapped

(function () {
    'use strict';

    angular.module('app').filter('checkedFilter', checkedFilter);

    function checkedFilter() {
        return function (items) {
            var checked = true;
            items.forEach(function (item) {
                if (item.checked) {
                    checked = false;
                    return false
                }
            });
            return checked
        }
    }
}());
