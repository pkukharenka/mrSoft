//wrapped

(function () {
    'use strict';

    angular.module('app').filter('lengthFilter', lengthFilter);

    function lengthFilter() {
        return function (items, value) {
            var filtredData = [];
            items.forEach(function (item) {
                if (item.length > value) {
                    filtredData.push(item)
                }
            });
            return filtredData;
        }
    }
}());

