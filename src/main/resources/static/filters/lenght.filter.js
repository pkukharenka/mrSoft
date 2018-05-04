angular.module('app').filter('lengthFilter', lengthFilter);

function lengthFilter() {
    return function (items, value) {
        var filtred = [];
        items.forEach(function (item) {
            if (item.length > value) {
                filtred.push(item)
            }
        });
        return filtred;
    }
}