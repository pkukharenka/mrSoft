angular.module('app').filter('sensitiveFilter', sensitiveFilter);

function sensitiveFilter() {
    return function (items, value) {
        var patt = new RegExp(value);
        var filtred = [];
        items.forEach(function (item) {
            if (patt.test(item)) {
                filtred.push(item)
            }
        });
        return filtred;
    }
}