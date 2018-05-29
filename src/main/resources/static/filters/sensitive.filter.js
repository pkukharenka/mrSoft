//wrapped

(function () {
    'use strict';

    angular.module('app').filter('sensitiveFilter', sensitiveFilter);

    function sensitiveFilter() {
        return function (items, value) {
            var pattern = new RegExp(value);
            var filtredData = [];
            items.forEach(function (item) {
                if (pattern.test(item)) {
                    filtredData.push(item)
                }
            });
            return filtredData;
        }
    }
}());

