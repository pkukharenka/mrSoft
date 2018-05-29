//wrapped

(function () {
    'use strict';

    angular.module('app').factory('secondService', secondService);

    secondService.$inject = ['$http', '$log'];

    function secondService($http, $log) {

        const url = 'http://www.mrsoft.by/data.json';

        return {
            findValues: findValues
        };

        function findValues() {
            return $http.post('/second/', url)
                .then(funcComplete)
                .catch(funcError)
        }

        function funcComplete(response) {
            return response.data;
        }

        function funcError(error) {
            $log.error('Handle error ', error);
        }
    }
}());

