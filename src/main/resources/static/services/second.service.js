angular.module('app').factory('secondService', secondService);

secondService.$inject = ['$http', '$log'];

function secondService($http, $log) {

    return {
        findValues: findValues
    };


    function findValues() {
        return $http.get('http://www.mrsoft.by/data.json')
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